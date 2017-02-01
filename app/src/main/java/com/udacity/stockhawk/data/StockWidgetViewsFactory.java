package com.udacity.stockhawk.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

/**
 * Created by rkalonji on 01/31/2017.
 */

public class StockWidgetViewsFactory implements RemoteViewsFactory {
    List<String> mCollections = new ArrayList<String>();

    Context mContext = null;

    public StockWidgetViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCollections.size();
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
        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        mView.setTextViewText(android.R.id.text1, mCollections.get(position));
        mView.setTextColor(android.R.id.text1, Color.BLACK);

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(StockWidgetProvider.ACTION_TOAST);
        final Bundle bundle = new Bundle();
        bundle.putString(StockWidgetProvider.EXTRA_STRING,
                mCollections.get(position));
        fillInIntent.putExtras(bundle);
        mView.setOnClickFillInIntent(android.R.id.text1, fillInIntent);
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
        mCollections.clear();

        Cursor c = mContext.getApplicationContext().getContentResolver().query(Contract.Quote.URI,
                null, null, null, null);

        if (c != null && c.moveToFirst()) {
            do {
                mCollections.add(
                        c.getString(c.getColumnIndex(Contract.Quote.COLUMN_SYMBOL)) +
                        "|" + c.getString(c.getColumnIndex(Contract.Quote.COLUMN_PRICE)));
            } while (c.moveToNext());
        }
    }

    @Override
    public void onDestroy() {

    }
}
