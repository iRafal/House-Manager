package com.medvid.andriy.housemanager.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.utils.CookiesManager;
import com.medvid.andriy.housemanager.utils.ImageUtils;
import com.medvid.andriy.housemanager.utils.SlidrHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 6/5/2015.
 */
public class EditProfileActivity extends BaseActivity implements View.OnClickListener{

    public static final String EDIT_USER_NAME = "edit_user_name";
    public static final String EDIT_USER_PASSWORD = "edit_user_password";

    public static final String EXTRA_PROFILE_EDITING_TYPE = "com.medvid.andriy.housemanager" +
            ".activity.EXTRA_PROFILE_EDITING_TYPE";

    @InjectView(R.id.ll_user_name_settings)
    LinearLayout ll_user_name_settings;
    @InjectView(R.id.ll_password_settings)
    LinearLayout ll_password_settings;

    @InjectView(R.id.tv_password_settings_title)
    TextView tv_password_settings_title;
    @InjectView(R.id.tv_user_name_settings_title)
    TextView tv_user_name_settings_title;

    @InjectView(R.id.et_enter_new_password)
    EditText et_enter_new_password;
    @InjectView(R.id.et_confirm_new_password)
    EditText et_confirm_new_password;
    @InjectView(R.id.btn_change_user_password)
    Button btn_change_user_password;

    @InjectView(R.id.et_new_user_name)
    EditText et_new_user_name;
    @InjectView(R.id.btn_change_user_name)
    Button btn_change_user_name;

    private Drawable errorIc = null;
    private ImageUtils mImageUtils = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_screen_layout);

        ButterKnife.inject(this);
        initSlidr();

        mImageUtils = new ImageUtils(this);
        errorIc = mImageUtils.getEditTextErrorDrawable(R.drawable.err_ic);

        initView();
    }

    private void initView() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String profileEditingType = extras.getString(EXTRA_PROFILE_EDITING_TYPE);
            if(profileEditingType.equals(EDIT_USER_NAME))   {
                showView(ll_user_name_settings);
                hideView(ll_password_settings);
            }   else if(profileEditingType.equals(EDIT_USER_PASSWORD))  {
                showView(ll_password_settings);
                hideView(ll_user_name_settings);
            }
        }

        btn_change_user_name.setOnClickListener(this);
        btn_change_user_password.setOnClickListener(this);
    }

    private void initSlidr() {
        SlidrHelper.initSlidr(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_change_user_name:
                changeUserName();
                break;
            case R.id.btn_change_user_password:
                changeUserPassword();
                break;
        }
    }

    private boolean validateInput(String password, String confirmPassword) {
        boolean validationSuccess = true;
        if(password.isEmpty())  {
            validationSuccess = false;
            et_enter_new_password.setError(getString(R.string.please_enter_user_password), errorIc);
        }

        if(confirmPassword.isEmpty())  {
            validationSuccess = false;
            et_confirm_new_password.setError(getString(R.string.please_confirm_password), errorIc);
        }   else if(!confirmPassword.equals(password))  {
            validationSuccess = false;
            et_confirm_new_password.setError(getString(R.string.confirm_password_must_be_repeated_exactly), errorIc);
        }
        return validationSuccess;
    }

    private void changeUserPassword()   {
        String password = et_enter_new_password.getText().toString();
        String confirmPassword = et_confirm_new_password.getText().toString();
        if(validateInput(password, confirmPassword))    {
            //Saving password actions
            String toastMessage = null;
            if(CookiesManager.updateUserName(password)) {
                toastMessage = getString(R.string.user_password_changed_successfully);
            }   else    {
                toastMessage = getString(R.string.user_password_changing_failed);
            }
            showToast(toastMessage, Toast.LENGTH_LONG);
        }
    }

    private boolean validateInput(String userName) {
        boolean validationSuccess = true;
        if(userName.isEmpty())  {
            validationSuccess = false;
            et_new_user_name.setError(getString(R.string.please_enter_user_name), errorIc);
        }
        return validationSuccess;
    }

    private void changeUserName()   {
        String userName = et_new_user_name.getText().toString();
        if(validateInput(userName)) {
            //Saving user name actions
            String toastMessage = null;
            if(CookiesManager.updateUserName(userName)) {
                toastMessage = getString(R.string.user_name_changed_successfully);
            }   else    {
                toastMessage = getString(R.string.user_name_changed_successfully);
            }
            showToast(toastMessage, Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
