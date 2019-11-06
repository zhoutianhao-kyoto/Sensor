package com.example.sensor;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;

import android.view.View;
import android.view.MenuItem;

import java.util.List;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mgr;
    private TextView txtList;
    private Sensor accSensor, proxSensor, gravitySensor, stepSensor, magSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mgr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        txtList = (TextView)findViewById(R.id.mainView);
        List<Sensor> sensorList = mgr.getSensorList(Sensor.TYPE_ALL);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Available sensors:\n");
        for(Sensor s: sensorList){
            strBuilder.append(s.getName()+"\n");
        }
        txtList.setVisibility(View.VISIBLE);
        txtList.setText(strBuilder);

        accSensor = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proxSensor = mgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        gravitySensor = mgr.getDefaultSensor(Sensor.TYPE_GRAVITY);
        stepSensor = mgr.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        magSensor = mgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mgr.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_UI);
        mgr.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mgr.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        mgr.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mgr.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mgr.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();

        TableRow accTable = findViewById(R.id.acc);
        TextView accX = (TextView) accTable.getChildAt(0);
        TextView accY = (TextView) accTable.getChildAt(1);
        TextView accZ = (TextView) accTable.getChildAt(2);

        TextView prox = (TextView) findViewById(R.id.proxView);

        TableRow gravTable = findViewById(R.id.gravity);
        TextView gravX = (TextView) gravTable.getChildAt(0);
        TextView gravY = (TextView) gravTable.getChildAt(1);
        TextView gravZ = (TextView) gravTable.getChildAt(2);

        TextView step = (TextView) findViewById(R.id.stepView);

        TableRow magTable = findViewById(R.id.mag);
        TextView magX = (TextView) magTable.getChildAt(0);
        TextView magY = (TextView) magTable.getChildAt(1);
        TextView magZ = (TextView) magTable.getChildAt(2);

        DecimalFormat df =new DecimalFormat("#0.00");

        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                accX.setText(df.format(event.values[0]));
                accY.setText(df.format(event.values[1]));
                accZ.setText(df.format(event.values[2]));
                break;
            case Sensor.TYPE_PROXIMITY:
                prox.setText(df.format(event.values[0]));
                break;
            case Sensor.TYPE_GRAVITY:
                gravX.setText(df.format(event.values[0]));
                gravY.setText(df.format(event.values[1]));
                gravZ.setText(df.format(event.values[2]));
                break;
            case Sensor.TYPE_STEP_COUNTER:
                step.setText(df.format(event.values[0]));
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magX.setText(df.format(event.values[0]));
                magY.setText(df.format(event.values[1]));
                magZ.setText(df.format(event.values[2]));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
