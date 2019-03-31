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
	public Sprite eyeLSprite;
	public Sprite eyeRSprite;
	public Sprite mouthSprite;
	public Sprite mouthShadowSprite;
	public Sprite browSprite;
	public Joint ropeJoint;

	float boostCounter;

	float boostDelay = 2;
	
	float eyeAngle = 50;
	float eyeDistance = 15;
	
	float mouthOffsetY = 20;
	float mouthScale = 0.3f;
	
	float browOffsetY = -5;
	
	Vector2 eyeTarget;
	
	TextureRegion stone;
	TextureRegion[] eyeL;
	TextureRegion[] eyeR;
	TextureRegion[] mouth;
	TextureRegion[] brow;
	
	int mouthIndex;
	int eyeIndex;
	int browIndex;
	
	boolean ouch;
	int ouchCounter;
	
	Vector2 lastVelocity;

	public StonePlayer(StoneGame game, float x, float y, World world) {

		this.game = game;
		this.world = world;
		this.mouthIndex = 0;
		this.eyeIndex = 0;
		this.browIndex = 0;
		
		mouth = new TextureRegion[15];
		brow = new TextureRegion[6];
		eyeL  = new TextureRegion[14];
		eyeR  = new TextureRegion[14];
		
		
		
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
		
		loadFaceSprites();
		
		sprite = new Sprite(game, stone, x, y, width, height);
		sprite.originX = sprite.width/2f;
		sprite.originY = sprite.height/2f;
		
		eyeLSprite = new Sprite(game, eyeL[eyeIndex], x, y, eyeL[eyeIndex].getRegionWidth(), eyeL[eyeIndex].getRegionHeight());
		eyeLSprite.originX = eyeLSprite.width/2f;
		eyeLSprite.originY = eyeLSprite.height/2f;
		
		eyeRSprite = new Sprite(game, eyeR[eyeIndex], x, y, eyeR[eyeIndex].getRegionWidth(), eyeR[eyeIndex].getRegionHeight());
		eyeRSprite.originX = eyeRSprite.width/2f;
		eyeRSprite.originY = eyeRSprite.height/2f;
		
		mouthSprite = new Sprite(game, mouth[mouthIndex], x, y, mouth[mouthIndex].getRegionWidth(), mouth[mouthIndex].getRegionHeight());
		mouthSprite.originX = mouthSprite.width/2f;
		mouthSprite.originY = mouthOffsetY;
		
		mouthShadowSprite = new Sprite(game, mouth[3], x, y, mouth[3].getRegionWidth(), mouth[3].getRegionHeight());
		mouthShadowSprite.originX = mouthShadowSprite.width/2f;
		mouthShadowSprite.originY = mouthOffsetY;
		
		browSprite = new Sprite(game, brow[browIndex], x, y, brow[browIndex].getRegionWidth(), brow[browIndex].getRegionHeight());
		browSprite.originX = browSprite.width/2f;
		browSprite.originY = browOffsetY;
		
		int mouseX = (int) (Gdx.input.getX() + game.camera.position.x - game.WIDTH / 2f);
		int mouseY = (int) (game.HEIGHT - Gdx.input.getY() + game.camera.position.y - game.HEIGHT / 2f);
		
		eyeTarget = new Vector2(mouseX,mouseY);
		
		

	}

	@Override
	public void update(float delta) {

		if (Gdx.input.isKeyPressed(Keys.A) && body.getAngularVelocity() < 3) {

			body.applyTorque(100 * delta, true);
		}

		if (Gdx.input.isKeyPressed(Keys.D) && body.getAngularVelocity() > -3) {

			body.applyTorque(-100 * delta, true);
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
			eyeIndex = 11;
			ouchCounter = 30;
		}
		else if(ouchCounter <= 0) {
			ouch = false;
			eyeIndex = 0;
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
		
		
		mouthSprite.scaleY = mouthScale;
		mouthShadowSprite.scaleY = 0;
		if(Math.abs(mouthScale) < 0.2f) {
			mouthSprite.scaleY = 0;
			mouthShadowSprite.scaleY = 1f;
			mouthShadowSprite.scaleX = 1f;
			mouthSprite.originY = mouthOffsetY;
		}
		else {
			
			if(mouthScale < 0) {
				mouthSprite.originY = mouthOffsetY - 20;
			}
			else {
				mouthSprite.originY = mouthOffsetY;
			}
		}
		
		mouthShadowSprite.originY = mouthSprite.originY;
		
		
		mouthSprite.x = sprite.x;
		mouthSprite.y = sprite.y;
		mouthShadowSprite.x = sprite.x;
		mouthShadowSprite.y = sprite.y;
		mouthSprite.rotation = sprite.rotation;
		mouthShadowSprite.rotation = sprite.rotation;
		
		
		
		// Calculate Eye Positions
		float relativeRotation = sprite.rotation + (90 - eyeAngle);
		
		eyeRSprite.x = (float) (sprite.x + Math.cos(relativeRotation*Math.PI/180)*eyeDistance);
		eyeRSprite.y = (float) (sprite.y + Math.sin(relativeRotation*Math.PI/180)*eyeDistance);
		
		relativeRotation += (90 - (relativeRotation - sprite.rotation))*2;
		
		eyeLSprite.x = (float) (sprite.x + Math.cos(relativeRotation*Math.PI/180)*eyeDistance);
		eyeLSprite.y = (float) (sprite.y + Math.sin(relativeRotation*Math.PI/180)*eyeDistance);
		
		if(ouch) {
			//Replace normal eyes with "ouch" eyes
			eyeLSprite.texture = eyeL[11];
			eyeRSprite.texture = eyeR[11];
			eyeRSprite.rotation = sprite.rotation;
			eyeLSprite.rotation = sprite.rotation;
		}
		else {
			eyeLSprite.texture = eyeL[0];
			eyeRSprite.texture = eyeR[0];
			
			// Rotate Eyes
			Vector2 eyePos = new Vector2(eyeRSprite.x, eyeRSprite.y);
			eyeRSprite.rotation = new Vector2(eyeTarget.x - eyePos.x, eyeTarget.y - eyePos.y).angle();
			
			eyePos = new Vector2(eyeLSprite.x, eyeLSprite.y);
			eyeLSprite.rotation = new Vector2(eyeTarget.x - eyePos.x, eyeTarget.y - eyePos.y).angle();
		}
		
		
		
		
		
		browSprite.x = sprite.x;
		browSprite.y = sprite.y;
		browSprite.rotation = sprite.rotation;
		
		sprite.render(batch);
		mouthShadowSprite.render(batch);
		mouthSprite.render(batch);
		eyeRSprite.render(batch);
		eyeLSprite.render(batch);
		//browSprite.render(batch);
		
	}
	
	public void loadFaceSprites() {
		
		for(int i = 0; i < mouth.length; i++) {
			mouth[i] = new TextureRegion(game.assets.getTexture("face/mouth/" + "mouth" + toTwoDigitNum(i) + ".png"));
		}
		
		for(int i = 0; i < brow.length; i++) {
			brow[i] = new TextureRegion(game.assets.getTexture("face/brow/" + "brow" + toTwoDigitNum(i) + ".png"));
		}
		
		for(int i = 0; i < eyeL.length; i++) {
			eyeL[i] = new TextureRegion(game.assets.getTexture("face/eye/eyes" + toTwoDigitNum(i) + "L.png"));
			eyeR[i] = new TextureRegion(game.assets.getTexture("face/eye/eyes" + toTwoDigitNum(i) + "R.png"));
		}
	}
	
	public String toTwoDigitNum(int n) {
		
		if(n < 0) {
			n = -n;
		}
		
		if(n >= 100) {
			return (n % 100) + "";
		}
		else if(n < 10) {
			return "0" + n;
		}
		else {
			return "" + n;
		}
		
		
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
