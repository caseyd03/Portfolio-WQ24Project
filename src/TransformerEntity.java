import processing.core.PImage;

import java.util.List;

public abstract class TransformerEntity implements ActionEntity{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;

    public TransformerEntity(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super();
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }
    abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    }

    public Point getPosition() {
        return position;
    };

    public List<PImage> getImages() {
        return images;
    }

    public PImage getCurrentImage() {
        return this.images.get(this.imageIndex % this.images.size());

    }

    public double getAnimationPeriod() {
        return animationPeriod;
    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
    }

    public String getId() {
        return id;
    }

    public void setPosition(Point pos) {
        this.position = pos;
    }

    public double getActionPeriod() {
        return actionPeriod;
    }

    public int getImageIndex() {
        return imageIndex;
    }

}
