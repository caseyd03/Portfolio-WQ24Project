import processing.core.PImage;

import java.util.List;

public class Sapling extends TransformerEntity implements Entity, ActionEntity, TargetEntity {
    private int health;
    private final int healthLimit;

    public Sapling(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        health++;
        if (!transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), super.getActionPeriod());
        }
    }


    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), super.getActionPeriod());
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this, 0), getAnimationPeriod());

    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (health <= 0) {
            Entity stump = Factory.createStump(WorldLoader.STUMP_KEY + "_" + super.getId(), super.getPosition(), imageStore.getImageList(WorldLoader.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.tryAddEntity(stump);

            return true;
        } else if (health >= healthLimit) {
            Tree tree = Factory.createTreeWithDefaults(WorldLoader.TREE_KEY + "_" + super.getId(), super.getPosition(), imageStore.getImageList(WorldLoader.TREE_KEY));

            world.removeEntity(scheduler, this);

            world.tryAddEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public int getHealth() {
        return this.health;
    }
    public void decreaseHealth() {
        this.health--;
    }
}
