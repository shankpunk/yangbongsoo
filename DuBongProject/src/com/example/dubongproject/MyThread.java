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
		mSurfaceHolder = surfaceHolder; //MyThread �����ڸ� ȣ���� �� SurfaceHolder�� ������
		this.mMainView = mMainView;  // ���κ� ���޹��� 
	}
	
	public void thread_action(boolean run){ 
		flag = run;
	}

	@Override
	public void run(){
		
			Canvas c; //ĵ������ �����. 
			while(flag){
				c = null;			
					c = mSurfaceHolder.lockCanvas(null); 
					//���� ���۸��� ���� ���ǽ�Ȧ�� ��ü�� ���� ĵ������ ��´� 
					//�����忡���� ���ǽ����� ĵ������ �����ϱ� ���� locCanvas�� ȣ���� 
					// ȭ�������� �� ���������� ĵ������ ��װ� ���� �Ҵ� 
					
					synchronized(mSurfaceHolder){ //�����尡 ������ �� ���ÿ� ���� ���� ó���� �� �����Ƿ� ���� �Ǹ� ó���ϵ��� ����ȭ�� �̿��� ���� ó�� ���� 
						try
						{			
							mMainView.action();
							mMainView.onDraw(c); //���ۿ� �׸���  ,,, ���� �׸��°��� �� Ŭ���� ������ draw���� drawBitmap�� ���� ����Ѵ� 			
						}
						catch(Exception e){}
					}
					if( c!= null){
						mSurfaceHolder.unlockCanvasAndPost(c); // �� �׷����� (ó���� ����Ǹ�) ��� Ǯ�� ĵ������ ������ ���κ信 ���� 
				}
			}
	}
}
