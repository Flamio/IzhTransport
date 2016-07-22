package com.menshikov.maksim.izhtransport.map;

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by Maksim on 22.07.2016.
 */
public interface IMapPoint
{
    void setXY(Point point);
    Point getXY();

    void draw(Canvas canvas);
}
