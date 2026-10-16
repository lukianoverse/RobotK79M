package robot;

import java.util.ArrayList;
import java.util.List;

import static robot.Direction.UP;

public class Robot {
    final static Position HOME = new Position(0, 0);

    private final List<Position> travelledHistory = new ArrayList<>();
    private final List<Character> commandHistory = new ArrayList<>();
    private final List<Integer> intersectingSteps = new ArrayList<>();

    private Position currentPosition;
    private Direction currentDirection;

    private void init(Position homePosition, Direction initialDirection) {
        currentPosition = homePosition;
        travelledHistory.add(currentPosition);
        currentDirection = initialDirection;
    }

    private void init() {
        init(HOME, UP);
    }

    public Robot() {
        this.init();
    }

    public Robot(Position homePosition) {
        this.init(homePosition, UP);
    }

    public Robot(Position homePosition, Direction initialDirection) {
        this.init(homePosition, initialDirection);
    }

    public void reset(Position homePosition, Direction initialDirection) {
        commandHistory.clear();
        intersectingSteps.clear();
        travelledHistory.clear();
        IO.println("Память очищена");
        init(homePosition, initialDirection);
        IO.println("Положение: " + currentPosition.toString());
    }

    public void reset() {
        reset(HOME, UP);
    }


    private void makeAStep(Direction direction) {
        Position destination;
        switch (direction) {
            case UP -> destination = currentPosition.getUp();
            case RIGHT -> destination = currentPosition.getRight();
            case DOWN -> destination = currentPosition.getDown();
            case LEFT -> destination = currentPosition.getLeft();
            default -> destination = currentPosition;
        }
        if (travelledHistory.contains(destination))
            intersectingSteps.add(travelledHistory.size());
        currentPosition = destination;
        travelledHistory.add(currentPosition);
    }

    private void makeAStep() {
        makeAStep(currentDirection);
    }

    private void turnLeft() {
        currentDirection = Direction.values()[(currentDirection.ordinal() + 3) % 4];
    }

    private void turnRight() {
        currentDirection = Direction.values()[(currentDirection.ordinal() + 1) % 4];
    }

    public void runACommand(char command) {
        switch (Character.toUpperCase(command)) {
            case 'L' -> turnLeft();
            case 'S' -> makeAStep();
            case 'R' -> turnRight();
            case 'Z' -> reset();
            default -> {
                IO.println("неизвестная команда " + command);
                return;
            }
        }
        commandHistory.add(command);
    }

    public void runCommandSequence(CharSequence commands) {
        commands.chars()
                .filter(c -> !Character.isWhitespace(c))
                .map(Character::toUpperCase)
                .forEach(codePoint -> runACommand((char) codePoint));
    }

    public String travelReport() {
        StringBuilder report = new StringBuilder("Посещены следующие клетки:");
        for (int i = 0; i < travelledHistory.size(); i++)
            report.append("\n\t%2d. %s"
                    .formatted(i, travelledHistory.get(i)));
        return report.toString();
    }

    public String returnsReport() {
        if (intersectingSteps.isEmpty())
            return "Повторных посещений клеток не было.";
        StringBuilder sb = new StringBuilder("Повторные прохождения были на шагах: ");
        for (int intersectionIndex : intersectingSteps)
            sb.append(
                    "\n\t%2d (%s)".formatted(
                            intersectionIndex,
                            travelledHistory.get(intersectionIndex))
            );
        return sb.toString();
    }

    public String commandsExecutedReport() {
        if (commandHistory.isEmpty())
            return "Ни одной команды не выполнено.";
        StringBuilder sb = new StringBuilder("Выполнены команды: ");
        for (char c : commandHistory) sb.append(c);
        return sb.toString();
    }

    public String getPosition() {
        return currentPosition.toString();
    }

    public String getArrow() {
        return currentDirection.arrow();
    }

}
