package com.quailshillstudio.ludumdare34;

import com.badlogic.gdx.Gdx;

public class Globals {
	public static float SCREEN_WIDTH = Gdx.graphics.getWidth();  
	public static float SCREEN_HEIGHT = Gdx.graphics.getHeight(); 
	public static float ASPECT_RATIO = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
	public static float BASIC_WORLD_WIDTH = 70;
	public static float WORLD_WIDTH = BASIC_WORLD_WIDTH; //38
	public static float WORLD_HEIGHT= WORLD_WIDTH/ASPECT_RATIO;
	public static float X_PIXELS_PER_METER = SCREEN_WIDTH/WORLD_WIDTH;
	public static float Y_PIXELS_PER_METER= SCREEN_HEIGHT/WORLD_HEIGHT;
	
	
	public static void updateValues(){
		 WORLD_HEIGHT= WORLD_WIDTH/ASPECT_RATIO;
		 X_PIXELS_PER_METER = SCREEN_WIDTH/WORLD_WIDTH;
		 Y_PIXELS_PER_METER= SCREEN_HEIGHT/WORLD_HEIGHT;
	}
	
	public static float pixelsToMetersX(float pixels){
		return (float)  pixels / X_PIXELS_PER_METER;
	}
	
	public static float pixelsToMetersY(float pixels){
		return (float) pixels / Y_PIXELS_PER_METER;
	}


	public static float metersToPixelsX(float meters) {
		return meters*X_PIXELS_PER_METER;
	}
	
	public static float metersToPixelsY(float meters) {
		return meters*Y_PIXELS_PER_METER;
	}
	
}
	

