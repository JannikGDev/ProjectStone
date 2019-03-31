package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sodacookie.pixelarena.StoneGame;
import com.sodacookie.pixelarena.other.PathElement;

public class Mover implements Entity {

	StoneGame game;
	World world;
	public Body body;

	public NineSlice shape;
	
	List<String> tags;
	List<PathElement> path;
	int pathIndex;
	
	Vector2 restMovement;
	float restRotation;
	
	float moveSpeed;
	float rotSpeed;

	public Mover(StoneGame game, float x, float y, float width, float height, float rotation, World world, boolean hookable, List<PathElement> path) {

		this.game = game;
		this.world = world;
		this.path = path;
		pathIndex = 0;
		moveSpeed = 0;
		rotSpeed = 0;
		
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
		
		groundBodyDef.type = BodyType.KinematicBody;
		
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
		
		groundBox.setAsBox(halfWidth, halfHeight, new Vector2(0, 0), 0);

		// Create a fixture from our polygon shape and add it to our ground body
		body.createFixture(groundBox, 0.0f);

		// Clean up after ourselves
		groundBox.dispose();
		
		if(hookable) {
			TextureRegion metalSlice = new TextureRegion(game.assets.getTexture("metalSlice.png"));
			shape = new NineSlice(game,metalSlice, 25,50,25,50, x, y , width, height);
		}
		else {
			TextureRegion metalSlice = new TextureRegion(game.assets.getTexture("metalSlice.png"));
			shape = new NineSlice(game,metalSlice, 25,50,25,50, x, y , width, height);
		}
		
		body.setUserData(this);
		
		tags =  new ArrayList<String>();
		
		if(hookable) {
			tags.add("hookable");
		}
		
		PathElement active = path.get(pathIndex);
		restMovement = active.position.cpy();
		restRotation = active.rotation;
		moveSpeed = restMovement.len()/active.time;
		rotSpeed = Math.abs(restRotation/active.time);
		
		body.setTransform(body.getPosition(), rotation * (float)Math.PI/180f);
		
		shape.x = body.getPosition().x * game.PHYSICS_ZOOM - shape.width/2f;
		shape.y = body.getPosition().y * game.PHYSICS_ZOOM - shape.height/2f;
		shape.rotation = (float) (body.getAngle()*180/Math.PI);
	}

	@Override
	public void update(float delta) {

		if(restMovement.len() <= 0.1f && Math.abs(restRotation) <= 0.1f) {
			
			pathIndex += 1;
			if(pathIndex >= path.size()) {
				pathIndex = 0;
			}
			
			PathElement active = path.get(pathIndex);
			restMovement = active.position.cpy();
			restRotation = active.rotation;
			moveSpeed = restMovement.len()/active.time;
			rotSpeed = Math.abs(restRotation/active.time);
		}
		else {
			

			Vector2 move = restMovement.cpy().nor().scl(moveSpeed / game.PHYSICS_ZOOM);
			this.body.setLinearVelocity(move);
			if(restMovement.len() < moveSpeed*delta) {
				restMovement.scl(0);
			}
			else {
				restMovement  = restMovement.sub(move.scl(game.PHYSICS_ZOOM*delta));
			}
			
			if(Math.abs(restRotation) > 0.01f) {
				this.body.setAngularVelocity(((float)Math.PI/180)*rotSpeed*(restRotation/Math.abs(restRotation)));
				if(restRotation > delta*rotSpeed) {
					restRotation -= delta*rotSpeed;
				}
				else {
					restRotation = 0;
				}
			}
			
			
		}
		
		shape.x = body.getPosition().x * game.PHYSICS_ZOOM - shape.width/2f;
		shape.y = body.getPosition().y * game.PHYSICS_ZOOM - shape.height/2f;
		shape.rotation = (float) (body.getAngle()*180/Math.PI);
		
		
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
