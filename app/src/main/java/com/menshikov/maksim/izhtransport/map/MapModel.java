package com.menshikov.maksim.izhtransport.map;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;

import com.menshikov.maksim.izhtransport.Sources.ITransportInfoSource;
import com.menshikov.maksim.izhtransport.Transport.TransportParser;

import java.util.ArrayList;

/**
 * Created by Maksim on 17.02.2016.
 */
public class MapModel
{
    private final int screenWidth;
    private final int screenHeight;
    private IMapSource mapSource;

    public static int mapWidth;
    public static int mapHeight;

    private static int currentLeft = 0;
    private static int currentTop = 0;
    private int currentWidth;
    private int currentHeight;

    public static Location leftTopMapLocation;
    public static Location rightBottomLocation;
    private ArrayList<MapPoint> points;

    public void setMapPoints(ArrayList<MapPoint> points)
    {
        this.points = points;
    }

    public ArrayList<MapPoint> getVisiblePoints()
    {
        if (this.points == null)
            return null;

        ArrayList<MapPoint> visiblePoints = new ArrayList<MapPoint>(this.points.size());

        for (MapPoint mapPoint : this.points)
        {
            MapPoint pointClone = mapPoint.Clone();
            if (!this.convertPointToScreenCoord(pointClone))
                continue;
            visiblePoints.add(pointClone);
        }
        return visiblePoints;
    }


    public boolean convertPointToScreenCoord(MapPoint point)
    {
        if (!this.isPointInCurrentMapRect(point))
            return false;

        float kx = (float) currentWidth / screenWidth;
        float ky = (float) currentHeight / screenHeight;

        point.getXY().x /= kx;
        point.getXY().y /= ky;
        point.getXY().x -= (float) getCurrentLeft() / kx;
        point.getXY().y -= (float) getCurrentTop() / ky;
        return true;
    }

    public int getCurrentWidth()
    {
        return currentWidth;
    }

    public int getCurrentHeight()
    {
        return currentHeight;
    }

    public void setCurrentLeft(int left)
    {
        currentLeft = left < 0 ? 0 : left + currentWidth > mapWidth ? mapWidth - currentWidth : left;
    }

    public void setCurrentTop(int top)
    {
        currentTop = top < 0 ? 0 : top + currentHeight > mapHeight ? mapHeight - currentHeight : top;
    }

    public void setCurrentWidth(int width)
    {
        currentWidth = width < 10 ? 10 : width > mapWidth ? mapWidth : width;
    }

    public void setCurrentHeight(int height)
    {
        currentHeight = height < 10 ? 10 : height > mapHeight ? mapHeight : height;
    }

    public int getCurrentTop()
    {
        return currentTop;
    }

    public int getCurrentLeft()
    {
        return currentLeft;
    }

    public MapModel(int _screenWidth, int _screenHeight, IMapSource mapSource)
    {
        screenWidth = _screenWidth;
        screenHeight = _screenHeight;
        this.mapSource = mapSource;
        currentHeight = screenHeight;
        currentWidth = screenWidth;

        mapHeight = mapSource.getHeight();
        mapWidth = mapSource.getWidth();

        leftTopMapLocation = new Location("izh");
        rightBottomLocation = new Location("izh");

        leftTopMapLocation.setLongitude(56.990728);
        leftTopMapLocation.setLatitude(52.915183);
        rightBottomLocation.setLongitude(56.710817);
        rightBottomLocation.setLatitude(53.557886);
    }

    public boolean isPointInCurrentMapRect(MapPoint point)
    {
        return point.getXY().x >= currentLeft ? point.getXY().y >= currentTop ? point.getXY().x <= currentLeft + currentWidth ? point.getXY().y <= currentTop + currentHeight ? true : false : false : false : false;
    }

    public Bitmap getMap()
    {
        return mapSource.getMap(new Rect(currentLeft, currentTop, currentLeft + currentWidth, currentTop + currentHeight), screenWidth, screenHeight);
    }
}
