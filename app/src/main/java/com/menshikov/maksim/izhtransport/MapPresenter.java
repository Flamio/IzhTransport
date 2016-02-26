package com.menshikov.maksim.izhtransport;

import android.util.Log;

/**
 * Created by Maksim on 17.02.2016.
 */
public class MapPresenter
{

    private MapModel model = null;
    private IMapView view = null;

    public MapPresenter(MapModel _model, IMapView _view)
    {
        model = _model;
        view =  _view;
        view.setMapMoveListener(new IMapMoveListener() {
            @Override
            public void onMoving(int dx, int dy)
            {
                model.setCurrentLeft(model.getCurrentLeft()+dx);//+dx<0?0:model.getCurrentLeft()+dx);
                model.setCurrentTop(model.getCurrentTop()+dy);//+dy<0?0:model.getCurrentTop()+dy);

               // if (model.getCurrentLeft()+model.getCurrentWidth()>model.getMapWidth())
               // {
                    //model.setCurrentLeft(model.getMapWidth()-model.getCurrentWidth());
              //  }

                //if (model.getCurrentTop()+model.getCurrentHeight()>model.getMapHeight())
             //   {
                   // model.setCurrentTop(model.getMapHeight()-model.getCurrentHeight());
               // }


                view.setBitmap(model.getMap(true));
            }

            @Override
            public void onStopMoving()
            {
                view.setBitmap(model.getMap(false));
            }

            @Override
            public void onScaling(boolean increase, int centerX,int centerY)
            {

                int newLeftOffset =model.getCurrentWidth()/100;
                int newTopOffset = newLeftOffset;

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
                    int newHeight = ((newWidth*model.getCurrentHeight())/model.getCurrentWidth());
                    model.setCurrentHeight(newHeight);
                    model.setCurrentWidth(newWidth);
                    model.setCurrentLeft(model.getCurrentLeft() + sign*newLeftOffset );
                    model.setCurrentTop(model.getCurrentTop()+ sign* newTopOffset);

                    view.setBitmap(model.getMap(true));
            }
        });
        view.setBitmap(model.getMap(false));
    }

}
