package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.sodacookie.pixelarena.StoneGame;

public class Sprite implements Entity {
	
	protected StoneGame game;
	
	public float x;
	public float y;
	
	public float width;
	public float height;
	
	public float originX;
	public float originY;
	
	public float rotation;
	
	public float scaleX;
	public float scaleY;
	
	public Color tint;
	
	public TextureRegion texture;
	
	public Sprite(StoneGame game, TextureRegion texture, float x, float y, float width, float height) {
		this.game = game;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		
		this.originX = width / 2;
		this.originY = height / 2;
		
		this.rotation = 0;
		
		this.scaleX = 1;
		this.scaleY = 1;
		
		this.tint = new Color(1,1,1,1);
	}
	
	@Override
	public void update(float delta) {
		
		

	}

	@Override
	public void render(SpriteBatch batch) {
		batch.begin();
		batch.draw(texture, x - originX, y - originY, originX, originY, width, height, scaleX, scaleY, rotation);
		batch.end();
	}

	@Override
	public void dispose() {
		

	}

	@Override
	public void collide(Entity collider) {
		
	}

	@Override
	public Rectangle getHitbox() {
		
		return new Rectangle(x - originX, y - originY, width, height);
	}
	
	@Override
	public List<String> getTags() {
		
		return new ArrayList<String>();
	}
}
