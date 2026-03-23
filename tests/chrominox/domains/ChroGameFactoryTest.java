package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChroGameFactoryTest {

	@Test
	void should_init_game_with_2_player() {
		var factory = new ChroGameFactory();
		
		factory.createGame(0);

		assertEquals(2, factory.getPlayer().size());
	}

	@Test
	void should_contains_59_in_bag_when_init_on_classic_game() {
		var factory = new ChroGameFactory();
		
		factory.createGame(0);

		assertEquals(59, factory.getBag().size());
	}
	
	@Test
	void should_contains_69_in_bag_when_init_on_short_game() {
		var factory = new ChroGameFactory();

		factory.createGame(1);
		
		assertEquals(69, factory.getBag().size());
		assertEquals(1, factory.getTypeOfGame());
	}
	
	@Test
	void should_contains_one_chromino_in_game_when_init() {
		var factory = new ChroGameFactory();

		factory.createGame(0);
		
		assertNotNull(factory.getGame());
		assertEquals(0, factory.getTypeOfGame());
	}
	
	@Test
	void should_give_to_player_and_remove_from_bag_when_init() {
		var factory = new ChroGameFactory();
		
		factory.createGame(0);
		
		assertEquals(59, factory.getBag().size());
	}
	
	@Test
	void should_throws_exception_when_invalid_init() {
		var factory = new ChroGameFactory();
		assertThrows(IllegalArgumentException.class, () -> factory.createGame(6));
	}
	
}
