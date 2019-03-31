package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sodacookie.pixelarena.StoneGame;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Booster implements Entity {

	StoneGame game;
	public Rectangle area;
	public Vector2 direction;
	public Sprite sprite;
	public float boostcounter;
	public boolean boostReady;
	
	
	public static final float BOOSTDELAY = 1.0f;

	public Booster(StoneGame game, float x, float y, float width, float height, Vector2 wind) {

		this.game = game;
		this.area = new Rectangle(x, y, width, height);
		this.direction = wind;
		this.boostcounter = 1.0f;
		this.boostReady = true;
		
		float angle = wind.angleRad();
		
		TextureRegion region = new TextureRegion(game.assets.getTexture("wind2.png"));

		sprite = new Sprite(region);
		sprite.setScale(((float)(Math.abs(Math.cos(angle)*width) + Math.abs(Math.sin(angle)*height)))/sprite.getWidth(), (float)(Math.abs(Math.sin(angle)*width) + Math.abs(Math.cos(angle)*height))/sprite.getHeight());	
	}

	@Override
	public void update(float delta) {
		this.sprite.setRotation(direction.angle());
		this.sprite.setX(area.x + area.width / 2f);
		this.sprite.setY(area.y + area.height / 2f);
		
		if(boostcounter < BOOSTDELAY) {
			boostcounter += delta;
			boostReady = false;
		}
		else {
			boostReady = true;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.begin();
		sprite.setColor(new Color(1,boostcounter,boostcounter,1));
		sprite.draw(batch);
		batch.end();
	}

	@Override
	public void collide(Entity collider) {

	}

	@Override
	public Rectangle getHitbox() {

		return area;
	}

	@Override
	public void dispose() {

	}

	@Override
	public List<String> getTags() {

		return new ArrayList<String>();
	}

}
