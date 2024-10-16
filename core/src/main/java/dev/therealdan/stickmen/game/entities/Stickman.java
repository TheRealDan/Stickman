package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.entities.weapons.Weapon;
import dev.therealdan.stickmen.main.StickmenApp;

public class Stickman extends Entity {

    private static Texture texture;

    private float maxHealth;
    private float health;

    private Weapon equipped;

    public Stickman(Vector2 position) {
        super(position);
        setMaxHealth(100);
        setHealth(getMaxHealth());
    }

    @Override
    public void render(StickmenApp app) {
        super.render(app);

        if (getHealth() < getMaxHealth()) {
            app.batch.setColor(Color.RED);
            app.batch.draw(app.texture, getPosition().x - getWidth() / 2f, getPosition().y + getHeight() + 10, getWidth(), 10);
            app.batch.setColor(Color.GREEN);
            app.batch.draw(app.texture, getPosition().x - getWidth() / 2f, getPosition().y + getHeight() + 10, getWidth() * getHealth() / getMaxHealth(), 10);
        }
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void setEquipped(Weapon weapon) {
        this.equipped = weapon;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getHealth() {
        return health;
    }

    public Weapon getEquipped() {
        return equipped;
    }

    @Override
    public Texture getTexture() {
        if (texture == null) texture = new Texture("stickman.png");
        return texture;
    }
}
