package com.medvid.andriy.housemanager.activity;

import android.os.Bundle;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.fragments.TutorialFragmentItem;

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
        manager.addFragment(TutorialFragmentItem.instantiateDescriptionFragment(
                getString(R.string.tutorial_page_1_description), R.drawable.tutorial_page_1), "1");
        manager.addFragment(TutorialFragmentItem.instantiateDescriptionFragment(
                getString(R.string.tutorial_page_2_description), R.drawable.tutorial_page_2), "2");
        manager.addFragment(TutorialFragmentItem.instantiateDescriptionFragment(
                getString(R.string.tutorial_page_3_description), R.drawable.list_symbol), "3");
        manager.addFragment(TutorialFragmentItem.instantiateDescriptionFragment(
                getString(R.string.tutorial_page_4_description), R.drawable.tutorial_page_4), "4");
        manager.addFragment(TutorialFragmentItem.instantiateStartFragment(), "5");

        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator.setViewPager(viewPager);
    }

}
