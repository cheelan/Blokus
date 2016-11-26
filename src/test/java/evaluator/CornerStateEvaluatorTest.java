package evaluator;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import controller.MoveGenerator;
import game.GameScorer;
import game.GameState;
import game.Move;

public class CornerStateEvaluatorTest {

	private CornerStateEvaluator evaluator;
	private GameScorer scorer;
	private MoveGenerator moveGenerator;
	private GameState state;
	
	@Before
	public void init() {
		scorer = mock(GameScorer.class);
		moveGenerator = mock(MoveGenerator.class);
		evaluator = new CornerStateEvaluator();
		evaluator.setMoveGenerator(moveGenerator);
		evaluator.setScorer(scorer);
	}
	
	@Test
	public void higherScoresShouldBeBetterThanLowerScores() {
		when(scorer.calculateScore(any(), eq(1))).thenReturn(2);
		when(scorer.calculateScore(any(), eq(2))).thenReturn(1);
		assert(evaluator.evaluate(state, 1) > evaluator.evaluate(state, 2));
	}
	
	@Test
	public void tiedScoresBrokenByNumberOfMoves() {
		when(scorer.calculateScore(any(), eq(1))).thenReturn(1);
		when(scorer.calculateScore(any(), eq(2))).thenReturn(1);
		when(moveGenerator.findAllValidMoves(any(), eq(1))).thenReturn(Arrays.asList(mock(Move.class)));
		when(moveGenerator.findAllValidMoves(any(), eq(2))).thenReturn(new ArrayList<Move>());
		assert(evaluator.evaluate(state, 1) > evaluator.evaluate(state, 2));
	}
}
