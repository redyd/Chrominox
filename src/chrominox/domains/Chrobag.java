package chrominox.domains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Représente un sac de chromino mélangé
 */
public final class Chrobag implements Bag, ChrominoGenerator {

	/**
	 * Collection qui va stocker les éléments du sac.
	 * <hr>
	 * <h1>It-1-q3</h1>
	 * <ol>
	 * <li>Choix d'interface de la collection : <br>
	 * J'ai choisi une {@link Queue}, car les opérations sur le sac sont les
	 * suivants:
	 * <ul>
	 * <li>Initialiser le sac</li>
	 * <li>Tirer un chromino (et donc le supprimer de la liste</li>
	 * </ul>
	 * En partant du principe que je mélange le sac à son initialisation, je n'ai
	 * pas besoin de faire des accès séquentiels avec du {@code random} ou autre: je
	 * n'ai qu'à prendre le premier élément de la liste en le supprimant (ce que une
	 * {@link Queue} fait très bien avec l'opération {@code poll}
	 * <li>Choix de l'implémentation concrète : <br>
	 * J'ai choisi d'utiliser une {@link LinkedList} car l'opération d'accès et de
	 * suppression du premier élément bien plus rapide que une {@link ArrayDeque}.
	 * La CTT d'accès du premier élémment est en {@code O(1)} pour les deux
	 * implémentations, mais l'opération de suppression est en {@code O(n)} pour
	 * {@link ArrayDeque} tandis qu'elle est en {@code O(1)} pour {@link LinkedList}
	 * </li>
	 * </ol>
	 */
	private final Queue<Chromino> bag;

	/**
	 * Initialise le {@code bag} avec 80 chrominos générés.
	 */
	public Chrobag() {
		this.bag = new LinkedList<>(generateRandomChrominoes());
	}

	@Override
	public int size() {
		return bag.size();
	}

	@Override
	public Queue<Chromino> getBag() {
		return bag;
	}

	@Override
	public Chromino take() {
		return bag.poll();
	}

	@Override
	public List<Chromino> take(final int count) {
		final List<Chromino> list = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			list.add(bag.poll());
		}

		return list;
	}

	/**
	 * Génère une liste de chrominos dans un ordre aléatoire
	 * 
	 * @return
	 */
	public List<Chromino> generateRandomChrominoes() {
		List<Chromino> list = new ArrayList<>(generateChrominoes());
		Collections.shuffle(list);
		return list;
	}

	@Override
	public List<Chromino> generateChrominoes() {
		final TileType[] colors = Arrays.copyOfRange(TileType.values(), 1, TileType.values().length);
		final List<Chromino> results = new ArrayList<>(80);

		for (int i = 0; i < colors.length; i++) {
			for (int j = i; j < colors.length; j++) {
				for (TileType current : colors) {
					results.add(new Chromino(colors[i], current, colors[j]));
				}
			}
		}

		results.addAll(generateCameleon(colors, 5));

		return results;
	}

	/**
	 * Permet de générer une liste de caméléons différents.
	 * 
	 * @return une liste de caméléon
	 */
	private List<Chromino> generateCameleon(final TileType[] colors, final int nbCameleon) {
		Set<Chromino> temp = new HashSet<>();
		List<Chromino> results = new ArrayList<>(nbCameleon);
		
		for (TileType color1 : colors) {
			for (TileType color2 : colors) {
				if (color1 != color2) {
					temp.add(new Chromino(color1, TileType.CAMELEON, color2));
				}
			}
		}

		int count = 0;
		for (Chromino chromino : temp) {
			if (count == nbCameleon) {
				break;
			}
			results.add(chromino);
			count++;
		}

		return results;
	}

	@Override
	public boolean empty() {
		return bag.size() == 0;
	}

}
