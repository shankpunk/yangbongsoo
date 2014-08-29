package com.example.dubongproject;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MyFire 
{
    public  int mType=1; //총알의 타입
    private Bitmap mBitmap; //총알의 이미지1
    private Bitmap mBitmap2;//총알의 이미지2
    private Bitmap mBitmap3;//총알의 이미지3
    private Bitmap mBitmap4;//총알의 이미지4
    public double mX;//현재의 x
    public double mY;//현재의 y
    
    private double mWidth1;//폭
    private double mHeight1;//높이 
    
    private double mWidth2;//폭
    private double mHeight2;//높이 
    
    private double mWidth3;//폭
    private double mHeight3;//높이 
    
    private double mWidth4;//폭
    private double mHeight4;//높이 
    
    public int mSpeed = 20;//속도
    public int mEnergy = 10;//에너지

    public boolean mActive = false;
    public ScreenConfig mScreenConfig;

    public MyFire(int width1, int height1, int width2, int height2, int width3, int height3, int width4, int height4, ScreenConfig screenConfig,
    		Bitmap bitmaporg, Bitmap bitmaporg2, Bitmap bitmaporg3, Bitmap bitmaporg4){     
        mScreenConfig = screenConfig;
       
        mWidth1 = screenConfig.getX(width1);
        mHeight1 = screenConfig.getY(height1);
        
        mWidth2 =  screenConfig.getX(width2);
        mHeight2 = screenConfig.getY(height2);
        
        mWidth3 =  screenConfig.getX(width3);
        mHeight3 = screenConfig.getY(height3);
        
        mWidth4 =  screenConfig.getX(width4);
        mHeight4 = screenConfig.getY(height4);
        
        mBitmap= Bitmap.createScaledBitmap(bitmaporg, (int)mWidth1, (int)mHeight1, false);
        mBitmap2= Bitmap.createScaledBitmap(bitmaporg2, (int)mWidth2, (int)mHeight2, false);
        mBitmap3= Bitmap.createScaledBitmap(bitmaporg3, (int)mWidth3, (int)mHeight3, false);
        mBitmap4= Bitmap.createScaledBitmap(bitmaporg4, (int)mWidth4, (int)mHeight4, false);
      
    }   
    
    public  void move(double x, double y){ //총알의 좌표처리 
        mX = x;
        mY = y;
        
    }
    
    public void action(){
       
        if(mActive == true){
            mY -= mScreenConfig.getY(mSpeed);  
            if( mY < 0){ //총알이 위를 지나서 사라지면 
                this.mActive = false; //활성화 종료 
            }   
        }
    }
    
    public void draw(Canvas canvas){    
        if(mActive == false) // 총알 활성화가 종료되면 그리지 않게 ... 
            return;
        if(mType ==1)
            canvas.drawBitmap(mBitmap,
                (int)(mX - mWidth1/2), (int)(mY - mHeight1/2), null);
        
        else if(mType ==2)  //업그레이드 된 총알 구현부분 
            canvas.drawBitmap(mBitmap2,
                (int)(mX - mWidth2/2), (int)(mY - mHeight2/2), null);
        
        else if(mType ==3) // 2단계 업그레이드 
        	 canvas.drawBitmap(mBitmap3,
                     (int)(mX - mWidth3/2), (int)(mY - mHeight3/2), null);
        
        else if(mType ==4) // 슈퍼 총알  
       	 canvas.drawBitmap(mBitmap4,
                    (int)(mX - mWidth4/2), (int)(mY - mHeight4/2), null);
     
    }   
}
