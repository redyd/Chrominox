package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChroPlayerTest {

	private static final String COLOR = "black";

	/*
	 * Test de take()
	 */

	@Test
	void should_take_one_and_reduce_bag() {
		var bag = new FakeBag();
		var p1 = new ChroPlayer("bernard", COLOR);
		int before = bag.size();

		p1.take(bag);

		assertEquals(before - 1, bag.size());
	}

	@Test
	void should_not_take_when_empty_bag() {
		var bag = new FakeBag();
		var player = new ChroPlayer("bernard", COLOR);
		bag.take(bag.size());
		
		assertFalse(player.take(bag));
	}

	@Test
	void should_reduce_bag_when_init() {
		var bag = new FakeBag();
		int before = bag.size();

		var p1 = new ChroPlayer("bernard", COLOR);

		p1.init(bag, 5);

		assertEquals(before - 5, bag.size());
	}

	/*
	 * Test du chromino actif
	 */

	@Test
	void should_be_null_when_create() {
		var p1 = new ChroPlayer("bernard", COLOR);

		assertThrows(IndexOutOfBoundsException.class, () -> p1.getActiveChromino());
	}

	@Test
	void should_return_false_when_coordinate_is_visible() {
		var p1 = new ChroPlayer("bernard", COLOR);

		assertFalse(p1.getActiveCoordinate().isVisible());
	}

	@Test
	void should_pass_to_next_active_chromino() {
		var p1 = new ChroPlayer("bernard", COLOR);
		var bag = new FakeBag();

		p1.init(bag, 10);

		var before = p1.getActiveChromino();
		p1.nextChromino();
		var after = p1.getActiveChromino();

		assertNotEquals(before, after);
	}

	@Test
	void should_return_correct_string_values() {
		var p1 = new ChroPlayer("Bernard", COLOR);
		assertEquals(COLOR, p1.getBorderColor());
		assertEquals("Bernard", p1.getName());
	}

	@Test
	void should_return_player_chrominoes() {
		var p1 = new ChroPlayer("bernard", COLOR);
		var bag = new FakeBag();
		for (int i = 0; i < 10; i++) {			
			p1.take(bag);
		}

		assertEquals(10, p1.getChrominoes().size());
	}

	@Test
	void should_move_active_coordinate() {
		var p1 = new ChroPlayer("bernard", COLOR);
		p1.getActiveCoordinate().move(1, 1);

		assertEquals(p1.getActiveCoordinate(), new Coordinate(new Point(2, 1), new Point(1, 1), new Point(0, 1)));
	}

	@Test
	void should_init_new_active_coordinate() {
		var p1 = new ChroPlayer("bernard", COLOR);
		p1.initActiveCoordinate();
		p1.take(new FakeBag());

		assertTrue(p1.hasActive());

		p1.removeActiveCoordinate();

		assertFalse(p1.hasActive());
	}

	@Test
	void should_reduce_hand_when_remove_active() {
		var p1 = new ChroPlayer("bernard", COLOR);
		var bag = new FakeBag();
		for (int i = 0; i < 10; i++) {
			p1.take(bag);
		}

		var active = p1.getActiveChromino();

		p1.removeActiveChromino();

		assertEquals(9, p1.getChrominoes().size());
		assertNotEquals(active, p1.getActiveChromino());
	}

	@Test
	void should_select_last_chromino_when_function() {
		var p1 = new ChroPlayer("bernard", COLOR);
		var bag = new FakeBag();
		p1.init(bag, 2);
		var before = p1.getActiveChromino();

		p1.selectLastChromino();

		assertNotEquals(before, p1.getActiveChromino());
	}
	
	@Test
	void compare_two_player() {
		var p1 = new ChroPlayer("Jeff", "");
		var p2 = new ChroPlayer("ELO", "");
		p1.points(10);
		
		assertTrue(p1.compareTo(p2) > 0);
		
		p2.points(20);
		
		assertTrue(p2.compareTo(p1) > 0);
		
		p1.points(10);
		
		assertEquals(0, p1.compareTo(p2));
	}
}
