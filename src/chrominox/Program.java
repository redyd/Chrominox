/**
 * Contient tout le code de l'application chrominox.
 */
package chrominox;

import java.util.List;


import chrominox.swing.*;
import chrominox.domains.ChroGameFactory;
import chrominox.supervisors.*;
import chrominox.supervisors.commons.ViewId;

/**
 * Construit l'application et lance son exécution.
 * 
 * @author Nicolas Hendrikx
 */
public final class Program {
	
	private Program() {
		
	}

	/**
	 * Point d'entrée d'une exécution.
	 * L'application ne tient pas compte des arguments.
	 * 
	 * @param args une liste d'arguments. 
	 */
	public static void main(String[] args) {
		final ChroGameFactory factory = new ChroGameFactory();
		final MainWindow window = new MainWindow("Ai 2025 - chrominox - Soeur Timëo", List.of(
			new SwingMainMenuView(new MainMenuSupervisor(factory)),
			new SwingPlayGameView(new PlayGameSupervisor(factory)),
			new SwingGameOverView(new GameOverSupervisor(factory)))
		);
		window.start(ViewId.MAIN_MENU);
	}

}
