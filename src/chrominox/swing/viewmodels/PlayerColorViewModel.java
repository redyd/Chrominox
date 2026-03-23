package chrominox.swing.viewmodels;

import java.awt.Color;


public enum PlayerColorViewModel {
	BLACK(Color.BLACK), ORANGE(Color.ORANGE), PINK(Color.PINK), WHITE(Color.WHITE), EMPTY(new Color(127,127,127, 127));

	private final Color color;

	PlayerColorViewModel(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
}
