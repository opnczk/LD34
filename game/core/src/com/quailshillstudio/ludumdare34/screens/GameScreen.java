package com.quailshillstudio.ludumdare34.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.quailshillstudio.ludumdare34.LD34;
import com.quailshillstudio.ludumdare34.entities.Destroyer;
import com.quailshillstudio.ludumdare34.entities.Ressource;
import com.quailshillstudio.ludumdare34.utils.BodyUD;
import com.quailshillstudio.ludumdare34.utils.CollisionGeometry;
import com.quailshillstudio.ludumdare34.utils.TouchFeedBack;


public class GameScreen implements Screen {

    private PerspectiveCamera camera;
	public SpriteBatch batch;
	public World world;
	private Box2DDebugRenderer debugRenderer;
	private ShapeRenderer shapeRenderer;
	private float count;
	private boolean clockwise = true;
	private TextureRegion galaxText;
	private int width, basicWidth, height, basicHeight;

	private Texture bckText;
	private OrthographicCamera orthoCam;
	public float size = 85f;
	private Body ball, center;
	private Sprite galaxSpr;
	private Array<Ressource> ressources;
	private Array<Destroyer> destroyers;
	public Array<Body> toDestroy;
	public Array<TouchFeedBack> touches;
	public Array<ParticleEffect> particles;
	
	public Body popCircle;
	private float timer, currentTime, initialTime, lastSize;
	private Stage stage;
	private ImageButton defenseButton, fusionButton;
	private boolean fusionning;
	private Vector2 nullVector;
	private Music music;
	Sound fusionSfx, explosionSfx, warpoutSfx;
	private boolean paused;
	private boolean startScreenDisplay;
	private boolean endScreenDisplay;
	private Texture screenTexture;
	private BitmapFont font;
	private Texture gameLogo;
	private int remainingTime;
	private int score;
	
	public GameScreen(LD34 topDown) { 
    	width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	orthoCam = new OrthographicCamera(width, height);
    	camera = new PerspectiveCamera(45, width, height);
    	basicWidth = 640;
    	basicHeight = 480;
    	camera.position.set(width / 2 - ((width*200) /basicWidth), height / 2 - ((height*320) /basicHeight), ((width*200) /basicWidth));
    	camera.near = 0.1f;
    	camera.far = 1000;
    	camera.rotate(45, 1, 0, 0);
    	stage = new Stage();
    	
    	touches = new Array<TouchFeedBack>();
    	particles = new Array<ParticleEffect>();
    	toDestroy = new Array<Body>();
    	nullVector = new Vector2(0,0);

    	Texture defenseButtonImg = new Texture(Gdx.files.internal("data/particles/defense.png"));
 	   	defenseButtonImg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
 	   	defenseButton = new ImageButton(new Image(defenseButtonImg).getDrawable());
 	   	defenseButton.setPosition(Gdx.graphics.getWidth() - (Gdx.graphics.getWidth()/10), 0); //** Button location **//
        defenseButton.setHeight(Gdx.graphics.getWidth() /10); //** Button Height **//
        defenseButton.setWidth(Gdx.graphics.getWidth() /10); //** Button Width **//
        stage.addActor(defenseButton);

    	Texture fusionButtonImg = new Texture(Gdx.files.internal("data/particles/fusion.png"));
 	   	fusionButtonImg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
 	   	fusionButton = new ImageButton(new Image(fusionButtonImg).getDrawable());
 	   	fusionButton.setPosition(0, 0); //** Button location **//
        fusionButton.setHeight(Gdx.graphics.getWidth() /10); //** Button Height **//
        fusionButton.setWidth(Gdx.graphics.getWidth() /10); //** Button Width **//
        stage.addActor(fusionButton);
        
        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), true);
        bckText = new Texture(Gdx.files.internal("bck.png"));
        bckText.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        galaxText = new TextureRegion(new Texture(Gdx.files.internal("galaxy.png")));
        galaxText.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        galaxSpr = new Sprite(galaxText);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

        music = Gdx.audio.newMusic(Gdx.files.internal("music/spacearray.ogg"));
        music.setLooping(true);
        
        this.fusionSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/boxopen.ogg"));
        this.explosionSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/warpout.ogg"));
        this.warpoutSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/explode.ogg"));
        
        /*Galaxy bounds*/
        BodyDef defBall = new BodyDef();
        defBall.type = BodyDef.BodyType.DynamicBody;
        defBall.position.set(240, 240); // center of the universe man
        ball = world.createBody(defBall);
        ball.setUserData(new BodyUD());
        
        FixtureDef fixDefBall = new FixtureDef();
        fixDefBall.isSensor = true;
        fixDefBall.density = .25f;
        fixDefBall.restitution = 0.4f;
        CircleShape rond = new CircleShape();
        rond.setRadius(size);
         
        fixDefBall.shape = rond;
        ball.createFixture(fixDefBall);
        rond.dispose();
        
        /*Galaxy Center*/
        BodyDef defCenter = new BodyDef();
        defCenter.type = BodyDef.BodyType.DynamicBody;
        defCenter.position.set(240, 240); // center of the universe man
        center = world.createBody(defCenter);
        center.setUserData(new BodyUD());
        
        FixtureDef fixDefCenter = new FixtureDef();
        fixDefCenter.isSensor = true;
        fixDefCenter.density = .25f;
        fixDefCenter.restitution = 0.4f;
        CircleShape rondcenter = new CircleShape();
        rondcenter.setRadius(size/4);
         
        fixDefCenter.shape = rondcenter;
        center.createFixture(fixDefCenter);
        rondcenter.dispose();
        
        /*Ressources and Destroyers pop-up circle*/
        BodyDef defPop = new BodyDef();
        defPop.type = BodyDef.BodyType.DynamicBody;
        defPop.position.set(240, 240); // center of the universe man
        popCircle = world.createBody(defPop);
        popCircle.setUserData(new BodyUD());
        
        FixtureDef fixDefPop = new FixtureDef();
        fixDefPop.isSensor = true;
        fixDefPop.density = .25f;
        fixDefPop.restitution = 0.4f;
        CircleShape rondPop = new CircleShape();
        rondPop.setRadius(840f);//840f
        fixDefPop.shape = rondPop;
        popCircle.createFixture(fixDefPop);
        ressources = new Array<Ressource>();
        destroyers = new Array<Destroyer>();
        
    	screenTexture = new Texture(Gdx.files.internal("deadScreen.png"));
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/kenvector_future.ttf"));
    	FreeTypeFontParameter params = new FreeTypeFontParameter();
	     params.borderColor = Color.BLACK;
	     params.color = Color.WHITE;
	     params.borderStraight = false;
	     params.borderWidth = 2.5f;
	     params.size = (Gdx.graphics.getWidth()*32) /640;
	     BitmapFont buttonFont = generator.generateFont(params); // font size 12 pixels
	     params.size = (Gdx.graphics.getWidth()*16) /640;
	     font = generator.generateFont(params);
	     generator.dispose(); // don't forget to dispose to avoid memory leaks!
	     
	     gameLogo = new Texture(Gdx.files.internal("gameLogo.png"));
	     gameLogo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        paused = true;
        startScreenDisplay = true;
        endScreenDisplay = false;
    }
   
	@Override
    public void dispose() {
		world.dispose();
		stage.dispose();
		bckText.dispose();
    }
 
    @Override
    public void render(float delta) {
    	if(paused && startScreenDisplay && Gdx.input.justTouched()){
    		paused = false;
    		startScreenDisplay = false;
    		initialTime = 0;
    		remainingTime = 6000;
    	}
    	if(paused && endScreenDisplay && Gdx.input.justTouched()){
    		paused = false;
    		endScreenDisplay = false;
    		initialTime = 0;
    		remainingTime = 6000;
    		size = 85f;
    		score = 0;
    		this.destroyers.clear();
    		this.ressources.clear();
    	}
    	if(remainingTime <= 0 && !paused && !endScreenDisplay && !startScreenDisplay){
    		float bonus = size - 85;
    		score += bonus *10;
    		this.paused = true;
    		this.endScreenDisplay = true;
    	}
    	if(!paused)
    		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
    	
    	currentTime += delta;
        remainingTime -= delta;
    	float position = currentTime - initialTime;
        float gamePlayPosition = position / 130;
        
    	Gdx.gl.glClearColor(1, 0, 0, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	camera.update();
        
    	if(size != lastSize){
    		ball.getFixtureList().get(0).getShape().setRadius(size);
    		center.getFixtureList().get(0).getShape().setRadius(size/4);
    	}
    	lastSize = size;
    	if(defenseButton.isPressed() && touches.size == 0){
    		for(Ressource res : ressources)
    			res.spared = false;
    		for(Destroyer des : destroyers)
    			des.spared = false;
    		
    		touches.add(new TouchFeedBack(240, 240, this));
    	}
    	if(!paused && fusionButton.isPressed() && fusionning == false){
    		this.fusionSfx.play();
    		ParticleEffect pae = new ParticleEffect();
            pae.load(Gdx.files.internal("fusion"),Gdx.files.internal(""));
            pae.scaleEffect(.5f);
            pae.start();
    		particles.add(pae);
    		float deltaSize = ball.getFixtureList().get(0).getShape().getRadius() - size;
    		ball.getFixtureList().get(0).getShape().setRadius(size);
    		center.getFixtureList().get(0).getShape().setRadius(size/4);
    		for(Ressource res1 : ressources){
            	if(CollisionGeometry.CircleCircle(res1.getPosition(), res1.getRadius(), ball.getPosition(), size)){
            		float radius = CollisionGeometry.distanceBetween2Points(res1.getPosition().x, res1.getPosition().y, ball.getPosition().x, ball.getPosition().y);
            		radius += deltaSize;
            		float angle = (float) Math.atan2(res1.getPosition().y - 240, res1.getPosition().x - 240);
            		float x = (float) (240 + (radius * Math.cos(angle)));
            		float y = (float) (240 + (radius * Math.sin(angle)));
            		res1.body.setLinearVelocity(nullVector);
            		res1.body.setTransform(x, y, res1.body.getAngle());
            	}
            }
    	}

    	for(TouchFeedBack touch : touches){
    		if(!touch.drawRing){
    			touches.removeValue(touch, false);
    		}
    	}
    	
    	batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        
		if(timer > 0){
        	timer -= delta;
        }else if(!paused && timer <= 0){
        	timer = (float) (Math.floor(Math.random() * 1.75f) + 0.5f);
        	double pileFace = Math.random();
        	if(pileFace <= 0.5){
        		ressources.add(new Ressource(this));
        	}else{
        		destroyers.add(new Destroyer(this));
        	}
        }
        
        batch.begin();
        batch.setProjectionMatrix(orthoCam.combined);
        batch.draw(bckText, -width/2, -height/2, width, height);
        
        batch.setProjectionMatrix(camera.combined);
            galaxSpr.setOrigin(galaxSpr.getWidth() / 2,galaxSpr.getHeight() / 2);
            galaxSpr.setPosition(center.getPosition().x - (galaxSpr.getWidth() / 2),center.getPosition().y - (galaxSpr.getHeight() / 2));
            galaxSpr.setRotation((float) Math.toDegrees(center.getAngle()));
            galaxSpr.setSize(size*2 + size/4, size*2 + size/4);
            galaxSpr.draw(batch);
        
            for(int i =0; i < touches.size; i++){
    			TouchFeedBack touch = touches.get(i); 
    			if(touch.drawRing == false)touches.removeIndex(i);
    			if(touch.hitPosition == 0)touch.hitPosition= gamePlayPosition;
    			touch.drawExpandingRing(batch, gamePlayPosition);
    		}
            int nbFus = 0;
            for(Ressource res : ressources){
            	if(nbFus == 0) nbFus ++;
            	res.update(delta);
            	res.render();
            	if(CollisionGeometry.CircleCircle(res.getPosition(), res.getRadius(), ball.getPosition(), size)){
            		float radius = CollisionGeometry.distanceBetween2Points(res.getPosition().x, res.getPosition().y, ball.getPosition().x, ball.getPosition().y);
            		radius -= size/1000;
            		float angle = (float) Math.atan2(res.getPosition().y - 240, res.getPosition().x - 240);
            		angle -= Math.toRadians(.5f);
            		float x = (float) (240 + (radius * Math.cos(angle)));
            		float y = (float) (240 + (radius * Math.sin(angle)));
            		res.body.setLinearVelocity(nullVector );
            		res.body.setTransform(x, y, res.body.getAngle());
            	}
            	
            	if(!paused && !fusionning && fusionButton.isPressed() && CollisionGeometry.CircleCircle(res.getPosition(), res.getRadius(), center.getPosition(), size/4)){
            		nbFus++;
            		size += res.destroy();
            		score += 10;
            		ParticleEffect pae = new ParticleEffect();
                    pae.load(Gdx.files.internal(res.color+"-fusion"),Gdx.files.internal(""));
                    pae.scaleEffect(1.25f);
                    pae.start();
            		particles.add(pae);
            		ressources.removeValue(res, false);
            	}
            	if(touches.size > 0 && CollisionGeometry.CircleCircle(res.getPosition(), res.getRadius(), ball.getPosition(), touches.get(0).getDestroyingRadius()) && ! CollisionGeometry.CircleCircle(res.getPosition(), res.getRadius(), ball.getPosition(), size)){
            		if(Math.random() <= 0.5 && !res.spared){
            			this.warpoutSfx.play();
            			res.destroy();
            			ressources.removeValue(res, false);
            		}else{
            			res.spared = true;
            		}
            	}
            }
            if(!paused && nbFus == 1  && fusionButton.isPressed() && !fusionning){
            	size -= .2f;
            }
            
            for(Destroyer des : destroyers){
            	des.update(delta);
            	des.render();
            	if(touches.size > 0 && CollisionGeometry.CircleCircle(des.getPosition(), des.getRadius(), ball.getPosition(), touches.get(0).getDestroyingRadius()) && !CollisionGeometry.CircleCircle(des.getPosition(), des.getRadius(), ball.getPosition(), size)){
            		if(Math.random() <= 0.75 && !des.spared){
            			this.warpoutSfx.play();
            			des.destroy();
            			score += 3;
            			destroyers.removeValue(des, false);
            		}else{
            			des.spared = true;
            		}
            	}
            	if(CollisionGeometry.CircleCircle(des.getPosition(), des.getRadius(), ball.getPosition(), size/4)){
            		this.explosionSfx.play();
            		size -= des.destroy();
            		score -= 5;
            		ParticleEffect pae = new ParticleEffect();
                    pae.load(Gdx.files.internal("explode"),Gdx.files.internal(""));
                    pae.scaleEffect(.5f);
                    pae.start();
            		particles.add(pae);
            		float deltaSize = ball.getFixtureList().get(0).getShape().getRadius() - size;
            		for(Ressource res1 : ressources){
                    	if(CollisionGeometry.CircleCircle(res1.getPosition(), res1.getRadius(), ball.getPosition(), ball.getFixtureList().get(0).getShape().getRadius())){
                    		float radius = CollisionGeometry.distanceBetween2Points(res1.getPosition().x, res1.getPosition().y, ball.getPosition().x, ball.getPosition().y);
                    		radius -= deltaSize;
                    		float angle = (float) Math.atan2(res1.getPosition().y - 240, res1.getPosition().x - 240);
                    		float x = (float) (240 + (radius * Math.cos(angle)));
                    		float y = (float) (240 + (radius * Math.sin(angle)));
                    		res1.body.setLinearVelocity(nullVector);
                    		res1.body.setTransform(x, y, res1.body.getAngle());
                    	}
                    }
            		destroyers.removeValue(des, false);
            	}
            }
            
            for(ParticleEffect pae : particles){
            	for(ParticleEmitter em : pae.getEmitters())
            		em.setPosition(240,240);
        	    pae.update(Gdx.graphics.getDeltaTime());
        	      pae.draw(batch);
        	      if (pae.isComplete()){
        	    	  particles.removeValue(pae, false);
        	    	  pae.dispose();
        	      }
            }
        batch.end();
        
        /*Here Render Uis*/
        if(startScreenDisplay){
        	
			stage.getBatch().begin();
			stage.getBatch().draw(screenTexture, 0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getWidth());
			stage.getBatch().draw(gameLogo, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight() - (float)( ((Gdx.graphics.getWidth()/2)/3.4) *1.5f), Gdx.graphics.getWidth()/2, (float) ((Gdx.graphics.getWidth()/2)/3.4));
			font.draw(stage.getBatch(), "Touch anywhere to start", Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/10*4.5f)/2, Gdx.graphics.getHeight()/2 - (Gdx.graphics.getHeight()/10*6f)/2);
			//button1.draw(stage.getBatch(),1);
			stage.getBatch().end();
			if(Gdx.input.justTouched()){
				/*
					Vector3 tmp = new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
					  app.camera.unproject(tmp);
					  Rectangle textureBounds=new Rectangle(Gdx.graphics.getWidth()/2-  button1.getWidth()/2, Gdx.graphics.getHeight()/2 -  button1.getHeight()*1.5f, button1.getWidth(), button1.getHeight());
					  // texture x is the x position of the texture
					  // texture y is the y position of the texture
					  // texturewidth is the width of the texture (you can get it with texture.getWidth() or textureRegion.getRegionWidth() if you have a texture region
					   // textureheight is the height of the texture (you can get it with texture.getHeight() or textureRegion.getRegionhHeight() if you have a texture region
					  if(textureBounds.contains(tmp.x,tmp.y))
					     {
						  restartGame();
					     }*/
			}
        }else if(this.endScreenDisplay){
        	stage.getBatch().begin();
			stage.getBatch().draw(screenTexture, 0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getWidth());
			font.draw(stage.getBatch(),"Score :"+score, Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/10*4.5f)/2, Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/10)/4);
			font.draw(stage.getBatch(), "Touch anywhere to restart", Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/10*4.5f)/2, Gdx.graphics.getHeight()/2 - (Gdx.graphics.getHeight()/10*6f)/2);
			//button1.draw(stage.getBatch(),1);
			stage.getBatch().end();
        }else{
        	stage.getBatch().begin();
        	font.draw(stage.getBatch(),"Score :"+score+ "     Time remaining :"+Math.round(remainingTime/100), Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/10*4.5f)/2, Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/10)/4);
        	stage.getBatch().end();
        }

        float speed = .5f;
        if(clockwise){
        	 ball.setTransform(240, 240,(float) (ball.getAngle() - Math.toRadians(speed)));
        	 center.setTransform(240, 240,(float) (center.getAngle() - Math.toRadians(speed)));
            count = count - speed;
        }else{
        	ball.setTransform(240, 240,(float) (ball.getAngle() + Math.toRadians(speed)));
        	 center.setTransform(240, 240,(float) (center.getAngle() + Math.toRadians(speed)));
            count = count + speed;
        }
        
        stage.act(Gdx.graphics.getDeltaTime());        
    	stage.draw();
    	//debugRenderer.render(world, camera.combined);
    	
    	
    	
    	Iterator<Body> i = toDestroy.iterator();
    	if(!world.isLocked()){
    	   while(i.hasNext()){
    	      Body b = i.next();
    	      if(b.getUserData() != null && ((BodyUD)b.getUserData()).on == true){
    	    	  ((BodyUD)b.getUserData()).on = false;
    	    	  world.destroyBody(b);
    	      }
    	      i.remove();
    	   }
    	   toDestroy.clear();
    	}
    }

	
    @Override
    public void pause() {
    
    }
 
    @Override
    public void resume() {
    
    }
 
    @Override
    public void resize(int width, int height) {        
    
    }

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		music.play();
	}

	@Override
	public void hide() {
		
	}

}
