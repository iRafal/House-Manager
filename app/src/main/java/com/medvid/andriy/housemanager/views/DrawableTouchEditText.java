package com.medvid.andriy.housemanager.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * Created by Андрій on 5/3/2015.
 */
public class DrawableTouchEditText extends EditText {
    public DrawableTouchEditText(Context context) {
        super(context);
    }

    public DrawableTouchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableTouchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawableTouchEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_UP)  {
            if(event.getRawX() >= this.getRight() - this.getTotalPaddingRight()) {
                DrawableTouchEditText.this.setText("");
                return true;
            }
        }
        return true;
    }
}
