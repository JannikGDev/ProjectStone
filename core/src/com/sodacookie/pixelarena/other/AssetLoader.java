package com.sodacookie.pixelarena.other;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {

	HashMap<String, Texture> textures;

	public static ArrayList<String> texPaths; 

	public AssetLoader() {

		textures = new HashMap<String, Texture>();
		
		texPaths = new ArrayList<String>();
		texPaths.add("backdrop1.png");
		texPaths.add("backdrop2.png");
		texPaths.add("backdrop3.png");
		texPaths.add("backdrop4.png");
		texPaths.add("ropepart.png");
		texPaths.add("stone.png");
		texPaths.add("wind.png");
		texPaths.add("wind2.png");
		texPaths.add("earthSlice.png");
		texPaths.add("rockSlice.png");
		texPaths.add("metalSlice.png");
		texPaths.add("creditsscreen.png");
		texPaths.add("titlescreen.png");
		
		for(int i = 0; i < 14; i++) {
			
			String eyeL = "face/eye/eyes" + toTwoDigitNum(i) + "L"  + ".png";
			String eyeR = "face/eye/eyes" + toTwoDigitNum(i) + "R"  + ".png";
			
			texPaths.add(eyeL);
			texPaths.add(eyeR);
		}
		
		for(int i = 0; i < 15; i++) {
			
			String mouth = "face/mouth/mouth" + toTwoDigitNum(i) + ".png";
			
			texPaths.add(mouth);
		}
		
		for(int i = 0; i < 6; i++) {
			
			String brow = "face/brow/brow" + toTwoDigitNum(i) + ".png";
			
			texPaths.add(brow);
		}
		
		
	}

	public void loadAssets() {

		for (int i = 0; i < texPaths.size(); i++) {
			String path = "images/" + texPaths.get(i);
			textures.put(texPaths.get(i), new Texture(path));
		}
		
	}

	public Texture getTexture(String name) {

		return textures.get(name);
	}
	
	public String toTwoDigitNum(int n) {
		
		if(n < 0) {
			n = -n;
		}
		
		if(n >= 100) {
			return (n % 100) + "";
		}
		else if(n < 10) {
			return "0" + n;
		}
		else {
			return "" + n;
		}
	}


}
