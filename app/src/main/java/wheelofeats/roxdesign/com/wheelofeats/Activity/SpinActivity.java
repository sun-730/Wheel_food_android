package wheelofeats.roxdesign.com.wheelofeats.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.Utils.Global;
import wheelofeats.roxdesign.com.wheelofeats.model.RestaurantModel;
import wheelofeats.roxdesign.com.wheelofeats.model.SpinModel;
import wheelofeats.roxdesign.com.wheelofeats.wheel.RotaryWheel;

public class SpinActivity extends Activity {
    private ProgressDialog progress;
    ArrayList<SpinModel> ModelArrayList;
    Button bttBack, bttSpin, bttShare;
    TextView txtSpinTitle, txtSpinComment, lblSpinHead;
    FrameLayout frameLayout;
    ArrayList<Paint> mpaint, mbackgroundpaint;
    ArrayList<String> mTitle;
    RotaryWheel rotaryWheel;
    ImageView wheelBackground, wheelIcon;
    RelativeLayout wheelLayer;
    int WheelRadius;
    private String spinType, selectedSpinName, selectedURL;
    long firstTime, lastTime;

    float deltaAngle = 0.001f;
    float initAngle, lastAngle, initW;
    int pos4;
    Timer timer;
    MyTimerTask myTimerTask;
    private static int REQUEST_CAMERA = 0;
    private static int SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);
        spinType = getIntent().getStringExtra("spinType");
        setContents();
        getSpinData();
//        setPaintData();
        setEvents();
        setEvents1();
    }
    private void setContents() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        selectedSpinName = "";
        lblSpinHead = (TextView) findViewById(R.id.lblSpinHead);
        lblSpinHead.setText(spinType);
        bttBack = (Button) findViewById(R.id.btt_spin_back);
        bttShare = (Button) findViewById(R.id.bttshare);
        bttSpin = (Button) findViewById(R.id.bttspin);
        frameLayout = (FrameLayout) findViewById(R.id.wheel_layer);
        txtSpinTitle = (TextView) findViewById(R.id.txtspintitle);
        txtSpinComment = (TextView) findViewById(R.id.txtspincomment);
        WheelRadius = (int)(metrics.widthPixels * 0.8);
//        WheelRadius = (int) getResources().getDimension(R.dimen.wheel_radius);
        wheelBackground = (ImageView) findViewById(R.id.imgbackground);
        wheelIcon = (ImageView) findViewById(R.id.wheel_icon);
        wheelLayer = (RelativeLayout) findViewById(R.id.wheellayer);
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
                if(spinType.equals("cuisines")){
                    Intent intent = new Intent(SpinActivity.this, SpinActivity.class);
                    intent.putExtra("spinType", selectedSpinName);
                    SpinActivity.this.startActivity(intent);

                }else{
                    Intent intent = new Intent(SpinActivity.this, WebActivity.class);
                    intent.putExtra("url", selectedURL);
                    SpinActivity.this.startActivity(intent);
                }
            }
        });
        bttShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotos();
            }
        });
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    void selectPhotos(){
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(SpinActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Global.checkPermission(SpinActivity.this);
                if (items[item].equals("Take Photo")) {
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    void combilePhotos(){

    }
    void sharePhots(){

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getSpinData(){
        ModelArrayList = new ArrayList<SpinModel>();
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotsChat = dataSnapshot.child(spinType).getChildren().iterator();
                while (dataSnapshotsChat.hasNext()) {
                    SpinModel model = new SpinModel();
                    DataSnapshot dataSnapshotChild = dataSnapshotsChat.next();
                    String title = dataSnapshotChild.getKey();

                    Map<String, String> stars = (Map<String, String>) dataSnapshotChild.getValue();
                    String comment = stars.get("comment");
                    String link = stars.get("link");
                    String color1 = stars.get("color1");
                    String color2 = stars.get("color2");
                    String color3 = stars.get("color3");
                    String imagedata = stars.get("background");
                    String imageicon = stars.get("icon");
                    model.setTitle(title);
                    model.setComment(comment);
                    model.setColor1(color1);
                    model.setColor2(color2);
                    model.setColor3(color3);
                    model.setLink(link);
                    model.setImgBackground(imagedata);
                    model.setImgIcon(imageicon);
                    ModelArrayList.add(model);
                }
                progress.hide();
                setPaintData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setPaintData(){

        mpaint = new ArrayList<Paint>();
        mbackgroundpaint = new ArrayList<Paint>();
        mTitle = new ArrayList<String>();

        for(int i = 0; i < ModelArrayList.size(); i++){
            Paint paint = new Paint();
            int r1 = Integer.parseInt(ModelArrayList.get(i).getColor1());
            int r2 = Integer.parseInt(ModelArrayList.get(i).getColor2());
            int r3 = Integer.parseInt(ModelArrayList.get(i).getColor3());
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
        rotaryWheel = new RotaryWheel(this);
        rotaryWheel.sectionCount = ModelArrayList.size();
        rotaryWheel.spinmodels = ModelArrayList;
        rotaryWheel.setPaintData(mpaint, mbackgroundpaint);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.width = WheelRadius;
        params.height = WheelRadius;
        rotaryWheel.setLayoutParams(params);
        frameLayout.addView(rotaryWheel);
    }

    void changeTitle(int index){
        selectedSpinName = ModelArrayList.get(index).getTitle();
        selectedURL = ModelArrayList.get(index).getLink();
        if(selectedURL.equals("")){
            String query = "http://yelp.com/search?find_desc=" + selectedSpinName + "&find_loc=" + Global.USER_CITY;
            selectedURL = query;
        }
        txtSpinTitle.setText(ModelArrayList.get(index).getTitle());
        txtSpinComment.setText(ModelArrayList.get(index).getComment());
        byte[] decodedString = Base64.decode(ModelArrayList.get(index).getImgBackground(), Base64.DEFAULT);
        if(decodedString.length > 20){
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            wheelBackground.setImageBitmap(decodedByte);
            wheelLayer.setBackgroundColor(getResources().getColor(R.color.layer_background));
        }
        byte[] decodedStringIcon = Base64.decode(ModelArrayList.get(index).getImgIcon(), Base64.DEFAULT);
        if(decodedStringIcon.length > 20){
            Bitmap decodedByteIcon = BitmapFactory.decodeByteArray(decodedStringIcon, 0, decodedStringIcon.length);
            wheelIcon.setImageBitmap(decodedByteIcon);
        }
        checkSpinType(index);
    }
    void checkSpinType(int index){
        if(spinType.equals("cuisines")){
            changeSpinButton(index);
        }

    }
    void changeSpinButton(int index){
        bttSpin.setText("SPIN '" + ModelArrayList.get(index).getTitle() + "' WHEEL");
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
        myTimerTask = new MyTimerTask();
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
        int index = (int)(rotate / (360/ModelArrayList.size()));
        if(index >= ModelArrayList.size()) index = 0;
        changeTitle(index);
    }

    class MyTimerTask extends TimerTask {

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
