package com.example.dubongproject;

import android.graphics.*;

public class EnemyPlane {
	public Bitmap mBitmap;				
	public double mX;					
	public double mY;					
	public double mWidth;					
	public double mHeight;					
	public boolean mAlive = false;		
	public boolean mActive = false;		
	public double mDegree = 180;		// ���� ������� ����
	public double mSpeed=1;				// ������� �ӵ�
	public int mType =1;				
	public double mStartX;				
	public double mStartY;				
	public int mObjectKind;				// ������� ����
	public int mEnergy = 10;			
	public int mStartLine = 0;			// ����Ⱑ ��Ÿ�� �ð�
	public int mTimeLine = 0;			// ������� �����Ӱ���� �ð�����
	public int mBfDegree = 0;			// �����̱� ��������
	public ScreenConfig mScreenConfig;	

	
	public EnemyPlane(int num, int object, int type, int startline, double startx, double starty, ScreenConfig screenConfig, 
			Bitmap enemyPlane, Bitmap enemyMidPlane, Bitmap enemyBigPlane, Bitmap enemyKingPlane)
	{		
		mScreenConfig = screenConfig;
		mObjectKind = object; //����� ����
		mType= type; //����� �ൿŸ��
		mStartX = mScreenConfig.getX(startx); //����Ⱑ ������ X��ǥ
		mStartY = mScreenConfig.getY(starty); //����Ⱑ ������ Y��ǥ
		mStartLine = startline; //����Ⱑ ���ýð�
		mTimeLine = 0;
		mAlive = true;
		
		if(mObjectKind == 1) {
			
			mEnergy = 10;
			mSpeed = 5;
			mWidth = mScreenConfig.getX(130);
			mHeight = mScreenConfig.getY(130);				
	        mBitmap= Bitmap.createScaledBitmap(enemyPlane, (int)mWidth, (int)mHeight, false);
		}
		else if(mObjectKind == 2) {
			
			mEnergy = 100;
			mSpeed = 3;
			mWidth = mScreenConfig.getX(200);
			mHeight = mScreenConfig.getY(200);				
	        mBitmap= Bitmap.createScaledBitmap(enemyMidPlane, (int)mWidth , (int)mHeight , false);   
		}		
		else if(mObjectKind == 3){
			
			mEnergy = 200;
			mSpeed = 1;
			mWidth = mScreenConfig.getX(300);
			mHeight = mScreenConfig.getY(300);				
	        mBitmap= Bitmap.createScaledBitmap(enemyBigPlane, (int)mWidth , (int)mHeight , false);   
		}
		else if(mObjectKind == 4){
			
			mEnergy = 3600;
			mSpeed = 5;
			mWidth = mScreenConfig.getX(500);
			mHeight = mScreenConfig.getY(500);				
	        mBitmap= Bitmap.createScaledBitmap(enemyKingPlane, (int)mWidth , (int)mHeight , false); 
		}
	}
	

	public  void moveStart(){
		mX = mStartX;
		mY = mStartY;
	}

	
	public void action()	{
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
				moveDirection();
				
				//Y�� ������ �̵��ϸ� ����
				if(mY > mScreenConfig.SCREEN_HEIGHT){
					mActive = false;
					mAlive = false;
								
				}
			}
		}
	}
	
	static Matrix mMatrix = new Matrix();
	public static int mTempBfDegree = 1000;
	
	public static Bitmap rotate(Bitmap mBitmap, int degree, int mTempBfDegree){
		if(mTempBfDegree - degree > 10 ||mTempBfDegree - degree < -10 )	
		{
			mMatrix.setRotate(degree, mBitmap.getWidth(), mBitmap.getHeight());
			Bitmap mBitmap_chg = Bitmap.createBitmap(mBitmap, 0,0, mBitmap.getWidth(),mBitmap.getHeight(),mMatrix, true);
			mBitmap = mBitmap_chg;
			mTempBfDegree = degree;
		}
		return mBitmap;
	}
	
	// Ÿ�Ժ��� �������� �����Ѵ�.
	public void moveDirection()	{
		// �׳� �Ʒ��� ��� ������ 
		if( this.mType == 1){
			mDegree = 180;
		}
		
		else if( this.mType == 2){		
			//100�� �ɶ����� �� �������ٰ�
			if(this.mTimeLine < 100 )
				mDegree = 180;
			//118�� �ɶ����� �������� Ʋ� ����ٲٰ� 
			else if(this.mTimeLine < 118 )
				mDegree = mDegree + 10;			
			//���� �ö� 
			else if(this.mTimeLine < 200 )
				mDegree = 0;	
		}
		
		else if (this.mType == 3){
			
			if(this.mTimeLine <80)
				mDegree = 180;
			//���������� �̵� �ϰ� 
			else if(this.mTimeLine < 100)
				mDegree = 90;
			//�������� �̵��ϰ� 
			else if(this.mTimeLine < 200)
				mDegree = -90; 
		}
		
		else if( this.mType == 4){
			
			// ������ �� ������
			if(this.mTimeLine < 50 )
				mDegree = 180;
			// �������� ����ٲٰ�
			else if(this.mTimeLine < 200 )
				mDegree = mDegree + 3;			
			// ���������� �̵�  
			else if(this.mTimeLine < 250 )
				mDegree = 90;			
			// �������� �̵�
			else if(this.mTimeLine < 300 )
				mDegree = -90;		
			// ���������� ���� �ٲٰ� 
			else if(this.mTimeLine < 400 )
				mDegree = mDegree - 3;		
			// �������� ���� Ʋ� �̵�
			else if(this.mTimeLine < 500 )
				mDegree = mDegree + 10;	
			// �������� ���� �ٲٰ�
			else if(this.mTimeLine < 600 )
				mDegree = mDegree + 3;		
			// ���������� �̵�
			else if(this.mTimeLine < 650 )
				mDegree = 90;			
			// �������� �̵�
			else if(this.mTimeLine < 700 )
				mDegree = -90;			
			
			else if(this.mTimeLine < 750)
				mDegree = 0;	
			else if(this.mTimeLine < 780)	
				mDegree = -90;
			
			else if(this.mTimeLine < 800 )
				mDegree = 180;
			// �������� ����ٲٰ�
			else if(this.mTimeLine < 850 )
				mDegree = mDegree + 3;			
			// ���������� �̵�  
			else if(this.mTimeLine < 900 )
				mDegree = 90;			
			// �������� �̵�
			else if(this.mTimeLine < 950 )
				mDegree = -90;		
			// ���������� ���� �ٲٰ� 
			else if(this.mTimeLine < 1000 )
				mDegree = mDegree - 3;		
			// �������� ���� Ʋ� �̵�
			else if(this.mTimeLine < 1050 )
				mDegree = mDegree + 10;	
			
		}
		
		this.mX = this.mX + Math.sin(Math.toRadians(this.mDegree)) *  mSpeed;
		this.mY = this.mY - Math.cos(Math.toRadians(this.mDegree)) *  mSpeed;
	}
	public void draw(Canvas canvas){		
		if(this.mActive == true ){
			if(mObjectKind == 2 ){ //2�� ����⸸ ȸ���ϵ��� ���� 
				canvas.drawBitmap(
					rotate(mBitmap,
						(int)(this.mDegree),
						(int)this.mBfDegree),
						(int)(mX - mWidth/2), 
						(int)(mY - mHeight/2), null);
			}
			else{
				// �������� ȸ�� �ۿ� ����
				canvas.drawBitmap(mBitmap,
					(int)(mX - mWidth/2), 
					(int)(mY - mHeight/2), null);
			}
		}
	}	
}
