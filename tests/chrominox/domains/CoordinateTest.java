package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.Test;

class CoordinateTest {

	private static List<Point> point = List.of(new Point(0, 0), new Point(1, 0), new Point(-1, 0), new Point(0, 1),
			new Point(1, 1), new Point(0, -1));

	/*
	 * Test de collideWith()
	 */

	@Test
	void should_not_collide_when_different() {
		var c1 = new Coordinate(point.get(0), point.get(1), point.get(2));
		var c2 = new Coordinate(point.get(3), point.get(4), point.get(5));

		assertFalse(c1.collideWith(c2));
		assertFalse(c2.collideWith(c1));
	}

	@Test
	void should_collide_when_one_in_commun() {
		var c1 = new Coordinate(point.get(0), point.get(1), point.get(2));
		var c2 = new Coordinate(point.get(2), point.get(4), point.get(5));

		assertTrue(c1.collideWith(c2));
		assertTrue(c2.collideWith(c1));
	}

	@Test
	void should_collide_when_two_in_commun() {
		var c1 = new Coordinate(point.get(0), point.get(1), point.get(2));
		var c2 = new Coordinate(point.get(2), point.get(1), point.get(5));

		assertTrue(c1.collideWith(c2));
		assertTrue(c2.collideWith(c1));
	}

	/*
	 * Test de rotate()
	 */

	@Test
	void should_rotate() {
		var actual = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));
		var expected = new Coordinate(new Point(0, 1), new Point(0, 0), new Point(0, -1));

		actual.rotate();

		assertEquals(expected, actual);
	}

	@Test
	void should_be_same_when_rotate_4_times() {
		var actual = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));
		var expected = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));

		actual.rotate();
		actual.rotate();
		actual.rotate();
		actual.rotate();

		assertEquals(expected, actual);
	}

	@Test
	void should_get_equals_when_same_hascode() {
		var actual = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));
		var other = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));

		assertEquals(actual.hashCode(), other.hashCode());
		assertEquals(actual, other);
		assertEquals(actual, actual);
	}

	@Test
	void should_not_be_equals() {
		var actual = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));
		var expected = new Coordinate(new Point(0, 1), new Point(0, 0), new Point(0, -1));

		assertNotEquals(actual, expected);
		assertNotEquals(actual, null);
		assertNotEquals(actual, new FakeBag());
	}

	@Test
	void should_return_correct_string() {
		var actual = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));

		assertEquals("Coordinate [coordinates=[Point [x=1, y=0], Point [x=0, y=0], Point [x=-1, y=0]]]",
				actual.toString());
	}

	@Test
	void should_return_correct_index_of_point() {
		var actual = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));

		assertEquals(0, actual.indexOf(new Point(1, 0)));
		assertEquals(1, actual.indexOf(new Point(0, 0)));
		assertEquals(2, actual.indexOf(new Point(-1, 0)));

		// Cas d'erreur
		assertEquals(-1, actual.indexOf(new Point(-1, 1)));
	}

//	@Test
//	void should_find_coordinate_when_point_given() {
//		Set<Coordinate> set = Set.of(new Coordinate(new Point(-2, -1), new Point(-2, 0), new Point(-2, 1)),
//				new Coordinate(new Point(-1, -1), new Point(-1, 0), new Point(-1, 1)),
//				new Coordinate(new Point(0, -1), new Point(0, 0), new Point(0, 1)),
//				new Coordinate(new Point(-2, 2), new Point(-1, 2), new Point(0, 2)));
//
//		assertEquals(new Coordinate(new Point(0, -1), new Point(0, 0), new Point(0, 1)),
//				Coordinate.findWithPoint(new Point(0, 0), set));
//		assertEquals(new Coordinate(new Point(-2, -1), new Point(-2, 0), new Point(-2, 1)),
//				Coordinate.findWithPoint(new Point(-2, 1), set));
//		assertEquals(new Coordinate(new Point(-2, 2), new Point(-1, 2), new Point(0, 2)),
//				Coordinate.findWithPoint(new Point(-1, 2), set));
//		
//		assertThrows(NoSuchElementException.class, () -> Coordinate.findWithPoint(new Point(10, -1), set));
//	}
}
