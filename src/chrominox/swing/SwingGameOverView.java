package chrominox.swing;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Objects;

import javax.swing.*;

import org.helmo.swing.SwingView;

import chrominox.supervisors.*;
import chrominox.supervisors.commons.ViewId;

/**
 * 
 */
public class SwingGameOverView extends SwingView<ViewId> implements GameOverView {
	private static final long serialVersionUID = 7019400806587102065L;
	private final ImageIcon background = new ImageIcon("resources/images/main_menu_background.jpg");
	
	private static final int COL_COUNT = 2;
	private static final int ROW_COUNT = 2;

	private final GameOverSupervisor supervisor;

	private final JLabel winnerLabel = new JLabel("Appuyez sur \u23ce pour revenir au menu principal",SwingConstants.CENTER);
	{
		winnerLabel.setForeground(Theme.PRIMARY_COLOR);
		winnerLabel.setFont(winnerLabel.getFont().deriveFont(winnerLabel.getFont().getSize2D()*2.0f));		
	}
	private final JPanel scorePanel = new JPanel(new GridLayout(ROW_COUNT, COL_COUNT, 2, 2));
	{
		scorePanel.setBackground(Theme.PRIMARY_BACKGROUND_COLOR_ALPHA);
	}

	private final JPanel winnerPanel = new JPanel();
	{
		winnerPanel.setBackground(Theme.SECONDARY_BACKGROUND_COLOR_ALPHA);
	}
	
	/**
	 * Constructeur.
	 * 
	 * @param supervisor
	 */
	public SwingGameOverView(GameOverSupervisor supervisor) {
		super(ViewId.END_GAME);
		
		this.supervisor = Objects.requireNonNull(supervisor, "You must provide a defined GameOverSupervisor");
		this.supervisor.setView(this);
		
		this.setLayout(new BorderLayout());				
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		drawBackground(g);
	}

	private void drawBackground(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, null);
	}
	
	@Override
	public void onEnter(ViewId fromView) {
		this.supervisor.onEnter(fromView);
	}
	
	@Override
	public void onKeyTyped(int keyCode) {
		if(KeyEvent.VK_ENTER == keyCode) {
			supervisor.onGoToMainMenu();
		}
	}
	
	@Override
	public void startDraw() {
		this.removeAll();
		this.scorePanel.removeAll();
	}

	@Override
	public void addScore(String playerName, int score, String color) {
		this.scorePanel.add(new SwingScoreView(playerName, score, color));
	}

	@Override
	public void endDraw() {
		add(scorePanel, BorderLayout.CENTER);

		winnerPanel.add(winnerLabel);
		add(winnerPanel, BorderLayout.PAGE_END);
	}

	@Override
	public void setWinner(String winnerMessage) {
		this.winnerLabel.setText(winnerMessage+" Appuyez sur \u23ce pour revenir au menu principal");		
	}
	

}
