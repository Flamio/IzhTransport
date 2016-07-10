package com.menshikov.maksim.izhtransport.map;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by Maksim on 17.02.2016.
 */
public interface IMapSource
{
    int getHeight();
    int getWidth();

    Bitmap getMap(Rect rect, int screenWidth, int screenHeight);
    Bitmap getBadMap(Rect rect, int screenWidth, int screenHeight);
}
