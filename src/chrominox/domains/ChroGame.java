package chrominox.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Classe représentant un jeu de chromino
 */
public final class ChroGame {

	private final ChroMosaic mosaic;
	private final Bag bag;
	private final List<Player> players;
	private final Player starter;
	
	private boolean allowToPick;
	private boolean roundPlayed;
	private Player activePlayer;

	/**
	 * Initialise une partie et distribue les chrominos aux joueurs.
	 * 
	 * @param bag      Sac contenant des chrominos
	 * @param handSize la taille des mains des joueurs
	 * @param players  les joueurs de la partie
	 */
	public ChroGame(final Bag bag, final int handSize, final Player... players) {
		this.mosaic = new ChroMosaic();
		this.players = new ArrayList<>(List.of(players));
		this.bag = bag;
		this.allowToPick = true;
		this.roundPlayed = false;

		initPlayers(handSize);

		this.activePlayer = this.starter = this.players.get(new Random().nextInt(this.players.size()));
	}

	/**
	 * Vérifie si la partie doit se terminer.
	 * 
	 * @param type le type de partie joué
	 * @return
	 */
	public boolean shouldEnded(int type) {
		return endedByEmptyHand() || endedByRound(type);
	}

	/**
	 * Vérifie si la partie doit se terminer car un joueur n'a plus de chromino en
	 * main.
	 * 
	 * @return {@code true} si la partie doit se terminer
	 */
	private boolean endedByEmptyHand() {
		if (endOfRound()) {
			for (Player player : players) {
				if (player.getChrominoes().size() == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return {@code true} si le tour est terminé
	 */
	public boolean endOfRound() {
		return activePlayer.equals(getLastPlayer());
	}

	private Player getLastPlayer() {
		return players.get(((players.indexOf(starter) - 1) + players.size()) % players.size());
	}

	/**
	 * Vérifie si la partie doit se terminer ou non.
	 * 
	 * @param type
	 * @return
	 */
	private boolean endedByRound(int type) {
		if (endOfRound()) {
			return switch (type) {
			case 0 -> classicEnd();
			case 1 -> shortEnd();
			default -> throw new IllegalArgumentException("Le type de partie fourni est invalide");
			};
		}

		return false;
	}
	
	/**
	 * @return {@code false} si la partie classique doit se terminer
	 */
	private boolean classicEnd() {
		return 
				bag.empty()
				&& !roundPlayed;
	}

	/**
	 * @return {@code true} si la partie courte doit se terminer
	 */
	private boolean shortEnd() {
		return 
				bag.empty() 
				|| !roundPlayed;
	}

	/**
	 * @return {@code true} si on autorise la pioche d'un chromino
	 */
	public boolean canPick() {
		return allowToPick;
	}

	/**
	 * @return la map de la mosaic
	 */
	public Map<Coordinate, Chromino> getMap() {
		return mosaic.getMap();
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	/**
	 * Permet de passer au joueur suivant.
	 * 
	 * @return le joueur suivant
	 */
	public Player nextPlayer() {
		this.allowToPick = true;
		final int nextIndex = (players.indexOf(activePlayer) + 1) % players.size();
		this.activePlayer = players.get(nextIndex);

		if (activePlayer.equals(starter)) {
			roundPlayed = false;
		}

		return activePlayer;
	}

	/**
	 * Permet de mettre un chromino dans la mosaïque.
	 * 
	 * @param coordinate coordonnées du chromino
	 * @param chromino   le chromino
	 * @return {@code true} si le chromino a été ajouté
	 */
	public Validation addToMosaic(final Chromino chromino, final Coordinate coordinate) {
		Objects.requireNonNull(chromino);
		Objects.requireNonNull(coordinate);

		int expectedScore = mosaic.calculScoreFor(chromino, coordinate);

		Validation state = this.mosaic.put(chromino, coordinate);
		if (state.equals(Validation.SUCCESS)) {
			activePlayer.points(expectedScore);
			roundPlayed = true;
		}

		return state;
	}

	/**
	 * Permet d'ajouter un chromino sans vérification de placement.
	 * 
	 * @param chromino
	 * @param coordinate
	 */
	public void bruteAdd(final Chromino chromino, final Coordinate coordinate) {
		Objects.requireNonNull(chromino);
		Objects.requireNonNull(coordinate);

		this.mosaic.bruteAdd(chromino, coordinate);
	}

	/**
	 * Permet d'initialiser la main d'un joueur.
	 * 
	 * @param handSize
	 */
	private void initPlayers(final int handSize) {
		for (final Player player : players) {
			player.init(bag, handSize);
		}
	}

	/**
	 * Fais piocher le joueur actuel.
	 */
	public boolean give() {
		if (allowToPick) {
			resetPick();
			return activePlayer.take(bag);
		}
		return false;
	}

	/**
	 * Empêche de piocher.
	 */
	public void resetPick() {
		this.allowToPick = false;
	}

	/**
	 * Permet de retirer à tous les joueurs la valeur des chrominos restant.
	 */
	public void removeRemainingPoints() {
		for (Player player : players) {
			int toRemove = 0;
			for (Chromino chromino : player.getChrominoes()) {
				toRemove += chromino.points();
			}
			player.points(-toRemove);
		}

	}

}
