package chrominox.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.*;
import javax.swing.Timer;

import org.helmo.swing.SwingView;
import org.helmo.swing.effect.MoveToEffect;

import chrominox.supervisors.*;
import chrominox.supervisors.commons.ViewId;
import chrominox.swing.components.MenuItem;
import chrominox.swing.components.DataPanel;
import chrominox.swing.components.ItemSelector;

public final class SwingMainMenuView extends SwingView<ViewId> implements MainMenuView {
	private static final long serialVersionUID = -2211970715714357966L;
	private final ImageIcon background = new ImageIcon("resources/images/main_menu_background.jpg");
	private final MainMenuSupervisor supervisor;
	
	private List<MenuItem> items;
	private ItemSelector selector;
	private int selected = 0;
	private final Timer animator;
	private MoveToEffect moveEffect;

	private final DataPanel commandsPanel;
	
	public SwingMainMenuView(MainMenuSupervisor supervisor) {
		super(ViewId.MAIN_MENU);
		this.supervisor = Objects.requireNonNull(supervisor, "You must provide a defined MainMenuSupervisor");
		this.supervisor.setView(this);

		this.commandsPanel = new DataPanel("COMMANDES", getWidth()/100*3, getHeight()/100*3, 200);
		this.commandsPanel.addAll(
				"↑ : déplacer vers le haut",
				"↓ : déplacer vers le bas",
				"↳ : choisir");

		this.animator = new Timer(16, (evt) -> onNewFrameRequested());
	}
	
	@Override
	public void paintComponent(Graphics painter) {
		super.paintComponent(painter);
		int deltaX = (this.getWidth() - background.getIconWidth())/2;
		int deltaY = (this.getHeight() - background.getIconHeight())/2;
        painter.drawImage(background.getImage(), deltaX, deltaY, null);
		
		Graphics2D renderer = (Graphics2D)painter;
		renderer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderer.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		commandsPanel.draw(renderer);
	
		selector.draw(renderer);
		
		for(var item : items) {	
			item.draw(renderer);
		}
		
	}

	@Override
	public void onKeyTyped(int keyCode) {

		int oldSelected = selected;
		if(keyCode == KeyEvent.VK_DOWN) {
			this.selected = (selected + 1) % items.size();
		} else if(keyCode == KeyEvent.VK_UP) {
			this.selected = (selected == 0) ? items.size() - 1 : selected - 1;
		} else if(KeyEvent.VK_ENTER == keyCode) {
			this.supervisor.onItemSelected(this.selected);
		}
		
		if(oldSelected != selected) {
			moveEffect = new MoveToEffect(selector, items.get(selected).getPosition(), 300);
		}
	}

	@Override
	public void setItems(List<String> items) {
		Objects.requireNonNull(items);
		if(items.isEmpty()) {
			throw new IllegalArgumentException("You must provide at least one item");
		}
		
		var left = Theme.PANEL_WIDTH/3;
		var top = Theme.PANEL_HEIGHT/6;
		var width = Theme.PANEL_WIDTH/3;
		var height = Theme.PANEL_HEIGHT/12;
		var gap = Theme.PANEL_HEIGHT/20;
		
		this.items = IntStream.range(0, items.size())
				.mapToObj((index) -> new MenuItem(items.get(index), left, top+index*(height+gap), width, height))
				.collect(Collectors.toList());
		this.selected = 0;
		
		selector = new ItemSelector(left-10, top-10, width+20, height+20);
	}

	@Override
	public void onQuitConfirmed(ViewId fromScreen) {
		confirmExit();
	}
	
	@Override
	public void onEnter(ViewId fromView) {
		animator.start();
	}

	
	@Override
	public void onLeave(ViewId toView) {
		animator.stop();
	}

	private void onNewFrameRequested() {
		if(moveEffect != null) {
			moveEffect.update();
			if(moveEffect.isDone()) {
				moveEffect = null;
			}
			this.updateUI();
		}
		
	}

}
