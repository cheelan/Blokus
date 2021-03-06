package controller;

import java.util.List;

import game.Controller;
import game.GameState;
import game.Move;

public class RandomController implements Controller {

	private MoveGenerator moveGenerator = new MoveGenerator();
	
	@Override
	public Move decide(GameState state, int playerId) {
		List<Move> moves = moveGenerator.findAllValidMoves(state, playerId);
		//System.out.println(moves.size() + " possible moves");
		if (moves.size() == 0) {
			return null;
		}
		return moves.get((int) (Math.random() * moves.size()));
	}

}
