package dev.therealdan.stickmen.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.therealdan.stickmen.screens.GameScreen;

public class StickmenApp extends Game {

    public FontManager font;

    public SpriteBatch batch;

    @Override
    public void create() {
        font = new FontManager();
        batch = new SpriteBatch();

        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        batch.dispose();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);
        batch.begin();
        super.render();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        batch.dispose();
        batch = new SpriteBatch();
        font.scale = Gdx.graphics.getWidth() / 1000f;
        super.resize(width, height);
    }
}
