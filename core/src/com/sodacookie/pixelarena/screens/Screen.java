package com.sodacookie.pixelarena.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sodacookie.pixelarena.StoneGame;

public interface Screen {
	
	
	public void init(StoneGame game);
	
	
	public void update(float delta);
	
	
	public void render(SpriteBatch batch);
	
	public void dispose();

}
