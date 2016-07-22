package com.menshikov.maksim.izhtransport.map;

import android.graphics.Point;
import android.location.Location;

/**
 * Created by Maksim on 22.07.2016.
 */
public class PointConverter
{
    private PointConverter()
    {}

    private static float LocationToMapKoeffX = (float) Math.abs((MapModel.rightBottomLocation.getLatitude() - MapModel.leftTopMapLocation.getLatitude()) /MapModel.mapWidth);
    private static float LocationToMapKoeffY = (float) Math.abs((MapModel.rightBottomLocation.getLongitude() - MapModel.leftTopMapLocation.getLongitude()) / MapModel.mapHeight);

    public static void convertToMapPoint(Location location, IMapPoint point)
    {
        float dx = ((float) (location.getLatitude() - MapModel.leftTopMapLocation.getLatitude()));
        float dy = -(float) (location.getLongitude() - MapModel.leftTopMapLocation.getLongitude());
        point.setXY(new Point(Math.round(dx / LocationToMapKoeffX), Math.round(dy / LocationToMapKoeffY)));
    }
}
