package com.quailshillstudio.ludumdare34.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	public GameScreen(LD34 topDown) { 
    	float width = Gdx.graphics.getWidth();
    	float height = Gdx.graphics.getHeight();
    	camera = new PerspectiveCamera(45, width, height);
    	camera.position.set(width / 2 - 200, height / 2 - 300, 200);
    	camera.near = 0.1f;
    	camera.far = 1000;
    	camera.rotate(45, 1, 0, 0);
    	//stage.setCamera(camera);
    	
        
        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), true);
        
        
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();
    }
 

   
	@Override
    public void dispose() {
		
    }
 
    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	camera.update();
        
    	batch.setProjectionMatrix(camera.combined);
        debugRenderer.render(world, camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        
        batch.begin();
        	batch.draw(new Texture(Gdx.files.internal("galaxy.png")), 0, 0, 250, 250);
        	
        batch.end();
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
