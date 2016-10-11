package game;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import game.Piece.Name;
import game.Piece.PieceFactory;

public class PieceTest {

	@Test
	public void testRotate() {
		Piece p = PieceFactory.createPiece(Name.T, 1);
		
		p = p.rotate();
		Assert.assertArrayEquals(new int[][]{{1, 0, 0}, {1, 1, 1}, {1, 0, 0}}, p.getShape());
		
		p = p.rotate();
		Assert.assertArrayEquals(new int[][]{{1, 1, 1}, {0, 1, 0}, {0, 1, 0}}, p.getShape());
		
		p = p.rotate();
		Assert.assertArrayEquals( new int[][]{{0, 0, 1}, {1, 1, 1}, {0, 0, 1}}, p.getShape());
		
		p = p.rotate();
		Assert.assertArrayEquals(new int[][]{{0, 1, 0}, {0, 1, 0}, {1, 1, 1}}, p.getShape());
	}
	
	@Test
	public void testFlip() {
		Piece p = PieceFactory.createPiece(Name.F, 1);
		
		p = p.flip();
		Assert.assertArrayEquals(new int[][]{{1, 1, 0}, {0, 1, 1}, {0, 1, 0}}, p.getShape());
		
		p = p.flip();
		Assert.assertArrayEquals(new int[][]{{0, 1, 1}, {1, 1, 0}, {0, 1, 0}}, p.getShape());
		
	}
	
	@Test
	public void testGetArrangements_whenPieceIsNotRotatableOrFlippable() {
		Piece p = PieceFactory.createPiece(Name.O, 1);
		Set<Piece> expected = new HashSet<Piece>();
		expected.add(p);
		Set<Piece> actual = p.getArrangements();
		Assert.assertEquals(expected, actual);
		
	}
	@Test
	public void testGetArrangements_whenPieceIsRotatable() {
		Piece p = PieceFactory.createPiece(Name.I3, 1);
		Assert.assertEquals(2, p.getArrangements().size());
	}
	
	@Test
	public void testGetArrangements_whenPieceIsRotatableAndFlippable() {
		Piece p = PieceFactory.createPiece(Name.F, 1);
		Assert.assertEquals(8, p.getArrangements().size());
	}
}
