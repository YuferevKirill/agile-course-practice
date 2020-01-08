package ru.unn.agile.gameoflife.Model;

import org.junit.Test;
import ru.unn.agile.gameoflife.model.GameOfLife;
import ru.unn.agile.gameoflife.model.GridArray;
import ru.unn.agile.gameoflife.model.GridString;

import static org.junit.Assert.*;


public class GameOfLifeTest {
    @Test
    public void canCreateGrid() {
        GameOfLife g = new GameOfLife(3, 3);

        assertNotNull(g.isCellLive(1, 1));
    }

    @Test
    public void heightIsCorrect() {
        GameOfLife g = new GameOfLife(3, 4);

        int gameHeight = g.getHeight();

        assertEquals(3, gameHeight);
    }

    @Test
    public void widthIsCorrect() {
        GameOfLife g = new GameOfLife(3, 4);

        int gameWidth = g.getWidth();

        assertEquals(4, gameWidth);
    }

    @Test
    public void canSetCell() {
        GameOfLife g = new GameOfLife(3, 3);
        g.setCell(1, 1);

        assertTrue(g.isCellLive(1, 1));
    }

    @Test
    public void allStartCellsAreDead() {
        int size = 4;
        GameOfLife g = new GameOfLife(size, size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertFalse(g.isCellLive(1, 1));
            }
        }
    }

    @Test
    public void canDeleteCell() {
        GameOfLife g = new GameOfLife(3, 3);
        g.setCell(1, 1);

        g.deleteCell(1, 1);
        assertFalse(g.isCellLive(1, 1));
    }

    @Test
    public void canMakeTurn() {
        GameOfLife g = new GameOfLife(3, 3);

        g.makeTurn();

        assertFalse(g.isCellLive(1, 1));
    }

    @Test
    public void neighborsNumIsCorrect() {
        GameOfLife g = new GameOfLife(3, 3);
        g.setCell(0, 1);
        g.setCell(1, 0);

        var neighboursNum = g.getNeighborsNum(1, 1);

        assertEquals(2, neighboursNum);
    }

    @Test
    public void neighborsNumIsCorrectOnLeftBorder() {
        GameOfLife g = new GameOfLife(3, 3);
        g.setCell(0, 1);
        g.setCell(1, 0);

        var neighboursNum = g.getNeighborsNum(0, 0);

        assertEquals(2, neighboursNum);
    }

    @Test
    public void neighborsNumIsCorrectOnRightBorder() {
        GameOfLife g = new GameOfLife(3, 3);
        g.setCell(1, 1);
        g.setCell(1, 2);

        var neighboursNum = g.getNeighborsNum(2, 2);

        assertEquals(2, neighboursNum);
    }

    @Test
    public void canGetCellBeyondBorder() {
        GameOfLife g = new GameOfLife(3, 3);

        assertFalse(g.isCellLive(4, 4));
    }

    @Test
    public void canSetCellBeyondBorder() {
        GameOfLife g = new GameOfLife(3, 3);
        g.setCell(4, 4);

        assertFalse(g.isCellLive(4, 4));
    }

    @Test(expected = NegativeArraySizeException.class)
    public void throwsOnNegativeHeight() {
        GameOfLife g = new GameOfLife(-3, 5);
    }

    @Test(expected = NegativeArraySizeException.class)
    public void throwsOnNegativeWidth() {
        GameOfLife g = new GameOfLife(5, -3);
    }

    @Test
    public void canCreateFromString() {
        int height = 3;
        int width = 3;
        GridString grid = new GridString(
                "..."
              + ".*."
              + "...", height, width);
        GameOfLife g = new GameOfLife(grid, height, width);

        assertTrue(g.isCellLive(1, 1));
    }

    @Test
    public void oneLivingCellDies() {
        GameOfLife g = new GameOfLife(3, 3);
        g.setCell(1, 1);

        g.makeTurn();

        assertFalse(g.isCellLive(1, 1));
    }

    @Test
    public void cellWithTwoNeighboursLive() {
        int height = 3;
        int width = 3;
        GridString grid = new GridString(
                ".*."
              + "**."
              + "...", height, width);
        GameOfLife g = new GameOfLife(grid, height, width);

        g.makeTurn();

        assertTrue(g.isCellLive(1, 1));
    }

    @Test
    public void cellWithThreeNeighboursLive() {
        int height = 3;
        int width = 3;
        GridString grid = new GridString(
                "**."
              + "**."
              + "...", height, width);
        GameOfLife g = new GameOfLife(grid, height, width);

        g.makeTurn();

        assertTrue(g.isCellLive(0, 0));
    }

    @Test
    public void cellWithThreeNeighboursRevive() {
        int height = 3;
        int width = 3;
        GridString grid = new GridString(
                "**."
              + "*.."
              + "...", height, width);
        GameOfLife g = new GameOfLife(grid, height, width);

        g.makeTurn();

        assertTrue(g.isCellLive(0, 0));
    }

    @Test
    public void cellWithFourNeighborsDies() {
        int height = 3;
        int width = 3;
        GridString grid = new GridString(
                "**."
              + "***"
              + "...", height, width);
        GameOfLife g = new GameOfLife(grid, height, width);

        g.makeTurn();

        assertFalse(g.isCellLive(1, 1));
    }

    @Test
    public void canCopyGameState() {
        GameOfLife g1 = new GameOfLife(3, 3);
        g1.setCell(1, 1);

        GameOfLife g2 = new GameOfLife(g1);

        assertTrue(g2.isCellLive(1, 1));
    }

    @Test
    public void cellsCalculatesWithoutOrder() {
        int height = 4;
        int width = 3;
        GridString grid = new GridString(
                "***"
              + ".*."
              + "*.*"
              + ".*.", height, width);
        GameOfLife g = new GameOfLife(grid, height, width);

        g.makeTurn();

        assertTrue(g.isCellLive(2, 0));
    }

    @Test
    public void canCreateFromUncorrectString() {
        int height = 2;
        int width = 2;
        GridString grid = new GridString("1234", height, width);
        GameOfLife g = new GameOfLife(grid, height, width);

        assertNotNull(g);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwsOnCreationFromShortString() {
        int height = 3;
        int width = 3;
        GridString grid = new GridString("..", height, width);

        GameOfLife g = new GameOfLife(grid, height, width);
    }


    @Test
    public void gliderMove() {
        int height = 4;
        int width = 4;
        GridString grid = new GridString(
                "..*."
              + "*.*."
              + ".**."
              + "....", height, width);
        GameOfLife g = new GameOfLife(grid, height, width);


        g.makeTurn();
        g.makeTurn();
        g.makeTurn();
        g.makeTurn();

        assertTrue((g.isCellLive(3, 3)));

    }

    @Test
    public void canCreateFromArray() {
        int height = 3;
        int width = 3;
        char[][] arr = {
                {'.', '.', '.'},
                {'.', '*', '.'},
                {'.', '.', '.'},
        };
        GridArray grid = new GridArray(arr, height, width);

        GameOfLife g = new GameOfLife(grid, height, width);

        assertTrue((g.isCellLive(1, 1)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwsOnCreationFromSmallerArray() {
        int height = 3;
        int width = 3;
        char[][] arr = {
                {'.', '.', '.'},
                {'.', '*', '.'},
        };
        GridArray grid = new GridArray(arr, height, width);

        GameOfLife g = new GameOfLife(grid, height, width);
    }

    @Test
    public void makeTurnOutputIsCorrect() {
        int height = 3;
        int width = 3;
        char[][] stableGrid = {
                {'.', '*', '*'},
                {'.', '*', '*'},
                {'.', '.', '.'},
        };
        GridArray grid = new GridArray(stableGrid, height, width);
        GameOfLife g = new GameOfLife(grid, height, width);

        char[][] res = g.makeTurn();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                assertTrue(res[i][j] == stableGrid[i][j]);
            }
        }
    }

    @Test
    public void gridNotChangesByChangingArray() {
        int height = 3;
        int width = 4;
        GridString grid = new GridString(
                "..*."
              + "*.*."
              + ".**.", height, width);

        GameOfLife g = new GameOfLife(grid, height, width);

        char[][] res = g.makeTurn();
        res[1][1] = '*';

        assertFalse(g.isCellLive(1, 1));
    }

}
