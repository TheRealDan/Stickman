package dev.therealdan.stickmen.game.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.entities.Pickup;

public abstract class Weapon extends Pickup {

    private long lastShot = System.currentTimeMillis();

    public Weapon(Vector2 position) {
        super(position);
    }

    public void setLastShot() {
        this.lastShot = System.currentTimeMillis();
    }

    public long getReload() {
        return 1000;
    }

    public long getLastShot() {
        return lastShot;
    }
}
