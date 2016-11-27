package evaluator;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import game.GameScorer;
import game.GameState;

public class ScoreStateEvaluatorTest {

	private ScoreStateEvaluator evaluator;
	private GameScorer scorer;
	private GameState state;
	
	@Before
	public void init() {
		scorer = mock(GameScorer.class);
		state = mock(GameState.class);
		evaluator = new ScoreStateEvaluator();
		evaluator.setScorer(scorer);
	}
	
	@Test
	public void higherScoresShouldBeBetterThanLowerScores() {
		when(scorer.calculateScore(any(), eq(1))).thenReturn(2);
		when(scorer.calculateScore(any(), eq(2))).thenReturn(1);
		assert(evaluator.evaluate(state, 1) > evaluator.evaluate(state, 2));
	}
	
	@Test
	public void lowerScoresShouldBeWorseThanHigherScores() {
		when(scorer.calculateScore(any(), eq(1))).thenReturn(1);
		when(scorer.calculateScore(any(), eq(2))).thenReturn(2);
		assert(evaluator.evaluate(state, 1) < evaluator.evaluate(state, 2));
	}
}
