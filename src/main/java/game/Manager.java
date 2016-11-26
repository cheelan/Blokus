package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.CornerEvaluatorController;
import controller.GreedyController;
import controller.MinimaxController;
import controller.RandomController;

public class Manager {
	
	GameRecorder recorder = new GameRecorder();
	
	/**
	 * Steps through one round of player turns. Returns true iff the game is over
	 * @return true iff the game is over, false otherwise
	 */
	public boolean go(GameState state) {
		int movesPlayed = 0;
		for (Player player : state.getPlayers().values()) {
			// Player.getcontroller().play(state) ?
			Move nextMove = player.play(state);
			if (nextMove != null) {
				state.getBoard().applyMove(nextMove);
				player.removePiece(nextMove.getPiece().getName());
				//recorder.recordState(player.getId(), state);
				movesPlayed++;
			}
		}
		//System.out.println(state.getBoard());
		GameScorer scorer = new GameScorer();
		//System.out.println("Player1 Score: " + scorer.calculateScore(state.getPlayer(1).getMoveHistory()) + " Player 2 Score: " + scorer.calculateScore(state.getPlayer(2).getMoveHistory()));
		return movesPlayed == 0;
	}
	
	/**
	 * Simulate an entire game
	 * @return Map of each player id and their score
	 */
	public Map<Integer, Integer> playEntireGame(GameState state) {
		boolean isGameOver = false;
		while(!isGameOver) {
			isGameOver = go(state);
		}
		GameScorer scorer = new GameScorer();
		Map<Integer, Integer> gameResult = new HashMap<Integer, Integer>();
		for (Player player : state.getPlayers().values()) {
			int score = scorer.calculateScore(state, player.getId());
			gameResult.put(player.getId(), score);
			System.out.println(player.getId() + " score " + score);
		}
		int winner = 0;
		if (gameResult.get(1) < gameResult.get(2)) {
			winner = 2;
		} else if (gameResult.get(2) < gameResult.get(1)) {
			winner = 1;
		}
		System.out.println(winner);
		//recorder.recordResults(winner);
		
		return gameResult;
	}
	
	public static void main(String[] args) {
		System.out.println("Start");
		Player player1 = new Player(1, Piece.getAllPieces(1), new GreedyController());
		Player player2 = new Player(2, Piece.getAllPieces(2), new MinimaxController());
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);

		Board board = new Board(new MoveValidator());
		GameState initialState = new GameState(board, players);
		Manager manager = new Manager();
		manager.playEntireGame(initialState);
		System.out.println("GAME OVER");
		GameScorer scorer = new GameScorer();
		for (Player player : players) {
			System.out.println("Player " + player.getId() + " Score = " + scorer.calculateScore(initialState, player.getId()));
		}
	}
	
}
