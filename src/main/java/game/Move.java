package game;

import lombok.Value;

@Value
public class Move {

	/**
	 * The piece to move
	 */
	private final Piece piece;
	
	/**
	 * The x coordinate (board row) to move the top left corner of the piece to
	 */
	private final int x;
	
	/**
	 * The y coordinate (board col) to move the top left corner of the piece to
	 */
	private final int y;
	
}
