import processing.core.PImage;

import java.util.List;

public abstract class BasicEntity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public BasicEntity(String id, Point position, List<PImage> images) {
        super();
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    public PImage getCurrentImage() {
        return this.images.get(this.imageIndex % this.images.size());

    }

    public void nextImage() {
        imageIndex = imageIndex + 1;
    }
    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }
    public void setPosition(Point pos) {
        this.position = pos;
    }


}
