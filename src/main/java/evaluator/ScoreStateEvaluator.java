package evaluator;

import game.GameScorer;
import game.GameState;
import lombok.Setter;

public class ScoreStateEvaluator implements StateEvaluator {

	@Setter private GameScorer scorer = new GameScorer();
	
	@Override
	public int evaluate(GameState state, int playerId) {
		int player1Score = scorer.calculateScore(state, 1);
		int player2Score = scorer.calculateScore(state, 2);
		if (playerId == 1) {
			return player1Score - player2Score;
		} else {
			return player2Score - player1Score;
		}
	}

}
