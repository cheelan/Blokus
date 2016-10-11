package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class RandomController implements Controller {

	private MoveValidator validator = new MoveValidator();
	
	@Override
	public Move decide(Board board, Collection<Piece> pieces) {
		System.out.println("Player has " + pieces.size());
		List<Move> moves = findAllValidMoves(board, pieces);
		System.out.println(moves.size() + " possible moves");
		return moves.get((int) (Math.random() * moves.size()));
	}

	private List<Move> findAllValidMoves(Board board, Collection<Piece> pieces) {
		List<Move> validMoves = new ArrayList<Move>();
		// Can optimize by keeping track of open corners and only looking to place things there
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.COLS; j++) {
				for (Piece piece : pieces) {
					// TODO need a way to get all arrangements of a piece
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
