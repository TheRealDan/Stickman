package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Stickman extends Entity {

    private static Texture texture;

    public Stickman(Vector2 position) {
        super(position);
    }

    @Override
    public Texture getTexture() {
        if (texture == null) texture = new Texture("stickman.png");
        return texture;
    }
}
