package com.medvid.andriy.housemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.medvid.andriy.housemanager.utils.DateUtils;
import com.rey.material.widget.CheckBox;

import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DatePickerDialog;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.utils.SlidrHelper;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/13/2015.
 */
public class MeasurementStatisticActivity extends BaseActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    @InjectView(R.id.tv_measurement_screen_title)
    TextView tv_measurement_screen_title;

    @InjectView(R.id.tv_first_date_select_title)
    TextView tv_first_date_select_title;

    @InjectView(R.id.cb_single_date_selector)
    CheckBox cb_single_date_selector;

    @InjectView(R.id.tv_from_date_selector)
    TextView tv_first_date_selector;

    @InjectView(R.id.tv_second_date_select_title)
    TextView tv_second_date_select_title;

    @InjectView(R.id.tv_to_date_selector)
    TextView tv_second_date_selector;

    @InjectView(R.id.tv_first_time_select_title)
    TextView tv_first_time_select_title;

    @InjectView(R.id.tv_from_time_selector)
    TextView tv_first_time_selector;

    @InjectView(R.id.tv_from_time_select_title)
    TextView tv_from_time_select_title;

    @InjectView(R.id.tv_to_time_selector)
    TextView tv_second_time_selector;

    @InjectView(R.id.tv_bar_chart)
    TextView tv_bar_chart;

    @InjectView(R.id.tv_line_chart)
    TextView tv_line_chart;

    private boolean isFirstDateWasSelected = false;
    private boolean isFirstTimeWasSelected = false;

    private Calendar mFirstDate = null;
    private Calendar mSecondDate = null;

    private Calendar mFirstTime = null;
    private Calendar mSecondTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurement_statistic_screen_layout);

        ButterKnife.inject(this);

        initSlidr();
        initView();
    }

    private void initView() {

        cb_single_date_selector.setOnCheckedChangeListener(this);

        tv_first_date_selector.setOnClickListener(this);
        tv_second_date_selector.setOnClickListener(this);

        tv_first_time_selector.setOnClickListener(this);
        tv_second_time_selector.setOnClickListener(this);

        tv_bar_chart.setOnClickListener(this);
        tv_line_chart.setOnClickListener(this);
    }

    private void initSlidr() {
        SlidrHelper.initSlidr(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.tv_from_date_selector:
                isFirstDateWasSelected = true;
                showDatePickerDialog();
                break;
            case R.id.tv_to_date_selector:
                isFirstDateWasSelected = false;
                showDatePickerDialog();
                break;
            case R.id.tv_from_time_selector:
                isFirstTimeWasSelected = true;
                showTimePickerDialog();
                break;
            case R.id.tv_to_time_selector:
                isFirstTimeWasSelected = false;
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

        Calendar datePickerValueCalendar = null;

        if (isFirstDateWasSelected && mFirstDate != null) {
            datePickerValueCalendar = mFirstDate;
        } else if (!isFirstDateWasSelected && mSecondDate != null) {
            datePickerValueCalendar = mSecondDate;
        } else {
            datePickerValueCalendar = Calendar.getInstance();
        }

        int maxAndMinYearPeriod = 25;
        int maxAndMinDay = 1;
        int maxAndMinMonth = 1;
        int minYear = datePickerValueCalendar.get(Calendar.YEAR) - maxAndMinYearPeriod;
        int maxYear = datePickerValueCalendar.get(Calendar.YEAR) + maxAndMinYearPeriod;

        int currentDay = datePickerValueCalendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = datePickerValueCalendar.get(Calendar.MONTH);
        int currentYear = datePickerValueCalendar.get(Calendar.YEAR);

        Dialog.Builder builder = new DatePickerDialog
                .Builder(
                R.style.Material_App_Dialog_DatePicker,
                maxAndMinDay, maxAndMinMonth, minYear,
                maxAndMinDay, maxAndMinMonth, maxYear,
                currentDay, currentMonth, currentYear) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();

                SimpleDateFormat dateFormat =
                        new SimpleDateFormat(getString(
                                R.string.day_and_full_month_name_and_year_date_format));

                boolean setNewData = true;
                Calendar calendar = dialog.getCalendar();

                if (isFirstDateWasSelected) {
                    if (mSecondDate != null && !cb_single_date_selector.isChecked()) {
                        if (DateUtils.compareTwoDates(calendar, mSecondDate) == 0) {
                            //Can't be the same as Second Date.
                            showToast(R.string.selected_date_can_not_be_the_same_as_second_date, Toast.LENGTH_LONG);
                            setNewData = false;
                        } else if (DateUtils.compareTwoDates(calendar, mSecondDate) > 0) {
                            //Can't be bigger than Second Date
                            showToast(R.string.selected_date_can_not_be_bigger_then_second_date, Toast.LENGTH_LONG);
                            setNewData = false;
                        } else {
                            mFirstDate = calendar;
                        }
                    } else {
                        mFirstDate = calendar;
                    }
                }

                if (!isFirstDateWasSelected) {
                    if (mFirstDate != null) {
                        if (DateUtils.compareTwoDates(calendar, mFirstDate) == 0) {
                            //Can't be the same as First Date.
                            showToast(R.string.selected_date_can_not_be_the_same_as_first_date, Toast.LENGTH_LONG);
                            setNewData = false;
                        } else if (DateUtils.compareTwoDates(calendar, mFirstDate) < 0) {
                            //Can't be less than First Date
                            showToast(R.string.selected_date_can_not_lessr_then_first_date, Toast.LENGTH_LONG);
                            setNewData = false;
                        } else {
                            mSecondDate = calendar;
                        }
                    } else {
                        mSecondDate = calendar;
                    }
                }

                if (setNewData) {
                    String date = dialog.getFormattedDate(dateFormat);

                    if (isFirstDateWasSelected) {
                        tv_first_date_selector.setText(date);
                    } else {
                        tv_second_date_selector.setText(date);
                    }
                }
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction(getString(R.string.ok)).
                negativeAction(getString(R.string.cancel));

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }

    private void showTimePickerDialog() {

        Calendar timePickerValueCalendar = null;

        if (isFirstTimeWasSelected && mFirstTime != null) {
            timePickerValueCalendar = mFirstTime;
        } else if (!isFirstTimeWasSelected && mSecondTime != null) {
            timePickerValueCalendar = mSecondTime;
        } else {
            timePickerValueCalendar = Calendar.getInstance();
        }

        Dialog.Builder builder = new TimePickerDialog
                .Builder(R.style.Material_App_Dialog_TimePicker,
                timePickerValueCalendar.get(Calendar.HOUR_OF_DAY),
                timePickerValueCalendar.get(Calendar.MINUTE)) {

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();

                boolean setNewTime = true;
                Calendar calendar = Calendar.getInstance();

                if (isFirstTimeWasSelected) {

                    calendar.set(Calendar.HOUR_OF_DAY, dialog.getHour());
                    calendar.set(Calendar.MINUTE, dialog.getMinute());

                    if (mSecondTime != null) {
                        if (DateUtils.compareTwoTimes(calendar, mSecondTime) == 0) {
                            //Can't be the same as Second Time.
                            showToast(R.string.selected_time_can_not_be_the_same_as_second_time,
                                    Toast.LENGTH_LONG);
                            setNewTime = false;
                        } else if (DateUtils.compareTwoTimes(calendar, mSecondTime) > 0) {
                            //Can't be bigger than Second Time
                            showToast(R.string.selected_time_can_not_be_bigger_then_second_time,
                                    Toast.LENGTH_LONG);
                            setNewTime = false;
                        } else {
                            mFirstTime = calendar;
                        }
                    } else {
                        mFirstTime = calendar;
                    }
                }

                if (!isFirstTimeWasSelected) {
                    calendar.set(Calendar.HOUR_OF_DAY, dialog.getHour());
                    calendar.set(Calendar.MINUTE, dialog.getMinute());

                    if (mFirstTime != null) {
                        if (DateUtils.compareTwoTimes(calendar, mFirstTime) == 0) {
                            //Can't be the same as First Time.
                            showToast(R.string.selected_time_can_not_be_the_same_as_first_time,
                                    Toast.LENGTH_LONG);
                            setNewTime = false;
                        } else if (DateUtils.compareTwoTimes(calendar, mFirstTime) < 0) {
                            //Can't be less than First Time
                            showToast(R.string.selected_time_can_not_lessr_then_first_time,
                                    Toast.LENGTH_LONG);
                            setNewTime = false;
                        } else {
                            mSecondTime = calendar;
                        }
                    } else {
                        mSecondTime = calendar;
                    }
                }

                if (setNewTime) {
                    String time = dialog.getFormattedTime(SimpleDateFormat.getTimeInstance());

                    if (isFirstTimeWasSelected) {
                        tv_first_time_selector.setText(time);
                    } else {
                        tv_second_time_selector.setText(time);
                    }
                }
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction(getString(R.string.ok)).
                negativeAction(getString(R.string.cancel));

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
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

                    if(mFirstTime == null && mSecondDate == null)   {
                        return;
                    }

                    if (DateUtils.compareTwoDates(mFirstDate, mSecondDate) >= 0) {
                        Calendar dayBefore = Calendar.getInstance();
                        dayBefore.set(mFirstDate.get(Calendar.YEAR),
                                mFirstDate.get(Calendar.MONTH),
                                mFirstDate.get(Calendar.DAY_OF_MONTH));
                        dayBefore.add(Calendar.DAY_OF_MONTH, -1);

                        SimpleDateFormat dateFormat =
                                new SimpleDateFormat(getString(
                                        R.string.day_and_full_month_name_and_year_date_format));

                        tv_second_date_selector.setText(dateFormat.format(dayBefore.getTime()));
                    }
                }
                break;
        }
    }

    private void toggleDateViewsState(boolean isSingleDate) {
        int color = 0;

        if (isSingleDate) {
            color = R.color.gray_bright;
        } else {
            color = R.color.black;
        }

        tv_second_date_select_title.setTextColor(getResources().getColor(color));
        tv_second_date_select_title.setEnabled(!isSingleDate);

        tv_second_date_selector.setTextColor(getResources().getColor(color));
        tv_second_date_selector.setEnabled(!isSingleDate);
    }

    private void showToast(String toastMessage, int toastLength) {
        Toast.makeText(MeasurementStatisticActivity.this,
                toastMessage, toastLength).show();
    }

    private void showToast(int messageResId, int toastLength) {
        String message = getString(messageResId);
        showToast(message, toastLength);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
