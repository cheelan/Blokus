package game;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.Piece.Name;
import game.Piece.PieceFactory;


public class GameStateTest {
	
	private GameState state;
	private Board board;
	private Player player1;
	private Player player2;
	private List<Player> players;

	@Before
	public void init() {
		board = mock(Board.class);
		player1 = mock(Player.class);
		when(player1.getId()).thenReturn(1);
		player2 = mock(Player.class);
		when(player2.getId()).thenReturn(2);
		players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		state = new GameState(board, players);
	}
	
	@Test
	public void deepCopiesShouldBeDeep() {
		Board copiedBoard = mock(Board.class);
		Player copiedPlayer1 = mock(Player.class);
		Player copiedPlayer2 = mock(Player.class);

		when(board.deepCopy()).thenReturn(copiedBoard);
		when(player1.deepCopy()).thenReturn(copiedPlayer1);
		when(player2.deepCopy()).thenReturn(copiedPlayer2);
		
		GameState copy = state.deepCopy();
		assert(copy.getBoard() == copiedBoard);
		assert(copy.getPlayers().get(1) == copiedPlayer1);
		assert(copy.getPlayers().get(2) == copiedPlayer2);
	}
	
	@Test
	public void applyMoveShouldUpdateBoard() {
		Move move = mock(Move.class);
		when(move.getPiece()).thenReturn(PieceFactory.createPiece(Name.ONE, 1));
		state.applyMove(1, move);
		verify(board).applyMove(move);
	}
	
	@Test
	public void applyMoveShouldUpdateMoveHistory() {
		Move move = mock(Move.class);
		when(move.getPiece()).thenReturn(PieceFactory.createPiece(Name.ONE, 1));
		List<Move> moveHistory = mock(List.class);
		when(player1.getMoveHistory()).thenReturn(moveHistory);
		state.applyMove(1, move);
		verify(moveHistory).add(move);
	}
	
	@Test
	public void applyMoveShouldRemovePlayersPiece() {
		Move move = mock(Move.class);
		when(move.getPiece()).thenReturn(PieceFactory.createPiece(Name.ONE, 1));
		state.applyMove(1, move);
		verify(player1).removePiece(Name.ONE);
	}
	
	@Test
	public void undoLastMoveShouldRemoveMoveFromHistory() {
		Move move = mock(Move.class);
		when(move.getPiece()).thenReturn(PieceFactory.createPiece(Name.ONE, 1));
		List<Move> moveHistory = new ArrayList<Move>();
		moveHistory.add(move);
		when(player1.getMoveHistory()).thenReturn(moveHistory);
		state.undoLastMove(1);
		assertEquals(0, player1.getMoveHistory().size());
	}
	
	@Test
	public void undoLastMoveShouldUpdateBoard() {
		Move move = mock(Move.class);
		when(move.getPiece()).thenReturn(PieceFactory.createPiece(Name.ONE, 1));
		List<Move> moveHistory = new ArrayList<Move>();
		moveHistory.add(move);
		when(player1.getMoveHistory()).thenReturn(moveHistory);
		state.undoLastMove(1);
		verify(board).removeMove(move);
	}
	
	@Test
	public void undoLastMoveShouldAddPlayersPiece() {
		Move move = mock(Move.class);
		when(move.getPiece()).thenReturn(PieceFactory.createPiece(Name.ONE, 1));
		List<Move> moveHistory = new ArrayList<Move>();
		moveHistory.add(move);
		when(player1.getMoveHistory()).thenReturn(moveHistory);
		state.undoLastMove(1);
		verify(player1).addPiece(Name.ONE);
	}
	
}
