package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Manager {

	private Board board;
	private List<Player> players;
	
	public Manager(List<Player> players, Board board) {
		this.players = players;
		this.board = board;
	}
	
	/**
	 * Steps through one round of player turns. Returns true iff the game is over
	 * @return true iff the game is over, false otherwise
	 */
	public boolean go() {
		int movesPlayed = 0;
		for (Player player : players) {
			Move nextMove = player.play(board);
			if (nextMove != null) {
				board.applyMove(nextMove);
				player.removePiece(nextMove.getPiece().getName());
				movesPlayed++;
			}
		}
		return movesPlayed == 0;
	}
	
	public static void main(String[] args) {
		System.out.println("Start");
		Player player1 = new Player(1, Piece.getAllPieces(1), new RandomController());
		Player player2 = new Player(2, Piece.getAllPieces(2), new RandomController());
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		Board board = new Board(new MoveValidator());
		Manager manager = new Manager(players, board);
		Scanner scan = new Scanner(System.in);
		boolean isGameOver = false;
		while(!isGameOver) {
			String s = scan.next();
			isGameOver = manager.go();
			System.out.println("Move " + board.getMoves());
			System.out.println(board);
		}
		scan.close();
		System.out.println("GAME OVER");
		// TODO current rules are that you have to start at a corner, should be a specific corner
	}
	
}
