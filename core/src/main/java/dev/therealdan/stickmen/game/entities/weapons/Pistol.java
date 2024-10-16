package dev.therealdan.stickmen.game.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Pistol extends Weapon {

    private static Texture texture;

    public Pistol(Vector2 position) {
        super(position);
    }

    @Override
    public float getWidth() {
        return 50;
    }

    @Override
    public float getHeight() {
        return 25;
    }

    @Override
    public Texture getTexture() {
        if (texture == null) texture = new Texture("pistol.png");
        return texture;
    }
}
