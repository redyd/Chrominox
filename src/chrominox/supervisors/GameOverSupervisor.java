package chrominox.supervisors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import chrominox.domains.ChroGame;
import chrominox.domains.Factory;
import chrominox.domains.Player;
import chrominox.supervisors.commons.ViewId;

/**
 * Affiche les scores des joueurs et désigne le vainqueur.
 * */
public class GameOverSupervisor extends Supervisor {
	
	private static final String WINNER = "Le gagnant est %s avec %d points !";
	private static final String EQUALTITY = "%s gagnent à égalité avec %d points !";
	private static final int SINGLE_WINNER = 1;
	
	private final Factory factory;
	private GameOverView view;
	
	/**
	 * Constructeur.
	 * 
	 * @param factory La factory qui va se chrager de créer la partie
	 */
	public GameOverSupervisor(Factory factory) {
		this.factory = Objects.requireNonNull(factory);
	}

	/**
	 * Change la vue.
	 * 
	 * @param view vue de fin de partie
	 */
	public void setView(GameOverView view) {
		this.view = view;
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void onEnter(ViewId fromView) {
		if(fromView == ViewId.PLAY_GAME) {
			this.view.startDraw();
			
			ChroGame game = factory.getGame();
			game.removeRemainingPoints();
			
			List<Player> winners = new ArrayList<>(game.getPlayers());
			Collections.sort(winners, Collections.reverseOrder());
			int countWinners = displayScore(winners);
			
			if (countWinners == SINGLE_WINNER) {
				this.view.setWinner(WINNER.formatted(winners.getFirst().getName(), winners.getFirst().getPoints()));
			} else {
				this.view.setWinner(buildWinnersMessage(winners, countWinners));
			}
			
			this.view.endDraw();
		}
	}

	private int displayScore(List<Player> winners) {
		int countWinners = 0;
		
		for (Player player : winners) {
			if (player.getPoints() == winners.getFirst().getPoints()) {
				countWinners++;
			}
			
			this.view.addScore(player.getName(), player.getPoints(), player.getBorderColor().toUpperCase());
		}
		
		return countWinners;
	}

	private String buildWinnersMessage(List<Player> winners, int countWinners) {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < countWinners; i++) {
			builder.append(winners.get(i).getName());
			builder.append(" & ");
		}
		
		builder.delete(builder.length() - 3, builder.length());
		
		return EQUALTITY.formatted(builder.toString(), winners.getFirst().getPoints());
	}

	/**
	 * Appelée par la vue quand l'utilisateur souhaite revenir au menu principal.
	 * */
	public void onGoToMainMenu() {
		view.goTo(ViewId.MAIN_MENU);
	}
}
