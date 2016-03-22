package com.menshikov.maksim.izhtransport;

import android.graphics.Point;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maksim on 17.02.2016.
 */
public class MapPresenter
{

    private MapModel model = null;
    private IMapView view = null;

    public MapPresenter(MapModel _model, IMapView _view) throws IOException {
        model = _model;
        view =  _view;
        view.setMapMoveListener(new IMapMoveListener() {
            @Override
            public void onMoving(int dx, int dy)
            {
                model.setCurrentLeft(model.getCurrentLeft()+dx);
                model.setCurrentTop(model.getCurrentTop()+dy);
                view.clearTransportPoints();
                view.setBitmap(model.getMap(true));
            }

            @Override
            public void onStopMoving() throws InterruptedException {
                view.setBitmap(model.getMap(false));
                view.setTransportPoints(model.getTransportPoints());
            }

            @Override
            public void onScaling(boolean increase, int centerX,int centerY)
            {

                int newLeftOffset =model.getCurrentWidth()/100;
                int newTopOffset = model.getCurrentHeight()/100;

                int sign = 0;
                if (increase)
                {
                    sign = 1;
                }
                else
                {
                    sign = -1;
                }
                    int newWidth = model.getCurrentWidth() - sign* newLeftOffset;
                    int newHeight = model.getCurrentHeight() - sign* newTopOffset;//((newWidth*model.getCurrentHeight())/model.getCurrentWidth());
                    model.setCurrentHeight(newHeight);
                    model.setCurrentWidth(newWidth);
                    model.setCurrentLeft(model.getCurrentLeft() + sign*newLeftOffset );
                    model.setCurrentTop(model.getCurrentTop()+ sign* newTopOffset);
                    view.setBitmap(model.getMap(true));
                    view.clearTransportPoints();
            }
        });

        view.setBitmap(model.getMap(false));
      //  view.setTransportPoints(model.getTransportPoints());

    }

}
