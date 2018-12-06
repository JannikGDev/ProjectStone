package com.sodacookie.pixelarena.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.sodacookie.pixelarena.StoneGame;
import com.sodacookie.pixelarena.entities.Background;
import com.sodacookie.pixelarena.entities.Entity;
import com.sodacookie.pixelarena.entities.StonePlayer;
import com.sodacookie.pixelarena.entities.WindZone;
import com.sodacookie.pixelarena.other.Exit;
import com.sodacookie.pixelarena.entities.Plattform;
import com.sodacookie.pixelarena.entities.RectangleShape;
import com.sodacookie.pixelarena.entities.Rope;

public class GameScreen implements Screen {

	World physicsWorld;

	Box2DDebugRenderer debugRenderer;
	OrthographicCamera physicsCamera;
	FitViewport debugViewport;

	StoneGame game;

	List<Plattform> plattforms;
	List<WindZone> windZones;
	List<Exit> exits;
	StonePlayer player;

	String levelFile;
	int entry;

	public static Vector2 playerVelocity = new Vector2();
	public static float playerRotation = 0;
	public static float playerRotationVelocity = 0;

	Rope rope;

	Background background;

	Rectangle cameraBounds;

	public GameScreen(String levelFile, int entry) {

		this.levelFile = levelFile;
		this.entry = entry;
	}

	@Override
	public void init(StoneGame game) {

		this.game = game;

		Box2D.init();

		physicsWorld = new World(new Vector2(0, -10), true);

		debugRenderer = new Box2DDebugRenderer();

		this.physicsCamera = new OrthographicCamera();
		this.debugViewport = new FitViewport(game.WIDTH / game.PHYSICS_ZOOM, game.HEIGHT / game.PHYSICS_ZOOM,
				physicsCamera);
		this.physicsCamera.setToOrtho(false, game.WIDTH / game.PHYSICS_ZOOM, game.HEIGHT / game.PHYSICS_ZOOM);

		plattforms = new ArrayList<Plattform>();
		windZones = new ArrayList<WindZone>();
		exits = new ArrayList<Exit>();

		XmlReader levelParser = new XmlReader();

		Element levelData = levelParser.parse(Gdx.files.internal(levelFile));

		float backdropOffset = levelData.getFloatAttribute("backdropOffset");
		float levelWidth = levelData.getFloatAttribute("width");
		float levelHeight = levelData.getFloatAttribute("height");

		this.cameraBounds = new Rectangle(0, 0, levelWidth, levelHeight);

		float lowerX = levelData.getChildByName("lowerbound").getFloatAttribute("x") + 10;
		float lowerY = levelData.getChildByName("lowerbound").getFloatAttribute("y") + 10;
		float upperX = levelData.getChildByName("upperbound").getFloatAttribute("x") - 10;
		float upperY = levelData.getChildByName("upperbound").getFloatAttribute("y") - 10;
		
		//LevelBottom
		plattforms.add(new Plattform(game, lowerX - levelWidth, lowerY - game.HEIGHT, levelWidth*3, game.HEIGHT, 0, physicsWorld, false));
		
		//LevelTop
		plattforms.add(new Plattform(game, lowerX - levelWidth, upperY, levelWidth*3, game.HEIGHT, 0, physicsWorld, false));
		
		//LevelRightWall
		plattforms.add(new Plattform(game, upperX, lowerY - levelHeight, game.WIDTH, levelHeight*3, 0, physicsWorld, false));
		
		//LevelLeftWall
		plattforms.add(new Plattform(game, lowerX - game.WIDTH, lowerY - levelHeight, game.WIDTH, levelHeight*3, 0, physicsWorld, false));

		Array<Element> plattformList = levelData.getChildrenByName("plattform");

		for (int i = 0; i < plattformList.size; i++) {

			Element e = plattformList.get(i);

			float x = e.getFloatAttribute("x");
			float y = e.getFloatAttribute("y");
			float width = e.getFloatAttribute("width");
			float height = e.getFloatAttribute("height");
			float rotation = e.getFloatAttribute("rotation");
			boolean hookable = e.getBooleanAttribute("hookable");

			plattforms.add(new Plattform(game, x, y, width, height, rotation, physicsWorld, hookable));

		}

		Array<Element> windZoneList = levelData.getChildrenByName("windzone");

		for (int i = 0; i < windZoneList.size; i++) {

			Element e = windZoneList.get(i);

			float x = e.getFloatAttribute("x");
			float y = e.getFloatAttribute("y");
			float width = e.getFloatAttribute("width");
			float height = e.getFloatAttribute("height");
			float windX = e.getFloatAttribute("wind-x");
			float windY = e.getFloatAttribute("wind-y");

			windZones.add(new WindZone(game, x, y, width, height, new Vector2(windX, windY)));

		}

		Array<Element> entryList = levelData.getChildrenByName("entry");

		for (int i = 0; i < entryList.size; i++) {

			Element e = entryList.get(i);

			float x = e.getFloatAttribute("x");
			float y = e.getFloatAttribute("y");
			int id = e.getIntAttribute("id");

			if (id == entry) {
				player = new StonePlayer(game, x, y, physicsWorld);
			}
		}

		Array<Element> exitList = levelData.getChildrenByName("exit");

		for (int i = 0; i < exitList.size; i++) {

			Element e = exitList.get(i);

			float x = e.getFloatAttribute("x");
			float y = e.getFloatAttribute("y");
			float width = e.getFloatAttribute("width");
			float height = e.getFloatAttribute("height");
			int id = e.getIntAttribute("id");
			int nextLevel = e.getIntAttribute("nextLevel");
			int nextEntry = e.getIntAttribute("entry");

			exits.add(new Exit(x, y, width, height, id, nextLevel, nextEntry));
		}

		player.body.setTransform(player.body.getTransform().getPosition(), this.playerRotation);
		player.body.setAngularVelocity(this.playerRotationVelocity);
		player.body.setLinearVelocity(this.playerVelocity);

		background = new Background(this.game, backdropOffset);

	}

	@Override
	public void update(float delta) {

		physicsWorld.step(delta, game.PHYSCIS_VELOCITY_ITERATIONS, game.PHYSCIS_POSITION_ITERATIONS);

		for (int i = 0; i < plattforms.size(); i++) {
			plattforms.get(i).update(delta);

			if (rope != null) {
				if (rope.checkCutting(plattforms.get(i).getHitbox())) {
					rope.dispose();
					rope = null;
				}
			}
		}

		player.update(delta);

		background.update(delta);

		if (rope != null) {
			rope.update(delta);
		}

		Rectangle playerBox = player.getHitbox();

		for (int i = 0; i < exits.size(); i++) {
			Exit e = exits.get(i);

			if (e.getRect().contains(playerBox.x + playerBox.width / 2f, playerBox.y + playerBox.height / 2f)) {

				this.playerRotation = player.body.getAngle();
				this.playerRotationVelocity = player.body.getAngularVelocity();
				this.playerVelocity = player.body.getLinearVelocity();

				game.switchScreen(new GameScreen("leveldata/level" + e.nextLevel + ".xml", e.entry));
			}
		}

		for (int i = 0; i < windZones.size(); i++) {
			windZones.get(i).update(delta);
			WindZone wind = windZones.get(i);

			if (wind.area.contains(playerBox.getCenter(new Vector2()))) {
				player.body.applyForceToCenter(wind.wind, true);
			}

		}

		if (Gdx.input.justTouched()) {

			int x = (int) (Gdx.input.getX() + game.camera.position.x - game.WIDTH / 2f);
			int y = (int) (game.HEIGHT - Gdx.input.getY() + game.camera.position.y - game.HEIGHT / 2f);

			physicsWorld.rayCast(new RayCastCallback() {

				@Override
				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

					Entity e = (Entity) fixture.getBody().getUserData();

					if (e != null && e.getTags() != null && e.getTags().contains("hookable")) {

						RopeJointDef defJoint = new RopeJointDef();
						defJoint.maxLength = player.body.getPosition().dst(point);
						defJoint.bodyA = player.body;
						defJoint.bodyB = fixture.getBody();
						defJoint.collideConnected = true;
						defJoint.localAnchorA.set(new Vector2(0, 0));
						defJoint.localAnchorB.set(point.cpy().sub(fixture.getBody().getPosition()));

						RopeJoint joint = (RopeJoint) physicsWorld.createJoint(defJoint);

						rope = new Rope(game, physicsWorld, point.cpy().scl(game.PHYSICS_ZOOM), player.body, joint);

						return 0;
					} else {
						return 0;
					}

				}

			}, player.body.getPosition(), new Vector2(x / game.PHYSICS_ZOOM, y / game.PHYSICS_ZOOM));

		} else {

			if (rope != null && !Gdx.input.isButtonPressed(Buttons.LEFT)) {
				rope.dispose();
				rope = null;
			}
		}

		float cameraX = game.camera.position.x;
		float cameraY = game.camera.position.y;
		
		cameraX = Math.min(cameraX, player.sprite.x + game.WIDTH/16f*0);
		cameraX = Math.max(cameraX, player.sprite.x - game.WIDTH/16f*0);
		
		cameraY = Math.min(cameraY, player.sprite.y + game.HEIGHT/16f);
		cameraY = Math.max(cameraY, player.sprite.y - game.HEIGHT/16f);

		cameraX = Math.max(cameraX, cameraBounds.x + game.WIDTH / 2f);
		cameraY = Math.max(cameraY, cameraBounds.y + game.HEIGHT / 2f);

		cameraX = Math.min(cameraX, cameraBounds.width - game.WIDTH / 2f);
		cameraY = Math.min(cameraY, cameraBounds.height - game.HEIGHT / 2f);

		game.camera.position.set(cameraX, cameraY, 0);
	}

	@Override
	public void render(SpriteBatch batch) {

		background.render(batch);

		for (int i = 0; i < windZones.size(); i++) {
			windZones.get(i).render(batch);
		}

		for (int i = 0; i < plattforms.size(); i++) {
			plattforms.get(i).render(batch);
		}

		if (rope != null) {
			rope.render(batch);
		}

		player.render(batch);

		//debugRenderer.render(physicsWorld, physicsCamera.combined);
	}

	@Override
	public void dispose() {

	}

}
