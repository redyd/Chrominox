package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PointTest {

	static List<Arguments> nextToCenter() {
		return List.of(Arguments.of(new Point(1, 0)), Arguments.of(new Point(0, 1)), Arguments.of(new Point(-1, 0)),
				Arguments.of(new Point(0, -1)));
	}

	/*
	 * Test de move()
	 */

	@Test
	void should_move_to_1_1_when_move() {
		var point = new Point(0, 0);

		point.move(1, 1);

		assertEquals(1, point.getX());
		assertEquals(1, point.getY());
	}

	/*
	 * Test de isNextTo()
	 */

	@ParameterizedTest
	@MethodSource("nextToCenter")
	void should_be_next_to(Point p2) {
		var p1 = new Point(0, 0);

		assertTrue(p1.isNextTo(p2));
		assertTrue(p2.isNextTo(p1));
	}

	@Test
	void should_not_be_nextTo_when_equals() {
		var p1 = new Point(3, 3);
		var p2 = new Point(3, 3);
		
		assertFalse(p1.isNextTo(p2));
		assertFalse(p2.isNextTo(p1));
	}
	
	/*
	 * Test de turnAround()
	 */

	@Test
	void should_turn_around_0_0_when_point_above_center() {
		var center = new Point(0, 0);
		var point = new Point(0, -1);

		point.turnAround(center);

		assertEquals(point, new Point(1, 0));
	}

	@Test
	void should_turn_around_0_0_when_point_right_to_center() {
		var center = new Point(0, 0);
		var point = new Point(1, 0);

		point.turnAround(center);

		assertEquals(point, new Point(0, 1));
	}

	@Test
	void should_turn_around_0_0_when_point_under_center() {
		var center = new Point(0, 0);
		var point = new Point(0, 1);

		point.turnAround(center);

		assertEquals(point, new Point(-1, 0));
	}

	@Test
	void should_turn_around_0_0_when_point_left_to_center() {
		var center = new Point(0, 0);
		var point = new Point(-1, 0);

		point.turnAround(center);

		assertEquals(point, new Point(0, -1));
	}
	
	@Test
	void should_not_be_equals_when_differents() {
		var p1 = new Point(0, 0);
		var p2 = new Point(1, 0);
		
		assertNotEquals(p1, p2);
		assertNotEquals(p1, new FakeBag());
	}
	
	@Test
	void should_return_0_when_no_contact() {
		var toCheck = new Point(0, 0);
		var set = Set.of(new Point(2, 0), new Point(2, 1), new Point(2, 3), new Point(-1, -1));
		
		assertEquals(0, toCheck.contactWith(set).size());
	}
	
	@Test
	void should_return_2_when_two_contacts() {
		var toCheck = new Point(0, 0);
		var set = Set.of(new Point(5, 5), new Point(0, 1), new Point(3, 5), new Point(1, 0));
		
		assertEquals(2, toCheck.contactWith(set).size());
	}

}
