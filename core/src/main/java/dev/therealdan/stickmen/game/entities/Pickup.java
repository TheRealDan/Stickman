package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.main.StickmenApp;

public abstract class Pickup extends Entity {

    public Pickup(Vector2 position) {
        super(position);
    }

    @Override
    public void render(StickmenApp app) {
        float height = (float) Math.abs((System.currentTimeMillis() % 2000) - 1000) / 1000;
        Vector2 position = getPosition().cpy().add(0, getHeight() + getHeight() * height);

        app.batch.setColor(Color.WHITE);
        app.batch.draw(getTexture(), position.x - getWidth() / 2f, position.y, getWidth(), getHeight());
    }
}
