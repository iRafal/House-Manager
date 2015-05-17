package com.medvid.andriy.housemanager.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Андрій on 5/3/2015.
 */
public class DrawableTouchEditText extends EditText {

    private InputMethodManager mInputMethodManager = null;

    public DrawableTouchEditText(Context context) {
        super(context);
        init(context);
    }

    public DrawableTouchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawableTouchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mInputMethodManager = (InputMethodManager) context.
                getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawableTouchEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_UP)  {
            if(event.getRawX() >= this.getRight() - this.getTotalPaddingRight()) {
                DrawableTouchEditText.this.setText("");
//                return true;
            }
        }   else {
            mInputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
        }

        return true;
    }
}
