package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.math.Vector2;

public class Player extends Stickman {

    private int index;

    private Vector2 movement = new Vector2();

    public Player(Controller controller, Vector2 position) {
        super(position);
        index = controller.getPlayerIndex();
    }

    public void jump() {
        getVelocity().set(getVelocity().x, 8f);
    }

    public void axisMoved(Controller controller, int i, float v) {
        switch (i) {
            case 0:
                getMovement().set(v > 0 ? v > 0.2f ? v : 0 : v < -0.2f ? v : 0, getMovement().y);
        }
    }

    public int getIndex() {
        return index;
    }

    public Vector2 getMovement() {
        return movement;
    }
}
