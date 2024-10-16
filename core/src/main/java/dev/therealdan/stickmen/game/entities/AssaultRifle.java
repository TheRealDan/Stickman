package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class AssaultRifle extends Entity {

    private static Texture texture;

    public AssaultRifle(Vector2 position) {
        super(position);
    }

    @Override
    public Texture getTexture() {
        if (texture == null) texture = new Texture("assault_rifle.png");
        return texture;
    }
}
