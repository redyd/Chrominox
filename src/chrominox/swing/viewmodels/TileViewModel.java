package chrominox.swing.viewmodels;

import java.awt.Color;

public final class TileViewModel {

	private final TileColorViewModel color;
	private final PlayerColorViewModel borderColor;
	private final int y;
	private final int x;
	
	public TileViewModel(String hexColor, String hexBorderColor, int x, int y) {
		this.color = TileColorViewModel.valueOf(hexColor);
		this.borderColor = PlayerColorViewModel.valueOf(hexBorderColor);
		this.y = y;
		this.x = x;
	}
	
	public Color getColor() {
		return color.getColor();
	}
	
	public Color getBorderColor() {
		return borderColor.getColor();
	}

	public int getX() {
		return  x;
	}

	public int getY() {
		return  y;
	}

	public boolean isCameleon() {
		return color == TileColorViewModel.CAMELEON;
	}

}
