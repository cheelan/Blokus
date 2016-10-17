package game;

import java.util.List;

public class GameScorer {

	public static final int INITIAL_SCORE = -89;
	public static final int ALL_PIECES_USED_BONUS = 15;
	public static final int LAST_PIECE_MONIMO_BONUS = 5;
	
	/**
	 * Given an ordered list of a player's moves, calculate the player's score
	 * @param moves
	 * @return
	 */
	public int calculateScore(List<Move> moves) {
		int score = INITIAL_SCORE;
		for (Move move : moves) {
			score += move.getPiece().getValue();
		}
		// 15 point bonus for playing all pieces
		if (moves.size() == Piece.Name.values().length) {
			score += ALL_PIECES_USED_BONUS;
			// Extra 5 point bonus if the last piece is the monimo
			if (moves.get(moves.size() - 1).getPiece().getValue() == 1) {
				score += LAST_PIECE_MONIMO_BONUS;
			}
		}
		return score;
	}
}
