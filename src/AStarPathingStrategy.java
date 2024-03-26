import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class AStarPathingStrategy
        implements PathingStrategy
{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        PriorityQueue<ANode> open = new PriorityQueue<>();
        open.add(new ANode(start, 0));
        System.out.println("open list: " + open);
        HashMap<Point, Point> track = new HashMap<>();
        track.put(start, null);
        LinkedList<Point> closed = new LinkedList<>();
        while (!open.isEmpty()) {
            ANode current = open.poll();
            System.out.println("enter loop");
            List<Point> validNeighbors = potentialNeighbors.apply(current.point).filter(canPassThrough).collect(Collectors.toList());
            System.out.println("neighbors: " + validNeighbors);
            for (Point neighbor : validNeighbors) {
                if (closed.contains(neighbor)) {
                    validNeighbors.remove(neighbor);
                } else {
                    ANode neighborNode = new ANode(neighbor, current.gval + 1);
                    System.out.println("neighbor: " + neighborNode.point);
                    if (!open.contains(neighborNode)) {
                        open.add(neighborNode);
                        track.put(neighbor, current.point);
                    } else if (neighborNode.gval > current.gval + 1) {
                        open.remove(neighborNode);
                        neighborNode.gval = current.gval + 1;
                        open.add(neighborNode);
                        track.put(neighbor, current.point);
                    }
                    System.out.println("open list: " + open);
                    System.out.println("track: " + track);
                }
            }

            closed.add(current.point);
            ANode smallest = open.peek();
            for (ANode node : open) {
                if (smallest.fValue(end) > node.fValue(end)) {
                    smallest = node;
                    System.out.println("smallest f value: " + smallest);
                    track.put(smallest.point, current.point);
                }
            }

            current = smallest;
            List<Point> path = new LinkedList<>();
            if (current.point.equals(end)) {
                Point prevPoint = track.get(current.point);
                System.out.println("track: " + track);
                while (prevPoint != null) {
                    path.add(current.point);
                    System.out.println("path: " + path);
                    current = new ANode(prevPoint, 0);
                    prevPoint = track.get(current.point);
                }
                Collections.reverse(path);
                return path;
            }
        }

        LinkedList<Point> emptyPath = new LinkedList<>();

        return emptyPath;
    }
    public static class ANode implements Comparable<ANode> {
        private Point point;
        private double gval;

        public ANode(Point point, double gval) {
            this.point = point;
            this.gval = gval;
        }

        public double hValue(Point end) {
            return Math.sqrt(Math.pow(end.x - point.x, 2) + Math.pow(end.y - point.y, 2));
        }

        public double fValue(Point end) {
            return this.gval + this.hValue(end);
        }

        public Point getPoint() {
            return this.point;
        }

        public double getGval() {
            return this.gval;
        }

        @Override
        public int compareTo(ANode o) {
            return Double.compare(this.fValue(o.point), o.fValue(o.point));
        }
    }
}

