package wheelofeats.roxdesign.com.wheelofeats.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.AsyncTask;

import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.Utils.Global;

public class SplashActivity extends Activity {
    boolean isRead;
    private ProgressDialog progress;
    AsyncTask<Void, Integer, Void> timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getPreference();
//        getAllData();
//        timerTask = new AsyncTask<Void, Integer, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                // TODO Auto-generated method stub
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void result) {
//                // TODO Auto-generated method stub
//                getPreference();
//                super.onPostExecute(result);
//            }
//        };
//        timerTask.execute();
    }
    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
//        timerTask.cancel(true);
        super.onPause();
    }

    private void getPreference()
    {
        String key = Global.WHEEL_SP;
        SharedPreferences sp = getSharedPreferences(key, Activity.MODE_PRIVATE);
        isRead = sp.getBoolean(Global.ReadTerms, false);
        if(isRead){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }else {
            startActivity(new Intent(SplashActivity.this, TutorialActivity.class));
            finish();
        }
        Global.sound_on = sp.getBoolean(Global.SOUND_ONOFF, true);
    }

    private void getAllData(){
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        AsyncHttpClient http = new AsyncHttpClient();
        String url = Global.BASE_URL;
        http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int ret_val = jsonObject.getInt("ret_val");
                    if (ret_val == Global.ERR_SUCCESS) {
                        JSONObject jsonObjData = jsonObject.getJSONObject("ret_data");
                        progress.dismiss();
                        getPreference();
                    } else {
                        String errStr = jsonObject.getString("err_code");
                        Global.ShowErrorDialog(getApplicationContext(), getString(R.string.net_error), errStr);
                        progress.dismiss();
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                progress.dismiss();
            }
        });
    }
}
