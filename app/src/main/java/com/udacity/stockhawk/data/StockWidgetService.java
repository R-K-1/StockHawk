package com.udacity.stockhawk.data;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by rkalonji on 01/31/2017.
 */

public class StockWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        StockWidgetViewsFactory dataProvider = new StockWidgetViewsFactory(
                getApplicationContext(), intent);
        return dataProvider;
    }
}
