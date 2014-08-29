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
		connect = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE); //���ͳ��� ����Ǿ��ֳ� Ȯ�� 
		freind_list_button1 = (Button)findViewById(R.id.friend_list_button1); //���ΰ�ħ
		freind_list_button2 = (Button)findViewById(R.id.friend_list_button2); //�������
		
		requestURL1 = "http://121.157.84.63:8080/lance/androidFriendList.jsp";
		
		
		/*//���� ������ ���̵� �Ѱܹ��� 
		Intent intent = getIntent();
		id = intent.getStringExtra("id");*/
		
		//�� ��ȭ��ȣ�ο� �ִ� ��ȭ����� �� ������
		getContactData();
				
		
		//
		try {
			db_Handler = DB_Handler.open(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("db open�� �����־�");
		}
		
		//���ΰ�ħ �� ģ�����
		freind_list_button1.setOnClickListener(new View.OnClickListener() { 		
				@Override
				public void onClick(View v) {
					//DB�� �ִ� ��ȭ����� �� ������ 
					if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
							|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
							 ){
								new Networking1().execute();
								
							}
							
							else{
								Toast toast = Toast.makeText(FriendListActivity.this , "���ͳݿ����� ������ϴ�", Toast.LENGTH_SHORT);
								toast.show();
							}		
					
				}		
			});//end onClick()
		
		//���� ģ�����
		freind_list_button2.setOnClickListener(new View.OnClickListener() { 		
			@Override
			public void onClick(View v) {
				
				//������ �ʱ�ȭ ���� 
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
		
		
		/*db_Handler.insert("�����", "01022223333");
		db_Handler.insert("�����", "01044444444");
		db_Handler.insert("�ں���", "01055555555");
		db_Handler.insert("������", "01066666666");
		
		
		
		cursor = db_Handler.selectAll();
		while(cursor.moveToNext()){
			sqlite_Code.add(cursor.getString(0));
			sqlite_Name.add(cursor.getString(1));
			sqlite_Phone_Number.add(cursor.getString(2));
		}
		cursor.close();
		
		//sqlite ������ ��� ����� ���
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
	          // �ּҷ��� ����� URI
	        Uri uContactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	   
	       // �ּҷ��� �̸��� ��ȭ��ȣ�� �� �̸�
	       String strProjection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
	   
	       // �ּҷ��� ��� ���� �������� ������ Ŀ���� ����
	       phoneCursor = getContentResolver().query(uContactsUri, null, null, null, strProjection);
	       phoneCursor.moveToFirst();
	   
	       String name = "";
	       String phoneNumber = "";
	         

	        // �ּҷ��� �̸�
	        int nameColumn = phoneCursor.getColumnIndex(Phone.DISPLAY_NAME);
	        // �ּҷ��� ��ȭ��ȣ
	        int phoneColumn = phoneCursor.getColumnIndex(Phone.NUMBER);
	           
	        while(!phoneCursor.isAfterLast()){
	            name = phoneCursor.getString(nameColumn);
	            phoneNumber = phoneCursor.getString(phoneColumn);
	            
	            // HashMap�� data ���� 
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
			//doInBackground �� ����Ǳ� ��, �� AsyncTask.execute()	�� ȣ�������� ���� ���� ����Ǵ� �༮ 
			//���� �ʱ�ȭ �۾��� ���ش�
			super.onPreExecute();	
		}
		
		@Override
		protected String doInBackground(URL... params) {
			//������ �����忡�� �۾��� ������ 
			//AsyncTask.execute(params)�� ���ؼ� ������ �� 
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
			//doInBackground���� ��ó���� �ϴٰ� �߰��� UI �����忡�� ó���� ���� �ִ� ��� ȣ��� 
			//doInBackground���� ����Ǵ� ������ UI �����忡�� �۵��� 
			//doInBackground���� ��������� publishProgress( progress ) �� ȣ�� ���־����� ���� 
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			//doInBackground�� ó���� ��� ������ �ش� �޼ҵ尡 return �Ǿ����� �� ���ϰ��� ����� ���鼭 �� �޼ҵ尡 �����
			//AsyncTask�� ��ҵǰų� ���� �߻��ÿ� parameter �� null �� ����
			//�� onPostExecute �� ������ UIThread���� �����
			super.onPostExecute(result);
				
			//System.out.println(result);
			
			//��Ʈ��ŷ�� ���Ӱ� ���������� ���� sqlite�κ��� ��� �����ߵ�.(���ΰ�ħ)
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
			
			
			//���� �ϳ��� ���ؼ� ��ġ�ϴ°͵鸸 �߷�����
			for(int i=0;i<index;i++){
				for(int j=0; j<dB_Phone_List.length; j++){
					if(phone_Book_List[i].equals(dB_Phone_List[j])){
						
						//��ġ�ϴ� �͵��� ��Ƽ� �������� ���Ľ��� 
						for_Sort_List1.add(phone_Book_List[i+1]+","+phone_Book_List[i]);
						
						////////////////////////����////////////////////
						//union_Phone_List_Number.add(phone_Book_List[i]); 
						//union_Phone_List_Name.add(phone_Book_List[i+1]); 
						//System.out.println(Phone_Book_List[i]); //����ȣ 
						//System.out.println(Phone_Book_List[i+1]); //�̸�
					}
				}
			}
			Collections.sort(for_Sort_List1);
			//���ĵȰ��� , �� ������� for_Sort_List2�� ��� 
			for(int i=0; i<for_Sort_List1.size();i++){
				for_Sort_Array1 = for_Sort_List1.get(i).split(",");
			
				for_Sort_List2.add(for_Sort_Array1[0]);
				for_Sort_List2.add(for_Sort_Array1[1]);
			}
			
			//�ùٸ��� ����Ǿ��� Ȯ�� 
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
			//System.out.println("��������� ����");	
			
			//union�̸��� ��ȣ�� sqlite�� �Űܼ� �����ϱ� 
			for(int i=0; i<union_Phone_List_Name.size();i++){
				db_Handler.insert(union_Phone_List_Name.get(i), union_Phone_List_Number.get(i));
			}
			
			//sqlite�� ����� �̸�,������ Listview�� �����Ű�� 
			cursor = db_Handler.selectAll();
			while(cursor.moveToNext()){
				sqlite_Code.add(cursor.getString(0));
				sqlite_Name.add(cursor.getString(1));
				sqlite_Phone_Number.add(cursor.getString(2));
			}
			cursor.close();
			
			/*//sqlite db�� ����� ����Ǿ��ֳ� Ȯ�ο�
			for(int i=0;i<sqlite_Name.size();i++){
				System.out.println(sqlite_Code.get(i));
				System.out.println(sqlite_Name.get(i));
				System.out.println(sqlite_Phone_Number.get(i));
			}*/
			
			/*//�迭�� ����
			Intent intent = new Intent(FriendListActivity.this,FriendList_MenuActivity.class);
			intent.putExtra("sqlite_Phone_Number", sqlite_Phone_Number);
			intent.putExtra("sqlite_Name", sqlite_Name);
			startActivity(intent);*/
			
			Toast toast = Toast.makeText(FriendListActivity.this , "���ΰ�ħ�Ǿ����ϴ�.", Toast.LENGTH_SHORT);
			toast.show();
			//for(int i=0; i<union_Phone_List.size();i++)
			//	System.out.println(union_Phone_List.get(i));
		}
		
		
		@Override
		protected void onCancelled() {
			//AsyncTask�� ������ ��������� ȣ��
			//������ �ٽ� �ʱ�ȭ
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
			System.out.println("����Ƽ�����ڵ� exception");
		}
		return result;
	}
	
}
