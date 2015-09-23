package com.example.devon.scrollingexperiment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by Devon on 22/09/2015.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startExperiment = (Button) findViewById(R.id.startExperiment);
        startExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperimentData.getSubjectId();
                ExperimentData.setupTrials();
                Trial nextTrial = ExperimentData.getNextTrial();
                Intent i = new Intent(MainActivity.this, nextTrial.getActivity());
                i.putExtra("distance", nextTrial.getDistance());
                startActivity(i);
            }
        });
    }
}
