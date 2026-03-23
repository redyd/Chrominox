package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChrominoTest {

	/*
	 * Test de equals()
	 */
	
	@Test
	void compareTwoChrominoEquals() {
		var c1 = new Chromino(TileType.RED, TileType.RED, TileType.CYAN);
		var c2 = new Chromino(TileType.RED, TileType.RED, TileType.CYAN);
		
		assertEquals(c1, c2);
	}
	
	@Test
	void compareTwoChrominoEqualsButReversed() {
		var c1 = new Chromino(TileType.RED, TileType.RED, TileType.CYAN);
		var c2 = new Chromino(TileType.CYAN, TileType.RED, TileType.RED);
		
		assertEquals(c1, c2);
	}
	
	@Test
	void compareTwoChrominoDifferents() {
		var c1 = new Chromino(TileType.RED, TileType.CYAN, TileType.CYAN);
		var c2 = new Chromino(TileType.CYAN, TileType.RED, TileType.RED);
		
		assertNotEquals(c1, c2);
	}
	
	@Test
	void should_not_be_equals() {
		var c1 = new Chromino(TileType.RED, TileType.CYAN, TileType.CYAN);
		
		assertNotEquals(c1, null);
		assertNotEquals(c1, new FakeBag());
	}
	
	@Test
	void should_get_same_hash_code_when_equals() {
		var c1 = new Chromino(TileType.RED, TileType.RED, TileType.CYAN);
		var c2 = new Chromino(TileType.RED, TileType.RED, TileType.CYAN);
		
		assertEquals(c1.hashCode(), c2.hashCode());
	}
	
	@Test
	void should_get_same_hash_code_when_equals_but_reversed() {
		var c1 = new Chromino(TileType.RED, TileType.RED, TileType.CYAN);
		var c2 = new Chromino(TileType.CYAN, TileType.RED, TileType.RED);
		
		assertEquals(c1.hashCode(), c2.hashCode());
	}
	
	/*
	 * Test de reversed
	 */
	
	@Test
	void should_reverse_chromino_when_different_tiles() {
		var actual = new Chromino(TileType.RED, TileType.CYAN, TileType.GREEN);
		var expected = new Chromino(TileType.GREEN, TileType.CYAN, TileType.RED);
		
		assertEquals(actual, expected);
	}
	
	/*
	 * Test de isCameleon()
	 */
	
	@Test
	void should_be_cameleon_when_cameleon_in_center_and_unique() {
		var actual = new Chromino(TileType.RED, TileType.CAMELEON, TileType.GREEN);
		
		assertTrue(actual.isCameleon());
	}
	
	@Test
	void should_not_be_cameleon_when_cameleon_in_center_but_not_unique() {
		var actual = new Chromino(TileType.RED, TileType.CAMELEON, TileType.CAMELEON);
		
		assertFalse(actual.isCameleon());
	}
	
	@Test
	void should_not_be_cameleon_when_cameleon_not_in_center_but_unique() {
		var actual = new Chromino(TileType.RED, TileType.GREEN, TileType.CAMELEON);
		
		assertFalse(actual.isCameleon());
	}
	
	/*
	 * Test de hasDifferentTiles()
	 */
	
	@Test
	void should_be_false_when_similar_tiles() {
		var c1 = new Chromino(TileType.RED, TileType.CYAN, TileType.CYAN);
		assertFalse(c1.hasDifferentTiles());
	}
	
	@Test
	void should_be_true_when_different_tiles() {
		var c1 = new Chromino(TileType.RED, TileType.CYAN, TileType.GREEN);
		assertTrue(c1.hasDifferentTiles());
	}
	
	/*
	 * Test de getColorAt()
	 */
	
	@Test
	void should_get_color_when_in_index() {
		var c1 = new Chromino(TileType.RED, TileType.CYAN, TileType.CYAN);
		
		assertEquals(c1.getColorAt(0), TileType.RED.toString());
	}
	
	@Test
	void should_throw_exception_when_init_with_null() {
		assertThrows(IllegalArgumentException.class, () -> new Chromino(null, null, TileType.CYAN));
	}
	
	@Test
	void should_return_correct_string() {
		var c1 = new Chromino(TileType.RED, TileType.CYAN, TileType.CYAN);
		
		assertEquals("Chromino [part1=RED, part2=CYAN, part3=CYAN]", c1.toString());
	}
	
	@Test
	void should_return_1_when_points_on_monochromino() {
		assertEquals(1, new Chromino(TileType.CYAN, TileType.CYAN, TileType.CYAN).points());
	}
	
	@Test
	void should_return_2_when_points_on_duochromino() {
		assertEquals(2, new Chromino(TileType.CYAN, TileType.RED, TileType.CYAN).points());
	}

	@Test
	void should_return_3_when_points_on_trichromino() {
		assertEquals(3, new Chromino(TileType.CYAN, TileType.CAMELEON, TileType.RED).points());
	}

}
