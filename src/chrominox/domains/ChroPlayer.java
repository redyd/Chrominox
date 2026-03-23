package chrominox.domains;

import java.util.List;
import java.util.Objects;

/**
 * Représente un joueur de chromino.
 */
public final class ChroPlayer implements Player {

	private static final String NO_NAME = "NO_NAME";

	private final String name;
	private final String borderColor;
	private final PlayerHand hand;
	private Coordinate activeCoordinate;
	private int score;

	/**
	 * Crée un joueur avec un nom, une couleur de bordure lors de la sélection du
	 * chromino en main, et initialise la coordonnée active au centre.
	 * 
	 * @param name        le nom du joueur
	 * @param borderColor couleur de bordure du chromino lors de sa sélection
	 */
	public ChroPlayer(final String name, final String borderColor) {
		this.name = Objects.requireNonNullElse(name, NO_NAME);
		this.hand = new PlayerHand();
		this.borderColor = borderColor;
		this.activeCoordinate = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));
		this.score = 0;
	}

	@Override
	public void selectLastChromino() {
		hand.selectLastChrominoOnActive();
	}
	
	@Override
	public int points(int points) {
		this.score += points;
		return this.score;
	}
	
	@Override
	public int getPoints() {
		return score;
	}

	@Override
	public void init(final Bag bag, final int handSize) {
		hand.clear();
		take(bag, handSize);
	}

	@Override
	public boolean take(final Bag bag) {
		return bag.size() != 0 && hand.add(bag.take());
	}

	/**
	 * Permet d'ajouter à la main un nombre de chromino.
	 * 
	 * @param count le nombre de chromino à ajouter
	 * @return {@code true} si les chrominos ont été ajoutés
	 */
	private boolean take(final Bag bag, final int count) {
		return hand.add(bag.take(count));
	}

	@Override
	public void nextChromino() {
		hand.nextChromino();
	}

	@Override
	public List<Chromino> getChrominoes() {
		return hand.getChrominoes();
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}

	@Override
	public Chromino getActiveChromino() {
		return hand.getActive();
	}

	@Override
	public Coordinate getActiveCoordinate() {
		return activeCoordinate;
	}

	@Override
	public boolean hasActive() {
		return activeCoordinate.isVisible() && hand.size() != 0;
	}

	@Override
	public void initActiveCoordinate() {
		this.activeCoordinate = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));
		this.activeCoordinate.show();
	}

	@Override
	public void removeActiveCoordinate() {
		this.activeCoordinate.hide();
	}

	@Override
	public void removeActiveChromino() {
		hand.removeActive();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Player o) {
		return Integer.compare(getPoints(), o.getPoints());
	}

}
