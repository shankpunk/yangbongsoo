package com.example.slidingsimplesample;
 
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
 
public class Fragment_Set extends Fragment{
	CheckBox checkBox1;
	CheckBox checkBox2;
	CheckBox checkBox3;
	
	boolean check1;
	boolean check2;
	boolean check3;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set, container, false);
        checkBox1 = (CheckBox)v.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox)v.findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox)v.findViewById(R.id.checkbox3);
        
    	checkBox1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkBox1.isChecked() == true)
					check1 = true;
				else
					check1 = false;
				savePreferences();
			}
		});

		checkBox2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkBox2.isChecked() == true)
					check2 = true;
				else
					check2 = false;
				savePreferences();
			}
		});

		checkBox3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkBox3.isChecked() == true)
					check3 = true;
				else
					check3 = false;
				savePreferences();
			}
		});

		getPreferences();
		
        return v;
    }
    
	// 값 불러오기
	private void getPreferences() {
		SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

		check1 = pref.getBoolean("check1", false);
		checkBox1.setChecked(check1);

		check2 = pref.getBoolean("check2", false);
		checkBox2.setChecked(check2);

		check3 = pref.getBoolean("check3", false);
		checkBox3.setChecked(check3);

	}

	// 값 저장하기
	private void savePreferences() {
		SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putBoolean("check1", check1);
		editor.putBoolean("check2", check2);
		editor.putBoolean("check3", check3);

		editor.commit();
	}


	// 값(Key Data) 삭제하기
	private void removePreferences() {
		SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove("hi");
		editor.commit();
	}

	// 값(ALL Data) 삭제하기
	private void removeAllPreferences() {
		SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}


}