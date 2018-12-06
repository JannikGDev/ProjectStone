package com.sodacookie.pixelarena;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sodacookie.pixelarena.other.AssetLoader;
import com.sodacookie.pixelarena.other.UserInterface;
import com.sodacookie.pixelarena.screens.LoadScreen;
import com.sodacookie.pixelarena.screens.Menu;
import com.sodacookie.pixelarena.screens.Screen;

public class StoneGame extends ApplicationAdapter {
	SpriteBatch batch;
	Screen active;
	
	
    public final static float WIDTH = 800;
    public final static float HEIGHT = 600;
	
    public final static int MIN_FPS = 20;

    
	public final static int PHYSCIS_VELOCITY_ITERATIONS = 6;
	public final static int PHYSCIS_POSITION_ITERATIONS = 2;
    public final float PHYSICS_ZOOM = 100f;
    
	public ShapeRenderer shape;
	public Preferences memory;
	public UserInterface UI;
	public OrthographicCamera camera;
	public FitViewport viewport;
	
	public AssetLoader assets;
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.shape = new ShapeRenderer();
		this.UI = new UserInterface();
		this.memory =  Gdx.app.getPreferences("stonegame-savedata");
		this.camera = new OrthographicCamera();
		this.viewport = new FitViewport(WIDTH, HEIGHT, camera);
		this.camera.setToOrtho(false, WIDTH, HEIGHT);
		
		this.assets = new AssetLoader();
		
		Box2D.init();
		
		active = new LoadScreen();
		active.init(this);
	}

	@Override
	public void render () {
		
		float delta = Gdx.graphics.getDeltaTime();
		
		delta = Math.min(delta, 1f/MIN_FPS);
		
		active.update(delta);
		
		camera.update();
		
		
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		shape.setProjectionMatrix(camera.combined);
		
		active.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		memory.flush();
	}
	
	public void switchScreen(Screen nextScreen) {
		active.dispose();
		
		active = nextScreen;
		active.init(this);
		
	}
}
