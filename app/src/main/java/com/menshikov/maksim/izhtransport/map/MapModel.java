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
public class MapModel {
    private final int screenWidth;
    private final int screenHeight;
    private IMapSource mapSource;
    private ITransportInfoSource transportInfoSource;

    private int mapWidth;
    private int mapHeight;

    private int currentLeft = 3000;
    private int currentTop = 3000;
    private int currentWidth;
    private int currentHeight;

    private float LocationToMapKoeffX;
    private float LocationToMapKoeffY;

    private Location leftTopMapLocation;
    private Location rightBottomLocation;

    private TransportParser transportParser;

    public int getCurrentWidth() {
        return currentWidth;
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentLeft(int left) {
        currentLeft = left < 0 ? 0 : left + currentWidth > mapWidth ? mapWidth - currentWidth : left;
    }

    public void setCurrentTop(int top) {
        currentTop = top < 0 ? 0 : top + currentHeight > mapHeight ? mapHeight - currentHeight : top;
    }

    public void setCurrentWidth(int width) {
        currentWidth = width < 10 ? 10 : width > mapWidth ? mapWidth : width;
    }

    public void setCurrentHeight(int height) {
        currentHeight = height < 10 ? 10 : height > mapHeight ? mapHeight : height;
    }

    public int getCurrentTop() {
        return currentTop;
    }

    public int getCurrentLeft() {
        return currentLeft;
    }

    public MapModel(int _screenWidth, int _screenHeight, IMapSource mapSource, ITransportInfoSource transportInfoSource) {
        screenWidth = _screenWidth;
        screenHeight = _screenHeight;
        this.mapSource = mapSource;
        this.transportInfoSource = transportInfoSource;
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

        LocationToMapKoeffX = (float) Math.abs((rightBottomLocation.getLatitude() - leftTopMapLocation.getLatitude()) / mapWidth);
        LocationToMapKoeffY = (float) Math.abs((rightBottomLocation.getLongitude() - leftTopMapLocation.getLongitude()) / mapHeight);

        transportParser = new TransportParser(transportInfoSource);
    }

    public Point convertLocationToMap(Location object) {
        Point mapPoint = new Point();

        float dx = ((float) (object.getLatitude() - leftTopMapLocation.getLatitude()));
        float dy = -(float) (object.getLongitude() - leftTopMapLocation.getLongitude());

        mapPoint.set(Math.round(dx / LocationToMapKoeffX), Math.round(dy / LocationToMapKoeffY));

        return mapPoint;
    }

    public boolean isPointInCurrentMapRect(Point point) {
        return point.x >= currentLeft ? point.y >= currentTop ? point.x <= currentLeft + currentWidth ? point.y <= currentTop + currentHeight ? true : false : false : false : false;
    }

    public Bitmap getMap(boolean isBad) {
        if (isBad) {
            return mapSource.getBadMap(new Rect(currentLeft, currentTop, currentLeft + currentWidth, currentTop + currentHeight), screenWidth, screenHeight);
        } else {
            return mapSource.getMap(new Rect(currentLeft, currentTop, currentLeft + currentWidth, currentTop + currentHeight), screenWidth, screenHeight);
        }
    }

    public ArrayList<Point> getTransportPoints() throws InterruptedException {
        ArrayList<Location> locations = transportParser.getTransportPositions(0, 0);
        if (locations == null) {
            return null;
        }

        ArrayList<Point> points = new ArrayList<Point>();

        for (int i = 0; i < locations.size(); i++) {
            Point currentPoint = convertLocationToMap(locations.get(i));
            if (isPointInCurrentMapRect(currentPoint)) {
                float kx = (float) currentWidth / screenWidth;
                float ky = (float) currentHeight / screenHeight;

                currentPoint.x /= kx;
                currentPoint.y /= ky;
                currentPoint.x -= (float) getCurrentLeft() / kx;
                currentPoint.y -= (float) getCurrentTop() / ky;

                points.add(currentPoint);
            }
        }
        return points;
    }
}
