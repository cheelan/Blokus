package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import game.Board;
import game.Move;
import game.MoveValidator;
import game.Piece;
import lombok.Setter;

public class MoveGenerator {
	
	@Setter private MoveValidator validator = new MoveValidator();
	
	public List<Move> findAllValidMoves(Board board, Collection<Piece> pieces) {
		List<Move> validMoves = new ArrayList<Move>();
		// Can optimize by keeping track of open corners and only looking to place things there
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.COLS; j++) {
				for (Piece piece : pieces) {
					for (Piece arrangement : piece.getArrangements()) {
						Move proposed = new Move(arrangement, i, j);
						if (validator.isMoveValid(board, proposed)) {
							validMoves.add(proposed);
						}
					}
				}
			}
		}
		return validMoves;
	}
}
