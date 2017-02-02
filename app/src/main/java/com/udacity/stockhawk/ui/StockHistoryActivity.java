package com.udacity.stockhawk.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.stetho.common.ArrayListAccumulator;
import com.facebook.stetho.common.StringUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Stock;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by rkalonji on 01/26/2017.
 */

public class StockHistoryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stock_history);

        Intent i = getIntent();

        Stock stock = (Stock) i.getParcelableExtra("SelectedStock");

        TextView historyView = (TextView) findViewById(R.id.stock_history_history);
        historyView.setText(stock.getmSymbol());

        LineChart lineChart = (LineChart) findViewById(R.id.line_chart_view);
        ArrayList<Entry> entries = new ArrayList<Entry>();
        List<String> quotes = Arrays.asList(stock.getmHistory().split("\\r?\\n"));
        Collections.reverse(quotes);

        Calendar cl = Calendar.getInstance();

        ArrayList<String> dates = new ArrayList<String>();

        int j = 0;
        for (String quote:quotes) {
            String[] values = quote.split(",");

            try {
                cl.setTime(new SimpleDateFormat("MM-dd-YY").parse(values[0]));
            } catch (ParseException e) {
            }
            
            String date =  cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.DAY_OF_MONTH) + "-" + cl.get(Calendar.YEAR);
            dates.add(date);
            entries.add(new Entry((float)j, Float.parseFloat(values[1])));
            j++;
        }
        LineDataSet dataSet= new LineDataSet(entries, "");
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        Object[] datesObject = dates.toArray();
        xAxis.setValueFormatter(new MyValueFormatter(Arrays.copyOf(datesObject,datesObject.length,
                String[].class)));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setDrawLabels(false);

        lineChart.invalidate();
    }
}
