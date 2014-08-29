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
	
	public boolean mDrawCls = false; //�ش� ���ǿ� �¾ƾ� �׸��� �ְ� �����ϱ� ���� ��ġ 
	private int mFireLevel = 1; 
	private ScreenConfig mScreenConfig;
	private MyPlane mMyPlane;	// �� �����
	private final static int MYFIRE_SIZE =20; //�� �Ѿ�20�� 
	private MyFire[] mMyFire; //���Ѿ� �迭 
	private PowerPotion[] mPowerPotion;//�Ŀ����� 
	private final static int ENEMY_SIZE = 71; //������� 70�� + �� 1��
	private EnemyPlane[] mEnemyPlane;
	private final static int EXPLOSION_SIZE = 71;
	public Explosion[] mExplosion;
	private final static int ENEMY_FIRE_SIZE =120; //�� �Ѿ� ���� 70�� + �� �̻��� 50�� 
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
	// ������	
	public MainView(Context context, AttributeSet attr){ //�����ڿ��� ������ ������Ŵ , SurfaceView ���� �����۾��� 
		super(context, attr);
		SurfaceHolder holder = getHolder(); //���ǽ� �並 �����̴°� ���ǽ�Ȧ���ϱ� �����ڿ��� �ݹ��Լ� ��� 
		holder.addCallback(this);
		
		mMainThread = new MyThread(holder,this); //���ǽ�Ȧ���� ������ ������ ���� 
		setFocusable(true);
		mMainContext = context; // ��Ʈ�� �̹����� ���� ���ؼ��� Context�� �ʿ� 
	}
	
	//SurfaceHolder �������̽� ������ �ʼ������� �������̵� ����� �ϴ� �κ� 
    public void surfaceCreated(SurfaceHolder holder){ //���ǽ� �䰡 �����ɶ� 
    	
		mMainThread.thread_action(true); 
		setFocusable(true);
		mMainThread.start();
		
    }
    
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {}
    
    public void surfaceDestroyed(SurfaceHolder holder){ //���ǽ� �䰡 ����ɶ� 
    	
    	mMainThread.thread_action(false); //������ ����
    	kingcount = 0;
    	shotcount = 0;
    }   	
    
	// �ʱ� ������ �ʱ�ȭ�� ���
	public void make(int width, int height, MainActivity mMainActivity)
	{				
		
		mScreenConfig = new ScreenConfig(width,height);	// screenconfig�� ���� ���μ��� ũ�� ���� 	
		mScreenConfig.setSize(1000,2000); // �������� ������ ���� ���� ũ�� ����
		 this.mMainActivity = mMainActivity;
		
		//�Ŀ����� ��ü  ���� 
		Bitmap powerpotionBitmap =loadBitmap("powerpotion.png");
		mPowerPotion = new PowerPotion[2];
		
		mPowerPotion[0] = new PowerPotion(190, 500, 0, mScreenConfig,powerpotionBitmap);
		mPowerPotion[1] = new PowerPotion(1300, 500, 0, mScreenConfig,powerpotionBitmap);
		
		// ������� �ʱ�ȭ 
		Bitmap myPlaneBitmap   = loadBitmap("plane.png");
		mMyPlane = new MyPlane(mScreenConfig, myPlaneBitmap , 250 , 250);//�̹��� ������ ���� MyPlane ��ü ������ �ʱ�ȭ�� ���� 
		mMyPlane.start_position(500, 1500);//500,1000 �� ����� ��ġ�ϰ� �� 
		
		// �Ѿ� �ʱ�ȭ 
		Bitmap myFireBitmap   = loadBitmap("myfire.png");
		Bitmap myFireBitmap2  = loadBitmap("myfire2.png");		
		Bitmap myFireBitmap3  = loadBitmap("myfire3.png");	
		Bitmap myFireBitmap4  = loadBitmap("special_fire.png");
		mMyFire = new MyFire[MYFIRE_SIZE]; //�Ѿ� ��ü ���� 
		for(int i = 0; i<MYFIRE_SIZE;i++)
		{
			mMyFire[i] = new MyFire(70, 70,170,170,240,240,300,300, mScreenConfig, myFireBitmap, myFireBitmap2, myFireBitmap3, myFireBitmap4); //�� ��ü���� �ʱ�ȭ �۾� 
			mMyFire[i].mActive=false;
		}
		
		//�����Ѿ˹�ư �ʱ�ȭ
		Bitmap superfire = loadBitmap("superfirebutton.png");
		mSuperFireButton = new SuperFireButton(70, 1100, mScreenConfig, superfire);
		
		//���� ��ư �ʱ�ȭ 
		Bitmap superpower   = loadBitmap("superbutton.png");
		mSuperButton = new SuperButton(650 ,1100 , mScreenConfig , superpower);
		
		//���� ����� �ʱ�ȭ
		Bitmap superplane = loadBitmap("super.png");
		mSuperPlane = new SuperPlane(380,1450, mScreenConfig, superplane);
		
		//������� �ʱ�ȭ 
		mEnemyPlane = new EnemyPlane[ENEMY_SIZE]; // ENEMY_SIZE = 71 
		produce_Enemyplane();
		
		//�� �Ѿ� �ʱ�ȭ 
		Bitmap enemyfireBitmap = loadBitmap("enemy_fire.png");
		mEnemyFire = new EnemyFire[ENEMY_FIRE_SIZE];
		
		for(int i = 0; i<ENEMY_FIRE_SIZE;i++)
		{
			mEnemyFire[i] = new EnemyFire( 1, mScreenConfig, enemyfireBitmap);
			mEnemyFire[i].mActive=false;
		}
		
		//����ó�� �ʱ�ȭ 
		Bitmap explosionBitmap = loadBitmap("explosion.png");		
		mExplosion = new Explosion[EXPLOSION_SIZE]; 
		for(int i = 0; i<EXPLOSION_SIZE;i++)
		{
			mExplosion[i] = new Explosion( mScreenConfig, explosionBitmap);
		}
		
		//���� �ʱ�ȭ 
		Bitmap cloud1 = loadBitmap("cloud1.png");
		Bitmap cloud2 = loadBitmap("cloud2.png");
		Bitmap cloud3 = loadBitmap("cloud3.png");
		
		mCloud = new Cloud[CLOUD_SIZE];
		mCloud[0] = new Cloud(14,250,200,100,-200,cloud1,mScreenConfig);
		mCloud[1] = new Cloud(18,300,250,600,-1000,cloud2,mScreenConfig);
		mCloud[2] = new Cloud(20,500,200,800,-800,cloud3, mScreenConfig);
		mCloud[3] = new Cloud(25,300,250,200,-1800,cloud1,mScreenConfig);
		mCloud[4] = new Cloud(30,250,300,-100,-600,cloud2,mScreenConfig);

		// �׸��⸦ �����ص� ��
		mDrawCls = true;		
	}//make �޼ҵ� end 
	
	public void produce_Enemyplane(){
		
		Bitmap enemyPlaneBitmap =loadBitmap("enemy_plane1.png");
		Bitmap enemyMidPlaneBitmap = loadBitmap("enemy_plane2.png");
		Bitmap enemyBigPlaneBitmap= loadBitmap("enemy_plane3.png");
		Bitmap enemyKingPlaneBitmap=loadBitmap("enemy_plane4.png");
		
		mEnemyPlane[0] = new  EnemyPlane( 0, 
				1,   // ���������
				1,   // ������ൿŸ��
				30,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
		);
		mEnemyPlane[0].mAlive = true;
		
		mEnemyPlane[1] = new  EnemyPlane( 1, 
				1,   // ���������
				1,   // ������ൿŸ��
				30,  // ����Ⱑ ���� �ð�
				200, // ����Ⱑ ������ X��ǥ
				50, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[1].mAlive = true;

		mEnemyPlane[2] = new  EnemyPlane( 2, 
				1,   // ���������
				1,   // ������ൿŸ��
				30,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[2].mAlive = true;		

		mEnemyPlane[3] = new  EnemyPlane( 3, 
				1,   // ���������
				1,   // ������ൿŸ��
				90,  // ����Ⱑ ���� �ð�
				700, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[3].mAlive = true;

		mEnemyPlane[4] = new  EnemyPlane( 4, 
				1,   // ���������
				1,   // ������ൿŸ��
				90,  // ����Ⱑ ���� �ð�
				800, // ����Ⱑ ������ X��ǥ
				50, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[4].mAlive = true;
		///////////////////////////////////////////////////
		mEnemyPlane[5] = new  EnemyPlane( 5, 
				1,   // ���������
				1,   // ������ൿŸ��
				90,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[5].mAlive = true;
		
		mEnemyPlane[6] = new  EnemyPlane( 6, 
				1,   // ���������
				1,   // ������ൿŸ��
				190,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[6].mAlive = true;		

		mEnemyPlane[7] = new  EnemyPlane( 7, 
				1,   // ���������
				1,   // ������ൿŸ��
				190,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[7].mAlive = true;	

		mEnemyPlane[8] = new  EnemyPlane( 8, 
				1,   // ���������
				1,   // ������ൿŸ��
				190,  // ����Ⱑ ���� �ð�
				500, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[8].mAlive = true;	

		mEnemyPlane[9] = new  EnemyPlane( 9, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				190,  // ����Ⱑ ���� �ð�
				700, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[9].mAlive = true;	
		
		//////////////////////////////////////////////
		mEnemyPlane[10] = new  EnemyPlane( 10, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				190,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[10].mAlive = true;
		
		mEnemyPlane[11] = new  EnemyPlane( 11, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				500,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[11].mAlive = true;
		
		mEnemyPlane[12] = new  EnemyPlane( 12, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				530,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				50, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[12].mAlive = true;
		
		mEnemyPlane[13] = new  EnemyPlane( 13, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				560,  // ����Ⱑ ���� �ð�
				500, // ����Ⱑ ������ X��ǥ
				30, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[13].mAlive = true;
		
		mEnemyPlane[14] = new  EnemyPlane( 14, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				590,  // ����Ⱑ ���� �ð�
				700, // ����Ⱑ ������ X��ǥ
				70, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[14].mAlive = true;
		
		///////////////////////////////
		mEnemyPlane[15] = new  EnemyPlane(15, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				590,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[15].mAlive = true;
		
		mEnemyPlane[16] = new  EnemyPlane( 16, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				600,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				50, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[16].mAlive = true;
		
		mEnemyPlane[17] = new  EnemyPlane( 17, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				610,  // ����Ⱑ ���� �ð�
				400, // ����Ⱑ ������ X��ǥ
				30, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[17].mAlive = true;
		
		mEnemyPlane[18] = new  EnemyPlane(18, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				630,  // ����Ⱑ ���� �ð�
				280, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[18].mAlive = true;
		
		mEnemyPlane[19] = new  EnemyPlane( 19, 
				3,   // ��������� 
				1,   // ������ൿŸ��
				640,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[19].mAlive = true;
		////////////////////////////////////////////////////
		mEnemyPlane[20] = new  EnemyPlane(20, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				640,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[20].mAlive = true;
		
		mEnemyPlane[21] = new  EnemyPlane(21, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				640,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[21].mAlive = true;
		
		mEnemyPlane[22] = new  EnemyPlane( 22, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				660,  // ����Ⱑ ���� �ð�
				200, // ����Ⱑ ������ X��ǥ
				30, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[22].mAlive = true;
		
		mEnemyPlane[23] = new  EnemyPlane( 23, 
				1,   // ���������
				1,   // ������ൿŸ��
				670,  // ����Ⱑ ���� �ð�
				700, // ����Ⱑ ������ X��ǥ
				20, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[23].mAlive = true;
		
		mEnemyPlane[24] = new  EnemyPlane(24, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				680,  // ����Ⱑ ���� �ð�
				800, // ����Ⱑ ������ X��ǥ
				40, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[24].mAlive = true;
		//////////////////////////////////////////////////////
		mEnemyPlane[25] = new  EnemyPlane( 25, 
				2,   // ���������
				2,   // ������ൿŸ��
				690,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				80,  // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[25].mAlive = true;
		
		mEnemyPlane[26] = new  EnemyPlane(26, 
				3,   // ��������� 
				1,   // ������ൿŸ��
				700,  // ����Ⱑ ���� �ð�
				500, // ����Ⱑ ������ X��ǥ
				20, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[26].mAlive = true;
		
		mEnemyPlane[27] = new  EnemyPlane(27, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				700,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				30, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[27].mAlive = true;
		
		mEnemyPlane[28] = new  EnemyPlane( 28, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				710,  // ����Ⱑ ���� �ð�
				450, // ����Ⱑ ������ X��ǥ
				10, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[28].mAlive = true;
		
		mEnemyPlane[29] = new  EnemyPlane( 29, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				720,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				80, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[29].mAlive = true;
		///////////////////////////////////////////////////////
		mEnemyPlane[30] = new  EnemyPlane(30, 
				3,   // ��������� 
				1,   // ������ൿŸ��
				730,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				30, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[30].mAlive = true;
		
		mEnemyPlane[31] = new  EnemyPlane(31, 
				3,   // ���������
				1,   // ������ൿŸ��
				740,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				20, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[31].mAlive = true;
		
		mEnemyPlane[32] = new  EnemyPlane( 32, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				740,  // ����Ⱑ ���� �ð�
				500, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[32].mAlive = true;
		
		mEnemyPlane[33] = new  EnemyPlane( 33, 
				3,   // ��������� 
				1,   // ������ൿŸ��
				750,  // ����Ⱑ ���� �ð�
				700, // ����Ⱑ ������ X��ǥ
				50, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[33].mAlive = true;
		
		mEnemyPlane[34] = new  EnemyPlane(34, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				760,  // ����Ⱑ ���� �ð�
				200, // ����Ⱑ ������ X��ǥ
				10, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[34].mAlive = true;
		///////////////////////////////////////////////////////////////////////////
		mEnemyPlane[35] = new  EnemyPlane(35, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				770,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				50, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[35].mAlive = true;
		
		mEnemyPlane[36] = new  EnemyPlane(36, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				770,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				30, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[36].mAlive = true;
		
		mEnemyPlane[37] = new  EnemyPlane( 37, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				770,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				20, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[37].mAlive = true;
		
		mEnemyPlane[38] = new  EnemyPlane(38, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				780,  // ����Ⱑ ���� �ð�
				700, // ����Ⱑ ������ X��ǥ
				60, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[38].mAlive = true;
		
		mEnemyPlane[39] = new  EnemyPlane( 39, 
				3,   // ��������� 
				1,   // ������ൿŸ��
				780,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				70, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[39].mAlive = true;
		//////////////////////////////////////////////////////////
		mEnemyPlane[40] = new  EnemyPlane( 40, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				790,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[40].mAlive = true;
		
		mEnemyPlane[41] = new  EnemyPlane( 41, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				800,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				50, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[41].mAlive = true;
		
		mEnemyPlane[42] = new  EnemyPlane( 42, 
				1,   // ���������
				1,   // ������ൿŸ��
				800,  // ����Ⱑ ���� �ð�
				700, // ����Ⱑ ������ X��ǥ
				70, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[42].mAlive = true;
		
		mEnemyPlane[43] = new  EnemyPlane( 43, 
				2,   // ���������
				2,   // ������ൿŸ��
				810,  // ����Ⱑ ���� �ð�
				800, // ����Ⱑ ������ X��ǥ
				90, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[43].mAlive = true;
		
		mEnemyPlane[44] = new  EnemyPlane( 44, 
				3,   // ��������� 
				1,   // ������ൿŸ��
				820,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				300, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[44].mAlive = true;
		///////////////////////////////////////////////////////////////////
		mEnemyPlane[45] = new  EnemyPlane( 45, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				830,  // ����Ⱑ ���� �ð�
				400, // ����Ⱑ ������ X��ǥ
				40, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[45].mAlive = true;
		
		mEnemyPlane[46] = new  EnemyPlane( 46, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				830,  // ����Ⱑ ���� �ð�
				500, // ����Ⱑ ������ X��ǥ
				20, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[46].mAlive = true;
		
		mEnemyPlane[47] = new  EnemyPlane( 47, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				840,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
			
		);	
		mEnemyPlane[47].mAlive = true;
		
		mEnemyPlane[48] = new  EnemyPlane( 48, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				840,  // ����Ⱑ ���� �ð�
				700, // ����Ⱑ ������ X��ǥ
				30, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[48].mAlive = true;
		
		mEnemyPlane[49] = new  EnemyPlane( 49, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				850,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				80, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[49].mAlive = true;
		//////////////////////////////////////////////////
		mEnemyPlane[50] = new  EnemyPlane( 50, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				850,  // ����Ⱑ ���� �ð�
				500, // ����Ⱑ ������ X��ǥ
				70, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[50].mAlive = true;
		
		mEnemyPlane[51] = new  EnemyPlane( 51, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				860,  // ����Ⱑ ���� �ð�
				600, // ����Ⱑ ������ X��ǥ
				60, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[51].mAlive = true;
		
		mEnemyPlane[52] = new  EnemyPlane( 52, 
				3,   // ��������� 
				1,   // ������ൿŸ��
				870,  // ����Ⱑ ���� �ð�
				600, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[52].mAlive = true;
		
		mEnemyPlane[53] = new  EnemyPlane( 53, 
				3,   // ��������� 
				1,   // ������ൿŸ��
				880,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[53].mAlive = true;
		
		mEnemyPlane[54] = new  EnemyPlane( 54, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				890,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				40, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[54].mAlive = true;
		///////////////////////////////////////////////////////////
		mEnemyPlane[55] = new  EnemyPlane( 55, 
				2,   // ��������� 
				2,   // ������ൿŸ��
				900,  // ����Ⱑ ���� �ð�
				600, // ����Ⱑ ������ X��ǥ
				50, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[55].mAlive = true;
		
		mEnemyPlane[56] = new  EnemyPlane( 56, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				920,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				30, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[56].mAlive = true;
		
		mEnemyPlane[57] = new  EnemyPlane(57, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				930,  // ����Ⱑ ���� �ð�
				500, // ����Ⱑ ������ X��ǥ
				70, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[57].mAlive = true;
		
		mEnemyPlane[58] = new  EnemyPlane( 58, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				930,  // ����Ⱑ ���� �ð�
				600, // ����Ⱑ ������ X��ǥ
				50, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[58].mAlive = true;
		
		mEnemyPlane[59] = new  EnemyPlane( 59, 
				3,   // ��������� 
				1,   // ������ൿŸ��
				940,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[59].mAlive = true;
		
		
		mEnemyPlane[60] = new  EnemyPlane( 60, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				1500,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				100, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[60].mAlive = true;
		
		mEnemyPlane[61] = new  EnemyPlane( 61, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				1530,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				50, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[61].mAlive = true;
		
		mEnemyPlane[62] = new  EnemyPlane( 62, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				1560,  // ����Ⱑ ���� �ð�
				700, // ����Ⱑ ������ X��ǥ
				150, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[62].mAlive = true;
		
		mEnemyPlane[63] = new  EnemyPlane( 63, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				1590,  // ����Ⱑ ���� �ð�
				500, // ����Ⱑ ������ X��ǥ
				30, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[63].mAlive = true;
		
		mEnemyPlane[64] = new  EnemyPlane( 64, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				1620,  // ����Ⱑ ���� �ð�
				900, // ����Ⱑ ������ X��ǥ
				150, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[64].mAlive = true;
		
		mEnemyPlane[65] = new  EnemyPlane( 65, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				1650,  // ����Ⱑ ���� �ð�
				700, // ����Ⱑ ������ X��ǥ
				70, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[65].mAlive = true;
		
		mEnemyPlane[66] = new  EnemyPlane( 66, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				1680,  // ����Ⱑ ���� �ð�
				400, // ����Ⱑ ������ X��ǥ
				130, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[66].mAlive = true;
		
		mEnemyPlane[67] = new  EnemyPlane( 67, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				1710,  // ����Ⱑ ���� �ð�
				800, // ����Ⱑ ������ X��ǥ
				60, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[67].mAlive = true;
		
		mEnemyPlane[68] = new  EnemyPlane( 68, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				1740,  // ����Ⱑ ���� �ð�
				100, // ����Ⱑ ������ X��ǥ
				150, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[68].mAlive = true;
		
		mEnemyPlane[69] = new  EnemyPlane( 69, 
				1,   // ��������� 
				1,   // ������ൿŸ��
				1770,  // ����Ⱑ ���� �ð�
				300, // ����Ⱑ ������ X��ǥ
				40, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[69].mAlive = true;
		
		mEnemyPlane[70] = new  EnemyPlane( 70,  //�տ�
				4,   // ��������� 
				4,   // ������ൿŸ��
				2500,  // ����Ⱑ ���� �ð�
				500, // ����Ⱑ ������ X��ǥ
				0, 	 // ����Ⱑ ������ Y��ǥ
				mScreenConfig, 
				enemyPlaneBitmap,
				enemyMidPlaneBitmap,
				enemyBigPlaneBitmap,
				enemyKingPlaneBitmap
				
		);	
		mEnemyPlane[70].mAlive = true;
		
		
	}//produce_Enemyplane end

	private Bitmap loadBitmap(String filename)	//�� �޼ҵ带 ���ؼ� asset�� �ִ� �̹����� ������ ����Ҽ� �ִ�.			
	{//������ ��� ������ �������Ϸ� �����ϱ� ������  ����Ʈ��Ʈ������ �б����� ���¸Ŵ����� ����ؾ��� 
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
		
		if(mFireLevel == 1){ //ù �ܰ迡�� 20ī��Ʈ�� �ѹ��� �Ѿ� �߻�
			if(mCount % 20 == 0) 
				startFire();
		}
		
		else if(mFireLevel == 2){ // �Ŀ����� ������ 10ī��Ʈ�� �ѹ��� �Ѿ� �߻�
			if(mCount % 10 == 0)
				startFire();
		}
		
		else if(mFireLevel == 3){ // �Ŀ����� ������ 5ī��Ʈ�� �ѹ��� �Ѿ� �߻�
			if(mCount % 5 == 0)
				startFire();
		}
		
		else if(mFireLevel == 4){ //���۹�ư �������� �۵� 
			mFireCount++;
			if(mFireCount<=300){
				if(mCount % 4 == 0)
					startFire();
			}
			else{
				mFireLevel = beforeFireLevel; //�����Ѿ��� �������� ���� ������ ���ƿ��� ����  
				mFireCount =0;
			}
		}
		
		for(int i=0; i< CLOUD_SIZE;i++)
		{
			mCloud[i].action();
		}
		
		for(int i=0; i<MYFIRE_SIZE;i++)
		{
			mMyFire[i].action(); //FireŬ������ action�� �Ѿ��� �߻�ǰ� ���� ���� ���� ���� 
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
		
		//�� ����Ⱑ �Ŀ����� ������ ����2 	
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
		
		//�� ����Ⱑ �Ŀ����� �Ǹ����� ����3 
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
		 
		 enemyFireMotion(); //���� ��� �Ѿ��� �߻��Ұ��� ���� 
		 for(int i=0; i< ENEMY_FIRE_SIZE;i++)
			{
				mEnemyFire[i].action(); //�� �Ѿ��� ������ �ӵ� ������  �� �Ѿ��� ȭ������� �������� ��Ȱ��ȭ ��Ű�� �۾� 
			}	
		 
		 //�� ����� �浹 ���� 
		 for(int i=0; i< MYFIRE_SIZE;i++)
			{
				for(int j=0;j< ENEMY_SIZE ;j++)
				{
					checkExplosion(i,j);
				}
			}	
		 
		 //�� ����� �浹 ����
		 checkMyExplosion();
		 
		 //����
		 for(int i=0; i<EXPLOSION_SIZE;i++)
			{
				mExplosion[i].action();
			}	
		 
		 //�� ���� 
			 kingcount++;
		  if(kingcount ==2500 || kingcount == 3000){ 
			 mMainActivity.soundKing();
		 }
			 
		//�ʻ�� ȣ�������� �۵� 
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
	
	//�� ����Ⱑ �¾����� 
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
	
	//�� �Ѿ��� �� ����� ������ 
	public void checkExplosion(int i, int j)// i�� �� �Ѿ� , j�� �� ����� 
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
	
	//�� ����� �Ѿ� 
	public void enemyFireMotion()
	{
		for(int i=0; i< ENEMY_FIRE_SIZE;i++)
		{
			if(mEnemyPlane[mEnemyPlaneArray[i]].mActive == true) 
			{
				if(mEnemyPlane[mEnemyPlaneArray[i]].mTimeLine == mFireTimeArray[i])
				{
					if(mFireTypeArray[i] == 1) //�Ѿ��� �ϳ��� �߻� 
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
				    			mEnemyFire[k].setSpeed(mFireTypeArray[i]); // �� �̻��� �ӵ��� ������ (���� Ŭ��������)
				    			mEnemyFire[k].mActive = true;
				    			break;
				    		}
						}
					}
					
					else if(mFireTypeArray[i] == 2) //6���� �Ѿ��� �߻� 
					{
						int count_temp = 6;
						// ����
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
					
					else if(mFireTypeArray[i] == 3) //10���� �Ѿ��� �߻�
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
					
					else if(mFireTypeArray[i] == 4) //�Ѿ��� 15���� �߻� 
					{
						int count_temp = 15;
						// ����
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
	
			// �ѽ�� ��ü
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
			
			// �� �Ѿ��� ������
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
			
			// �ѽ�� �ð�
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
	
	//�� ����� �Ѿ�
    public void startFire() //�Ѿ� �߻�Ǵ� �޼ҵ� 
    {
    	if(mMyPlane.mActive== false) //����Ⱑ ������ �̻��ϵ� ���ư��� �ȵǴϱ� ���ǹ��� ���ؼ� �ɷ��� 
    		return;
    	
    	for(int i=0; i<MYFIRE_SIZE;i++)
    	{
    		if(mMyFire[i].mActive == false) //�Ѿ� Ȱ��ȭ�� ����Ǹ� �ٽ� ȣ��� ��� ��ȯ�ϴ� ���� 
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
		
		canvas.drawColor(Color.rgb(67, 116, 217));// ������ �׷�
		 
		for(int i =0; i< CLOUD_SIZE; i++)
        {
        	mCloud[i].draw(canvas);
        }  
		
        for(int i=0;i<MYFIRE_SIZE;i++)
        {
        	mMyFire[i].draw(canvas); //�Ѿ� �׷� 
        }	
		
        for(int i=0; i< ENEMY_SIZE;i++) //�� ����� �׷� 
        {        	
        	mEnemyPlane[i].draw(canvas);
        }
        
        for(int i=0; i<=1;i++)
        {
        	mPowerPotion[i].draw(canvas);
        }
        
        mMyPlane.draw(canvas); //�� ����� �׷�
        mSuperButton.draw(canvas); //���۹�ư 
        
        
        //�� �̻���
        for(int i=0; i< ENEMY_FIRE_SIZE;i++)
        {        	
        	mEnemyFire[i].draw(canvas);
        }	
        
        //���� �̹��� 
        for(int i=0;i<EXPLOSION_SIZE;i++)
        {
        	mExplosion[i].draw(canvas);
        }   	
        
        mSuperPlane.draw(canvas); // ���ۺ���� 
        
        //������ ȭ�鿡 ǥ�� 
        Paint paint = new Paint();
		paint.setTextSize(32);
		paint.setColor(Color.YELLOW);
		paint.setTextAlign(Paint.Align.RIGHT);
		
		if(mGage <=GAGE_LIMET) //�������� 1500���ϸ� ��� ä�������� 
		canvas.drawText( mGage + "",
				(float)mScreenConfig.getX(900), (float)mScreenConfig.getY(100), paint);
		else{
			canvas.drawText( GAGE_LIMET + "",// 1500 �̻� ������ 1500���� ���� ��Ű�� ���̻� ������ ���ϰ� �Ѵ�. 
					(float)mScreenConfig.getX(900), (float)mScreenConfig.getY(100), paint);
		}
		
		 if(mGage >= GAGE_LIMET){ //�������� 1000�� �Ǹ� ��ư ���� 
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
    		x2 = event.getX(); //�� �հ����� ���� x��ǥ
   		 	y2 = event.getY();// �� �հ����� ���� y��ǥ 
   		 	
   		 	//�հ����� �� ����⿡ ������ �����̰� ����
 			if(x2 > mMyPlane.mX - mMyPlane.mWidth/2 &&
				x2 < mMyPlane.mX + mMyPlane.mWidth/2 &&
				y2 > mMyPlane.mY - mMyPlane.mHeight/2 &&
				y2 < mMyPlane.mY + mMyPlane.mHeight/2){
    			mMyPlane.move2((x2), (y2));
    		}

 			//�ʻ�� ��ư�� Ŭ���ϸ� !! 
 			if(x2 > mSuperButton.mX - mSuperButton.mWidth/2 &&
 					x2 < mSuperButton.mX + mSuperButton.mWidth/2 &&
 					y2 > mSuperButton.mY - mSuperButton.mHeight/2 &&
 					y2 < mSuperButton.mY + mSuperButton.mHeight/2){
 					
 					//�ʻ� ����Ⱑ �������� �Ʒ����� ���� �ö���� ����
 					mSuperPlane.mActive = true;
 					mSuperButton.mActive = false;
 	    		}
 			
 			//�����Ѿ� ��ư�� Ŭ���ϸ� !! 
 			if(flag == true && mFireLevel==2){
	 			if(x2 > mSuperFireButton.mX - mSuperFireButton.mWidth/2 &&
	 					x2 < mSuperFireButton.mX + mSuperFireButton.mWidth/2 &&
	 					y2 > mSuperFireButton.mY - mSuperFireButton.mHeight/2 &&
	 					y2 < mSuperFireButton.mY + mSuperFireButton.mHeight/2){
	 					mFireLevel = 4;
	 					mSuperFireButton.mActive = false;
	 					flag = false;
	 					mGage = 0; // ������ 0���� �ʱ�ȭ
	 					beforeFireLevel = 2; //�����Ѿ��� ������ �ٽ� ���ư� �Ѿ� ���� ����
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
	 					mGage = 0; // ������ 0���� �ʱ�ȭ
	 					beforeFireLevel = 3; //�����Ѿ��� ������ �ٽ� ���ư� �Ѿ� ���� ����
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
