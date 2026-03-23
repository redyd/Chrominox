package chrominox.domains;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class FakeBag implements Bag {
	
	private final Queue<Chromino> bag = new ArrayDeque<>(List.of(
			new Chromino(TileType.CYAN, TileType.CYAN, TileType.CYAN),
			new Chromino(TileType.CYAN, TileType.GREEN, TileType.CYAN),
			new Chromino(TileType.CYAN, TileType.MAGENTA, TileType.CYAN),
			new Chromino(TileType.CYAN, TileType.RED, TileType.CYAN),
			new Chromino(TileType.CYAN, TileType.YELLOW, TileType.CYAN),
			new Chromino(TileType.CYAN, TileType.GREEN, TileType.GREEN),
			new Chromino(TileType.CYAN, TileType.GREEN, TileType.MAGENTA),
			new Chromino(TileType.CYAN, TileType.GREEN, TileType.RED),
			new Chromino(TileType.CYAN, TileType.GREEN, TileType.YELLOW),
			new Chromino(TileType.CYAN, TileType.CAMELEON, TileType.RED)
			));

	@Override
	public int size() {
		return bag.size();
	}

	@Override
	public Queue<Chromino> getBag() {
		return bag;
	}

	@Override
	public Chromino take() {
		return bag.poll();
	}

	@Override
	public List<Chromino> take(int count) {
		List<Chromino> list = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			list.add(bag.poll());
		}
		
		return list;
	}

	@Override
	public boolean empty() {
		// TODO Auto-generated method stub
		return false;
	}

}
