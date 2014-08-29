package com.example.dubongproject;

import android.annotation.SuppressLint;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class MyPlane 
{
	private Bitmap mBitmap;
	private Bitmap mBitmapL;
	private Bitmap mBitmapR;
	public double mX;
	public double mY;
	public double mWidth;
	public double mHeight;
	private boolean mIsDraw = true;
	private boolean mIsAble = true;
	public boolean mActive = true;
	public ScreenConfig mScreenConfig;	
	public int mDirectionMode = 1;
	public PowerPotion mPowerPotion;
	public MainView mMainView;
	public MainActivity mGameActivity;

	
	
	public MyPlane( ScreenConfig screenConfig, Bitmap bitmaporg,int width, int height)
	{		
		mScreenConfig = screenConfig;
		mWidth  = screenConfig.getX(width);// ����� ���μ��� ũ�� 
		mHeight = screenConfig.getY(height);	
        mBitmap  = Bitmap.createScaledBitmap(bitmaporg, (int)mWidth, (int)mHeight, false);	
      
	}	

	public  void start_position(double x, double y)
	{
		mX = mScreenConfig.getX(x);
		mY = mScreenConfig.getY(y);
	}
	
	public void move2(double x, double y){
		mX = x;
		mY = y;
	}
	
	public void action()
	{
		 	
			if( mX - mWidth/2 < 0) //����̽��� ���� ������ ���� ���̻� ������ ������Ŵ 
				mX =  mWidth/2;
			
			else if( mX + mWidth/2 > mScreenConfig.SCREEN_WIDTH) // ����̽� ������ ������ ���� ���� 
				mX = mScreenConfig.SCREEN_WIDTH - mWidth/2;
			
			if( mY - mHeight/2 < 0)// ����̽��� ���� ������ ���� ���� 
				mY =  mHeight/2;
			
			if( mY + mHeight/2 > mScreenConfig.SCREEN_HEIGHT) //����̽��� �Ʒ��� ������ ���� ���� 
				mY = mScreenConfig.SCREEN_HEIGHT -mHeight/2;
	}
	

	public void draw(Canvas canvas)
	{		
		if(mIsDraw == true && mActive == true) 
		{	
				canvas.drawBitmap(mBitmap,(int)(mX - mWidth/2), (int)(mY - mHeight/2), null);

		}
	}
}
