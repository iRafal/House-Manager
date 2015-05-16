package com.medvid.andriy.housemanager.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Андрій on 5/3/2015.
 */
public class BaseActivity extends ActionBarActivity {

    protected String mCurrentFragmentClassName;

    public Fragment getCurrentFragment(int fragment_hold) {
        return getSupportFragmentManager().findFragmentById(fragment_hold);
    }

    /**
     * Replace in activity current fragment by another.
     *
     * @param classInfo class info of new fragment.
     * @param args      arguments for newly created fragment.
     */
    public void replace(final Class<? extends Fragment> classInfo, final Bundle args) {
        replace(classInfo, 0, args, false);
    }

    /**
     * Replace in activity current fragment by another.
     *
     * @param classInfo     class info of new fragment.
     * @param args          arguments for newly created fragment.
     * @param useStack      true - add to "back Stack", otherwise false.
     * @param fragmentPlace - current fragment layout id
     */
    public void replace(Class<? extends Fragment> classInfo, int fragmentPlace, Bundle args, boolean useStack) {
        if (null != classInfo) {
            final Fragment frgCurrent = getCurrentFragment(fragmentPlace);
            final String classCurr = (null == frgCurrent) ? null : frgCurrent.getClass().getName();
            final String className = classInfo.getName();

            if (!className.equals(mCurrentFragmentClassName) || !className.equals(classCurr)) {
                final Fragment fragment = Fragment.instantiate(this, classInfo.getName());

                replace(fragment, args, useStack, fragmentPlace);
            }
        }
    }

    /**
     * Replace in activity current fragment by another.
     *
     * @param fragment      the object that extends the class Fragment.
     * @param args          arguments for fragment.
     * @param useStack      true - add to "back Stack", otherwise false.
     * @param fragmentPlace - current fragment layout id
     */
    public void replace(final Fragment fragment, final Bundle args, final boolean useStack, int fragmentPlace) {
        if (null != fragment && !isFinishing()) {
            Fragment frag = getCurrentFragment(fragmentPlace);
            final String classCurr = (null == frag) ? null : frag.getClass().getName();
            final String className = fragment.getClass().getCanonicalName();

            if (!className.equals(mCurrentFragmentClassName) || !className.equals(classCurr)) {
                // replace fragment now
                final FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction ft = fm.beginTransaction();

                // update activity set fragment
                mCurrentFragmentClassName = className;

                // specify replace parameters for fragment
                if (null != args)
                    fragment.setArguments(args);

                ft.replace(fragmentPlace, fragment, mCurrentFragmentClassName);

                if (useStack)
                    ft.addToBackStack(className);

                ft.commit();
            }
        }
    }
}
