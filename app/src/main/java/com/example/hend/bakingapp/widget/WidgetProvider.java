package com.example.hend.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.hend.bakingapp.R;
import com.example.hend.bakingapp.Utils.ConstantsUtil;
import com.example.hend.bakingapp.ui.activities.cooking_Details_Activity;


public class WidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, String jsonRecipeIngredients, int imgResId, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_provider);

        Intent intent = new Intent(context, cooking_Details_Activity.class);
        intent.putExtra(ConstantsUtil.WIDGET_EXTRA, "CAME_FROM_WIDGET");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        if (jsonRecipeIngredients.equals("")) {
            jsonRecipeIngredients = "No ingredients yet!";
        }

        views.setTextViewText(R.id.widget_ingredients, jsonRecipeIngredients);

        // OnClick intent for textview
        views.setOnClickPendingIntent(R.id.widget_ingredients, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // Gets called once created and on every update period
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetService.startActionOpenRecipe(context);
    }

    public static void updateWidgetRecipe(Context context, String jsonRecipe, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

