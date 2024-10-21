package dev.therealdan.stickmen.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.GameInstance;

public class Player extends Stickman {

    private int index;

    private Vector2 movement = new Vector2();
    private boolean canJump = true;

    public Player(Controller controller, Vector2 position) {
        this(controller.getPlayerIndex(), position);
    }

    public Player(int index, Vector2 position) {
        super(position);
        this.index = index;
    }

    public void controls(Controller controller, GameInstance instance) {
        if (canJump())
            if ((controller == null && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) || (controller != null && (controller.getButton(9) || controller.getButton(0))))
                jump();

        if (getEquipped() != null)
            if ((controller == null && (Gdx.input.isButtonPressed(Input.Buttons.LEFT))) || (controller != null && (controller.getAxis(5) > 0.2f)))
                shoot(instance);

        if (controller == null) {
            float x = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= 1;
            if (Gdx.input.isKeyPressed(Input.Keys.D)) x += 1;
            getMovement().set(x, 0);
        }
    }

    public void jump() {
        getVelocity().set(getVelocity().x, 20f);
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

    public Controller getController() {
        for (Controller controller : Controllers.getControllers())
            if (controller.getPlayerIndex() == getIndex())
                return controller;
        return null;
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
