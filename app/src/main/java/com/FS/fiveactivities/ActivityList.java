package com.FS.fiveactivities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityList extends Activity {
    private Point point;
    private Display defaultDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* remove title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_list);

        defaultDisplay = getWindowManager().getDefaultDisplay();
        point = new Point();
        defaultDisplay.getSize(point);

        String[] list = {"Калькулятор","Кликер","Файлы","Тест","Назад"};
        createList(list);
    }

    void createList(final String text[]){
        LinearLayout lay=findViewById(R.id.mainList);
        for(int i=0;i<text.length;i++){
            LinearLayout Item=new LinearLayout(this);
            final int name = i;
            Button gotoactivity = initButton(text[i],point.x/2,point.y/10, 32);
            gotoactivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextActivity;
                    switch(text[name]){
                        case "Калькулятор":
                            nextActivity = new Intent(ActivityList.this, calc.class);
                            startActivity(nextActivity);
                            break;
                        case "Кликер":
                            nextActivity = new Intent(ActivityList.this, clicker.class);
                            startActivity(nextActivity);
                            break;
                        case "Файлы":
                            nextActivity = new Intent(ActivityList.this, files.class);
                            startActivity(nextActivity);
                            break;
                        case "Тест":
                            nextActivity = new Intent(ActivityList.this, test.class);
                            startActivity(nextActivity);
                            break;
                        case "Назад":
                            finish();
                            break;
                            default:
                                finish();
                    }
                }
            });
            Item.addView(gotoactivity);

            Item.addView(initTextView(getEmojiByUnicode(0x2665),32));
            lay.addView(Item);
        }
    }
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    Button initButton(String text, int x, int y, float textSize){
        Button a=new Button(this);
        a.setText(text);
        a.setTextSize(textSize);
        a.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        a.getLayoutParams().width = x;
        a.getLayoutParams().height = y;
        return a;
    }

    TextView initTextView(String text, float textSize) {
        TextView a = new TextView(this);
        a.setText(Html.fromHtml(text));
        a.setTextSize(textSize);
        a.setGravity(Gravity.CENTER);
        a.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,2));
        return a;
    }
}
