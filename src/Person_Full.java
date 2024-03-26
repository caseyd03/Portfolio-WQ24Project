import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Person_Full extends TransformerEntity implements Entity {
    private final int resourceLimit;

    public Person_Full(String id, Point position, List<PImage> images, int resourceLimit, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<TargetEntity> fullTarget = world.findNearest(super.getPosition(), new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && moveTo(world, fullTarget.get(), scheduler)) {
            transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), super.getActionPeriod());
        }
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), super.getActionPeriod());
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this, 0), getAnimationPeriod());

    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Person_Searching dude = Factory.createPersonSearching(super.getId(), super.getPosition(), super.getActionPeriod(), super.getAnimationPeriod(), resourceLimit, super.getImages());

        world.removeEntity(scheduler, this);

        world.tryAddEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
        return true;
    }

    public boolean corrupt(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Skeleton skeleton = Factory.createSkeleton(super.getId(), super.getPosition(), super.getActionPeriod(), super.getAnimationPeriod(), imageStore.getImageList("skeleton"));

        world.removeEntity(scheduler, this);
        scheduler.unscheduleAllEvents(this);

        world.tryAddEntity(skeleton);
        skeleton.scheduleActions(scheduler, world, imageStore);

        return true;
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (super.getPosition().adjacent(target.getPosition())) {
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!super.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - super.getPosition().x);
        Point newPos = new Point(super.getPosition().x + horiz, super.getPosition().y);

        if (horiz == 0 || world.getOccupant(newPos).isPresent() && world.getOccupant(newPos).get().getClass() != Stump.class) {
            int vert = Integer.signum(destPos.y - super.getPosition().y);
            newPos = new Point(super.getPosition().x, super.getPosition().y + vert);

            if (vert == 0 || world.getOccupant(newPos).isPresent() && world.getOccupant(newPos).get().getClass() != Stump.class) {
                newPos = super.getPosition();
            }
        }

        return newPos;
    }


}
