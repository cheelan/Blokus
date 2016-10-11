package game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import game.Piece.Name;
import game.Piece.PieceFactory;

public class MoveValidatorTest {
	
	private final MoveValidator validator = new MoveValidator();
	private Board board;
	
	@Before
	public void init() {
		board = Mockito.mock(Board.class);
	}
	
	@Test
	public void movesOutsideBoard_areInvalid() {
		Move move = new Move(PieceFactory.createPiece(Name.F, 1), Board.COLS - 2, 0);
		Assert.assertFalse(validator.isMoveValid(board, move));
		
		Move move2 = new Move(PieceFactory.createPiece(Name.F, 1), 0, Board.ROWS - 2);
		Assert.assertFalse(validator.isMoveValid(board, move2));
	}
	
	@Test
	public void overlappingPieces_areInvalid() {
		Mockito.when(board.getCell(1, 1)).thenReturn(1);
		Move move = new Move(PieceFactory.createPiece(Name.F, 1), 0, 0);
		Assert.assertFalse(validator.isMoveValid(board, move));
		
		Mockito.when(board.getCell(1, 1)).thenReturn(2);
		Assert.assertFalse(validator.isMoveValid(board, move));
	}
	
	@Test
	public void movesTouchingSameColorEdge_areInvalid() {
		Mockito.when(board.getCell(1, 1)).thenReturn(1);
		Move move = new Move(PieceFactory.createPiece(Name.F, 1), 1, 2);
		Assert.assertFalse(validator.isMoveValid(board, move));
	}
	
	@Test
	public void movesTouchingDifferenteColorEdge_areInvalid() {
		Mockito.when(board.getCell(1, 1)).thenReturn(2);
		Move move = new Move(PieceFactory.createPiece(Name.F, 1), 1, 2);
		Assert.assertFalse(validator.isMoveValid(board, move));
	}
	
	@Ignore @Test
	public void movesNotTouchingSameColorCorner_areInvalid() {
		
	}
	
	// TODO Exception: Non connecting moves are ok only on the player's first move
	@Ignore @Test
	public void nonConnectedNonOpeningMoves_areInvalid() {
		
	}
	
	@Ignore @Test
	public void openingMove_mustTouchCorner() {
		
	}
}
