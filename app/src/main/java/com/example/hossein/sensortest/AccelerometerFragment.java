package com.example.hossein.sensortest;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccelerometerFragment extends Fragment implements SensorEventListener {


    private Vibrator mVibrator;

    public AccelerometerFragment() {
        // Required empty public constructor
    }

    public static AccelerometerFragment newInstance() {

        Bundle args = new Bundle();

        AccelerometerFragment fragment = new AccelerometerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private TextView mTextViewXAxis;
    private TextView mTextViewYAxis;
    private TextView mTextViewZAxis;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accelerometer, container, false);

        mTextViewXAxis = view.findViewById(R.id.tv_x);
        mTextViewYAxis = view.findViewById(R.id.tv_y);
        mTextViewZAxis = view.findViewById(R.id.tv_z);

        senSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this , senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);


        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float xAxis = event.values[0];
            float yAxis = event.values[1];
            float zAxis = event.values[2];
            String x = String.format( "%.2f", xAxis);
            String y = String.format( "%.2f", yAxis);
            String z = String.format( "%.2f", zAxis);

            mTextViewXAxis.setText(x + "");
            mTextViewYAxis.setText(y + "");
            mTextViewZAxis.setText(z + "");
            if(xAxis > 5){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mVibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
