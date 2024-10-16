package dev.therealdan.stickmen.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import dev.therealdan.stickmen.game.entities.Blood;
import dev.therealdan.stickmen.game.entities.Entity;
import dev.therealdan.stickmen.game.entities.Player;
import dev.therealdan.stickmen.game.entities.Stickman;
import dev.therealdan.stickmen.game.entities.weapons.Bullet;
import dev.therealdan.stickmen.game.entities.weapons.Pistol;
import dev.therealdan.stickmen.game.entities.weapons.Weapon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GameInstance {

    private Random random = new Random();

    private HashSet<Entity> entities = new HashSet<>();
    private HashSet<Platform> platforms = new HashSet<>();

    public GameInstance() {
        spawnPlatform(new Platform(new Vector2(0, -50), 1000, 50, Color.BLACK));
        spawnEntity(new Stickman(new Vector2()));
    }

    public void tick(float delta) {
        entity:
        for (Entity entity : getEntities()) {
            if (!entity.ignoreGravity()) entity.getVelocity().add(0, -30f * delta);

            if (entity instanceof Stickman && !(entity instanceof Player)) {
                Stickman stickman = (Stickman) entity;
                if (stickman.getEquipped() == null) stickman.setEquipped(new Pistol(new Vector2()));
                stickman.shoot(this);
            }

            if (entity instanceof Blood) {
                Blood blood = (Blood) entity;
                if (System.currentTimeMillis() - blood.getStart() > blood.getLifetime()) {
                    entities.remove(entity);
                }
            } else if (entity instanceof Bullet) {
                Bullet bullet = (Bullet) entity;
                if (entity.getVelocity().len() <= 20 || System.currentTimeMillis() - bullet.getStart() > bullet.getLifetime()) {
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
                        entity.getVelocity().set(0, entity.getVelocity().y * entity.getFriction());
                    continue;
                }
                if (platform.getPosition().x + platform.getWidth() / 2f < entity.getPosition().x - entity.getWidth() / 2f) {
                    if (platform.getPosition().y + platform.getHeight() / 2f < entity.getPosition().y) continue;
                    if (platform.getPosition().y - platform.getHeight() / 2f > entity.getPosition().y + entity.getHeight()) continue;
                    if (platform.getPosition().x + platform.getWidth() / 2f > entity.getPosition().x - entity.getWidth() / 2f + entity.getVelocity().x * 100f * delta)
                        entity.getVelocity().set(0, entity.getVelocity().y * entity.getFriction());
                    continue;
                }

                if (platform.getPosition().y + platform.getHeight() / 2f < entity.getPosition().y) {
                    if (platform.getPosition().y + platform.getHeight() / 2f > entity.getPosition().y + entity.getVelocity().y * 100f * delta) {
                        if (entity instanceof Player) ((Player) entity).setCanJump(true);
                        if (Math.abs(entity.getPosition().y - (platform.getPosition().y + platform.getHeight() / 2f)) > 100)
                            entity.getPosition().set(entity.getPosition().x, entity.getPosition().y - Math.abs(entity.getPosition().y - (platform.getPosition().y + platform.getHeight() / 2f)));
                        entity.getVelocity().set(entity.getVelocity().x * entity.getFriction(), 0);
                    }
                } else if (platform.getPosition().y - platform.getHeight() / 2f > entity.getPosition().y + entity.getHeight()) {
                    if (platform.getPosition().y - platform.getHeight() / 2f < entity.getPosition().y + entity.getHeight() + entity.getVelocity().y * 100f * delta) {
                        entity.getVelocity().set(entity.getVelocity().x * entity.getFriction(), 0);
                    }
                }
            }

            for (Entity each : getEntities()) {
                if (each.equals(entity)) continue;
                if (entity instanceof Stickman) {
                    Stickman stickman = (Stickman) entity;
                    if (stickman.getHealth() <= 0) {
                        entities.remove(stickman);
                        Blood.effect(this, random, stickman.getPosition().cpy().add(0, stickman.getHeight() / 2f), 50, 2 + random.nextInt(6) + random.nextInt(6));
                        continue entity;
                    }

                    if (each instanceof Weapon) {
                        if (stickman.contains(each.getPosition())) {
                            stickman.setEquipped((Weapon) each);
                            entities.remove(each);
                        }
                    } else if (each instanceof Bullet) {
                        Bullet bullet = (Bullet) each;
                        if (!bullet.getOwner().equals(entity) && stickman.contains(bullet.getPosition())) {
                            stickman.setHealth(Math.max(0, stickman.getHealth() - 5));
                            entities.remove(bullet);
                            Blood.effect(this, random, bullet.getPosition().cpy(), 1 + random.nextInt(4), 2 + random.nextInt(6) + random.nextInt(6));
                        }
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
