package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChroMosaicTest {

	@Test
	void should_bruteadd_when_no_collide() {
		var mosaic = new ChroMosaic();

		assertTrue(mosaic.bruteAdd(Values.CHROMINO.getFirst(), Values.COORD.getFirst()));
	}

	@Test
	void should_not_bruteAdd_when_collide() {
		var mosaic = new ChroMosaic();

		assertTrue(mosaic.bruteAdd(Values.CHROMINO.getFirst(),
				new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0))));

		assertFalse(mosaic.bruteAdd(Values.CHROMINO.getLast(),
				new Coordinate(new Point(0, 1), new Point(0, 0), new Point(0, -1))));
	}

	@Test
	void should_not_put_when_superposition() {
		var mosaic = new ChroMosaic();

		mosaic.bruteAdd(Values.CHROMINO.getFirst(), new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0)));
		assertEquals(Validation.ON_OTHER_CHROMINO, mosaic.put(Values.CHROMINO.getLast(),
				new Coordinate(new Point(0, 1), new Point(0, 0), new Point(0, -1))));

		assertNotEquals(Validation.ON_OTHER_CHROMINO, mosaic.put(Values.CHROMINO.getLast(),
				new Coordinate(new Point(1, 1), new Point(1, 1), new Point(1, -1))));
	}

	@Test
	void should_not_add_when_less_than_two_contact() {
		var mosaic = new ChroMosaic();
		mosaic.bruteAdd(Values.CHROMINO.getFirst(), new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0)));
		mosaic.put(Values.CHROMINO.getLast(), new Coordinate(new Point(-2, 0), new Point(-3, 0), new Point(-4, 0)));

		assertEquals(Validation.AT_LEAST_TWO, mosaic.put(Values.CHROMINO.get(2),
				new Coordinate(new Point(1, 1), new Point(1, 2), new Point(1, 3))));

		assertNotEquals(Validation.AT_LEAST_TWO, mosaic.put(Values.CHROMINO.get(2),
				new Coordinate(new Point(0, 1), new Point(-1, 1), new Point(-1, 2))));
	}

	@Test
	void should_not_add_when_two_differents_color_in_contact() {
		var mosaic = new ChroMosaic();
		mosaic.bruteAdd(new Chromino(TileType.CYAN, TileType.CYAN, TileType.GREEN),
				new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0)));

		assertEquals(Validation.ONE_TILE_WRONG,
				mosaic.put(new Chromino(TileType.MAGENTA, TileType.MAGENTA, TileType.MAGENTA),
						new Coordinate(new Point(1, 1), new Point(0, 1), new Point(-1, 1))));
	}

	@Test
	void should_put_chromino_when_two_contact_and_similar() {
		var mosaic = new ChroMosaic();
		mosaic.bruteAdd(new Chromino(TileType.CYAN, TileType.CYAN, TileType.GREEN),
				new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0)));

		assertEquals(Validation.SUCCESS, mosaic.put(new Chromino(TileType.CYAN, TileType.GREEN, TileType.GREEN),
				new Coordinate(new Point(0, 1), new Point(-1, 1), new Point(-2, 1))));
	}

	@Test
	void should_put_chromino_when_two_contact_and_similar_and_cameleon() {
		var mosaic = new ChroMosaic();
		mosaic.bruteAdd(new Chromino(TileType.CYAN, TileType.CYAN, TileType.GREEN),
				new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0)));

		assertEquals(Validation.SUCCESS, mosaic.put(new Chromino(TileType.CYAN, TileType.CAMELEON, TileType.GREEN),
				new Coordinate(new Point(0, 1), new Point(-1, 1), new Point(-2, 1))));
	}

	@Test
	void should_find_the_parent() {
		var mosaic = new ChroMosaic();

		mosaic.bruteAdd(new Chromino(TileType.GREEN, TileType.CAMELEON, TileType.CYAN),
				new Coordinate(new Point(-1, 0), new Point(0, 0), new Point(1, 0)));

		assertEquals(Validation.SUCCESS, mosaic.put(new Chromino(TileType.GREEN, TileType.GREEN, TileType.CYAN),
				new Coordinate(new Point(-1, 1), new Point(0, 1), new Point(1, 1))));
		
		assertEquals(Validation.SUCCESS, mosaic.put(new Chromino(TileType.MAGENTA, TileType.CAMELEON, TileType.GREEN),
				new Coordinate(new Point(-2, -1), new Point(-2, 0), new Point(-2, 1))));
		
		assertEquals(Validation.SUCCESS, mosaic.put(new Chromino(TileType.YELLOW, TileType.GREEN, TileType.RED),
				new Coordinate(new Point(-3, 0), new Point(-3, 1), new Point(-3, 2))));
	}

}
