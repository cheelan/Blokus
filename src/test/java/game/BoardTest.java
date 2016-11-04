package game;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import game.Piece.Name;
import game.Piece.PieceFactory;

public class BoardTest {

	private Board board;
	private MoveValidator validator;
	
	@Before
	public void init() {
		validator = mock(MoveValidator.class);
		board = new Board(validator);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidMoves_shouldFail() {
		when(validator.isMoveValid(eq(board), any(Move.class))).thenReturn(false);
		board.applyMove(new Move(PieceFactory.createPiece(Name.F, 1), 0, 0));
	}
		
	@Test
	public void appliedMoves_shouldAddPieceToBoard() {
		when(validator.isMoveValid(eq(board), any(Move.class))).thenReturn(true);

		Move move = new Move(PieceFactory.createPiece(Name.F, 1), 0, 0);
		board.applyMove(move);
		int[][] shape = move.getPiece().getShape();
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				Assert.assertEquals(shape[i][j], board.getCell(i, j));
			}
		}
	}
	
	@Test
	public void removeMove_ShouldRemovePieceFromBoard() {
		when(validator.isMoveValid(eq(board), any(Move.class))).thenReturn(true);

		Move move = new Move(PieceFactory.createPiece(Name.F, 1), 0, 0);
		board.applyMove(move);
		board.removeMove(move);
		int[][] shape = move.getPiece().getShape();
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				Assert.assertEquals(0, board.getCell(i, j));
			}
		}
	}
	
	@Test
	public void copiesShouldInitiallyBeEqual() {
		Board copy = board.deepCopy();
		assert(copy.equals(board));
	}
	
	@Test
	public void copiesShouldHaveDifferentInstance() {
		when(validator.isMoveValid(eq(board), any(Move.class))).thenReturn(true);
		Board copy = board.deepCopy();
		Move move = new Move(PieceFactory.createPiece(Name.F, 1), 0, 0);
		copy.applyMove(move);
		assert(!copy.equals(board));
	}
}
