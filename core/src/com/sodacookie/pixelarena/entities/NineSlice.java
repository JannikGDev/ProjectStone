package com.sodacookie.pixelarena.entities;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.sodacookie.pixelarena.StoneGame;

public class NineSlice implements Entity {

	protected StoneGame game;

	public float x;
	public float y;

	public float width;
	public float height;

	public float originX;
	public float originY;

	public float rotation;

	public float scaleX;
	public float scaleY;

	int left, right, top, bottom;

	public TextureRegion texture;
	public TextureRegion topleft;
	public TextureRegion topright;
	public TextureRegion topmiddle;
	public TextureRegion centerleft;
	public TextureRegion centermiddle;
	public TextureRegion centerright;
	public TextureRegion bottomleft;
	public TextureRegion bottommiddle;
	public TextureRegion bottomright;

	public NineSlice(StoneGame game, TextureRegion texture, int left, int right, int top, int bottom, float x, float y,
			float width, float height) {

		this.game = game;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		
		this.scaleX = 1;
		this.scaleY = 1;
		
		this.originX = width/2f;
		this.originY = height/2f;

		int centerW = right - left;
		int centerH = bottom - top;
		int rightmargin = (texture.getRegionWidth() - right);
		int bottommargin = (texture.getRegionHeight() - bottom);
		
		topleft = new TextureRegion(texture, 0, 0, left, top);
		topmiddle = new TextureRegion(texture, left, 0, centerW, top);
		topright = new TextureRegion(texture, right, 0, rightmargin, top);

		centerleft = new TextureRegion(texture, 0, top, left, centerH );
		centermiddle = new TextureRegion(texture, left, top, centerW, centerH);
		centerright = new TextureRegion(texture, right, top, rightmargin, centerH);

		bottomleft = new TextureRegion(texture, 0, bottom, left, bottommargin);
		bottommiddle = new TextureRegion(texture, left, bottom, centerW, bottommargin);
		bottomright = new TextureRegion(texture, right, bottom,rightmargin, bottommargin);
		
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(SpriteBatch batch) {
		
		batch.begin();
		
		int centerW = right - left;
		int centerH = bottom - top;
		int rightmargin = (texture.getRegionWidth() - right);
		int bottommargin = (texture.getRegionHeight() - bottom);

		

		// Draw corners
		batch.draw(topleft, 
				this.x, 
				this.y + (height - top) * scaleY, 
				originX, originY - (height - top) * scaleY, 
				left, top, 
				scaleX, scaleY,
				rotation);
		
		batch.draw(topright, 
				this.x + (width - rightmargin) * scaleX, 
				this.y + (height - top) * scaleY, 
				originX - (width - rightmargin) * scaleX, originY - (height - top) * scaleY, 
				rightmargin, top, 
				scaleX, scaleY, 
				rotation);
		
		batch.draw(bottomleft, 
				this.x, 
				this.y, 
				originX, originY, 
				left, bottommargin, 
				scaleX, scaleY, 
				rotation);
		
		batch.draw(bottomright, 
				this.x + (width - rightmargin) * scaleX, 
				this.y,
				originX - (width - rightmargin) * scaleX, originY, 
				rightmargin, bottommargin, 
				scaleX, scaleY, 
				rotation);
		
		
		for (int y = top; y < height - bottommargin; y += centerH) {
			batch.draw(centerleft, 
					this.x, 
					this.y + y * scaleY,
					originX, originY - y * scaleY, 
					left, centerH, 
					scaleX, scaleY, 
					rotation);
			
			batch.draw(centerright, 
					this.x + (width - rightmargin)*scaleX, 
					this.y + y * scaleY,
					originX - (width - rightmargin)*scaleX, originY - y * scaleY, 
					rightmargin, centerH, 
					scaleX, scaleY, 
					rotation);
					
		}
		
		for (int x = left; x < width - rightmargin; x += centerW) {
			batch.draw(bottommiddle, 
					this.x + x * scaleX, 
					this.y,
					originX - x * scaleX, originY, 
					centerW, bottommargin, 
					scaleX, scaleY, 
					rotation);
			
			batch.draw(topmiddle, 
					this.x + x * scaleX, 
					this.y + (height - top) * scaleY,
					originX - x * scaleX, originY - (height - top) * scaleY, 
					centerW, top, 
					scaleX, scaleY, 
					rotation);
		}
		
		for (int x = left; x < width - rightmargin; x += centerW) {
			
			
			
			for (int y = top; y < height - bottommargin; y += centerH) {
				
				batch.draw(centermiddle, 
						this.x + x  * scaleX, 
						this.y + y * scaleY,
						originX - x * scaleX, originY - y * scaleY, 
						centerW, centerH, 
						scaleX, scaleY, 
						rotation);
				
			}
			
			
			
		}
		
		
		batch.end();
	}

	@Override
	public void collide(Entity collider) {

	}

	@Override
	public Rectangle getHitbox() {

		return new Rectangle(this.x, this.y, this.width, this.height);
	}

	@Override
	public void dispose() {

	}

	@Override
	public List<String> getTags() {

		return null;
	}

}
