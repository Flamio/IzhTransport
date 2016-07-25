package com.menshikov.maksim.izhtransport.map;

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
    void setMapPoints(ArrayList<IMapPoint> points);
    void clearTransportPoints();
    void setXYMap(int x, int y);
    Point getXYMap();
    void redraw(boolean isDrawingTransport);
}
