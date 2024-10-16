package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.main.StickmenApp;

public class Blood extends Entity {

    private static Texture texture;

    private float size;
    private long start = System.currentTimeMillis();

    public Blood(Vector2 position, float size) {
        super(position);
        this.size = size;
    }

    @Override
    public void render(StickmenApp app) {
        app.batch.setColor(Color.RED);
        app.batch.draw(getTexture(), getPosition().x - getWidth() / 2f, getPosition().y, getWidth(), getHeight());
    }

    public long getLifetime() {
        return 1000;
    }

    public long getStart() {
        return start;
    }

    @Override
    public float getFriction() {
        return 0;
    }

    @Override
    public float getWidth() {
        return size * ((float) (getLifetime() - (System.currentTimeMillis() - getStart())) / getLifetime());
    }

    @Override
    public float getHeight() {
        return getWidth();
    }

    @Override
    public Texture getTexture() {
        if (texture == null) texture = new Texture("blank.png");
        return texture;
    }
}
