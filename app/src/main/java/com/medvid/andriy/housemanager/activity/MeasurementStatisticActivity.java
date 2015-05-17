package com.medvid.andriy.housemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.rey.material.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

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

        tv_from_date_selector.setOnClickListener(this);
        tv_to_date_selector.setOnClickListener(this);

        tv_from_time_selector.setOnClickListener(this);
        tv_to_time_selector.setOnClickListener(this);

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
        Dialog.Builder builder = new DatePickerDialog
                .Builder(R.style.Material_App_Dialog_DatePicker) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();

                SimpleDateFormat dateFormat =
                        new SimpleDateFormat(getString(
                                R.string.day_and_full_month_name_and_year_date_format));

                String date = dialog.getFormattedDate(dateFormat);
                if (isFromDateWasSelected) {
                    tv_from_date_selector.setText(date);
                } else {
                    tv_to_date_selector.setText(date);
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

        Calendar calendar = Calendar.getInstance();

        Dialog.Builder builder = new TimePickerDialog
                .Builder(R.style.Material_App_Dialog_TimePicker,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)) {

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();
                String time = dialog.getFormattedTime(SimpleDateFormat.getTimeInstance());

                if (isFromTimeWasSelected) {
                    tv_from_time_selector.setText(time);
                } else {
                    tv_to_time_selector.setText(time);

                    super.onPositiveActionClicked(fragment);
                }
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

        tv_to_date_selector.setTextColor(getResources().getColor(color));
        tv_to_date_selector.setEnabled(!isSingleDate);
    }
}
