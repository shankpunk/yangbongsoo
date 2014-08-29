package com.lance.commu.friendlist;

import java.util.ArrayList;

import com.lance.commu.intro.R;  
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FriendList_MenuActivity extends Activity {
	
	ArrayList<String> sqlite_Phone_Number = new ArrayList<String>();
	ArrayList<String> sqlite_Name = new ArrayList<String>();
	int index=0;
	TextView textView;

	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		//배열 값 전달받음
		sqlite_Phone_Number = (ArrayList<String>)getIntent().getSerializableExtra("sqlite_Phone_Number");
		sqlite_Name = (ArrayList<String>)getIntent().getSerializableExtra("sqlite_Name");
		
		//for(int i=0; i<union_Phone_List.size();i++)
		//	System.out.println(union_Phone_List.get(i));
		
		
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, R.id.label, sqlite_Name);
		
	    ListView menuList = (ListView) findViewById(R.id.listViewMenu);
	    menuList.setOnItemClickListener(new MyOnItemClickListener());    
	    menuList.setAdapter(adapt);
	    
	    MyOnItemClickListener listViewExampleClickListener = new MyOnItemClickListener();
	    menuList.setOnItemClickListener(listViewExampleClickListener);
	}
	
	private final class MyOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
		public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id)
        {
        	//System.out.println("여기까지는 정상");
        	//TextView textView = (TextView)itemClicked;
        	//TextView textView = (TextView)findViewById(R.id.label);
        	textView = (TextView)findViewById(R.id.textView1);
        	textView.setText(sqlite_Name.get(position));
        	String strText = textView.getText().toString(); //친구목록의 해당 이름을 가져와 
        	
        	//Toast toast = Toast.makeText(FriendList_MenuActivity.this , strText, Toast.LENGTH_SHORT);
			//toast.show();
        	
        	//그 이름과 union_Phone_List_Name과 for문을 돌려서 찾고 
        	//찾은 순번에 맞는 union_Phone_List_Number를 체크 
        	
        	for(int i=0; i<sqlite_Name.size();i++){
        		
        		if(strText.equals(sqlite_Name.get(i))){
        			 
        			//Toast toast = Toast.makeText(FriendList_MenuActivity.this , union_Phone_List_Number.get(index), Toast.LENGTH_SHORT);
        			//toast.show();
        			break;
        		}
        		index++;
        	}
        	
        	//그 번호로 바로 전화를 걸게 시켜 
        	startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+sqlite_Phone_Number.get(index))));
        	index = 0;
        	
        }
    }
}
