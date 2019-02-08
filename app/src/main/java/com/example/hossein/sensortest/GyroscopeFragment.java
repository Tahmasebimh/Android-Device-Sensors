package com.example.hossein.sensortest;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class GyroscopeFragment extends Fragment implements SensorEventListener {


    private final String DEBUG_TAG = "<<<<<<event>>>>>";

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
        mSensorProximetry = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this , mSensorProximetry , SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this , mSensorGyroscope , SensorManager.SENSOR_DELAY_NORMAL);

        if(mSensorProximetry == null){
            //Log.e("<><><><>", "Proximity sensor not available.");
            getActivity().finish();
        }
        {

        }
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){

                        case (MotionEvent.ACTION_DOWN) :
                            Log.d(DEBUG_TAG,"Action was DOWN");
                            return true;
                        case (MotionEvent.ACTION_MOVE) :
                            Log.d(DEBUG_TAG,"Action was MOVE");
                            return true;
                        case (MotionEvent.ACTION_UP) :
                            Log.d(DEBUG_TAG,"Action was UP");
                            return true;
                        case (MotionEvent.ACTION_CANCEL) :
                            Log.d(DEBUG_TAG,"Action was CANCEL");
                            return true;
                        case (MotionEvent.ACTION_OUTSIDE) :
                            Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                                    "of current screen element");
                            return true;
                        default :
                            return false;
                }
            }
        });


        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if(sensor.getType() == Sensor.TYPE_PROXIMITY){
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
            }else {
                mFrameLayout.setBackgroundColor(Color.WHITE);
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

