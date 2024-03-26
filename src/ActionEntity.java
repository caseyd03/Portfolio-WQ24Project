import processing.core.PImage;

public interface ActionEntity extends Entity {
    void nextImage();
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    PImage getCurrentImage();
    double getAnimationPeriod();
    String log();
    String getId();
    Point getPosition();
    void setPosition(Point pos);
}
