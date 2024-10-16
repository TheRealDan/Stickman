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

    public boolean ignoreGravity() {
        return false;
    }

    public boolean contains(Vector2 point) {
        return getPosition().x - getWidth() / 2f < point.x && point.x < getPosition().x + getWidth() + 2f &&
            getPosition().y - getHeight() / 2f < point.y && point.y < getPosition().y + getHeight() + 2f;
    }

    public boolean within(Vector2 start, Vector2 end) {
        return Math.min(start.x, end.x) < getPosition().x && getPosition().x < Math.max(start.x, end.x)
            && Math.min(start.y, end.y) < getPosition().y && getPosition().y < Math.max(start.y, end.y);
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
