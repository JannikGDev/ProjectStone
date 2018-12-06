package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.sodacookie.pixelarena.StoneGame;

public class Rope implements Entity {

	StoneGame game;

	Sprite sprite;
	int segments;
	
	Body player;
	Vector2 anchor;
	RopeJoint joint;
	
	float originalDistance;
	
	float currentDistance;
	
	World world;

	public Rope(StoneGame game, World world, Vector2 endPoint, Body playerBody, RopeJoint joint) {
		this.game = game;		
		this.player = playerBody;
		this.anchor = endPoint.cpy();
		this.joint = joint;
		this.world = world;
		
		//Create Rope Sprite
		TextureRegion region = new TextureRegion(game.assets.getTexture("ropepart.png"));
		this.sprite = new Sprite(game, region, 0, 0, region.getRegionWidth()*0.5f, region.getRegionHeight()*0.5f);
		
		Vector2 playerPos = player.getPosition().cpy().scl(game.PHYSICS_ZOOM);
		
		this.originalDistance = playerPos.dst(anchor);
		this.currentDistance = originalDistance;
		this.segments = (int) (originalDistance / (sprite.width*0.8f)) + 1;
		
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(SpriteBatch batch) {

		Vector2 playerPos = player.getPosition().cpy().scl(game.PHYSICS_ZOOM);
		
		Vector2 direction = anchor.cpy().sub(playerPos).setLength(1.0f);
		
		float distance = playerPos.dst(anchor);
		
		float segmentWidth = distance/segments;
		
		for (int i = 0; i < segments; i++) {
			
			Vector2 pos = direction.cpy().scl(segmentWidth*(i+0.5f)).add(playerPos);
			
			sprite.x = pos.x;
			sprite.y = pos.y;
			sprite.rotation = direction.angle();
			
			sprite.render(batch);
			
		}
		currentDistance += (distance - currentDistance)*0.1f;
		joint.setMaxLength(Math.max(currentDistance,originalDistance*0.8f)/game.PHYSICS_ZOOM);
		
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
		
		world.destroyJoint(joint);
		joint = null;
	}
	
	public boolean checkCutting(Rectangle hitbox) {
		
		if(hitbox.contains(anchor)) {
			return false;
		}
		
		Vector2 playerPos = player.getPosition().cpy().scl(game.PHYSICS_ZOOM);
		
		Vector2 direction = anchor.cpy().sub(playerPos).setLength(1.0f);
		
		
		if(Intersects(playerPos.cpy().add(direction), anchor.cpy().sub(direction), hitbox)) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<String> getTags() {

		return null;
	}
	
	public boolean Intersects(Vector2 a, Vector2 b, Rectangle r)
	{

		float lineXmax = Math.max(a.x, b.x);
		float lineYmax = Math.max(a.y, b.y);
		float lineXmin = Math.min(a.x, b.x);
		float lineYmin = Math.min(a.y, b.y);
		
		float rectLeft = r.x;
		float rectRight = r.x+r.width;
		float rectTop = r.y + r.height;
		float rectBot = r.y;
		
		
	    if (rectLeft >= lineXmax|| rectRight <= lineXmin)
	    {
	        return false;
	    }

	    if (rectTop <= lineYmin || rectBot >= lineYmax)
	    {
	        return false;
	    }

	    float yAtRectLeft = calcYforX(a,b,rectLeft);
	    float yAtRectRight = calcYforX(a,b,rectRight);

	    if (rectBot > yAtRectLeft && rectBot > yAtRectRight)
	    {
	        return false;
	    }

	    if (rectTop < yAtRectLeft && rectTop < yAtRectRight)
	    {
	        return false;
	    }

	    return true;
	}
	
	private float calcYforX(Vector2 a, Vector2 b, float xPos) {
		
		Vector2 direction = b.cpy().sub(a);
		
		if(direction.x == 0) {
			return 0;
		}
		
		direction = direction.scl(1/direction.x);
		
		return direction.y*(xPos - a.x) + a.y;
	}

}
