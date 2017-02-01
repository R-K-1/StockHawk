package com.udacity.stockhawk.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.udacity.stockhawk.R;

/**
 * Created by rkalonji on 01/31/2017.
 */

public class StockWidgetViewsFactory implements RemoteViewsFactory {
    List<Stock> mStockCollections = new ArrayList<Stock>();

    Context mContext = null;

    public StockWidgetViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mStockCollections.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(), R.layout.list_item_quote);
        Stock stock = mStockCollections.get(position);
        mView.setTextViewText(R.id.symbol, stock.getmSymbol());
        mView.setTextViewText(R.id.price, stock.getmPrice());

        float rawAbsoluteChange = Float.parseFloat(stock.getmAbsoluteChange());
        float percentageChange = Float.parseFloat(stock.getmPercentageChange());

        if (rawAbsoluteChange > 0) {
            mView.setInt(R.id.change, "setBackgroundResource",
                    R.drawable.percent_change_pill_green);
        } else {
            mView.setInt(R.id.change, "setBackgroundResource",
                    R.drawable.percent_change_pill_red);
        }

        String change = PrefUtils.dollarFormatWithPlus(rawAbsoluteChange);
        String percentage = PrefUtils.percentageFormat(percentageChange / 100);

        if (PrefUtils.getDisplayMode(mContext)
                .equals(mContext.getString(R.string.pref_display_mode_absolute_key))) {
            mView.setTextViewText(R.id.change, change);
        } else {
            mView.setTextViewText(R.id.change, percentage);
        }

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(StockWidgetProvider.ACTION_VIEW_STOCK_HISTORY);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(StockWidgetProvider.EXTRA_SELECTED_STOCK,
                stock);
        fillInIntent.putExtras(bundle);
        mView.setOnClickFillInIntent(R.id.list_item_linear_layout, fillInIntent);
        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
        mStockCollections.clear();

        Cursor c = mContext.getApplicationContext().getContentResolver().query(Contract.Quote.URI,
                null, null, null, null);

        if (c != null && c.moveToFirst()) {
            do {
                mStockCollections.add(new Stock(
                        c.getString(c.getColumnIndex(Contract.Quote.COLUMN_SYMBOL)),
                        c.getString(c.getColumnIndex(Contract.Quote.COLUMN_PRICE)),
                        c.getString(c.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE)),
                        c.getString(c.getColumnIndex(Contract.Quote.COLUMN_PERCENTAGE_CHANGE)),
                        c.getString(c.getColumnIndex(Contract.Quote.COLUMN_HISTORY))
                ));
            } while (c.moveToNext());
        }
    }

    @Override
    public void onDestroy() { }
}
