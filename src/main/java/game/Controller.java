package game;

import java.util.Collection;

public interface Controller {

	public Move decide(Board board, Collection<Piece> pieces);
}
