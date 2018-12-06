package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.sodacookie.pixelarena.StoneGame;

public class Background implements Entity {

	
	
	List<Sprite> layers;
	
	StoneGame game;
	
	public float offset;
	
	public Background(StoneGame game, float offset) {
		
		layers = new ArrayList<Sprite>();
		this.game = game;
		this.offset = offset;
		
		for(int i = 0; i < 4; i++) {
			
			TextureRegion region = new TextureRegion(game.assets.getTexture("backdrop" + (i+1) + ".png"));
			
			float scale = game.HEIGHT / region.getRegionHeight();
			
			Sprite sprite = new Sprite(this.game,region,0,0,region.getRegionWidth()*scale, region.getRegionHeight()*scale);
			sprite.y = game.HEIGHT / 2f;
			sprite.x = this.offset;
			layers.add(sprite);
		}
	}
	
	@Override
	public void update(float delta) {
		
		float move = game.camera.position.x - game.WIDTH/2f;
		
		for(int i = 0; i < layers.size(); i++) {
			
			layers.get(i).x = move*(layers.size()-i-1)*0.1f;
			
		}
		
	}

	@Override
	public void render(SpriteBatch batch) {
		
		for(int i = 0; i < layers.size(); i++) {
			
			layers.get(i).render(batch);
			
		}
		
	}

	@Override
	public void collide(Entity collider) {
		

	}

	@Override
	public Rectangle getHitbox() {
		
		return null;
	}

	@Override
	public void dispose() {
		

	}

	@Override
	public List<String> getTags() {
		
		return null;
	}

}
