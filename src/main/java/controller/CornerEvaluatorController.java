package controller;

import java.util.List;

import evaluator.CornerStateEvaluator;
import evaluator.StateEvaluator;
import game.Controller;
import game.GameState;
import game.Move;

public class CornerEvaluatorController implements Controller {
	
	private MoveGenerator moveGenerator = new MoveGenerator();
	private StateEvaluator evaluator = new CornerStateEvaluator();

	@Override
	public Move decide(GameState state, int playerId) {
		List<Move> moves = moveGenerator.findAllValidMoves(state, playerId);
		if (moves.size() == 0) {
			return null;
		}
		int maxScore = Integer.MIN_VALUE;
		Move bestMove = null;
		for (Move move : moves) {
			state.applyMove(playerId, move);
			int score = evaluator.evaluate(state, playerId);
			if (score > maxScore) {
				maxScore = score;
				bestMove = move;
			}
			state.undoLastMove(playerId);
		}
		return bestMove;
	}

}
