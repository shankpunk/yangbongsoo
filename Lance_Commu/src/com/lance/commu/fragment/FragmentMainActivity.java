package com.lance.commu.fragment;


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

import com.example.slidingsimplesample.R;
import com.lance.commu.friendlist.FriendListActivity;
import com.lance.commu.sqliteDB.DB_Handler;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class FragmentMainActivity extends BaseActivity {
	ConnectivityManager connect; 
	String requestURL1; 	
	int index =0;
	private static HashMap<String, String> contactList; //내 전화번호부안의 이름,번호 저장됨
	String[] dB_Phone_List; //네트워크 통신을 통해 Oracle DB에서 얻어진 번호들 저장
	String[] phone_Book_List = new String[1000]; //내 전화번호부안의 이름,번호 저장됨
	
	//친구목록 리스트뷰에 오름차순으로 정렬하기 위해 거치는 중간단계 
	ArrayList<String> for_Sort_List1 = new ArrayList<String>();
	ArrayList<String> for_Sort_List2 = new ArrayList<String>();
	static String[] for_Sort_Array1 ;
	
	//최종적으로 Oracle DB와 내 전화번호부와 일치하는 번호,이름 저장
	ArrayList<String> union_Phone_List_Number = new ArrayList<String>();
	ArrayList<String> union_Phone_List_Name = new ArrayList<String>();
	
	//Sqlite DB를 쓰기위한 설정
	DB_Handler db_Handler;
	Cursor cursor = null;
	ArrayList<String> sqlite_Code = new ArrayList<String>();
	ArrayList<String> sqlite_Name = new ArrayList<String>();
	ArrayList<String> sqlite_Phone_Number = new ArrayList<String>();
	
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
  
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.logo);
		actionBar.setTitle("Commu");
		setContentView(R.layout.fragment_main);
		fragmentReplace(0);
		
		connect = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE); //인터넷이 연결되어있나 확인
		requestURL1 = "http://121.157.84.63:8080/lance/androidFriendList.jsp"; //네트워크 통신을 통해 갈 주소
		
		//내 전화번호부에 있는 전화목록을 다 가져오게 하는 함수
		//내 전화번호부와 OracleDB의 전화번호부와 비교를 할거야 
		//그래서 일치하는것만 추려서 ListView에 나타내게 할거야 
		getContactData();
		
		//Sqlite DB 쓰기위해 오픈
		//Oracle DB에서 가져온 내용을 Sqlite에 저장할거야 
		//그래서 매번 네트워크 통신을 통해서 Oracle DB결과를 얻는게 아니라 
		//Sqlite의 내용을 바로 가져와 쓰는 형식
		//처음과 새로고침 누를때만 네트워크 통신을 통해 Oracle DB를 다시 Sqlite에 저장
		try {
			db_Handler = DB_Handler.open(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("db open에 문제있어");
		}
		
		//인터넷이 연결되어 있는지 체크하고 연결되었다면 네트워킹을 통해 
		//Oracle DB결과를 얻어올거야 
		if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
				|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
				 ){
					new Networking1().execute();
					
				}
				
				else{
					Toast toast = Toast.makeText(FragmentMainActivity.this , "인터넷연결이 끊겼습니다", Toast.LENGTH_SHORT);
					toast.show();
				}	
		

	} //Oncreate end 
	
	//내 전화번호부의 이름과 번호를 가져오게 하는함수 
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
	           
	           for(Map.Entry<String, String> s : contactList.entrySet()){
	        	   String phone_number = s.getKey();
	        	   phone_number = phone_number.replaceAll("-", "");
	        	   String name = s.getValue();
	        	   //최종적으로 번호와 이름을 집어넣음
	        	   phone_Book_List[index++] = phone_number;
	        	   phone_Book_List[index++] = name;
	           }
	           
	        }
	     }
	}// getContactData end
	
	//AsyncTask를 써서 네트워킹 작업 
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
					//Oracle DB통해 얻어진 결과가 result에 담겨진다
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
			
			//Oracle DB내용이 담긴 result를 파싱해서 dB_Phone_List에 담는다
			dB_Phone_List = result.split(",");
			dB_Phone_List[0] = dB_Phone_List[0].substring(25, 38);
			dB_Phone_List[dB_Phone_List.length-1] = dB_Phone_List[dB_Phone_List.length-1].substring(1,14);
			  
			//핸펀 번호의 '-' 붙은것을 모두 제거 
			for(int i=0;i<dB_Phone_List.length;i++){
				dB_Phone_List[i] = dB_Phone_List[i].trim();
				dB_Phone_List[i] = dB_Phone_List[i].replaceAll("-", "");

			}
			
			//OracleDB번호와 내 전화번호부의 번호와 하나씩 비교해서 일치하는것들만 추려낸다
			for(int i=0;i<index;i++){
				for(int j=0; j<dB_Phone_List.length; j++){
					if(phone_Book_List[i].equals(dB_Phone_List[j])){
						
						//일치하는 것들을 모아서 오름차순 정렬을 위해 
						//for_Sort_List1에 이름과 번호를 같이 옮겨담는다
						//(이름과 번호가 한꺼번에 정렬되게 하기위해서)
						for_Sort_List1.add(phone_Book_List[i+1]+","+phone_Book_List[i]);
						
					}
				}
			}
			//정렬작업
			Collections.sort(for_Sort_List1);
			
			//정렬된것을 , 로 구분지어서 for_Sort_List2에 담는다 (이름따로 번호따로)
			for(int i=0; i<for_Sort_List1.size();i++){
				for_Sort_Array1 = for_Sort_List1.get(i).split(",");
			
				for_Sort_List2.add(for_Sort_Array1[0]);
				for_Sort_List2.add(for_Sort_Array1[1]);
			}
					
			//최종적으로 산출된 결과를 저장한다. 
			for(int i=0; i<for_Sort_List2.size();i+=2){
				union_Phone_List_Name.add(for_Sort_List2.get(i));
				union_Phone_List_Number.add(for_Sort_List2.get(i+1));
			
			}
		
			//네트워크 통신을 통해 얻은 결과를 Sqlite에 저장한다
			//최종 산출된 이름과 번호를 sqlite에 옮겨서 저장한다
			for(int i=0; i<union_Phone_List_Name.size();i++){
				db_Handler.insert(union_Phone_List_Name.get(i), union_Phone_List_Number.get(i));
			}
			
			cursor = db_Handler.selectAll();
			while(cursor.moveToNext()){
				sqlite_Code.add(cursor.getString(0));
				sqlite_Name.add(cursor.getString(1));
				sqlite_Phone_Number.add(cursor.getString(2));
			}
			cursor.close();
			
			//Fragment_FriendList에 값 전달하기
			Fragment_FriendList fragment_FriendList = new Fragment_FriendList();
			Bundle arguments = new Bundle();
			arguments.putString("test", "성공");
			fragment_FriendList.setArguments(arguments);
			
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
	
	
} //class end