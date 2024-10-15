package dev.therealdan.stickmen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import dev.therealdan.stickmen.game.GameInstance;
import dev.therealdan.stickmen.game.entities.Entity;
import dev.therealdan.stickmen.main.StickmenApp;

public class GameScreen extends BaseScreen {

    private GameInstance instance;

    public GameScreen(StickmenApp app) {
        super(app);
        instance = new GameInstance();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        float speed = 500 * camera.zoom;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || (Gdx.input.isKeyPressed(Input.Keys.W)))
            camera.position.add(new Vector3(0, speed * delta, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || (Gdx.input.isKeyPressed(Input.Keys.S)))
            camera.position.add(new Vector3(0, -speed * delta, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isKeyPressed(Input.Keys.A)))
            camera.position.add(new Vector3(-speed * delta, 0, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (Gdx.input.isKeyPressed(Input.Keys.D)))
            camera.position.add(new Vector3(speed * delta, 0, 0));

        for (Entity entity : getInstance().getEntities()) {
            entity.render(app);
        }
    }

    public GameInstance getInstance() {
        return instance;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        camera.zoom = Math.max(0.5f, camera.zoom + amountY / 10f);
        return false;
    }
}
