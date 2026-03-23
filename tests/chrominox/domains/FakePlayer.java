package chrominox.domains;

import java.util.ArrayList;
import java.util.List;

public class FakePlayer implements Player {

	private List<Chromino> list;

	@Override
	public void init(Bag bag, int handSize) {
		this.list = new ArrayList<>();
	}

	@Override
	public Chromino getActiveChromino() {
		return null;
	}

	@Override
	public Coordinate getActiveCoordinate() {
		return null;
	}

	@Override
	public List<Chromino> getChrominoes() {
		return list;
	}

	@Override
	public String getBorderColor() {
		return null;
	}

	@Override
	public void nextChromino() {

	}

	@Override
	public boolean hasActive() {
		return false;
	}

	@Override
	public void initActiveCoordinate() {
	}

	@Override
	public void removeActiveCoordinate() {
	}

	@Override
	public void removeActiveChromino() {
	}

	@Override
	public boolean take(Bag bag) {
		return list.add(bag.take());
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void selectLastChromino() {
	}

	@Override
	public int points(int points) {
		return points;
	}

	@Override
	public int compareTo(Player o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

}
