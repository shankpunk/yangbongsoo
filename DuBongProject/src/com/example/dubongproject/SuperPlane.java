package com.example.dubongproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SuperPlane {
	
	private Bitmap superplane;
	public double mX;
    public double mY;
    public double mWidth;//��
    public double mHeight;//���� 
    public ScreenConfig mScreenConfig;
    public boolean mAlive = false;		// ����ִ��� Ȯ��
	public boolean mActive = false;		// Ȱ��ȭ�Ǿ����� Ȯ��
	
    public SuperPlane(int x, int y, ScreenConfig screenconfig, Bitmap splane){
    	mX = x;
    	mY = y;
    	mScreenConfig = screenconfig;
    	mWidth = mScreenConfig.getX(1200);
		mHeight= mScreenConfig.getY(1200);
    	
    	superplane = Bitmap.createScaledBitmap(splane, (int)mWidth, (int)mHeight, false);
    }
    
    public void action(){
    	if(mActive == true){
    		mY -= mScreenConfig.getY(5);
    	}
    	
    	if(mY<-150)
    		this.mActive = false;
    }
    
    public void draw(Canvas canvas){    
    	  if(mActive == false) 
              return;
    	  canvas.drawBitmap(superplane,
                (int)(mX - mWidth/2), (int)(mY - mHeight/2), null);

}   
}
