package com.quailshillstudio.ludumdare34.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.quailshillstudio.ludumdare34.LD34;


public class GameScreen implements Screen {

    private PerspectiveCamera camera;
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private ShapeRenderer shapeRenderer;
	private float count;
	private boolean clockwise = true;
	private TextureRegion galaxText;
	private int width, basicWidth;
	private int height, basicHeight;
	//(Gdx.graphics.getHeight()*50) /480
	private Texture bckText;
	private OrthographicCamera orthoCam;
	private float size = 20;

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
    	//stage.setCamera(camera);
    	
        
        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), true);
        bckText = new Texture(Gdx.files.internal("bck.png"));
        galaxText = new TextureRegion(new Texture(Gdx.files.internal("galaxy.png")));
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();
    }
 

   
	@Override
    public void dispose() {
		
    }
 
    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(1, 0, 0, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	camera.update();
        
    	if(Gdx.input.isTouched()){
    		size += .5f;
    	}
    	batch.setProjectionMatrix(camera.combined);
        debugRenderer.render(world, camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        
        
        float cutieWidth = ((width*size) /basicWidth);
        float cutieHeight = ((height*size) /basicHeight);
        float cutieX = ((width*240) /basicWidth) - ((width*size) /basicWidth);
        float cutieY = ((height*240) /basicHeight) - ((height*size) /basicHeight);
        
        batch.begin();
        batch.setProjectionMatrix(orthoCam.combined);
        batch.draw(bckText, -width/2, -height/2, width*2, height*2);
        batch.setProjectionMatrix(camera.combined);
        batch.draw(galaxText, cutieX/2, cutieY/2, cutieWidth/2.0f,cutieHeight/2.0f, cutieWidth, cutieHeight, 1f, 1f,count, false);
        batch.end();
        
        /*if(count < -135){
            clockwise = false;
            count = -135;
        }else if(count > -45){
            clockwise = true;
            count = -45;
        }*/
        
        if(clockwise){
            //count --;
            count = count -1f;
        }else{
            //count ++;
            count = count + 1f;
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
		
	}

	@Override
	public void hide() {
		
	}

}
