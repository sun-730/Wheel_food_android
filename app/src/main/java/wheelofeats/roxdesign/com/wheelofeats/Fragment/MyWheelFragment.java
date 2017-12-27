package wheelofeats.roxdesign.com.wheelofeats.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import wheelofeats.roxdesign.com.wheelofeats.Activity.CreateWheelActivity;
import wheelofeats.roxdesign.com.wheelofeats.Activity.CustomSpinActivity;
import wheelofeats.roxdesign.com.wheelofeats.Activity.MyWheelsActivity;
import wheelofeats.roxdesign.com.wheelofeats.Activity.RestaurantActivity;
import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.Utils.Global;
import wheelofeats.roxdesign.com.wheelofeats.model.CustomSpinModel;
import wheelofeats.roxdesign.com.wheelofeats.model.CustomWheelModel;
import wheelofeats.roxdesign.com.wheelofeats.model.MyWheelList;
import wheelofeats.roxdesign.com.wheelofeats.model.MyWheelObject;
import wheelofeats.roxdesign.com.wheelofeats.model.RestaurantModel;
import wheelofeats.roxdesign.com.wheelofeats.wheel.CustomRotaryWheel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWheelFragment extends android.app.Fragment {
    TextView txtEnable;
    Switch swtEnable;
    Button bttCreate;
    GridView gridMyWheel;
    LayoutInflater layoutInflater;
    MyWheelRowAdapter myWheelRowAdapter;
    private Context context;

    public MyWheelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_my_wheel, container, false);
        txtEnable = (TextView) view.findViewById(R.id.edtEnable);
        swtEnable = (Switch) view.findViewById(R.id.switch_edit);
        bttCreate = (Button) view.findViewById(R.id.bttCreate);
        gridMyWheel = (GridView) view.findViewById(R.id.mywheel_grid);
        setEvents();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllMyWheelData();
    }

    void setEvents(){
        swtEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) txtEnable.setText(getResources().getString(R.string.enable));
                else  txtEnable.setText(getResources().getString(R.string.disable));
            }
        });
        bttCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateWheelActivity.class);
                intent.putExtra("index", -1);
                getActivity().startActivity(intent);
            }
        });
    }
    private void getAllMyWheelData(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(MyWheelObject.class).findAll();
        gridMyWheel.setAdapter(myWheelRowAdapter = new MyWheelRowAdapter());
        myWheelRowAdapter.clear();
        for(int i = 0; i < results.size(); i++){
            MyWheelObject myWheelObject = (MyWheelObject) results.get(i);
            CustomWheelModel model = new CustomWheelModel();
            model.setCustomTitle(myWheelObject.getTitle());
            model.setCustomID(myWheelObject.getId());
            model.setCustomDescription(myWheelObject.getDescription());
            ArrayList<String> arrayList = new ArrayList<>();
            if(!myWheelObject.getVal1().isEmpty()) arrayList.add(myWheelObject.getVal1());
            if(!myWheelObject.getVal2().isEmpty()) arrayList.add(myWheelObject.getVal2());
            if(!myWheelObject.getVal3().isEmpty()) arrayList.add(myWheelObject.getVal3());
            if(!myWheelObject.getVal4().isEmpty()) arrayList.add(myWheelObject.getVal4());
            if(!myWheelObject.getVal5().isEmpty()) arrayList.add(myWheelObject.getVal5());
            if(!myWheelObject.getVal6().isEmpty()) arrayList.add(myWheelObject.getVal6());
            if(!myWheelObject.getVal7().isEmpty()) arrayList.add(myWheelObject.getVal7());
            if(!myWheelObject.getVal8().isEmpty()) arrayList.add(myWheelObject.getVal8());

            model.setCustomContents(arrayList);

            myWheelRowAdapter.add(model);
        }

//        myWheelRowAdapter.notifyDataSetChanged();

    }

    public class MyWheelRowAdapter extends BaseAdapter {
        ArrayList<CustomWheelModel> restaurantList;
        public MyWheelRowAdapter(){
            restaurantList = new ArrayList<CustomWheelModel>();
        }
        @Override
        public int getCount() {
            int num = restaurantList.size() ;
            return num;
        }
        @Override
        public Object getItem(int position) {
            return restaurantList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void add(CustomWheelModel bean){
            restaurantList.add(bean);
        }

        public void clear(){
            restaurantList.clear();
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            CustomRotaryWheel customRotaryWheel;
//            if(convertView == null){
                convertView = View.inflate(getContext(), R.layout.custom_wheel_row, null);
//            }
            customRotaryWheel = new CustomRotaryWheel(getContext());
            final CustomWheelModel customWheelModel = (CustomWheelModel) getItem(position);
            customRotaryWheel.customWheelModel = customWheelModel;
            customRotaryWheel.tumbnail = 1;
            customRotaryWheel.init();
            RelativeLayout contentView = (RelativeLayout)convertView.findViewById(R.id.wheelview);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            params.width = (int)(metrics.widthPixels * 0.33);
            params.height = (int)(metrics.widthPixels * 0.33);
            customRotaryWheel.setLayoutParams(params);
            contentView.addView(customRotaryWheel);
            final int sePosition = position;
            Button _bttItem = (Button) contentView.findViewById(R.id.bttItem);
            _bttItem.setWidth(params.width);
            _bttItem.setHeight(params.height);
            _bttItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtEnable.getText().toString().endsWith("ENABLE")){
                        Intent intent = new Intent(getActivity(), CreateWheelActivity.class);
                        intent.putExtra("index", position);
                        getActivity().startActivity(intent);

                    }else{
                        Intent intent = new Intent(getActivity(), CustomSpinActivity.class);
                        intent.putExtra("index", position);
                        getActivity().startActivity(intent);
                    }


                }
            });

            return convertView;
        }

    }
}
