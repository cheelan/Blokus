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
		return true;
	}
}
