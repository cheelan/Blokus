package game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.Piece.Name;
import game.Piece.PieceFactory;

public class GameScorerTest {

	private GameScorer scorer;
	
	@Before
	public void init() {
		scorer = new GameScorer();
	}
	
	@Test
	public void scoreShouldEqualSumOfUnplayedPieces() {
		List<Move> moveHistory = new ArrayList<Move>();
		moveHistory.add(new Move(PieceFactory.createPiece(Name.I4, 1), 0, 0));
		moveHistory.add(new Move(PieceFactory.createPiece(Name.F, 1), 0, 0));
		moveHistory.add(new Move(PieceFactory.createPiece(Name.V3, 1), 0, 0));
		assertEquals(12 + GameScorer.INITIAL_SCORE, scorer.calculateScore(moveHistory));
	}
	
	@Test
	public void shouldApplyBonus_whenAllPiecesPlayed() {
		List<Move> moveHistory = new ArrayList<Move>();
		Move move = new Move(new Piece(0, Name.F, null, true, true), 0, 0);
		for (int i = 0; i < Piece.Name.values().length; i++) {
			moveHistory.add(move);
		}
		assertEquals(GameScorer.ALL_PIECES_USED_BONUS + GameScorer.INITIAL_SCORE, scorer.calculateScore(moveHistory));
	}
	
	@Test
	public void shouldApplyBonus_whenAllPiecesPlayedAndLastIsMonimo() {
		List<Move> moveHistory = new ArrayList<Move>();
		Move move = new Move(PieceFactory.createPiece(Name.ONE, 1), 0, 0);
		for (int i = 0; i < Piece.Name.values().length; i++) {
			moveHistory.add(move);
		}
		assertEquals(moveHistory.size() + GameScorer.ALL_PIECES_USED_BONUS 
				                        + GameScorer.LAST_PIECE_MONIMO_BONUS 
				                        + GameScorer.INITIAL_SCORE, scorer.calculateScore(moveHistory));
	}
}
