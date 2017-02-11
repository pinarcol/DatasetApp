package com.example.pinarcol.accelometersimulator;

/**
 * Created by pinarcol on 11.2.2017.
 */

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AccelometerActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    float gravity[] = new float[3];
    float linear_acceleration[] = new float[3];
    float timestamp = System.nanoTime();
    float timestampOld = System.nanoTime();
    float timeConstant = 0.18f;
    float alpha = 0.9f;
    float dt = 0;
    int count = 0;

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelometer);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void onSensorChanged(SensorEvent event)
    {
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate
        timestamp = System.nanoTime();
        dt = 1 / (count / ((timestamp - timestampOld) / 1000000000.0f));
        count++;
        alpha = timeConstant / (timeConstant + dt);

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        TextView tx=new TextView(this);
        TextView ty=new TextView(this);
        TextView tz=new TextView(this);

        tx=(TextView)findViewById(R.id.accx);
        tx.setText(String.format("Acc in x: " + Float.toString(linear_acceleration[0])));

        ty=(TextView)findViewById(R.id.accy);
        ty.setText(String.format("Acc in y: " + Float.toString(linear_acceleration[1])));

        tz=(TextView)findViewById(R.id.accz);
        tz.setText(String.format("Acc in z: " + Float.toString(linear_acceleration[2])));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

