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
		GameState state = mock(GameState.class);
		Move actual = new Move(mock(Piece.class), 0, 0);
		when(controller.decide(state, 1)).thenReturn(actual);
		Assert.assertEquals(actual, player.play(state));
	}
	
	@Test
	public void movesShouldBeAppendedToHistory() {
		GameState state = mock(GameState.class);
		Move move = new Move(mock(Piece.class), 0, 0);
		when(controller.decide(state, 1)).thenReturn(move);
		player.play(state);
		assertEquals(1, player.getMoveHistory().size());
		assertEquals(move, player.getMoveHistory().get(0));
	}
	
	@Test
	public void nullMovesShouldNotBeInHistory() {
		GameState state = mock(GameState.class);
		when(controller.decide(state, 1)).thenReturn(null);
		player.play(state);
		assertEquals(0, player.getMoveHistory().size());
	}
	
	@Test
	public void copiesShouldInitiallyBeEqual() {
		Player copy = player.deepCopy();
		assert(copy.equals(player));
	}
	
	@Test
	public void copiesShouldHaveNewHistoryInstance() {
		Player copy = player.deepCopy();
		copy.getMoveHistory().add(null);
		assert(!copy.getMoveHistory().equals(player.getMoveHistory()));
	}
	
	@Test
	public void copiesShouldHaveDifferentPiecesInstance() {
		Player copy = player.deepCopy();
		copy.removePiece(Name.F);
		assert(!copy.getPieces().equals(player.getPieces()));
	}
}
