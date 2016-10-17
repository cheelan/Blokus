package controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.Board;
import game.MoveValidator;
import game.Piece;
import game.Piece.Name;
import game.Piece.PieceFactory;

public class MoveGeneratorTest {
	
	private MoveGenerator moveGenerator;
	private MoveValidator validator;
	
	@Before
	public void init() {
		validator = mock(MoveValidator.class);
		moveGenerator = new MoveGenerator();
		moveGenerator.setValidator(validator);
	}
	
	@Test
	public void testFindAllValidMoves() {
		when(validator.isMoveValid(any(), any())).thenReturn(true);
		Board board = new Board(validator);
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(PieceFactory.createPiece(Name.ONE, 1));
		assertEquals(196, moveGenerator.findAllValidMoves(board, pieces).size());
	}
	
	@Test
	public void shouldNotGenerateInvalidMoves() {
		when(validator.isMoveValid(any(), any())).thenReturn(false);
		Board board = new Board(validator);
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(PieceFactory.createPiece(Name.ONE, 1));
		assertEquals(0, moveGenerator.findAllValidMoves(board, pieces).size());
	}

}
