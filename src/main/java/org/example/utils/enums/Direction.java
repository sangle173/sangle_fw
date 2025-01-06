package org.example.utils.enums;
public enum Direction {
    RIGHT("right"),
    LEFT("left"),
    UP("up"),
    DOWN("down");

    private final String value;

    Direction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    // Optional: Method to get Direction from String
    public static Direction fromString(String direction) {
        for (Direction d : Direction.values()) {
            if (d.value.equalsIgnoreCase(direction)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Invalid direction: " + direction);
    }

    public Direction opposite() {
        switch (this) {
            case RIGHT: return LEFT;
            case LEFT: return RIGHT;
            case UP: return DOWN;
            case DOWN: return UP;
            default: throw new IllegalArgumentException("Unknown direction: " + this);
        }
    }
}
