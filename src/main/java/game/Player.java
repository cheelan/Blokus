package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Piece.PieceFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Player {
	
	private Controller controller;
	@Getter private Map<Piece.Name, Piece> pieces;
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
	
	public void addPiece(Piece.Name piece) {
		pieces.put(piece, PieceFactory.createPiece(piece, id));
	}
	
	public Move play(GameState state) {
		Move move = controller.decide(state, id);
		if (move != null) {
			moveHistory.add(move);
		}
		return move;
	}
	
	public Player deepCopy() {
		List<Move> moveHistoryCopy = new ArrayList<Move>();
		moveHistoryCopy.addAll(moveHistory);
		
		Map<Piece.Name, Piece> piecesCopy = new HashMap<Piece.Name, Piece>();
		piecesCopy.putAll(pieces); // Shallow copy is ok here because the Piece objects themselves won't be modified
		
		Player copy = new Player(id, piecesCopy, controller);
		copy.moveHistory = moveHistoryCopy;
		return copy;
	}
}
