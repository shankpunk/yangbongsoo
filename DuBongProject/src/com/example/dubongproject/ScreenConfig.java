package com.example.dubongproject;

// 화면의 설정을 맡는다.
public class ScreenConfig {
	public static  double SCREEN_WIDTH;
	public static  double SCREEN_HEIGHT;

	public static  double VIRTUAL_WIDTH;
	public static  double VIRTUAL_HEIGHT;

	public ScreenConfig(double ScreenWidth , double ScreenHeight)
	{
		SCREEN_WIDTH = ScreenWidth;
		SCREEN_HEIGHT = ScreenHeight;
	}
	public void setSize(double width, double height)
	{
		VIRTUAL_WIDTH = width;
		VIRTUAL_HEIGHT = height;
	}	
	public double getX(double x)
	{
		return (double)( x * SCREEN_WIDTH/VIRTUAL_WIDTH);
	}	
	public double getY(double y)
	{
		return (double)( y * SCREEN_HEIGHT/VIRTUAL_HEIGHT);
	}	
}