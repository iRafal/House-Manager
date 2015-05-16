package com.medvid.andriy.housemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.utils.SlidrHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/3/2015.
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.et_user_name_sign_up)
        EditText et_user_name_sign_up;
    @InjectView(R.id.et_password_sign_up)
        EditText et_password_sign_up;
    @InjectView(R.id.et_confirm_password_sign_up)
        EditText et_confirm_password_sign_up;
    @InjectView(R.id.tv_sign_up)
        Button tv_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen_layout);

        ButterKnife.inject(this);

        initSlidr();

        initViews();
    }

    private void initViews()   {
        tv_sign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_sign_up:
                signUpAction();
                break;
        }
    }

    private void signUpAction() {
        String userNameOrEmail = et_user_name_sign_up.getText().toString();
        String password = et_password_sign_up.getText().toString();
        String confirmPassword = et_confirm_password_sign_up.getText().toString();

      startEntryActivity();
    }

    private void startEntryActivity()    {
        Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

    private void initSlidr() {
        SlidrHelper.initSlidr(this);
    }
}
