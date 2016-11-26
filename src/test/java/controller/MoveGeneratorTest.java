package controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import game.GameState;
import game.MoveValidator;
import game.Piece;
import game.Piece.Name;
import game.Piece.PieceFactory;
import game.Player;

public class MoveGeneratorTest {
	
	private MoveGenerator moveGenerator;
	private MoveValidator validator;
	private GameState state;
	private Player player1;
	private Map<Name, Piece> pieceMap;
	
	@Before
	public void init() {
		validator = mock(MoveValidator.class);
		moveGenerator = new MoveGenerator();
		moveGenerator.setValidator(validator);
		
		state = mock(GameState.class);
		player1 = mock(Player.class);
		when(state.getPlayer(1)).thenReturn(player1);
		pieceMap = mock(Map.class);
		when(player1.getPieces()).thenReturn(pieceMap);
	}
	
	@Test
	public void testFindAllValidMoves() {
		when(validator.isMoveValid(any(), any())).thenReturn(true);
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(PieceFactory.createPiece(Name.ONE, 1));
		when(pieceMap.values()).thenReturn(pieces);
		assertEquals(196, moveGenerator.findAllValidMoves(state, 1).size());
	}
	
	@Test
	public void shouldNotGenerateInvalidMoves() {
		when(validator.isMoveValid(any(), any())).thenReturn(false);
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(PieceFactory.createPiece(Name.ONE, 1));
		when(pieceMap.values()).thenReturn(pieces);
		assertEquals(0, moveGenerator.findAllValidMoves(state, 1).size());
	}

}
