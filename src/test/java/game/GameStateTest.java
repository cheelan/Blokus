package game;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


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
		player2 = mock(Player.class);
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
}
