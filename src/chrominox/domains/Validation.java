package chrominox.domains;

/**
 * Enumération de message de validation lors d'une tentative d'ajout de chromino
 * au jeu
 */
public enum Validation {

	SUCCESS(""), AT_LEAST_TWO("Le chromino doit être en contact avec au moins deux carreaux de la mosaïque"),
	ONE_TILE_WRONG("Un carreau ou plus est en contact avec un carreau de couleur différente"),
	ON_OTHER_CHROMINO("Il n'est pas possible d'ajouter un chromino sur un autre");

	private final String message;

	private Validation(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
