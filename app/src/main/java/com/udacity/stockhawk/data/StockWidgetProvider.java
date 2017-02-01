package com.udacity.stockhawk.data;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.ui.StockHistoryActivity;

/**
 * Created by rkalonji on 01/30/2017.
 */

public class StockWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_VIEW_STOCK_HISTORY = "VIEW_STOCK_HISTORY";
    public static final String EXTRA_SELECTED_STOCK = "SELECTED_STOCK";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_VIEW_STOCK_HISTORY)) {
            Intent i = new Intent(context, StockHistoryActivity.class);
            Stock stock = (Stock) intent.getParcelableExtra(EXTRA_SELECTED_STOCK);
            i.putExtra("SelectedStock",(Parcelable) stock);
            context.startActivity(i);
        }

        super.onReceive(context, intent);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, appWidgetManager, widgetId);

            // Adding collection list item handler
            final Intent onItemClick = new Intent(context, StockWidgetProvider.class);
            // onItemClick.setAction(ACTION_TOAST);
            onItemClick.setAction(ACTION_VIEW_STOCK_HISTORY);
            onItemClick.setData(Uri.parse(onItemClick.toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent
                    .getBroadcast(context, 0, onItemClick, PendingIntent.FLAG_UPDATE_CURRENT);
            mView.setPendingIntentTemplate(R.id.widgetCollectionList, onClickPendingIntent);

            appWidgetManager.updateAppWidget(widgetId, mView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews initViews(Context context, AppWidgetManager widgetManager, int widgetId) {

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.stock_quotes_widget_provider_layout);

        Intent intent = new Intent(context, StockWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mView.setRemoteAdapter(widgetId, R.id.widgetCollectionList, intent);

        return mView;
    }
}
