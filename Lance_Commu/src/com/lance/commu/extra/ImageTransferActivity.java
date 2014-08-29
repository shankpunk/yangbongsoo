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
	long size; // ���� ������ ����ũ�� 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagetransfer);
			
		gallery_button = (Button)findViewById(R.id.gallery_button);
		transfer_button = (Button)findViewById(R.id.transfer_button);
		phone_button = (Button)findViewById(R.id.phone_button);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		requestURL1 = "http://121.157.84.63:8080/lance/androidImageTransfer.jsp";
		connect = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE); //���ͳ��� ����Ǿ��ֳ� Ȯ��
		
		//����� ����
		edit_title = (EditText)findViewById(R.id.title);
		edit_content = (EditText)findViewById(R.id.content);
		
		
		//���� ������ ���̵� �Ѱܹ��� 
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		
		gallery_button.setOnClickListener(new View.OnClickListener() { //������ ��ư�� Ŭ���� 	
			
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(Intent.ACTION_PICK);
					intent.setType("image/*");
					startActivityForResult(intent, 0);

				}		
			});//end onClick()
		
		
		phone_button.setOnClickListener(new View.OnClickListener() { //��ȭ ��ư�� Ŭ���� 	
			
			@Override
			public void onClick(View v) {
				
				android.os.Process.killProcess(android.os.Process.myPid()); //���ø����̼� �����ϰ� ���� �� 
				
				/*String key = "success";
				Intent intent = new Intent(ImageTransferActivity.this,PhoneConnectActivity.class);
				intent.putExtra("key", key);
				startActivity(intent);*/
				
			}		
		});//end onClick()
		
		
		
		transfer_button.setOnClickListener(new View.OnClickListener() {//�̹��� ���� ��ư�� Ŭ���� 
				
				@Override
				public void onClick(View v) {
					
					if(imageUri != null){//������ �����ϰ� ���� �۾� 
						
						doSaveFile(); // ���� ������ ������ /sdcard/webtransfer�� �����Ŵ
						
						//���� ��Ű�� �� ������ ũ�⸦ �˾ƿ;��� 
						/*System.out.println(size);
						System.out.println(size);
						System.out.println(size);
						*/
						
						 title = edit_title.getText().toString();
						 content = edit_content.getText().toString();
						
						 	if(title.length() ==0 || title.trim().length() ==0){
								Toast toast = Toast.makeText(ImageTransferActivity.this , "������ �Է��ϼ���", Toast.LENGTH_SHORT);
								toast.show();
								edit_title.requestFocus();
							}
							
							else if(content.length() ==0 || content.trim().length() ==0){
								Toast toast = Toast.makeText(ImageTransferActivity.this , "������ �Է��ϼ���", Toast.LENGTH_SHORT);
								toast.show();
								edit_content.requestFocus();
							}
						 
							else{ // ����� ������ ��� �Է����� �� 
								
								//���ͳ��� ����� �ֳ� Ȯ�� 
								if(connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED 
								|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED	
								 ){
									new Networking().execute(); //�̹���(UUID) , ���� , ���� , ���� ������ ���̵� �� ���� 
									Toast toast = Toast.makeText(ImageTransferActivity.this , "���۵ƽ��ϴ�", Toast.LENGTH_SHORT);
									toast.show();
								}
								
								else{
									Toast toast = Toast.makeText(ImageTransferActivity.this , "���ͳݿ����� ������ϴ�", Toast.LENGTH_SHORT);
									toast.show();
								}
							
							}

					}
					else{ //���������� ������ ���� ���ߴٸ� 
						Toast toast = Toast.makeText(ImageTransferActivity.this , "�̹����� �����ϼ���", Toast.LENGTH_SHORT);
						toast.show();
					}
					
				}		
			});//end onClick()
	}// end onCreate
	
	@Override
	@SuppressLint("SdCardPath")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode==RESULT_OK&&requestCode==0){//������ȣ��
			
			imgBitmap = null;
			imageUri = data.getData();//�̹��� �޾ƿ�
			
			
			//�̹��� ��Ʈ������ ��ȯ
			try {
				imgBitmap = Media.getBitmap(getContentResolver(), imageUri);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//�̹��� �� ����
			imageView.setImageBitmap(imgBitmap);
			
			
		}
	}
	
	private class Networking extends AsyncTask<URL, Integer, String>{

		@Override
		protected void onPreExecute() { 
			//doInBackground �� ����Ǳ� ��, �� AsyncTask.execute()	�� ȣ�������� ���� ���� ����Ǵ� �༮ 
			//���� �ʱ�ȭ �۾��� ���ش�
			
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
			//������ �����忡�� �۾��� ������ 
			//AsyncTask.execute(params)�� ���ؼ� ������ �� 

			
			doFileUpload();
			onCancelled();
			return "";
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
				

			}
		
		@Override
		protected void onCancelled() {
			//AsyncTask�� ������ ��������� ȣ��
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
			multipart.addPart("images", bin); //���� ������ �̹��� 
			multipart.addPart("id", loginId); // ���� �α����� ���̵�
			multipart.addPart("uuid", uuId); //���� ������ �̹��� ���ϸ� 
			multipart.addPart("title", transferTitle); //�Է��� ���� 
			multipart.addPart("content", transferContent);// �Է��� ���� 
			
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
