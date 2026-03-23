package chrominox.domains;

import java.util.List;

/**
 * Les données de cette classe de test ont été générées par ChatGPT pour plus de
 * variété et de simplicité
 */
public class Values {

	public static final List<Chromino> CHROMINO = List.of(new Chromino(TileType.RED, TileType.RED, TileType.YELLOW),
			new Chromino(TileType.GREEN, TileType.MAGENTA, TileType.GREEN),
			new Chromino(TileType.YELLOW, TileType.GREEN, TileType.MAGENTA),
			new Chromino(TileType.MAGENTA, TileType.YELLOW, TileType.RED),
			new Chromino(TileType.GREEN, TileType.RED, TileType.CYAN),
			new Chromino(TileType.MAGENTA, TileType.MAGENTA, TileType.MAGENTA),
			new Chromino(TileType.RED, TileType.CYAN, TileType.RED),
			new Chromino(TileType.YELLOW, TileType.CAMELEON, TileType.YELLOW),
			new Chromino(TileType.CYAN, TileType.YELLOW, TileType.GREEN),
			new Chromino(TileType.MAGENTA, TileType.RED, TileType.CYAN));

	public static final List<Coordinate> COORD = List.of(
			new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0)), // 0
			new Coordinate(new Point(0, 2), new Point(0, 1), new Point(0, 0)), // 1
			new Coordinate(new Point(2, 2), new Point(1, 1), new Point(0, 0)), // 2
			new Coordinate(new Point(-1, 1), new Point(0, 0), new Point(1, -1)), // 3
			new Coordinate(new Point(2, 1), new Point(1, 1), new Point(0, 1)), // 4
			new Coordinate(new Point(1, -1), new Point(0, 0), new Point(-1, 1)), // 5
			new Coordinate(new Point(0, 0), new Point(1, 1), new Point(2, 2)) // 6
	);

}
