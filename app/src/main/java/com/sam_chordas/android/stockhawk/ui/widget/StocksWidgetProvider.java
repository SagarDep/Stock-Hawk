package com.sam_chordas.android.stockhawk.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

/**
 * Created by galaxywizkid on 11/4/16.
 */

public class StocksWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stock_widget_layout);
            //views.setTextViewText(R.id.app_widget_text, );
            Intent intentService = new Intent(context, StockWidgetRemoteViewService.class);

            views.setRemoteAdapter(R.id.widget_list_view, intentService);

            // Intent to launch MainActivity
            Intent appIntent = new Intent(context, MyStocksActivity.class);
            PendingIntent appPendingIntent = PendingIntent.getActivity(
                    context, 0, appIntent, 0);
            views.setOnClickPendingIntent(R.id.app_widget_text,appPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
