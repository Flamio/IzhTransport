package com.menshikov.maksim.izhtransport;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Maksim on 17.02.2016.
 */
public class MapModel
{
    private final int screenWidth;
    private final int screenHeight;
    private IMapSource mapSource;

    private int mapWidth;
    private int mapHeight;

    private int currentLeft = 300;
    private int currentTop = 300;
    private int currentWidth;
    private int currentHeight;

    private float LocationToMapKoeffX;
    private float LocationToMapKoeffY;

    private Location leftTopMapLocation;
    private Location rightBottomLocation;

    private Bitmap generalMap;
    private BitmapRegionDecoder decoder;

    public int getMapHeight()
    {
        return mapHeight;
    }

    public int getMapWidth()
    {
        return mapWidth;
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
        currentLeft = left<0 ? 0:  left + currentWidth>mapWidth?mapWidth- currentWidth:left;
    }

    public void setCurrentTop(int top)
    {
        currentTop = top<0 ? 0:  top + currentHeight>mapHeight?mapHeight -  currentHeight:top;
    }
    public void setCurrentWidth(int width)
    {
        currentWidth = width<10 ?10 :  width>mapWidth?mapWidth:width;
    }
    public void setCurrentHeight(int height)
    {
        currentHeight = height<10 ?10 :  height>mapHeight?mapHeight:height;
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

        leftTopMapLocation.setLatitude(56.990728);
        leftTopMapLocation.setLongitude(52.915183);
        rightBottomLocation.setLatitude(56.710817);
        rightBottomLocation.setLongitude(53.557886);

        LocationToMapKoeffX = (float)Math.abs((rightBottomLocation.getLatitude() - leftTopMapLocation.getLatitude()) / mapWidth);
        LocationToMapKoeffY = (float)Math.abs((rightBottomLocation.getLongitude() - leftTopMapLocation.getLongitude()) / mapHeight);
    }

    public Point convertLocationToMap (Location object)
    {
        Point mapPoint = new Point();


        float dx = -(float)(object.getLatitude() - leftTopMapLocation.getLatitude());
        float dy = (float)(object.getLongitude() - leftTopMapLocation.getLongitude());

        mapPoint.set(Math.round(dx/LocationToMapKoeffX),Math.round(dy/LocationToMapKoeffY));

        return mapPoint;
    }

    public boolean isPointInCurrentMapRect(Point point)
    {
        return point.x >= currentLeft? point.y>=currentTop? point.x <=currentLeft+currentWidth? point.y<=currentTop+currentHeight? true :false :false :false :false;
    }

    public Bitmap getMap(boolean isBad)
    {
        if (isBad)
        {
            return mapSource.getBadMap(new Rect(currentLeft,currentTop,currentLeft+currentWidth,currentTop+currentHeight),screenWidth,screenHeight);
        }
        else
        {
            return mapSource.getMap(new Rect(currentLeft,currentTop,currentLeft+currentWidth,currentTop+currentHeight),screenWidth,screenHeight);
        }
    }
}
