package com.example.dubongproject;

import android.view.SurfaceHolder;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.*;


@SuppressLint("WrongCall")
public class MyThread extends Thread {
	
	private SurfaceHolder mSurfaceHolder;
	private MainView mMainView;
	private boolean flag = false;
	
	public MyThread(SurfaceHolder surfaceHolder, MainView mMainView)
	{
		mSurfaceHolder = surfaceHolder; //MyThread 생성자를 호출할 때 SurfaceHolder를 생성함
		this.mMainView = mMainView;  // 메인뷰 전달받음 
	}
	
	public void thread_action(boolean run){ 
		flag = run;
	}

	@Override
	public void run(){
		
			Canvas c; //캔버스를 만든다. 
			while(flag){
				c = null;			
					c = mSurfaceHolder.lockCanvas(null); 
					//더블 버퍼링을 위해 서피스홀더 객체를 통해 캔버스를 얻는다 
					//스레드에서는 서피스뷰의 캔버스를 독점하기 위해 locCanvas를 호출함 
					// 화면정보를 다 담을때까지 캔버스를 잠그고 버퍼 할당 
					
					synchronized(mSurfaceHolder){ //스레드가 동작할 때 동시에 여러 건이 처리될 수 있으므로 단일 건만 처리하도록 동기화를 이용해 동시 처리 제한 
						try
						{			
							mMainView.action();
							mMainView.onDraw(c); //버퍼에 그리기  ,,, 실제 그리는것은 각 클래스 마다의 draw에서 drawBitmap을 통해 출력한다 			
						}
						catch(Exception e){}
					}
					if( c!= null){
						mSurfaceHolder.unlockCanvasAndPost(c); // 다 그렸으면 (처리가 종료되면) 잠금 풀고 캔버스의 내용을 메인뷰에 전송 
				}
			}
	}
}
