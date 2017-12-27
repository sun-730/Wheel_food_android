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
import wheelofeats.roxdesign.com.wheelofeats.model.CustomWheelModel;
import wheelofeats.roxdesign.com.wheelofeats.model.SpinModel;

/**
 * Created by panda on 09/12/2017.
 */

public class CustomRotaryWheel extends View {
    public int sectionCount;
    private int radius;
    public int tumbnail;
    public ArrayList<Paint> mpaint, mbackgroundpaint;
    public CustomWheelModel customWheelModel;
    Paint strokpaint, bgpaint;
    RectF rect, rectbackground;
    float percentage;
    public float myAngle;
    ArrayList<String> paintList;


    public CustomRotaryWheel(Context context) {
        super(context);
//        init();
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
        sectionCount = customWheelModel.getCustomContents().size();
        double tmp = (double)360/sectionCount;
        percentage = (float)tmp;
        if(tumbnail == 1){
            radius = (int)(metrics.widthPixels * 0.3);
        }else{
            radius = (int)(metrics.widthPixels * 0.8);
        }
//        radius = (int) getResources().getDimension(R.dimen.wheel_radius);
        myAngle = 0;
        setPaints();
    }
    private void setPaints(){
        paintList = new ArrayList<>();
        paintList.add("235");
        paintList.add("118");
        paintList.add("49");
        paintList.add("71");
        paintList.add("183");
        paintList.add("73");
        paintList.add("151");
        paintList.add("209");
        paintList.add("187");
        paintList.add("20");
        paintList.add("121");
        paintList.add("189");
        paintList.add("43");
        paintList.add("50");
        paintList.add("124");
        paintList.add("62");
        paintList.add("23");
        paintList.add("36");
        paintList.add("233");
        paintList.add("225");
        paintList.add("191");
        paintList.add("247");
        paintList.add("224");
        paintList.add("40");
        mpaint = new ArrayList<Paint>();
        mbackgroundpaint = new ArrayList<Paint>();
        for(int i = 0; i < sectionCount; i++){
            Paint paint = new Paint();
            int r1 = Integer.parseInt(paintList.get(i*3));
            int r2 = Integer.parseInt(paintList.get(i*3+1));
            int r3 = Integer.parseInt(paintList.get(i*3+2));
            int r11 = Math.min(r1 + 10, 255);
            int r21 = Math.min(r2 + 10, 255);
            int r31 = Math.min(r3 + 10, 255);
            int r12 = Math.max(r1 - 10, 0);
            int r22 = Math.max(r2 - 10, 0);
            int r32 = Math.max(r3 - 10, 0);
            String random1 = Integer.toString(r11, 16);
            String random2 = Integer.toString(r21, 16);
            String random3 = Integer.toString(r31, 16);
            if(random1.length() == 1) random1 = "0" + random1;
            if(random2.length() == 1) random2 = "0" + random2;
            if(random3.length() == 1) random3 = "0" + random3;
            String aaa = random1 +  random2 + random3;
            int color = Integer.parseInt(aaa, 16)+0xFF000000;
            paint.setColor(color);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            mpaint.add(paint);
            paint = new Paint();
            String random11 = Integer.toString(r12, 16);
            String random21 = Integer.toString(r22, 16);
            String random31 = Integer.toString(r32, 16);
            if(random11.length() == 1) random11 = "0" + random11;
            if(random21.length() == 1) random21 = "0" + random21;
            if(random31.length() == 1) random31 = "0" + random31;
            String aaa1 = random11+  random21 + random31;
//            String aaa1 = “BBBBBB”;
            int color2 = Integer.parseInt(aaa1, 16)+0xFF000000;
            paint.setColor(color2);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            mbackgroundpaint.add(paint);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
            canvas.drawArc(rect, -90 + percentage * i, percentage, true, mpaint.get(i));

        }
        for(int i = 0; i < sectionCount; i++){
            int x = (int)(rect.centerX() + (radius/2 * Math.cos(-Math.PI / 2+ i * percentage * ((float)Math.PI / 180f))));
            int y = (int)(rect.centerY() + (radius/2 * Math.sin(-Math.PI / 2 + i * percentage * ((float)Math.PI / 180f))));

            bgpaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
            String rotatedtext = customWheelModel.getCustomContents().get(i);
            if(tumbnail == 1){
                bgpaint.setTextSize(28);
                strokpaint.setTextSize(28);
            }else {
                bgpaint.setTextSize(38);
                strokpaint.setTextSize(38);
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
            if(rotatedtext.length() > 7) rotatedtext = rotatedtext.substring(0, 6);
            if(sectionCount > 5){
                canvas.drawText(rotatedtext, x+30, y-radius/(sectionCount-1)-10, strokpaint);
                canvas.drawText(rotatedtext, x+30, y-radius/(sectionCount-1)-10, bgpaint);
            }else{
                canvas.drawText(rotatedtext, x-10, y-radius/(sectionCount)-30, strokpaint);
                canvas.drawText(rotatedtext, x-10, y-radius/(sectionCount)-30, bgpaint);
            }
            canvas.restore();
        }
    }
}
