package com.menshikov.maksim.izhtransport;

/**
 * Created by Maksim on 17.02.2016.
 */
public interface IMapMoveListener
{
    void onMoving(int dx,int dy);
    void onStopMoving();
    void onScaling(boolean increase, int centerX,int centerY);
}
