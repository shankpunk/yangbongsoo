package com.lance.commu.signup;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.lance.commu.intro.R;  
import com.lance.commu.login.LoginActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SignUpActivity extends Activity {

	EditText edit_id,edit_pass,edit_name,edit_phone_number;
	Button insert_button, idCheck_button;
	RadioButton rb_man,rb_woman;
	String id;
	String pass;
	String name;
	String phone_number; //���������� ��Ʈ��ũ ���� ������ ���ݹ�ȣ 
	String PhoneNumber; //�� ���ݹ�ȣ �ڵ����� ���ͼ� ����Ǵ� ����
	String gender;
	String idCheck;
	String requestURL1;
	String requestURL2;
	ConnectivityManager connect;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		  
		/*if(android.os.Build.VERSION.SDK_INT >9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}	*/
		
		edit_id = (EditText)findViewById(R.id.id);
		edit_pass = (EditText)findViewById(R.id.pass);
		edit_name = (EditText)findViewById(R.id.name);
		edit_phone_number  = (EditText)findViewById(R.id.phone_number);
		rb_man = (RadioButton)findViewById(R.id.radiobutton_man);
		rb_woman = (RadioButton)findViewById(R.id.radiobutton_woman);
		
		insert_button = (Button)findViewById(R.id.insert_button);
		idCheck_button = (Button)findViewById(R.id.idCheck_button);
		
		requestURL1 = "http://121.157.84.63:8080/lance/androidJoin.jsp";
		requestURL2 = "http://121.157.84.63:8080/lance/androidIdCheck.jsp";
		
		connect = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE); //���ͳ��� ����Ǿ��ֳ� Ȯ�� 
		
		//�� �ڵ��� ��ȣ �������� 
		TelephonyManager systemService = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		PhoneNumber = systemService.getLine1Number();
		PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-10,PhoneNumber.length());
		PhoneNumber="0"+PhoneNumber;
		PhoneNumber = PhoneNumberUtils.formatNumber(PhoneNumber);
		edit_phone_number.setHint(PhoneNumber);
		edit_phone_number.setEnabled(false); 
		
		//////////////////////////////////////////////////////////////
		idCheck_button.setOnClickListener(new View.OnClickListener() {
		//���̵� �ߺ�üũ ��ư Ŭ���� 	
			@Override
			public void onClick(View v) {
				
				id = edit_id.getText().toString();
				
				if(id.length() ==0 || id.trim().length() ==0){
					Toast toast = Toast.makeText(SignUpActivity.this , "���̵� �Է��ϼ���", Toast.LENGTH_SHORT);
					toast.show();
					edit_id.requestFocus();
				}
				
				else{
					
					//���ͳ��� ����� �ֳ� Ȯ�� 
					if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
					|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
					 ){
						new Networking2().execute();
					}
					
					else{
						Toast toast = Toast.makeText(SignUpActivity.this , "���ͳݿ����� ������ϴ�", Toast.LENGTH_SHORT);
						toast.show();
					}	
				}
		}		
	});//end onClick()
		

		/////////////////////////////////////////////////////////////
		insert_button.setOnClickListener(new View.OnClickListener() {
		//ȸ������ ��ư Ŭ���� 		
				@Override
				public void onClick(View v) {
					
					 id = edit_id.getText().toString();
					 pass = edit_pass.getText().toString();
					 name = edit_name.getText().toString();
					 phone_number = edit_phone_number.getText().toString();
					
					if(rb_man.isChecked())
						gender="����";
					
					if(rb_woman.isChecked())
						gender="����";
					
					
					System.out.println(gender);
		
					if(id.length() ==0 || id.trim().length() ==0){
						Toast toast = Toast.makeText(SignUpActivity.this , "���̵� �Է��ϼ���", Toast.LENGTH_SHORT);
						toast.show();
						edit_id.requestFocus();
					}
					
					else if(pass.length() ==0 || pass.trim().length() ==0){
						Toast toast = Toast.makeText(SignUpActivity.this , "�н����带 �Է��ϼ���", Toast.LENGTH_SHORT);
						toast.show();
						edit_pass.requestFocus();
					}
					
					else if(name.length() ==0 || name.trim().length() ==0){
						Toast toast = Toast.makeText(SignUpActivity.this , "�̸��� �Է��ϼ���", Toast.LENGTH_SHORT);
						toast.show();
						edit_name.requestFocus();
					}
					
					/*else if(phone_number.length() ==0 || phone_number.trim().length() ==0){
						Toast toast = Toast.makeText(JoinActivity.this , "�ڵ�����ȣ�� �Է��ϼ���", Toast.LENGTH_SHORT);
						toast.show();
						edit_phone_number.requestFocus();
					}*/
							
					else{//��� data�� �� �� �Ŀ�  ���� Ŭ���� Networking �����ҰŴ�
						 
						//���ͳ��� ����� �ֳ� Ȯ�� 
						if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
						|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
						 ){
							new Networking1().execute();
						}
						
						else{
							Toast toast = Toast.makeText(SignUpActivity.this , "���ͳݿ����� ������ϴ�", Toast.LENGTH_SHORT);
							toast.show();
						}
							
					}
			}		
		});//end onClick()
	}//oncreate end
	

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
				
					result = sendData(id,pass,name,phone_number,gender,requestURL1);
					
			
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
				
			//result ������ jsp ������ ��� �� �ִ� ���߿��� rsult�κи� �����س��� �ϴµ� ... 
			//���⼭ result������ �ɰ��� �۾� ���� 
			String[] list;
			list = result.split(">");
			idCheck = list[2].substring(2, 7); // �ߺ� or ����  �ΰ��� string ���� ���� 
			System.out.println(idCheck);
			
			Toast toast_wait = Toast.makeText(SignUpActivity.this , "��ø� ��ٷ��ּ���", Toast.LENGTH_SHORT);
			toast_wait.show();
			
			if(idCheck.equals("dupli") ){
				
				Toast toast = Toast.makeText(SignUpActivity.this , "���̵� �ߺ��Դϴ�", Toast.LENGTH_SHORT);
				toast.show();
			}
			else{
				Toast toast = Toast.makeText(SignUpActivity.this , "�����մϴ� ȸ�������� �Ǽ̽��ϴ�", Toast.LENGTH_SHORT);
				toast.show();
				startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
			}
			
		}	
		@Override
		protected void onCancelled() {
			//AsyncTask�� ������ ��������� ȣ��
			super.onCancelled();
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private class Networking2 extends AsyncTask<URL, Integer, String>{

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
				
					result = sendData2(id,requestURL2);
					
			
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
				
			//result ������ jsp ������ ��� �� �ִ� ���߿��� rsult�κи� �����س��� �ϴµ� ... 
			//���⼭ result������ �ɰ��� �۾� ���� 
			String[] list;
			list = result.split(">");
			idCheck = list[2].substring(2, 7); // �ߺ� or ����  �ΰ��� string ���� ���� 
			System.out.println(idCheck);
			
			if(idCheck.equals("dupli") ){
				
				Toast toast = Toast.makeText(SignUpActivity.this , "���̵� �ߺ��Դϴ�", Toast.LENGTH_SHORT);
				toast.show();
			}
			
			if(idCheck.equals("possi")){
				Toast toast = Toast.makeText(SignUpActivity.this , "��밡���� ���̵��Դϴ�", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		
		@Override
		protected void onCancelled() {
			//AsyncTask�� ������ ��������� ȣ��
			super.onCancelled();
			
		}

	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String sendData(String id, String pass, String name, String phone_number, String gender, String requestURL1) throws ClientProtocolException, IOException{
		
		HttpPost request = makeHttpPost(id,pass,name,phone_number,gender,requestURL1);
		
		HttpClient client = new DefaultHttpClient();
		ResponseHandler<String> reshandler = new BasicResponseHandler();
		String result = client.execute(request, reshandler);
		return result;
	}
		
	private HttpPost makeHttpPost(String id, String pass, String name, String phone_number, String gender, String requestURL1){
		
		HttpPost request = new HttpPost(requestURL1);
		
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
    	dataList.add(new BasicNameValuePair("id", id));
    	dataList.add(new BasicNameValuePair("pass", pass));
    	dataList.add(new BasicNameValuePair("name", name));
    	dataList.add(new BasicNameValuePair("phone_number", PhoneNumber));
    	dataList.add(new BasicNameValuePair("gender", gender));

    	request.setEntity(makeEntity(dataList));
    	return request;
	}
	
	private HttpEntity makeEntity(List<NameValuePair> dataList){
		
		HttpEntity result = null;
		
		try{
			result = new UrlEncodedFormEntity(dataList,"UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
			System.out.println("����Ƽ�����ڵ� exception");
		}
		return result;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String sendData2(String id, String requestURL2) throws ClientProtocolException, IOException{
		
		HttpPost request = makeHttpPost2(id,requestURL2);
		
		HttpClient client = new DefaultHttpClient();
		ResponseHandler<String> reshandler = new BasicResponseHandler();
		String result = client.execute(request, reshandler);
		return result;
	}
		
	private HttpPost makeHttpPost2(String id, String requestURL2){
		
		HttpPost request = new HttpPost(requestURL2);
		
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
    	dataList.add(new BasicNameValuePair("id", id));
	
    	request.setEntity(makeEntity2(dataList));
    	return request;
	}
	
	private HttpEntity makeEntity2(List<NameValuePair> dataList){
		
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
