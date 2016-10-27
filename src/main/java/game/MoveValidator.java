package game;

public class MoveValidator {
	
	/**
	 * Return true iff:
	 * 1. The newly placed piece does not overlap with an existing piece
	 * 2. The newly placed piece fits within the borders of the board
	 * 3. The newly placed piece touches at least one corner of the same color
	 * 4. The newly placed piece touches zero edges of the same color
	 * @param move
	 * @return
	 */
	public boolean isMoveValid(Board board, Move move) {
		int[][] shape = move.getPiece().getShape();
		boolean isPieceConnected = false;
		boolean isPieceOnPlayer1Start = false;
		boolean isPieceOnPlayer2Start = false;
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				if (shape[i][j] == 0) {
					continue;
				}
				int x = move.getX() + i;
				int y = move.getY() + j;
				// The newly placed piece fits within the borders of the board
				if (x >= Board.ROWS || y >= Board.COLS || x < 0 || y < 0) {
					return false;
				}
				// The newly placed piece does not overlap with an existing piece
				if (board.getCell(x, y) != 0) {
					return false;
				}
				// The newly placed piece touches zero edges of the same color
				if (x + 1 < Board.ROWS) {
					if (board.getCell(x+1, y) == shape[i][j]) {
						return false;
					}
				}
				if (x - 1 >= 0) {
					if (board.getCell(x - 1, y) == shape[i][j]) {
						return false;
					}
				}
				if (y + 1 < Board.COLS) {
					if (board.getCell(x, y + 1) == shape[i][j]) {
						return false;
					}
				}
				if (y - 1 >= 0) {
					if (board.getCell(x, y - 1) == shape[i][j]) {
						return false;
					}
				}
				// TODO test each of these cases
				if (!isPieceConnected) {
					if (x+1 < Board.ROWS && y + 1 < Board.COLS && board.getCell(x + 1, y + 1) == shape[i][j]) {
						isPieceConnected = true;
					}
					if (x + 1 < Board.ROWS && y - 1 >= 0 && board.getCell(x + 1, y - 1) == shape[i][j]) {
						isPieceConnected = true;
					}
					if (x - 1 >= 0 && y + 1 < Board.COLS && board.getCell(x - 1, y + 1) == shape[i][j]) {
						isPieceConnected = true;
					}
					if (x - 1 >= 0 && y - 1 >= 0 && board.getCell(x - 1, y - 1) == shape[i][j]) {
						isPieceConnected = true;
					}
				}
				
				if (x == 4 && y == 4) {
					isPieceOnPlayer1Start = true;
				}
				if (x == Board.ROWS - 4 && y == Board.COLS - 4) {
					isPieceOnPlayer2Start = true;
				}
			}
		}
		// The newly placed piece touches at least one corner of the same color 
		// OR it is the player's first move, in which case it must touch a corner
		if (board.getMoves() < 2) {
			return isPieceOnPlayer1Start || isPieceOnPlayer2Start;
		}
		return isPieceConnected;
	}
}
