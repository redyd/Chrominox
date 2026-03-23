package chrominox.swing.viewmodels;

public final class DominoViewModel {

	private final TileColorViewModel firstColor;
	private final TileColorViewModel secondColor;
	private final TileColorViewModel thirdColor;
	private final PlayerColorViewModel borderColor;

	public DominoViewModel(String firstColor, String secondColor, String thirdColor, String borderColor) {
		this(TileColorViewModel.valueOf(firstColor.toUpperCase()), 
				TileColorViewModel.valueOf(secondColor.toUpperCase()), 
				TileColorViewModel.valueOf(thirdColor.toUpperCase()), 
				PlayerColorViewModel.valueOf(borderColor.toUpperCase()));

	}

	public DominoViewModel(TileColorViewModel firstColor, TileColorViewModel secondColor, TileColorViewModel thirdColor, PlayerColorViewModel borderColor) {
		this.firstColor = firstColor;
		this.secondColor = secondColor;
		this.thirdColor = thirdColor;
		this.borderColor = borderColor;
	}

	public TileColorViewModel getFirstColor() {
		return this.firstColor;
	}

	public TileColorViewModel getSecondColor() {
		return secondColor;
	}

	public TileColorViewModel getThirdColor() {
		return thirdColor;
	}

	public PlayerColorViewModel getBorderColor() {
		return borderColor;
	}
}
