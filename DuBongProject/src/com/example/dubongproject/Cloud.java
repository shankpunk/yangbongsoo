package com.example.dubongproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Cloud {
	
	public double 	mX;				
	public double 	mY;				
	public Bitmap 	mBitmap;
	public double	mWidth;			
	public double	mHeight;		
	public ScreenConfig 	mScreenConfig;
	public int 				mSpeed = 20;
	
	public Cloud(int speed, double width, double height, int startx, int starty, 
			Bitmap cloud, ScreenConfig screenConfig){
		
		mScreenConfig = screenConfig;
		mWidth 	= mScreenConfig.getX(width);
		mHeight = mScreenConfig.getY(height);
		mSpeed 	= speed;
		mX 		= mScreenConfig.getX(startx);
		mY 		= mScreenConfig.getY(starty);
		mBitmap	= Bitmap.createScaledBitmap(cloud, (int)mWidth, (int)mHeight, false);
		
	}
	
	public void action()
	{
		mY = mY + mScreenConfig.getY(this.mSpeed);
		if(mY > mScreenConfig.SCREEN_HEIGHT)
		{
			mY = -200;
		}
	}
	
	public void draw(Canvas canvas)
	{		
		canvas.drawBitmap(mBitmap,(int)(mX - mWidth/2), (int)(mY - mHeight/2), null);
	}	
}
