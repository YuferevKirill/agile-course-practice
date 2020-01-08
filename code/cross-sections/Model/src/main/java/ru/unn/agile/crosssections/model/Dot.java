package ru.unn.agile.crosssections.model;

import java.util.Objects;

public class Dot {

    private int x;
    private int y;

    public Dot(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Dot(final String x, final String y) {
        this(Integer.parseInt(x), Integer.parseInt(y));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dot dot = (Dot) o;
        return x == dot.x
                && y == dot.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
