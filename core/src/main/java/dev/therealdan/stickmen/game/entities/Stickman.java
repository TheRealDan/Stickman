package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.GameInstance;
import dev.therealdan.stickmen.game.entities.weapons.Bullet;
import dev.therealdan.stickmen.game.entities.weapons.Weapon;
import dev.therealdan.stickmen.main.StickmenApp;

public class Stickman extends Entity {

    private static Texture texture;

    private Vector2 direction = new Vector2(0, 1);
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

        if (getEquipped() != null) {
            Weapon weapon = getEquipped();
            app.batch.setColor(Color.BLACK);
            app.batch.draw(new TextureRegion(weapon.getTexture()), getPosition().x - weapon.getWidth() / 2f + (getDirection().y > 0 ? getWidth() / 2f : -getWidth() / 2f), getPosition().y - weapon.getHeight() / 2f + getHeight() / 2f, weapon.getWidth() / 2f, weapon.getHeight() / 2f, weapon.getWidth(), weapon.getHeight(), 1, getDirection().y > 0 ? -1 : 1, (float) (Math.atan2(getDirection().y, getDirection().x) * 180 / Math.PI + 450));
        }

        if (getHealth() < getMaxHealth()) {
            app.batch.setColor(Color.RED);
            app.batch.draw(app.texture, getPosition().x - getWidth() / 2f, getPosition().y + getHeight() + 10, getWidth(), 10);
            app.batch.setColor(Color.GREEN);
            app.batch.draw(app.texture, getPosition().x - getWidth() / 2f, getPosition().y + getHeight() + 10, getWidth() * getHealth() / getMaxHealth(), 10);
        }
    }

    public void shoot(GameInstance instance) {
        Weapon weapon = getEquipped();
        if (System.currentTimeMillis() - weapon.getLastShot() < weapon.getReload()) return;
        weapon.setLastShot();

        Bullet bullet = new Bullet(this, new Vector2(getPosition().x + (getDirection().y > 0 ? getWidth() / 2f : -getWidth() / 2f), getPosition().y + getHeight() / 2f));
        bullet.getVelocity().set(getDirection().y, -getDirection().x).nor().scl(25);
        instance.spawnEntity(bullet);
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

    public Vector2 getDirection() {
        return direction;
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
