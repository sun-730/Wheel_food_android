package wheelofeats.roxdesign.com.wheelofeats.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import wheelofeats.roxdesign.com.wheelofeats.R;

public class DrinkActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        context = this;
        setEvents();
    }
    private void setEvents(){
        Button back = (Button) findViewById(R.id.bttback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageButton bttLiquor = (ImageButton) findViewById(R.id.imgLiquor1);
        ImageButton bttBeer = (ImageButton) findViewById(R.id.imgBeer1);
        ImageButton bttTea = (ImageButton) findViewById(R.id.imgTea1);
        ImageButton bttWine = (ImageButton) findViewById(R.id.imgWine1);

        bttLiquor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpinActivity.class);
                intent.putExtra("spinType", "LIQUOR");
                context.startActivity(intent);

            }
        });
        bttBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpinActivity.class);
                intent.putExtra("spinType", "BEER");
                context.startActivity(intent);

            }
        });
        bttTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpinActivity.class);
                intent.putExtra("spinType", "TEA");
                context.startActivity(intent);

            }
        });
        bttWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpinActivity.class);
                intent.putExtra("spinType", "WINE");
                context.startActivity(intent);

            }
        });
    }
}
