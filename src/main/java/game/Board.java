package game;

import lombok.Getter;

public class Board {
	
	public static final int ROWS = 14;
	public static final int COLS = 14;
	
	@Getter private int moves = 0;
	private int[][] board = new int[ROWS][COLS];
	private MoveValidator validator;
	
	public Board(MoveValidator validator) {
		this.validator = validator;
	}
	
	public void applyMove(Move move) {
		if (!validator.isMoveValid(this, move)) {
			throw new IllegalArgumentException("Move is not valid");
		}
		int x = move.getX();
		int y = move.getY();
		for (int i = 0; i < move.getPiece().getShape().length; i++) {
			for (int j = 0; j < move.getPiece().getShape()[0].length; j++) {
				int val = move.getPiece().getShape()[i][j];
				if (val != 0) {
					board[x+i][y+j] = val;
				}
			}
		}
		moves += 1;
	}
	
	public int getCell(int row, int col) {
		return board[row][col];
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			builder.append("[");
			for (int j = 0; j < board[0].length; j++) {
				builder.append(board[i][j]+ ",");
			}
			builder.append("]\n");
		}
		return builder.toString();
	}
}
