package pract.es.deusto.wake_up;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.RemoteViews;
import android.widget.Toast;
import java.util.Calendar;
import java.util.logging.Handler;

/**
 * Implementation of App Widget functionality.
 */
public class clockWidget extends AppWidgetProvider {

    public static Bitmap BuildUpdate(String txTiempo,int tamañoT,Context context){
        Paint paint=new Paint();
        paint.setTextSize(tamañoT);
        Typeface miRelog=Typeface.createFromAsset(context.getAssets(),"fuentes/Lato-BlackItalic.ttf");
        paint.setTypeface(miRelog);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setSubpixelText(true);
        paint.setAntiAlias(true);
        float posicion=-paint.ascent();
        int altura=(int)(paint.measureText(txTiempo)+0.5f);
        int anchura=(int) (posicion+paint.descent()+0.5f);

        Bitmap image=Bitmap.createBitmap(altura,anchura,Bitmap.Config.ARGB_4444);
        Canvas canvas =new Canvas(image);
        canvas.drawText(txTiempo,0,posicion,paint);
        return image;
    }
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String fecha= ""+Calendar.getInstance().getTime();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_widget);
        views.setImageViewBitmap(R.id.imgtiempo,BuildUpdate(fecha,100,context));
        appWidgetManager.updateAppWidget(appWidgetId, views);
        Intent intentUpdate = new Intent(context, clockWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);


        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);


        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.update, pendingUpdate);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them


       for (int appWidgetId : appWidgetIds) {
               updateAppWidget(context, appWidgetManager, appWidgetId);
           }

    }


    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

