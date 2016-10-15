package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;

public class Player {
	private Map<Piece.Name, Piece> pieces;
	private Controller controller;
	@Getter private int id;
	@Getter private List<Move> moveHistory = new ArrayList<Move>();
	
	public Player(int id, Map<Piece.Name, Piece> pieces, Controller controller) {
		this.pieces = pieces;
		this.controller = controller;
		this.id = id;
	}
	
	public void removePiece(Piece.Name piece) {
		pieces.remove(piece);
	}
	
	public Move play(Board board) {
		Move move = controller.decide(board, pieces.values());
		if (move != null) {
			moveHistory.add(move);
		}
		return move;
	}
}
