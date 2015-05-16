package com.medvid.andriy.housemanager.activity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.utils.SlidrHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ????? on 5/15/2015.
 */
public class BarChartActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    @InjectView(R.id.bar_chart_view)
    BarChart bar_chart_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.animated_bar_chart_activity_layout);

        ButterKnife.inject(this);

        initSlidr();

        initView();
    }

    private void initView() {
        bar_chart_view.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        bar_chart_view.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        bar_chart_view.setPinchZoom(true);

        bar_chart_view.setDrawBarShadow(false);
        bar_chart_view.setDrawGridBackground(false);

        XAxis xAxis = bar_chart_view.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);

        bar_chart_view.getAxisLeft().setDrawGridLines(false);

        // add a nice and smooth animation
        bar_chart_view.animateY(2500);

        //
        //bar_chart_view.getLegend().setEnabled(true);

        //
         Legend l = bar_chart_view.getLegend();
         l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
         l.setFormSize(8f);
         l.setFormToTextSpace(4f);
         l.setXEntrySpace(6f);

        bar_chart_view.setEnabled(false);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        int xMax = 7;
        int yMax = 100;

        for (int i = 0; i < xMax + 1; i++) {
            float mult = yMax + 1;
            float val1 = (float) (Math.random() * mult) + mult / 3;
            yVals1.add(new BarEntry((int) val1, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < xMax + 1; i++) {
            xVals.add((int) yVals1.get(i).getVal() + "");
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Data Set");
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setDrawValues(false);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);

        bar_chart_view.setData(data);
        //bar_chart_view.invalidate();
    }

    private void initSlidr() {
        SlidrHelper.initSlidr(this);
    }

    private void saveToGallery() {
        if (bar_chart_view.saveToGallery("title" + System.currentTimeMillis(), 50)) {
            Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                    Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
