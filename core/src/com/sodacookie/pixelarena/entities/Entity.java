package com.sodacookie.pixelarena.entities;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface Entity {
	
	
	public void update(float delta);
	
	public void render(SpriteBatch batch);
	
	public void collide(Entity collider);
	
	public Rectangle getHitbox();
	
	public void dispose();
	
	public List<String> getTags();
	
	
	
	
	
	
}
