package ru.unn.agile.polygon.model;

public class Polygon {
    private static final int MINIMAL_NUMBER_OF_VERTICES = 3;
    private final Point[] vertices;
    private final int size;
    private final double area;

    public Polygon(final Point... points) {
        if (points.length < MINIMAL_NUMBER_OF_VERTICES) {
            throw new IllegalArgumentException("A polygon must have at least three vertices");
        }

        this.vertices = points;
        this.size = points.length;
        this.area = this.calculateArea();
    }

    public int getSize() {
        return size;
    }

    public double getArea() {
        return this.area;
    }

    private double calculateArea() {
        double area = 0;

        for (int i = 0; i < this.size - 1; i++) {
            area += vertices[i].getX() * vertices[i + 1].getY();

            area -= vertices[i].getY() * vertices[i + 1].getX();
        }

        area += vertices[this.size - 1].getX() * vertices[0].getY();

        area -= vertices[this.size - 1].getY() * vertices[0].getX();

        area = area / 2;

        return area;
    }
}
