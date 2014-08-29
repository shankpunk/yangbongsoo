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
	String phone_number; //최종적으로 네트워크 통해 보내는 핸펀번호 
	String PhoneNumber; //내 핸펀번호 자동으로 얻어와서 저장되는 변수
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
		
		connect = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE); //인터넷이 연결되어있나 확인 
		
		//내 핸드폰 번호 가져오기 
		TelephonyManager systemService = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		PhoneNumber = systemService.getLine1Number();
		PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-10,PhoneNumber.length());
		PhoneNumber="0"+PhoneNumber;
		PhoneNumber = PhoneNumberUtils.formatNumber(PhoneNumber);
		edit_phone_number.setHint(PhoneNumber);
		edit_phone_number.setEnabled(false); 
		
		//////////////////////////////////////////////////////////////
		idCheck_button.setOnClickListener(new View.OnClickListener() {
		//아이디 중복체크 버튼 클릭시 	
			@Override
			public void onClick(View v) {
				
				id = edit_id.getText().toString();
				
				if(id.length() ==0 || id.trim().length() ==0){
					Toast toast = Toast.makeText(SignUpActivity.this , "아이디를 입력하세요", Toast.LENGTH_SHORT);
					toast.show();
					edit_id.requestFocus();
				}
				
				else{
					
					//인터넷이 연결돼 있나 확인 
					if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
					|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
					 ){
						new Networking2().execute();
					}
					
					else{
						Toast toast = Toast.makeText(SignUpActivity.this , "인터넷연결이 끊겼습니다", Toast.LENGTH_SHORT);
						toast.show();
					}	
				}
		}		
	});//end onClick()
		

		/////////////////////////////////////////////////////////////
		insert_button.setOnClickListener(new View.OnClickListener() {
		//회원가입 버튼 클릭시 		
				@Override
				public void onClick(View v) {
					
					 id = edit_id.getText().toString();
					 pass = edit_pass.getText().toString();
					 name = edit_name.getText().toString();
					 phone_number = edit_phone_number.getText().toString();
					
					if(rb_man.isChecked())
						gender="남자";
					
					if(rb_woman.isChecked())
						gender="여자";
					
					
					System.out.println(gender);
		
					if(id.length() ==0 || id.trim().length() ==0){
						Toast toast = Toast.makeText(SignUpActivity.this , "아이디를 입력하세요", Toast.LENGTH_SHORT);
						toast.show();
						edit_id.requestFocus();
					}
					
					else if(pass.length() ==0 || pass.trim().length() ==0){
						Toast toast = Toast.makeText(SignUpActivity.this , "패스워드를 입력하세요", Toast.LENGTH_SHORT);
						toast.show();
						edit_pass.requestFocus();
					}
					
					else if(name.length() ==0 || name.trim().length() ==0){
						Toast toast = Toast.makeText(SignUpActivity.this , "이름을 입력하세요", Toast.LENGTH_SHORT);
						toast.show();
						edit_name.requestFocus();
					}
					
					/*else if(phone_number.length() ==0 || phone_number.trim().length() ==0){
						Toast toast = Toast.makeText(JoinActivity.this , "핸드폰번호를 입력하세요", Toast.LENGTH_SHORT);
						toast.show();
						edit_phone_number.requestFocus();
					}*/
							
					else{//모든 data가 다 들어간 후에  내부 클래스 Networking 시작할거다
						 
						//인터넷이 연결돼 있나 확인 
						if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
						|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
						 ){
							new Networking1().execute();
						}
						
						else{
							Toast toast = Toast.makeText(SignUpActivity.this , "인터넷연결이 끊겼습니다", Toast.LENGTH_SHORT);
							toast.show();
						}
							
					}
			}		
		});//end onClick()
	}//oncreate end
	

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
				
			//result 값에는 jsp 내용이 모두 들어가 있다 그중에서 rsult부분만 추출해내야 하는데 ... 
			//여기서 result내용을 쪼개는 작업 실행 
			String[] list;
			list = result.split(">");
			idCheck = list[2].substring(2, 7); // 중복 or 가능  두개의 string 값이 나옴 
			System.out.println(idCheck);
			
			Toast toast_wait = Toast.makeText(SignUpActivity.this , "잠시만 기다려주세요", Toast.LENGTH_SHORT);
			toast_wait.show();
			
			if(idCheck.equals("dupli") ){
				
				Toast toast = Toast.makeText(SignUpActivity.this , "아이디 중복입니다", Toast.LENGTH_SHORT);
				toast.show();
			}
			else{
				Toast toast = Toast.makeText(SignUpActivity.this , "축하합니다 회원가입이 되셨습니다", Toast.LENGTH_SHORT);
				toast.show();
				startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
			}
			
		}	
		@Override
		protected void onCancelled() {
			//AsyncTask를 강제로 취소했을때 호출
			super.onCancelled();
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private class Networking2 extends AsyncTask<URL, Integer, String>{

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
				
			//result 값에는 jsp 내용이 모두 들어가 있다 그중에서 rsult부분만 추출해내야 하는데 ... 
			//여기서 result내용을 쪼개는 작업 실행 
			String[] list;
			list = result.split(">");
			idCheck = list[2].substring(2, 7); // 중복 or 가능  두개의 string 값이 나옴 
			System.out.println(idCheck);
			
			if(idCheck.equals("dupli") ){
				
				Toast toast = Toast.makeText(SignUpActivity.this , "아이디 중복입니다", Toast.LENGTH_SHORT);
				toast.show();
			}
			
			if(idCheck.equals("possi")){
				Toast toast = Toast.makeText(SignUpActivity.this , "사용가능한 아이디입니다", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		
		@Override
		protected void onCancelled() {
			//AsyncTask를 강제로 취소했을때 호출
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
			System.out.println("언서포티드인코딩 exception");
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
			System.out.println("언서포티드인코딩 exception");
		}
		return result;
	}
	
}
