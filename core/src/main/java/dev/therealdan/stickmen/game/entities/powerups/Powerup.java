package dev.therealdan.stickmen.game.entities.powerups;

import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.entities.Pickup;
import dev.therealdan.stickmen.game.entities.Stickman;

public abstract class Powerup extends Pickup {

    public Powerup(Vector2 position) {
        super(position);
    }

    public void collect(Stickman stickman) {
    }

    @Override
    public float getWidth() {
        return 50;
    }

    @Override
    public float getHeight() {
        return getWidth();
    }
}
