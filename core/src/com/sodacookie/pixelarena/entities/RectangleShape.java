package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.sodacookie.pixelarena.StoneGame;

public class RectangleShape implements Entity {

	
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
	
	public Color color;
	
	public RectangleShape(StoneGame game, float x, float y, float w, float h) {
		this.game = game;
		
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		
		this.originX = w / 2;
		this.originY = h / 2;
		
		this.rotation = 0;
		
		this.scaleX = 1;
		this.scaleY = 1;
		
		this.color = new Color(1,0,0,1);
	}
	
	@Override
	public void update(float delta) {
		
		

	}

	@Override
	public void render(SpriteBatch batch) {
		
		game.shape.begin(ShapeType.Filled);
		game.shape.setColor(color);
		
		game.shape.rect(x - originX, y - originY, originX, originY, width, height, scaleX, scaleY, rotation);
		
		game.shape.end();
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
