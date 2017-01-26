package com.udacity.stockhawk.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Stock;

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
        historyView.setText(stock.getmHistory());
    }
}
