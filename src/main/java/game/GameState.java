package game;

import lombok.Data;

@Data
public class GameState {

	private final Board board;
	private final Player player1; // TODO consider putting this in a list to support arbitrary numbers of players
	private final Player player2;
	
	/**
	 * Create an equal copy of the state that can be modified without disturbing the original state
	 * Useful to give to controllers so they can play around with the state
	 * @return
	 */
	public GameState deepCopy() {
		return new GameState(board.deepCopy(), player1.deepCopy(), player2.deepCopy());
	}
}
