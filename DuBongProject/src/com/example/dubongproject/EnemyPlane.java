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
	public double mDegree = 180;		// 현재 비행기의 방향
	public double mSpeed=1;				// 비행기의 속도
	public int mType =1;				
	public double mStartX;				
	public double mStartY;				
	public int mObjectKind;				// 비행기의 종류
	public int mEnergy = 10;			
	public int mStartLine = 0;			// 비행기가 나타날 시간
	public int mTimeLine = 0;			// 비행기의 움직임계산의 시간기준
	public int mBfDegree = 0;			// 움직이기 이전각도
	public ScreenConfig mScreenConfig;	

	
	public EnemyPlane(int num, int object, int type, int startline, double startx, double starty, ScreenConfig screenConfig, 
			Bitmap enemyPlane, Bitmap enemyMidPlane, Bitmap enemyBigPlane, Bitmap enemyKingPlane)
	{		
		mScreenConfig = screenConfig;
		mObjectKind = object; //비행기 종류
		mType= type; //비행기 행동타입
		mStartX = mScreenConfig.getX(startx); //비행기가 나오는 X좌표
		mStartY = mScreenConfig.getY(starty); //비행기가 나오는 Y좌표
		mStartLine = startline; //비행기가 나올시간
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
		//초기 생성시 mStartLine이 설정이 된다
		//이값을 계속 감소시킨 후 0이 되면 화면에 등장시킨다. 
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
				
				//Y축 밑으로 이동하면 종료
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
	
	// 타입별로 움직임을 구현한다.
	public void moveDirection()	{
		// 그냥 아래로 계속 내려와 
		if( this.mType == 1){
			mDegree = 180;
		}
		
		else if( this.mType == 2){		
			//100이 될때까지 쭉 내려오다가
			if(this.mTimeLine < 100 )
				mDegree = 180;
			//118이 될때까지 왼쪽으로 틀어서 방향바꾸고 
			else if(this.mTimeLine < 118 )
				mDegree = mDegree + 10;			
			//위로 올라가 
			else if(this.mTimeLine < 200 )
				mDegree = 0;	
		}
		
		else if (this.mType == 3){
			
			if(this.mTimeLine <80)
				mDegree = 180;
			//오른쪽으로 이동 하고 
			else if(this.mTimeLine < 100)
				mDegree = 90;
			//왼쪽으로 이동하고 
			else if(this.mTimeLine < 200)
				mDegree = -90; 
		}
		
		else if( this.mType == 4){
			
			// 밑으로 쭉 내려와
			if(this.mTimeLine < 50 )
				mDegree = 180;
			// 왼쪽으로 방향바꾸고
			else if(this.mTimeLine < 200 )
				mDegree = mDegree + 3;			
			// 오른쪽으로 이동  
			else if(this.mTimeLine < 250 )
				mDegree = 90;			
			// 왼쪽으로 이동
			else if(this.mTimeLine < 300 )
				mDegree = -90;		
			// 오른쪽으로 방향 바꾸고 
			else if(this.mTimeLine < 400 )
				mDegree = mDegree - 3;		
			// 왼쪽으로 방향 틀어서 이동
			else if(this.mTimeLine < 500 )
				mDegree = mDegree + 10;	
			// 왼쪽으로 방향 바꾸고
			else if(this.mTimeLine < 600 )
				mDegree = mDegree + 3;		
			// 오른쪽으로 이동
			else if(this.mTimeLine < 650 )
				mDegree = 90;			
			// 왼쪽으로 이동
			else if(this.mTimeLine < 700 )
				mDegree = -90;			
			
			else if(this.mTimeLine < 750)
				mDegree = 0;	
			else if(this.mTimeLine < 780)	
				mDegree = -90;
			
			else if(this.mTimeLine < 800 )
				mDegree = 180;
			// 왼쪽으로 방향바꾸고
			else if(this.mTimeLine < 850 )
				mDegree = mDegree + 3;			
			// 오른쪽으로 이동  
			else if(this.mTimeLine < 900 )
				mDegree = 90;			
			// 왼쪽으로 이동
			else if(this.mTimeLine < 950 )
				mDegree = -90;		
			// 오른쪽으로 방향 바꾸고 
			else if(this.mTimeLine < 1000 )
				mDegree = mDegree - 3;		
			// 왼쪽으로 방향 틀어서 이동
			else if(this.mTimeLine < 1050 )
				mDegree = mDegree + 10;	
			
		}
		
		this.mX = this.mX + Math.sin(Math.toRadians(this.mDegree)) *  mSpeed;
		this.mY = this.mY - Math.cos(Math.toRadians(this.mDegree)) *  mSpeed;
	}
	public void draw(Canvas canvas){		
		if(this.mActive == true ){
			if(mObjectKind == 2 ){ //2번 비행기만 회전하도록 설정 
				canvas.drawBitmap(
					rotate(mBitmap,
						(int)(this.mDegree),
						(int)this.mBfDegree),
						(int)(mX - mWidth/2), 
						(int)(mY - mHeight/2), null);
			}
			else{
				// 나머지는 회전 작용 안함
				canvas.drawBitmap(mBitmap,
					(int)(mX - mWidth/2), 
					(int)(mY - mHeight/2), null);
			}
		}
	}	
}
