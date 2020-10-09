package com.FS.fiveactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class calc extends Activity {
    private LinearLayout mainCalcLayout;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* remove title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_calc);

        final EditText A=initEditText(R.id.Edit1,"",26);
        final EditText B=initEditText(R.id.Edit2,"",26);
        back = initButton("Назад", 30);
        mainCalcLayout = findViewById(R.id.mainCalcLayout);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final Intent i = new Intent(calc.this, ActivityList.class);
                //startActivity(i);
                finish();
            }
        });
        mainCalcLayout.addView(back);

        TextWatcher w=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String v1=A.getText().toString(),v2=B.getText().toString();

                if(v1.length()>0 && v2.length()>0)
                    initResultList(new String[]{
                            calculator(v1,v2,"+"),
                            calculator(v1,v2,"-"),
                            calculator(v1,v2,"*"),
                            calculator(v1,v2,"/"),
                    });
                else
                    initResultList(new String[]{});
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        A.addTextChangedListener(w);
        B.addTextChangedListener(w);
    }

    String calculator(String v1,String v2,String sign){
        int a=Integer.parseInt(v1),b=Integer.parseInt(v2);
        switch (sign){
            case "+":return v1+sign+v2+"="+(a+b);
            case "-":return v1+sign+v2+"="+(a-b);
            case "*":return v1+sign+v2+"="+(a*b);
            case "/":return v1+sign+v2+"="+(a/b);
        }
        return "";
    }

    EditText initEditText(int id,String text,float size){
        EditText a=findViewById(id);
        a.setGravity(Gravity.CENTER);
        a.setText(text);
        a.setTextSize(size);
        return a;
    }

    void initResultList(String text[]){
        ListView v=findViewById(R.id.calcList);
        v.setAdapter(new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,text));
    }

    Button initButton(String text, float textSize){
        Button a=new Button(this);
        a.setText(text);
        a.setTextSize(textSize);
        return a;
    }
}
