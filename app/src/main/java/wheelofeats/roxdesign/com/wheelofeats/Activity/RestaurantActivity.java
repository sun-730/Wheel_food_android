package wheelofeats.roxdesign.com.wheelofeats.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import wheelofeats.roxdesign.com.wheelofeats.Fragment.DrinkFragment;
import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.model.FeatureModel;
import wheelofeats.roxdesign.com.wheelofeats.model.RestaurantModel;

public class RestaurantActivity extends Activity {
    private Context context;
    Button bttBack;
    ListView tlRestaurant;
    RestaurantRowAdapter restaurantRowAdapter;
    private ProgressDialog progress;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        context = this;
        setContents();
        setEvents();
        initData();
//        downloadImageTMP();
    }
    @Override
    public void onBackPressed() {
    }
    private void setContents(){
        bttBack = (Button)findViewById(R.id.btt_restaurant_back);
        tlRestaurant = (ListView) findViewById(R.id.tbl_restaurant);
        tlRestaurant.setAdapter(restaurantRowAdapter = new RestaurantRowAdapter());
    }

    private void setEvents(){
        bttBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData(){
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        restaurantRowAdapter.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotsChat = dataSnapshot.child("restaurant").getChildren().iterator();
                while (dataSnapshotsChat.hasNext()) {
                    RestaurantModel model = new RestaurantModel();
                    DataSnapshot dataSnapshotChild = dataSnapshotsChat.next();
                    String title = dataSnapshotChild.getKey();
                    String imagename = dataSnapshotChild.getValue(String.class);
                    model.setItemTitle1(title);
                    model.setItemImgName(imagename);
                    restaurantRowAdapter.add(model);
                }
                restaurantRowAdapter.notifyDataSetChanged();
                progress.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void downloadImageTMP(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

//        StorageReference image = storageRef.child("example.jpg");
        StorageReference gsReference = storage.getReferenceFromUrl("gs://wheeleat-84daf.appspot.com/tea-bg.png");
//        final long ONE_MEGABYTE = 1024 * 1024;
        final long ONE_MEGABYTE = 1024 * 1024;
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            }
        });
    }


    public class RestaurantRowAdapter extends BaseAdapter {
        ArrayList<RestaurantModel> restaurantList;
        public RestaurantRowAdapter(){
            restaurantList = new ArrayList<RestaurantModel>();
        }
        @Override
        public int getCount() {
            int num = (int)((restaurantList.size() + 1) / 2);
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

        public void add(RestaurantModel bean){
            restaurantList.add(bean);
        }

        public void clear(){
            restaurantList.clear();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = View.inflate(RestaurantActivity.this, R.layout.restaurant_row, null);
            }

            int index1 = position * 2;
            int index2 = position * 2 + 1;

            TextView txtTitle1 = (TextView)convertView.findViewById(R.id.txt_restaurant_item1);
            TextView txtTitle2 = (TextView)convertView.findViewById(R.id.txt_restaurant_item2);
            final ImageButton imgRestaurant1 = (ImageButton)convertView.findViewById(R.id.img_restaurant_item1);
            TextView txtTitle3 = (TextView)convertView.findViewById(R.id.txt_restaurant_item3);
            TextView txtTitle4 = (TextView)convertView.findViewById(R.id.txt_restaurant_item4);
            ImageButton imgRestaurant3 = (ImageButton)convertView.findViewById(R.id.img_restaurant_item2);
            LinearLayout linearLayout2 = (LinearLayout)convertView.findViewById(R.id.layer_restaurant_item2);

            final RestaurantModel contact1 = (RestaurantModel)getItem(index1);
            txtTitle1.setText(contact1.getItemTitle1());
            txtTitle2.setText(contact1.getItemTitle2());
            byte[] decodedString = Base64.decode(contact1.getItemImgName(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgRestaurant1.setImageBitmap(decodedByte);
            imgRestaurant1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(contact1.getItemTitle1().equals("DRINKS")){
                        Intent intent = new Intent(context, DrinkActivity.class);
                        context.startActivity(intent);
                    }else {
                        Intent intent = new Intent(context, SpinActivity.class);
                        intent.putExtra("spinType", contact1.getItemTitle1());
                        context.startActivity(intent);
                    }
                }
            });
            if(index2 >= restaurantList.size()){
                linearLayout2.setVisibility(View.INVISIBLE);
            }else{
                final RestaurantModel contact2 = (RestaurantModel)getItem(index2);
                if(contact2 != null){
                    linearLayout2.setVisibility(View.VISIBLE);
                    txtTitle3.setText(contact2.getItemTitle1());
                    txtTitle4.setText(contact2.getItemTitle2());
                    byte[] decodedString2 = Base64.decode(contact2.getItemImgName(), Base64.DEFAULT);
                    Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                    imgRestaurant3.setImageBitmap(decodedByte2);

                    imgRestaurant3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(contact2.getItemTitle1().equals("DRINKS")){
                                Intent intent = new Intent(context, DrinkActivity.class);
                                context.startActivity(intent);
                            }else{
                                Intent intent = new Intent(context, SpinActivity.class);
                                intent.putExtra("spinType", contact2.getItemTitle1());
                                context.startActivity(intent);

                            }
                        }
                    });
                }
            }


            return convertView;
        }

    }
}
