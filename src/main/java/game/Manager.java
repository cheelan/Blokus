package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.GreedyController;
import controller.RandomController;

public class Manager {

	private GameState state;
	
	public Manager(GameState state) {
		this.state = state;
	}
	
	/**
	 * Steps through one round of player turns. Returns true iff the game is over
	 * @return true iff the game is over, false otherwise
	 */
	public boolean go() {
		int movesPlayed = 0;
		for (Player player : state.getPlayers().values()) {
			// Player.getcontroller().play(state) ?
			Move nextMove = player.play(state);
			if (nextMove != null) {
				state.getBoard().applyMove(nextMove);
				player.removePiece(nextMove.getPiece().getName());
				movesPlayed++;
			}
		}
		GameScorer scorer = new GameScorer();
		System.out.println("Player1 Score: " + scorer.calculateScore(state.getPlayer(1).getMoveHistory()) + " Player 2 Score: " + scorer.calculateScore(state.getPlayer(2).getMoveHistory()));
		return movesPlayed == 0;
	}
	
	/**
	 * Simulate an entire game
	 * @return Map of each player and their score
	 */
	public Map<Player, Integer> playEntireGame() {
		boolean isGameOver = false;
		while(!isGameOver) {
			isGameOver = go();
		}
		GameScorer scorer = new GameScorer();
		Map<Player, Integer> gameResult = new HashMap<Player, Integer>();
		for (Player player : state.getPlayers().values()) {
			 gameResult.put(player,scorer.calculateScore(player.getMoveHistory()));
		}
		return gameResult;
	}
	
	public static void main(String[] args) {
		System.out.println("Start");
		Player player1 = new Player(1, Piece.getAllPieces(1), new GreedyController());
		Player player2 = new Player(2, Piece.getAllPieces(2), new RandomController());
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);

		Board board = new Board(new MoveValidator());
		GameState initialState = new GameState(board, players);
		Manager manager = new Manager(initialState);
		Scanner scan = new Scanner(System.in);
		boolean isGameOver = false;
		while(!isGameOver) {
			//String s = scan.next();
			isGameOver = manager.go();
			System.out.println("Move " + board.getMoves());
			System.out.println(board);
		}
		scan.close();
		System.out.println("GAME OVER");
		GameScorer scorer = new GameScorer();
		for (Player player : players) {
			System.out.println("Player " + player.getId() + " Score = " + scorer.calculateScore(player.getMoveHistory()));
		}
	}
	
}
