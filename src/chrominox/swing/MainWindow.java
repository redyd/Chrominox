/**
 * Implémente les interfaces utilisateur de l'application avec Swing.
 * */
package chrominox.swing;

import java.util.*;

import javax.swing.JFrame;

import org.helmo.swing.SwingView;
import org.helmo.swing.adapter.MyKeyAdapter;

import chrominox.supervisors.commons.ViewId;

/**
 * Affiche les écrans qui composent l'application.
 * @author Nicolas Hendrikx
 */

public final class MainWindow extends JFrame {
	private static final long serialVersionUID = 6621287528953860235L;
	
	private final Map<ViewId, SwingView<ViewId>> nameToScreen = new LinkedHashMap<>();
	private SwingView<ViewId> current;
	
	/**
	 * Initialise cette fenêtre principale avec un titre et des écrans.
	 * @param title le titre affiché par cette fenêtre principale.
	 */
	public MainWindow(String title, List<SwingView<ViewId>> screens) {
		super(title);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Theme.WINDOW_WIDTH, Theme.WINDOW_HEIGHT);
		setBackground(Theme.PRIMARY_BACKGROUND_COLOR);
		setFont(Theme.NORMAL_FONT);
		setResizable(false);
		
		Objects.requireNonNull(screens,"You must provide a non-empty list of views");
		for(var screen : screens) {
			Objects.requireNonNull(screen,"You must provide defined view");
			this.nameToScreen.put(screen.getViewId(), screen);
			screen.setRouter(this::goTo);
		}
		
		this.addKeyListener(new MyKeyAdapter(this::onKeyTyped));
	}

	/**
	 * Permet d'afficher un écran
	 * @param screenTitle
	 */
	public void goTo(ViewId screenTitle) {
		var found = getScreen(screenTitle);
		
		if(current != null) {
			current.onLeave(screenTitle);
			found.onEnter(current.getViewId());
		} else {
			found.onEnter(ViewId.NONE);
		}
		
		this.current = found;		
		this.setContentPane(current);
	}

	private SwingView<ViewId> getScreen(ViewId screenName) {
		if(!nameToScreen.containsKey(screenName)) {
			throw new IllegalArgumentException("screenName["+screenName+"] does not match any registered screen name.");
		}
		return nameToScreen.get(screenName);
	}

	/**
	 * Permet de démarrer un affcihage
	 * @param screenName
	 */
	public void start(ViewId screenName) {
		this.goTo(screenName);
		this.setVisible(true);
	}
	
	private void onKeyTyped(int keyCode) {
		if(current == null) {
			return;
		}
		this.current.onKeyTyped(keyCode);		
	}
	
}
