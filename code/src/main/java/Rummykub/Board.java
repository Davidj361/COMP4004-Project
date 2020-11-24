package Rummykub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Board {
	protected ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();

	public Board() {

	}

	//Add a new set to the board
	public void addSet(ArrayList<Tile> set) {
		board.add(set);
	}

	//Add specific tiles from hand to board
	public void addToCurrent(ArrayList<Tile> tiles, int row){
		board.get(row).addAll(tiles);
		Collections.sort(board.get(row), new Comparator<Tile>() {
			@Override
			public int compare(Tile tile2, Tile tile1)
			{
				return  tile1.getValue().compareTo(tile2.getValue());
			}
		});
	}

	//Seperates a current set on the board into two sets
	public void seperateSet(int row, int tileNum){
		ArrayList<Tile> newSet = new ArrayList<Tile>();
		for (int i = row; i < board.get(tileNum).size(); i++) {
			newSet.add(board.get(tileNum).get(i));
		}
		board.add(newSet);
	}

	//combine two sets thats are currently on the board
	public void combineCurrent(int row1, int row2){
		ArrayList<Tile> combine = board.get(row2);
		board.remove(row2);
		board.get(row1).addAll(combine);
		Collections.sort(board.get(row1), new Comparator<Tile>() {
			@Override
			public int compare(Tile tile2, Tile tile1)
			{
				return  tile1.getValue().compareTo(tile2.getValue());
			}
		});
	}
}