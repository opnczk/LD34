package com.quailshillstudio.ludumdare34.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.quailshillstudio.ludumdare34.screens.GameScreen;
import com.quailshillstudio.ludumdare34.utils.BodyUD;

public class Destroyer {

	private ParticleEmitter particleEmitter;
	private Fixture fixture;
	private GameScreen gmScr;
	private Body body;
	public boolean spared;
	private String textName;
	private Sprite Spr;

	public Destroyer(GameScreen gmScr){
		this.gmScr = gmScr;
		float angle = (float) (Math.random()*Math.PI*2);
		float x1 = 240;
		float y1 = 240;
		float x = (float) (Math.cos(angle)*gmScr.popCircle.getFixtureList().get(0).getShape().getRadius()) + x1;
		float y = (float) (Math.sin(angle)*gmScr.popCircle.getFixtureList().get(0).getShape().getRadius()) + y1;
		double random = Math.random();
		
		if(random < 0.25){
			textName = "ast1.png";
		}else if(random >= 0.25 && random < 0.5){
			textName = "ast2.png";
		}else if(random >= 0.5 && random < 0.75){
			textName = "ast3.png";
		}else {
			textName = "ast4.png";
		}
		
		BodyDef defBall = new BodyDef();
	    defBall.type = BodyDef.BodyType.DynamicBody;
	    defBall.position.set(x,y); // center of the universe man
	    body = gmScr.world.createBody(defBall);
	    body.setUserData(new BodyUD());
		
	    CircleShape sensorShape = new CircleShape();
		sensorShape.setRadius(1f);
		FixtureDef sensorDef = new FixtureDef();
		sensorDef.shape = sensorShape;
		fixture = body.createFixture(sensorDef);
		
		TextureRegion galaxText = new TextureRegion(new Texture(Gdx.files.internal(textName)));
        galaxText.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        Spr = new Sprite(galaxText);
		
		float velx = x1 - x;
	    float vely = y1 - y;
	    float length = (float) Math.sqrt(velx * velx + vely * vely);
	    if (length != 0) {
	       velx = velx / length;
	       vely = vely / length;
	    }
	   float speed = gmScr.size*2.5f;
	    	
	   float finalVelx = velx * speed;
	   float finalVely = vely * speed;

	   body.setLinearVelocity(finalVelx,finalVely);
		
	   particleEmitter = new ParticleEmitter(gmScr,"burningb");
	}
	
	public void update(float delta) {
		 
	}

	public void render() {
		 Spr.setOrigin(Spr.getWidth() / 2,Spr.getHeight() / 2);
         Spr.setPosition(body.getPosition().x - (Spr.getWidth() / 2),body.getPosition().y - (Spr.getHeight() / 2));
         Spr.setRotation((float) Math.toDegrees(body.getAngle()));
         Spr.setScale(.15f);
         Spr.draw(gmScr.batch);
        particleEmitter.render(fixture.getBody().getWorldPoint((((CircleShape)fixture.getShape()).getPosition())));
	}

	public float destroy() {
		gmScr.toDestroy.add(body);
		if(textName.contains("ast1.png")){
			return 2.5f;
		}else if(textName.contains("ast2.png")){
			return 3f;
		}else if(textName.contains("ast3.png")){
			return 4f;
		}else {
			return 5f;
		}
	}

	public Vector2 getPosition() {
		return body.getPosition();
	}

	public float getRadius() {
		return 1f;
	}

}
