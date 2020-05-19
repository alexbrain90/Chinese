package com.example.chinese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public void onClick_bLoad(View view){
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/chinese");
        File sdFile = new File(sdPath, "database.txt");
        int count = 0;
        try {
            sPref.edit().clear().apply();
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";
            while ((str = br.readLine()) != null){
                String[] substr = str.split(";");
                if (substr.length != 3)
                    continue;

                sPref.edit()
                        .putInt(Integer.toString(count), 0)
                        .putString(Integer.toString(count)+"H", substr[0])
                        .putString(Integer.toString(count)+"P", substr[1])
                        .putString(Integer.toString(count)+"T", substr[2])
                        .putInt("totalCount", count+1)
                        .apply();
                count++;
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
