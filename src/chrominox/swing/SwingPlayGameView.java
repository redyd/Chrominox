package chrominox.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;

import org.helmo.swing.SwingView;

import chrominox.swing.viewmodels.*;
import chrominox.supervisors.*;
import chrominox.supervisors.commons.ViewId;

public class SwingPlayGameView extends SwingView<ViewId> implements PlayGameView {
	public static final int NUMBER_COLUMN = 18;
	private final ImageIcon background = new ImageIcon("resources/images/play_game_background.jpg");
	private static final long serialVersionUID = -8437344739545669731L;

	private final JTextPane pane;
	private final List<TileViewModel> gameboard = new ArrayList<>();
	private final List<DominoViewModel> hand = new ArrayList<>();
	private Rectangle pieceRect;

	private final PlayGameSupervisor supervisor;
	private final Deque<String> messages =  new ArrayDeque<>();
	private SwingViewMode mode;
	
	public SwingPlayGameView(PlayGameSupervisor supervisor) {
		super(ViewId.PLAY_GAME);

		this.supervisor = Objects.requireNonNull(supervisor, "You must provide a defined PlayGameSupervisor");
		this.supervisor.setView(this);
	
		
		int left = Theme.PANEL_WIDTH / 100;
		
		pane = new JTextPane();
		pane.setBounds(left, 608, Theme.PANEL_WIDTH - (Theme.PANEL_WIDTH / 100 * 3), 150);
		pane.setFocusable(false);
		
		this.add(pane);
	}

	@Override
	public void onEnter(ViewId fromScreen) {
		this.messages.clear();
		this.pane.setText("");
		supervisor.onEnter(fromScreen);
		
		mode = new HandViewMode();
	}

	@Override
	public void onLeave(ViewId toScreen) {
		supervisor.onLeave(toScreen);
	}

	@Override
	public void startDraw() {
		this.hand.clear();
		this.gameboard.clear();
	}

	@Override
	public void addToHand(String firstColor, String secondColor, String thirdColor, String borderColor) {
		hand.add(new DominoViewModel(firstColor, secondColor, thirdColor, borderColor));
	}

	@Override
	public void addToBoard(String color, String borderColor, int row, int col) {
		int left = Theme.PANEL_WIDTH / 100 * 2;
		int top = Theme.PANEL_HEIGHT / 100 * 2;
		int tileSize = Theme.TILE_SIZE;

		gameboard.add(new TileViewModel(color, borderColor, top + (col + NUMBER_COLUMN/2) * tileSize, left + (row + NUMBER_COLUMN/2) * tileSize));
	}
	
	@Override
	public void endDraw() {
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, Theme.PANEL_WIDTH, Theme.PANEL_HEIGHT, null);
		
		drawCommands(g);
		
		drawBoard(g);
		drawCurrentPos(g);
		drawHand(g);
	}

	private void drawCurrentPos(Graphics g) {
		if (pieceRect != null) {
			g.drawRect(pieceRect.x, pieceRect.y, pieceRect.width, pieceRect.height);
		}

	}

	private void drawBoard(Graphics g) {
		for (var tvm : this.gameboard) {
			g.setColor(tvm.getBorderColor());
			g.drawRect(tvm.getX(), tvm.getY(), Theme.TILE_SIZE-1, Theme.TILE_SIZE-1);
			g.setColor(tvm.getColor());
			g.fillRect(tvm.getX()+1, tvm.getY()+1, Theme.TILE_SIZE-3, Theme.TILE_SIZE-3);
			if(tvm.isCameleon()) {
				g.setColor(Color.BLACK);
				Font temp = g.getFont();
				g.setFont(new Font(temp.getName(), temp.getStyle(), temp.getSize()*2));
				g.drawString("C", tvm.getX() + Theme.TILE_SIZE/4, tvm.getY() + Theme.TILE_SIZE/4*3);
				g.setFont(temp);
			}
		}
	}

	private void drawHand(Graphics g) {
		int tileSize = Theme.TILE_SIZE;
		int left = Theme.PANEL_WIDTH / 2 + Theme.PANEL_WIDTH / 100 * 2;
		int top = Theme.PANEL_HEIGHT / 100 * 8;

		g.setColor(Theme.PRIMARY_COLOR);
		g.drawString("PLAYER HAND", left, top - Theme.PANEL_WIDTH / 100);

		for (var dvm : this.hand) {
			g.setColor(dvm.getBorderColor().getColor());
			g.drawRect(left - 2, top - 2, tileSize + 4, 3 * tileSize + 4);

			drawTile(g, left, top, tileSize, dvm);

			left += tileSize + 5;
		}
	}

	private void drawTile(Graphics g, int left, int top, int tileSize, DominoViewModel dvm) {
		g.setColor(dvm.getFirstColor().getColor());
		g.fillRect(left, top, tileSize, tileSize);

		g.setColor(dvm.getSecondColor().getColor());
		g.fillRect(left, top + tileSize, tileSize, tileSize);
		if(dvm.getSecondColor() == TileColorViewModel.CAMELEON) {
			g.setColor(Color.BLACK);
			Font temp = g.getFont();
			g.setFont(new Font(temp.getName(), temp.getStyle(), temp.getSize()*2));
			g.drawString("C", left + tileSize/4, top + tileSize + tileSize/4*3);
			g.setFont(temp);
		}

		g.setColor(dvm.getThirdColor().getColor());
		g.fillRect(left, top + 2 * tileSize, tileSize, tileSize);
	}
	
	@Override
	public void switchMode(ViewMode mode) {
		if(ViewMode.BOARD == mode && !(this.mode instanceof GameBoardViewMode) ) {
			this.mode = new GameBoardViewMode();
		} else if(ViewMode.HAND == mode && !(this.mode instanceof HandViewMode)){
			this.mode = new HandViewMode();
		}
	}
	
	@Override
	public void onKeyTyped(int keyCode) {
		this.mode.onKeyTyped(keyCode, supervisor);
	}

	private void drawCommands(Graphics g) {
		this.mode.drawCommands(g);
	}
	
	@Override
	public void addMessage(String msg) {
		this.messages.push(msg);
		this.pane.setText(messages.stream().collect(Collectors.joining("\n")));
		
	}
	
}

interface SwingViewMode {
	void drawCommands(Graphics g);
	void onKeyTyped(int keyCode, PlayGameSupervisor superviser);
}

class GameBoardViewMode implements SwingViewMode {

	@Override
	public void drawCommands(Graphics g) {
		int left = Theme.PANEL_WIDTH / 2 + Theme.PANEL_WIDTH / 100 * 2;
		int top = Theme.PANEL_HEIGHT / 100 * 50;
		int width = Theme.TILE_SIZE*8 + 40;
		
		g.setColor(new Color(255,255,255,226));
		g.fillRect(left-2, top-11, width, 142);
		g.setColor(Color.BLACK);
		
		g.drawString("\u2191 : déplacer le domino vers le haut",left, top);
		g.drawString("\u2193 : déplacer le domino vers le bas",left, top+20);
		g.drawString("\u2192 : déplacer le domino vers la droite",left, top+40);
		g.drawString("\u2190 : déplacer le domino vers la gauche",left, top+60);
		g.drawString("Espace : pivoter le domino de 90°",left, top+80);
		g.drawString("\u23ce : confirmer le placement",left, top+100);
		g.drawString("Esc: Retourner au choix de pièce", left, top+120);	
	}

	@Override
	public void onKeyTyped(int keyCode, PlayGameSupervisor superviser) {
		if (keyCode == KeyEvent.VK_DOWN) {
			superviser.onMove(1, 0);
		} else if (keyCode == KeyEvent.VK_UP) {
			superviser.onMove(-1, 0);
		} else if (keyCode == KeyEvent.VK_LEFT) {
			superviser.onMove(0, -1);
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			superviser.onMove(0, 1);
		} else if (keyCode == KeyEvent.VK_SPACE) {
			superviser.onRotate();
		} else if(keyCode == KeyEvent.VK_ENTER) {
			superviser.onConfirm();
		} else if(keyCode == KeyEvent.VK_ESCAPE) {
			superviser.onBack();
		}
	}
}

class HandViewMode implements SwingViewMode {

	@Override
	public void drawCommands(Graphics g) {
		int left = Theme.PANEL_WIDTH / 2 + Theme.PANEL_WIDTH / 100 * 2;
		int top = Theme.PANEL_HEIGHT / 100 * 50;
		int width = Theme.TILE_SIZE*8 + 40;
		
		g.setColor(new Color(255,255,255,226));
		g.fillRect(left-2, top-11, width, 82);
		g.setColor(Color.BLACK);
		
		g.drawString("Espace : choisir le domino suivant",left, top);
		g.drawString("\u23ce : placer le domino sur le plateau",left, top+20);
		g.drawString("+ : pecher un domino", left, top+40);	
		g.drawString("Esc : passer son tour", left, top+60);	
		
	}

	@Override
	public void onKeyTyped(int keyCode, PlayGameSupervisor superviser) {
		if(keyCode == KeyEvent.VK_ENTER) {
			superviser.onPieceSelected();
		} else if(keyCode == KeyEvent.VK_SPACE) {
			superviser.onSelectNextPiece();
		} else if(keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_F12) {
			superviser.onPick();
		} else if(keyCode == KeyEvent.VK_ESCAPE) {
			superviser.onPass();
		}
	}
	
}
