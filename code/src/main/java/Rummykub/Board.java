package Rummykub;

import java.util.ArrayList;

public class Board {
	private Tile [][] board;
	
	public Board() {
		board = new Tile[100][100];
	}
	
	public boolean putTiles(ArrayList<Tile> tiles, int row, int col) {
		Tile[][] tempBoard = board;
		for (int i=0; i< tiles.size(); i++) {
			if (row+i > 99 || col > 99)
				return false;
			if (tempBoard[row+i][col] != null)
				return false;
			tempBoard[row+i][col] = tiles.get(i);
		}
		board = tempBoard;
		return true;
	}
}
