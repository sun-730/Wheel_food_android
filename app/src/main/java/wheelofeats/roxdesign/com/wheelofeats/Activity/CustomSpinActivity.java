package wheelofeats.roxdesign.com.wheelofeats.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmResults;
import wheelofeats.roxdesign.com.wheelofeats.Fragment.MyWheelFragment;
import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.Utils.Global;
import wheelofeats.roxdesign.com.wheelofeats.model.CustomSpinModel;
import wheelofeats.roxdesign.com.wheelofeats.model.CustomWheelModel;
import wheelofeats.roxdesign.com.wheelofeats.model.MyWheelObject;
import wheelofeats.roxdesign.com.wheelofeats.wheel.CustomRotaryWheel;

/**
 * Created by panda on 17/12/2017.
 */

public class CustomSpinActivity extends Activity {
    private ProgressDialog progress;
    ArrayList<String> ModelArrayList;
    CustomWheelModel model;
    Button bttBack, bttSpin, bttShare;
    TextView txtSpinTitle, txtSpinComment, lblSpinHead;
    FrameLayout frameLayout;
    CustomRotaryWheel rotaryWheel;
    ImageView wheelBackground;
    RelativeLayout wheelLayer;
    int WheelRadius;
    private String selectedSpinName, selectedURL, Title;
    long firstTime, lastTime;

    float deltaAngle = 0.001f;
    float initAngle, lastAngle, initW;
    int pos4;
    int indexWheel, WheelCount;
    Timer timer;
    CustomSpinActivity.MyCustomTimerTask myTimerTask;
    private static int REQUEST_CAMERA = 0;
    private static int SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_spin);
        indexWheel = getIntent().getExtras().getInt("index");
        setContents();
        addCustomSpin();
        setEvents();
        setEvents1();
    }
    private void setContents() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        selectedSpinName = "";
        lblSpinHead = (TextView) findViewById(R.id.lblSpinHeadCustom);
        lblSpinHead.setText(Title);
        bttBack = (Button) findViewById(R.id.btt_spin_backCustom);
        bttShare = (Button) findViewById(R.id.bttshareCustom);
        bttSpin = (Button) findViewById(R.id.bttspinCustom);
        frameLayout = (FrameLayout) findViewById(R.id.wheel_layerCustom);
        txtSpinTitle = (TextView) findViewById(R.id.txtspintitleCustom);
        txtSpinComment = (TextView) findViewById(R.id.txtspincommentCustom);
        WheelRadius = (int)(metrics.widthPixels * 0.8);
//        WheelRadius = (int) getResources().getDimension(R.dimen.wheel_radius);
        wheelBackground = (ImageView) findViewById(R.id.imgbackgroundCustom);
        wheelLayer = (RelativeLayout) findViewById(R.id.wheellayerCustom);
    }
    private void setEvents() {
        bttBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bttSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(CustomSpinActivity.this, WebActivity.class);
                    intent.putExtra("url", selectedURL);
                    CustomSpinActivity.this.startActivity(intent);
            }
        });
    }

    private void addCustomSpin() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(MyWheelObject.class).findAll();
        MyWheelObject myWheelObject = (MyWheelObject) results.get(indexWheel);
        model = new CustomWheelModel();
        model.setCustomTitle(myWheelObject.getTitle());
        model.setCustomID(myWheelObject.getId());
        model.setCustomDescription(myWheelObject.getDescription());
        ModelArrayList = new ArrayList<>();
        if(!myWheelObject.getVal1().isEmpty()) ModelArrayList.add(myWheelObject.getVal1());
        if(!myWheelObject.getVal2().isEmpty()) ModelArrayList.add(myWheelObject.getVal2());
        if(!myWheelObject.getVal3().isEmpty()) ModelArrayList.add(myWheelObject.getVal3());
        if(!myWheelObject.getVal4().isEmpty()) ModelArrayList.add(myWheelObject.getVal4());
        if(!myWheelObject.getVal5().isEmpty()) ModelArrayList.add(myWheelObject.getVal5());
        if(!myWheelObject.getVal6().isEmpty()) ModelArrayList.add(myWheelObject.getVal6());
        if(!myWheelObject.getVal7().isEmpty()) ModelArrayList.add(myWheelObject.getVal7());
        if(!myWheelObject.getVal8().isEmpty()) ModelArrayList.add(myWheelObject.getVal8());

        WheelCount = ModelArrayList.size();
        model.setCustomContents(ModelArrayList);
        rotaryWheel = new CustomRotaryWheel(this);
        rotaryWheel.customWheelModel = model;
        rotaryWheel.tumbnail = 0;
        rotaryWheel.init();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.width = WheelRadius;
        params.height = WheelRadius;
        rotaryWheel.setLayoutParams(params);
        frameLayout.addView(rotaryWheel);
        txtSpinComment.setText(model.getCustomDescription());
    }

    void changeTitle(int index){

        selectedSpinName = ModelArrayList.get(index);
        String query = "http://yelp.com/search?find_desc=" + selectedSpinName + "&find_loc=" + Global.USER_CITY;
        selectedURL = query;
        txtSpinTitle.setText(ModelArrayList.get(index));
    }
    private void setEvents1(){

        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        beginTrackingWithTouch(motionEvent);
                        break;
                    case MotionEvent.ACTION_UP:
                        endTrackingWithTouch(motionEvent);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        continueTrackingWithTouch(motionEvent);
                        break;
                }
                return true;
            }
        });
    }
    int getPos4(float x, float y){
        if (x < WheelRadius/2){
            if(y < WheelRadius/2){
                pos4 = 4;
                return 4;
            }else {
                pos4 = 3;
                return 3;
            }
        }else {
            if(y > WheelRadius/2){
                pos4 = 2;
                return 2;
            }else {
                pos4 = 1;
                return 1;
            }
        }
    }

    boolean beginTrackingWithTouch(MotionEvent motionEvent){
        //1 - Get touch position
        float touchx = motionEvent.getX();
        float touchy = motionEvent.getY();
        float dist = calculateDistanceFromCenter(touchx, touchy);
        if (dist < 40 || dist > WheelRadius/2 - 10) {
            return false;
        }

        //2 - calculate distance from center
        float dx = touchx - WheelRadius/2;
        float dy = touchy - WheelRadius/2;
        //3 - Calculate init angle and init timeticks
        initAngle = (float)Math.atan2(dy, dx);
        firstTime = Calendar.getInstance().getTimeInMillis();

        //4 - save current transform
        return true;
    }

    boolean continueTrackingWithTouch(MotionEvent motionEvent){
        float touchx = motionEvent.getX();
        float touchy = motionEvent.getY();
        float dx = touchx - WheelRadius/2;
        float dy = touchy - WheelRadius/2;
        float ang = (float)Math.atan2(dy, dx);
        float angleDifference = initAngle - ang;
//        Log.d("continueAngle = ", ang*180/Math.PI + "");
        pos4 = getPos4(touchx, touchy);
//        if(pos4 == 1 || pos4 == 4) angleDifference *= -1;
        if(initAngle < 0 && ang < 0){
            angleDifference *= -1;
        }
        if(initAngle < 0 && ang > 0.75){
            angleDifference = ang + initAngle;
        }
        if(initAngle > 0 && ang < 0){
            angleDifference = initAngle + ang;
        }
        rotaryWheel.setRotation((float) (rotaryWheel.getRotation() +  angleDifference));
        return true;
    }

    void endTrackingWithTouch(MotionEvent motionEvent){
        float touchx = motionEvent.getX();
        float touchy = motionEvent.getY();
        float dx = touchx - WheelRadius/2;
        float dy = touchy - WheelRadius/2;
        lastAngle = (float)Math.atan2(dy, dx);
        if(initAngle > 0){
            if(lastAngle < 0){
                initAngle *= -1;
            }
        }else if(initAngle < 0){
            if(lastAngle > 0){
                initAngle *= -1;
            }
        }

        lastTime = Calendar.getInstance().getTimeInMillis();
        initW = (lastAngle - initAngle) / (lastTime - firstTime) * 200;
//        initW = (lastAngle - initAngle) * deltaTime;
        if(timer != null){
            timer.cancel();
            timer.purge();
        }
        timer = new Timer();
        myTimerTask = new CustomSpinActivity.MyCustomTimerTask();
        timer.schedule(myTimerTask, 10, 1);
    }


    float calculateDistanceFromCenter(float x, float y){
        float dx = x - WheelRadius/2;
        float dy = y - WheelRadius/2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private void getCurrentSection(){
        float rotate = rotaryWheel.getRotation();
        if(rotate > 0){
            rotate = 360 - (rotate % 360);
            Log.v("Rotated === ", rotate + "");
        }else{
            Log.v("----Rotated === ", rotate % 360 + "");
            rotate = Math.abs(rotate % 360);
        }
        int index = (int)(rotate / (360/WheelCount));
        if(index >= WheelCount) index = 0;
        changeTitle(index);
    }

    class MyCustomTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    if(timer == null) return;
                    rotaryWheel.setRotation((float) (rotaryWheel.getRotation() + initW));
                    if(initW > 0){
                        initW -= deltaAngle;
                    }else if(initW < 0){
                        initW += deltaAngle;
                    }
                    if(initW > -deltaAngle && initW < deltaAngle){
                        getCurrentSection();
                        timer.cancel();
                        timer.purge();
                        timer = null;
                    }
                }});
        }

    }
}
