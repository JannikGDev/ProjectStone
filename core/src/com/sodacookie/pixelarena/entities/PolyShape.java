package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.sodacookie.pixelarena.StoneGame;

public class PolyShape implements Entity {

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
	
	public float[] vertices;
	
	public PolyShape(StoneGame game, float x, float y, float[] vertices) {
		this.game = game;
		
		this.x = x;
		this.y = y;
		
		this.vertices = vertices;
		
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
		
		game.shape.begin(ShapeType.Line);
		game.shape.setColor(color);
		
		float cos = (float) Math.cos(rotation*Math.PI/180);
		float sin = (float) Math.sin(rotation*Math.PI/180);
		
		float[] vertices_rotated = new float[vertices.length];
		
		for(int i = 0; i < vertices.length; i += 2) {
			
			vertices_rotated[i] = cos*vertices[i] - sin*vertices[i+1] + x;
			vertices_rotated[i+1] = sin*vertices[i] + cos*vertices[i+1] + y;
		}
		
		game.shape.polygon(vertices_rotated);
		
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
