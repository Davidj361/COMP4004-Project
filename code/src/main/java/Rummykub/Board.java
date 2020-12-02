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
			public int compare(Tile tile1, Tile tile2){return  Integer.compare(tile1.getValue(), tile2.getValue());}
		});
	}

	//Separates a current set on the board into two sets
	public void separateSet(int row, int tileNum){
		ArrayList<Tile> newSet = new ArrayList<Tile>();
		for (int i = tileNum; i < board.get(row).size(); i++) {
			newSet.add(board.get(row).get(i));
		}
		int size = board.get(row).size();
		for(int i = tileNum; i < size; i++){
			board.get(row).remove(tileNum);
		}
		board.add(newSet);
	}

	//combine two sets that's are currently on the board
	public void combineCurrent(int row1, int row2){
		ArrayList<Tile> combine = board.get(row2);
		board.remove(row2);
		board.get(row1).addAll(combine);
		Collections.sort(board.get(row1), new Comparator<Tile>() {
			@Override
			public int compare(Tile tile1, Tile tile2){return  Integer.compare(tile1.getValue(), tile2.getValue());}
		});
	}

	public String printBoard() {
		String printBoard = "";
		int size = 0;
		for (int i = 0; i < board.size(); i++) {
			if(i < 10) {
				printBoard += "0" + Integer.toString(i) + "|  ";
			}else {
				printBoard += Integer.toString(i) + "|  ";
			}
			if (board.get(i).size() > size){
				size = board.get(i).size();
			}
			for (int j = 0; j < board.get(i).size(); j++) {
				printBoard += board.get(i).get(j).toString() + "  ";
			}
			printBoard += "\n";
		}
		for(int i = 0; i < ((size * 18) + 1); i++) {
			printBoard+="-";
		}
		return printBoard;
	}


	public boolean checkBoard() {
		for (int i = 0; i < board.size(); i++) {
			int type = 0;
			int valueCorrect = 0;
			ArrayList<Tile.Colors> colors = new ArrayList<Tile.Colors>();


			if(board.get(i).size() < 3){
				return false;
			}
			for (int j = 0; j < board.get(i).size() - 1; j++) {
				colors.add(board.get(i).get(j).color);
				if((board.get(i).get(j).getValue() == board.get(i).get(j + 1).getValue() - 1 || board.get(i).get(j).getValue() == board.get(i).get(j + 1).getValue() + 1) && board.get(i).get(j).getColor() == board.get(i).get(j+1).getColor() && (type == 0 || type == 1)) {
					valueCorrect++;
					type = 1;
				} else {
					if(board.get(i).get(j).getValue() == board.get(i).get(j + 1).getValue() && !colors.contains(board.get(i).get(j+1).getColor()) && (type == 0 || type == 2)){
						valueCorrect++;
						type = 2;
					}
				}
			}
			if(valueCorrect != board.get(i).size()-1){
				return false;
			}
		}
		return true;
	}

	// Return true if b is the same as this board, otherwise false
	// TODO Implement this function
	public boolean compare(Board b) {
		return false;
	}
}