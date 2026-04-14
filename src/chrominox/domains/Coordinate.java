package chrominox.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Représente les coordonnées d'un chromino
 */
public final class Coordinate {

	/**
	 * Points des couleurs formant la coordonnée.
	 */
	private final List<Point> coordinates;
	/**
	 * Si la coordonnée doit être visible en jeu.
	 */
	private boolean visible;

	/**
	 * Instancie une coordonnée.
	 * 
	 * @param points les points formants la coordonnée
	 */
	public Coordinate(final Point... points) {
		this.coordinates = new ArrayList<>(List.of(points));
		this.visible = false;
	}
	
	private Coordinate(List<Point> points) {
		this.coordinates = List.copyOf(points);
		this.visible = false;
	}

	public boolean isVisible() {
		return visible;
	}

	/**
	 * Permet de cacher une coordonnée.
	 */
	public void hide() {
		visible = false;
	}

	/**
	 * Permet d'activer une coordonnée.
	 */
	public void show() {
		visible = true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coordinates);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Coordinate other))
			return false;
        return Objects.equals(coordinates, other.coordinates);
	}

	@Override
	public String toString() {
		return "Coordinate [coordinates=" + coordinates + "]";
	}

	/**
	 * @return une copie de la liste des coordonnées
	 */
	public List<Point> getPoints() {
		return List.copyOf(coordinates);
	}

	/**
	 * Permet de vérifier si une coordonnée rentre en collision avec une autre
	 * coordonnée.
	 * 
	 * @param other l'autre coordonnée
	 * @return {@code true} si la coordonnée rentre en collision avec {@code other},
	 *         sinon faux
	 */
	public boolean collideWith(final Coordinate other) {
		for (final Point point2d : coordinates) {
			if (other.coordinates.contains(point2d)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Permet de déplacer tous les points.
	 * 
	 * @param drow nombre de ligne à déplacer
	 * @param dcol nombre de colonne à déplacer
	 */
	public void move(final int drow, final int dcol) {
		for (final Point point : coordinates) {
			point.move(drow, dcol);
		}
	}

	/**
	 * Permet d'effectuer une rotation sur 3 points
	 */
	public void rotate() {
		final Point center = coordinates.get(1);
		coordinates.getFirst().turnAround(center);
		coordinates.getLast().turnAround(center);
	}

	/**
	 * Récupère la position dans la liste d'un point.
	 * 
	 * @param toAdd
	 * @return
	 */
	public int indexOf(Point toAdd) {
		int i = 0;
		for (Point point : coordinates) {
			if (point.equals(toAdd)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public Coordinate reversed() {
		return new Coordinate(coordinates.reversed());
	}
	
	/**
	 * Permet d'associer un point avec une coordonnée contenant ce point.
	 * 
	 * @param lost
	 * @return
	 */
//	public static Coordinate findWithPoint(Point lost, Set<Coordinate> set) {
//		var horizontal = findParentSearcher(lost, set, 0, 1); // 40 opérations max
//		if (horizontal != null) { // 1 opération
//			return horizontal;
//		}
//
//		var vertical = findParentSearcher(lost, set, 1, 0); // 40 opérations max
//		if (vertical != null) { // 1 opération
//			return vertical;
//		}
//
//		throw new NoSuchElementException("Aucune coordonnée trouvée");
//	} // CTT total: 82 opérations dans le pire des cas

	/**
	 * Fais parcourir une coordonnée contenant un point perdu pour essayer de le
	 * réassocier à une coordonnée existante.
	 * 
	 * @param lost le point perdu
	 * @param set  le set contenant les coordonnées
	 * @param row  décallage de ligne
	 * @param col  décallage de colonne
	 * @return la coordonnée présente dans {@code set} contenant {@code lost}, sinon
	 *         {@code null}
	 */
//	private static Coordinate findParentSearcher(Point lost, Set<Coordinate> set, int row, int col) {
//		Point p1 = lost.copyOf().move(row, col); // 1 opération
//		Point p2 = p1.copyOf().move(row, col); // 1 opération
//		Coordinate searcher = new Coordinate(lost.copyOf(), p1, p2); // 1 opération
//
//		for (int i = 0; i < 3; i++) { // 3 itérations max
//			if (set.contains(searcher)) { // 1 opération
//				return searcher;
//			}
//			if (set.contains(searcher.reversed())) { // 2 opérations
//				return searcher.reversed();
//			}
//
//			searcher.move(row * -1, col * -1); // 1 opération
//		} // total: 3 * 4 = 12
//
//		return null; // 1 opération
//	} // CTT total (dans le pire des cas): 3 + 12 * 3 + 1 = 40 

	/**
	 * Vérifie si une coordonnée possède un point.
	 * 
	 * @param lost le point à vérifier
	 * @return {@code true} si le point appartient à la coordonnée
	 */
	public boolean contains(Point lost) {
		return coordinates.contains(lost); // 3 opérations max
	}

}
