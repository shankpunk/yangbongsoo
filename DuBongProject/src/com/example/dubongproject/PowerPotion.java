package com.example.dubongproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.Toast;

public class PowerPotion {
	public Bitmap mBitmap;
	public double mX;					
	public double mY;					
	public double mWidth;					
	public double mHeight;	
	public ScreenConfig mScreenConfig;	
	public int mStartLine = 0;	
	public double mStartX;
	public double mStartY;	
	public int mTimeLine = 0;
	public boolean mAlive = false;		
	public boolean mActive = false;
	public int xInc =10;
	public int yInc = 10;
	public PowerPotion mPowerPotion;
	public MainView mMainView;
	public MainActivity mMainActivity;
	public PowerPotion(int startline, int startx, int starty, ScreenConfig screenConfig, Bitmap powerpotion)
	{		
		mScreenConfig = screenConfig;
		mStartX = mScreenConfig.getX(startx); //�Ŀ������� ������ X��ǥ
		mStartY = mScreenConfig.getY(starty); //�Ŀ������� ������ Y��ǥ
		mStartLine = startline; //�Ŀ������� ���ýð�
		mTimeLine = 0;
		mAlive = true;
		mWidth = mScreenConfig.getX(180);
		mHeight = mScreenConfig.getY(180);
		mBitmap = Bitmap.createScaledBitmap(powerpotion, (int)mWidth, (int)mHeight, false);
	}
	
	public  void moveStart(){
		mX = mStartX;
		mY = mStartY;
	}
	
	public  void move(int x, int y){
		mX = x;
		mY = y;
	}
	
	public void destory(){ 
	}
	
	public void action(){
		//�ʱ� ������ mStartLine�� ������ �ȴ�
		//�̰��� ��� ���ҽ�Ų �� 0�� �Ǹ� ȭ�鿡 �����Ų��. 
		if(mStartLine >= 0)
		{
			mStartLine--;
			if(mStartLine <=0){
				moveStart();
				mActive = true;
			}
		}
		
		else{
			if(mActive== true){
				mTimeLine++; 
				mX += xInc;
				mY += yInc;
				
				if(mX<0 || mX >mScreenConfig.SCREEN_WIDTH){
					xInc = -xInc;
					
				}
				if(mY<0 || mY>mScreenConfig.SCREEN_HEIGHT-100){ //�ֵ�� �߰��ؼ� ��ũ�� ���� ���̰� ���ܼ� -100 ���� 
					yInc = -yInc;
					
				}
			}
		}
	}
	
	public void draw(Canvas canvas){		
		if(this.mActive == true ){
			canvas.drawBitmap(mBitmap,
					(int)(mX - mWidth/2), 
					(int)(mY - mHeight/2), null);
			
		}
	}	
}
