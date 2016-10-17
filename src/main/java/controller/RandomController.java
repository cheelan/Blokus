package controller;

import java.util.Collection;
import java.util.List;

import game.Board;
import game.Controller;
import game.Move;
import game.Piece;

public class RandomController implements Controller {

	private MoveGenerator moveGenerator = new MoveGenerator();
	
	@Override
	public Move decide(Board board, Collection<Piece> pieces) {
		List<Move> moves = moveGenerator.findAllValidMoves(board, pieces);
		System.out.println(moves.size() + " possible moves");
		if (moves.size() == 0) {
			return null;
		}
		return moves.get((int) (Math.random() * moves.size()));
	}

}
