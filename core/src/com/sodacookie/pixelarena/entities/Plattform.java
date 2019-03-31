package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sodacookie.pixelarena.StoneGame;

public class Plattform implements Entity {

	StoneGame game;
	World world;
	public Body body;

	public NineSlice shape;
	
	List<String> tags;

	public Plattform(StoneGame game, float x, float y, float width, float height, float rotation, World world, boolean hookable) {

		this.game = game;
		this.world = world;
		
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();

		// Set its world position
		groundBodyDef.position
				.set(new Vector2(x  / game.PHYSICS_ZOOM, y / game.PHYSICS_ZOOM));

		// Create a body from the defintion and add it to the world
		body = world.createBody(groundBodyDef);

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();

		// Set the polygon shape as a box which is twice the size of our view port and
		// 20 high
		// (setAsBox takes half-width and half-height as arguments)
		float halfWidth = (width / 2f) / game.PHYSICS_ZOOM;
		float halfHeight = (height / 2f) / game.PHYSICS_ZOOM;
		
		groundBox.setAsBox(halfWidth, halfHeight, new Vector2(halfWidth, halfHeight), rotation * (float)Math.PI/180f);

		// Create a fixture from our polygon shape and add it to our ground body
		body.createFixture(groundBox, 0.0f);

		// Clean up after ourselves
		groundBox.dispose();
		
		
		if(hookable) {
			TextureRegion earthNineSlice = new TextureRegion(game.assets.getTexture("earthSlice.png"));
			shape = new NineSlice(game,earthNineSlice, 25,50,25,50, x, y , width, height);
		}
		else {
			TextureRegion stoneNineSlice = new TextureRegion(game.assets.getTexture("rockSlice.png"));
			shape = new NineSlice(game,stoneNineSlice, 25,50,25,50, x, y , width, height);
		}
		
		shape.rotation = rotation;
		
		body.setUserData(this);
		
		
		
		tags =  new ArrayList<String>();
		
		if(hookable) {
			tags.add("hookable");
		}
		

	}

	@Override
	public void update(float delta) {

		shape.x = body.getPosition().x * game.PHYSICS_ZOOM;
		shape.y = body.getPosition().y * game.PHYSICS_ZOOM;

	}

	@Override
	public void render(SpriteBatch batch) {

		shape.render(batch);
	}

	@Override
	public void collide(Entity collider) {

	}

	@Override
	public Rectangle getHitbox() {

		return new Rectangle(shape.x, shape.y, shape.width, shape.height);
	}

	@Override
	public void dispose() {

	}

	@Override
	public List<String> getTags() {
		
		return tags;
	}

}
