package com.medvid.andriy.housemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.dataset.User;
import com.medvid.andriy.housemanager.utils.CookiesManager;
import com.medvid.andriy.housemanager.utils.SlidrHelper;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/3/2015.
 */
public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String SIGN_IN_ACTIVITY_TAG = "SignInActivity";

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
        String userName = et_user_name_sign_in.getText().toString();
        String password = et_password_sign_in.getText().toString();

        if(validateInput(userName, password))   {
            //Init User object
            Gson gson = new Gson();

            Random randomGenerator = new Random();
            int randomId = randomGenerator.nextInt(Integer.MAX_VALUE);

            User currentUser = User.getSimpleUser(randomId, userName, password);
            String userStringJsonObject = gson.toJson(currentUser);

            //After send for validation to server
            //TODO: async task for sending user info to server for validation

            //TODO: In the same thread if success write data for auto login
            CookiesManager.writeUserData(userStringJsonObject);

            //TODO: start in sign in thread by handler
            startEntryActivity();
        }
    }

    private boolean validateInput(String userName, String password) {
        boolean validationSuccess = true;
        if(userName.isEmpty())  {
            validationSuccess = false;
            //
            et_user_name_sign_in.setError(getString(R.string.please_enter_user_name));
        }

        if(password.isEmpty())  {
            validationSuccess = false;
            //
            et_password_sign_in.setError(getString(R.string.please_enter_user_password));
        }
        return validationSuccess;
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
