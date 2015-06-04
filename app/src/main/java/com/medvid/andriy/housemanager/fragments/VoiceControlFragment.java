package com.medvid.andriy.housemanager.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.activity.VoiceControlInfoActivity;
import com.medvid.andriy.housemanager.utils.DialogUtils;
import com.medvid.andriy.housemanager.utils.ImageUtils;
import com.medvid.andriy.housemanager.voice_recognition.RecognitionManager;
import com.melnykov.fab.FloatingActionButton;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Андрій on 5/4/2015.
 */
public class VoiceControlFragment extends Fragment
        implements ScreenShotable, View.OnClickListener,
        RecognitionManager.OnCommandRecognizedListener {

    public static final String VOICE_CONTROL_SCREEN = "Voice Control";

    private ActionBarActivity mActionBarActivity = null;
    private ActionBar mActionBar = null;
    private View mFragmentView = null;
    private ImageUtils mImageUtils = null;

    private DialogUtils mDialogUtils = null;
    private RecognitionManager mRecognitionManager = null;

    @InjectView(R.id.floating_button)
        FloatingActionButton floating_button;
    @InjectView(R.id.rb_ripple_microphone_background)
        RippleBackground mRippleBackground;
    @InjectView(R.id.iv_center_image)
        ImageView iv_center_image;
    @InjectView(R.id.tv_command_title)
        TextView tv_command_title;

    private ProgressDialog mScreenInitializingDialog = null;
    private ProgressDialog mExecutingCommandDialog = null;

    public static VoiceControlFragment instantiate()    {
        return new VoiceControlFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.voice_control_screen_layout, container, false);
        ButterKnife.inject(this, mFragmentView);
        return mFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = mActionBarActivity.getString(R.string.voice_recognition);
        String message = mActionBarActivity.getString(R.string.initialization);

        mScreenInitializingDialog = mDialogUtils.getProgressDialog(title, message, true, false);
        mScreenInitializingDialog.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar = mActionBarActivity.getSupportActionBar();
        mActionBar.setTitle(R.string.voice_control);
        mActionBar.invalidateOptionsMenu();

        initViews();

        mRecognitionManager = new RecognitionManager(mActionBarActivity, getList(), this);
    }

    private List<String> getList()  {
        List<String> commandsList = new ArrayList<>();
        commandsList.add("switch on the lamp");
        commandsList.add("switch off the lamp");

        commandsList.add("switch on thermometer");
        commandsList.add("switch off thermometer");

        commandsList.add("switch on conditioner");
        commandsList.add("switch off conditioner");

        commandsList.add("switch on chandelier");
        commandsList.add("switch off chandelier");

        return commandsList;
    }

    private void initViews()    {
        iv_center_image.setOnClickListener(this);
        floating_button.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecognitionManager.stopRecognition();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActionBarActivity = (ActionBarActivity)activity;
        mImageUtils = new ImageUtils(activity);
        mDialogUtils = new DialogUtils(activity);
    }

    @Override
    public void takeScreenShot() {
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.iv_center_image:
               onRippleAction();
                break;
            case R.id.floating_button:
                startVoiceControlInfoScreen();
                break;
        }
    }

    private void startVoiceControlInfoScreen() {
        Intent voiceControlInfoActivityIntent =
                new Intent(mActionBarActivity, VoiceControlInfoActivity.class);
        mActionBarActivity.startActivity(voiceControlInfoActivityIntent);
    }

    private void toggleFloatingActionButton()   {
        if(floating_button.isVisible())   {
            floating_button.hide(true);
        }   else    {
            floating_button.show(true);
        }
    }

    private void onRippleAction()   {
        if(!mRippleBackground.isRippleAnimationRunning()) {
            mRippleBackground.startRippleAnimation();
            tv_command_title.setVisibility(View.VISIBLE);
            iv_center_image.setImageDrawable(
                    mImageUtils.getDrawableFromResource(R.drawable.microphone_white));

            mRecognitionManager.startRecognition();

        }   else    {
            mRippleBackground.stopRippleAnimation();
            iv_center_image.setImageDrawable(
                    mImageUtils.getDrawableFromResource(R.drawable.microphone_black));
            mRecognitionManager.pauseRecognition();
        }

        toggleFloatingActionButton();
    }

    @Override
    public void onInitializationFinished() {
        mScreenInitializingDialog.hide();
    }

    @Override
    public void onCommandRecognized(String command) {
        tv_command_title.setText(command);
    }

    @Override
    public void onRecognitionFinished(String command) {
        onRippleAction();
        String lastCommand = mActionBarActivity.getString(R.string.last_command);
        String newTitle = String.format("%s:\n\"%s\"", lastCommand, tv_command_title.getText());

        tv_command_title.setText(newTitle);

        String title = mActionBarActivity.getString(R.string.executing_command);
        String message = String.format("\"%s\"", command);
        mExecutingCommandDialog = mDialogUtils.getProgressDialog(title, message);
        mExecutingCommandDialog.show();
    }
}