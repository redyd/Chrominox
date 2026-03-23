package chrominox.supervisors;

import chrominox.supervisors.commons.ViewId;

/**
 * Décrit comment le superviseur de fin de partie alimente sa vue en données.
 * */
public interface GameOverView {
	/**
	 * Débute le rafraichissement de l'écran
	 * */
	void startDraw();
	
	/**
	 * Affiche la paire {@code playName -> score} dans la couleur {@code color}.
	 * */
	void addScore(String playerName, int score, String color);
	
	/**
	 * Affiche le message désigne le vainqueur.
	 * */
	void setWinner(String winnerMessage);
	
	
	/**
	 * Termine le rendu.
	 * */
	void endDraw();
	
	/**
	 * Méthode appelée par un superviseur pour demander de naviguer de cet écran vers l'ecran {@code toView}.
	 * */
	void goTo(ViewId toView);
}
