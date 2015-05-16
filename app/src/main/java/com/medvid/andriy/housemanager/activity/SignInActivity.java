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
public class SignInActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.et_user_name_sign_in)
        EditText et_user_name_sign_in;
    @InjectView(R.id.et_password_sign_in)
        EditText et_password_sign_in;
    @InjectView(R.id.tv_sign_in)
        Button tv_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_screen_layout);

        ButterKnife.inject(this);

        initSlidr();

        initViews();

    }

    private void initViews()    {
        tv_sign_in.setOnClickListener(this);
    }

    private void signInAction() {
        String userNameOrEmail = et_user_name_sign_in.getText().toString();
        String password = et_password_sign_in.getText().toString();

        startEntryActivity();
    }

    private void startEntryActivity()    {
        Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_sign_in:
                signInAction();
                break;
        }
    }

    private void initSlidr() {
        SlidrHelper.initSlidr(this);
    }
}
