package chrominox.domains;

import java.util.List;

/**
 * <h1>It-1-q4</h1><br>
 * <i>Fait référence à la classe {@link PlayerHand}</i>
 * <ol>
 * <li>Interface de collection : <br>
 * J'ai choisi une {@link List} car j'ai besoin de faire des accès par indice,
 * des ajouts, ainsi que des suppressions. Aucune autre collection ne permet
 * cela.</li>
 * <li>Choix de l'implémentation concrète :<br>
 * J'ai choisi une {@link ArrayList} car il n'y a qu'une petite quantité de
 * données, et que c'est la plus rapide pour des accès par indice. La CTT de
 * {@link ArrayList} est de {@code O(1)} pour l'accès par indice, ainsi que les
 * ajouts ({@code O(1) mais amorti}, et est en {@code O(n)} pour une suppression
 * (pas forcément dérangeant ici car la main d'un joueur n'est pas trop grande).
 * Une {@link LinkedList} aurait par contre eu un accès lent (en {@code O(n)}
 * mais des ajouts et suppression plus rapide (en {@code O(1)}</li>
 * </ol>
 */
public interface Player extends Comparable<Player> {

	/**
	 * Initialise la main du joueur ainsi que le chromino actif.
	 * 
	 * @param handSize taille de la main
	 */
	void init(Bag bag, int handSize);

	/**
	 * Permet de prendre un chromino du sac.
	 * 
	 * @return {@code true} si le chromino a bien été pris
	 */
	boolean take(Bag bag);

	/**
	 * @return le nom du joueur
	 */
	String getName();

	/**
	 * Détermine si le joueur a une coordonnée active sur le plateau lors du
	 * placement.
	 * 
	 * @return vrai si la coordonnée active est visible
	 */
	boolean hasActive();

	/**
	 * Récupère le chromino actif du joueur.
	 * 
	 * @return le chromino actif
	 */
	Chromino getActiveChromino();

	/**
	 * Récupère les coordonnées du chromino actif.
	 * 
	 * @return les coordonnées du chromino actif
	 */
	Coordinate getActiveCoordinate();

	/**
	 * Récupère la liste des chrominos en main.
	 * 
	 * @return une liste de chromino
	 */
	List<Chromino> getChrominoes();

	/**
	 * Récupère la couleur de bordure des chrominos actif.
	 * 
	 * @return la couleur de bordure
	 */
	String getBorderColor();

	/**
	 * Permet de cacher la coordonnée active sur le plateau
	 */
	void removeActiveCoordinate();

	/**
	 * Permet de retirer de sa main le chromino actif (lors de la pose de celui-ci)
	 */
	void removeActiveChromino();

	/**
	 * Permet de passer au chromino suivant de sa main
	 */
	void nextChromino();

	/**
	 * Permet d'initialiser la coordonnée active sur le plateau.
	 */
	void initActiveCoordinate();

	/**
	 * Prend le dernier chromino en main
	 */
	void selectLastChromino();

	/**
	 * Ajoute des points au joueur et les retourne.
	 * 
	 * @param points nombre de points à ajouter
	 */
	int points(int points);

	@Override
	int compareTo(Player o);

	/**
	 * Récupère les points.
	 * 
	 * @return les points du joueurs
	 */
	int getPoints();

}
