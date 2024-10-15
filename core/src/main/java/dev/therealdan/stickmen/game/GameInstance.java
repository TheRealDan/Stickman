package dev.therealdan.stickmen.game;

import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.entities.Entity;
import dev.therealdan.stickmen.game.entities.Stickman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameInstance {

    private HashSet<Entity> entities = new HashSet<>();

    public GameInstance() {
        entities.add(new Stickman(new Vector2()));
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }
}
