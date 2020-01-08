package ru.unn.agile.gameoflife.model;

import java.util.Arrays;

public class GameOfLife {
    private char[][] grid;
    private int height;
    private int width;

    private static final int MAX_NEIGHBOURS = 3;
    private static final int NEIGHBOURS_TO_REPRODUCE = 3;
    private static final int MIN_NEIGHBOURS = 2;

    public GameOfLife(final int height, final int width) {
        if ((height <= 0) || (width <= 0)) {
            throw new NegativeArraySizeException("array size is negative");
        } else {
            this.height = height;
            this.width = width;
        }

        grid = new char[this.height][this.width];

        for (char[] row: grid) {
            Arrays.fill(row, '.');
        }
    }

    public GameOfLife(final GameOfLife game) {
        this(game.height, game.width);

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (game.isCellLive(i, j)) {
                    setCell(i, j);
                }
            }
        }

    }

    public GameOfLife(final IGridInput grid, final int height, final int width) {
        this(height, width);

        try {
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    if (grid.get(i, j) == '*') {
                        setCell(i, j);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException  e) {
            throw new IndexOutOfBoundsException("input grid smaller than required");
        }
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public char[][] getGrid() {
        return grid;
    }

    public boolean isPointOnGrid(final int y, final int x) {
        return ((y >= 0) && (y < this.height) && (x >= 0) && (x < this.width));
    }

    public void setCell(final int y, final int x) {
        if (isPointOnGrid(y, x)) {
            grid[y][x] = '*';
        }
    }

    public void deleteCell(final int y, final int x) {
        grid[y][x] = '.';
    }

    public boolean isCellLive(final int y, final int x) {
        if (isPointOnGrid(y, x)) {
            return grid[y][x] == '*';
        } else {
            return false;
        }
    }

    public int getNeighborsNum(final int y, final int x) {
        int neighborsNum = 0;
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if ((i != y) || (j != x)) {
                    neighborsNum += this.isCellLive(i, j) ? 1 : 0;
                }
            }
        }

        return neighborsNum;
    }

    public char[][] makeTurn() {
        GameOfLife tempGame = new GameOfLife(this);

        char[][] resultGrid = new char[height][width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if ((tempGame.getNeighborsNum(i, j) < MIN_NEIGHBOURS)
                 || (tempGame.getNeighborsNum(i, j) > MAX_NEIGHBOURS)) {
                    deleteCell(i, j);
                }
                if (tempGame.getNeighborsNum(i, j) == NEIGHBOURS_TO_REPRODUCE) {
                    setCell(i, j);
                }
                resultGrid[i][j] = grid[i][j];
            }
        }
        return resultGrid;
    }

}
