package com.example.dubongproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SuperButton {
	
	private Bitmap superpower; //이미지
	public double mX;
    public double mY;
    public double mWidth;//폭
    public double mHeight;//높이 
    public ScreenConfig mScreenConfig;
    public boolean mActive = true;
    
	public SuperButton(int x, int y,ScreenConfig screenconfig, Bitmap superimg){
	
		mX = x;
		mY = y;
		mScreenConfig = screenconfig;
		mWidth = mScreenConfig.getX(200);
		mHeight= mScreenConfig.getY(200);
		superpower = Bitmap.createScaledBitmap(superimg, (int)mWidth, (int)mHeight, false);
	}
	
	  public void draw(Canvas canvas){    
	       
		  		if(mActive == true){
	        	 canvas.drawBitmap(superpower,
	                     (int)(mX - mWidth/2), (int)(mY - mHeight/2), null);
		  		}
	     
	    }   
}
