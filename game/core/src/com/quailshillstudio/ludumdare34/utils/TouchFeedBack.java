package com.quailshillstudio.ludumdare34.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.quailshillstudio.ludumdare34.screens.GameScreen;

public class TouchFeedBack {
	public boolean drawRing = true;
	private Texture ring1Img;
	private Texture ringImg;
	private Sprite ring1;
	private Sprite ring;
	public float hitPosition;
	private float givenX;
	private float givenY;
	private float duration = 0.017f;
	private float count =360.0f;
	private Color color = new Color(1f,1f,1f,1f);
	private Sprite Spr;
	private Body ringBody;
	private GameScreen gmScr;
	private float size;
	
	public TouchFeedBack(float x, float y, GameScreen gmScr){
		this.gmScr = gmScr;
		this.givenX = x;
		this.givenY = Gdx.graphics.getHeight() - y;
		ring1Img = new Texture(Gdx.files.internal("data/particles/ring-basic.png"));
		ringImg = new Texture(Gdx.files.internal("data/particles/circle-basic.png"));
		ringImg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		ring1Img.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		ring1 = new Sprite(ringImg);
		ring = new Sprite(ring1Img);
		 /*Galaxy Center*/
        BodyDef defCenter = new BodyDef();
        defCenter.type = BodyDef.BodyType.DynamicBody;
        defCenter.position.set(x, y); // center of the universe man
        ringBody = gmScr.world.createBody(defCenter);
        
        FixtureDef fixDefCenter = new FixtureDef();
        fixDefCenter.isSensor = true;
        fixDefCenter.density = .25f;
        fixDefCenter.restitution = 0.4f;
        CircleShape rondcenter = new CircleShape();
        rondcenter.setRadius(gmScr.size/4);
         
        fixDefCenter.shape = rondcenter;
        ringBody.createFixture(fixDefCenter);
        rondcenter.dispose();
//        hitPosition = gmScr.currentTime;
        		}
	
	public void drawExpandingRing(SpriteBatch batch, float position){
     	float tickAlphaScale = (position - hitPosition);
		float alpha = 1-(1*tickAlphaScale/duration);
		size += 7.5f;
		if(ringBody.getFixtureList().size > 0)
		ringBody.getFixtureList().get(0).getShape().setRadius(size);
		
		Color tmpColor = batch.getColor();
     	batch.setColor(color.r, color.g, color.b, alpha);
     	ring1.setOrigin(ring1.getWidth()/ 2,ring1.getHeight()/ 2);
        ring1.setPosition(ringBody.getPosition().x - (size*2/2 + size/8),ringBody.getPosition().y - (size*2/2 + size/8));
        ring1.setRotation((float) Math.toDegrees(ringBody.getAngle()));
        ring1.setSize(size*2 + size/4, size*2 + size /4);
        ring1.draw(batch);
     	batch.setColor(tmpColor);
     	
     	if(count < 0.0f){
    		count = 360.0f;
     	}else{
    		count --;
    		count --;
    		count --;
    		count --;
     	}
     	System.out.println(alpha);
    	if(alpha < 0.05f){
     		drawRing = false;
     		gmScr.world.destroyBody(ringBody);
     	}
     }
	
	public float getDestroyingRadius(){
		return this.size;
	}
}
