package com.lance.commu.friendlist;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.lance.commu.intro.R;  
import com.lance.commu.sqliteDB.DB_Handler;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FriendListActivity extends Activity {
	Button freind_list_button1;
	Button freind_list_button2;
	ConnectivityManager connect;
	String requestURL1; 
	//String id;
	int index =0;
	private static HashMap<String, String> contactList;
	String[] dB_Phone_List;
	//ArrayList<String> Phone_Book_List;
	String[] phone_Book_List = new String[1000];
	
	ArrayList<String> for_Sort_List1 = new ArrayList<String>();
	ArrayList<String> for_Sort_List2 = new ArrayList<String>();
	static String[] for_Sort_Array1 ;
	
	ArrayList<String> union_Phone_List_Number = new ArrayList<String>();
	ArrayList<String> union_Phone_List_Name = new ArrayList<String>();
	
	DB_Handler db_Handler;
	Cursor cursor = null;
	ArrayList<String> sqlite_Code = new ArrayList<String>();
	ArrayList<String> sqlite_Name = new ArrayList<String>();
	ArrayList<String> sqlite_Phone_Number = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_list);
		connect = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE); //인터넷이 연결되어있나 확인 
		freind_list_button1 = (Button)findViewById(R.id.friend_list_button1); //새로고침
		freind_list_button2 = (Button)findViewById(R.id.friend_list_button2); //기존목록
		
		requestURL1 = "http://121.157.84.63:8080/lance/androidFriendList.jsp";
		
		
		/*//내가 접속한 아이디값 넘겨받음 
		Intent intent = getIntent();
		id = intent.getStringExtra("id");*/
		
		//내 전화번호부에 있는 전화목록을 다 가져와
		getContactData();
				
		
		//
		try {
			db_Handler = DB_Handler.open(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("db open에 문제있어");
		}
		
		//새로고침 한 친구목록
		freind_list_button1.setOnClickListener(new View.OnClickListener() { 		
				@Override
				public void onClick(View v) {
					//DB에 있는 전화목록을 다 가져와 
					if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
							|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
							 ){
								new Networking1().execute();
								
							}
							
							else{
								Toast toast = Toast.makeText(FriendListActivity.this , "인터넷연결이 끊겼습니다", Toast.LENGTH_SHORT);
								toast.show();
							}		
					
				}		
			});//end onClick()
		
		//기존 친구목록
		freind_list_button2.setOnClickListener(new View.OnClickListener() { 		
			@Override
			public void onClick(View v) {
				
				//기존것 초기화 시켜 
				sqlite_Name.clear();
				sqlite_Code.clear();
				sqlite_Phone_Number.clear();
				
				cursor = db_Handler.selectAll();
				while(cursor.moveToNext()){
					sqlite_Code.add(cursor.getString(0));
					sqlite_Name.add(cursor.getString(1));
					sqlite_Phone_Number.add(cursor.getString(2));
				}
				cursor.close();
				
				Intent intent = new Intent(FriendListActivity.this,FriendList_MenuActivity.class);
				intent.putExtra("sqlite_Phone_Number", sqlite_Phone_Number);
				intent.putExtra("sqlite_Name", sqlite_Name);
				startActivity(intent);
				
			}		
		});//end onClick()
		
		
		/*db_Handler.insert("양봉수", "01022223333");
		db_Handler.insert("김봉수", "01044444444");
		db_Handler.insert("박봉수", "01055555555");
		db_Handler.insert("강봉수", "01066666666");
		
		
		
		cursor = db_Handler.selectAll();
		while(cursor.moveToNext()){
			sqlite_Code.add(cursor.getString(0));
			sqlite_Name.add(cursor.getString(1));
			sqlite_Phone_Number.add(cursor.getString(2));
		}
		cursor.close();
		
		//sqlite 데이터 모두 지우는 기능
		//db_Handler.removeData();	
		sqlite_Name.clear();
		sqlite_Code.clear();
		sqlite_Phone_Number.clear();
		
		for(int i=0;i<sqlite_Name.size();i++){
			System.out.println(sqlite_Code.get(i));
			System.out.println(sqlite_Name.get(i));
			System.out.println(sqlite_Phone_Number.get(i));
		}*/
		
	}//onCreate end 
	
	private void getContactData(){
		Cursor phoneCursor = null;
	     contactList = new HashMap<String,String>();
	     
	   try{
	          // 주소록이 저장된 URI
	        Uri uContactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	   
	       // 주소록의 이름과 전화번호의 열 이름
	       String strProjection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
	   
	       // 주소록을 얻기 위한 쿼리문을 날리고 커서를 리턴
	       phoneCursor = getContentResolver().query(uContactsUri, null, null, null, strProjection);
	       phoneCursor.moveToFirst();
	   
	       String name = "";
	       String phoneNumber = "";
	         

	        // 주소록의 이름
	        int nameColumn = phoneCursor.getColumnIndex(Phone.DISPLAY_NAME);
	        // 주소록의 전화번호
	        int phoneColumn = phoneCursor.getColumnIndex(Phone.NUMBER);
	           
	        while(!phoneCursor.isAfterLast()){
	            name = phoneCursor.getString(nameColumn);
	            phoneNumber = phoneCursor.getString(phoneColumn);
	            
	            // HashMap에 data 넣음 
	            contactList.put(phoneNumber, name);
	            phoneCursor.moveToNext();
	         }
	     }
	     catch(Exception e){
	         e.printStackTrace();
	     }
	     finally{
	        if(phoneCursor != null){
	           phoneCursor.close();
	           phoneCursor = null;
	           
	           //Phone_Book_List = new ArrayList<String>();
	           //System.out.println(contactList);
	           for(Map.Entry<String, String> s : contactList.entrySet()){
	        	   String phone_number = s.getKey();
	        	   phone_number = phone_number.replaceAll("-", "");
	        	   String name = s.getValue();
	        	   //System.out.println("one = "+phone_number +"," +"two = " + name );
	        	   
	        	   phone_Book_List[index++] = phone_number;
	        	   phone_Book_List[index++] = name;
	        	 // Phone_Book_List.add(index++, phone_number);
	        	 // Phone_Book_List.add(index++, name);
	           }
	           
	         //  for(int i=0; i<index;i++)
	        //	   System.out.println(Phone_Book_List[i]);
	        }
	     }
	}

	private class Networking1 extends AsyncTask<URL, Integer, String>{

		@Override
		protected void onPreExecute() { 
			//doInBackground 가 수행되기 전, 즉 AsyncTask.execute()	를 호출했을때 가정 먼저 수행되는 녀석 
			//보통 초기화 작업을 해준다
			super.onPreExecute();	
		}
		
		@Override
		protected String doInBackground(URL... params) {
			//별개의 스레드에서 작업을 실행함 
			//AsyncTask.execute(params)를 통해서 수행이 됌 
			String result =null;
			
			try {

					result = sendData1(requestURL1);
					
			} catch (ClientProtocolException e) {
				
				e.printStackTrace();
				System.out.println("clientprotocol exception");
			} catch (IOException e) {
				
				e.printStackTrace();
				System.out.println("io excption");
			}

			onCancelled();
			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			//doInBackground에서 일처리를 하다가 중간에 UI 스레드에서 처리할 일이 있는 경우 호출됨 
			//doInBackground에서 수행되는 내용은 UI 스레드에서 작동함 
			//doInBackground에서 명시적으로 publishProgress( progress ) 를 호출 해주었을때 수행 
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			//doInBackground의 처리가 모두 끝나고 해당 메소드가 return 되었을때 그 리턴값이 여기로 오면서 이 메소드가 실행됨
			//AsyncTask가 취소되거나 예외 발생시에 parameter 로 null 이 들어옴
			//이 onPostExecute 의 내용은 UIThread에서 수행됨
			super.onPostExecute(result);
				
			//System.out.println(result);
			
			//네트워킹을 새롭게 긁을때마다 기존 sqlite부분을 모두 지워야돼.(새로고침)
			db_Handler.removeData();	
			sqlite_Name.clear();
			sqlite_Code.clear();
			sqlite_Phone_Number.clear();
			
			
			dB_Phone_List = result.split(",");
			
			dB_Phone_List[0] = dB_Phone_List[0].substring(25, 38);
			dB_Phone_List[dB_Phone_List.length-1] = dB_Phone_List[dB_Phone_List.length-1].substring(1,14);
			  
			for(int i=0;i<dB_Phone_List.length;i++){
				dB_Phone_List[i] = dB_Phone_List[i].trim();
				dB_Phone_List[i] = dB_Phone_List[i].replaceAll("-", "");
				//System.out.println(DB_Phone_List[i]);
			}
			
			
			//둘이 하나씩 비교해서 일치하는것들만 추려내서
			for(int i=0;i<index;i++){
				for(int j=0; j<dB_Phone_List.length; j++){
					if(phone_Book_List[i].equals(dB_Phone_List[j])){
						
						//일치하는 것들을 모아서 오름차순 정렬시켜 
						for_Sort_List1.add(phone_Book_List[i+1]+","+phone_Book_List[i]);
						
						////////////////////////기존////////////////////
						//union_Phone_List_Number.add(phone_Book_List[i]); 
						//union_Phone_List_Name.add(phone_Book_List[i+1]); 
						//System.out.println(Phone_Book_List[i]); //폰번호 
						//System.out.println(Phone_Book_List[i+1]); //이름
					}
				}
			}
			Collections.sort(for_Sort_List1);
			//정렬된것을 , 로 구분지어서 for_Sort_List2에 담아 
			for(int i=0; i<for_Sort_List1.size();i++){
				for_Sort_Array1 = for_Sort_List1.get(i).split(",");
			
				for_Sort_List2.add(for_Sort_Array1[0]);
				for_Sort_List2.add(for_Sort_Array1[1]);
			}
			
			//올바르게 저장되었나 확인 
			/*for(int i=0; i<for_Sort_List1.size();i++)
				System.out.println(for_Sort_List1.get(i));
			System.out.println("/////////////////////////////");
			System.out.println("/////////////////////////////");
			for(int i=0; i<for_Sort_List2.size();i++)
				System.out.println(for_Sort_List2.get(i));*/
			
			for(int i=0; i<for_Sort_List2.size();i+=2){
				union_Phone_List_Name.add(for_Sort_List2.get(i));
				union_Phone_List_Number.add(for_Sort_List2.get(i+1));
			
			}
			//System.out.println("여기까지는 정상");	
			
			//union이름과 번호를 sqlite에 옮겨서 저장하기 
			for(int i=0; i<union_Phone_List_Name.size();i++){
				db_Handler.insert(union_Phone_List_Name.get(i), union_Phone_List_Number.get(i));
			}
			
			//sqlite에 저장된 이름,전번을 Listview와 연결시키기 
			cursor = db_Handler.selectAll();
			while(cursor.moveToNext()){
				sqlite_Code.add(cursor.getString(0));
				sqlite_Name.add(cursor.getString(1));
				sqlite_Phone_Number.add(cursor.getString(2));
			}
			cursor.close();
			
			/*//sqlite db에 제대로 저장되어있나 확인용
			for(int i=0;i<sqlite_Name.size();i++){
				System.out.println(sqlite_Code.get(i));
				System.out.println(sqlite_Name.get(i));
				System.out.println(sqlite_Phone_Number.get(i));
			}*/
			
			/*//배열값 전달
			Intent intent = new Intent(FriendListActivity.this,FriendList_MenuActivity.class);
			intent.putExtra("sqlite_Phone_Number", sqlite_Phone_Number);
			intent.putExtra("sqlite_Name", sqlite_Name);
			startActivity(intent);*/
			
			Toast toast = Toast.makeText(FriendListActivity.this , "새로고침되었습니다.", Toast.LENGTH_SHORT);
			toast.show();
			//for(int i=0; i<union_Phone_List.size();i++)
			//	System.out.println(union_Phone_List.get(i));
		}
		
		
		@Override
		protected void onCancelled() {
			//AsyncTask를 강제로 취소했을때 호출
			//껐을때 다시 초기화
			for_Sort_List1.clear();
			for_Sort_List2.clear();
			union_Phone_List_Name.clear();
			union_Phone_List_Number.clear();
			super.onCancelled();
		}
	}
	
	private String sendData1(String requestURL1) throws ClientProtocolException, IOException{
		
		HttpPost request = makeHttpPost1(requestURL1);
		HttpClient client = new DefaultHttpClient();
		ResponseHandler<String> reshandler = new BasicResponseHandler();
		String result = client.execute(request, reshandler);
		return result;
	}
		
	private HttpPost makeHttpPost1(String requestURL1){
		
		HttpPost request = new HttpPost(requestURL1);
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
    	request.setEntity(makeEntity1(dataList));
    	return request;
	}
	
	private HttpEntity makeEntity1(List<NameValuePair> dataList){
		
		HttpEntity result = null;
		
		try{
			result = new UrlEncodedFormEntity(dataList,"UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
			System.out.println("언서포티드인코딩 exception");
		}
		return result;
	}
	
}
