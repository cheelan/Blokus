package game;

public interface Controller {

	public Move decide(GameState state, int playerId);
}
