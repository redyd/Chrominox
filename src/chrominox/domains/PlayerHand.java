package chrominox.domains;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Représente la main d'un joueur contenant des chrominos
 */
public final class PlayerHand {

	private final List<Chromino> chrominoes;
	private int active;

	/**
	 * Initialise une main vide.
	 */
	public PlayerHand() {
		this(List.of());
	}

	/**
	 * Initialise la main avec une collection de chrominos.
	 * 
	 * @param chrominoes les chrominos à placer en main
	 */
	public PlayerHand(final Collection<Chromino> chrominoes) {
		Objects.requireNonNull(chrominoes);

		this.chrominoes = new ArrayList<>(List.copyOf(chrominoes));
		this.active = 0;
	}

	public List<Chromino> getChrominoes() {
		return List.copyOf(chrominoes);
	}

	/**
	 * Récupère le nombre de chromino en main.
	 * 
	 * @return le nombre de chromino en main
	 */
	public int size() {
		return chrominoes.size();
	}

	/**
	 * Permet de passer au chromino suivant.
	 */
	public void nextChromino() {
		active = (active + 1) % size();
	}

	/**
	 * Permet de récupérer le chromino actuel.
	 * 
	 * @return le chromino actuel
	 * @throws IndexOutOfBoundsException si la liste est vide
	 */
	public Chromino getActive() {
		Objects.checkIndex(active, size());

		return chrominoes.get(active);
	}

	/**
	 * Permte d'obtenir le {@code n}<sup>{@code ième}</sup> chromino de la main.
	 * 
	 * @param pos position du chromino dans la main
	 * @return le chromino à la position {@code pos}
	 * @throws IndexOutOfBoundsException si l'index passé est hors de la main
	 */
	public Chromino get(final int pos) {
		Objects.checkIndex(pos, size());

		return chrominoes.get(pos);
	}

	/**
	 * Permet d'ajouter un chromino à la main.
	 * 
	 * @param toAdd le chromino à ajouter
	 * @return {@code true} si le chromino a été ajouté, sinon {@code false}
	 */
	public boolean add(final Chromino toAdd) {
		Objects.requireNonNull(toAdd);

		return chrominoes.add(toAdd);
	}

	/**
	 * Permet d'ajouter des chrominos à la main.
	 * 
	 * @param toAdd une collection de chromino
	 * @return {@code true} si les chrominos ont été ajoutés, sinon {@code false}
	 */
	public boolean add(final Collection<Chromino> toAdd) {
		Objects.requireNonNull(toAdd);

		return chrominoes.addAll(toAdd);
	}

	/**
	 * Permet de vider la main
	 */
	public void clear() {
		chrominoes.clear();
		active = 0;
	}

	/**
	 * Permet de retirer de la liste le chromino actif.
	 * 
	 * @return le chromino supprimé
	 */
	public Chromino removeActive() {
		Objects.checkIndex(active, size());

		final int active = this.active;
		this.active = 0;

		return chrominoes.remove(active);
	}

	/**
	 * Sélectionne le dernier chromino de la main
	 */
	public void selectLastChrominoOnActive() {
		active = chrominoes.size() - 1;
	}

}
