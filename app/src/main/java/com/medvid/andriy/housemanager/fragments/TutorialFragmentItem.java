package com.medvid.andriy.housemanager.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.activity.EntryActivity;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TutorialFragmentItem extends Fragment implements View.OnClickListener {

    private static final String DESCRIPTION = "TutorialFragmentItem.DESCRIPTION";
    private static final String START = "TutorialFragmentItem.START";

    private String mTitleString = null;
    private int mImageResId = 0;
    private String mViewMode = null;

    private Activity mActivity = null;

    @InjectView(R.id.ll_tutorial_description)
    LinearLayout ll_tutorial_description;

    @InjectView(R.id.tv_description)
    TextView tv_description;

    @InjectView(R.id.ll_lets_start)
    LinearLayout ll_lets_start;

    @InjectView(R.id.btn_start)
    Button btn_start;

    public static Fragment instantiateStartFragment() {
        TutorialFragmentItem tutorialFragmentItem = new TutorialFragmentItem();
        tutorialFragmentItem.setViewMode(START);
        return tutorialFragmentItem;
    }

    public static Fragment instantiateDescriptionFragment(String title, int imageResId) {
        TutorialFragmentItem tutorialFragmentItem = new TutorialFragmentItem();
        tutorialFragmentItem.setImageResId(imageResId);
        tutorialFragmentItem.setTitleString(title);
        tutorialFragmentItem.setViewMode(DESCRIPTION);
        return tutorialFragmentItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.tutorial_fragment_layout, container, false);
        ButterKnife.inject(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mViewMode.equals(DESCRIPTION))   {
            ll_tutorial_description.setVisibility(View.VISIBLE);
            ll_lets_start.setVisibility(View.GONE);
        }   else if(mViewMode.equals(START))    {
            ll_tutorial_description.setVisibility(View.GONE);
            ll_lets_start.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
    }

    private void initViews()    {
        btn_start.setOnClickListener(this);

        if(mViewMode == DESCRIPTION)    {
            tv_description.setText(mTitleString);
            tv_description.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, mImageResId);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_start:
                Intent mainIntent = new Intent(mActivity, EntryActivity.class);
                startActivity(mainIntent);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public void setTitleString(String titleString) {
        this.mTitleString = titleString;
    }

    public void setImageResId(int imageResId) {
        this.mImageResId = imageResId;
    }

    public void setViewMode(String viewMode) {
        this.mViewMode = viewMode;
    }
}