package com.quailshillstudio.ludumdare34.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.quailshillstudio.ludumdare34.LD34;

public class MainMenuScreen implements Screen{
	private LD34 game;
	private Stage stage;
	private Table table, container;
	private TextureRegion background;
	private SpriteBatch batch;
	
	private Texture musicOn, musicOff;
	private Music music;
	public MainMenuScreen(final LD34 game){
		this.game = game;
		batch = new SpriteBatch();
		batch.setProjectionMatrix(game.camera.combined);

	     stage = new Stage();
	    stage.setViewport(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), game.camera));
	    container = new Table();
	     container.setFillParent(true);
	     stage.addActor(container);
	     Texture logo = new Texture("gameLogo.png");
	     logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	     int logheight = (int) (Gdx.graphics.getHeight()/2.5);
	     int logwidth = (logo.getWidth()*logheight)/logo.getHeight();
	    
	     container.add(new Image(logo)).width(logwidth).height(logheight).left();
	     container.row().spaceTop((Gdx.graphics.getHeight()*50) /480);
	     table = new Table();
	     
	     Texture bg = new Texture("data/ui/background.png");
	     musicOn = new Texture("data/ui/musicOn.png");
	     musicOff = new Texture("data/ui/musicOff.png");
	     musicOn.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	     musicOff.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	     background = new TextureRegion( bg, 0, 0, 1280, 800);
	     
	      music = Gdx.audio.newMusic(Gdx.files.internal("music/menu.ogg"));
	      
	     FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/BrokenGlass.ttf"));
	     FreeTypeFontParameter params = new FreeTypeFontParameter();
	     params.borderColor = Color.BLACK;
	     params.color = Color.WHITE;
	     params.borderStraight = false;
	     params.borderWidth = 2.5f;
	     params.size = (Gdx.graphics.getWidth()*22) /640;
	     BitmapFont buttonFont = generator.generateFont(params); // font size 12 pixels
	     generator.dispose(); // don't forget to dispose to avoid memory leaks!
	    
	     TextButtonStyle style = new TextButtonStyle();
	     style.up = new SpriteDrawable(new Sprite(new Texture("data/ui/buttonXL.png")));
		 style.font = buttonFont;
		 
		 
		 //table.debugTable();
		 TextButton button1 = new TextButton("      Start Game     ", style);
		 button1.addListener(new InputListener() {
			
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(game.gameScreen = new GameScreen(game));
                    dispose();
                    return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });
		 table.add(button1).space((Gdx.graphics.getWidth()*10)/640).height((Gdx.graphics.getHeight()*50) /480).fill();
		 
		 table.row();
		 
		 TextButton button2 = new TextButton("  Tutorial  ", style);
		 button2.addListener(new InputListener() {
				
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	                    return true;
	            }
	            
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	                   // game.setScreen(new TutorialScreen(game));
	            }
	        });
		 table.add(button2).space((Gdx.graphics.getWidth()*10) /640).height((Gdx.graphics.getHeight()*50) /480).fill();
		 
		 table.row();
		 
		 TextButton button3 = new TextButton("credits", style);
		 button3.addListener(new InputListener() {
				
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	                    return true;
	            }
	            
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	//game.setScreen(new CreditsScreen(game));
	            }
	        });
		 table.add(button3).space((Gdx.graphics.getWidth()*10) /640).height((Gdx.graphics.getHeight()*50) /480).fill();
		 
		 container.add(table).center();
		 stage.addActor(getMusicButton());
	}
	
	@Override
	public void show() {
		 Gdx.input.setInputProcessor(stage);
		 music.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	stage.act(Gdx.graphics.getDeltaTime());
    	
    	 batch.begin();
    	 batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
         batch.end();
    	if(!game.musicOn)music.pause();
    	if(game.musicOn)music.play();
    	stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		music.stop();
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
		music.dispose();
		musicOn.dispose();
		musicOff.dispose();
	}
	
	public Button getMusicButton() {
		Button button = new Button();
		ButtonStyle style = new ButtonStyle();
		style.up = 	new SpriteDrawable(new Sprite(musicOn));		
		
		button.setStyle(style);
		button.setName("Music");
		button.addListener(musicClickListener);
		button.bottom().setWidth(Gdx.graphics.getWidth()/10);
		button.setX(Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/10);
		button.setHeight(Gdx.graphics.getWidth()/10);
		
		return button;
	}
	
	public ClickListener musicClickListener = new ClickListener() {
		@Override
		public void clicked (InputEvent event, float x, float y) {
			if(game.musicOn){
				Array<Actor> stageActors = stage.getActors();
				for(int i = 0; i < stageActors.size; i++){
					if(stageActors.get(i).getName() != null && stageActors.get(i).getName().contentEquals("Music")){
					Button btn = (Button) stageActors.get(i);
					btn.getStyle().up = new SpriteDrawable(new Sprite(musicOff));
					}
				}
				game.musicOn = false;
			}else{
				Array<Actor> stageActors = stage.getActors();
				for(int i = 0; i < stageActors.size; i++){
					if(stageActors.get(i).getName() != null && stageActors.get(i).getName().contentEquals("Music")){
					Button btn = (Button) stageActors.get(i);
					btn.getStyle().up = new SpriteDrawable(new Sprite(musicOn));
					}
				}
				game.musicOn = true;
			}
		}
	};

}
