package com.lance.commu.fragment;
 

import com.example.slidingsimplesample.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_FriendList extends Fragment {

	String test1;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friendlist, container, false);
        
        
        Bundle bundle=getArguments();

       
        if(bundle !=null) {
        	test1 = bundle.getString("test");
        }
        
        System.out.println("bundle : "+bundle);
        System.out.println("°á°ú:"+test1);
        
       
        
        return v;
    }
}