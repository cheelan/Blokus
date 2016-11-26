package controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.Controller;
import game.GameScorer;
import game.GameState;
import game.Move;
import lombok.Data;
import lombok.Setter;

public class MinimaxController implements Controller {

	@Setter private int minimaxDepth = 3;
	
	@Setter private GameScorer scorer = new GameScorer();
	@Setter private MoveGenerator moveGenerator = new MoveGenerator();
	
	private int myPlayerId;
	private int opponentPlayerId;
	
	@Override
	public Move decide(GameState state, int playerId) {
		// Assume only two players
		if (playerId == 1) {
			myPlayerId = 1;
			opponentPlayerId = 2;
		} else {
			myPlayerId = 2;
			opponentPlayerId = 1;
		}
		// As the board gets more crowded, the branching factor goes down, so greater depths are reachable
		if (state.getPlayer(myPlayerId).getMoveHistory().size() > 5) {
			minimaxDepth = 5;
		}
		else if (state.getPlayer(myPlayerId).getMoveHistory().size() > 7) {
			minimaxDepth = 9;
		}
		else if (state.getPlayer(myPlayerId).getMoveHistory().size() > 8) {
			minimaxDepth = 11;
		}
		int nextMoveIndex = state.getPlayer(playerId).getMoveHistory().size();
		long start = System.currentTimeMillis();
		Node bestState = minimax(state, minimaxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		System.out.println("Projected score difference: " + bestState.getScore());
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start));
		if (bestState.getState().getPlayer(myPlayerId).getMoveHistory().size() > nextMoveIndex) {
			Move move = bestState.getState().getPlayer(myPlayerId).getMoveHistory().get(nextMoveIndex);
			return move;
		}
		return null;
		
	}
	
	public Node minimax(GameState state, int depth, int alpha, int beta, boolean isMaximizer) {
		if (depth == 0) {
			int player1Score = scorer.calculateScore(state, myPlayerId);
			int player2Score = scorer.calculateScore(state, opponentPlayerId);
			int score = player1Score - player2Score;
			return new Node(state, score);
		}
		// Might be able to optimize without deep copy: http://gamedev.stackexchange.com/questions/79879/how-to-calculate-move-while-avoiding-deep-copy?rq=1
		if (isMaximizer) {
			List<Move> moves = moveGenerator.findAllValidMoves(state, myPlayerId);
			// Optimization: Alpha beta pruning is more effective when stronger moves are considered first. 
			// As a heuristic, it's better to move bigger pieces first
			Collections.sort(moves, new MoveComparator());
			int bestValue = Integer.MIN_VALUE;
			Node bestState = new Node(state, bestValue);
			for (int i = 0; i < moves.size(); i++) {
				Move move = moves.get(i);
				state.applyMove(myPlayerId, move);

				Node s = minimax(state, depth - 1, alpha, beta, false);
				int score = s.getScore();
				if (score > bestValue) {
					bestValue = score;
					// TODO Still making unnecessary deep copies. We could store the best move instead of the best state
					bestState = new Node(s.getState().deepCopy(), score);
				}
				// Randomize ties
				else if (score == bestValue) {
					double v = i + 1.0;
					if (Math.random() < 1.0 / v) {
						bestState = new Node(s.getState().deepCopy(), score);
					}
				}
				state.undoLastMove(myPlayerId);
				alpha = Math.max(alpha, score);
				if (beta <= alpha) {
					break;
				}
			}
			// Even though the maximizer has no children, the minimizer may still be able to play moves
			if (moves.size() == 0) {
				return minimax(state, depth - 1, alpha, beta, false);
			}
			return bestState;
		} else {
			List<Move> moves = moveGenerator.findAllValidMoves(state, opponentPlayerId);
			// Optimization: Alpha beta pruning is more effective when stronger moves are considered first. 
			// As a heuristic, it's better to move bigger pieces first
			Collections.sort(moves, new MoveComparator());
			int bestValue = Integer.MAX_VALUE;
			Node bestState = new Node(state, bestValue);
			for (int i = 0; i < moves.size(); i++) {
				Move move = moves.get(i);
				state.applyMove(opponentPlayerId, move);

				Node s = minimax(state, depth - 1, alpha, beta, true);
				int score = s.getScore();
				if (score < bestValue) {
					bestValue = score;
					bestState = new Node(s.getState().deepCopy(), score);
				}
				// Randomize ties
				else if (score == bestValue) {
					double v = i + 1.0;
					if (Math.random() < 1.0 / v) {
						bestState = new Node(s.getState().deepCopy(), score);
					}
				}
				state.undoLastMove(opponentPlayerId);
				beta = Math.min(beta, score);
				if (beta <= alpha) {
					break;
				}
			}
			// Even though the maximizer has no children, the minimizer may still be able to play moves
			if (moves.size() == 0) {
				return minimax(state, depth - 1, alpha, beta, true);
			}
			return bestState;
		}
		
	}
	
	@Data
	private static class Node {
		private final GameState state;
		private final int score;
	}
	
	private static class MoveComparator implements Comparator<Move> {
		
		@Override
		public int compare(Move m1, Move m2) {
			return m1.getPiece().getValue() > m2.getPiece().getValue() ? -1 : m1.getPiece().getValue() < m2.getPiece().getValue() ? 1 : 0;
		}
	}
}
