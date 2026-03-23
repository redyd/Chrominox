package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChroGameTest {

	private static Coordinate co1 = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));

	@Test
	void should_put_values_when_empty() {
		var bag = new FakeBag();
		var game = new ChroGame(bag, 10, new FakePlayer(), new FakePlayer());

		game.bruteAdd(bag.take(), co1);

		assertEquals(1, game.getMap().size());
	}

	@Test
	void should_change_player_when_next() {
		var game = new ChroGame(new FakeBag(), 10, new FakePlayer(), new FakePlayer());
		var current = game.getActivePlayer();
		game.nextPlayer();

		assertNotEquals(current, game.getActivePlayer());
	}

	@Test
	void should_increase_size_of_current_player_when_give() {
		var p1 = new ChroPlayer("", "");
		var p2 = new ChroPlayer("", "");
		var game = new ChroGame(new FakeBag(), 3, p1, p2);
		var active = game.getActivePlayer();

		game.give();

		assertEquals(4, active.getChrominoes().size());
	}

	@Test
	void should_not_be_able_to_pick_when_already_picked() {
		var p1 = new ChroPlayer("", "");
		var game = new ChroGame(new FakeBag(), 3, p1);

		assertTrue(game.canPick());
		game.give();
		assertFalse(game.canPick());
		assertFalse(game.give());
	}

	@Test
	void should_add_to_mosaic() {
		var p1 = new ChroPlayer("", "");
		var game = new ChroGame(new FakeBag(), 3, p1);

		assertEquals(Validation.class,
				game.addToMosaic(Values.CHROMINO.getFirst(), Values.COORD.getFirst()).getClass());
	}

	@Test
	void should_grant_points_when_add() {
		var p1 = new ChroPlayer("john", "orange");
		p1.initActiveCoordinate();
		
		var game = new ChroGame(new Chrobag(), 10, p1);
		game.bruteAdd(new Chromino(TileType.RED, TileType.CAMELEON, TileType.GREEN), co1);

		game.addToMosaic(new Chromino(TileType.RED, TileType.GREEN, TileType.GREEN),
				new Coordinate(new Point(0, 1), new Point(-1, 1), new Point(-2, 1)));

		assertEquals(2 + 3 + 3, game.getActivePlayer().points(0));
		
		game.addToMosaic(new Chromino(TileType.GREEN, TileType.MAGENTA, TileType.RED),
				new Coordinate(new Point(-2, 0), new Point(-2, -1), new Point(-2, -2)));
		
		assertEquals((2 + 3 + 3) + (3 + 3 + 2), game.getActivePlayer().points(0));

	}
	
	@Test
	void should_throws_exception_when_invalid_type() {
		var game = new ChroGame(new Chrobag(), 10, new ChroPlayer("", ""));
		
		assertThrows(IllegalArgumentException.class, () -> game.shouldEnded(4));
	}
	
	@Test
	void should_end_when_bag_has_no_more_chromino() {
		var p1 = new ChroPlayer("Paul", "pink");
		var p2 = new ChroPlayer("Georges", "orange");
		var bag = new Chrobag();
		var game = new ChroGame(bag, 5, p1, p2);
		bag.take(bag.size());
		
		assertFalse(game.shouldEnded(1));
		
		game.nextPlayer();
		
		assertTrue(game.shouldEnded(1));
		
		game.nextPlayer();
	}
	
	@Test
	void should_end_when_player_has_no_chromino() {
		var p1 = new ChroPlayer("John", "pink");
		var p2 = new ChroPlayer("Ringo", "orange");
		var bag = new Chrobag();
		var game = new ChroGame(bag, 5, p1, p2);
		var first = game.getActivePlayer();
		
		for (int i = 0; i < 5; i++) {
			first.removeActiveChromino();
		}
		
		assertFalse(game.shouldEnded(0));
		assertFalse(game.shouldEnded(1));
		
		game.nextPlayer();
		
		assertTrue(game.shouldEnded(0));
		assertTrue(game.shouldEnded(1));
		
		game.nextPlayer();
		
	}
	
	@Test
	void should_end_when_second_player_has_no_chromino() {
		var p1 = new ChroPlayer("John", "pink");
		var p2 = new ChroPlayer("Ringo", "orange");
		var bag = new Chrobag();
		var game = new ChroGame(bag, 5, p1, p2);
		var first = game.getActivePlayer();
		
		game.nextPlayer();
		
		for (int i = 0; i < 5; i++) {
			(p1.equals(first) ? p2 : p1).removeActiveChromino();
		}
		
		assertTrue(game.shouldEnded(0));
		
		game.nextPlayer();
	}
	
	@Test
	void should_end_when_bag_empty_and_round_not_played() {
		var p1 = new ChroPlayer("John", "pink");
		var p2 = new ChroPlayer("Ringo", "orange");
		var bag = new Chrobag();
		var game = new ChroGame(bag, 5, p1, p2);
		bag.take(bag.size());
		
		game.nextPlayer();
		
		assertTrue(game.shouldEnded(0));
	}
	
	@Test
	void should_end_when_short_game() {
		var p1 = new ChroPlayer("Jeff", "");
		var bag = new Chrobag();
		var game = new ChroGame(bag, 5, p1);
		
		assertTrue(game.shouldEnded(1));
		bag.take(bag.size());
	}
	
	@Test
	void should_remove_remaining_point_when_game_end() {
		var p1 = new ChroPlayer("David", "");
		var p2 = new ChroPlayer("Jeff", "");
		var game = new ChroGame(new Chrobag(), 5, p1, p2);
		
		int point1 = 0;
		for (Chromino chromino : p1.getChrominoes()) {
			point1 -= chromino.points();
		}
		
		int point2 = 0;
		for (Chromino chromino : p2.getChrominoes()) {
			point2 -= chromino.points();
		}
		
		game.removeRemainingPoints();
		
		assertEquals(point1, p1.getPoints());
		assertEquals(point2, p2.getPoints());
	}

}
