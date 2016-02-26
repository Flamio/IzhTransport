package com.menshikov.maksim.izhtransport;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

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

    private int currentLeft = 0;
    private int currentTop = 0;
    private int currentWidth;
    private int currentHeight;

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

//    public float getAspectRatioScreen()
//    {
//        return (float)screenHeight/(float)screenWidth;
//    }
//
//    public float getCurrentKoeffY()
//    {
//        return (float)currentHeight/(float)screenHeight;
//    }
//
//    public float getCurrentKoeffX()
//    {
//        return (float)currentWidth/(float)screenWidth;
//    }

    public MapModel(int _screenWidth, int _screenHeight, IMapSource mapSource)
    {
        screenWidth = _screenWidth;
        screenHeight = _screenHeight;
        this.mapSource = mapSource;
        currentHeight = screenHeight;
        currentWidth = screenWidth;

        mapHeight = mapSource.getHeight();
        mapWidth = mapSource.getWidth();
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
