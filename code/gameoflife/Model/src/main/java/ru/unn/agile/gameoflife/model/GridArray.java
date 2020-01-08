package ru.unn.agile.gameoflife.model;

public class GridArray implements IGridInput {
    private char[][] arr;
    private int height;
    private int width;
    public GridArray(final char[][] arr, final int height, final int width) {
        this.height = height;
        this.width = width;
        this.arr = arr.clone();
    }

    public char get(final int i, final int j) {
        return arr[i][j];
    }
}
