package com.quailshillstudio.ludumdare34.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.quailshillstudio.ludumdare34.screens.GameScreen;

public class ParticleEmitter{
	private ParticleEffect pe;
	private GameScreen gameScreen;
	
	public ParticleEmitter(GameScreen gmScr, String name){
		gameScreen = gmScr;
		pe = new ParticleEffect();
        pe.load(Gdx.files.internal(name),Gdx.files.internal(""));
        pe.scaleEffect(2f);
        pe.start();
	}
	
	public void render(Vector2 vec){
		pe.getEmitters().first().setPosition(vec.x, vec.y);
	    pe.update(Gdx.graphics.getDeltaTime());
	      pe.draw(gameScreen.batch);
	      if (pe.isComplete())
	         pe.reset();
	}
}
