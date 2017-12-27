package wheelofeats.roxdesign.com.wheelofeats.wheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.model.SpinModel;

/**
 * Created by admin on 27/08/2017.
 */

public class RotaryWheel extends View{
    public int sectionCount;
    private int radius;
    public ArrayList<Paint> mpaint, mbackgroundpaint;
    public ArrayList<SpinModel> spinmodels;
    Paint strokpaint, bgpaint;
    RectF rect, rectbackground;
    float percentage;
    public float myAngle;

    public RotaryWheel(Context context) {
        super(context);
        init();
    }

    public void init(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        bgpaint = new Paint();
        strokpaint = new Paint();
        bgpaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
        bgpaint.setAntiAlias(true);
        bgpaint.setStyle(Paint.Style.FILL);
        rect = new RectF();
        rectbackground = new RectF();
        double tmp = (double)360/sectionCount;
        percentage = (float)tmp;
        radius = (int)(metrics.widthPixels * 0.8);
//        radius = (int) getResources().getDimension(R.dimen.wheel_radius);
        myAngle = 0;

    }

    public void setPaintData(ArrayList<Paint> paints,ArrayList<Paint> mbackground){
        mpaint = paints;
        mbackgroundpaint = mbackground;
        invalidate();
    }

    public void changeRotate(float angle){
        myAngle += angle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw background circle anyway
        int left = 0;
        int width = radius;
        int top = 0;
        double tmp = (double)360/sectionCount;
        percentage = (float)tmp;
        rect.set(left+30, top+30, left+width-30, top + width-30);
        rectbackground.set(left, top, left+width, top + width);
//        canvas.drawArc(rect, -90, 360, true, bgpaint);
        for(int i = 0; i < sectionCount; i++){
            canvas.drawArc(rectbackground, -90 + percentage * i, percentage, true, mbackgroundpaint.get(i));
        }
        for(int i = 0; i < sectionCount; i++){
//            canvas.drawArc(rectbackground, -90 + percentage * i, percentage, true, mbackgroundpaint.get(i));
            canvas.drawArc(rect, -90 + percentage * i, percentage, true, mpaint.get(i));
//            canvas.save();
//            canvas.rotate(-30);
//            canvas.drawText("abc" + i, (float)(rect.centerX() + (radius/3 * Math.cos(-Math.PI / 4+ i * percentage * ((float)Math.PI / 180f)))),
//                    (float)(rect.centerY() + (radius/3 * Math.sin(-Math.PI / 4 + i * percentage * ((float)Math.PI / 180f)))), bgpaint);
//            canvas.restore();

        }
        for(int i = 0; i < sectionCount; i++){
            int x = (int)(rect.centerX() + (radius/2 * Math.cos(-Math.PI / 2+ i * percentage * ((float)Math.PI / 180f))));
            int y = (int)(rect.centerY() + (radius/2 * Math.sin(-Math.PI / 2 + i * percentage * ((float)Math.PI / 180f))));

            bgpaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
            String rotatedtext = spinmodels.get(i).getTitle();
            if(rotatedtext.length() > 14){
                bgpaint.setTextSize(20);
                strokpaint.setTextSize(20);

            }else{
                bgpaint.setTextSize(28);
                strokpaint.setTextSize(28);

            }
            Rect rect = new Rect();
            bgpaint.getTextBounds(rotatedtext, 0, rotatedtext.length(), rect);
            strokpaint.getTextBounds(rotatedtext, 0, rotatedtext.length(), rect);

            bgpaint.setTextAlign(Paint.Align.LEFT);
            strokpaint.setTextAlign(Paint.Align.LEFT);

            bgpaint.setColor(Color.WHITE);
            canvas.save();
            canvas.rotate(90 + percentage * i + percentage/2 , x,y);
            bgpaint.setStyle(Paint.Style.FILL);
            strokpaint.setARGB(255, 150, 150, 150);
            strokpaint.setTypeface(Typeface.DEFAULT_BOLD);
            strokpaint.setStyle(Paint.Style.STROKE);
            strokpaint.setStrokeWidth(4);
            if(sectionCount > 8){
                canvas.drawText(rotatedtext, x+30, y-radius/(sectionCount-1)-10, strokpaint);
                canvas.drawText(rotatedtext, x+30, y-radius/(sectionCount-1)-10, bgpaint);
            }else{
                canvas.drawText(rotatedtext, x-10, y-radius/(sectionCount-1)-30, strokpaint);
                canvas.drawText(rotatedtext, x-10, y-radius/(sectionCount-1)-30, bgpaint);
            }
            canvas.restore();
        }
    }

}
