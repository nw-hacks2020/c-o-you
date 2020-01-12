package com.example.c_o_you;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

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
//                String[] ingredients = FDC.getIngredients("vanilla ice cream");
//                System.out.println("INGREDIentSSS");
//                System.out.println(Arrays.toString(ingredients));
//                System.out.println("Apple Juice");
//                System.out.println(NLP.getIngredients("Apple Juice"));
//                System.out.println("Orange Juice");
//                System.out.println(NLP.getIngredients("Orange Juice"));
//                System.out.println("Coffee");
//                System.out.println(NLP.getIngredients("Coffee"));
//                System.out.println("Soda");
//                System.out.println(NLP.getIngredients("Soda"));
//                System.out.println("Mineral Water");
//                System.out.println(NLP.getIngredients("Mineral Water"));



            } catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
