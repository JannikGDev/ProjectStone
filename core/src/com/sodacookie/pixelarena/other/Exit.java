package com.sodacookie.pixelarena.other;

import com.badlogic.gdx.math.Rectangle;

public class Exit {
	
	public float width;
	public float height;
	public float x;
	public float y;
	public int id;
	public String nextLevel;
	public int entry;
	
	public Exit(float x, float y, float width, float height, int id, String nextLevel, int entry) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.nextLevel = nextLevel;
		this.entry = entry;
	}
	
	public Rectangle getRect() {
		
		return new Rectangle(x,y,width,height);
	}
	
}
