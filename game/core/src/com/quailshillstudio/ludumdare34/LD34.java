package com.quailshillstudio.ludumdare34;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.quailshillstudio.ludumdare34.screens.GameScreen;
import com.quailshillstudio.ludumdare34.screens.SplashScreen;


public class LD34 extends Game {
	public OrthographicCamera camera;
	SpriteBatch batch;
	public SplashScreen splashScreen;
	public GameScreen gameScreen;
	public String deviceId;
	public boolean musicOn = true;
	protected Screen creditsScreen;
	public AssetManager manager;
	//AdsController adsController;
	
	
	public LD34(String deviceId){
		super();
		this.deviceId = deviceId;
		manager = new AssetManager();
	}
	
	public LD34(){
		super();
		manager = new AssetManager();
	}
	
	/*public LD34(AdsController androidLauncher) {
		super();
		this.adsController = androidLauncher;
		manager = new AssetManager();
	}*/

	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
		batch = new SpriteBatch();

		splashScreen = new SplashScreen(this);
     setScreen(splashScreen);
	}
 
	public void render() {
		super.render(); // important!
		
	}
 
	public void dispose() {
		batch.dispose();
	}
	
	public static int calculateXvalue(int val){
		return (val*Gdx.graphics.getWidth())/1280;
	}
	
	public static int calculateYvalue(int val){
		return (val*Gdx.graphics.getHeight())/800;
	}
 
}
