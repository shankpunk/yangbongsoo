package com.example.dubongproject;

import java.io.BufferedInputStream;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color; 
import android.graphics.Paint;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.os.*;
import android.view.View.OnTouchListener;

@SuppressLint("NewApi")
public class MainView extends SurfaceView implements SurfaceHolder.Callback{
	MyThread mMainThread;
	Handler mHandler;
	Context mMainContext;
	
	public boolean mDrawCls = false; //해당 조건에 맞아야 그릴수 있게 설정하기 위한 장치 
	private int mFireLevel = 1; 
	private ScreenConfig mScreenConfig;
	private MyPlane mMyPlane;	// 내 비행기
	private final static int MYFIRE_SIZE =20; //내 총알20개 
	private MyFire[] mMyFire; //내총알 배열 
	private PowerPotion[] mPowerPotion;//파워포션 
	private final static int ENEMY_SIZE = 71; //적비행기 70기 + 왕 1기
	private EnemyPlane[] mEnemyPlane;
	private final static int EXPLOSION_SIZE = 71;
	public Explosion[] mExplosion;
	private final static int ENEMY_FIRE_SIZE =120; //적 총알 각각 70개 + 왕 미사일 50개 
	private int mGage = 0;	
	private final static int GAGE_LIMET = 1000;
	private EnemyFire[] mEnemyFire;
	private SuperButton mSuperButton;
	private SuperPlane mSuperPlane;
	private SuperFireButton mSuperFireButton;
	public double x2,y2;
	public Cloud[] mCloud;
	private final static int CLOUD_SIZE =5;
	public MainActivity mMainActivity;
	public boolean flag = false;
	public static int kingcount = 0;
	public static int shotcount = 0;
	public static int beforeFireLevel = 0;
	// 생성자	
	public MainView(Context context, AttributeSet attr){ //생성자에서 스레드 생성시킴 , SurfaceView 관련 기초작업들 
		super(context, attr);
		SurfaceHolder holder = getHolder(); //서피스 뷰를 움직이는건 서피스홀더니까 생성자에서 콜백함수 등록 
		holder.addCallback(this);
		
		mMainThread = new MyThread(holder,this); //서피스홀더를 움직일 스레드 생성 
		setFocusable(true);
		mMainContext = context; // 비트맵 이미지를 읽이 위해서는 Context가 필요 
	}
	
	//SurfaceHolder 인터페이스 구현시 필수적으로 오버라이딩 해줘야 하는 부분 
    public void surfaceCreated(SurfaceHolder holder){ //서피스 뷰가 생성될때 
    	
		mMainThread.thread_action(true); 
		setFocusable(true);
		mMainThread.start();
		
    }
    
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {}
    
    public void surfaceDestroyed(SurfaceHolder holder){ //서피스 뷰가 종료될때 
    	
    	mMainThread.thread_action(false); //스레드 종료
    	kingcount = 0;
    	shotcount = 0;
    }   	
    
	// 초기 생성시 초기화할 대상
	public void make(int width, int height, MainActivity mMainActivity)
	{				
		
		mScreenConfig = new ScreenConfig(width,height);	// screenconfig에 실제 가로세로 크기 전달 	
		mScreenConfig.setSize(1000,2000); // 가상으로 설정한 가로 세로 크기 전달
		 this.mMainActivity = mMainActivity;
		
		//파워포션 객체  생성 
		Bitmap powerpotionBitmap =loadBitmap("powerpotion.png");
		mPowerPotion = new PowerPotion[2];
		
		mPowerPotion[0] = new PowerPotion(190, 500, 0, mScreenConfig,powerpotionBitmap);
		mPowerPotion[1] = new PowerPotion(1300, 500, 0, mScreenConfig,powerpotionBitmap);
		
		// 내비행기 초기화 
		Bitmap myPlaneBitmap   = loadBitmap("plane.png");
		mMyPlane = new MyPlane(mScreenConfig, myPlaneBitmap , 250 , 250);//이미지 저장한 것을 MyPlane 객체 생성시 초기화로 보냄 
		mMyPlane.start_position(500, 1500);//500,1000 에 비행기 위치하게 됨 
		
		// 총알 초기화 
		Bitmap myFireBitmap   = loadBitmap("myfire.png");
		Bitmap myFireBitmap2  = loadBitmap("myfire2.png");		
		Bitmap myFireBitmap3  = loadBitmap("myfire3.png");	
		Bitmap myFireBitmap4  = loadBitmap("special_fire.png");
		mMyFire = new MyFire[MYFIRE_SIZE]; //총알 객체 생성 
		for(int i = 0; i<MYFIRE_SIZE;i++)
		{
			mMyFire[i] = new MyFire(70, 70,170,170,240,240,300,300, mScreenConfig, myFireBitmap, myFireBitmap2, myFireBitmap3, myFireBitmap4); //각 객체마다 초기화 작업 
			mMyFire[i].mActive=false;
		}
		
		//슈퍼총알버튼 초기화
		Bitmap superfire = loadBitmap("superfirebutton.png");
		mSuperFireButton = new SuperFireButton(70, 1100, mScreenConfig, superfire);
		
		//슈퍼 버튼 초기화 
		Bitmap superpower   = loadBitmap("superbutton.png");
		mSuperButton = new SuperButton(650 ,1100 , mScreenConfig , superpower);
		
		//슈퍼 비행기 초기화
		Bitmap superplane = loadBitmap("super.png");
		mSuperPlane = new SuperPlane(380,1450, mScreenConfig, superplane);
		
		//적비행기 초기화 
		mEnemyPlane = new EnemyPlane[ENEMY_SIZE]; // ENEMY_SIZE = 71 
		produce_Enemyplane();
		
		//적 총알 초기화 
		Bitmap enemyfireBitmap = loadBitmap("enemy_fire.png");
		mEnemyFire = new EnemyFire[ENEMY_FIRE_SIZE];
		
		for(int i = 0; i<ENEMY_FIRE_SIZE;i++)
		{
			mEnemyFire[i] = new EnemyFire( 1, mScreenConfig, enemyfireBitmap);
			mEnemyFire[i].mActive=false;
		}
		
		//폭발처리 초기화 
		Bitmap explosionBitmap = loadBitmap("explosion.png");		
		mExplosion = new Explosion[EXPLOSION_SIZE]; 
		for(int i = 0; i<EXPLOSION_SIZE;i++)
		{
			mExplosion[i] = new Explosion( mScreenConfig, explosionBitmap);
		}
		
		//구름 초기화 
		Bitmap cloud1 = loadBitmap("cloud1.png");
		Bitmap cloud2 = loadBitmap("cloud2.png");
		Bitmap cloud3 = loadBitmap("cloud3.png");
		
		mCloud = new Cloud[CLOUD_SIZE];
		mCloud[0] = new Cloud(14,250,200,100,-200,cloud1,mScreenConfig);
		mCloud[1] = new Cloud(18,300,250,600,-1000,cloud2,mScreenConfig);
		mCloud[2] = new Cloud(20,500,200,800,-800,cloud3, mScreenConfig);
		mCloud[3] = new Cloud(25,300,250,200,-1800,cloud1,mScreenConfig);
		mCloud[4] = new Cloud(30,250,300,-100,-600,cloud2,mScreenConfig);

		// 그리기를 시작해도 됨
		mDrawCls = true;		
	}//make 메소드 end 
	
	public void produce_Enemyplane(){
		
		Bitmap enemyPlaneBitmap =loadBitmap("enemy_plane1.png");
		Bitmap enemyMidPlaneBitmap = loadBitmap("enemy_plane2.png");
		Bitmap enemyBigPlaneBitmap= loadBitmap("enemy_plane3.png");
		Bitmap enemyKingPlaneBitmap=loadBitmap("enemy_plane4.png");
		
		mEnemyPlane[0] = new  EnemyPlane( 0, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				30,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
		);
		mEnemyPlane[0].mAlive = true;
		
		mEnemyPlane[1] = new  EnemyPlane( 1, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				30,  // 비행기가 나올 시간
				200, // 비행기가 나오는 X좌표
				50, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[1].mAlive = true;

		mEnemyPlane[2] = new  EnemyPlane( 2, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				30,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[2].mAlive = true;		

		mEnemyPlane[3] = new  EnemyPlane( 3, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				90,  // 비행기가 나올 시간
				700, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[3].mAlive = true;

		mEnemyPlane[4] = new  EnemyPlane( 4, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				90,  // 비행기가 나올 시간
				800, // 비행기가 나오는 X좌표
				50, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[4].mAlive = true;
		///////////////////////////////////////////////////
		mEnemyPlane[5] = new  EnemyPlane( 5, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				90,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[5].mAlive = true;
		
		mEnemyPlane[6] = new  EnemyPlane( 6, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				190,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[6].mAlive = true;		

		mEnemyPlane[7] = new  EnemyPlane( 7, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				190,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[7].mAlive = true;	

		mEnemyPlane[8] = new  EnemyPlane( 8, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				190,  // 비행기가 나올 시간
				500, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[8].mAlive = true;	

		mEnemyPlane[9] = new  EnemyPlane( 9, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				190,  // 비행기가 나올 시간
				700, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[9].mAlive = true;	
		
		//////////////////////////////////////////////
		mEnemyPlane[10] = new  EnemyPlane( 10, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				190,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[10].mAlive = true;
		
		mEnemyPlane[11] = new  EnemyPlane( 11, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				500,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[11].mAlive = true;
		
		mEnemyPlane[12] = new  EnemyPlane( 12, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				530,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				50, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[12].mAlive = true;
		
		mEnemyPlane[13] = new  EnemyPlane( 13, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				560,  // 비행기가 나올 시간
				500, // 비행기가 나오는 X좌표
				30, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[13].mAlive = true;
		
		mEnemyPlane[14] = new  EnemyPlane( 14, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				590,  // 비행기가 나올 시간
				700, // 비행기가 나오는 X좌표
				70, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[14].mAlive = true;
		
		///////////////////////////////
		mEnemyPlane[15] = new  EnemyPlane(15, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				590,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[15].mAlive = true;
		
		mEnemyPlane[16] = new  EnemyPlane( 16, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				600,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				50, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[16].mAlive = true;
		
		mEnemyPlane[17] = new  EnemyPlane( 17, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				610,  // 비행기가 나올 시간
				400, // 비행기가 나오는 X좌표
				30, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[17].mAlive = true;
		
		mEnemyPlane[18] = new  EnemyPlane(18, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				630,  // 비행기가 나올 시간
				280, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[18].mAlive = true;
		
		mEnemyPlane[19] = new  EnemyPlane( 19, 
				3,   // 비행기종류 
				1,   // 비행기행동타입
				640,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[19].mAlive = true;
		////////////////////////////////////////////////////
		mEnemyPlane[20] = new  EnemyPlane(20, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				640,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[20].mAlive = true;
		
		mEnemyPlane[21] = new  EnemyPlane(21, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				640,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[21].mAlive = true;
		
		mEnemyPlane[22] = new  EnemyPlane( 22, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				660,  // 비행기가 나올 시간
				200, // 비행기가 나오는 X좌표
				30, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[22].mAlive = true;
		
		mEnemyPlane[23] = new  EnemyPlane( 23, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				670,  // 비행기가 나올 시간
				700, // 비행기가 나오는 X좌표
				20, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[23].mAlive = true;
		
		mEnemyPlane[24] = new  EnemyPlane(24, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				680,  // 비행기가 나올 시간
				800, // 비행기가 나오는 X좌표
				40, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[24].mAlive = true;
		//////////////////////////////////////////////////////
		mEnemyPlane[25] = new  EnemyPlane( 25, 
				2,   // 비행기종류
				2,   // 비행기행동타입
				690,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				80,  // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[25].mAlive = true;
		
		mEnemyPlane[26] = new  EnemyPlane(26, 
				3,   // 비행기종류 
				1,   // 비행기행동타입
				700,  // 비행기가 나올 시간
				500, // 비행기가 나오는 X좌표
				20, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[26].mAlive = true;
		
		mEnemyPlane[27] = new  EnemyPlane(27, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				700,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				30, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[27].mAlive = true;
		
		mEnemyPlane[28] = new  EnemyPlane( 28, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				710,  // 비행기가 나올 시간
				450, // 비행기가 나오는 X좌표
				10, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[28].mAlive = true;
		
		mEnemyPlane[29] = new  EnemyPlane( 29, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				720,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				80, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[29].mAlive = true;
		///////////////////////////////////////////////////////
		mEnemyPlane[30] = new  EnemyPlane(30, 
				3,   // 비행기종류 
				1,   // 비행기행동타입
				730,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				30, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[30].mAlive = true;
		
		mEnemyPlane[31] = new  EnemyPlane(31, 
				3,   // 비행기종류
				1,   // 비행기행동타입
				740,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				20, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[31].mAlive = true;
		
		mEnemyPlane[32] = new  EnemyPlane( 32, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				740,  // 비행기가 나올 시간
				500, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[32].mAlive = true;
		
		mEnemyPlane[33] = new  EnemyPlane( 33, 
				3,   // 비행기종류 
				1,   // 비행기행동타입
				750,  // 비행기가 나올 시간
				700, // 비행기가 나오는 X좌표
				50, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[33].mAlive = true;
		
		mEnemyPlane[34] = new  EnemyPlane(34, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				760,  // 비행기가 나올 시간
				200, // 비행기가 나오는 X좌표
				10, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[34].mAlive = true;
		///////////////////////////////////////////////////////////////////////////
		mEnemyPlane[35] = new  EnemyPlane(35, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				770,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				50, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[35].mAlive = true;
		
		mEnemyPlane[36] = new  EnemyPlane(36, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				770,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				30, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[36].mAlive = true;
		
		mEnemyPlane[37] = new  EnemyPlane( 37, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				770,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				20, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[37].mAlive = true;
		
		mEnemyPlane[38] = new  EnemyPlane(38, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				780,  // 비행기가 나올 시간
				700, // 비행기가 나오는 X좌표
				60, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[38].mAlive = true;
		
		mEnemyPlane[39] = new  EnemyPlane( 39, 
				3,   // 비행기종류 
				1,   // 비행기행동타입
				780,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				70, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[39].mAlive = true;
		//////////////////////////////////////////////////////////
		mEnemyPlane[40] = new  EnemyPlane( 40, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				790,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[40].mAlive = true;
		
		mEnemyPlane[41] = new  EnemyPlane( 41, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				800,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				50, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[41].mAlive = true;
		
		mEnemyPlane[42] = new  EnemyPlane( 42, 
				1,   // 비행기종류
				1,   // 비행기행동타입
				800,  // 비행기가 나올 시간
				700, // 비행기가 나오는 X좌표
				70, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[42].mAlive = true;
		
		mEnemyPlane[43] = new  EnemyPlane( 43, 
				2,   // 비행기종류
				2,   // 비행기행동타입
				810,  // 비행기가 나올 시간
				800, // 비행기가 나오는 X좌표
				90, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[43].mAlive = true;
		
		mEnemyPlane[44] = new  EnemyPlane( 44, 
				3,   // 비행기종류 
				1,   // 비행기행동타입
				820,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				300, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[44].mAlive = true;
		///////////////////////////////////////////////////////////////////
		mEnemyPlane[45] = new  EnemyPlane( 45, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				830,  // 비행기가 나올 시간
				400, // 비행기가 나오는 X좌표
				40, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[45].mAlive = true;
		
		mEnemyPlane[46] = new  EnemyPlane( 46, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				830,  // 비행기가 나올 시간
				500, // 비행기가 나오는 X좌표
				20, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[46].mAlive = true;
		
		mEnemyPlane[47] = new  EnemyPlane( 47, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				840,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
			
		);	
		mEnemyPlane[47].mAlive = true;
		
		mEnemyPlane[48] = new  EnemyPlane( 48, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				840,  // 비행기가 나올 시간
				700, // 비행기가 나오는 X좌표
				30, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[48].mAlive = true;
		
		mEnemyPlane[49] = new  EnemyPlane( 49, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				850,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				80, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[49].mAlive = true;
		//////////////////////////////////////////////////
		mEnemyPlane[50] = new  EnemyPlane( 50, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				850,  // 비행기가 나올 시간
				500, // 비행기가 나오는 X좌표
				70, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[50].mAlive = true;
		
		mEnemyPlane[51] = new  EnemyPlane( 51, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				860,  // 비행기가 나올 시간
				600, // 비행기가 나오는 X좌표
				60, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[51].mAlive = true;
		
		mEnemyPlane[52] = new  EnemyPlane( 52, 
				3,   // 비행기종류 
				1,   // 비행기행동타입
				870,  // 비행기가 나올 시간
				600, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[52].mAlive = true;
		
		mEnemyPlane[53] = new  EnemyPlane( 53, 
				3,   // 비행기종류 
				1,   // 비행기행동타입
				880,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[53].mAlive = true;
		
		mEnemyPlane[54] = new  EnemyPlane( 54, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				890,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				40, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[54].mAlive = true;
		///////////////////////////////////////////////////////////
		mEnemyPlane[55] = new  EnemyPlane( 55, 
				2,   // 비행기종류 
				2,   // 비행기행동타입
				900,  // 비행기가 나올 시간
				600, // 비행기가 나오는 X좌표
				50, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[55].mAlive = true;
		
		mEnemyPlane[56] = new  EnemyPlane( 56, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				920,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				30, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[56].mAlive = true;
		
		mEnemyPlane[57] = new  EnemyPlane(57, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				930,  // 비행기가 나올 시간
				500, // 비행기가 나오는 X좌표
				70, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[57].mAlive = true;
		
		mEnemyPlane[58] = new  EnemyPlane( 58, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				930,  // 비행기가 나올 시간
				600, // 비행기가 나오는 X좌표
				50, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[58].mAlive = true;
		
		mEnemyPlane[59] = new  EnemyPlane( 59, 
				3,   // 비행기종류 
				1,   // 비행기행동타입
				940,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[59].mAlive = true;
		
		
		mEnemyPlane[60] = new  EnemyPlane( 60, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				1500,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				100, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[60].mAlive = true;
		
		mEnemyPlane[61] = new  EnemyPlane( 61, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				1530,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				50, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[61].mAlive = true;
		
		mEnemyPlane[62] = new  EnemyPlane( 62, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				1560,  // 비행기가 나올 시간
				700, // 비행기가 나오는 X좌표
				150, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[62].mAlive = true;
		
		mEnemyPlane[63] = new  EnemyPlane( 63, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				1590,  // 비행기가 나올 시간
				500, // 비행기가 나오는 X좌표
				30, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[63].mAlive = true;
		
		mEnemyPlane[64] = new  EnemyPlane( 64, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				1620,  // 비행기가 나올 시간
				900, // 비행기가 나오는 X좌표
				150, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[64].mAlive = true;
		
		mEnemyPlane[65] = new  EnemyPlane( 65, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				1650,  // 비행기가 나올 시간
				700, // 비행기가 나오는 X좌표
				70, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[65].mAlive = true;
		
		mEnemyPlane[66] = new  EnemyPlane( 66, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				1680,  // 비행기가 나올 시간
				400, // 비행기가 나오는 X좌표
				130, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[66].mAlive = true;
		
		mEnemyPlane[67] = new  EnemyPlane( 67, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				1710,  // 비행기가 나올 시간
				800, // 비행기가 나오는 X좌표
				60, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[67].mAlive = true;
		
		mEnemyPlane[68] = new  EnemyPlane( 68, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				1740,  // 비행기가 나올 시간
				100, // 비행기가 나오는 X좌표
				150, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[68].mAlive = true;
		
		mEnemyPlane[69] = new  EnemyPlane( 69, 
				1,   // 비행기종류 
				1,   // 비행기행동타입
				1770,  // 비행기가 나올 시간
				300, // 비행기가 나오는 X좌표
				40, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[69].mAlive = true;
		
		mEnemyPlane[70] = new  EnemyPlane( 70,  //왕왕
				4,   // 비행기종류 
				4,   // 비행기행동타입
				2500,  // 비행기가 나올 시간
				500, // 비행기가 나오는 X좌표
				0, 	 // 비행기가 나오는 Y좌표
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[70].mAlive = true;
		
		
	}//produce_Enemyplane end

	private Bitmap loadBitmap(String filename)	//이 메소드를 통해서 asset에 있는 이미지를 가져와 사용할수 있다.			
	{//에셋은 모든 파일을 원시파일로 관리하기 때문에  바이트스트림으로 읽기위해 에셋매니저를 사용해야함 
		Bitmap bm = null;
		try
		{
			AssetManager am = mMainContext.getAssets();
			BufferedInputStream buf = new BufferedInputStream(am.open(filename) );
			bm = BitmapFactory.decodeStream(buf);
		}
		catch(Exception ex)
		{}
		return bm;
	}	
	
	int mCount =0;
	int mFireCount =0;
	public void action()
	{
		mCount++;
		
		if(mCount> 10000)
		{
			mCount = 0;
		}
		
		if(mFireLevel == 1){ //첫 단계에는 20카운트에 한번씩 총알 발사
			if(mCount % 20 == 0) 
				startFire();
		}
		
		else if(mFireLevel == 2){ // 파워포션 먹으면 10카운트에 한번씩 총알 발사
			if(mCount % 10 == 0)
				startFire();
		}
		
		else if(mFireLevel == 3){ // 파워포션 먹으면 5카운트에 한번씩 총알 발사
			if(mCount % 5 == 0)
				startFire();
		}
		
		else if(mFireLevel == 4){ //슈퍼버튼 눌렀을때 작동 
			mFireCount++;
			if(mFireCount<=300){
				if(mCount % 4 == 0)
					startFire();
			}
			else{
				mFireLevel = beforeFireLevel; //슈퍼총알이 끝났을때 이전 레벨로 돌아오게 설정  
				mFireCount =0;
			}
		}
		
		for(int i=0; i< CLOUD_SIZE;i++)
		{
			mCloud[i].action();
		}
		
		for(int i=0; i<MYFIRE_SIZE;i++)
		{
			mMyFire[i].action(); //Fire클래스의 action은 총알이 발사되고 위로 가는 것을 설정 
		}
		
		for(int i=0;i<=1;i++)
		{
			mPowerPotion[i].action();
		}
		
		mMyPlane.action();
		mSuperPlane.action();
		
		for(int i=0; i< ENEMY_SIZE;i++)
		{
			mEnemyPlane[i].action();
		}
		
		//내 비행기가 파워포션 먹으면 레벨2 	
		if(mPowerPotion[0].mActive){
			if(mPowerPotion[0].mX > mMyPlane.mX - mMyPlane.mWidth/2 &&
					mPowerPotion[0].mX < mMyPlane.mX + mMyPlane.mWidth/2 &&
					mPowerPotion[0].mY > mMyPlane.mY - mMyPlane.mHeight/2 &&
					mPowerPotion[0].mY < mMyPlane.mY + mMyPlane.mHeight/2){
				
				mFireLevel=2;
				mMainActivity.soundLevelup();
				mPowerPotion[0].mActive = false;
			}
		}
		
		//내 비행기가 파워포션 또먹으면 레벨3 
		 if(mPowerPotion[1].mActive){
			if(mPowerPotion[1].mX > mMyPlane.mX - mMyPlane.mWidth/2 &&
					mPowerPotion[1].mX < mMyPlane.mX + mMyPlane.mWidth/2 &&
					mPowerPotion[1].mY > mMyPlane.mY - mMyPlane.mHeight/2 &&
					mPowerPotion[1].mY < mMyPlane.mY + mMyPlane.mHeight/2){
				
				mFireLevel=3;
				mMainActivity.soundLevelup();
				mPowerPotion[1].mActive = false;
			}
		}
		 
		 enemyFireMotion(); //언제 몇개의 총알을 발사할건지 결정 
		 for(int i=0; i< ENEMY_FIRE_SIZE;i++)
			{
				mEnemyFire[i].action(); //적 총알이 나가는 속도 설정과  적 총알이 화면밖으로 나갔을때 비활성화 시키는 작업 
			}	
		 
		 //적 비행기 충돌 구현 
		 for(int i=0; i< MYFIRE_SIZE;i++)
			{
				for(int j=0;j< ENEMY_SIZE ;j++)
				{
					checkExplosion(i,j);
				}
			}	
		 
		 //내 비행기 충돌 구현
		 checkMyExplosion();
		 
		 //폭발
		 for(int i=0; i<EXPLOSION_SIZE;i++)
			{
				mExplosion[i].action();
			}	
		 
		 //왕 사운드 
			 kingcount++;
		  if(kingcount ==2500 || kingcount == 3000){ 
			 mMainActivity.soundKing();
		 }
			 
		//필살기 호출했을때 작동 
		 if( mSuperPlane.mActive == true){
			for(int i=0;i<ENEMY_FIRE_SIZE;i++){
				if(mEnemyFire[i].mActive == true){
					if(		mEnemyFire[i].mX > mSuperPlane.mX-mSuperPlane.mWidth/2  && 
							mEnemyFire[i].mX < mSuperPlane.mX+mSuperPlane.mWidth/2  &&
							mEnemyFire[i].mY > mSuperPlane.mY-mSuperPlane.mHeight/2  &&
							mEnemyFire[i].mY < mSuperPlane.mY+mSuperPlane.mHeight/2
						){
						mEnemyFire[i].mActive=false;
					}
				}
			}
		 }
		 
	
	}//action end
	
	//내 비행기가 맞았을때 
	public void checkMyExplosion(){
		
		if(mMyPlane.mActive == true){
			for(int i=0;i<ENEMY_FIRE_SIZE;i++){
				if(mEnemyFire[i].mActive == true){
					if(		mMyPlane.mX > mEnemyFire[i].mX-mEnemyFire[i].mWidth/2  && 
							mMyPlane.mX < mEnemyFire[i].mX+mEnemyFire[i].mWidth/2  &&
							mMyPlane.mY > mEnemyFire[i].mY-mEnemyFire[i].mHeight/2  &&
							mMyPlane.mY < mEnemyFire[i].mY+mEnemyFire[i].mHeight/2
					){
						mEnemyFire[i].mActive = false;
						mMyPlane.mActive = false;
						mMainActivity.goFinish();
					}
				}
			}
		}
	}
	
	//내 총알이 적 비행기 맞을때 
	public void checkExplosion(int i, int j)// i는 내 총알 , j는 적 비행기 
	{
		if(mEnemyPlane[j].mActive == true && mEnemyPlane[j].mAlive == true && mMyFire[i].mActive == true)
		{
			if(		mMyFire[i].mX > mEnemyPlane[j].mX-mEnemyPlane[j].mWidth/2  && 
					mMyFire[i].mX < mEnemyPlane[j].mX+mEnemyPlane[j].mWidth/2  &&
					mMyFire[i].mY > mEnemyPlane[j].mY-mEnemyPlane[j].mHeight/2  &&
					mMyFire[i].mY < mEnemyPlane[j].mY+mEnemyPlane[j].mHeight/2
			)
			{
				mMyFire[i].mActive = false;
				mEnemyPlane[j].mEnergy -= mMyFire[i].mEnergy;
				for(int k=0; k<EXPLOSION_SIZE;k++)
				{
					if(mExplosion[k].mActive == false)
					{
						mExplosion[k].mActive = true;
						mExplosion[k].move( (int)mEnemyPlane[j].mX, (int)mEnemyPlane[j].mY);		
					}
				}
			}
            if(mEnemyPlane[j].mEnergy<=0)
            {
                mEnemyPlane[j].mActive = false;
                mEnemyPlane[j].mAlive = false;
                if(mEnemyPlane[j].mObjectKind ==1)              
                	mGage += 100;
                else if(mEnemyPlane[j].mObjectKind ==2)             
                	mGage += 200;
                else if(mEnemyPlane[j].mObjectKind ==3)             
                	mGage += 300;
                else if(mEnemyPlane[j].mObjectKind ==4)             
                	mGage += 400;
            }			
		}	
	}	
	
	//적 비행기 총알 
	public void enemyFireMotion()
	{
		for(int i=0; i< ENEMY_FIRE_SIZE;i++)
		{
			if(mEnemyPlane[mEnemyPlaneArray[i]].mActive == true) 
			{
				if(mEnemyPlane[mEnemyPlaneArray[i]].mTimeLine == mFireTimeArray[i])
				{
					if(mFireTypeArray[i] == 1) //총알을 하나씩 발사 
					{	
						for(int k=0; k< ENEMY_FIRE_SIZE; k++)
						{
				    		if(mEnemyFire[k].mActive == false)
				    		{
				    			
				    			mEnemyFire[k].startFire(
				    					mEnemyPlane[mEnemyPlaneArray[i]].mX, 
				    					mEnemyPlane[mEnemyPlaneArray[i]].mY,
				    					mMyPlane.mX, 
				    					mMyPlane.mY
				    					
				    			);
				    			mEnemyFire[k].setSpeed(mFireTypeArray[i]); // 적 미사일 속도가 결정됨 (그쪽 클래스에서)
				    			mEnemyFire[k].mActive = true;
				    			break;
				    		}
						}
					}
					
					else if(mFireTypeArray[i] == 2) //6개의 총알을 발사 
					{
						int count_temp = 6;
						// 수정
						for(int k=0; k< ENEMY_FIRE_SIZE; k++)
						{
				    		if(mEnemyFire[k].mActive == false)
				    		{
				    			mEnemyFire[k].startFire(
				    					mEnemyPlane[mEnemyPlaneArray[i]].mX, 
				    					mEnemyPlane[mEnemyPlaneArray[i]].mY,
				    					mMyPlane.mX - mScreenConfig.getX((count_temp-3) * mScreenConfig.getX(400)), 
				    					mMyPlane.mY - mScreenConfig.getY((count_temp-3) * mScreenConfig.getY(400))
				    					 );
				    			count_temp--;
				    			mEnemyFire[k].setSpeed(mFireTypeArray[i]);
				    			mEnemyFire[k].mActive = true;
				    			if(count_temp <= 0)
				    			{
				    				break;
				    			}
				    		}
						}
					}
					
					else if(mFireTypeArray[i] == 3) //10개의 총알을 발사
					{
						
						int count_temp = 10;
						for(int k=0; k< ENEMY_FIRE_SIZE; k++)
						{
				    		if(mEnemyFire[k].mActive == false)
				    		{
				    			mEnemyFire[k].startFire(
				    					mEnemyPlane[mEnemyPlaneArray[i]].mX, 
				    					mEnemyPlane[mEnemyPlaneArray[i]].mY,
				    					mEnemyPlane[mEnemyPlaneArray[i]].mX + Math.sin(Math.toRadians(36* k)) *  100,
				    					mEnemyPlane[mEnemyPlaneArray[i]].mY - Math.cos(Math.toRadians(36* k)) *  100
				    					 );
				    			count_temp--;
				    			mEnemyFire[k].setSpeed(mFireTypeArray[i]);
				    			mEnemyFire[k].mActive = true;
				    			if(count_temp <= 0)
				    			{
				    				break;
				    			}
				    		}
						}
					}	
					
					else if(mFireTypeArray[i] == 4) //총알을 15개씩 발사 
					{
						int count_temp = 15;
						// 수정
						for(int k=0; k< ENEMY_FIRE_SIZE; k++)
						{
				    		if(mEnemyFire[k].mActive == false)
				    		{
				    			mEnemyFire[k].startFire(
				    					mEnemyPlane[mEnemyPlaneArray[i]].mX - mScreenConfig.getX((count_temp-7) * 50), 
				    					mEnemyPlane[mEnemyPlaneArray[i]].mY,
				    					mMyPlane.mX, 
				    					mMyPlane.mY
				    					 );
				    			count_temp--;
				    			mEnemyFire[k].setSpeed(mFireTypeArray[i]);
				    			mEnemyFire[k].mActive = true;
				    			if(count_temp <= 0)
				    			{
				    				break;
				    			}
				    		}
						}
					}
				}
	    	}																																									
		}		
	}
	
			// 총쏘는 개체
			private int[] mEnemyPlaneArray = { 
		              0,  1,  2,  3,  4,  5,  6,  7,  8,  9,
					  10, 11, 12,  13,  14,  15,  16,  17,  18,  19, 
					  20, 21, 22,  23,  24,  25,  26,  27,  28,  29, 
					  30, 31, 32,  33,  34,  35,  36,  37,  38,  39, 
					  40, 41, 42,  43,  44,  45,  46,  47,  48,  49, 
					  50, 51, 52,  53,  54,  55,  56,  57,  58,  59,
					  60, 61, 62,  63,  64,  65,  66,  67,  68,  69,
					  70, 70, 70,  70,  70,  70,  70,  70,  70,  70,
					  70, 70, 70,  70,  70,  70,  70,  70,  70,  70,
					  70, 70, 70,  70,  70,  70,  70,  70,  70,  70,
					  70, 70, 70,  70,  70,  70,  70,  70,  70,  70,
					  70, 70, 70,  70,  70,  70,  70,  70,  70,  70
		            };	
			
			// 각 총알의 움직임
			private int[] mFireTypeArray = { 
		              1,  1,  1,  1,  1,  1,  1,  1,  1,  1,
		              1,  1,  3,  2,  3,  1,  3,  2,  2,  3,
					  1,  3,  2,  2,  1,  3,  3,  2,  1,  2,
					  1,  2,  3,  2,  1,  2,  3,  3,  2,  1,
					  3,  2,  3,  2,  1,  1,  2,  3,  1,  2,
					  2,  3,  2,  1,  2,  1,  2,  2,  2,  3,
					  2,  3,  2,  3,  3,  2,  2,  3,  3,  2,
					  4,  4,  2,  3,  2,  3,  4,  4,  2,  3,
					  2,  3,  4,  2,  3,  4,  4,  2,  3,  4,
					  3,  2,  4,  2,  2,  2,  3,  3,  2,  3,
					  4,  2,  3,  3,  3,  4,  4,  2,  2,  2,
					  3,  2,  3,  2,  2,  4,  2,  3,  2,  3
		          };		
			
			// 총쏘는 시간
		    private int[] mFireTimeArray = {
			     30, 30, 30, 30, 30, 30, 30, 30, 30,30,   		
			     30, 30, 30, 30, 30, 30, 30, 30, 30,30,   
			     30, 30, 30, 30, 30, 30, 30, 30, 30,30,   
			     30, 30, 30, 30, 30, 30, 30, 30, 30,30,   
			     30, 30, 30, 30, 30, 30, 30, 30, 30,30,   
			     30, 30, 30, 30, 30, 30, 30, 30, 30,30,
			     30, 30, 30, 30, 30, 30, 30, 30, 30,30,
			     30, 40, 50, 70, 100, 130, 140, 150, 155, 160,
			     170, 200, 220, 235, 259, 270, 300, 330, 340, 380,
			     400, 420, 450, 460, 470, 490, 510, 540, 550, 580,
			     600, 620, 630, 660, 690, 720, 740, 770, 780,800,
			     830, 860, 870, 890, 900, 930, 960, 970, 990, 1000
		    };	
	
	//내 비행기 총알
    public void startFire() //총알 발사되는 메소드 
    {
    	if(mMyPlane.mActive== false) //비행기가 없으면 미사일도 날아가면 안되니까 조건문을 통해서 걸러냄 
    		return;
    	
    	for(int i=0; i<MYFIRE_SIZE;i++)
    	{
    		if(mMyFire[i].mActive == false) //총알 활성화가 종료되면 다시 호출돼 계속 순환하는 형태 
    		{
    			
    			if(mFireLevel == 1)
    			{
	    			mMyFire[i].mEnergy = 10;
	    			mMyFire[i].mType = 1;
	    			mMyFire[i].move(mMyPlane.mX, mMyPlane.mY);
	    			mMyFire[i].mActive = true;
	    			break;
    			}
    			else if(mFireLevel == 2)
    			{
    				
	    			mMyFire[i].mEnergy = 15;
	    			mMyFire[i].mType = 2;
	    			mMyFire[i].move(mMyPlane.mX, mMyPlane.mY);
	    			mMyFire[i].mActive = true;
	    			break;
    			} 
    			
    			else if(mFireLevel == 3){
    				mMyFire[i].mEnergy = 20;
	    			mMyFire[i].mType = 3;
	    			mMyFire[i].move(mMyPlane.mX, mMyPlane.mY);
	    			mMyFire[i].mActive = true;
	    			break;
    			}
    			
    			else if(mFireLevel == 4){
    				mMyFire[i].mEnergy = 40;
	    			mMyFire[i].mType = 4;
	    			mMyFire[i].move(mMyPlane.mX, mMyPlane.mY);
	    			mMyFire[i].mActive = true;
	    			break;
    			}
    		}
    	}
    }	
    
	@Override
	public  void onDraw(Canvas canvas){
		//super.onDraw(canvas);
		if(mDrawCls == false)
			return;
		
		canvas.drawColor(Color.rgb(67, 116, 217));// 바탕을 그려
		 
		for(int i =0; i< CLOUD_SIZE; i++)
        {
        	mCloud[i].draw(canvas);
        }  
		
        for(int i=0;i<MYFIRE_SIZE;i++)
        {
        	mMyFire[i].draw(canvas); //총알 그려 
        }	
		
        for(int i=0; i< ENEMY_SIZE;i++) //적 비행기 그려 
        {        	
        	mEnemyPlane[i].draw(canvas);
        }
        
        for(int i=0; i<=1;i++)
        {
        	mPowerPotion[i].draw(canvas);
        }
        
        mMyPlane.draw(canvas); //내 비행기 그려
        mSuperButton.draw(canvas); //슈퍼버튼 
        
        
        //적 미사일
        for(int i=0; i< ENEMY_FIRE_SIZE;i++)
        {        	
        	mEnemyFire[i].draw(canvas);
        }	
        
        //폭발 이미지 
        for(int i=0;i<EXPLOSION_SIZE;i++)
        {
        	mExplosion[i].draw(canvas);
        }   	
        
        mSuperPlane.draw(canvas); // 슈퍼비행기 
        
        //게이지 화면에 표시 
        Paint paint = new Paint();
		paint.setTextSize(32);
		paint.setColor(Color.YELLOW);
		paint.setTextAlign(Paint.Align.RIGHT);
		
		if(mGage <=GAGE_LIMET) //게이지가 1500이하면 계속 채워나가고 
		canvas.drawText( mGage + "",
				(float)mScreenConfig.getX(900), (float)mScreenConfig.getY(100), paint);
		else{
			canvas.drawText( GAGE_LIMET + "",// 1500 이상 넘으면 1500으로 고정 시키고 더이상 오르지 못하게 한다. 
					(float)mScreenConfig.getX(900), (float)mScreenConfig.getY(100), paint);
		}
		
		 if(mGage >= GAGE_LIMET){ //게이지가 1000이 되면 버튼 생성 
				mSuperFireButton.mActive = true;
				mSuperFireButton.draw(canvas);
				flag = true;
			}
	}
	
	int pointer_id = -1;
	@Override
	public boolean onTouchEvent(MotionEvent event){
		
		final int action = event.getAction();
    	pointer_id = event.getPointerId(0);    
 	
		switch(action & MotionEvent.ACTION_MASK){
    	case MotionEvent.ACTION_DOWN:
    	case MotionEvent.ACTION_MOVE:
    		x2 = event.getX(); //내 손가락이 찍은 x좌표
   		 	y2 = event.getY();// 내 손가락이 찍은 y좌표 
   		 	
   		 	//손가락이 내 비행기에 있으면 움직이게 설정
 			if(x2 > mMyPlane.mX - mMyPlane.mWidth/2 &&
				x2 < mMyPlane.mX + mMyPlane.mWidth/2 &&
				y2 > mMyPlane.mY - mMyPlane.mHeight/2 &&
				y2 < mMyPlane.mY + mMyPlane.mHeight/2){
    			mMyPlane.move2((x2), (y2));
    		}

 			//필살기 버튼을 클릭하면 !! 
 			if(x2 > mSuperButton.mX - mSuperButton.mWidth/2 &&
 					x2 < mSuperButton.mX + mSuperButton.mWidth/2 &&
 					y2 > mSuperButton.mY - mSuperButton.mHeight/2 &&
 					y2 < mSuperButton.mY + mSuperButton.mHeight/2){
 					
 					//필살 비행기가 스물스물 아래에서 위로 올라오게 설정
 					mSuperPlane.mActive = true;
 					mSuperButton.mActive = false;
 	    		}
 			
 			//슈퍼총알 버튼을 클릭하면 !! 
 			if(flag == true && mFireLevel==2){
	 			if(x2 > mSuperFireButton.mX - mSuperFireButton.mWidth/2 &&
	 					x2 < mSuperFireButton.mX + mSuperFireButton.mWidth/2 &&
	 					y2 > mSuperFireButton.mY - mSuperFireButton.mHeight/2 &&
	 					y2 < mSuperFireButton.mY + mSuperFireButton.mHeight/2){
	 					mFireLevel = 4;
	 					mSuperFireButton.mActive = false;
	 					flag = false;
	 					mGage = 0; // 게이지 0으로 초기화
	 					beforeFireLevel = 2; //슈퍼총알이 끝나면 다시 돌아갈 총알 레벨 저장
	 	    		}
 			}
 			
 			else if(flag == true && mFireLevel==3){
	 			if(x2 > mSuperFireButton.mX - mSuperFireButton.mWidth/2 &&
	 					x2 < mSuperFireButton.mX + mSuperFireButton.mWidth/2 &&
	 					y2 > mSuperFireButton.mY - mSuperFireButton.mHeight/2 &&
	 					y2 < mSuperFireButton.mY + mSuperFireButton.mHeight/2){
	 					mFireLevel = 4;
	 					mSuperFireButton.mActive = false;
	 					flag = false;
	 					mGage = 0; // 게이지 0으로 초기화
	 					beforeFireLevel = 3; //슈퍼총알이 끝나면 다시 돌아갈 총알 레벨 저장
	 	    		}
 			}
    		break;
    		
    	case MotionEvent.ACTION_UP:
    		break;
    	case MotionEvent.ACTION_CANCEL:
			
    		break;
    	}
    	return true;
    }
}
