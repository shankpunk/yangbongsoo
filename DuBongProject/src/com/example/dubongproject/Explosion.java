package com.example.dubongproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion {
	private Bitmap 			mBitmap;		
	public double 			mX;				
	public double 			mY;				
	private double 			mWidth;			
	private double 			mHeight;		

	private ScreenConfig 	mScreenConfig;	
	private int 			mTimeLine;		
	public boolean 			mActive;		

	public Explosion( 	ScreenConfig screenConfig,Bitmap mBitmaporg){	
		
		mScreenConfig = screenConfig;
		mTimeLine = 0;
		mWidth=screenConfig.getX(100);
		mHeight=screenConfig.getY(100);	
		mBitmap= Bitmap.createScaledBitmap(mBitmaporg, (int)mWidth, (int)mHeight, false);
	
	}

	public  void move(int x, int y){
		mX = x;
		mY = y;
		mTimeLine = 0;	
		mActive= true;
	}
	
	public void action()	{
		if(mActive== true)
		{
			mTimeLine++;
			if(mTimeLine > 30)
			{
				mActive = false;				
			}
		}
	}
	
	public void draw(Canvas canvas)	{		
		if(this.mActive == true ){
			if(mTimeLine %3 == 0){
				if(mTimeLine < 10){//±ôºý°Å¸®°Ô ÇÏ±â À§ÇØ¼­ 
					canvas.drawBitmap(mBitmap,
						(int)(mX - mWidth/2), (int)(mY), null);
				}
			}
			
		}		
	}	
}
