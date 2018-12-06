package com.sodacookie.pixelarena.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.sodacookie.pixelarena.StoneGame;

public class Animation implements Entity {

	StoneGame game;
	public TextureRegion[] frames;
	public Map<String, int[]> anims;

	public int[] activeAnim;
	public int activeFrame;

	public boolean looping;
	public boolean paused;

	public float framesPerSecond;

	public float timer;
	
	public int frameWidth;
	public int frameHeight;

	public float x;
	public float y;

	public float width;
	public float height;

	public float originX;
	public float originY;

	public float rotation;

	public float scaleX;
	public float scaleY;

	public Animation(StoneGame game, TextureRegion tex, int cols, int rows) {

		this.game = game;

		frameWidth = tex.getRegionWidth() / cols;
		frameHeight = tex.getRegionHeight() / rows;

		
		this.frames = new TextureRegion[cols * rows];
		this.anims = new HashMap<String, int[]>();
		this.activeAnim = new int[] { 0 };
		this.looping = false;
		this.framesPerSecond = 10;
		this.timer = 0;
		this.paused = true;

		this.x = 0;
		this.y = 0;
		this.width = frameWidth;
		this.height = frameHeight;
		
		this.originX = this.width / 2f;
		this.originY = this.height / 2f;
		
		this.scaleX = 1;
		this.scaleY = 1;
		
		this.rotation = 0;
		
		
		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {

				frames[col * rows + row] = new TextureRegion(tex, col * frameWidth, row * frameHeight, frameWidth,
						frameHeight);

			}
		}
	}

	public void addAnim(String name, int[] frameNumbers) {

		anims.put(name, frameNumbers);

	}

	public void playAnim(String name, boolean looping) {
		this.activeAnim = anims.get(name);
		this.looping = looping;
		this.paused = false;
	}

	@Override
	public void update(float delta) {

		if (!paused) {
			this.timer += delta;

			if (timer >= 1 / framesPerSecond) {
				timer = 0;

				activeFrame++;

				if (activeFrame >= activeAnim.length) {
					activeFrame = 0;
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.begin();
		batch.draw(frames[activeAnim[activeFrame]], x, y, originX, originY, width, height, scaleX, scaleY, rotation);
		batch.end();
	}

	@Override
	public void collide(Entity collider) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getHitbox() {
		// TODO Auto-generated method stub
		return new Rectangle(x,y,width,height);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getTags() {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

}
