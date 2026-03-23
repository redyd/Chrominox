package chrominox.supervisors;

import java.util.Map;
import java.util.Objects;

import chrominox.domains.*;
import chrominox.supervisors.PlayGameView.ViewMode;
import chrominox.supervisors.commons.ViewId;

/**
 * Cette classe représente le superviseur de jeu
 */
public class PlayGameSupervisor extends Supervisor {

	private static final String BORDER = "BLACK";
	private static final String CHANGE_PLAYER = "Au tour de ";
	private static final String SCORE_PLAYER = "Score de %s: %d.";
	private static final String ALREADY_PICKED = "Vous ne pouvez pas piocher une deuxième fois";
	private static final String HAVE_TO_PICK = "Vous devez piocher un chromino ou en poser un pour passer votre tour";
		

	private final Factory factory;
	private ChroGame game;
	private Player activePlayer;
	private PlayGameView view;

	/**
	 * Constructeur du superviseur du jeu.
	 * 
	 * @param factory La factory qui va se chrager de créer la partie
	 */
	public PlayGameSupervisor(Factory factory) {
		this.factory = factory;
		this.game = factory.getGame();
	}

	public void setView(PlayGameView view) {
		this.view = Objects.requireNonNull(view);
	}

	@Override
	public void onEnter(ViewId fromScreen) {
		if (fromScreen == ViewId.MAIN_MENU) {
			this.game = factory.getGame();
			this.activePlayer = game.getActivePlayer();
			this.view.addMessage(CHANGE_PLAYER + activePlayer.getName());
			drawHand();
			drawBoard();
		}
		draw();
	}

	private void draw() {
		view.startDraw();

		drawBoard();
		drawHand();

		view.endDraw();
	}

	private void drawBoard() {
		var map = game.getMap();
		for (Map.Entry<Coordinate, Chromino> entry : map.entrySet()) {
			Coordinate coo = entry.getKey();
			Chromino chromino = entry.getValue();

			addToBoard(coo, chromino, BORDER);
		}

		if (activePlayer.hasActive()) {
			Chromino activeChromino = activePlayer.getActiveChromino();
			Coordinate activeCoordinate = activePlayer.getActiveCoordinate();
			addToBoard(activeCoordinate, activeChromino, BORDER);
		}

	}

	private void addToBoard(Coordinate coo, Chromino chromino, String color) {
		int i = 0;
		for (Point point : coo.getPoints()) {
			view.addToBoard(chromino.getColorAt(i), color, point.getX(), point.getY());
			i++;
		}
	}

	private void drawHand() {
		for (Chromino chromino : activePlayer.getChrominoes()) {
			var color = activePlayer.getActiveChromino().equals(chromino) ? activePlayer.getBorderColor() : BORDER;
			view.addToHand(chromino.getColorAt(0), chromino.getColorAt(1), chromino.getColorAt(2), color);
		}
	}

	/**
	 * Méthode appelée par la vue pour déplacer le chromino actif sur la mosaique de
	 * {@code dr} ligne et de {@code dc} colonnes.
	 */
	public void onMove(final int dr, final int dc) {
		activePlayer.getActiveCoordinate().move(dc, dr);
		draw();
	}

	/**
	 * Méthode appelée par la vue pour effectuer une rotation de 90 du chromino
	 * actif.
	 */
	public void onRotate() {
		if (activePlayer.hasActive()) {
			activePlayer.getActiveCoordinate().rotate();
			draw();
		}
	}

	/**
	 * Méthode appelée par la vue quand le joueur souhaite ajouter son chromino à la
	 * mosaique.
	 */
	public void onConfirm() {
		Validation state = game.addToMosaic(activePlayer.getActiveChromino(), activePlayer.getActiveCoordinate());

		if (activePlayer.hasActive() && state.equals(Validation.SUCCESS)) {
			activePlayer.removeActiveChromino();
			activePlayer.removeActiveCoordinate();
			game.resetPick();
			
			nextPlayer();

			view.switchMode(ViewMode.HAND);

			draw();
		}

		if (!state.toString().isEmpty()) {
			view.addMessage(state.toString());
		}
	}

	/**
	 * Méthode appelée par la vue quand le joueur souhaite passer son tour.
	 */
	public void onPass() {
		if (!game.canPick()) {
			nextPlayer();
		} else {
			view.addMessage(HAVE_TO_PICK);
		}
	}
	
	private void nextPlayer() {
		if (this.game.shouldEnded(factory.getTypeOfGame())) {
			view.endDraw();
			view.goTo(ViewId.END_GAME);
		}
		
		this.activePlayer.removeActiveCoordinate();
		this.activePlayer = game.nextPlayer();
		
		displayScore();
		draw();
	}

	/**
	 * Permet d'afficher les scores des joueurs.
	 */
	private void displayScore() {
		this.view.addMessage(CHANGE_PLAYER + activePlayer.getName());
		StringBuilder builder = new StringBuilder();
		for (Player player : game.getPlayers()) {
			builder.append(SCORE_PLAYER.formatted(player.getName(), player.getPoints())).append(" ");
		}
		
		this.view.addMessage(builder.toString());
	}

	/**
	 * Appelée par la vue quand le joueur souhaite passer au chromino suivant.
	 */
	public void onSelectNextPiece() {
		activePlayer.nextChromino();
		draw();
	}

	/**
	 * Appelée dans le mode {@code BOARD} si le joueur souhaite changer de pièce.
	 */
	public void onBack() {
		activePlayer.removeActiveCoordinate();
		view.switchMode(ViewMode.HAND);
		draw();
	}

	/**
	 * Appelée si le joueur souhaite piocher un nouveau chromino
	 */
	public void onPick() {
		if (game.canPick()) {
			game.give();
			activePlayer.selectLastChromino();
			onPieceSelected();
		} else {
			view.addMessage(ALREADY_PICKED);
		}
	}

	/**
	 * Appelée dans le mode {@code HAND} si le joueur a choisi son chromino.
	 */
	public void onPieceSelected() {
		activePlayer.initActiveCoordinate();
		view.switchMode(ViewMode.BOARD);
		draw();
	}

}
