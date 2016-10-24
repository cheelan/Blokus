package controller;

import java.util.ArrayList;
import java.util.List;

import game.Controller;
import game.GameState;
import game.Move;

public class GreedyController implements Controller {
	
	private MoveGenerator moveGenerator = new MoveGenerator();

	@Override
	public Move decide(GameState state, int playerId) {
		List<Move> moves = moveGenerator.findAllValidMoves(state.getBoard(), state.getPlayer(playerId).getPieces().values());
		if (moves.size() == 0) {
			return null;
		}
		// Figure out what the highest value pieces are
		int maxValue = 0;
		for (Move move : moves) {
			maxValue = Math.max(move.getPiece().getValue(), maxValue);
		}
		// Randomly pick one of those pieces
		List<Move> greedyMoves = new ArrayList<Move>();
		for (Move move : moves) {
			if (move.getPiece().getValue() == maxValue) {
				greedyMoves.add(move);
			}
		}
		return greedyMoves.get((int) (Math.random() * greedyMoves.size()));
	}

}
