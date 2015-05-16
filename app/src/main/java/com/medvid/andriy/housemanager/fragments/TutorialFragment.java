package com.medvid.andriy.housemanager.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.activity.EntryActivity;
import com.medvid.andriy.housemanager.activity.TutorialActivity;

import butterknife.ButterKnife;

public class TutorialFragment extends Fragment implements View.OnClickListener{

    private Activity mActivity = null;
    private View mFragmentView = null;

    public static Fragment instantiate() {
        return new TutorialFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.tutorial_fragment_layout, container, false);
        ButterKnife.inject(this, mFragmentView);
        return mFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
    }

    private void initViews()    {
        ButterKnife.findById(mFragmentView, R.id.text).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.text:
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
}