package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sodacookie.pixelarena.StoneGame;

public class WindZone implements Entity {

	StoneGame game;
	public Rectangle area;
	public Vector2 wind;
	public Sprite sprite;

	public WindZone(StoneGame game, float x, float y, float width, float height, Vector2 wind) {

		this.game = game;
		this.area = new Rectangle(x, y, width, height);
		this.wind = wind;
		
		float angle = wind.angleRad();
		
		TextureRegion region = new TextureRegion(game.assets.getTexture("wind2.png"));

		sprite = new Sprite(game, region, 0,0, (float)(Math.abs(Math.cos(angle)*width) + Math.abs(Math.sin(angle)*height)), (float)(Math.abs(Math.sin(angle)*width) + Math.abs(Math.cos(angle)*height)));
	}

	@Override
	public void update(float delta) {
		this.sprite.rotation = wind.angle();
		this.sprite.x = area.x + area.width / 2f;
		this.sprite.y = area.y + area.height / 2f;
		sprite.update(delta);
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.render(batch);
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
