package game;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import game.Piece.Name;
public class PlayerTest {

	private Player player;
	private Controller controller;
	private Map<Name, Piece> pieces;
	
	
	@Before
	public void init() {
		controller = mock(Controller.class);
		pieces = new HashMap<Name, Piece>();
		pieces.put(Name.F, mock(Piece.class));
		player = new Player(1, pieces, controller);
	}
	
	@Test
	public void testRemovePiece() {
		player.removePiece(Name.F);
		Assert.assertEquals(0, pieces.size());
	}
	
	@Test
	public void playShouldReturnControllersMove() {
		Board board = mock(Board.class);
		Move actual = new Move(mock(Piece.class), 0, 0);
		when(controller.decide(board, pieces.values())).thenReturn(actual);
		Assert.assertEquals(actual, player.play(board));
	}
	
	@Test
	public void movesShouldBeAppendedToHistory() {
		Board board = mock(Board.class);
		Move move = new Move(mock(Piece.class), 0, 0);
		when(controller.decide(board, pieces.values())).thenReturn(move);
		player.play(board);
		assertEquals(1, player.getMoveHistory().size());
		assertEquals(move, player.getMoveHistory().get(0));
	}
	
	@Test
	public void nullMovesShouldNotBeInHistory() {
		Board board = mock(Board.class);
		when(controller.decide(board, pieces.values())).thenReturn(null);
		player.play(board);
		assertEquals(0, player.getMoveHistory().size());
	}
}
