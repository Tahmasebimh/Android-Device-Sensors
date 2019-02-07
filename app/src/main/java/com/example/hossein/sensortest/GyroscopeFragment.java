package com.example.hossein.sensortest;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class GyroscopeFragment extends Fragment implements SensorEventListener {


    public static GyroscopeFragment newInstance() {

        Bundle args = new Bundle();

        GyroscopeFragment fragment = new GyroscopeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public GyroscopeFragment() {
        // Required empty public constructor
    }

    private SensorManager mSensorManager;
    private Sensor mSensorGyroscope;
    private Sensor mSensorProximetry;
    private FrameLayout mFrameLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gyroscope, container, false);
        mFrameLayout = view.findViewById(R.id.frm_gyro);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        //mSensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorProximetry = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
       // mSensorManager.registerListener(this , mSensorProximetry , 2 * 1000 * 1000);
        mSensorManager.registerListener(this , mSensorProximetry , SensorManager.SENSOR_DELAY_NORMAL);

        if(mSensorProximetry == null){
            Log.e("<><><><>", "Proximity sensor not available.");
            getActivity().finish();
        }

        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        Log.i("<><><><>" , "Sensor set");
        if(sensor.getType() == Sensor.TYPE_PROXIMITY){
            Toast.makeText(getActivity(), "Proxy", Toast.LENGTH_SHORT).show();
            if(sensorEvent.values[0] < mSensorProximetry.getMaximumRange()) {
                // Detected something nearby
                mFrameLayout.setBackgroundColor(Color.RED);
            } else {
                // Nothing is nearby
                mFrameLayout.setBackgroundColor(Color.GREEN);
            }
        }else if(sensor.getType() == Sensor.TYPE_GYROSCOPE){
            if(sensorEvent.values[2] > 0.5f) { // anticlockwise
                mFrameLayout.setBackgroundColor(Color.BLUE);
            } else if(sensorEvent.values[2] < -0.5f) { // clockwise
                mFrameLayout.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

