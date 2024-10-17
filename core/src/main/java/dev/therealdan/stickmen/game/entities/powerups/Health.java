package dev.therealdan.stickmen.game.entities.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.entities.Stickman;

public class Health extends Powerup {

    private static Texture texture;

    public Health(Vector2 position) {
        super(position);
    }

    @Override
    public void collect(Stickman stickman) {
        stickman.setHealth(Math.min(stickman.getMaxHealth(), stickman.getHealth() + 20));
    }

    @Override
    public Texture getTexture() {
        if (texture == null) texture = new Texture("health.png");
        return texture;
    }
}
