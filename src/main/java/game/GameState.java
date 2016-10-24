package game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class GameState {

	private final Board board;
	private final Map<Integer, Player> players;
	
	public GameState(Board board, List<Player> players) {
		this.board = board;
		this.players = new HashMap<Integer, Player>();
		for (Player p : players) {
			this.players.put(p.getId(), p);
		}
	}
	
	private GameState(Board board, Map<Integer, Player> players) {
		this.board = board;
		this.players = players;
	}
	
	/**
	 * Create an equal copy of the state that can be modified without disturbing the original state
	 * Useful to give to controllers so they can play around with the state
	 * @return
	 */
	public GameState deepCopy() {
		Map<Integer, Player> playersCopy = new HashMap<Integer, Player>();
		for (Map.Entry<Integer, Player> entry : players.entrySet()) {
			playersCopy.put(entry.getKey(), entry.getValue().deepCopy());
		}
		return new GameState(board.deepCopy(), playersCopy);
	}
	
	public Player getPlayer(int playerId) {
		return players.get(playerId);
	}
}
