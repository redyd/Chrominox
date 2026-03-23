package chrominox.domains;

import java.util.List;

/**
 * Gère la création d'une partie de chrominox
 */
public final class ChroGameFactory implements Factory {

	public static final Coordinate DEFAULT_CO = new Coordinate(new Point(1, 0), new Point(0, 0), new Point(-1, 0));
	private static final int NB_PLAYERS = 2;
	private static final String[] COLORS = { "orange", "pink" };
	private static final String PLAYER = "joueur %d";

	private Bag bag;
	private ChroGame game;
	private int type = -1;

	@Override
	public void createGame(final int selected) {
		final int handSize = switch (selected) {
		case 0 -> 10;
		case 1 -> 5;
		default -> throw new IllegalArgumentException("La partie sélectionnée est invalide");
		};

		this.type = selected;
		this.bag = new Chrobag();
		this.game = new ChroGame(bag, handSize, createPlayers());

		this.game.bruteAdd(bag.takeCameleon(), DEFAULT_CO);
	}

	private Player[] createPlayers() {
		Player[] players = new Player[NB_PLAYERS];
		for (int i = 0; i < NB_PLAYERS; i++) {
			players[i] = new ChroPlayer(PLAYER.formatted(i + 1), COLORS[i % COLORS.length]);
		}
		return players;
	}

	@Override
	public ChroGame getGame() {
		return game;
	}

	@Override
	public int getTypeOfGame() {
		return type;
	}

	/**
	 * @return une copie de la liste des joueurs
	 */
	public List<Player> getPlayer() {
		return List.copyOf(game.getPlayers());
	}

	public Bag getBag() {
		return bag;
	}

}
