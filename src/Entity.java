import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public interface Entity {
    void nextImage();
    PImage getCurrentImage();
    String log();
    String getId();
    Point getPosition();
    void setPosition(Point pos);


}
