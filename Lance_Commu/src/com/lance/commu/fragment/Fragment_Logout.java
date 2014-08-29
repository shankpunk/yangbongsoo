package com.lance.commu.fragment;
 
import com.example.slidingsimplesample.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
 
public class Fragment_Logout extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logout, container, false);
        
        
        //종료코드
        android.os.Process.killProcess(android.os.Process.myPid());
        
        return v;
    }
}