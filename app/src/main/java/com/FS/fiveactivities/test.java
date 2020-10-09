package com.FS.fiveactivities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class test extends Activity {

    //<c> - checkbox
    //<r> - radio           |  <c> - correct
    //<e> - text            |  <i> - incorrect
    String tests[][] = {
            {"question1<r>", "f<i>", "t<c>", "f<i>", "f<i>"},
            {"question2<r>", "f<i>", "t<c>", "f<i>", "f<i>"},
            {"question3<c>", "f<i>", "t<c>", "t<c>", "f<i>"},
            {"question4<c>", "f<i>", "t<c>", "t<c>", "f<i>"},
            {"question5<e>", "t<c>"},

    };
    int testPageNow, counter, typeOfAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* remove title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);
        initStart();
    }

    void initStart() {
        LinearLayout lay = findViewById(R.id.mainLayout);
        lay.removeAllViews();
        ImageView ship = initShip();
        Bitmap bmp;
        int width=500;
        int height=500;
        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.save);//image is your image
        bmp=Bitmap.createScaledBitmap(bmp, width,height, true);
        ship.setImageBitmap(bmp);
        lay.addView(ship);
        Button btn = initButton("Начать", 28);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPage(0);
            }
        });
        lay.addView(btn);
        Button back = initButton("Назад", 28);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final Intent i = new Intent(test.this, ActivityList.class);
                //startActivity(i);
                finish();
            }
        });
        lay.addView(back);
    }

    void initPage(int page) {
        LinearLayout lay = findViewById(R.id.mainLayout);
        testPageNow = page;
        lay.removeAllViews();
        lay.addView(initTextView(tests[page][0], 28));
        String testPageName = tests[page][0];

        if (testPageName.contains("<r>"))
            typeOfAnswer = 0;
        else if (testPageName.contains("<c>"))
            typeOfAnswer = 1;
        else if (testPageName.contains("<e>"))
            typeOfAnswer = 2;

        final RadioButton rb[]=new RadioButton[tests[page].length-1];
        RadioGroup rg = new RadioGroup(this);
        final CheckBox ch[]=new CheckBox[tests[page].length-1];
        final EditText et = initEditText("Ответ",22);
        switch (typeOfAnswer){
            case 0:
                for(int i=0;i<ch.length;i++){
                    rb[i]=initRadioButton(tests[page][i+1],22);
                    rg.addView(rb[i]);
                }
                lay.addView(rg);
                break;
            case 1:
                for(int i=0;i<ch.length;i++){
                    ch[i]=initCheckBox(tests[page][i+1],22);
                    lay.addView(ch[i]);
                }
                break;
            case 2:
                lay.addView(et);
                break;

            default:
                for(int i=0;i<ch.length;i++){
                    ch[i]=initCheckBox(tests[page][i+1],22);
                    lay.addView(ch[i]);
                }
        }

        Button btn = initButton("Дальше >>>", 28);
        lay.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initPage(testPageNow);
                switch (typeOfAnswer){
                    case 0:
                        for (int i = 0; i < ch.length; i++)
                            if (rb[i].isChecked())
                                if (tests[testPageNow][i + 1].contains("<c>")) counter++;
                        Log.d("@@@@@@@@@@@@@ ", counter + "");
                        break;
                    case 1:
                        for (int i = 0; i < ch.length; i++)
                            if (ch[i].isChecked())
                                if (tests[testPageNow][i + 1].contains("<c>")) counter++;
                        Log.d("@@@@@@@@@@@@@ ", counter + "");
                        break;
                    case 2:
                        if(et.getText().toString().equals(tests[testPageNow][1])){
                            counter++; Log.d("@@@@@@@@@@@@@ ", counter + "");
                        }
                        break;

                    default:
                        for (int i = 0; i < ch.length; i++)
                            if (ch[i].isChecked())
                                if (tests[testPageNow][i + 1].contains("<c>")) counter++;
                        Log.d("@@@@@@@@@@@@@ ", counter + "");
                }
                testPageNow++;
                if (testPageNow < tests.length)
                    initPage(testPageNow);
                else
                    finishTest(counter);
            }
        });
    }

    void finishTest(int counter) {
        LinearLayout lay = findViewById(R.id.mainLayout);
        if (counter == 5) {
            lay.removeAllViews();
            lay.addView(initTextView("Оценка 3", 28));
        } else if (counter > 6 && counter < 8) {
            lay.removeAllViews();
            lay.addView(initTextView("Оценка 4", 28));
        } else if (counter > 8 && counter < 11) {
            lay.removeAllViews();
            lay.addView(initTextView("Оценка 5", 28));
        } else {
            lay.removeAllViews();
            lay.addView(initTextView("Оценка 2", 28));
        }
        Button btnRestart = initButton("Перезапустить", 28);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPage(0);
            }
        });
        lay.addView(btnRestart);

        Button btnToStart = initButton("К начальному экрану", 28);
        btnToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initStart();
            }
        });
        lay.addView(btnToStart);
        Button btnExit = initButton("Завершить тест", 28);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final Intent i = new Intent(test.this, ActivityList.class);
                //startActivity(i);
                finish();
            }
        });
        lay.addView(btnExit);
    }
    ImageView initShip() {
        ImageView a = new ImageView(this);
        a.setImageResource(R.drawable.save);
        return a;
    }
    EditText initEditText(String text, float textSize) {
        EditText a = new EditText(this);
        a.setHint(Html.fromHtml(text));
        a.setTextSize(textSize);
        a.setGravity(Gravity.CENTER);
        return a;
    }
    RadioButton initRadioButton(String text, float textSize) {
        RadioButton a = new RadioButton(this);
        a.setText(Html.fromHtml(text));
        a.setTextSize(textSize);
        a.setGravity(Gravity.LEFT);
        return a;
    }

    CheckBox initCheckBox(String text, float textSize) {
        CheckBox a = new CheckBox(this);
        a.setText(Html.fromHtml(text));
        a.setTextSize(textSize);
        a.setGravity(Gravity.LEFT);
        return a;
    }

    Button initButton(String text, float textSize) {
        Button a = new Button(this);
        a.setText(text);
        a.setTextSize(textSize);
        a.setGravity(Gravity.CENTER);
        return a;
    }

    TextView initTextView(String text, float textSize) {
        TextView a = new TextView(this);
        a.setText(Html.fromHtml(text));
        a.setTextSize(textSize);
        a.setGravity(Gravity.CENTER);
        return a;
    }
}