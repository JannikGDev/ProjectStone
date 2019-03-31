package com.sodacookie.pixelarena.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.sodacookie.pixelarena.StoneGame;
import com.sodacookie.pixelarena.entities.Sprite;

public class CreditScreen implements Screen {

	StoneGame game;
	Sprite background;
	Stage stage;
	
	Button btn_back;
	
	@Override
	public void init(StoneGame game) {
		this.game = game;
		
		Texture texture = game.assets.getTexture("creditsscreen.png");
		TextureRegion region = new TextureRegion(texture);
		background = new Sprite(game,region,400,300,800,600);
		
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		btn_back = new TextButton("back", game.UI.btn_style);
		btn_back.setPosition(0, 550);
		btn_back.setSize(200, 50);
		
		stage.addActor(btn_back);
		
		btn_back.addListener(new ChangeListener() {
			
			public void changed (ChangeEvent event, Actor actor) {
				CreditScreen.this.game.switchScreen(new Menu());
			}
		});
		
	}

	@Override
	public void update(float delta) {
		background.update(delta);
		
		stage.act();
		
	}

	@Override
	public void render(SpriteBatch batch) {
		background.render(batch);
		
		stage.draw();
	}

	@Override
	public void dispose() {
		
		
	}

}
