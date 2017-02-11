package com.example.pinarcol.accelometersimulator;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button accButton = (Button) findViewById(R.id.accelometer);
        accButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AccelometerActivity.class);
                startActivity(intent);
            }
        });

        final Button gyrButton = (Button) findViewById(R.id.gyroscope);
        gyrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), GyroscopeActivity.class);
                startActivity(intent);
            }
        });
    }
}

