package chrominox.supervisors;

import chrominox.supervisors.commons.ViewId;

/**
 * Classe héritée par tous les superviseurs pour réagir aux événements de navigation.
 * */
public abstract class Supervisor {
	
	/**
	 * Exécute des instructions avant que la fenêtre principale ne passe de la vue {@code fromView} à la vue
	 * de ce superviseur.
	 * 
	 * @param fromView le nom de la vue que l'application a quitté.
	 * */
	public void onEnter(ViewId fromView) {
		
	}
	
	/**
	 * Exécute des instructions avant que la fenêtre principale ne quitte ce superviseur pour afficher la vue {@code toView}.
	 * 
	 * @param toView le nom de la vue que l'application va afficher.
	 * */
	public void onLeave(ViewId toView) {
		
	}
}
