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
			playersCopy.put(entry.getKey().intValue(), entry.getValue().deepCopy());
		}
		return new GameState(board.deepCopy(), playersCopy);
	}
	
	public Player getPlayer(int playerId) {
		return players.get(playerId);
	}
	
	/**
	 * Apply a move to the game state
	 * @param playerId
	 * @param move
	 */
	public void applyMove(int playerId, Move move) {
		board.applyMove(move);
		players.get(playerId).getMoveHistory().add(move);
		players.get(playerId).removePiece(move.getPiece().getName());
	}
	
	/**
	 * Pop playerId's most recent move off the game state.
	 * Helpful for controllers that want to experiment with moves without using a copy
	 * @param playerId
	 */
	public void undoLastMove(int playerId) {
		List<Move> moveHistory = players.get(playerId).getMoveHistory();
		Move removed = moveHistory.get(moveHistory.size() - 1);
		moveHistory.remove(moveHistory.size() - 1);
		board.removeMove(removed);
		players.get(playerId).addPiece(removed.getPiece().getName());
	}
}
