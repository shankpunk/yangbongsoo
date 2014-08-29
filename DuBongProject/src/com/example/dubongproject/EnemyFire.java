package com.example.dubongproject;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class EnemyFire {
	public  int 	mNum;			
	private Bitmap 	mBitmap;		
	public double 	mX;				
	public double 	mY;				
	public double 	mMyPlainX;		
	public double 	mMyPlainY;		
	public double 	mEnemyX;		
	public double 	mEnemyY;		
	public double 	mBaseValue;		
	public double 	mWidth;			
	public double 	mHeight;		
	public int 		mSpeed = 5;			
	public boolean 	mActive = false;
	public ScreenConfig mScreenConfig;

	public EnemyFire( int num, ScreenConfig screenConfig,Bitmap bitmaporg){		
		mScreenConfig = screenConfig;
		mNum = num;
		mBitmap = bitmaporg;
		mWidth = mScreenConfig.getX(2);
		mHeight = mScreenConfig.getY(2);
	}	

	public void setSpeed(int speed){ 
		
		this.mSpeed = 10 + (speed * 3);
	}
	
	public  void startFire(	double enemyX, double enemyY, double myPlainX, double myPlainY){
		
		mX = enemyX;
		mY = enemyY;
		mEnemyX = enemyX;
		mEnemyY = enemyY;
		mMyPlainX = myPlainX;
		mMyPlainY = myPlainY;
		
		
		mBaseValue = Math.sqrt((myPlainX - enemyX) * (myPlainX - enemyX) + 
				(myPlainY - enemyY) * (myPlainY - enemyY)); //�� ���� ������ ũ�� 
			
	}
	
	public void action(){
		if(mActive == true)	{			
		
				mX += ((mMyPlainX - mEnemyX)/mBaseValue  * mSpeed/2.6); //��������ȭ ���Ѽ� �װɷ� ���ư��� !!  (�� �� ����� �ִ� ������ �̻����� ���ƿ���)
				mY += ((mMyPlainY - mEnemyY)/mBaseValue  * mSpeed/2.6);			
				
				//mSpeed/2.6���� ���ϴ°� ���ִ� �� �̻��� �ӵ��� Ȯ������ ���⼭ 2.6�����°� ���ָ� �ӵ��� ������ 
				
				//�Ѿ��� ȭ������� ������ ��Ȱ��ȭ 
				if( mY > mScreenConfig.SCREEN_HEIGHT || mY < 0)	{
					this.mActive = false;
				}
				if( mX > mScreenConfig.SCREEN_WIDTH || mX < 0){
					this.mActive = false;
				
			}
		}
	}
	
	public void draw(Canvas canvas){		
		if(mActive == true){
			canvas.drawBitmap(mBitmap,(int)(mX - mWidth/2), (int)(mY - mHeight/2), null);
		}
	}	
}
