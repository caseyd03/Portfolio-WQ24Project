import processing.core.PImage;

import java.util.List;

public class Stump extends BasicEntity implements Entity, TargetEntity {
    public Stump(String id, Point position, List<PImage> images) {

        super(id, position, images);
    }

    public void decreaseHealth() {}
}
