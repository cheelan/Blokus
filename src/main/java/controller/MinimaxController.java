package controller;

import evaluator.ScoreStateEvaluator;
import evaluator.StateEvaluator;
import game.Controller;
import game.GameState;
import game.Move;

public class MinimaxController implements Controller {

	private StateEvaluator evaluator = new ScoreStateEvaluator();
	private MoveGenerator generator = new MoveGenerator();
	private MinimaxHelper minimax = new MinimaxHelper(evaluator, generator);
	
	@Override
	public Move decide(GameState state, int playerId) {
		int myPlayerId;
		int opponentPlayerId;
		int minimaxDepth = 3;
		if (playerId == 1) {
			myPlayerId = 1;
			opponentPlayerId = 2;
		} else {
			myPlayerId = 2;
			opponentPlayerId = 1;
		}
		// As the board gets more crowded, the branching factor goes down, so greater depths are reachable
		if (state.getPlayer(myPlayerId).getMoveHistory().size() > 6) {
			minimaxDepth = 5;
		}
		else if (state.getPlayer(myPlayerId).getMoveHistory().size() > 7) {
			minimaxDepth = 7;
		}
		else if (state.getPlayer(myPlayerId).getMoveHistory().size() > 8) {
			minimaxDepth = 11;
		}
		return minimax.getMinimaxMove(state, myPlayerId, opponentPlayerId, minimaxDepth);
	}
}
