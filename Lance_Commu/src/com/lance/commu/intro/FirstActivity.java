package com.lance.commu.intro;

import com.lance.commu.intro.R;  
import com.lance.commu.fragment.FragmentMainActivity;
import com.lance.commu.friendlist.FriendListActivity;
import com.lance.commu.login.LoginActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

public class FirstActivity extends Activity {
	SharedPreferences sp;
	String id;
	String pw;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.logo);
		actionBar.setTitle("Commu");
		
		//splash Activity ����
		Handler handler = new Handler();
		handler.postDelayed(new splashHandler(), 1000);
		
		sp = getSharedPreferences("PreName", MODE_PRIVATE);
		id = sp.getString("sp_id", "1"); //�ʱⰪ 1�� ��
		pw = sp.getString("sp_pw", "1"); //�ʱⰪ 1�� ��
		
		/*System.out.println("//////////////");
		System.out.println(id);
		System.out.println(pw);
		System.out.println("//////////////");
		
		if(id == "1")
			System.out.println("id is  null");
		
		if(pw == "1")
			System.out.println("pw is null");*/
	}
	
	class splashHandler implements Runnable{
		
		@Override
		public void run(){
			//���� �α��ν� 
			if(id == "1" && pw == "1"){
				startActivity(new Intent(FirstActivity.this,LoginActivity.class));
				finish();
			}
			else{
				//�ι�°���� �ڵ��α��� 
				startActivity(new Intent(FirstActivity.this,FragmentMainActivity.class));
				finish();
			}
		}
	}
	
	//�ε����϶��� ��Ű ����
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		 switch (keyCode) {
		     case KeyEvent.KEYCODE_BACK:
		         return true;
		     }
		     return super.onKeyDown(keyCode, event);
	}
}
