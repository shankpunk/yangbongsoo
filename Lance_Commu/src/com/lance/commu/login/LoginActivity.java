package com.lance.commu.login;

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
import com.lance.commu.extra.BackPressCloseHandler;
import com.lance.commu.fragment.FragmentMainActivity;
import com.lance.commu.friendlist.FriendListActivity;
import com.lance.commu.signup.SignUpActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button insert_button, login_button;
	EditText edit_id,edit_pass;
	String id; // 내가 입력하는 아이디들 (옳은 아이디일수 있고 틀릴수 있는 아이디값들) 
	String rememberRealId; //로그인 된 후 세션처럼 기억하고 있어야 할 아이디 
	String pass;
	String requestURL1; 
	String login;
	ConnectivityManager connect;
	SharedPreferences sp;
	private BackPressCloseHandler backPressCloseHandler;
	static boolean flag = false;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.logo);
		actionBar.setTitle("Commu");
		
		
		backPressCloseHandler = new BackPressCloseHandler(this);//두번 뒤로가기 시 종료 작업
		
		insert_button = (Button)findViewById(R.id.insert_button);
		login_button = (Button)findViewById(R.id.login_button);
		
		edit_id = (EditText)findViewById(R.id.id);
		edit_pass = (EditText)findViewById(R.id.pass);
		requestURL1 = "http://121.157.84.63:8080/lance/androidLogin.jsp";
		connect = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE); //인터넷이 연결되어있나 확인 

		//체크박스
		findViewById(R.id.checkbox).setOnClickListener
									(new Button.OnClickListener(){
						@Override
						public void onClick(View v){
							checkAutoLogin(v);
						}	
		});
		
		
		insert_button.setOnClickListener(new View.OnClickListener() {
			//로그인 창에서 회원가입 버튼 클릭시 		
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
					
				}		
			});//end onClick()
		
		
		login_button.setOnClickListener(new View.OnClickListener() {
			//로그인 버튼 클릭시	
				@Override
				public void onClick(View v) {
					
					 id = edit_id.getText().toString();
					 pass = edit_pass.getText().toString();
					 
					//먼저 아이디 패스워드 입력했는지 확인
					if(id.length() ==0 || id.trim().length() ==0){
						Toast toast = Toast.makeText(LoginActivity.this , "아이디를 입력하세요", Toast.LENGTH_SHORT);
						toast.show();
						edit_id.requestFocus();
					}
					
					else if(pass.length() ==0 || pass.trim().length() ==0){
						Toast toast = Toast.makeText(LoginActivity.this , "패스워드를 입력하세요", Toast.LENGTH_SHORT);
						toast.show();
						edit_pass.requestFocus();
					}	
					
					 
					else{//아이디, 패스워드 값을 모두 입력하고 난 후 클릭하면 
							//인터넷이 연결돼 있나 확인 
							if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
							|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
							 ){
								new Networking1().execute();
							}
							
							else{
								Toast toast = Toast.makeText(LoginActivity.this , "인터넷연결이 끊겼습니다", Toast.LENGTH_SHORT);
								toast.show();
							}		
					}
				}		
			});//end onClick()
	}//onCreate end 
	
	public void checkAutoLogin(View v){
		CheckBox ch = (CheckBox)findViewById(R.id.checkbox);
		if(ch.isChecked()){//자동로그인이 체크됐다면
			flag = true; //이걸통해 네트워크 통신하면서 아디,비번값 기억하냐 안하냐 차이
		}
		else{//자동로그인이 체크 안됐다면
			flag = false;
		}
	}
	
	@Override
	public void onBackPressed() { 
		//super.onBackPressed();
		backPressCloseHandler.onBackPressed();
	}

	private class Networking1 extends AsyncTask<URL, Integer, String>{

		@Override
		protected void onPreExecute() { 
			//doInBackground 가 수행되기 전, 즉 AsyncTask.execute()	를 호출했을때 가정 먼저 수행되는 녀석 
			//보통 초기화 작업을 해준다
			super.onPreExecute();	
		}
		
		@Override
		protected String doInBackground(URL... params){
			//별개의 스레드에서 작업을 실행함 
			//AsyncTask.execute(params)를 통해서 수행이 됌 
			String result =null;
			
			try {

					result = sendData1(id,pass,requestURL1);
					
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
				
			String[] list;
			list = result.split(">");
			login = list[2].substring(2, 7);
			System.out.println(login);
		
			if(login.equals("notid")){
				Toast toast = Toast.makeText(LoginActivity.this , "아이디를 잘못 입력하셨습니다", Toast.LENGTH_SHORT);
				toast.show();
			}
			
			else if(login.equals("notps")){
				Toast toast = Toast.makeText(LoginActivity.this , "비밀번호를 잘못 입력하셨습니다", Toast.LENGTH_SHORT);
				toast.show();
			}
			
			else if(login.equals("login")){
				Toast toast = Toast.makeText(LoginActivity.this , "로그인 성공", Toast.LENGTH_SHORT);
				toast.show();
				
				if(flag == true){
					//다음번 자동로그인을 위하여 sharedpreference에 저장
					sp = getSharedPreferences("PreName", MODE_PRIVATE);
					SharedPreferences.Editor editor = sp.edit();
					
					editor.putString("sp_id", edit_id.getText().toString());
					editor.putString("sp_pw", edit_pass.getText().toString());
					editor.commit();
				}
				
				System.out.println("여기까지는 정상적으로 작동");
				
				Intent intent = new Intent(LoginActivity.this,FragmentMainActivity.class);
				startActivity(intent);
				
				
				/*////////////////////여기는 ImageTransferActivity부분/////////////////////////////
				//로그인 성공후 아이디를 기억해서 DB의 Gallery writer가 rememberRealId가 되도록 설정해야함 
				rememberRealId = edit_id.getText().toString();
				//내가 접속한 아이디값을 가지고 다음 페이지로 이동하는데 FriendListActivity에는 쓸데가 없음 
				Intent intent = new Intent(LoginActivity.this,FriendListActivity.class);
				intent.putExtra("id", rememberRealId);
				startActivity(intent);
				///////////////////////////////////////////////////////////////////////////////////*/
				}
		}

		@Override
		protected void onCancelled() {
			//AsyncTask를 강제로 취소했을때 호출
			super.onCancelled();
		}
	}
	
	private String sendData1(String id, String pass, String requestURL1) throws ClientProtocolException, IOException{
		
		HttpPost request = makeHttpPost1(id, pass, requestURL1);
		HttpClient client = new DefaultHttpClient();
		ResponseHandler<String> reshandler = new BasicResponseHandler();
		String result = client.execute(request, reshandler);
		return result;
	}
		
	private HttpPost makeHttpPost1(String id, String pass, String requestURL1){
		
		HttpPost request = new HttpPost(requestURL1);
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
    	dataList.add(new BasicNameValuePair("id", id));
    	dataList.add(new BasicNameValuePair("pass", pass));
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
