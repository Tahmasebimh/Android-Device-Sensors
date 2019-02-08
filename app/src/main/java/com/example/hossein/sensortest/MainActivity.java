package com.example.hossein.sensortest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends SingleFragmentsActivity {

    private static final String DEBUG_TAG = ">>>>debug<<<<,";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected int getContainer() {
        return R.id.frm_sensor_container;
    }

    @Override
    public Fragment getFragment() {
        return ChooseSensorFragment.newInstance();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
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
                return super.onTouchEvent(event);
        }
    }
}
