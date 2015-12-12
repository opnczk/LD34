package com.quailshillstudio.ludumdare34.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.quailshillstudio.ludumdare34.screens.GameScreen;

public class Ressource {

	private ParticleEmitter particleEmitter;
	private Fixture fixture;
	private GameScreen gmScr;
	private Body body;

	public Ressource(GameScreen gmScr){
		this.gmScr = gmScr;
		float angle = (float) (Math.random()*Math.PI*2);
		float x1 = 240;
		float y1 = 240;
		float x = (float) (Math.cos(angle)*gmScr.popCircle.getFixtureList().get(0).getShape().getRadius()) + x1;
		float y = (float) (Math.sin(angle)*gmScr.popCircle.getFixtureList().get(0).getShape().getRadius()) + y1;
		
		BodyDef defBall = new BodyDef();
	    defBall.type = BodyDef.BodyType.DynamicBody;
	    defBall.position.set(x,y); // center of the universe man
	    body = gmScr.world.createBody(defBall);
		
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
	   float speed = 25f;
	    	
	   float finalVelx = velx * speed;
	   float finalVely = vely * speed;

	   body.setLinearVelocity(finalVelx,finalVely);
		
	   particleEmitter = new ParticleEmitter(gmScr);
	}
	
	public void update(float delta) {
		 
	}

	public void render() {
        particleEmitter.render(fixture.getBody().getWorldPoint((((CircleShape)fixture.getShape()).getPosition())));
	}

}
