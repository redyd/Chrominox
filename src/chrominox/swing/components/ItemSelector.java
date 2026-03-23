package chrominox.swing.components;

import java.awt.Color;
import java.awt.Graphics2D;

import chrominox.swing.Theme;
import org.helmo.swing.engine.GameComponent;

/**
 * Met en évidence un élément sélectionné à l'aide d'un rectangle.
 * 
 * @author Nicolas Hendrikx
 */
public class ItemSelector extends GameComponent {

	private final Color fillColor;

	/**
	 * {@inheritDoc}
	 * */
	public ItemSelector(int left, int top, int weight, int height) {
		super(left, top, weight, height);
		this.fillColor = Theme.SECONDARY_BACKGROUND_COLOR_ALPHA;
	}

	private Color getFillColor() {
		return fillColor;
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void draw(Graphics2D painter) {
		painter.setColor(getFillColor());
		painter.fillRoundRect(getLeft(), getTop(), getWidth(), getHeight(), 5, 5);
		
		painter.setColor(getFillColor().brighter());
		painter.drawRoundRect(getLeft(), getTop(), getWidth(), getHeight(), 5, 5);
	}

}
