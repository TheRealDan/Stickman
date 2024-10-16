package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.GameInstance;
import dev.therealdan.stickmen.main.StickmenApp;

public class Player extends Stickman {

    private int index;

    private Vector2 movement = new Vector2();
    private Vector2 direction = new Vector2();
    private boolean canJump = true;

    private Entity equipped;

    public Player(Controller controller, Vector2 position) {
        super(position);
        index = controller.getPlayerIndex();
    }

    @Override
    public void render(StickmenApp app) {
        super.render(app);

        if (getEquipped() != null && getEquipped() instanceof AssaultRifle) {
            AssaultRifle assaultRifle = (AssaultRifle) getEquipped();
            app.batch.setColor(Color.BLACK);
            app.batch.draw(new TextureRegion(assaultRifle.getTexture()), getPosition().x - assaultRifle.getWidth() / 2f + (getDirection().y > 0 ? getWidth() / 2f : -getWidth() / 2f), getPosition().y - assaultRifle.getHeight() / 2f + getHeight() / 2f, assaultRifle.getWidth() / 2f, assaultRifle.getHeight() / 2f, assaultRifle.getWidth(), assaultRifle.getHeight(), 1, getDirection().y > 0 ? -1 : 1, (float) (Math.atan2(getDirection().y, getDirection().x) * 180 / Math.PI + 450));
        }
    }

    public void jump() {
        getVelocity().set(getVelocity().x, 15f);
        setCanJump(false);
    }

    public void shoot(GameInstance instance) {
        if (!(getEquipped() instanceof AssaultRifle)) return;
        AssaultRifle assaultRifle = (AssaultRifle) getEquipped();
        if (System.currentTimeMillis() - assaultRifle.getLastShot() < assaultRifle.getReload()) return;
        assaultRifle.setLastShot();

        Bullet bullet = new Bullet(new Vector2(getPosition().x + (getDirection().y > 0 ? getWidth() / 2f : -getWidth() / 2f), getPosition().y + getHeight() / 2f));
        bullet.getVelocity().set(getDirection().y, -getDirection().x).nor().scl(25);
        instance.spawnEntity(bullet);
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public void setEquipped(Entity equipped) {
        this.equipped = equipped;
    }

    public void axisMoved(Controller controller, int i, float v) {
        switch (i) {
            case 0:
                getMovement().set(v > 0 ? v > 0.2f ? v : 0 : v < -0.2f ? v : 0, getMovement().y);
                break;
            case 2:
                if (Math.abs(getDirection().x) + Math.abs(v) > 0.5)
                    getDirection().set(getDirection().x, v);
                break;
            case 3:
                if (Math.abs(getDirection().y) + Math.abs(v) > 0.5)
                    getDirection().set(v, getDirection().y);
                break;
        }
    }

    public int getIndex() {
        return index;
    }

    public Vector2 getMovement() {
        return movement;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public boolean canJump() {
        return canJump;
    }

    public Entity getEquipped() {
        return equipped;
    }
}
