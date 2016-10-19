package game;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;


public class GameStateTest {
	
	private GameState state;
	private Board board;
	private Player player1;
	private Player player2;

	@Before
	public void init() {
		board = mock(Board.class);
		player1 = mock(Player.class);
		player2 = mock(Player.class);
		state = new GameState(board, player1, player2);
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
		assert(copy.getPlayer1() == copiedPlayer1);
		assert(copy.getPlayer2() == copiedPlayer2);

	}
}
