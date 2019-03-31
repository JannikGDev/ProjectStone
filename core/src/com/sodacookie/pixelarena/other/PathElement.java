package com.sodacookie.pixelarena.other;

import com.badlogic.gdx.math.Vector2;


public class PathElement {

	public enum PathType {
		LINEAR
	}
	
	public Vector2 position;
	public float rotation;
	public PathType type;
	public float time;
	
	public PathElement(float time, Vector2 relPosition, float relRot, PathType type) {
		this.type = type;
		rotation = relRot;
		position = relPosition;
		this.time = time;
	}
	
	public PathElement(float time, Vector2 relPosition, float relRot) {
		type = PathType.LINEAR;
		rotation = relRot;
		position = relPosition;
		this.time = time;
	}
	
	public PathElement(float time, Vector2 relPosition) {
		type = PathType.LINEAR;
		rotation = 0;
		position = relPosition;
		this.time = time;
	}
	
	
	
}