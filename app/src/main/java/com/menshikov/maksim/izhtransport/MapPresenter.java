package com.menshikov.maksim.izhtransport;

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
                model.setCurrentLeft(model.getCurrentLeft()+dx<0?0:model.getCurrentLeft()+dx);
                model.setCurrentTop(model.getCurrentTop()+dy<0?0:model.getCurrentTop()+dy);

                if (model.getCurrentLeft()+model.getCurrentWidth()>model.getMapWidth())
                {
                    model.setCurrentLeft(model.getMapWidth()-model.getCurrentWidth());
                }

                if (model.getCurrentTop()+model.getCurrentHeight()>model.getMapHeight())
                {
                    model.setCurrentTop(model.getMapHeight()-model.getCurrentHeight());
                }


                view.setBitmap(model.getMap(true));
            }

            @Override
            public void onStopMoving()
            {
                view.setBitmap(model.getMap(false));
            }

            @Override
            public void onScaling(boolean increase)
            {

            }
        });
        view.setBitmap(model.getMap(false));
    }

}
