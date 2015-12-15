package com.quailshillstudio.ludumdare34.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.TimeUtils;
import com.quailshillstudio.ludumdare34.LD34;

public class SplashScreen implements Screen{
    
    private SpriteBatch batch;
    private LD34 app;
    private Texture texture;
    private long startTime;
    private Sound splash;
    private int splashEffectDuration = 2500;
    private int screenDuration = 3000;
    private Pixmap pixmap = new Pixmap( Gdx.graphics.getWidth(), (int) (Gdx.graphics.getWidth() /1.6f), Pixmap.Format.RGBA4444);
	private boolean possible;
	private BitmapFont font;
	private GlyphLayout layout;
	private boolean flag;
	private boolean flagLoad;
    
    
    public SplashScreen(LD34 game) // ** constructor called initially **//
    {
        app = game; // ** get Game parameter **//
        batch = new SpriteBatch();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/Roboto-Thin.ttf"));
	     FreeTypeFontParameter params = new FreeTypeFontParameter();
	     //params.borderColor = Color.BLACK;
	     params.color = Color.BLACK;
	     //params.borderStraight = false;
	     //params.borderWidth = 2.5f;
	     params.size = (Gdx.graphics.getWidth()*17) /640;
	     font = generator.generateFont(params); // font size 12 pixels
	     generator.dispose(); // don't forget to dispose to avoid memory leaks!
	     layout = new GlyphLayout(); //dont do this every frame! Store it as member
        loadAssets();
        //app.mapDownloader.convertMapToETC1();
    }

    public void loadAssets(){
    	
    }

	@Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(1, 1, 1, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(app.camera.combined);
       
	        if(app.manager.update()) {
	            possible = true; 
	         }

        batch.begin();
        batch.draw(texture, 0, -(Gdx.graphics.getWidth() /1.6f - Gdx.graphics.getHeight()) / 2 , Gdx.graphics.getWidth(), Gdx.graphics.getWidth() /1.6f);
        /*
         * Create a picture in front of the Texture Splash.png to create a fondu effect
         * values in milliseconds : see above : 
         * splashEffectDuration = 1500;
         * screenDuration = 6000;
         */
        if(TimeUtils.millis() < (startTime+splashEffectDuration)){
        	 pixmap.setColor(1, 1, 1, 1-(((float)(TimeUtils.millis()-startTime)/splashEffectDuration)));
             pixmap.fill();
             Texture fadeText = new Texture(pixmap);
             //It's the textures responsibility now... get rid of the pixmap
             batch.draw(fadeText, 0, 0);
        }
        /*
        if(TimeUtils.millis() > (startTime+(screenDuration-splashEffectDuration))){
        	float alpha = (((float)(TimeUtils.millis()-(startTime+(screenDuration-splashEffectDuration)))/splashEffectDuration));
        	if(alpha >=1){alpha = 1;}
       	 	pixmap.setColor(1, 1, 1, alpha);
            pixmap.fill();
            Texture fadeText = new Texture(pixmap);
            //It's the textures responsibility now... get rid of the pixmap
            batch.draw(fadeText, 0, 0);
       }
        */
       batch.end();
       batch.begin();
       
	       if(!app.manager.update()){
	    	   layout.setText(font, "www.quailshillstudio.com");
	    	   font.draw(batch, "www.quailshillstudio.com", Gdx.graphics.getWidth()/2 - layout.width/2, Gdx.graphics.getHeight()/7);
	    	   }
	       else{ 
	    	   layout.setText(font, "www.quailshillstudio.com");
	    	   font.draw(batch, "www.quailshillstudio.com", Gdx.graphics.getWidth()/2 - layout.width/2, Gdx.graphics.getHeight()/7);
	    	   }
       
       batch.end();
       if (TimeUtils.millis()>(startTime+screenDuration) && possible){ app.setScreen(new GameScreen(app)); dispose();}
    }

	@Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        texture = new Texture(Gdx.files.internal("data/splash.png")); //** texture is now the splash image **//
        texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        splash = Gdx.audio.newSound(Gdx.files.internal("music/splash.mp3"));
        splash.play();
        startTime = TimeUtils.millis();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        texture.dispose();
        pixmap.dispose();
        batch.dispose();
        splash.dispose();
        font.dispose();
    }

}