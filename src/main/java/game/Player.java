package game;

import java.util.Map;

public class Player {
	private Map<Piece.Name, Piece> pieces;
	private Controller controller;
	
	public Player(int id, Map<Piece.Name, Piece> pieces, Controller controller) {
		this.pieces = pieces;
		this.controller = controller;
	}
	
	public void removePiece(Piece.Name piece) {
		pieces.remove(piece);
	}
	
	public Move play(Board board) {
		return controller.decide(board, pieces.values());		
	}
}
