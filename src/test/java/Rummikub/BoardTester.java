package Rummikub;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

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
        System.out.println(board.printHelper());
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
        System.out.println(board.printHelper());
    }

    @Test
    public void lessThan3TilesTestFail()
    {
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(1, Tile.Colors.BL));
        hand.add( new Tile(1, Tile.Colors.BL));
        board.addSet(hand);
        assertFalse(board.checkBoard());
        System.out.println(board.printHelper());
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
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());
        board.separateSet(0,3);
        assertTrue(board.checkBoard());
        System.out.println(board.printHelper());
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
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());
        board.separateSet(row,tile);
        assertFalse(board.checkBoard());
        System.out.println(board.printHelper());
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
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(0,1,2));

        board.combineCurrent(row1,row2, index);
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());
    }

    @Test
    public void combineCurrentJokerTestSuccess()
    {
        int row1 = 0;
        int row2 = 1;
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(4, Tile.Colors.BL));
        hand.add( new Tile(5, Tile.Colors.BL));
        hand.add( new Tile(6, Tile.Colors.BL));
        hand.add( new Tile(0, Tile.Colors.JOKER));

        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(7, Tile.Colors.BL));
        hand2.add( new Tile(8, Tile.Colors.BL));
        hand2.add( new Tile(9, Tile.Colors.BL));
        board.addSet(hand2);
        board.addSet(hand);
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(0,1,2));

        board.combineCurrent(row1,row2, index);
        assertTrue(board.checkBoard());
        System.out.println(board.printHelper());
    }

    @Test
    public void addToCurrentTestSuccess()
    {
        int row1 = 0;
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(4, Tile.Colors.BL));
        hand.add( new Tile(5, Tile.Colors.BL));
        hand.add( new Tile(6, Tile.Colors.BL));

        board.addSet(hand);
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());

        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(7, Tile.Colors.BL));
        hand2.add( new Tile(8, Tile.Colors.BL));
        hand2.add( new Tile(9, Tile.Colors.BL));
        board.addToCurrent(hand2,0);
        assertTrue(board.checkBoard());
        System.out.println(board.printHelper());
    }

    @Test
    public void addToCurrentTestFailure()
    {
        int row1 = 0;
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(4, Tile.Colors.BL));
        hand.add( new Tile(5, Tile.Colors.BL));
        hand.add( new Tile(6, Tile.Colors.BL));

        board.addSet(hand);
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());

        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(11, Tile.Colors.BL));
        hand2.add( new Tile(8, Tile.Colors.BL));
        hand2.add( new Tile(9, Tile.Colors.BL));
        board.addToCurrent(hand2,0);
        assertFalse(board.checkBoard());
        System.out.println(board.printHelper());
    }

    @Test
    public void addToCurrentJokerTestSuccess()
    {
        int row1 = 0;
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(4, Tile.Colors.BL));
        hand.add( new Tile(5, Tile.Colors.BL));
        hand.add( new Tile(6, Tile.Colors.BL));
        hand.add( new Tile(0, Tile.Colors.JOKER));

        board.addSet(hand);
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());

        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(7, Tile.Colors.BL));
        hand2.add( new Tile(8, Tile.Colors.BL));
        hand2.add( new Tile(9, Tile.Colors.BL));
        board.addToCurrent(hand2,0);
        assertTrue(board.checkBoard());
        System.out.println(board.printHelper());
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
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(0,1,2));

        board.combineCurrent(row1,row2,index);
        assertFalse(board.checkBoard());
        System.out.println(board.printHelper());
    }

    @Test
    public void jokerTestSuccess()
    {
        ArrayList<Tile> hand = new ArrayList<Tile>();
        hand.add( new Tile(0, Tile.Colors.JOKER));
        hand.add( new Tile(4, Tile.Colors.BL));
        hand.add( new Tile(4, Tile.Colors.RE));
        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(0, Tile.Colors.JOKER));
        hand2.add( new Tile(2, Tile.Colors.BL));
        hand2.add( new Tile(3, Tile.Colors.BL));
        board.addSet(hand2);
        assertTrue(board.checkBoard());

        board.addSet(hand);
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());
    }
    @Test
    public void jokerTest2Success()
    {
        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(0, Tile.Colors.JOKER));
        hand2.add( new Tile(2, Tile.Colors.BL));
        hand2.add( new Tile(3, Tile.Colors.BL));
        hand2.add( new Tile(4, Tile.Colors.BL));
        hand2.add( new Tile(5, Tile.Colors.BL));
        hand2.add( new Tile(7, Tile.Colors.BL));
        hand2.add( new Tile(8, Tile.Colors.BL));
        hand2.add( new Tile(9, Tile.Colors.BL));
        hand2.add( new Tile(10, Tile.Colors.BL));
        board.addSet(hand2);
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());
    }

    @Test
    public void jokerTest3Success()
    {
        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(5, Tile.Colors.BL));
        hand2.add( new Tile(6, Tile.Colors.BL));
        hand2.add( new Tile(7, Tile.Colors.BL));
        hand2.add( new Tile(8, Tile.Colors.BL));
        hand2.add( new Tile(9, Tile.Colors.BL));
        hand2.add( new Tile(10, Tile.Colors.BL));
        hand2.add( new Tile(0, Tile.Colors.JOKER));
        hand2.add( new Tile(11, Tile.Colors.BL));
        hand2.add( new Tile(12, Tile.Colors.BL));
        hand2.add( new Tile(13, Tile.Colors.BL));

        board.addSet(hand2);
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());
    }

    @Test
    public void jokerTest4Success()
    {
        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(5, Tile.Colors.BL));
        hand2.add( new Tile(0, Tile.Colors.JOKER));
        hand2.add( new Tile(6, Tile.Colors.BL));
        hand2.add( new Tile(7, Tile.Colors.BL));
        hand2.add( new Tile(8, Tile.Colors.BL));
        hand2.add( new Tile(9, Tile.Colors.BL));
        hand2.add( new Tile(10, Tile.Colors.BL));
        hand2.add( new Tile(11, Tile.Colors.BL));
        hand2.add( new Tile(12, Tile.Colors.BL));
        hand2.add( new Tile(13, Tile.Colors.BL));

        board.addSet(hand2);
        System.out.println(board.printHelper());
        assertTrue(board.checkBoard());
    }

    @Test
    public void jokerTestFailure()
    {
        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(5, Tile.Colors.BL));
        hand2.add( new Tile(0, Tile.Colors.JOKER));
        hand2.add( new Tile(6, Tile.Colors.BK));
        hand2.add( new Tile(7, Tile.Colors.BL));
        hand2.add( new Tile(8, Tile.Colors.BL));
        hand2.add( new Tile(9, Tile.Colors.BL));
        hand2.add( new Tile(10, Tile.Colors.BL));
        hand2.add( new Tile(11, Tile.Colors.BL));
        hand2.add( new Tile(12, Tile.Colors.BL));
        hand2.add( new Tile(13, Tile.Colors.BL));

        board.addSet(hand2);
        System.out.println(board.printHelper());
        assertFalse(board.checkBoard());
    }

    @Test
    public void jokerTest2Failure()
    {
        ArrayList<Tile> hand2 = new ArrayList<Tile>();
        hand2.add( new Tile(5, Tile.Colors.BL));
        hand2.add( new Tile(0, Tile.Colors.JOKER));
        hand2.add( new Tile(5, Tile.Colors.BK));
        hand2.add( new Tile(5, Tile.Colors.RE));
        hand2.add( new Tile(5, Tile.Colors.YE));


        board.addSet(hand2);
        System.out.println(board.printHelper());
        assertFalse(board.checkBoard());
    }
}