package chrominox.domains;

/**
 * Interface de création de jeu
 */
public interface Factory {
	
	/**
	 * Permet d'initialiser une partie.
	 * 
	 * @param selected le mode de jeu sélectionné (classique -> 0 ; rapide -> 1)
	 */
	void createGame(final int selected);

	/**
	 * @return le jeu actuel
	 */
	ChroGame getGame();

	/**
	 * @return le type de partie créée, -1 si aucune
	 */
	int getTypeOfGame();

}
