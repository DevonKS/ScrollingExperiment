package com.example.devon.scrollingexperiment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class ListViewNoFrictionActivity extends Activity {
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Intent i = getIntent();
        final int distance = i.getIntExtra("distance", -1);

        final int index = (int) (Math.random() * 1000);
        final String valueToFind;
        if (index > distance) {
            valueToFind = String.valueOf(index - distance);
        }
        else {
            valueToFind = String.valueOf(index + distance);
        }

        setTitle("Standard Without Friction | " + valueToFind);

        Button startButton = (Button) findViewById(R.id.startTrial);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long startTime = System.nanoTime();
                // Get ListView object from xml
                listView = (ListView) findViewById(R.id.list);
                listView.setFriction(ViewConfiguration.getScrollFriction() / 10000);

                List<String> values = new ArrayList<String>();
                for (Integer i = 0; i < 1000; i++) {
                    values.add(i.toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListViewNoFrictionActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, values);

                listView.setAdapter(adapter);

                listView.setSelection(index);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String  itemValue    = (String) listView.getItemAtPosition(position);
                        if (itemValue.equals(valueToFind)) {
                            long stopTime = System.nanoTime();
                            double duration = (stopTime - startTime) / 1000000000.0;
                            System.out.println("Duration: " + duration);
                            Trial currentTrial = ExperimentData.getCurrentTrial();
                            currentTrial.setDuration(duration);
                            ExperimentData.log();

                            Trial next = ExperimentData.getNextTrial();
                            Intent i;
                            if (next == null) {
                                i = new Intent(ListViewNoFrictionActivity.this, MainActivity.class);
                            }
                            else {
                                i = new Intent(ListViewNoFrictionActivity.this, next.getActivity());
                                i.putExtra("distance", next.getDistance());
                            }
                            startActivity(i);
                        }
                    }

                });
            }
        });

    }

}
