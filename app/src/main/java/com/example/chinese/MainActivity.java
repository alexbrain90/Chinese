package com.example.chinese;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private Random rnd;
    private TextView tvHieroglyph, tvPinyin, tvTranslate, tvRating;
    private int currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        tvHieroglyph = findViewById(R.id.tvHieroglyph);
        tvPinyin = findViewById(R.id.tvPinyin);
        tvTranslate = findViewById(R.id.tvTranslate);
        tvRating = findViewById(R.id.tvRating);

        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        rnd = new Random();

        /*sPref.edit()
                .putInt("totalCount", 5)
                .putInt("0",0)
                .putString("0H","1H")
                .putString("0P","1P")
                .putString("0T","1T")
                .putInt("1",0)
                .putString("1H","2H")
                .putString("1P","2P")
                .putString("1T","2T")
                .putInt("2",0)
                .putString("2H","3H")
                .putString("2P","3P")
                .putString("2T","3T")
                .putInt("3",0)
                .putString("3H","4H")
                .putString("3P","4P")
                .putString("3T","4T")
                .putInt("4",0)
                .putString("4H","5H")
                .putString("4P","5P")
                .putString("4T","5T")
                .apply();*/

        loadNext();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }
    public void menu_Edit(MenuItem item){
        startActivity(new Intent(this, EditActivity.class));
    }

    private void loadNext(){
        int count = sPref.getInt("totalCount", 0);
        if (count == 0)
            return;
        int id1 = rnd.nextInt(count); int val1 = sPref.getInt(Integer.toString(id1),-1);
        int id2 = rnd.nextInt(count); int val2 = sPref.getInt(Integer.toString(id2),-1);
        int id3 = rnd.nextInt(count); int val3 = sPref.getInt(Integer.toString(id3),-1);
        int id4 = rnd.nextInt(count); int val4 = sPref.getInt(Integer.toString(id4),-1);
        int id5 = rnd.nextInt(count); int val5 = sPref.getInt(Integer.toString(id5),-1);
        currentID = id1;

        if (val1>val2)
            currentID = id2;
        if (val2>val3)
            currentID = id3;
        if (val3>val4)
            currentID = id4;
        if (val4>val5)
            currentID = id5;

        //tvTranslate.setText(Integer.toString(currentID));

        tvHieroglyph.setText(sPref.getString(Integer.toString(currentID) + "H", ""));
        tvPinyin.setText(sPref.getString(Integer.toString(currentID) + "P", ""));
        tvTranslate.setText(sPref.getString(Integer.toString(currentID) + "T", ""));
        tvRating.setText(getString(R.string.tvRating) + Integer.toString(sPref.getInt(Integer.toString(currentID),-1)) + "   ");
        tvTranslate.setVisibility(View.INVISIBLE);
    }

    public void onClick_tvHieroglyph(View view){
        tvTranslate.setVisibility(View.VISIBLE);
    }
    public void onClick_bYes(View view){
        int curVal = sPref.getInt(Integer.toString(currentID), -1);
        if (curVal > 99)
            curVal = 99;
        sPref.edit()
                .putInt(Integer.toString(currentID),curVal+1)
                .apply();

        loadNext();
    }
    public void onClick_bNo(View view){
        int curVal = sPref.getInt(Integer.toString(currentID), -1);
        if (curVal < 0)
            curVal = 0;
        sPref.edit()
                .putInt(Integer.toString(currentID),curVal-1)
                .apply();

        loadNext();
    }
}
