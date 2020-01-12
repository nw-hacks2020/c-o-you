package com.example.c_o_you;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ArrayList<String> blocks = getIntent().getExtras().getStringArrayList("BLOCKS");
        Toast.makeText(this, Arrays.toString(blocks.toArray()), Toast.LENGTH_LONG).show();
    }
}
