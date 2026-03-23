package chrominox.supervisors;

import java.util.*;

import chrominox.domains.Factory;
import chrominox.supervisors.commons.ViewId;

/**
 * Superviseur du menu principal
 */
public final class MainMenuSupervisor extends Supervisor {
	
	public static final String NEW_CLASSIC_GAME = "Partie classique";
	public static final String NEW_SHORT_GAME = "Partie rapide";
	public static final String QUIT = "Quitter";
	
	private final List<String> items = List.of(NEW_CLASSIC_GAME, NEW_SHORT_GAME, QUIT);
	private final Factory factory;
	private MainMenuView view;
	
	/**
	 * Constructeur.
	 * 
	 * @param factory La factory qui va se chrager de créer la partie
	 */
	public MainMenuSupervisor(Factory factory) {
		this.factory = Objects.requireNonNull(factory);
	}

	public void setView(MainMenuView screen) {
		this.view = screen;
		this.view.setItems(items);
	}

	/**
	 * Méthode appelée par la vue quand l'utilisateur a choisi un item.
	 * 
	 * @param selected la position de l'item dans la liste reçue par la vue.
	 * */
	public void onItemSelected(int selected) {
		Objects.checkIndex(selected, items.size());
		var item = items.get(selected);
		
		if(QUIT.equals(item)) {
			this.view.onQuitConfirmed(ViewId.MAIN_MENU);
		} else {
			factory.createGame(selected);
			this.view.goTo(ViewId.PLAY_GAME);
		}
		
	}


}
