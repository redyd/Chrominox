package chrominox.domains;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Indique comment un sac doit se comporter.
 */
public interface Bag {
	
	/**
	 * Récupère la taille du sac.
	 * 
	 * @return la taille du sac
	 */
	int size();
	
	/**
	 * Pioche un chromino aléatoirement dans le sac.
	 * 
	 * @return un chromino
	 */
	Chromino take();
	
	/**
	 * Permet de piocher un nombre de chromino.
	 * 
	 * @param count le nombre de chromino à piocher
	 * @return une liste de chrominos
	 */
	List<Chromino> take(int count);
	
	/**
	 * Permet de tirer un chromino Caméléon.
	 * 
	 * @return un chromino caméléon, sinon {@code null}
	 */
	default Chromino takeCameleon() {
		final Queue<Chromino> bag = getBag();
		
		for (final Chromino chromino : bag) {
			if (chromino.isCameleon()) {
				bag.remove(chromino);
				return chromino;
			}
		}
		
		throw new NoSuchElementException("Aucun caméléon a été trouvé.");
	}
	
	/**
	 * Récupère une référence vers le {@code bag}.
	 * 
	 * @return le {@code bag}
	 */
	Queue<Chromino> getBag();

	/**
	 * @return {@code true} si le sac est vide
	 */
	boolean empty();

}
