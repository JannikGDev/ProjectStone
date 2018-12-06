package com.sodacookie.pixelarena.other;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {

	HashMap<String, Texture> textures;

	public static final String[] texPaths = new String[] { "backdrop1.png", "backdrop2.png", "backdrop3.png",
			"backdrop4.png", "eye.png", "eyeOuch.png", "mouth.png", "ropepart.png", "stone.png", "wind.png",
			"wind2.png", "stoneNineSlice.png", "earthNineSlice.png" };

	public AssetLoader() {

		textures = new HashMap<String, Texture>();

	}

	public void loadAssets() {

		for (int i = 0; i < texPaths.length; i++) {
			String path = "images/" + texPaths[i];
			textures.put(texPaths[i], new Texture(path));
		}

	}

	public Texture getTexture(String name) {

		return textures.get(name);
	}

}
