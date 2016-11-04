package com.menshikov.maksim.izhtransport.map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.menshikov.maksim.izhtransport.ResourceManager;

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

        final float arrowSizeInDip = 7;
        final float arrowSizeInDip2 = 21;
        final float bitmapSizeInDip = 128;

        int arrowSizeInPixels = (int)this.dipToPixels(ResourceManager.Instance().getResources(), arrowSizeInDip);
        int arrowSizeInPixels2 = (int)this.dipToPixels(ResourceManager.Instance().getResources(), arrowSizeInDip2);
        int bitmapSizeInPixels = (int)this.dipToPixels(ResourceManager.Instance().getResources(), bitmapSizeInDip);

        Bitmap rotatedDirectionIcon = this.rotateBitmap(this.directionIcon, this.direction);
        Rect drawingIconRect = null;
        if (this.direction >= 0 && this.direction <= 45 || this.direction > 315)
            drawingIconRect = new Rect(this.point.x - arrowSizeInPixels, this.point.y - arrowSizeInPixels2, this.point.x + arrowSizeInPixels, this.point.y-arrowSizeInPixels);
        if (this.direction > 45 && this.direction <= 135)
            drawingIconRect = new Rect(this.point.x + arrowSizeInPixels, this.point.y - arrowSizeInPixels, this.point.x + arrowSizeInPixels2, this.point.y+arrowSizeInPixels);
        if (this.direction > 135 && this.direction <= 225)
            drawingIconRect = new Rect(this.point.x - arrowSizeInPixels, this.point.y + arrowSizeInPixels, this.point.x + arrowSizeInPixels, this.point.y+arrowSizeInPixels2);
        if (this.direction > 225)
            drawingIconRect = new Rect(this.point.x - arrowSizeInPixels2, this.point.y - arrowSizeInPixels, this.point.x - arrowSizeInPixels, this.point.y + arrowSizeInPixels);

        canvas.drawBitmap(rotatedDirectionIcon, new Rect(0, 0, bitmapSizeInPixels, bitmapSizeInPixels), drawingIconRect, null);
    }


    @Override
    abstract public ICloneable clone();
}