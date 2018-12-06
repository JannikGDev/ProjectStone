package com.sodacookie.pixelarena.other;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class UserInterface {

	Texture up;
	Texture down;
	Texture over;
	
	public BitmapFont font;
	
	public TextButtonStyle btn_style;
	
	public LabelStyle lbl_style;
	
	
	public UserInterface() {
		
		
		font = new BitmapFont();
		
		
		Pixmap map = new Pixmap(200, 200, Format.RGBA8888);
		
		map.setColor(0.5f, 0.5f,0.5f, 1);
		map.fillRectangle(0, 0, 190, 190);
		
		up = new Texture(map);
		
		map.setColor(0.4f, 0.4f,0.4f, 1);
		map.fillRectangle(0, 0, 190, 190);
		
		down = new Texture(map);
		
		map.setColor(0.6f, 0.6f,0.6f, 1);
		map.fillRectangle(0, 0, 190, 190);
		
		over = new Texture(map);
		
		btn_style = new TextButtonStyle();
		btn_style.up = new TextureRegionDrawable(new TextureRegion(up));
		btn_style.down = new TextureRegionDrawable(new TextureRegion(down));
		btn_style.over = new TextureRegionDrawable(new TextureRegion(over));
		btn_style.font = font;
		
		lbl_style = new LabelStyle(font, new Color(1,1,1,1));
	}
	
	
	
	
	
	
	
	
}
