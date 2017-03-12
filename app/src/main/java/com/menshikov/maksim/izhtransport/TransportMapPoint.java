package com.menshikov.maksim.izhtransport;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;

import com.menshikov.maksim.izhtransport.map.MapPoint;
import com.menshikov.maksim.izhtransport.map.NumerableMapPoint;

/**
 * Created by Maksim on 31.07.2016.
 */
public class TransportMapPoint extends MapPoint implements NumerableMapPoint {
    private final int sizeFromCenterInPixels;
    private final int sizeFromCenterInPixels2;
    private final int bitmapSizeInPixels;

    private Paint textPaint;

    protected Bitmap directionIcon;
    private int number;

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);

        return rotatedBitmap;
    }

    private float direction;

    protected float getDegreeDirection() {
        return this.direction;
    }

    public void setDegreeDirection(float direction) {
        this.direction = direction;
    }

    public TransportMapPoint(Bitmap bitmap, Bitmap directionIcon) {
        super(bitmap);
        this.directionIcon = directionIcon;

        sizeFromCenterInPixels = (int) this.dipToPixels(ResourceManager.Instance().getResources(), this.sizeTransportIconFromCenterInDp);
        sizeFromCenterInPixels2 = (int) this.dipToPixels(ResourceManager.Instance().getResources(), this.sizeTransportIconFromCenterInDp * 3);
        bitmapSizeInPixels = (int) this.dipToPixels(ResourceManager.Instance().getResources(), this.directionIcon.getHeight());
        this.CreateTextPaint();
    }

    private void CreateTextPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(this.dipToPixels(ResourceManager.Instance().getResources(), 12f));
        textPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (this.direction != -1)
            this.DrawDirection(canvas);

        this.DrawNumber(canvas);
    }

    private void DrawDirection(Canvas canvas) {
        Bitmap rotatedDirectionIcon = this.rotateBitmap(this.directionIcon, this.direction);
        Rect drawingIconRect = null;
        /*if (this.direction >= 0 && this.direction <= 45 || this.direction > 315)
            drawingIconRect = new Rect(this.point.x - sizeFromCenterInPixels, this.point.y - sizeFromCenterInPixels2, this.point.x + sizeFromCenterInPixels, this.point.y - sizeFromCenterInPixels);*/
        if (this.direction >= 0 && this.direction <= 135)
            drawingIconRect = new Rect(this.point.x + sizeFromCenterInPixels, this.point.y - sizeFromCenterInPixels, this.point.x + sizeFromCenterInPixels2, this.point.y + sizeFromCenterInPixels);
        if (this.direction > 135 && this.direction <= 225)
            drawingIconRect = new Rect(this.point.x - sizeFromCenterInPixels, this.point.y + sizeFromCenterInPixels, this.point.x + sizeFromCenterInPixels, this.point.y + sizeFromCenterInPixels2);
        if (this.direction > 225)
            drawingIconRect = new Rect(this.point.x - sizeFromCenterInPixels2, this.point.y - sizeFromCenterInPixels, this.point.x - sizeFromCenterInPixels, this.point.y + sizeFromCenterInPixels);
        canvas.drawBitmap(rotatedDirectionIcon, new Rect(0, 0, bitmapSizeInPixels, bitmapSizeInPixels), drawingIconRect, null);
    }

    private void DrawNumber(Canvas canvas) {
        canvas.drawText(Integer.toString(this.number), this.point.x - sizeFromCenterInPixels,
                this.point.y - sizeFromCenterInPixels - this.dipToPixels(ResourceManager.Instance().getResources(), 5f), textPaint);
    }

    @Override
    public TransportMapPoint Clone() {
        TransportMapPoint clonePoint = new TransportMapPoint(this.bitmap, this.directionIcon);
        clonePoint.setXY(new Point(this.getXY()));
        Location cloneLocation = new Location("izh");
        cloneLocation.setLatitude(this.getGeoLocation().getLatitude());
        cloneLocation.setLongitude(this.getGeoLocation().getLongitude());
        clonePoint.setGeoLocation(cloneLocation);
        clonePoint.setDegreeDirection(this.getDegreeDirection());
        clonePoint.SetNumber(this.GetNumber());
        return clonePoint;
    }

    @Override
    public void SetNumber(int number) {
        this.number = number;
    }

    @Override
    public int GetNumber() {
        return this.number;
    }
}