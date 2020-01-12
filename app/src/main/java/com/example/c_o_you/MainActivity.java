package com.example.c_o_you;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("HIIII");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {
                FDC.getIngredients("vanilla ice cream");
            } catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
