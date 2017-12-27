package wheelofeats.roxdesign.com.wheelofeats.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.Utils.Global;
import wheelofeats.roxdesign.com.wheelofeats.adapter.SlidingImage_Adapter;

public class TutorialActivity extends AppCompatActivity {
    LinearLayout last_linear;
    Button btt_gotIt;
    CheckBox chk_terms;
    // Array of Image IDs to Show In ImageSwitcher
    private static final Integer[] IMAGES = {R.drawable.tutorials1, R.drawable.tutorials2, R.drawable.tutorials3, R.drawable.tutorials4, R.drawable.tutorials5, R.drawable.tutorials6, R.drawable.tutorials7};
    ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    static ViewPager mPager;
    static int currentPage = 0;
    static int NUM_PAGES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        init();
        setContent();
        hideGotitView();
        setEvents();
    }
    @Override
    public void onBackPressed() {
    }
    private void setContent(){
        last_linear = (LinearLayout)findViewById(R.id.tutorial_last);
        btt_gotIt = (Button)findViewById(R.id.btt_gotit);
        chk_terms = (CheckBox)findViewById(R.id.checkBox);
    }
    private void setEvents(){
        btt_gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chk_terms.isChecked()){
                    savePreference();
                    startActivity(new Intent(TutorialActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
        chk_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(chk_terms.isChecked())
                startActivity(new Intent(TutorialActivity.this, TermsActivity.class));
            }
        });
    }
    private void savePreference(){
        String key = Global.WHEEL_SP;
        SharedPreferences sp = getSharedPreferences(key, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Global.ReadTerms, true);
        editor.commit();
    }

    private void setGotitView(){
        last_linear.setVisibility(View.VISIBLE);

    }
    private void hideGotitView(){
        last_linear.setVisibility(View.GONE);

    }

    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(TutorialActivity.this,ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                if(currentPage == IMAGES.length-1){
                    setGotitView();
                }else {
                    hideGotitView();
                }

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
}
