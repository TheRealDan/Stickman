package dev.therealdan.stickmen.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.main.StickmenApp;

public class Platform {

    private static Texture texture;

    private Vector2 position;
    private float width;
    private float height;
    private Color color;

    public Platform(Vector2 position, float width, float height, Color color) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void render(StickmenApp app) {
        app.batch.setColor(getColor());
        app.batch.draw(getTexture(), getPosition().x - getWidth() / 2f, getPosition().y - getHeight() / 2f, getWidth(), getHeight());
    }

    public boolean contains(Vector2 point) {
        return getPosition().x - getWidth() / 2f < point.x && point.x < getPosition().x + getWidth() + 2f &&
            getPosition().y - getHeight() / 2f < point.y && point.y < getPosition().y + getHeight() + 2f;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public Texture getTexture() {
        if (texture == null) texture = new Texture("blank.png");
        return texture;
    }
}
