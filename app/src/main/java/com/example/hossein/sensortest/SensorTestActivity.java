package com.example.hossein.sensortest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class SensorTestActivity extends SingleFragmentsActivity {

    private static String TAG_SENSOR_TYPE = "sensorType";
    public static final int GYROSCOPE_TYPE = 2;
    public static final int FINGER_PRINT_TYPE = 0;
    public static final int ACCLERATOR_TYPE = 1;

    public static Intent newIntent(Context context , int sensorType){
        Intent intent = new Intent(context , SensorTestActivity.class);
        intent.putExtra(TAG_SENSOR_TYPE , sensorType);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);
    }

    @Override
    protected int getContainer() {
        return R.id.container_finger;
    }

    @Override
    public Fragment getFragment() {
        if(getIntent().hasExtra(TAG_SENSOR_TYPE)){
            if (getIntent().getIntExtra(TAG_SENSOR_TYPE , -1) == FINGER_PRINT_TYPE){
                return FingerPrintFragment.newInstance();
            }else if(getIntent().getIntExtra(TAG_SENSOR_TYPE , -1) == ACCLERATOR_TYPE){
                return AccelerometerFragment.newInstance();
            }else if(getIntent().getIntExtra(TAG_SENSOR_TYPE , -1) == GYROSCOPE_TYPE){
                return GyroscopeFragment.newInstance();
            }
        }
        return null;
    }
}
