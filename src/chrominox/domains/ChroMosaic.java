package chrominox.domains;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Mosaique permettant de stocker des chrominos avec des coordonnées.
 * <hr>
 * <h1>It-2-q1</h1> <i>Postcondition(s) d’un ajout réussi et condition(s) de
 * non-duplication d’un chromino</i>
 * <p>
 * Pour garantir qu'un chromino ne soit jamais dupliqué, il faut s'assurer de
 * supprimer la référence de ce chromino du conteneur duquel on l'enlève. On
 * utilise aussi les propriétés de la map qui va garantir que toutes les clés
 * sont uniques pour éviter des éventuels problèmes.
 * </p>
 * <h1>It-2-q2</h1> <i>CTT de l’algorithme d’ajout d’un chromino à la
 * mosaïque</i>
 * <p>
 * La complexité temporelle théorique est en {@code O(1)} (justifications dans
 * les différentes méthodes utilisées par la méthode {@code put} et dans cette
 * dernière).
 * </p>
 * <h1>It-2-q3</h1> <i>Choix de collection pour la mosaïque</i>
 * <p>
 * J'ai choisi une map comme interface de collection car j'ai besoin de lier un
 * chromino à ses coordonnées et des points avec leurs couleurs. Seul la map
 * offre cette fonctionnalité.
 * </p>
 * <p>
 * J'ai choisi une simple {@link HashMap} car je n'ai pas besoin d'avoir mes
 * informations triées ou de l'ordre d'insertion ({@link TreeMap} ou
 * {@link LinkedHashMap}. La {@link HashMap} bénéficie en plus d'opérations en
 * {@code O(1)} contrairement à la {@link TreeMap} avec des opérations en
 * {@code O(log n)}.
 * </p>
 * <h1>It-3-q1</h1> <i>Conditions(s) sur les points remportés par un joueur lors
 * d’un placement</i>
 * <p>
 * Le nombre de points minimum d'un coup est de 4 (ajout d'un chromino de valeur
 * 2 sur un chromino de valeur 1 avec deux points de contact -> 2 + (2*1) =
 * 4).<br>
 * [ R - C - M ]<br>
 * ....[ M - M - M ]<br>
 * ........[ m - m - g ]<br>
 * <i>Le chromino posé est représenté en minuscule</i>
 * </p>
 * <p>
 * Le nombre de points maximum est de 21 (ajout d'un chromino de valeur 3 en
 * contact avec 4 chrominos de valeur 3 avec 6 contacts -> 3 + 6*3 = 21)<br>
 * ....[ G - G - M ][ M - M - Y ]<br>
 * .....|G|[ G - C - M ][ M - C - Y ]<br>
 * |G|..|G|....[ r - c - m ]<br>
 * |B|..|B|[ B - C - M ][ M - C - R ]<br>
 * |B|.[ B - B - M ][ M - M - M]<br>
 * <i>Le chromino posé est représenté en minuscule</i>
 * </p>
 * <h1>It-3-q2</h1> <i>CTT de l’algorithme de calcul des points remportés par un
 * joueur lors d’un placement</i>
 * <p>
 * <strong>La notation {@code n} fait référence au nombre d'élément présent dans
 * la map {@code map}</strong>
 * <ul>
 * <li>La méthode commence avec un appel à {@code checkContact}: 15 opérations
 * -> O(1)</li>
 * <li>Initialise une variable {@code total} avec la valeur du chromino à
 * ajouter: ~1 opération -> O(1)</li>
 * <li>On itère sur toutes les clés de la map {@code contactPoints}, qui
 * correspondent au point du chromino à ajouter (3 itérations):</li>
 * <ul>
 * <li>Récupération de la liste des points de contact avec la clé: 1 opération
 * -> O(1)</li>
 * <li>Création d'un set pour acceuillir les coordonnées en contact avec la clé:
 * 1 opération -> O(1)</li>
 * <li>On itère sur tous les points en contact avec la clé (maximum 12
 * itérations (= le nombre de points de contact possible car la méthode est
 * exécutée avant de vérfier si la pose est valide)):
 * <ul>
 * <li>Ajout de la coordonnée contenant le point en contact avec la clé dans le
 * set: n opération -> O(n)</li>
 * </ul>
 * <li>On itère sur le set de coordonnée retrouvée (maximum 4 itération, cf
 * It-3-q1 ~ points maximum):</li>
 * <ul>
 * <li>Ajout des points du chromino correspondant à la coordonnée au total</li>
 * </ul>
 * </ul>
 * </ul>
 * Au final, on se retrouve avec une méthode en O(n). Les détails des opérations
 * se trouvent dans le corps de {@code calculPointsFor} et de ses méthodes
 * utilisées.
 * </p>
 * <p>
 * Une approche non-itérative avait été utilisée au premier abord afin d'avoir
 * un algorithme en O(1) (commentaire ligne 152). Cependant, le calcul
 * nécessitait environ 80 opérations maxmimum et était donc moins performant
 * qu'une simple itération car il est improbable d'avoir 80 éléments ou plus sur
 * lequel itérer au vu des règles du jeu. Les méthodes utilisées ont simplement
 * été commentées et non supprimées (cf {@link Coordinate} ligne 143 et 178).
 * </p>
 * <h1>It-3-q3</h1> <i>Choix de collection pour les valeurs des chrominos (afin
 * de calculer les points de la pose d’un chromino)</i>
 * <p>
 * Pour calculer la valeur d'un chromino, j'ai converti ma liste de tuile en un
 * set et en ai récupéré sa taille. Je n'ai donc pas d'implémentation concrète car
 * j'ai utilisé un Set immuable (Set.of()).
 * </p>
 */
public final class ChroMosaic {

	private static final int NB_CONTACT = 2;

	private final Map<Coordinate, Chromino> map;
	private final Map<Point, TileType> usedPoints;

	/**
	 * Initialise {@code map} et {@code usedPoints}
	 */
	public ChroMosaic() {
		map = new HashMap<>();
		usedPoints = new HashMap<>();
	}

	/**
	 * Calcul le nombre de points à ajouter au joueur.
	 * 
	 * @param chromino
	 * @param coordinate
	 * @param contactPoints
	 */
	public int calculScoreFor(final Chromino chromino, final Coordinate coordinate) {
		final Map<Point, List<Point>> contactPoints = checkContact(coordinate); // 15 opérations (cf méthode
																				// checkContact)
		int total = chromino.points(); // 1 opération

		for (final Point key : contactPoints.keySet()) { // 3 itérations
			final List<Point> inContact = contactPoints.get(key); // 1 opération
			final Set<Coordinate> coordinatesInContact = new HashSet<>(); // 1 opération

			for (final Point lostPoint : inContact) { // maximum 12 itérations car les règles de placement ne sont
														// pas respectées. Le score sera cependant correct car si la
														// pose est comfirmée, les points seront cohérent.

				coordinatesInContact.add(findWithPointIterative(lostPoint, map.keySet())); // n opération (n représente
																							// le nombre d'élément dans
																							// la map "map")s

//				coordinatesInContact.add(Coordinate.findWithPoint(lostPoint, map.keySet()));
			}

			for (final Coordinate coordinateInContact : coordinatesInContact) { // 4 itérations max car impossible
																				// d'avoir plus (cf It-3-q1 point max)
				total += map.get(coordinateInContact).points(); // 1 opération
			} // 4 * 1 = 4 opérations

		}

		return total; // 1 opération
	}

	/**
	 * Ajoute une clé {@code Chromino} et une valeur {@code Coordinate}
	 * 
	 * @param chromino   le chromino à ajouter
	 * @param coordinate la coordonnée à ajouter
	 * @return {@code true} si le chrimono a été ajouté, sinon faux
	 */
	public Validation put(final Chromino chromino, final Coordinate coordinate) {
		// Vérifie si les chrominos ne se chevauche pas
		if (!checkSuperposition(coordinate)) { // 3 opérations (cf méthode)
			return Validation.ON_OTHER_CHROMINO;
		}

		// Vérifie si il y a au moins deux tuiles qui se touche
		final Map<Point, List<Point>> contactPoints = checkContact(coordinate); // 15 opérations (cf méthode)

		if (countContact(contactPoints) < NB_CONTACT) { // 1 opération
			return Validation.AT_LEAST_TWO;
		}

		final Validation tile = checkTileType(chromino, coordinate, contactPoints);

		if (tile.equals(Validation.SUCCESS)) {
			map.put(coordinate, chromino); // 1 opération
			addToUsedPoints(chromino, coordinate); // 3 opération
		}

		return tile;
		// Vérifie si les tuiles qui se touche sont identiques
		// 37 opérations (cf méthode)
	} // TOTAL: 1 + 1 + 3 + 15 + 1 + 3 + 1 + 37 = O(62) => O(1)

	/**
	 * @param contactPoints
	 * @return
	 */
	private int countContact(final Map<Point, List<Point>> contactPoints) {
		int nbContact = 0; // 1 opération
		for (final List<Point> list : contactPoints.values()) {
			nbContact += list.size();
		} // 3 itérations (ici, on itère sur 3 éléments de type list, correspondant à une
			// liste de point de contact pour chaque coordonnée que l'on veut ajouter)
		return nbContact;
	}

	/**
	 * Vérifie si le chromino ne rentre pas en collision avec un autre chromino
	 * 
	 * @param coordinate
	 * @return {@code true} si les coordonnées ne se superpose pas, sinon
	 *         {@code false}
	 */
	private boolean checkSuperposition(final Coordinate coordinate) {
		for (final Point point : coordinate.getPoints()) {
			if (usedPoints.containsKey(point)) { // 1 opération
				return false;
			}
		}
		return true;
	} // dans le pire des cas, 3 itérations de boucle -> 3 * 1 = 3 opérations

	/**
	 * Calcul les points de contact pour chaque point d'une coordonnée.
	 * 
	 * @param coordinate Coordonnée à vérifier
	 * @return une map contentant en clé un point de {@code coordinate} et en valeur
	 *         la liste des points sur le plateau en contact
	 */
	private Map<Point, List<Point>> checkContact(final Coordinate coordinate) {
		final Map<Point, List<Point>> points = new HashMap<>(); // 1 opération

		for (final Point point : coordinate.getPoints()) {
			points.put(point, point.contactWith(Set.copyOf(usedPoints.keySet()))); // le point va calculer les 4
																		// potentiels contact
			// pour plus de simplicité -> 4 opérations + 1
			// opération (put)
		} // -> 5 * 3 opérations -> 15 opérations

		return points;
	} // 15 opérations

	/**
	 * Vérifie si les tuiles en contact sont de mêmes couleurs.
	 * 
	 * @param chromino      Le chromino à ajouter
	 * @param coordinate    Les coordonnées du chromino à ajouter
	 * @param contactPoints Les points en contact avec ce chromino
	 * @return
	 */
	private Validation checkTileType(final Chromino chromino, final Coordinate coordinate,
			final Map<Point, List<Point>> contactPoints) {
		for (final Point toAdd : contactPoints.keySet()) {
			final List<Point> inContact = contactPoints.get(toAdd); // 1 opération

			for (final Point pointInContact : inContact) { // Ici, on va itérer entre 0 et 3 fois (car il n'est pas
															// possible
															// d'avoir 4 points de contact, car cela impliquerait une
															// superposition, ce qui aurait déjà arrêté le traitement en
															// amont
				final TileType tileInContact = usedPoints.get(pointInContact); // 1 opération
				final TileType tileToAdd = chromino.getTileAt(coordinate.indexOf(toAdd)); // 2 opérations

				if (!tileInContact.equals(TileType.CAMELEON) && !tileToAdd.equals(TileType.CAMELEON)
						&& !tileInContact.equals(tileToAdd)) { // 3 opérations
					return Validation.ONE_TILE_WRONG;
				}
			}
		} // 3 itérations car 3 points de coordonnée à vérifier
			// -> 3 * (1 + 1 + 3 + 1 + 2 + 3) = 33 opérations

		return Validation.SUCCESS;
	} // 37 opérations

	private Coordinate findWithPointIterative(Point lost, Set<Coordinate> set) {
		for (Coordinate coordinate : set) { // n opération
			if (coordinate.contains(lost)) { // 3 opérations
				return coordinate;
			}
		}
		throw new NoSuchElementException("Aucune coordonnée trouvée");
	} // total: O(3n) -> O(n)

	/**
	 * Permet d'ajouter un chromino dans {@code usedPoints}.
	 * 
	 * @param chromino
	 * @param coordinate
	 */
	private void addToUsedPoints(final Chromino chromino, final Coordinate coordinate) {
		int i = 0;
		for (final Point point : coordinate.getPoints()) {
			usedPoints.put(point, chromino.getTileAt(i));
			i++;
		}
	}

	public Map<Coordinate, Chromino> getMap() {
		return Map.copyOf(map);
	}

	/**
	 * Force l'ajout d'un chromino en ne vérifiant que la superposition pour garder
	 * un état de plateau correct.
	 * 
	 * @param chromino
	 * @param coordinate
	 */
	public boolean bruteAdd(final Chromino chromino, final Coordinate coordinate) {
		if (checkSuperposition(coordinate)) {
			map.put(coordinate, chromino);
			addToUsedPoints(chromino, coordinate);
			return true;
		}
		return false;
	}

}
