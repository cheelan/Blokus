package evaluator;

import controller.MoveGenerator;
import game.GameScorer;
import game.GameState;
import lombok.Setter;

/**
 * Score a state based on the score differential and break ties by picking the
 * state with the most moves available relative to the opponent
 */
public class CornerStateEvaluator implements StateEvaluator {

	@Setter private GameScorer scorer = new GameScorer();
	@Setter private MoveGenerator moveGenerator = new MoveGenerator();
	
	@Override
	public int evaluate(GameState state, int playerId) {
		// Get the score differential and multiply by 1000 (so this dominates the moves)
		int score = 1000 * (scorer.calculateScore(state, 1) - scorer.calculateScore(state, 2));
		// Add the difference between player one's available moves and player two's available moves as a tiebreaker
		int player1Moves = moveGenerator.findAllValidMoves(state, 1).size();
		int player2Moves = moveGenerator.findAllValidMoves(state, 2).size();
		
		if (playerId == 1) {
			return score + player1Moves - player2Moves;
		} else {
			return (-1 * score) + player2Moves - player1Moves;
		}
	}

}
