package wheelofeats.roxdesign.com.wheelofeats.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.Utils.Global;
import wheelofeats.roxdesign.com.wheelofeats.model.MyWheelList;
import wheelofeats.roxdesign.com.wheelofeats.model.MyWheelObject;

public class CreateWheelActivity extends AppCompatActivity {

    Button bttBack, bttCancel, bttSave;
    private Context context;
    private Realm realm;
    private int indexWheel;
    private MyWheelObject savedObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_create_wheel);
        realm = Realm.getDefaultInstance();
        indexWheel = -1;
        indexWheel = getIntent().getExtras().getInt("index");
        setContents();
        setEvents();
        if(indexWheel != -1){
            bttCancel.setText("Delete");
            getInitialData();
        }else{
            bttCancel.setText("Cancel");
        }
     }
    @Override
    public void onBackPressed() {
        Realm.init(context);
    }
    private void setContents(){
        bttBack = (Button)findViewById(R.id.btt_create_back);
        bttCancel = (Button)findViewById(R.id.bttCancel);
        bttSave = (Button)findViewById(R.id.bttSave);
    }

    private void setEvents(){
        bttBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bttCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(indexWheel != -1){
                    realm.beginTransaction();
                    savedObject.deleteFromRealm();
                    realm.commitTransaction();
                    finish();
                }else{
                    finish();
                }
            }
        });
        bttSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWheelData();
            }
        });
    }

    void getInitialData(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(MyWheelObject.class).findAll();
        savedObject = (MyWheelObject) results.get(indexWheel);
        EditText _title = (EditText)findViewById(R.id.wheel_title);
        EditText _content1 = (EditText)findViewById(R.id.entry1);
        EditText _content2 = (EditText)findViewById(R.id.entry2);
        EditText _content3 = (EditText)findViewById(R.id.entry3);
        EditText _content4 = (EditText)findViewById(R.id.entry4);
        EditText _content5 = (EditText)findViewById(R.id.entry5);
        EditText _content6 = (EditText)findViewById(R.id.entry6);
        EditText _content7 = (EditText)findViewById(R.id.entry7);
        EditText _content8 = (EditText)findViewById(R.id.entry8);
        EditText _comment = (EditText)findViewById(R.id.wheel_description);

        _title.setText(savedObject.getTitle());
        _comment.setText(savedObject.getDescription());
        _content1.setText(savedObject.getVal1());
        _content2.setText(savedObject.getVal2());
        _content3.setText(savedObject.getVal3());
        _content4.setText(savedObject.getVal4());
        _content5.setText(savedObject.getVal5());
        _content6.setText(savedObject.getVal6());
        _content7.setText(savedObject.getVal7());
        _content8.setText(savedObject.getVal8());
    }


    void saveWheelData(){
        makeSaveData();
    }
    void makeSaveData(){
        EditText _title = (EditText)findViewById(R.id.wheel_title);
        EditText _content1 = (EditText)findViewById(R.id.entry1);
        EditText _content2 = (EditText)findViewById(R.id.entry2);
        EditText _content3 = (EditText)findViewById(R.id.entry3);
        EditText _content4 = (EditText)findViewById(R.id.entry4);
        EditText _content5 = (EditText)findViewById(R.id.entry5);
        EditText _content6 = (EditText)findViewById(R.id.entry6);
        EditText _content7 = (EditText)findViewById(R.id.entry7);
        EditText _content8 = (EditText)findViewById(R.id.entry8);
        EditText _comment = (EditText)findViewById(R.id.wheel_description);

        ArrayList<String> arrayList = new ArrayList<>();
        if(!_content1.getText().toString().isEmpty()) arrayList.add(_content1.getText().toString());
        if(!_content2.getText().toString().isEmpty()) arrayList.add(_content2.getText().toString());
        if(!_content3.getText().toString().isEmpty()) arrayList.add(_content3.getText().toString());
        if(!_content4.getText().toString().isEmpty()) arrayList.add(_content4.getText().toString());
        if(!_content5.getText().toString().isEmpty()) arrayList.add(_content5.getText().toString());
        if(!_content6.getText().toString().isEmpty()) arrayList.add(_content6.getText().toString());
        if(!_content7.getText().toString().isEmpty()) arrayList.add(_content7.getText().toString());
        if(!_content8.getText().toString().isEmpty()) arrayList.add(_content8.getText().toString());

        if(arrayList.size() < 3) {
            Toast.makeText(context,"Please insert more than 2 entry", Toast.LENGTH_SHORT).show();
            return;
        }

        if(indexWheel != -1){
            realm.beginTransaction();
            savedObject.setDescription(_comment.getText().toString());
            savedObject.setVal1(_content1.getText().toString());
            savedObject.setVal2(_content2.getText().toString());
            savedObject.setVal3(_content3.getText().toString());
            savedObject.setVal4(_content4.getText().toString());
            savedObject.setVal5(_content5.getText().toString());
            savedObject.setVal6(_content6.getText().toString());
            savedObject.setVal7(_content7.getText().toString());
            savedObject.setVal8(_content8.getText().toString());
            realm.copyToRealmOrUpdate(savedObject);
            realm.commitTransaction();
        }else{
            realm.beginTransaction();
            MyWheelObject object = realm.createObject(MyWheelObject.class, UUID.randomUUID().toString());
            object.setTitle(_title.getText().toString());
            object.setDescription(_comment.getText().toString());
            object.setVal1(_content1.getText().toString());
            object.setVal2(_content2.getText().toString());
            object.setVal3(_content3.getText().toString());
            object.setVal4(_content4.getText().toString());
            object.setVal5(_content5.getText().toString());
            object.setVal6(_content6.getText().toString());
            object.setVal7(_content7.getText().toString());
            object.setVal8(_content8.getText().toString());
            realm.commitTransaction();
        }

        finish();

    }
}
