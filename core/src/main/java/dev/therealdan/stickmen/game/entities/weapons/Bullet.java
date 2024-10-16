package dev.therealdan.stickmen.game.entities.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.entities.Entity;
import dev.therealdan.stickmen.main.StickmenApp;

public class Bullet extends Entity {

    private static Texture texture;

    private long start = System.currentTimeMillis();

    public Bullet(Vector2 position) {
        super(position);
    }

    @Override
    public void render(StickmenApp app) {
        app.batch.setColor(Color.BLACK);
        app.batch.draw(getTexture(), getPosition().x - getWidth() / 2f, getPosition().y, getWidth(), getHeight());
    }

    public long getStart() {
        return start;
    }

    @Override
    public boolean ignoreGravity() {
        return true;
    }

    @Override
    public float getWidth() {
        return 10;
    }

    @Override
    public float getHeight() {
        return 10;
    }

    @Override
    public Texture getTexture() {
        if (texture == null) texture = new Texture("blank.png");
        return texture;
    }
}
