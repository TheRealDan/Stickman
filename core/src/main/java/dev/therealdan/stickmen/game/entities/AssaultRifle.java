package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class AssaultRifle extends Entity {

    private static Texture texture;

    private long reload = 50;
    private long lastShot = System.currentTimeMillis();

    public AssaultRifle(Vector2 position) {
        super(position);
    }

    public void setLastShot() {
        this.lastShot = System.currentTimeMillis();
    }

    public long getReload() {
        return reload;
    }

    public long getLastShot() {
        return lastShot;
    }

    @Override
    public Texture getTexture() {
        if (texture == null) texture = new Texture("assault_rifle.png");
        return texture;
    }
}
