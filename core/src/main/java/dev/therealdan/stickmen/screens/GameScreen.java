package dev.therealdan.stickmen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.therealdan.stickmen.game.GameInstance;
import dev.therealdan.stickmen.game.Platform;
import dev.therealdan.stickmen.game.entities.Entity;
import dev.therealdan.stickmen.game.entities.Player;
import dev.therealdan.stickmen.main.StickmenApp;

public class GameScreen extends BaseScreen {

    private GameInstance instance;

    public GameScreen(StickmenApp app) {
        super(app);
        instance = new GameInstance();

        for (Controller controller : Controllers.getControllers())
            connected(controller);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        getInstance().tick(delta);

        float speed = 500 * camera.zoom;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || (Gdx.input.isKeyPressed(Input.Keys.W)))
            camera.position.add(new Vector3(0, speed * delta, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || (Gdx.input.isKeyPressed(Input.Keys.S)))
            camera.position.add(new Vector3(0, -speed * delta, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isKeyPressed(Input.Keys.A)))
            camera.position.add(new Vector3(-speed * delta, 0, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (Gdx.input.isKeyPressed(Input.Keys.D)))
            camera.position.add(new Vector3(speed * delta, 0, 0));

        for (Platform platform : getInstance().getPlatforms()) {
            platform.render(app);
        }

        for (Entity entity : getInstance().getEntities()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.getPosition().add(player.getMovement().x * 500f * delta, 0);
            }
            entity.render(app);
        }
    }

    @Override
    public void connected(Controller controller) {
        if (getInstance().getPlayer(controller) == null) {
            Player player = new Player(controller, new Vector2(camera.position.x, camera.position.y));
            getInstance().spawnPlayer(player);
        }
    }

    public GameInstance getInstance() {
        return instance;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        getInstance().spawnPlatform(new Platform(getMousePosition(), 500, 50, Color.BLUE));
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        camera.zoom = Math.max(0.5f, camera.zoom + amountY / 10f);
        return false;
    }

    @Override
    public boolean buttonDown(Controller controller, int i) {
        Player player = getInstance().getPlayer(controller);
        if (player == null) return false;
        switch (i) {
            default:
                System.out.println(i);
            case 0:
            case 9:
                player.jump();
        }
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int i, float v) {
        Player player = getInstance().getPlayer(controller);
        if (player == null) return false;
        player.axisMoved(controller, i, v);
        return false;
    }
}
