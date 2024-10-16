package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.main.StickmenApp;

public abstract class Entity {

    private Vector2 position;
    private Vector2 velocity;

    public Entity(Vector2 position) {
        this.position = position;
        this.velocity = new Vector2();
    }

    public void render(StickmenApp app) {
        app.batch.setColor(Color.WHITE);
        app.batch.draw(getTexture(), getPosition().x - getWidth() / 2f, getPosition().y, getWidth(), getHeight());
    }

    public float getWidth() {
        return 100;
    }

    public float getHeight() {
        return getTexture().getHeight() / (getTexture().getWidth() / getWidth());
    }

    public Texture getTexture() {
        return null;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
