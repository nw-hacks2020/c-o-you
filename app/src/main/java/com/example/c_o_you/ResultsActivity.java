package com.example.c_o_you;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsActivity extends AppCompatActivity {

    @BindView(R.id.results_list)
    ListView resultsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ButterKnife.bind(this);

        ArrayList<String> blocks = getIntent().getExtras().getStringArrayList("BLOCKS");
//        Toast.makeText(this, Arrays.toString(blocks.toArray()), Toast.LENGTH_LONG).show();

        List<ResultItem> results = new ArrayList<>();
        for (String block: blocks) {
            results.add(new ResultItem(block));
        }

        ResultItemAdapter resultItemAdapter = new ResultItemAdapter(getApplicationContext(), results);
        resultsListView.setAdapter(resultItemAdapter);
    }
}
