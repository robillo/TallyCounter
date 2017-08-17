package com.robillo.tallycounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MyTallyCounter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter = (MyTallyCounter) findViewById(R.id.my_tally_counter);
        (findViewById(R.id.increment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.increment();
            }
        });
    }
}
