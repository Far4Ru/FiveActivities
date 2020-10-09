package com.FS.fiveactivities;


import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class files extends Activity {
    EditText ed;
    ListView lv;
    LinearLayout buttonsControl;
    Button backButton, saveButton;

    private Button back;
    String DOWNLOAD = Environment.getExternalStorageDirectory() + "/download";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* remove title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_files);
        back = findViewById(R.id.backToList);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final Intent i = new Intent(files.this, ActivityList.class);
                //startActivity(i);
                finish();
            }
        });

        lv = findViewById(R.id.filelist);
        buttonsControl = findViewById(R.id.buttonsControl);
        ed = findViewById(R.id.edit);
        backButton = findViewById(R.id.back);
        saveButton = findViewById(R.id.save);
        backButton.setText("Назад");
        saveButton.setText("Сохранить");
        ed.setVisibility(View.GONE);
        buttonsControl.setVisibility(View.GONE);
        initFileList(new File(DOWNLOAD));
    }

    void initFileList(final File f) {
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                f.list()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String filename = f.list()[position];
                final String filePath = f.getPath() + "/"+filename;
                showFile(filePath);

                buttonsControl.setVisibility(View.VISIBLE);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ed.setVisibility(View.GONE);
                        buttonsControl.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);
                        back.setVisibility(View.VISIBLE);


                    }
                });
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveFile(filePath);
                        //alert
                    }
                });
                ed.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
            }
        });
    }

    char [] readFile(File f){
        char buf[] = new char[(int) f.length()];
        try {

            FileReader file = new FileReader(f);
            file.read(buf);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }
    void showFile(String filePath){
        char p[]=readFile(new File(filePath));
        ed.setText(p,0,p.length);
    }
    void saveFile(String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(ed.getText().toString());
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(files.this,
                "Сохранено", Toast.LENGTH_SHORT);
        toast.show();
    }


    Button initButton(String text, float textSize){
        Button a=new Button(this);
        a.setText(text);
        a.setTextSize(textSize);
        return a;
    }
}