package com.example.dubongproject;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MyFire 
{
    public  int mType=1; //�Ѿ��� Ÿ��
    private Bitmap mBitmap; //�Ѿ��� �̹���1
    private Bitmap mBitmap2;//�Ѿ��� �̹���2
    private Bitmap mBitmap3;//�Ѿ��� �̹���3
    private Bitmap mBitmap4;//�Ѿ��� �̹���4
    public double mX;//������ x
    public double mY;//������ y
    
    private double mWidth1;//��
    private double mHeight1;//���� 
    
    private double mWidth2;//��
    private double mHeight2;//���� 
    
    private double mWidth3;//��
    private double mHeight3;//���� 
    
    private double mWidth4;//��
    private double mHeight4;//���� 
    
    public int mSpeed = 20;//�ӵ�
    public int mEnergy = 10;//������

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
    
    public  void move(double x, double y){ //�Ѿ��� ��ǥó�� 
        mX = x;
        mY = y;
        
    }
    
    public void action(){
       
        if(mActive == true){
            mY -= mScreenConfig.getY(mSpeed);  
            if( mY < 0){ //�Ѿ��� ���� ������ ������� 
                this.mActive = false; //Ȱ��ȭ ���� 
            }   
        }
    }
    
    public void draw(Canvas canvas){    
        if(mActive == false) // �Ѿ� Ȱ��ȭ�� ����Ǹ� �׸��� �ʰ� ... 
            return;
        if(mType ==1)
            canvas.drawBitmap(mBitmap,
                (int)(mX - mWidth1/2), (int)(mY - mHeight1/2), null);
        
        else if(mType ==2)  //���׷��̵� �� �Ѿ� �����κ� 
            canvas.drawBitmap(mBitmap2,
                (int)(mX - mWidth2/2), (int)(mY - mHeight2/2), null);
        
        else if(mType ==3) // 2�ܰ� ���׷��̵� 
        	 canvas.drawBitmap(mBitmap3,
                     (int)(mX - mWidth3/2), (int)(mY - mHeight3/2), null);
        
        else if(mType ==4) // ���� �Ѿ�  
       	 canvas.drawBitmap(mBitmap4,
                    (int)(mX - mWidth4/2), (int)(mY - mHeight4/2), null);
     
    }   
}
