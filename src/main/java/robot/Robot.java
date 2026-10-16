import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Robot {
    final static Position HOME = new Position(0, 0);

    private final List<Position> travelled = new ArrayList<>();
    private final List<Integer> intersectingSteps = new ArrayList<>();

    private Position currentPosition;
    private Direction currentDirection;

    public void reset() {
        intersectingSteps.clear();
        travelled.clear();
        currentPosition = HOME;
        travelled.add(currentPosition);
        currentDirection = Direction.UP;
    }

    public Robot() {
        this.reset();
    }

    public Robot(Position homePosition) {
        this();
        currentPosition = homePosition;
        travelled.set(0, homePosition);
    }

}
