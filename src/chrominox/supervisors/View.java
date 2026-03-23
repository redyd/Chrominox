package chrominox.supervisors;

import chrominox.supervisors.commons.ViewId;

/**
 * Interface d'une vues
 */
public interface View {
	/**
	 * Retourne le nom de cette vue.
	 * */
	String getName();
	
	/**
	 * Méthode appelée par un superviseur pour demander de naviguer de cet écran vers l'ecran {@code screenName}.
	 * */
	void goTo(ViewId screenName);
	
	/**
	 * Méthode appelée par la fenêtre principale avant que cet écran ne devienne l'écran courant.
	 * */
	void onEnter(ViewId fromScreen);
	
	/**
	 * Méthode appelée par la fenêtre principale avant que cet écran ne soit plus l'écran courant.
	 * */
	void onLeave(ViewId toScreen);
	
	/**
	 * Méthode appelée pour confirmer une demande de quitter
	 * */
	void onQuitConfirmed(ViewId fromScreen);
}
