package evaluator;

import game.GameState;

public interface StateEvaluator {
	
	/**
	 * Score a given state. The score is positive if it favors playerId
	 * @param state
	 * @return
	 */
	public int evaluate(GameState state, int playerId);
}
