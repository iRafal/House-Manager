package com.medvid.andriy.housemanager.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.medvid.andriy.housemanager.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/15/2015.
 */
public class LineChartActivity extends BaseActivity implements OnChartValueSelectedListener {

    @InjectView(R.id.line_chart)
    LineChart line_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_chart_screen_layout);

        ButterKnife.inject(this);

        initView();
    }

    private void initView() {

        line_chart.setOnChartValueSelectedListener(this);

        // no description text
        line_chart.setDescription("");
        line_chart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable value highlighting
        line_chart.setHighlightEnabled(true);

        // enable touch gestures
        line_chart.setTouchEnabled(true);

        line_chart.setDragDecelerationFrictionCoef(0.95f);

        // enable scaling and dragging
        line_chart.setDragEnabled(true);
        line_chart.setScaleEnabled(true);
        line_chart.setDrawGridBackground(false);
        line_chart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        line_chart.setPinchZoom(true);

        // set an alternative background color
        line_chart.setBackgroundColor(Color.WHITE);

        // add data
        setData(20, 30);

        line_chart.animateX(2500);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
        Legend l = line_chart.getLegend();
        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tf);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        XAxis xAxis = line_chart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = line_chart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaxValue(200f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = line_chart.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setTextColor(ColorTemplate.getHoloBlue());
        rightAxis.setAxisMaxValue(200f);
        rightAxis.setDrawGridLines(true);
    }

    private void saveToGallery() {
        if (line_chart.saveToGallery(getString(R.string.line_chart)
                + " " + System.currentTimeMillis(), 50)) {
            showToast(R.string.saved_to_gallery, Toast.LENGTH_LONG);
        } else {
            showToast(R.string.saving_to_gallery_failed, Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_save_to_gallery:
                saveToGallery();
                break;
        }
        return true;
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 50;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals1.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.WHITE);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());

        set1.setLineWidth(2.5f);
        set1.setCircleSize(4.5f);
        set1.setHighLightColor(Color.rgb(244, 117, 117));

        set1.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        set1.setDrawCircleHole(true);
        set1.setCircleColorHole(Color.WHITE);

        set1.setDrawValues(true);


        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);

        // set data
        line_chart.setData(data);
    }
}
