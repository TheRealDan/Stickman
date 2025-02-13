package dev.therealdan.stickmen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.therealdan.stickmen.game.Platform;
import dev.therealdan.stickmen.game.entities.Entity;
import dev.therealdan.stickmen.main.StickmenApp;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScreen implements Screen, InputProcessor, ControllerListener {

    protected StickmenApp app;

    protected Viewport viewport;
    protected OrthographicCamera camera;

    public BaseScreen(StickmenApp app) {
        this.app = app;
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        Controllers.addListener(this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float v) {
        camera.update();
        app.batch.setProjectionMatrix(camera.combined);

        List<String> info = new ArrayList<>();
        info.add(Gdx.graphics.getFramesPerSecond() + " FPS");

        float y = 20;
        for (String line : info) {
            app.font.draw(app.batch, camera, line, getX(20), getY(y), 12, Color.BLACK);
            y += app.font.getHeight(app.batch, camera, line, 12);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void connected(Controller controller) {
    }

    @Override
    public void disconnected(Controller controller) {
    }

    public boolean isOnScreen(Entity entity) {
        return entity.within(getPosition(0, 0), getPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    public boolean isOnScreen(Platform platform) {
        return platform.within(getPosition(0, 0), getPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    public boolean containsMouse(float x, float y, float width, float height) {
        float mx = Gdx.input.getX() - Gdx.graphics.getWidth() / 2f;
        float my = -Gdx.input.getY() + Gdx.graphics.getHeight() / 2f;
        return x < mx && mx < x + width
            && y < my && my < y + height;
    }

    public Vector2 getMousePosition() {
        return getPosition(Gdx.input.getX(), Gdx.input.getY());
    }

    public Vector2 getPosition(int screenX, int screenY) {
        Vector3 vec = camera.unproject(new Vector3(screenX, screenY, 0));
        return new Vector2(vec.x, vec.y);
    }

    public float getX(float x) {
        return camera.unproject(new Vector3(x, 0, 0)).x;
    }

    public float getY(float y) {
        return camera.unproject(new Vector3(0, y, 0)).y;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean buttonDown(Controller controller, int i) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int i) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int i, float v) {
        return false;
    }
}
