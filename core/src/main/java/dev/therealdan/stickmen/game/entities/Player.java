package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.math.Vector2;

public class Player extends Stickman {

    private int index;

    private Vector2 movement = new Vector2();
    private boolean canJump = true;

    public Player(Controller controller, Vector2 position) {
        super(position);
        index = controller.getPlayerIndex();
    }

    public void jump() {
        getVelocity().set(getVelocity().x, 15f);
        setCanJump(false);
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
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

    public boolean canJump() {
        return canJump;
    }

}
