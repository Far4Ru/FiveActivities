package com.FS.fiveactivities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class clicker extends Activity {

    int money,days,students,price;
    int interest = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* remove title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_clicker);
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
                initMainPage();
            }
        });
        lay.addView(btn);
        Button back = initButton("Назад", 28);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final Intent i = new Intent(clicker.this, ActivityList.class);
                //startActivity(i);
                finish();
            }
        });
        lay.addView(back);
    }
    void initMainPage(){

        final LinearLayout lay = findViewById(R.id.mainLayout);
        lay.removeAllViews();
        final ImageView ship = initShip();
        Bitmap bmp;
        int width=300;
        int height=300;
        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.save);//image is your image
        bmp=Bitmap.createScaledBitmap(bmp, width,height, true);
        ship.setImageBitmap(bmp);

        price = 10;
        final TextView tv1 = initTextView("Время: "+days+"/30 дней", 30);
        final TextView tv2 = initTextView("Денег: "+money+"/50000 р.", 30);
        final TextView tv3 = initTextView("Стоимость 1 билета: "+price+"р.", 30);
        final TextView tv4 = initTextView("Студентов сейчас: "+students+" человек", 30);
        Button btnUp= initButton("Повысить стоимость билета (повысит недовольство)",18);
        lay.addView(tv2);
        lay.addView(tv1);
        lay.addView(tv3);
        lay.addView(tv4);
        lay.addView(btnUp);
        lay.addView(ship);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price+=new Random().nextInt(100)+1;
                tv3.setText("Стоимость 1 билета: "+price+"р.");
                interest--;
            }
        });

        ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipFlick(lay,ship);

                money+=price;
                students++;
                tv4.setText("Студентов сейчас: "+students+" человек");
                tv1.setText("Денег: "+money+"/50000 р.");
            }
        });

        final Timer tm=new Timer();
        tm.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shipFlick(lay,ship);
                        tv2.setText("Время: "+days+"/30 дней");
                        tv4.setText("Студентов сейчас: "+students+" человек");
                        tv1.setText("Денег: "+money+"/50000 р.");

                        if (days>=30 && money < 50000){
                            tm.cancel();
                            initFinalResults("Вы не справились, корабль не отплыл");
                        }
                        else if  (days>=30 && money > 45000){
                            tm.cancel();
                            initFinalResults("Вы смогли, корабль продали");
                        }
                        else if  (money > 50000){
                            tm.cancel();
                            initFinalResults("Вы смогли, корабль отплыл");
                        }
                    }
                });
                days+=new Random().nextInt(3)+1;
                int n;
                if (interest>=1){
                    n = new Random().nextInt(interest)+1;
                    students+=n;
                    money+=price*n;
                }
                else{
                }
            }
        },2000,2000);

    }
    void initFinalResults(String resultText){

        LinearLayout lay = findViewById(R.id.mainLayout);
        lay.removeAllViews();
        final TextView showResult = initTextView(resultText, 30);
        lay.addView(showResult);
        Button btnRetry= initButton("Попробовать снова",30);
        Button btnExit= initButton("Выйти и смириться",30);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final Intent i = new Intent(clicker.this, ActivityList.class);
                //startActivity(i);
                finish();
            }
        });
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money =0;
                days =0;
                students=0;
                price=0;
                interest = 20;
                initStart();
            }
        });
        lay.addView(btnRetry);
        lay.addView(btnExit);

    }

    void shipFlick(LinearLayout lay, final ImageView shipbefore){;
        lay.removeView(shipbefore);
        Bitmap bmp;
        int width=310;
        int height=310;
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.save);//image is your image
        bmp = Bitmap.createScaledBitmap(bmp, width,height, true);
        shipbefore.setImageBitmap(bmp);
        lay.addView(shipbefore);
        final ImageView shipFinal = shipbefore;
        final LinearLayout layFinal = lay;
        /*final TextView flowNum = initTextView("+1", new Random().nextInt(20)+10);
        lay.addView(flowNum);*/


        final Timer tm=new Timer();
        tm.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layFinal.removeView(shipFinal);
                        Bitmap bmp;
                        int width=300;
                        int height=300;
                        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.save);//image is your image
                        bmp = Bitmap.createScaledBitmap(bmp, width,height, true);
                        shipFinal.setImageBitmap(bmp);
                        layFinal.addView(shipFinal);
                    }
                });
            }
        },100);
        /*final Timer tm2=new Timer();
        tm2.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layFinal.removeView(flowNum);
                    }
                });
            }
        },200);*/
    }


    ImageView initShip() {
        ImageView a = new ImageView(this);
        a.setImageResource(R.drawable.save);
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