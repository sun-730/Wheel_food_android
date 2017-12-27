package wheelofeats.roxdesign.com.wheelofeats.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import wheelofeats.roxdesign.com.wheelofeats.R;

public class TermsActivity extends AppCompatActivity {
    private WebView webView;
    private Button btt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        setContents();
    }
    @Override
    public void onBackPressed() {
    }
    private void setContents(){
        webView = (WebView)findViewById(R.id.webview);
        String url = "file:///android_asset/terms.htm";
        webView.loadUrl(url);
        btt_back = (Button)findViewById(R.id.btt_privacy_back);
        btt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
