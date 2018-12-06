package com.sodacookie.pixelarena.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class Menu implements Screen {

	StoneGame game;
	Stage stage;
	
	Button btn_start;
	@Override
	public void init(StoneGame game) {
		this.game = game;

		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
				
		btn_start = new TextButton("Start game", game.UI.btn_style);
		btn_start.setPosition(300, 400);
		btn_start.setSize(200, 50);
		
		
		stage.addActor(btn_start);
		
		
		btn_start.addListener(new ChangeListener() {
			
			public void changed (ChangeEvent event, Actor actor) {
				Menu.this.game.switchScreen(new GameScreen("leveldata/world0.xml",0));
			}
		});
		
	}

	@Override
	public void update(float delta) {
			
		stage.act();
		
	}

	@Override
	public void render(SpriteBatch batch) {
		stage.draw();
	
		batch.begin();
		
		game.UI.font.draw(batch, "Journey of a Stone", 100, 500,  600, Align.center, false);
		
		batch.end();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
