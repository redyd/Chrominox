package chrominox.swing.viewmodels;

import java.awt.Color;


public enum TileColorViewModel {
	CAMELEON(Color.WHITE), CYAN(Color.CYAN), GREEN(Color.GREEN), MAGENTA(Color.MAGENTA), RED(Color.RED), YELLOW(Color.YELLOW);

	private final Color color;

	TileColorViewModel(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
}
