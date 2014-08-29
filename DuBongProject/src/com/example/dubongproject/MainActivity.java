package com.example.dubongproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.hardware.SensorEvent;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends Activity {
	
	private MainView        mMainView;
	private WakeLock        mWl;
	public MyPlane mMyPlane;
	public EnemyPlane mEnemyPlane;
	public AdView adView;
	private SoundPool sound_pool;
	private int sound_explosion;	
	private int sound_levelup;
	private int sound_king;
	private int sound_background;
	public static MediaPlayer mp;
  
	public Handler h;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        //게임 중 화면이 어두워지는 걸 방지하기 위해 깨어 있기 기능 설정 
        PowerManager pm = (PowerManager)this.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        mWl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "gamelock"); 
        mWl.acquire();	        
 
        //애드몹 추가하기 
        adView =(AdView)findViewById(R.id.adView);         
        adView.loadAd(new AdRequest());
        adView.setKeepScreenOn(true);
        
        //사운드 집어넣기 
        sound_pool = new SoundPool(20,AudioManager.STREAM_MUSIC,0 );
        //최대스트림갯수, 스트림타입, 샘플링품질 
        try
        {
	        AssetManager assetManager = getAssets();
	        AssetFileDescriptor explosion_sound = assetManager.openFd("explosion.wav");
	        AssetFileDescriptor levelup_sound =  assetManager.openFd("levelup.wav");
	        AssetFileDescriptor king_sound =  assetManager.openFd("kingsound.wav");
	        //AssetFileDescriptor back_sound =  assetManager.openFd("background.mp3");
	        //경로, 재생우선순위(디폴트1)
	        sound_explosion = sound_pool.load(explosion_sound,1);
	        sound_levelup = sound_pool.load(levelup_sound, 1);
	        sound_king = sound_pool.load(king_sound, 1);
	        //sound_background = sound_pool.load(back_sound, 1);
        }
        catch(Exception e){}  
            
        //백그라운드 음악
        mp = MediaPlayer.create(this, R.raw.background);
        mp.start();
        
        //1분6초 후 ending 
        h = new Handler();
        h.postDelayed(irun, 68000); 
        
 
		// 실제 디바이스 가로세로 크기를 구함
        int Width = getWindowManager().getDefaultDisplay().getWidth();
        int Height = getWindowManager().getDefaultDisplay().getHeight();
        mMainView = (MainView)findViewById(R.id.main_view); // MainView 클래스와 xml을 연결 
        mMainView.make(Width, Height,this); //초기화 작업 

    }//oncreate end
    
    Runnable irun = new Runnable(){
		public void run(){
			Intent i = new Intent(MainActivity.this ,FinishActivity.class);
			startActivity(i);
			finish();
		}
	};
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		h.removeCallbacks(irun);
	}
    
    public void goFinish(){
    	finish();
    }
    
    public void soundExplostion()
    {
    	try
    	{//	식별자,	1이 가장큰소리, 반복모드 (반복 모드, 지정값 +1회 반복(0이면 1회). -1이면 무한 반복),1정상속도
    		sound_pool.play(sound_explosion, 1.0f , 1.0f , 0, 0, 1.0f);
    	}
    	catch(Exception e)
    	{}    	
    }	
    
    public void soundLevelup(){
    	try
    	{//	식별자,	1이 가장큰소리, 반복모드 (반복 모드, 지정값 +1회 반복(0이면 1회). -1이면 무한 반복),1정상속도
    		sound_pool.play(sound_levelup, 1.0f , 1.0f , 0, 0, 1.0f);
    	}
    	catch(Exception e)
    	{}    
    }
    
    public void soundKing(){
    	try
    	{//	식별자,	1이 가장큰소리, 반복모드 (반복 모드, 지정값 +1회 반복(0이면 1회). -1이면 무한 반복),1정상속도
    		sound_pool.play(sound_king, 1.0f , 1.0f , 0, 0, 1.0f);
    	}
    	catch(Exception e)
    	{}    
    }
    
   /* public void soundBack(){
    	try
    	{//	식별자,	1이 가장큰소리, 반복모드 (반복 모드, 지정값 +1회 반복(0이면 1회). -1이면 무한 반복),1정상속도
    		sound_pool.play(sound_background, 1.0f , 1.0f , 0, 0, 1.0f);
    	}
    	catch(Exception e)
    	{}    
    }*/
}