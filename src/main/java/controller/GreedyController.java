package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import game.Board;
import game.Controller;
import game.Move;
import game.Piece;

public class GreedyController implements Controller {
	
	private MoveGenerator moveGenerator = new MoveGenerator();

	@Override
	public Move decide(Board board, Collection<Piece> pieces) {
		List<Move> moves = moveGenerator.findAllValidMoves(board, pieces);
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
