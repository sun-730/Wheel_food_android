package wheelofeats.roxdesign.com.wheelofeats.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmResults;
import wheelofeats.roxdesign.com.wheelofeats.Fragment.MyWheelFragment;
import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.model.MyWheelList;

public class MyWheelsActivity extends Activity {
    MyWheelFragment myWheelFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Button bttBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wheels);
        setContents();
        setEvents();
    }
    @Override
    public void onBackPressed() {
    }
    private void setContents(){
        bttBack = (Button)findViewById(R.id.btt_mywheel_back);

        myWheelFragment = new MyWheelFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mywheel_layer, myWheelFragment, "MYWHEEL");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void setEvents(){

        bttBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
