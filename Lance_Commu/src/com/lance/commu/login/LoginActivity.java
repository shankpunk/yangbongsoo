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
	String id; // ���� �Է��ϴ� ���̵�� (���� ���̵��ϼ� �ְ� Ʋ���� �ִ� ���̵𰪵�) 
	String rememberRealId; //�α��� �� �� ����ó�� ����ϰ� �־�� �� ���̵� 
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
		
		
		backPressCloseHandler = new BackPressCloseHandler(this);//�ι� �ڷΰ��� �� ���� �۾�
		
		insert_button = (Button)findViewById(R.id.insert_button);
		login_button = (Button)findViewById(R.id.login_button);
		
		edit_id = (EditText)findViewById(R.id.id);
		edit_pass = (EditText)findViewById(R.id.pass);
		requestURL1 = "http://121.157.84.63:8080/lance/androidLogin.jsp";
		connect = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE); //���ͳ��� ����Ǿ��ֳ� Ȯ�� 

		//üũ�ڽ�
		findViewById(R.id.checkbox).setOnClickListener
									(new Button.OnClickListener(){
						@Override
						public void onClick(View v){
							checkAutoLogin(v);
						}	
		});
		
		
		insert_button.setOnClickListener(new View.OnClickListener() {
			//�α��� â���� ȸ������ ��ư Ŭ���� 		
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
					
				}		
			});//end onClick()
		
		
		login_button.setOnClickListener(new View.OnClickListener() {
			//�α��� ��ư Ŭ����	
				@Override
				public void onClick(View v) {
					
					 id = edit_id.getText().toString();
					 pass = edit_pass.getText().toString();
					 
					//���� ���̵� �н����� �Է��ߴ��� Ȯ��
					if(id.length() ==0 || id.trim().length() ==0){
						Toast toast = Toast.makeText(LoginActivity.this , "���̵� �Է��ϼ���", Toast.LENGTH_SHORT);
						toast.show();
						edit_id.requestFocus();
					}
					
					else if(pass.length() ==0 || pass.trim().length() ==0){
						Toast toast = Toast.makeText(LoginActivity.this , "�н����带 �Է��ϼ���", Toast.LENGTH_SHORT);
						toast.show();
						edit_pass.requestFocus();
					}	
					
					 
					else{//���̵�, �н����� ���� ��� �Է��ϰ� �� �� Ŭ���ϸ� 
							//���ͳ��� ����� �ֳ� Ȯ�� 
							if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
							|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
							 ){
								new Networking1().execute();
							}
							
							else{
								Toast toast = Toast.makeText(LoginActivity.this , "���ͳݿ����� ������ϴ�", Toast.LENGTH_SHORT);
								toast.show();
							}		
					}
				}		
			});//end onClick()
	}//onCreate end 
	
	public void checkAutoLogin(View v){
		CheckBox ch = (CheckBox)findViewById(R.id.checkbox);
		if(ch.isChecked()){//�ڵ��α����� üũ�ƴٸ�
			flag = true; //�̰����� ��Ʈ��ũ ����ϸ鼭 �Ƶ�,����� ����ϳ� ���ϳ� ����
		}
		else{//�ڵ��α����� üũ �ȵƴٸ�
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
			//doInBackground �� ����Ǳ� ��, �� AsyncTask.execute()	�� ȣ�������� ���� ���� ����Ǵ� �༮ 
			//���� �ʱ�ȭ �۾��� ���ش�
			super.onPreExecute();	
		}
		
		@Override
		protected String doInBackground(URL... params){
			//������ �����忡�� �۾��� ������ 
			//AsyncTask.execute(params)�� ���ؼ� ������ �� 
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
				
			String[] list;
			list = result.split(">");
			login = list[2].substring(2, 7);
			System.out.println(login);
		
			if(login.equals("notid")){
				Toast toast = Toast.makeText(LoginActivity.this , "���̵� �߸� �Է��ϼ̽��ϴ�", Toast.LENGTH_SHORT);
				toast.show();
			}
			
			else if(login.equals("notps")){
				Toast toast = Toast.makeText(LoginActivity.this , "��й�ȣ�� �߸� �Է��ϼ̽��ϴ�", Toast.LENGTH_SHORT);
				toast.show();
			}
			
			else if(login.equals("login")){
				Toast toast = Toast.makeText(LoginActivity.this , "�α��� ����", Toast.LENGTH_SHORT);
				toast.show();
				
				if(flag == true){
					//������ �ڵ��α����� ���Ͽ� sharedpreference�� ����
					sp = getSharedPreferences("PreName", MODE_PRIVATE);
					SharedPreferences.Editor editor = sp.edit();
					
					editor.putString("sp_id", edit_id.getText().toString());
					editor.putString("sp_pw", edit_pass.getText().toString());
					editor.commit();
				}
				
				System.out.println("��������� ���������� �۵�");
				
				Intent intent = new Intent(LoginActivity.this,FragmentMainActivity.class);
				startActivity(intent);
				
				
				/*////////////////////����� ImageTransferActivity�κ�/////////////////////////////
				//�α��� ������ ���̵� ����ؼ� DB�� Gallery writer�� rememberRealId�� �ǵ��� �����ؾ��� 
				rememberRealId = edit_id.getText().toString();
				//���� ������ ���̵��� ������ ���� �������� �̵��ϴµ� FriendListActivity���� ������ ���� 
				Intent intent = new Intent(LoginActivity.this,FriendListActivity.class);
				intent.putExtra("id", rememberRealId);
				startActivity(intent);
				///////////////////////////////////////////////////////////////////////////////////*/
				}
		}

		@Override
		protected void onCancelled() {
			//AsyncTask�� ������ ��������� ȣ��
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
			System.out.println("����Ƽ�����ڵ� exception");
		}
		return result;
	}
}
