package chrominox.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Représente un chromino
 */
public final class Chromino {

	/**
	 * Représente toutes les couleurs du chromino.
	 */
	private final List<TileType> parts;

	/**
	 * Construit le chromino {@code (part1, part2, part3)}.
	 * 
	 * @param part1 le type du premier bloc, non-null
	 * @param part2 le type du second bloc, non-null
	 * @param part3 le type du troisième bloc, non-null
	 * @throws NullPointerException si un des arguments vaut {@code null}.
	 */
	public Chromino(final TileType part1, final TileType part2, final TileType part3) {
		if (part1 == null || part2 == null || part3 == null) {
			throw new IllegalArgumentException("Une des couleurs est nulle.");
		}

		parts = new ArrayList<>(List.of(part1, part2, part3));
	}

	@Override
	public String toString() {
		return "Chromino [part1=" + parts.get(0) + ", part2=" + parts.get(1) + ", part3=" + parts.get(2) + "]";
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (TileType tile : parts) {
			hash += tile.hashCode();
		}

		return hash;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (!(other instanceof Chromino)) {
			return false;
		}

		Chromino toC = (Chromino) other;

		return equals(toC.parts.get(0), toC.parts.get(1), toC.parts.get(2));
	}

	/**
	 * Redéfinissions de l'égalité pour obtenir {@code true} en cas de chromino en
	 * miroir.
	 * 
	 * @param part1 couleur 1
	 * @param part2 couleur 2
	 * @param part3 couleur 3
	 * @return {@code true} si le chromino possède les mêmes couleurs ou des
	 *         couleurs en miroir. <br>
	 *         Exemple: RED - GREEN - BLUE == BLUE - GREEN - RED
	 */
	public boolean equals(final TileType part1, final TileType part2, final TileType part3) {
		if (this.parts.get(1) != part2) {
			return false;
		}

		return (this.parts.get(0) == part1 && this.parts.get(2) == part3)
				|| (this.parts.get(0) == part3 && this.parts.get(2) == part1);
	}

	/**
	 * Permet de créer un nouveau chromino avec ses couleurs inversées par rapport
	 * au chromino actuel.
	 * 
	 * @return le chromino retourné
	 */
	public Chromino reversed() {
		return new Chromino(parts.get(2), parts.get(1), parts.get(0));
	}

	/**
	 * Permet de déterminer si le chromino est un caméléon valide.
	 * 
	 * @return {@code true} si le chromino est un caméléon valide
	 */
	public boolean isCameleon() {
		return parts.indexOf(TileType.CAMELEON) == 1 && parts.lastIndexOf(TileType.CAMELEON) == 1
				&& parts.get(0) != parts.get(2);
	}

	/**
	 * Permet de déterminer si toutes les couleurs du chromino sont différentes.
	 * 
	 * @return {@code true} si toutes les pièces sont différentes
	 */
	public boolean hasDifferentTiles() {
		for (int i = 0; i < parts.size(); i++) {
			for (int j = i + 1; j < parts.size(); j++) {
				if (parts.get(i).equals(parts.get(j))) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Permet d'obtenir une couleur à la position {@code i}.
	 * 
	 * @param position position de la tuiles
	 * @return la couleur de la tuile à la position donnée
	 */
	public TileType getTileAt(final int position) {
		Objects.checkIndex(position, parts.size());

		return parts.get(position);
	}

	/**
	 * Permet d'obtenir la couleur à la position {@code i} du chromino.
	 * 
	 * @param position la position de la couleur sur le chromino
	 * @return la couleur à la position {@code i} du chromino
	 */
	public String getColorAt(final int position) {
		return getTileAt(position).toString();
	}
	
	/**
	 * Calcul la valeur d'un chromino.
	 * 
	 * @return la valeur du chromino
	 */
	public int points() {
		return Set.copyOf(parts).size();
	}

}
