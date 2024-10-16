package dev.therealdan.stickmen.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.entities.AssaultRifle;
import dev.therealdan.stickmen.game.entities.Bullet;
import dev.therealdan.stickmen.game.entities.Entity;
import dev.therealdan.stickmen.game.entities.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameInstance {

    private HashSet<Entity> entities = new HashSet<>();
    private HashSet<Platform> platforms = new HashSet<>();

    public GameInstance() {
        spawnPlatform(new Platform(new Vector2(0, -50), 500, 50, Color.BLACK));
    }

    public void tick(float delta) {
        for (Entity entity : getEntities()) {
            if (!entity.ignoreGravity()) entity.getVelocity().add(0, -30f * delta);

            if (entity instanceof Bullet) {
                if (entity.getVelocity().len() == 0 || System.currentTimeMillis() - ((Bullet) entity).getStart() > 10000) {
                    entities.remove(entity);
                }
            } else if (entity instanceof Player) {
                Player player = (Player) entity;
                player.getVelocity().set(player.getMovement().x * 600f * delta, player.getVelocity().y);
            }

            for (Platform platform : getPlatforms()) {
                if (platform.getPosition().x - platform.getWidth() / 2f > entity.getPosition().x + entity.getWidth() / 2f) {
                    if (platform.getPosition().y + platform.getHeight() / 2f < entity.getPosition().y) continue;
                    if (platform.getPosition().y - platform.getHeight() / 2f > entity.getPosition().y + entity.getHeight()) continue;
                    if (platform.getPosition().x - platform.getWidth() / 2f < entity.getPosition().x + entity.getWidth() / 2f + entity.getVelocity().x * 100f * delta)
                        entity.getVelocity().set(0, entity.getVelocity().y);
                    continue;
                }
                if (platform.getPosition().x + platform.getWidth() / 2f < entity.getPosition().x - entity.getWidth() / 2f) {
                    if (platform.getPosition().y + platform.getHeight() / 2f < entity.getPosition().y) continue;
                    if (platform.getPosition().y - platform.getHeight() / 2f > entity.getPosition().y + entity.getHeight()) continue;
                    if (platform.getPosition().x + platform.getWidth() / 2f > entity.getPosition().x - entity.getWidth() / 2f + entity.getVelocity().x * 100f * delta)
                        entity.getVelocity().set(0, entity.getVelocity().y);
                    continue;
                }

                if (platform.getPosition().y + platform.getHeight() / 2f < entity.getPosition().y) {
                    if (platform.getPosition().y + platform.getHeight() / 2f > entity.getPosition().y + entity.getVelocity().y * 100f * delta) {
                        if (entity instanceof Player) ((Player) entity).setCanJump(true);
                        if (Math.abs(entity.getPosition().y - (platform.getPosition().y + platform.getHeight() / 2f)) > 100)
                            entity.getPosition().set(entity.getPosition().x, entity.getPosition().y - Math.abs(entity.getPosition().y - (platform.getPosition().y + platform.getHeight() / 2f)));
                        entity.getVelocity().set(entity.getVelocity().x, 0);
                    }
                } else if (platform.getPosition().y - platform.getHeight() / 2f > entity.getPosition().y + entity.getHeight()) {
                    if (platform.getPosition().y - platform.getHeight() / 2f < entity.getPosition().y + entity.getHeight() + entity.getVelocity().y * 100f * delta) {
                        entity.getVelocity().set(entity.getVelocity().x, 0);
                    }
                }
            }

            for (Entity each : getEntities()) {
                if (each.equals(entity)) continue;
                if (each instanceof AssaultRifle && entity instanceof Player) {
                    if (entity.contains(each.getPosition())) {
                        ((Player) entity).setEquipped(each);
                        entities.remove(each);
                    }
                }
            }

            entity.getPosition().add(entity.getVelocity().x * 100f * delta, entity.getVelocity().y * 100f * delta);
        }
    }

    public void spawnEntity(Entity entity) {
        entities.add(entity);
    }

    public void spawnPlatform(Platform platform) {
        platforms.add(platform);
    }

    public Player getPlayer(Controller controller) {
        for (Entity entity : getEntities())
            if (entity instanceof Player)
                if (controller.getPlayerIndex() == ((Player) entity).getIndex())
                    return (Player) entity;
        return null;
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public List<Platform> getPlatforms() {
        return new ArrayList<>(platforms);
    }
}
