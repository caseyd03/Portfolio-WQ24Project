import processing.core.PImage;

import java.util.List;

public class House extends BasicEntity implements Entity, TargetEntity {
    public House(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }

    public void decreaseHealth() {}
}
