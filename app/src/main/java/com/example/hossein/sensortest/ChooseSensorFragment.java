package com.example.hossein.sensortest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseSensorFragment extends Fragment implements View.OnClickListener {

    public static ChooseSensorFragment newInstance() {

        Bundle args = new Bundle();

        ChooseSensorFragment fragment = new ChooseSensorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ChooseSensorFragment() {
        // Required empty public constructor
    }

    private Button mButtonFingerPrint;
    private Button mButtonAccelerator;
    private Button mButtonGyroscope;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_sensor, container, false);

        mButtonFingerPrint = view.findViewById(R.id.btn_finger_print);
        mButtonAccelerator = view.findViewById(R.id.btn_accelarator);
        mButtonGyroscope = view.findViewById(R.id.btn_gyroscope);

        mButtonFingerPrint.setOnClickListener(this);
        mButtonGyroscope.setOnClickListener(this);
        mButtonAccelerator.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_finger_print :{
                Intent intent = SensorTestActivity.newIntent(getActivity() , SensorTestActivity.FINGER_PRINT_TYPE);
                startActivity(intent);
                break;
            }case R.id.btn_gyroscope :{
                Intent intent = SensorTestActivity.newIntent(getActivity() , SensorTestActivity.GYROSCOPE_TYPE);
                startActivity(intent);
                break;
            }case R.id.btn_accelarator :{
                Intent intent = SensorTestActivity.newIntent(getActivity() , SensorTestActivity.ACCLERATOR_TYPE);
                startActivity(intent);
                break;
            }
        }
    }
}
