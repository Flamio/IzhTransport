package com.menshikov.maksim.izhtransport;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.test.ApplicationTestCase;

import com.menshikov.maksim.izhtransport.map.IMapMoveListener;
import com.menshikov.maksim.izhtransport.map.IMapSource;
import com.menshikov.maksim.izhtransport.map.IMapView;
import com.menshikov.maksim.izhtransport.map.MapModel;
import com.menshikov.maksim.izhtransport.map.MapPoint;
import com.menshikov.maksim.izhtransport.map.MapPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class GraphicMapTest extends ApplicationTestCase<Application>
{
    public GraphicMapTest()
    {
        super(Application.class);
    }

    private MapModel mapModel;
    private MapPresenter mapPresenter;
    private IMapSource mapSource;
    private IMapView mapView = new IMapView()
    {
        private Bitmap bitmap;
        private IMapMoveListener mapMoveListener;
        private ArrayList<MapPoint> points;
        private Point xYMap;
        private boolean isRedraw;

        @Override
        public void setBitmap(Bitmap bitmap)
        {
            this.bitmap = bitmap;
        }

        @Override
        public void setMapMoveListener(IMapMoveListener mapMoveListener)
        {
            this.mapMoveListener = mapMoveListener;
        }

        @Override
        public void setMapPoints(ArrayList<MapPoint> points)
        {
            this.points = points;
        }


        @Override
        public void setXYMap(int x, int y)
        {
            this.xYMap = new Point(x,y);
        }

        @Override
        public Point getXYMap()
        {
            return this.xYMap;
        }

        @Override
        public void redraw(boolean isDrawingTransport)
        {
            this.isRedraw = true;
        }
    };

    private void initialTestEntities()
    {
        this.mapSource = new IMapSource()
        {
            @Override
            public int getHeight()
            {
                return 1000;
            }

            @Override
            public int getWidth()
            {
                return 1000;
            }

            @Override
            public Bitmap getMap(Rect rect, int screenWidth, int screenHeight)
            {
                return Bitmap.createBitmap(screenWidth,screenHeight, Bitmap.Config.ARGB_8888);
            }
        };

        this.mapPresenter = new MapPresenter(this.mapView, this.mapSource, 100, 100);
    }

    private Object getField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException
    {
        Field f = obj.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(obj);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        this.initialTestEntities();
    }

    public void testInitialRasterPlace() throws NoSuchFieldException, IllegalAccessException, InterruptedException
    {
        MapModel mapModel = (MapModel) this.getField(this.mapPresenter, "model");
        assertEquals(mapModel.getCurrentLeft(), 450);
        assertEquals(mapModel.getCurrentTop(), 450);
        boolean isRedraw = (boolean)this.getField(this.mapView, "isRedraw");
        assertTrue(isRedraw);
    }

}