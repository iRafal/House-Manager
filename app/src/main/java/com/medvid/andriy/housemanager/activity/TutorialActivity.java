package com.medvid.andriy.housemanager.activity;

import android.os.Bundle;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.fragments.TutorialFragment;
import com.medvid.andriy.housemanager.utils.DialogBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;
import multiplemodel.viewpager.ModelPagerAdapter;
import multiplemodel.viewpager.PagerManager;


public class TutorialActivity extends BaseActivity {

    @InjectView(R.id.view_pager)
    ScrollerViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_screen_layout);

        ButterKnife.inject(this);

        initViews();
    }

    private void initViews()    {

        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = ButterKnife.findById(this,R.id.indicator);

        PagerManager manager = new PagerManager();
        manager.addFragment(TutorialFragment.instantiate());
        manager.addFragment(TutorialFragment.instantiate());
        manager.addFragment(TutorialFragment.instantiate());
        manager.addFragment(TutorialFragment.instantiate());

        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator.setViewPager(viewPager);
    }

}
