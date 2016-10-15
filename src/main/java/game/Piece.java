package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Piece {
	
	public enum Name {
		ONE, TWO, V3, I3, O, T4, L4, I4, Z4, F, X, P, W, Z5, Y, V5, T;
	}
	
	public static class PieceFactory {
		public static Piece createPiece(Name name, int id) {
			switch (name) {
				case ONE: return new Piece(1, name, new int[][]{{id}}, false, false);
				case TWO: return new Piece(2, name, new int[][]{{id, id}}, false, true);
				case V3: return new Piece(3, name, new int[][]{{id, id}, {0, id}}, true, true);
				case I3: return new Piece(3, name, new int[][]{{id, id, id}}, false, true);
				case O: return new Piece(4, name, new int[][]{{id, id}, {id, id}}, false, false);
				case T4: return new Piece(4, name, new int[][]{{0, id, 0}, {id, id, id}}, true, true);
				case L4: return new Piece(4, name, new int[][]{{0, 0, id}, {id, id, id}}, true, true);
				case I4: return new Piece(4, name, new int[][]{{id, id, id, id}}, false, true);
				case Z4: return new Piece(4, name, new int[][]{{0, id, id}, {id, id, 0}}, true, true);
				case F: return new Piece(5, name, new int[][]{{0, id, id}, {id, id, 0}, {0, id, 0}}, true, true);
				case X: return new Piece(5, name, new int[][]{{0, id, 0}, {id, id, id}, {0, id, 0}}, false, false);
				case P: return new Piece(5, name, new int[][]{{id, 0}, {id, id}, {id, id}}, true, true);
				case W: return new Piece(5, name, new int[][]{{0, id, id}, {id, id, 0}, {id, 0, 0}}, true, true);
				case Z5: return new Piece(5, name, new int[][]{{0, 0, id}, {id, id, id}, {id, 0, 0}}, true, true); 
				case Y: return new Piece(5, name, new int[][]{{0, id, 0, 0}, {id, id, id, id}}, true, true);
				case V5: return new Piece(5, name, new int[][]{{id, 0, 0}, {id, 0, 0}, {id, id, id}}, true, true);
				case T: return new Piece(5, name, new int[][]{{0, id, 0}, {0, id, 0}, {id, id, id}}, true, true);
				default: throw new IllegalArgumentException("Cannot create piece of name " + name);	
			}
			// https://c2strategy.wordpress.com/2011/04/10/piece-names/

			

//			pieces[9] = new Piece(5, new int[][]{{id, 0, 0, 0}, {id, id, id, id}}, true, true);
//			pieces[10] = new Piece(5, new int[][]{{0, id, 0}, {0, id, 0}, {id, id, id}}, true, true);
//			pieces[12] = new Piece(5, new int[][]{{0, id, id, id}, {id, id, 0, 0}}, true, true);
//			pieces[14] = new Piece(5, new int[][]{{id, id, id, id, id}}, false, true);
//			pieces[17] = new Piece(5, new int[][]{{id, id}, {id, 0}, {id, id}}, true, true);
		}
	}

	@Getter private final int[][] shape;
	@Getter private final int value;
	@Getter private final boolean isFlippable;
	@Getter private final boolean isRotatable;
	@Getter private final Name name;
	
	public static Map<Name, Piece> getAllPieces(int id) {
		Map<Name, Piece> allPieces = new HashMap<Name, Piece>();
		for (Name name : Name.values()) {
			allPieces.put(name, PieceFactory.createPiece(name, id));
		}
		return allPieces;
	}
	
	public Piece(int value, Name name, int[][] shape, boolean isFlippable, boolean isRotatable) {
		this.value = value;
		this.name = name;
		this.shape = shape;
		this.isFlippable = isFlippable;
		this.isRotatable = isRotatable;
	}
	
	/**
	 * Rotate a piece 90 degrees clockwise
	 * @return
	 */
	public Piece rotate() {
		if (!isRotatable) {
			return this;
		}
		int m = shape.length;
		int n = shape[0].length;
		int[][] newPiece = new int[n][m];
		for (int r = 0; r < m; r++) {
	        for (int c = 0; c < n; c++) {
	            newPiece[c][m-1-r] = shape[r][c];
	        }
		}
		return new Piece(value, name, newPiece, isFlippable, isRotatable);
	}
	
	public Piece flip() {
		if (!isFlippable) {
			return this;
		}
		int j = shape.length;
		int i = shape[0].length;
		int[][] newPiece = new int[j][i];
		for (int y = 0; y < j; y++) {
			for (int x = 0; x < i; x++) {
				newPiece[y][i - 1 - x] = shape[y][x];
			}
		}
		return new Piece(value, name, newPiece, isFlippable, isRotatable);
	}
	
	public Set<Piece> getArrangements() {
		Set<Piece> arrangements = new HashSet<Piece>();
		if (isRotatable) {
			Piece p = new Piece(value, name, shape, isFlippable, isRotatable);
			for (int i = 0; i < 4; i++) {
				arrangements.add(p);
				if (isFlippable) {
					arrangements.add(p.flip());
				}
				p = p.rotate();
			}
		} else {
			Piece p = new Piece(value, name, shape, isFlippable, isRotatable);
			arrangements.add(p);
			if (isFlippable) {
				arrangements.add(p.flip());
			}
		}
		return arrangements;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < shape.length; i++) {
			builder.append("[");
			for (int j = 0; j < shape[0].length; j++) {
				builder.append(shape[i][j]+ ",");
			}
			builder.append("]\n");
		}
		return builder.toString();
	}
	
}


