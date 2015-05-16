package com.medvid.andriy.housemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/3/2015.
 */
public class EntryActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.tv_sign_in_action)
        TextView tv_sign_in_action;
    @InjectView(R.id.tv_sign_up_action)
        TextView tv_sign_up_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_screen_layout);

        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        tv_sign_in_action.setOnClickListener(this);
        tv_sign_up_action.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_sign_in_action:
                startSignInScreen();
                break;
            case R.id.tv_sign_up_action:
                startSignUpScreen();
                break;
        }
    }

    private void startSignInScreen()    {
        Intent mainIntent = new Intent(EntryActivity.this, SignInActivity.class);
        startActivity(mainIntent);
    }

    private void startSignUpScreen()    {
        Intent mainIntent = new Intent(EntryActivity.this, SignUpActivity.class);
        startActivity(mainIntent);
    }
}
