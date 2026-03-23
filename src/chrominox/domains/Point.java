package chrominox.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Représente un point 2D dans l'espace graphique (axe des y inversé)
 */
public final class Point {

	/**
	 * Coordonnée {@code x} du point
	 */
	private int x;
	/**
	 * Coordonnée {@code y} du point
	 */
	private int y;

	/**
	 * Initialise le point avec ses coordonnées {@code x} et {@code y}.
	 * 
	 * @param x
	 * @param y
	 */
	public Point(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof final Point other))
			return false;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	/**
	 * Permet de redéfinir le point.
	 * 
	 * @param x
	 * @param y
	 */
	public void setTo(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Permet de vérifier si un point est à côté d'un autre point.
	 * 
	 * @param other un autre point
	 * @return {@code true} si les deux points sont côte-à-côte
	 */
	public boolean isNextTo(final Point other) {
		return Math.abs(getX() - other.getX()) + Math.abs(getY() + other.getY()) == 1;
	}

	/**
	 * Permet de déplacer le point sur un écran graphique. Les opérations sur le
	 * {@code x} affecte donc les colonnes, tandis que les opérations sur le
	 * {@code y} affecte les lignes.
	 * 
	 * @param drow nombre de lignes à déplacer
	 * @param dcol nombre de colonnes à déplacer
	 */
	public Point move(final int drow, final int dcol) {
		x += dcol;
		y += drow;
		return this;
	}

	/**
	 * Permet de faire tourner un point autour d'un autre dans le sens anti-horloger
	 * et de 90°.
	 * 
	 * @param center point autour du quel tourner
	 */
	public void turnAround(final Point center) {
		int newX = getX() - center.getX();
		int newY = getY() - center.getY();
		final int temp = newX;

		newX = -newY;
		newY = temp;

		newX += center.getX();
		newY += center.getY();

		setTo(newX, newY);
	}

	/**
	 * Permet de récupérer une copie des points au contact de celui-ci.
	 * 
	 * @param set contient des points
	 * @return le nombre de points en contact
	 */
	public List<Point> contactWith(Set<Point> set) {
		List<Point> points = new ArrayList<>();
		Point rotateArround = new Point(x, y + 1);

		for (int i = 0; i < 4; i++) {
			if (set.contains(rotateArround)) {
				points.add(rotateArround.copyOf());
			}
			rotateArround.turnAround(this);
		}

		return points;
	}

	/**
	 * Copie le point.
	 * 
	 * @return
	 */
	public Point copyOf() {
		return new Point(x, y);
	}

}
