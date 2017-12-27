package wheelofeats.roxdesign.com.wheelofeats.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import wheelofeats.roxdesign.com.wheelofeats.R;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    Button bttBack;
    String searchURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        searchURL = getIntent().getStringExtra("url");
        setContents();
        setEvent();
        setWebContent();
    }

    private void setContents(){
        bttBack = (Button) findViewById(R.id.btt_web_back);
        webView = (WebView) findViewById(R.id.web_view);
    }

    private void setEvent(){
        bttBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setWebContent(){

        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebChromeClient(new WebChromeClient());
//
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });

        webView.loadUrl(searchURL);
    }
}
