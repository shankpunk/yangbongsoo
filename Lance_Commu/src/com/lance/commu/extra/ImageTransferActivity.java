package com.lance.commu.extra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
  
import com.lance.commu.intro.R;   
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageTransferActivity extends Activity {

	Button gallery_button, transfer_button,phone_button;
	EditText edit_title,edit_content;
	ImageView imageView;
	Uri imageUri;
	Bitmap imgBitmap;
	String id;
	String requestURL1; 
	String uuid;
	String title,content;
	ConnectivityManager connect;
	ProgressDialog mDlg;
	Context mContext;
	long size; // 내가 선택한 파일크기 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagetransfer);
			
		gallery_button = (Button)findViewById(R.id.gallery_button);
		transfer_button = (Button)findViewById(R.id.transfer_button);
		phone_button = (Button)findViewById(R.id.phone_button);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		requestURL1 = "http://121.157.84.63:8080/lance/androidImageTransfer.jsp";
		connect = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE); //인터넷이 연결되어있나 확인
		
		//제목과 내용
		edit_title = (EditText)findViewById(R.id.title);
		edit_content = (EditText)findViewById(R.id.content);
		
		
		//내가 접속한 아이디값 넘겨받음 
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		
		gallery_button.setOnClickListener(new View.OnClickListener() { //갤러리 버튼을 클릭시 	
			
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(Intent.ACTION_PICK);
					intent.setType("image/*");
					startActivityForResult(intent, 0);

				}		
			});//end onClick()
		
		
		phone_button.setOnClickListener(new View.OnClickListener() { //전화 버튼을 클릭시 	
			
			@Override
			public void onClick(View v) {
				
				android.os.Process.killProcess(android.os.Process.myPid()); //어플리케이션 종료하고 싶을 때 
				
				/*String key = "success";
				Intent intent = new Intent(ImageTransferActivity.this,PhoneConnectActivity.class);
				intent.putExtra("key", key);
				startActivity(intent);*/
				
			}		
		});//end onClick()
		
		
		
		transfer_button.setOnClickListener(new View.OnClickListener() {//이미지 전송 버튼을 클릭시 
				
				@Override
				public void onClick(View v) {
					
					if(imageUri != null){//사진을 선택하고 난후 작업 
						
						doSaveFile(); // 내가 선택한 사진을 /sdcard/webtransfer에 저장시킴
						
						//저장 시키고 그 파일의 크기를 알아와야함 
						/*System.out.println(size);
						System.out.println(size);
						System.out.println(size);
						*/
						
						 title = edit_title.getText().toString();
						 content = edit_content.getText().toString();
						
						 	if(title.length() ==0 || title.trim().length() ==0){
								Toast toast = Toast.makeText(ImageTransferActivity.this , "제목을 입력하세요", Toast.LENGTH_SHORT);
								toast.show();
								edit_title.requestFocus();
							}
							
							else if(content.length() ==0 || content.trim().length() ==0){
								Toast toast = Toast.makeText(ImageTransferActivity.this , "내용을 입력하세요", Toast.LENGTH_SHORT);
								toast.show();
								edit_content.requestFocus();
							}
						 
							else{ // 제목과 내용을 모두 입력했을 시 
								
								//인터넷이 연결돼 있나 확인 
								if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
								|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
								 ){
									new Networking().execute(); //이미지(UUID) , 제목 , 내용 , 내가 접속한 아이디 값 전송 
									Toast toast = Toast.makeText(ImageTransferActivity.this , "전송됐습니다", Toast.LENGTH_SHORT);
									toast.show();
								}
								
								else{
									Toast toast = Toast.makeText(ImageTransferActivity.this , "인터넷연결이 끊겼습니다", Toast.LENGTH_SHORT);
									toast.show();
								}
							
							}

					}
					else{ //갤러리에서 사진을 선택 안했다면 
						Toast toast = Toast.makeText(ImageTransferActivity.this , "이미지를 선택하세요", Toast.LENGTH_SHORT);
						toast.show();
					}
					
				}		
			});//end onClick()
	}// end onCreate
	
	@Override
	@SuppressLint("SdCardPath")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode==RESULT_OK&&requestCode==0){//갤러리호출
			
			imgBitmap = null;
			imageUri = data.getData();//이미지 받아옴
			
			
			//이미지 비트맵으로 변환
			try {
				imgBitmap = Media.getBitmap(getContentResolver(), imageUri);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//이미지 뷰 세팅
			imageView.setImageBitmap(imgBitmap);
			
			
		}
	}
	
	private class Networking extends AsyncTask<URL, Integer, String>{

		@Override
		protected void onPreExecute() { 
			//doInBackground 가 수행되기 전, 즉 AsyncTask.execute()	를 호출했을때 가정 먼저 수행되는 녀석 
			//보통 초기화 작업을 해준다
			
		/*	mDlg = new ProgressDialog(mContext);
			mDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mDlg.setMessage("start");
			mDlg.setProgress(0);
			mDlg.setMax((int)size);
			mDlg.show();*/
			super.onPreExecute();
			
		}
		
		@Override
		protected String doInBackground(URL... params) {
			//별개의 스레드에서 작업을 실행함 
			//AsyncTask.execute(params)를 통해서 수행이 됌 

			
			doFileUpload();
			onCancelled();
			return "";
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
				

			}
		
		@Override
		protected void onCancelled() {
			//AsyncTask를 강제로 취소했을때 호출
			super.onCancelled();
			}
		}
	
	@SuppressLint("SdCardPath")
	public long doSaveFile(){
		File path = new File("/sdcard/webtransfer");
		FileOutputStream out = null;
		//File f;
		long size = 0;
		if (!path.isDirectory()) {
			path.mkdirs();
		}
		
		try {
			 uuid = UUID.randomUUID().toString();
			 out = new FileOutputStream(
					"/sdcard/webtransfer/"+uuid);
			imgBitmap.compress(Bitmap.CompressFormat.PNG, 70, out);
			
			
			//f = new File("/sdcard/webtransfer/"+uuid);
			//size = f.length();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("FileNotFoundException");
		}finally{
			try {
				out.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		}
		return size;
	}
	
	@SuppressLint("SdCardPath")
	public void doFileUpload(){
		HttpClient httpClient = null;
		try{
			httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(requestURL1);
			File saveFile = new File("/sdcard/webtransfer/"+uuid);
			FileBody bin = new FileBody(saveFile);
			
			StringBody loginId = new StringBody(id, Charset.forName("utf-8"));
			StringBody uuId = new StringBody(uuid,Charset.forName("utf-8"));
			StringBody transferTitle = new StringBody(title,Charset.forName("utf-8"));
			StringBody transferContent = new StringBody(content,Charset.forName("utf-8"));
			
			MultipartEntity multipart = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			multipart.addPart("images", bin); //내가 선택한 이미지 
			multipart.addPart("id", loginId); // 내가 로그인한 아이디값
			multipart.addPart("uuid", uuId); //내가 선택한 이미지 파일명 
			multipart.addPart("title", transferTitle); //입력한 제목 
			multipart.addPart("content", transferContent);// 입력한 내용 
			
			size = multipart.getContentLength();
			
			System.out.println(size);
			System.out.println(size);
			System.out.println(size);
			
			post.setEntity(multipart);
			HttpResponse response = httpClient.execute(post);
			HttpEntity resEntity = response.getEntity();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("doFileUpload error");
		}
		finally{
			httpClient.getConnectionManager().shutdown();
		}
	}
}
