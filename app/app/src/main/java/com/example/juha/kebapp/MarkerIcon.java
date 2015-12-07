package com.example.juha.kebapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import java.util.Objects;

/**
 * Created by Juha on 08/11/15.
 */
class MarkerIcon {

    private Bitmap.Config conf = Bitmap.Config.ARGB_8888;
    private Bitmap bmp = Bitmap.createBitmap(200,200, conf);
    private Canvas canvas = new Canvas(bmp);

    public MarkerIcon(String name, int stars){
        Paint color = new Paint();
        color.setTextSize(35);
        color.setColor(Color.BLACK);

        Paint color2 = new Paint();
        color2.setColor(Color.WHITE);

        canvas.drawRect(0,0,200,200,color2);
        canvas.drawText(name, 0,35,color);
        canvas.drawText(Integer.toString(stars),0,20,color);
    }

}
