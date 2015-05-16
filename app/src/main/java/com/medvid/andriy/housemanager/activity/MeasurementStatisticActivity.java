package com.medvid.andriy.housemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.utils.SlidrHelper;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/13/2015.
 */
public class MeasurementStatisticActivity extends BaseActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        CompoundButton.OnCheckedChangeListener {

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";

    @InjectView(R.id.tv_measurement_screen_title)
    TextView tv_measurement_screen_title;

    @InjectView(R.id.tv_first_date_select_title)
    TextView tv_first_date_select_title;

    @InjectView(R.id.cb_single_date_selector)
    CheckBox cb_single_date_selector;

    @InjectView(R.id.tv_from_date_selector)
    TextView tv_from_date_selector;

    @InjectView(R.id.tv_second_date_select_title)
    TextView tv_second_date_select_title;

    @InjectView(R.id.tv_to_date_selector)
    TextView tv_to_date_selector;

    @InjectView(R.id.tv_first_time_select_title)
    TextView tv_first_time_select_title;

    @InjectView(R.id.tv_from_time_selector)
    TextView tv_from_time_selector;

    @InjectView(R.id.tv_from_time_select_title)
    TextView tv_from_time_select_title;

    @InjectView(R.id.tv_to_time_selector)
    TextView tv_to_time_selector;

    @InjectView(R.id.tv_bar_chart)
    TextView tv_bar_chart;

    @InjectView(R.id.tv_line_chart)
    TextView tv_line_chart;

    private boolean isFromDateWasSelected = false;
    private boolean isFromTimeWasSelected = false;

    private DatePickerDialog mDatePickerDialog = null;
    private TimePickerDialog mTimePickerDialog = null;

    private boolean mIsVibrate = true;
    private boolean mIs24HoursMode = true;
    private boolean mIsCloseOnSingleTapDay = false;
    private boolean mIsCloseOnSingleTapMinute = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurement_statistic_screen_layout);

        ButterKnife.inject(this);

        initSlidr();

        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {

        cb_single_date_selector.setOnCheckedChangeListener(this);

        tv_from_date_selector.setOnClickListener(this);
        tv_to_date_selector.setOnClickListener(this);

        tv_from_time_selector.setOnClickListener(this);
        tv_to_time_selector.setOnClickListener(this);

        tv_bar_chart.setOnClickListener(this);
        tv_line_chart.setOnClickListener(this);

        initDateAndTimePicker();

        loadSavedDateAndTimePickerInstance(savedInstanceState);
    }

    private void loadSavedDateAndTimePickerInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }

            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
        }
    }

    private void initDateAndTimePicker()    {

        final Calendar calendar = Calendar.getInstance();

        mDatePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), mIsVibrate);
        mDatePickerDialog.setYearRange(1985, 2028);
        mDatePickerDialog.setCloseOnSingleTapDay(mIsCloseOnSingleTapDay);

        mTimePickerDialog = TimePickerDialog.newInstance(this,
                calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE),
                mIs24HoursMode, mIsVibrate);
        mTimePickerDialog.setCloseOnSingleTapMinute(mIsCloseOnSingleTapMinute);

    }

    private void initSlidr() {
        SlidrHelper.initSlidr(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.tv_from_date_selector:
                isFromDateWasSelected = true;
                showDatePickerDialog();
                break;
            case R.id.tv_to_date_selector:
                isFromDateWasSelected = false;
                showDatePickerDialog();
                break;
            case R.id.tv_from_time_selector:
                isFromTimeWasSelected = true;
                showTimePickerDialog();
                break;
            case R.id.tv_to_time_selector:
                isFromTimeWasSelected = false;
                showTimePickerDialog();
                break;
            case R.id.tv_bar_chart:
                startBarChartActivity();
                break;
            case R.id.tv_line_chart:
                startLineChartActivity();
                break;
        }
    }

    private void startBarChartActivity() {
        Intent barChartActivityIntent = new Intent(this, BarChartActivity.class);
        startActivity(barChartActivityIntent);
    }

    private void startLineChartActivity() {
        Intent lineChartActivityIntent = new Intent(this, LineChartActivity.class);
        startActivity(lineChartActivityIntent);
    }

    private void showDatePickerDialog() {
        mDatePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
    }

    private void showTimePickerDialog() {
        mTimePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(c.getTime());

        if (isFromDateWasSelected) {
            tv_from_date_selector.setText(strDate);
        } else {
            tv_to_date_selector.setText(strDate);
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        if (isFromTimeWasSelected) {
            tv_from_time_selector.setText(hourOfDay + " : " + minute);
        } else {
            tv_to_time_selector.setText(hourOfDay + " : " + minute);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        int id = buttonView.getId();

        switch (id) {
            case R.id.cb_single_date_selector:
                if (isChecked) {
                   toggleDateViewsState(isChecked);
                } else {
                    toggleDateViewsState(isChecked);
                }
                break;
        }
    }

    private void toggleDateViewsState(boolean isSingleDate)  {
        int color = 0;

        if(isSingleDate) {
            color = R.color.gray_bright;
        }   else    {
            color = R.color.black;
        }

        tv_second_date_select_title.setTextColor(getResources().getColor(color));
        tv_second_date_select_title.setEnabled(!isSingleDate);

        tv_to_date_selector.setTextColor(getResources().getColor(color));
        tv_to_date_selector.setEnabled(!isSingleDate);
    }
}
