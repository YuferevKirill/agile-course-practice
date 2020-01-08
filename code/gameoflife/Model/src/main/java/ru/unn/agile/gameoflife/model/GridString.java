package ru.unn.agile.gameoflife.model;

public class GridString implements IGridInput {
    private String str;
    private int height;
    private int width;
    public GridString(final String str, final int height, final int width) {
        this.height = height;
        this.width = width;
        this.str = str;
    }

    public char get(final int i, final int j) {
        return str.charAt(i * this.width + j);
    }
}
