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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.sodacookie.pixelarena.StoneGame;

public class LoadScreen implements Screen {

	StoneGame game;
	Stage stage;
	
	boolean first;
	
	
	@Override
	public void init(StoneGame game) {
		this.game = game;

		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
				
		Label loadingText = new Label("Loading...",game.UI.lbl_style);
		loadingText.setPosition(game.WIDTH/2f - loadingText.getMinWidth()/2f, game.HEIGHT/2f);
		
		
		stage.addActor(loadingText);
		first = true;
		
	}

	@Override
	public void update(float delta) {
			
		stage.act();
		
		if(!first) {
			game.assets.loadAssets();
			game.switchScreen(new Menu());
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		stage.draw();
		
		first = false;
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
