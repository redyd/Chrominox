package chrominox.swing;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import chrominox.swing.viewmodels.PlayerColorViewModel;

public class SwingScoreView extends JPanel {
	private static final long serialVersionUID = -7033330803874528386L;
	private final JLabel playerNameLabel;
	private final JLabel scoreLabel;
	
	public SwingScoreView(String playerName, int score, String color) {
		this.setLayout(new BorderLayout(16, 16));
		this.setBackground(PlayerColorViewModel.valueOf(color).getColor());
		
		playerNameLabel= new JLabel(playerName, SwingConstants.CENTER);	
		playerNameLabel.setForeground(Theme.PRIMARY_COLOR);
		playerNameLabel.setFont(Theme.ITEM_FONT);
		
		scoreLabel = new JLabel(String.format("%02d", score), SwingConstants.CENTER);
		scoreLabel.setForeground(Theme.PRIMARY_COLOR);
		scoreLabel.setFont(Theme.ITEM_FONT);
		scoreLabel.setFont(Theme.ITEM_FONT.deriveFont(Theme.ITEM_FONT.getSize2D()*2.0f));
		
		add(playerNameLabel, BorderLayout.PAGE_START);
		add(scoreLabel,BorderLayout.CENTER);
		
		setBorder(new LineBorder(PlayerColorViewModel.valueOf(color).getColor(), 4, true));
	}
}
