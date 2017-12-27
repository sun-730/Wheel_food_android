package wheelofeats.roxdesign.com.wheelofeats.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.model.FeatureModel;

public class UnlockFeatureActivity extends Activity {
    private Button bttBack, bttRestore;
    private ListView tlUnlock;
    private ArrayList<FeatureModel> featureModelArrayList;
    private UnlockRowAdapter unlockRowAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_feature);
        context = this;
        setContents();
        setEvents();
        initData();
    }
    @Override
    public void onBackPressed() {
    }
    private void initData(){
        unlockRowAdapter.clear();
        FeatureModel model = new FeatureModel();
        model.setFeatureTitle("AT A BAKERY");
        model.setFeatureImgName("unlock_item5");
        model.setFeatureDescription("Too many pastries to choose from? Don’t know if you want a crepe or croissant sandwich? This wheel will upload in “Already at Restaurant” upon purchase. Each result you land on comes with small facts.");
        model.setFeaturePrice("Buy $0.99");
        unlockRowAdapter.add(model);

        model = new FeatureModel();
        model.setFeatureTitle("PICK MY BEER");
        model.setFeatureImgName("unlock_item6");
        model.setFeatureDescription("Making decisions can be more difficult when already under the influence. Purchase our beer wheel to help you. Each result you land on comes with small facts.");
        model.setFeaturePrice("Buy $0.99");
        unlockRowAdapter.add(model);

        model = new FeatureModel();
        model.setFeatureTitle("CUSTOM WHEELS");
        model.setFeatureImgName("unlock_item1");
        model.setFeatureDescription("Create your own wheels and save them. Enter many as 8 options, You can go back and edit these options anytime.");
        model.setFeaturePrice("Buy $0.99");
        unlockRowAdapter.add(model);

        model = new FeatureModel();
        model.setFeatureTitle("PICK MY SEAFOOD");
        model.setFeatureImgName("unlock_item2");
        model.setFeatureDescription("Need a break from cattle? Buy our seafood wheel, it will automatically be added to the 'Already at Restaurant' menu. Each result you land on comes with small facts.");
        model.setFeaturePrice("Buy $0.99");
        unlockRowAdapter.add(model);

        model = new FeatureModel();
        model.setFeatureTitle("VEGETARIAN");
        model.setFeatureImgName("unlock_item4");
        model.setFeatureDescription("For this wheel we listed vegetables high in nutrients. Whatever the wheel lands on, find a dish with that primary ingredient, it'll be easier to pick a dish if you're at a restaurant or finding a recipe. Each result you land on comes with small facts.");
        model.setFeaturePrice("Buy $0.99");
        unlockRowAdapter.add(model);

        model = new FeatureModel();
        model.setFeatureTitle("PICK MY WINE");
        model.setFeatureImgName("unlock_item3");
        model.setFeatureDescription("Some restaurants can have an extensive wine list that can be overwhelming. We're here to help. Each result you land on comes with small facts.");
        model.setFeaturePrice("Buy $0.99");
        unlockRowAdapter.add(model);

        model = new FeatureModel();
        model.setFeatureTitle("PICK MY CHEESE");
        model.setFeatureImgName("unlock_item7");
        model.setFeatureDescription("Need to host a party, or fill a cheeseboard at a restaurant? Spin to help you decide. The following choices are popular cheeses that pair well with wine.");
        model.setFeaturePrice("Buy $0.99");
        unlockRowAdapter.add(model);

        unlockRowAdapter.notifyDataSetChanged();
    }

    private void setContents(){
        bttBack = (Button) findViewById(R.id.btt_unlock_back);
        bttRestore = (Button) findViewById(R.id.btt_restore);
        tlUnlock = (ListView) findViewById(R.id.tbl_unlock);
        tlUnlock.setAdapter(unlockRowAdapter = new UnlockRowAdapter());
    }
    private void setEvents(){
        bttBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class UnlockRowAdapter extends BaseAdapter {
        ArrayList<FeatureModel> featureList;
        public UnlockRowAdapter(){
            featureList = new ArrayList<FeatureModel>();
        }
        @Override
        public int getCount() {
            return featureList.size();
        }
        @Override
        public Object getItem(int position) {
            return featureList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void add(FeatureModel bean){
            featureList.add(bean);
        }

        public void clear(){
            featureList.clear();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = View.inflate(UnlockFeatureActivity.this, R.layout.unlock_row, null);
            }
            TextView txtTitle = (TextView)convertView.findViewById(R.id.unlock_row_title);
            TextView txtDescription = (TextView)convertView.findViewById(R.id.unlock_row_description);
            Button bttBuy = (Button)convertView.findViewById(R.id.unlock_btt_buy);
            ImageView img = (ImageView)convertView.findViewById(R.id.unlock_row_img);

            FeatureModel contact = (FeatureModel)getItem(position);
            txtTitle.setText(contact.getFeatureTitle());
            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(contact.getFeatureImgName(), "drawable",
                    context.getPackageName());
            img.setImageResource(resourceId);
            txtDescription.setText(contact.getFeatureDescription());
            txtDescription.setSelected(true);
            bttBuy.setText(contact.getFeaturePrice());
            txtDescription.setMovementMethod(new ScrollingMovementMethod());
            return convertView;
        }

    }
}
