package Rummikub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Board {
	protected ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();

	//Add a new set to the board
	public void addSet(ArrayList<Tile> set) {
		set = addJoker(set);
		board.add(set);
		Collections.sort(board.get(board.size()-1), new Comparator<Tile>() {
			@Override
			public int compare(Tile tile1, Tile tile2) { return Integer.compare(tile1.getValue(), tile2.getValue()); }
		});
	}

	//Add specific tiles from hand to board
	public void addToCurrent(ArrayList<Tile> tiles, int row) {
		ArrayList<Tile> checkRow = board.get(row);
		checkRow.addAll(tiles);
		checkRow = addJoker(checkRow);
		board.set(row,checkRow);
		Collections.sort(board.get(row), new Comparator<Tile>() {
			@Override
			public int compare(Tile tile1, Tile tile2) { return Integer.compare(tile1.getValue(), tile2.getValue()); }
		});
	}

	//Separates a current set on the board into two sets
	public void separateSet(int row, int tileNum) {
		ArrayList<Tile> newSet = new ArrayList<Tile>();
		if (row < board.size() && board.get(row).size() > tileNum) {
			for (int col = tileNum; col < board.get(row).size(); col++)
				newSet.add(board.get(row).get(col));
			int size = board.get(row).size();
			for (int col = tileNum; col < size; col++)
				board.get(row).remove(tileNum);
			board.add(newSet);
		}
	}

	//combine two sets that's are currently on the board
	public void combineCurrent(int sourceRow, int destinationRow, ArrayList<Integer> tiles) {
		ArrayList<Tile> moving = new ArrayList<Tile>();
		for (int i = 0; i < tiles.size(); i++)
			moving.add(board.get(sourceRow).get(tiles.get(i)-1));
		for (int i = 0; i < tiles.size(); i++)
			board.get(sourceRow).remove(board.get(sourceRow).get(tiles.get(i) - i -1));
		ArrayList<Tile> checkDestination = board.get(destinationRow);
		checkDestination.addAll(moving);
		checkDestination = addJoker(checkDestination);
		board.set(destinationRow,checkDestination);
		// if moving all tiles from source row, remove the empty row
		if (board.get(sourceRow).size() == 0) {
			board.remove(sourceRow);
		}
		for (int i = 0; i < board.size(); i++) {
			Collections.sort(board.get(i), new Comparator<Tile>() {
				@Override
				public int compare(Tile tile1, Tile tile2) {
					return Integer.compare(tile1.getValue(), tile2.getValue());
				}
			});
		}
	}

	public String printHelper() {
		StringBuilder printBoard = new StringBuilder();
		int size = 0;
		for (int i = 0; i < board.size(); i++) {
			if (i < 9) {
				printBoard.append("0").append(Integer.toString(i + 1)).append("|  ");
			}else {
				printBoard.append(Integer.toString(i + 1)).append("|  ");
			}
			size = getSize(size, i);
			for (int j = 0; j < board.get(i).size(); j++) {
				if (getTile(i, j).getColor() == Tile.Colors.JOKER) {
					printBoard.append(getTile(i, j).getColor()).append("}  ");
				} else {
					printBoard.append(getTile(i, j).getValue()).append("  ");
					printBoard.append(getTile(i, j).getColor()).append("}  ");
				}
			}
			printBoard.append("\n");
		}
		for (int i = 0; i < (size * 18) + 1; i++)
			printBoard.append("-");
		return printBoard.toString();
	}

	private int getSize(int size, int i) {
		if (board.get(i).size() > size)
			size = board.get(i).size();
		return size;
	}


	// Checks if the board is valid with all the modified rows
	// Used for ending turns to see if what player did is valid
	public boolean checkBoard() {
		for (int i = 0; i < board.size(); i++) {
			int type = 0;
			int valueCorrect = 0;
			Tile.Colors color = null;
			ArrayList<Tile.Colors> colors = new ArrayList<Tile.Colors>();
			if (board.get(i).size() < 3)
				return false;
			for (int j = 0; j < board.get(i).size() - 1; j++) {
				if (color == null)
					color = getColorIfNotJoker(i,j);
				colors.add(getTile(i, j).color);
				if ( getTile(i, j).getValue() == board.get(i).get(j + 1).getValue() - 1 && (getTile(i, j).getColor() == Tile.Colors.JOKER
						|| (getTile(i, j).getColor() == color && board.get(i).get(j+1).getColor() == color
						||  board.get(i).get(j+1).getColor() == Tile.Colors.JOKER)) && (type == 0
						|| type == 1) ) {
					valueCorrect++;
					type = 1;
				} else {
					if (getTile(i, j).getValue() == board.get(i).get(j + 1).getValue()
							&& (!colors.contains(board.get(i).get(j+1).getColor())
							&& colors.size() < 4) && (type == 0 || type == 2)) {
						valueCorrect++;
						type = 2;
					}
				}
			}
			if (valueCorrect != board.get(i).size()-1)
				return false;
		}
		return true;
	}

	// Gets the color, if the color isn't a joker
	private Tile.Colors getColorIfNotJoker(int i, int j) {
		Tile.Colors ret = null;
		if (getTile(i, j).getColor() != Tile.Colors.JOKER)
			ret = getTile(i, j).getColor();
		return ret;
	}

	private Tile getTile(int i, int j) {
		return board.get(i).get(j);
	}


	public ArrayList<Tile> addJoker(ArrayList<Tile> row) {
		for(int i = 0; i < row.size(); i++) {
			if (row.get(i).getColor() == Tile.Colors.JOKER) {
				if (i == 0 &&row.get(1).getValue() == row.get(2).getValue()) {
					row.get(i).setValue(row.get(i + 1).getValue());
					return row;
				}
				else if (i == 1 && row.get(0).getValue() == row.get(2).getValue()) {
					row.get(i).setValue(row.get(i + 1).getValue());
					return row;
				}
				else if (i == 2 && row.get(0).getValue() == row.get(1).getValue()) {
					row.get(i).setValue(row.get(i - 1).getValue());
					return row;
				} else {
					row.remove(i);
					Collections.sort(row, new Comparator<Tile>() {
						@Override
						public int compare(Tile tile1, Tile tile2) {
							return Integer.compare(tile1.getValue(), tile2.getValue());
						}
					});
					for (int j = 0; j < row.size() - 1; j++) {
						if (row.get(j).getValue() != row.get(j + 1).getValue() - 1) {
							Tile tile = new Tile(row.get(j).getValue() + 1, Tile.Colors.JOKER);
							row.add(tile);
							return row;
						}
					}
					if (row.get(row.size()-1).getValue() == 13) {
						Tile tile = new Tile(row.get(0).getValue() - 1, Tile.Colors.JOKER);
						row.add(tile);
						return row;
					} else {
						Tile tile = new Tile(row.get(row.size()-1).getValue() + 1, Tile.Colors.JOKER);
						row.add(tile);
						return row;
					}
				}
			}
		}
		return row;
	}

	public ArrayList<Tile> getTiles() {
		ArrayList<Tile> tiles = new ArrayList<>();
		for (int i = 0; i < board.size(); i++)
			tiles.addAll(board.get(i));
		return tiles;
	}

	public ArrayList<Tile> getRow (int index) {
		return board.get(index);
	}

	public int getBoardSize() {
		return board.size();
	}

	public void setTiles(ArrayList<Tile> tiles) {
		board.clear();
		if (!tiles.isEmpty())
			board.add(tiles);
	}

	// this function checks if there are tiles of indices
	// this is used for checking indices for moving tiles
	public boolean hasTiles(int srcRow, int[] tilesIndex) {
		for (int index: tilesIndex) {
			if (board.get(srcRow) == null || board.get(srcRow).get(index-1) == null)
				return false;
		}
		return true;
	}
}
