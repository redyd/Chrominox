package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HandTest {

	/*
	 * Fonctions de paramètres
	 */

	static List<Arguments> fullHands() {
		return List.of(Arguments.of(new PlayerHand(List.of(Values.CHROMINO.get(0), Values.CHROMINO.get(1)))),
				Arguments.of(new PlayerHand(List.of(Values.CHROMINO.get(0), Values.CHROMINO.get(1), Values.CHROMINO.get(2)))),
				Arguments.of(new PlayerHand(List.of(Values.CHROMINO.get(3), Values.CHROMINO.get(2), Values.CHROMINO.get(4)))),
				Arguments.of(new PlayerHand(List.of(Values.CHROMINO.get(0), Values.CHROMINO.get(1), Values.CHROMINO.get(2),
						Values.CHROMINO.get(3), Values.CHROMINO.get(4), Values.CHROMINO.get(5),
						Values.CHROMINO.get(6)))));
	}

	static List<Arguments> casualHands() {
		return List.of(
				Arguments.of(new PlayerHand(), new PlayerHand(List.of(Values.CHROMINO.get(0))),
						new PlayerHand(List.of(Values.CHROMINO.get(0), Values.CHROMINO.get(1)))),
				Arguments.of(new PlayerHand(List.of(Values.CHROMINO.get(0), Values.CHROMINO.get(1), Values.CHROMINO.get(2)))),
				Arguments.of(new PlayerHand(List.of(Values.CHROMINO.get(3), Values.CHROMINO.get(2), Values.CHROMINO.get(4)))),
				Arguments.of(new PlayerHand(List.of(Values.CHROMINO.get(0), Values.CHROMINO.get(1), Values.CHROMINO.get(2),
						Values.CHROMINO.get(3), Values.CHROMINO.get(4), Values.CHROMINO.get(5),
						Values.CHROMINO.get(6)))));
	}

	static List<Arguments> iterations() {
		return List.of(Arguments.of(1), Arguments.of(5), Arguments.of(10), Arguments.of(11), Arguments.of(16),
				Arguments.of(49));
	}

	/*
	 * Test des constructeurs
	 */

	@Test
	void should_init_with_empty_and_mutable_list_when_create() {
		var hand = new PlayerHand();

		assertEquals(0, hand.size());
		assertTrue(hand.add(Values.CHROMINO.get(0)));
	}

	@Test
	void should_init_with_list_when_create() {
		var hand = new PlayerHand(List.of(Values.CHROMINO.get(0), Values.CHROMINO.get(1), Values.CHROMINO.get(2)));

		assertEquals(3, hand.size());
		assertTrue(hand.add(Values.CHROMINO.get(3)));
	}

	/*
	 * Test de nextChrominoes() et getActive()
	 */

	@ParameterizedTest
	@MethodSource("fullHands")
	void should_pass_to_next_when_nextChrominoes_and_full_hands(PlayerHand hand) {
		var currentChromino = hand.getActive();
		hand.nextChromino();

		var nextChromino = hand.getActive();

		assertNotEquals(currentChromino, nextChromino);
		assertEquals(hand.get(0), currentChromino);
		assertEquals(hand.get(1), nextChromino);
	}

	@Test
	void should_throw_exeption_when_getActive_on_empty_hands() {
		var hand = new PlayerHand();

		assertThrows(IndexOutOfBoundsException.class, () -> hand.getActive());
	}

	@ParameterizedTest
	@MethodSource("iterations")
	void should_give_same_chromino_when_nextChrominoes_on_single_hand(int it) {
		var hand = new PlayerHand(List.of(Values.CHROMINO.get(0)));
		var current = hand.getActive();

		for (int i = 0; i < it; i++) {
			hand.nextChromino();
		}

		assertEquals(hand.getActive(), current);
	}

	/*
	 * Test de get()
	 */

	@ParameterizedTest
	@MethodSource("fullHands")
	void should_throw_exeption_when_get_out_of_bounds(PlayerHand hand) {
		assertThrows(IndexOutOfBoundsException.class, () -> hand.get(100));
	}

	@Test
	void should_get_correct_position_when_get() {
		var hand = new PlayerHand(List.of(Values.CHROMINO.get(0), Values.CHROMINO.get(1), Values.CHROMINO.get(2)));

		assertEquals(Values.CHROMINO.get(1), hand.get(1));
	}

	/*
	 * Test de add(Chromino) et add(Collection)
	 */

	@ParameterizedTest
	@MethodSource("casualHands")
	void should_increase_size_of_hands_by_1_when_add_chromino(PlayerHand hand) {
		int before = hand.size();

		hand.add(Values.CHROMINO.get(5));

		assertEquals(before + 1, hand.size());
	}

	@ParameterizedTest
	@MethodSource("casualHands")
	void should_increase_size_of_hands_by_3_when_add_collections(PlayerHand hand) {
		int before = hand.size();

		hand.add(List.of(Values.CHROMINO.get(0), Values.CHROMINO.get(1), Values.CHROMINO.get(2)));

		assertEquals(before + 3, hand.size());
	}
	
	/*
	 * Test de clear() 
	 */
	
	@ParameterizedTest
	@MethodSource("casualHands")
	void should_remove_every_chrominoes_and_set_current_to_0(PlayerHand hand) {
		hand.clear();
		
		assertEquals(0, hand.size());
		assertThrows(IndexOutOfBoundsException.class, () -> hand.getActive());
	}
	
	/*
	 * Test de removeActive()
	 */
	
	@ParameterizedTest
	@MethodSource("fullHands")
	void should_reduce_hands_and_set_active_to_0(PlayerHand hand) {
		int prevSize = hand.size();
		
		hand.nextChromino();
		hand.nextChromino();
		
		var current = hand.getActive();
		
		var removed = hand.removeActive();
		
		assertEquals(current, removed);
		assertEquals(prevSize - 1, hand.size());
		assertEquals(hand.getActive(), hand.get(0));
	}
}
