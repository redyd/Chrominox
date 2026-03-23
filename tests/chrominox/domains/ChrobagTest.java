package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class ChrobagTest {
	
	@Test
	void should_init_with_80_values() {
		var bag = new Chrobag();
		
		assertEquals(80, bag.size());
	}
	
	@Test
	void should_generates_5_cameleon() {
		var bag = new Chrobag();
		int count = 0;
		for (Chromino chromino : bag.generateChrominoes()) {
			if (chromino.isCameleon()) {
				count++;
			}
		}
		
		assertEquals(5, count);
	}
	
	@Test
	void should_generate_differents_chromino() {
		var bag = new Chrobag();
		var generate = bag.generateChrominoes();
		
		for (Chromino chromino : generate) {
			int count = 0;
			for (Chromino toCheck : generate) {
				if (chromino.equals(toCheck) || chromino.reversed().equals(toCheck)) {
					count++;
				}
			}
			assertEquals(1, count);
		}
	}

	@Test
	void should_take_one_cameleon_chromino() {
		var chromino = new Chrobag().takeCameleon();
		assertTrue(chromino.isCameleon());
	}
	
	@Test
	void should_reduce_bag_when_takes() {
		var bag = new Chrobag();
		int before = bag.size();
		
		bag.takeCameleon();
		
		int after = bag.size();
		
		assertNotEquals(before, after);
	}
	
	@Test
	void should_reduce_list_when_take() {
		var bag = new Chrobag();
		int before = bag.size();
		
		for (int i = 0; i < 10; i++) {
			bag.take();
		}
		
		int after = bag.size();
		
		assertNotEquals(before, after);
		assertEquals(before - 10, after);
	}
	
	@Test
	void should_reduce_list_when_multiple_take() {
		var bag = new Chrobag();
		int before = bag.size();
		
		bag.take(10);
		
		int after = bag.size();
		
		assertNotEquals(before, after);
		assertEquals(before - 10, after);
	}
	
	@Test
	void should_throw_exception_when_no_cameleon() {
		var bag = new Chrobag();
		for (int i = 0; i < 5; i++) {
			bag.takeCameleon();
		}
		
		assertThrows(NoSuchElementException.class, () -> bag.takeCameleon());
	}
	
	@Test
	void should_be_empty() {
		var bag = new Chrobag();
		bag.take(bag.size() - 1);
		
		assertFalse(bag.empty());
		
		bag.take();
		
		assertTrue(bag.empty());
		
	}

}
