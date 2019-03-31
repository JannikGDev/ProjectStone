package com.sodacookie.pixelarena.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.sodacookie.pixelarena.StoneGame;
import com.sodacookie.pixelarena.entities.Sprite;

public class Menu implements Screen {

	StoneGame game;
	Stage stage;
	
	Button btn_start;
	Button credits;
	
	Sprite background;
	
	@Override
	public void init(StoneGame game) {
		this.game = game;

		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		btn_start = new TextButton("Start game", game.UI.btn_style);
		btn_start.setPosition(300, 75);
		btn_start.setSize(200, 50);
		
		stage.addActor(btn_start);
		
		credits = new TextButton("Credits", game.UI.btn_style);
		credits.setPosition(300, 25);
		credits.setSize(200, 50);
		
		stage.addActor(credits);
		
		btn_start.addListener(new ChangeListener() {
			
			public void changed (ChangeEvent event, Actor actor) {
				Menu.this.game.switchScreen(new GameScreen("leveldata/earth0.xml",0));
			}
		});
		

		credits.addListener(new ChangeListener() {
			
			public void changed (ChangeEvent event, Actor actor) {
				Menu.this.game.switchScreen(new CreditScreen());
			}
		});
		
		Texture texture = game.assets.getTexture("titlescreen.png");
		TextureRegion region = new TextureRegion(texture);
		background = new Sprite(game,region,400,300,800,600);
		
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
		stage.dispose();
	}

}
