package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.sodacookie.pixelarena.StoneGame;

public class StonePlayer implements Entity {

	StoneGame game;
	World world;
	
	public Body body;

	public Sprite sprite;
	public Sprite eyeSprite;
	public Sprite mouthSprite;

	public Joint ropeJoint;

	float boostCounter;

	float boostDelay = 2;
	
	float eyeAngle = 50;
	float eyeDistance = 15;
	
	float mouthOffsetY = 20;
	float mouthScale = 0.3f;
	
	Vector2 eyeTarget;
	
	TextureRegion stone;
	TextureRegion eye;
	TextureRegion eyeOuch;
	TextureRegion mouth;
	
	boolean ouch;
	int ouchCounter;
	
	Vector2 lastVelocity;

	public StonePlayer(StoneGame game, float x, float y, World world) {

		this.game = game;
		this.world = world;

		// First we create a body definition
		BodyDef bodyDef = new BodyDef();

		// We set our body to dynamic, for something like ground which doesn't move we
		// would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;

		// Set our body's starting position in the world
		bodyDef.position.set(x / game.PHYSICS_ZOOM, y / game.PHYSICS_ZOOM);

		// Create our body in the world using our body definition
		body = world.createBody(bodyDef);
		
		float width = 50f;
		float height = 50f;
		boostCounter = 0;
		
		float[] vertices = new float[] {8,11,	23,3,	49,5,	60,19,	58,48,	37,57,	15,57,	0,31};
		
		float[] physic_vertices = new float[vertices.length];

		for (int i = 0; i < vertices.length; i++) {
			physic_vertices[i] = ((vertices[i]-32f) / 64f) * width;
			
			physic_vertices[i] = physic_vertices[i] / game.PHYSICS_ZOOM;
		}

		// Create a polygon shape
		PolygonShape poly = new PolygonShape();

		poly.set(physic_vertices);
		

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = poly;
		fixtureDef.density = 5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.2f; // Make it bounce a little bit

		// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);

		poly.dispose();

		stone = new TextureRegion(game.assets.getTexture("stone.png"));
		
		eye = new TextureRegion(game.assets.getTexture("eye.png"));
		
		mouth = new TextureRegion(game.assets.getTexture("mouth.png"));
		
		eyeOuch = new TextureRegion(game.assets.getTexture("eyeOuch.png"));
		
		sprite = new Sprite(game, stone, x, y, width, height);
		sprite.originX = sprite.width/2f;
		sprite.originY = sprite.height/2f;
		
		eyeSprite = new Sprite(game, eye, x, y, eye.getRegionWidth(), eye.getRegionHeight());
		eyeSprite.originX = eyeSprite.width/2f;
		eyeSprite.originY = eyeSprite.height/2f;
		
		mouthSprite = new Sprite(game, mouth, x, y, mouth.getRegionWidth(), mouth.getRegionHeight());
		mouthSprite.originX = mouthSprite.width/2f;
		mouthSprite.originY = mouthOffsetY;
		
		int mouseX = (int) (Gdx.input.getX() + game.camera.position.x - game.WIDTH / 2f);
		int mouseY = (int) (game.HEIGHT - Gdx.input.getY() + game.camera.position.y - game.HEIGHT / 2f);
		
		eyeTarget = new Vector2(mouseX,mouseY);
		
		

	}

	@Override
	public void update(float delta) {

		if (Gdx.input.isKeyPressed(Keys.Q) && body.getAngularVelocity() < 2) {

			body.applyTorque(100 * delta, true);
		}

		if (Gdx.input.isKeyPressed(Keys.E) && body.getAngularVelocity() > -2) {

			body.applyTorque(-100 * delta, true);
		}

		if (boostCounter <= 0) {
			if (Gdx.input.isKeyPressed(Keys.W)) {

				body.applyForceToCenter(new Vector2(0, 100), true);
				boostCounter = boostDelay;
			}

			if (Gdx.input.isKeyPressed(Keys.A)) {
				
				body.applyForceToCenter(new Vector2(-100, 0), true);
				boostCounter = boostDelay;
			}

			if (Gdx.input.isKeyPressed(Keys.S)) {

				body.applyForceToCenter(new Vector2(0, -100), true);
				boostCounter = boostDelay;
			}

			if (Gdx.input.isKeyPressed(Keys.D)) {

				body.applyForceToCenter(new Vector2(100, 0), true);
				boostCounter = boostDelay;
			}
		} else {
			boostCounter -= delta;
		}

		sprite.x = body.getPosition().x * game.PHYSICS_ZOOM;
		sprite.y = body.getPosition().y * game.PHYSICS_ZOOM;

		sprite.rotation = (float) (body.getAngle() * 180 / Math.PI);
		
		int mouseX = (int) (Gdx.input.getX() + game.camera.position.x - game.WIDTH / 2f);
		int mouseY = (int) (game.HEIGHT - Gdx.input.getY() + game.camera.position.y - game.HEIGHT / 2f);
		
		eyeTarget = new Vector2(mouseX, mouseY);
		
		if(lastVelocity == null) {
			lastVelocity = body.getLinearVelocity();
		}
		
		float damping = (body.getLinearVelocity().sub(lastVelocity)).len();
		
		if(damping > 2.5f || body.getAngularVelocity() > 12.0f) {
			ouch = true;
			eyeSprite.texture = eyeOuch;
			ouchCounter = 30;
		}
		else if(ouchCounter <= 0) {
			ouch = false;
			eyeSprite.texture = eye;
		} else {
			ouchCounter -= 1;
		}
		
		lastVelocity = new Vector2(body.getLinearVelocity().x,body.getLinearVelocity().y);
		
		float mouthScaleTarget;
		
		if(ouch) {
			mouthScaleTarget = -0.4f;;
			mouthScale += (mouthScaleTarget - mouthScale)*0.2f;
		}
		else {
			mouthScaleTarget = (float)(1/(1+Math.exp(-(body.getLinearVelocity().len()-2))));
			mouthScale += (mouthScaleTarget - mouthScale)*0.05f;
		}
		
		
	}

	@Override
	public void render(SpriteBatch batch) {

		sprite.render(batch);
		
		
		if(mouthScale < 0) {
			mouthSprite.originY = mouthOffsetY - 20;
		}
		else {
			mouthSprite.originY = mouthOffsetY;
		}
		
		mouthSprite.x = sprite.x;
		mouthSprite.y = sprite.y;
		mouthSprite.rotation = sprite.rotation;
		mouthSprite.scaleY = mouthScale;
		mouthSprite.render(batch);
		
		
		float relativeRotation = sprite.rotation + (90 - eyeAngle);
		
		eyeSprite.x = (float) (sprite.x + Math.cos(relativeRotation*Math.PI/180)*eyeDistance);
		eyeSprite.y = (float) (sprite.y + Math.sin(relativeRotation*Math.PI/180)*eyeDistance);
		
		Vector2 eyePos = new Vector2(eyeSprite.x, eyeSprite.y);
		eyeSprite.rotation = new Vector2(eyeTarget.x - eyePos.x, eyeTarget.y - eyePos.y).angle();
		if(ouch) {
			eyeSprite.rotation = sprite.rotation + 180;
		}
		eyeSprite.render(batch);
		
		relativeRotation += (90 - (relativeRotation - sprite.rotation))*2;
		
		eyeSprite.x = (float) (sprite.x + Math.cos(relativeRotation*Math.PI/180)*eyeDistance);
		eyeSprite.y = (float) (sprite.y + Math.sin(relativeRotation*Math.PI/180)*eyeDistance);
		
		eyePos = new Vector2(eyeSprite.x, eyeSprite.y);
		eyeSprite.rotation = new Vector2(eyeTarget.x - eyePos.x, eyeTarget.y - eyePos.y).angle();
		
		if(ouch) {
			eyeSprite.rotation = sprite.rotation;
		}
		eyeSprite.render(batch);
		
	}

	@Override
	public void collide(Entity collider) {

	}

	@Override
	public Rectangle getHitbox() {

		return new Rectangle(sprite.getHitbox());
	}

	@Override
	public void dispose() {

	}

	@Override
	public List<String> getTags() {
		
		return new ArrayList<String>();
	}

}
