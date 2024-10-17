package dev.therealdan.stickmen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.therealdan.stickmen.game.GameInstance;
import dev.therealdan.stickmen.game.Platform;
import dev.therealdan.stickmen.game.entities.Entity;
import dev.therealdan.stickmen.game.entities.Player;
import dev.therealdan.stickmen.game.entities.weapons.AssaultRifle;
import dev.therealdan.stickmen.game.entities.weapons.Pistol;
import dev.therealdan.stickmen.main.StickmenApp;

import java.util.Random;

public class GameScreen extends BaseScreen {

    private Random random = new Random();
    private GameInstance instance;

    public GameScreen(StickmenApp app) {
        super(app);
        instance = new GameInstance();
        camera.zoom = 2;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        generate();

        getInstance().tick(delta);

        Vector2 position = null;
        for (Controller controller : Controllers.getControllers()) {
            Player player = getInstance().getPlayer(controller);
            if (player == null) continue;

            if (position == null) {
                position = player.getPosition().cpy();
            } else {
                position.add(player.getPosition()).scl(0.5f);
            }

            player.controls(controller, getInstance());
        }

        if (position != null) {
            camera.position.lerp(new Vector3(position.x, position.y, camera.position.z), 0.1f);

            float minDistance = getPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2).dst(getPosition(Gdx.graphics.getWidth() / 2, (int) (Gdx.graphics.getHeight() * 0.85f)));
            float maxDistance = getPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2).dst(getPosition(Gdx.graphics.getWidth() / 2, (int) (Gdx.graphics.getHeight() * 0.9f)));
            boolean zoomIn = false;
            boolean zoomOut = false;
            for (Controller controller : Controllers.getControllers()) {
                Player player = getInstance().getPlayer(controller);
                if (player == null) continue;
                if (player.getPosition().dst(position) > maxDistance) zoomOut = true;
                if (player.getPosition().dst(position) < minDistance) zoomIn = true;
            }
            if (zoomOut) {
                camera.zoom += 0.01f * ((camera.zoom + 1) - camera.zoom);
            } else if (zoomIn && camera.zoom > 2) {
                camera.zoom += 0.002f * ((camera.zoom - 1) - camera.zoom);
            }
        }

        for (Platform platform : getInstance().getPlatforms()) {
            platform.render(app);
        }

        for (Entity entity : getInstance().getEntities()) {
            entity.render(app);
        }
    }

    public void generate() {
        Vector2 position = random.nextBoolean() ? getPosition(random.nextBoolean() ? -random.nextInt(Gdx.graphics.getWidth()) : Gdx.graphics.getWidth() + random.nextInt(Gdx.graphics.getWidth()), -Gdx.graphics.getHeight() + random.nextInt(Gdx.graphics.getHeight() * 3)) : getPosition(-Gdx.graphics.getWidth() + random.nextInt(Gdx.graphics.getWidth() * 3), random.nextBoolean() ? -random.nextInt(Gdx.graphics.getHeight()) : Gdx.graphics.getHeight() + random.nextInt(Gdx.graphics.getHeight()));
        Platform newPlatform = new Platform(position, 500, 50, Color.DARK_GRAY);
        for (Platform platform : getInstance().getPlatforms())
            if (platform.getPosition().dst(position) < Math.max(platform.getWidth(), platform.getHeight()) + Math.max(newPlatform.getWidth(), newPlatform.getHeight()))
                return;
        getInstance().spawnPlatform(newPlatform);
    }

    public GameInstance getInstance() {
        return instance;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (button) {
            case 0:
                getInstance().spawnPlatform(new Platform(getMousePosition(), 500, 50, Color.BLUE));
                break;
            case 1:
                getInstance().spawnEntity(random.nextBoolean() ? new Pistol(getMousePosition()) : new AssaultRifle(getMousePosition()));
                break;
        }
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
        if (player == null) {
            player = new Player(controller, new Vector2(camera.position.x, camera.position.y));
            getInstance().spawnEntity(player);
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
