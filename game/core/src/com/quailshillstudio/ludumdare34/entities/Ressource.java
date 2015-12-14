package com.quailshillstudio.ludumdare34.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.quailshillstudio.ludumdare34.screens.GameScreen;
import com.quailshillstudio.ludumdare34.utils.BodyUD;

public class Ressource {

	private ParticleEmitter particleEmitter;
	private Fixture fixture;
	private GameScreen gmScr;
	public Body body;
	public boolean spared;
	public String color = "green";

	public Ressource(GameScreen gmScr){
		this.gmScr = gmScr;
		double random = Math.random();
		if(random < 0.2){
			color = "green";
		}else if(random >= 0.2 && random < 0.4){
			color = "red";
		}else if(random >= 0.4 && random < 0.6){
			color = "blue";
		}else if(random >= 0.6 && random < 0.8){
			color = "violet";
		}else {
			color = "yellow";
		}
		
		float angle = (float) (Math.random()*Math.PI*2);
		float x1 = 240;
		float y1 = 240;
		float x = (float) (Math.cos(angle)*gmScr.popCircle.getFixtureList().get(0).getShape().getRadius()) + x1;
		float y = (float) (Math.sin(angle)*gmScr.popCircle.getFixtureList().get(0).getShape().getRadius()) + y1;
		
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
		
	   particleEmitter = new ParticleEmitter(gmScr,color+"-burst");
	}
	
	public void update(float delta) {
		 
	}

	public void render() {
        particleEmitter.render(fixture.getBody().getWorldPoint((((CircleShape)fixture.getShape()).getPosition())));
	}

	public float destroy() {
		gmScr.toDestroy.add(body);
		if(color.contains("green")){
			return 2f;
		}else if(color.contains("red")){
			return 3f;
		}else if(color.contains("blue")){
			return 3.75f;
		}else if(color.contains("violet")){
			return 4.5f;
		}else {
			return 4.75f;
		}
	}

	public Vector2 getPosition() {
		return this.body.getPosition();
	}

	public float getRadius() {
		return 1f;
	}
	
}
