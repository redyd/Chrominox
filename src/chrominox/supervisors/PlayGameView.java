package chrominox.supervisors;

import chrominox.supervisors.commons.ViewId;

/**
 * Décrit comment le superviseur de jeu alimente sa vue en données.
 * */
public interface PlayGameView {
	
	/**
	 * Enum qui représente les deux mode de vue du jeu
	 */
	public enum ViewMode {
		BOARD, HAND
	}
	
	/**
	 * Débute le rafraichissement de l'écran
	 * */
	void startDraw();

	/**
	 * Change le mode d'interaction.
	 * <p>
	 * Deux modes sont prévus :
	 * <ul>
	 * <li>{@code HAND} pour modifier la main du joueur actif
	 * <li>{@code BOARD} pour ajouter le chromino actif à la mosaique
	 * </ul>
	 * </p>
	 * 
	 * @see PlayGameView.ViewMode
	 * */
	void switchMode(ViewMode mode);
	
	/**
	 * Dessine un chromino de la main du joueur à la suite des autres.
	 * <p>
	 * Le chromino est dessiné de haut en bas. Autrement dit, {@code firstColor} correspond au bloc du dessus
	 * tandis que {@code thirdColor} correspond au bloc du dessous.
	 * </p>
	 * */
	void addToHand(String firstColor, String secondColor, String thirdColor, String borderColor);

	/**
	 * Dessine un bloc à la position logique {@code row, col}.
	 * <p>
	 * Pour dessiner un chromino, vous devrez appeler trois fois cette méthode.
	 * </p>
	 * */
	void addToBoard(String color, String borderColor, int row, int col);
	
	
	/**
	 * Termine le rendu de l'écran
	 * */
	void endDraw();
	
	/**
	 * Ajoute le message à l'écran
	 * */
	void addMessage(String msg);

	/**
	 * Méthode appelée par un superviseur pour demander de naviguer de cet écran vers l'ecran {@code toView}.
	 * */
	void goTo(ViewId toView);
}
