package robot;

public enum Direction {
    UP("↑"),
    RIGHT("→"),
    DOWN("↓"),
    LEFT("←");

    final String arrow;

    Direction(String arrow) {
        this.arrow = arrow;
    }

    public String arrow() {
        return arrow;
    }
}
