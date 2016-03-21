package com.menshikov.maksim.izhtransport;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by Maksim on 17.02.2016.
 */
public interface IMapView
{
    void setBitmap(Bitmap bitmap);
    void setMapMoveListener(IMapMoveListener mapMoveListener);
    void setTransportPoints(ArrayList<Point> points);
    void clearTransportPoints();
}
