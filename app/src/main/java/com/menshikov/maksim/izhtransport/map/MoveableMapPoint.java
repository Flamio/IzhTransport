package com.menshikov.maksim.izhtransport.map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

/**
 * Created by Maksim on 31.07.2016.
 */
abstract public class MoveableMapPoint extends MapPoint
{
    protected  Bitmap directionIcon;

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);

        return rotatedBitmap;
    }

    private float direction;

    protected float getDegreeDirection()
    {
        return this.direction;
    }

    public void setDegreeDirection(float direction)
    {
        this.direction = direction;
    }

    public MoveableMapPoint(Bitmap bitmap, Bitmap directionIcon)
    {
        super(bitmap);
        this.directionIcon = directionIcon;
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        Bitmap rotatedDirectionIcon = this.rotateBitmap(this.directionIcon, this.direction);
        Rect drawingIconRect = null;
        if (this.direction >= 0 && this.direction <= 45 || this.direction > 315)
            drawingIconRect = new Rect(this.point.x - 10, this.point.y - 30, this.point.x + 10, this.point.y-10);
        if (this.direction > 45 && this.direction <= 135)
            drawingIconRect = new Rect(this.point.x + 10, this.point.y - 10, this.point.x + 30, this.point.y+10);
        if (this.direction > 135 && this.direction <= 225)
            drawingIconRect = new Rect(this.point.x - 10, this.point.y + 10, this.point.x + 10, this.point.y+30);
        if (this.direction > 225)
            drawingIconRect = new Rect(this.point.x - 30, this.point.y - 10, this.point.x - 10, this.point.y + 10);

        canvas.drawBitmap(rotatedDirectionIcon, new Rect(0, 0, 128, 128), drawingIconRect, null);
    }


    @Override
    abstract public ICloneable clone();
}