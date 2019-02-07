package com.example.hossein.sensortest;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentsActivity {

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
}
