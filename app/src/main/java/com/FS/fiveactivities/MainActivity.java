package com.FS.fiveactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private LinearLayout mainLayout;
    private Button start;
    private Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            /* remove title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
        start = initButton("Запустить", 30);
        exit = initButton("Выход", 30);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(MainActivity.this, ActivityList.class);
                i.putExtra("mysend", 144);
                i.putExtra("send200", "i hate terminator 6 - carl must die");
                startActivity(i);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mainLayout.addView(start);
        mainLayout.addView(exit);


    }

    Button initButton(String text, float textSize){
        Button a=new Button(this);
        a.setText(text);
        a.setTextSize(textSize);
        a.setGravity(Gravity.CENTER);
        return a;
    }
}
