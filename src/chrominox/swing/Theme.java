package chrominox.swing;

import java.awt.*;

import javax.swing.UIManager;

/**
 * Définit l'aspect général des écrans.
 * */
public final class Theme {
	static {
		 try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Dimensions
	public static final int WINDOW_WIDTH = 1200;
	public static final int WINDOW_HEIGHT = 800;
	public static final int PANEL_WIDTH = 1184;
	public static final int PANEL_HEIGHT = 760;
	public static final int TILE_SIZE = 32;
	
	//Police de caractères
	public static final Font ITEM_FONT = new Font("Ravie", Font.PLAIN, 24);
	public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 20);
	public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
	
	//Couleurs
	public static final Color PRIMARY_BACKGROUND_COLOR = new Color(234, 179, 179);
	public static final Color PRIMARY_BACKGROUND_COLOR_ALPHA =  new Color(234, 179, 179, 158);
	public static final Color SECONDARY_BACKGROUND_COLOR = new Color(111, 111, 150);
	public static final Color SECONDARY_BACKGROUND_COLOR_ALPHA =  new Color(111, 111, 150, 158);
	
	public static final Color PRIMARY_COLOR = new Color(253, 245, 223);

}
