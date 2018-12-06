package com.sodacookie.pixelarena.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.sodacookie.pixelarena.StoneGame;
import com.sodacookie.pixelarena.entities.RectangleShape;

public class Evader implements Screen {

	
	RectangleShape shape;
	ArrayList<RectangleShape> blocks;
	float counter;
	float delay;
	float speed;
	Random rand;
	long progress;
	int last;
	boolean pause;
	StoneGame game;
	
	@Override
	public void init(StoneGame game) {
		this.game = game;
		
		this.shape = new RectangleShape(game, 300, 50, 100, 100);
		this.shape.color = Color.GREEN;
		this.rand = new Random();
		this.blocks = new ArrayList<RectangleShape>();
		this.delay = 2;
		this.counter = delay;
		this.progress = 0;
		this.last = rand.nextInt(3);
		this.pause = false;
	}

	@Override
	public void update(float delta) {
		
		if(!pause) {
			
			if(Gdx.input.isKeyJustPressed(Keys.LEFT)) {
				shape.x = 300;
			}
			
			if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
				shape.x = 500;
			}
			
			if(Gdx.input.isKeyJustPressed(Keys.DOWN) || Gdx.input.isKeyJustPressed(Keys.UP)) {
				shape.x = 400;
			}
			
			counter -= delta;
			if(counter <= 0) {
				counter = delay;
				this.speed = 10 * 1/delay;
				
				last = ((rand.nextInt(2) + 1) + last) % 3;
				
				for(int n = 0; n < 3; n++) {
					if(n != last) {
						blocks.add(new RectangleShape(game, 300+n*100, 650, 100, 100));
					}
					
				}
				this.progress += 1;
				
				this.delay = (float) (1.5f * Math.exp(-0.05*progress)) + 0.3f;
			}
			
			
			for(int i = blocks.size() - 1; i >= 0; i--) {
				RectangleShape rect = blocks.get(i);
				
				rect.y -= 100*delta*speed;
				
				if(rect.y < -100) {
					blocks.remove(i);
				}
				
				Rectangle intersection = new Rectangle();
				
				if(Intersector.intersectRectangles(rect.getHitbox(), shape.getHitbox(), intersection)) {
					
					pause = true;
					
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		
		shape.render(batch);
			
		for(int i = blocks.size() - 1; i >= 0; i--) {
			RectangleShape rect = blocks.get(i);
			rect.render(batch);
		}
	}

	@Override
	public void dispose() {
		
	}

}
