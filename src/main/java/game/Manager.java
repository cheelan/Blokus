package game;

import java.util.Scanner;

public class Manager {

	public enum GameState {
		PLAYER1_TURN,
		PLAYER2_TURN,
		GAME_OVER;
	}
	
	private GameState state = GameState.PLAYER1_TURN;
	private Player player1;
	private Player player2;
	private Board board;
	
	public Manager(Player player1, Player player2, Board board) {
		this.player1 = player1;
		this.player2 = player2;
		this.board = board;
	}
	
	public void go() {
		Move nextMove = null;
		switch (state) {
			case PLAYER1_TURN:
				nextMove = player1.play(board);
				break;
			case PLAYER2_TURN:
				nextMove = player2.play(board);
				break;
			case GAME_OVER:
				return;
		}
		try {
			board.applyMove(nextMove);
			// Remove nextMove from player
			// TODO Check game over condition
			if (state == GameState.PLAYER1_TURN) {
				player1.removePiece(nextMove.getPiece().getName());
				state = GameState.PLAYER2_TURN;
			} else {
				player2.removePiece(nextMove.getPiece().getName());
				state = GameState.PLAYER1_TURN;
			}
		} catch (IllegalArgumentException e) {
			// TODO Display some feedback for the player
		}		
	}
	
	public static void main(String[] args) {
		System.out.println("Start");
		Player player1 = new Player(1, Piece.getAllPieces(1), new RandomController());
		Player player2 = new Player(2, Piece.getAllPieces(2), new RandomController());
		Board board = new Board(new MoveValidator());
		Manager manager = new Manager(player1, player2, board);
		Scanner scan = new Scanner(System.in);
		for (int i = 0; i < 12; i++) {
			String s = scan.next();
			manager.go();
			System.out.println("Move " + board.getMoves());
			System.out.println(board);
		}
		// TODO current rules are that you have to start at a corner, should be a specific corner
	}
	
}
