package Rummykub;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class BoardTester extends MyTestCase {
    Board board = new Board();

    // NOTE: Tests are for each function in Board Class
    //       Includes success and failures for each function (identifying if the functions work)
    //       Assertions are all done with checking the board

    @Test
    public void addSetTestSuccess()
    {
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(1, Tile.Colors.BL));
        hand.add( new Tile(1, Tile.Colors.BK));
        hand.add( new Tile(1, Tile.Colors.RE));
        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(1, Tile.Colors.BL));
        hand2.add( new Tile(2, Tile.Colors.BL));
        hand2.add( new Tile(3, Tile.Colors.BL));
        board.addSet(hand2);
        assertTrue(board.checkBoard());
        board.addSet(hand);
        assertTrue(board.checkBoard());
        System.out.println(board.printBoard());
    }

    @Test
    public void addSetTestFail()
    {
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(1, Tile.Colors.BL));
        hand.add( new Tile(1, Tile.Colors.BL));
        hand.add( new Tile(1, Tile.Colors.RE));
        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(1, Tile.Colors.BL));
        hand2.add( new Tile(2, Tile.Colors.BK));
        hand2.add( new Tile(3, Tile.Colors.BL));
        board.addSet(hand2);
        assertFalse(board.checkBoard());
        board.addSet(hand);
        assertFalse(board.checkBoard());
        System.out.println(board.printBoard());
    }

    @Test
    public void lessThan3TilesTestFail()
    {
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(1, Tile.Colors.BL));
        hand.add( new Tile(1, Tile.Colors.BL));
        board.addSet(hand);
        assertFalse(board.checkBoard());
        System.out.println(board.printBoard());
    }

    @Test
    public void separateSetTestSuccess()
    {
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(1, Tile.Colors.BL));
        hand.add( new Tile(2, Tile.Colors.BL));
        hand.add( new Tile(3, Tile.Colors.BL));
        hand.add( new Tile(4, Tile.Colors.BL));
        hand.add( new Tile(5, Tile.Colors.BL));
        hand.add( new Tile(6, Tile.Colors.BL));
        board.addSet(hand);
        System.out.println(board.printBoard());
        assertTrue(board.checkBoard());
        board.separateSet(0,3);
        assertTrue(board.checkBoard());
        System.out.println(board.printBoard());
    }

    @Test
    public void separateSetTestFailure()
    {
        int row = 0;
        int tile = 2;
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(1, Tile.Colors.BL));
        hand.add( new Tile(2, Tile.Colors.BL));
        hand.add( new Tile(3, Tile.Colors.BL));
        hand.add( new Tile(4, Tile.Colors.BL));
        hand.add( new Tile(5, Tile.Colors.BL));
        hand.add( new Tile(6, Tile.Colors.BL));
        board.addSet(hand);
        System.out.println(board.printBoard());
        assertTrue(board.checkBoard());
        board.separateSet(row,tile);
        assertFalse(board.checkBoard());
        System.out.println(board.printBoard());
    }

    @Test
    public void combineCurrentTestSuccess()
    {
        int row1 = 0;
        int row2 = 1;
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(4, Tile.Colors.BL));
        hand.add( new Tile(5, Tile.Colors.BL));
        hand.add( new Tile(6, Tile.Colors.BL));
        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(1, Tile.Colors.BL));
        hand2.add( new Tile(2, Tile.Colors.BL));
        hand2.add( new Tile(3, Tile.Colors.BL));
        board.addSet(hand2);
        board.addSet(hand);
        System.out.println(board.printBoard());
        assertTrue(board.checkBoard());

        board.combineCurrent(0,1);
        assertTrue(board.checkBoard());
        System.out.println(board.printBoard());
    }

    @Test
    public void combineCurrentTestFailure()
    {
        int row1 = 0;
        int row2 = 1;
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(4, Tile.Colors.BL));
        hand.add( new Tile(4, Tile.Colors.BK));
        hand.add( new Tile(4, Tile.Colors.RE));
        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(1, Tile.Colors.BL));
        hand2.add( new Tile(2, Tile.Colors.BL));
        hand2.add( new Tile(3, Tile.Colors.BL));
        board.addSet(hand2);
        board.addSet(hand);
        System.out.println(board.printBoard());
        assertTrue(board.checkBoard());

        board.combineCurrent(0,1);
        assertFalse(board.checkBoard());
        System.out.println(board.printBoard());
    }
}