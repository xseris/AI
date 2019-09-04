package holes3d;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Grid {
	int size;
	Point start;
	Point stop;
	int holeNum;
	int stairNum;
	List<Point> holes;
	List<Integer> holeStates;
	List<Point> upStairs;
	List<Integer> upStairsStates;
	List<Point> downStairs;
	List<Integer> downStairsStates;

	public Grid(final int size, final int holeNum, final int stairNum, final Grid prev, final boolean last) {
		this.holes = new ArrayList<>();
		this.holeStates = new ArrayList<>();
		this.upStairs = new ArrayList<>();
		this.upStairsStates = new ArrayList<>();
		this.downStairs = new ArrayList<>();
		this.downStairsStates = new ArrayList<>();
		this.size = size;
		this.holeNum = holeNum;
		this.stairNum = stairNum;
		if (prev == null) {
			this.initialize(null, last);
		} else {
			this.initialize(prev.upStairs, last);
		}
	}

	private void initialize(final List<Point> prevUpStairs, final boolean last) {
		if (prevUpStairs == null) {
			this.start = this.randomPoint();
			int i = this.holeNum;
			while (i > 0) {
				final Point p = this.randomPoint();
				boolean ok = true;
				for (final Point hole : this.holes) {
					if (hole.distance(p) == 0.0) {
						ok = false;
						break;
					}
				}
				if (ok && p.distance(this.start) != 0.0) {
					--i;
					this.holes.add(p);
					this.holeStates.add(p.x + p.y * this.size);
				}
			}
			i = this.stairNum;
			while (i > 0) {
				final Point p = this.randomPoint();
				boolean ok = true;
				for (final Point hole : this.holes) {
					if (hole.distance(p) == 0.0) {
						ok = false;
						break;
					}
				}
				for (final Point stair : this.upStairs) {
					if (stair.distance(p) == 0.0) {
						ok = false;
						break;
					}
				}
				if (ok && p.distance(this.start) != 0.0) {
					--i;
					this.upStairs.add(p);
					this.upStairsStates.add(p.x + p.y * this.size);
				}
			}
		} else if (!last) {
			for (final Point prevUp : prevUpStairs) {
				final Point p2 = new Point(prevUp.x, prevUp.y);
				this.downStairs.add(p2);
				this.downStairsStates.add(p2.x + p2.y * this.size);
			}
			int i = this.holeNum;
			while (i > 0) {
				final Point p = this.randomPoint();
				boolean ok = true;
				for (final Point hole : this.holes) {
					if (hole.distance(p) == 0.0) {
						ok = false;
						break;
					}
				}
				for (final Point down : this.downStairs) {
					if (down.distance(p) == 0.0) {
						ok = false;
						break;
					}
				}
				if (ok) {
					--i;
					this.holes.add(p);
					this.holeStates.add(p.x + p.y * this.size);
				}
			}
			i = this.stairNum;
			while (i > 0) {
				final Point p = this.randomPoint();
				boolean ok = true;
				for (final Point hole : this.holes) {
					if (hole.distance(p) == 0.0) {
						ok = false;
						break;
					}
				}
				for (final Point stair : this.downStairs) {
					if (stair.distance(p) == 0.0) {
						ok = false;
						break;
					}
				}
				for (final Point stair : this.upStairs) {
					if (stair.distance(p) == 0.0) {
						ok = false;
						break;
					}
				}
				if (ok) {
					--i;
					this.upStairs.add(p);
					this.upStairsStates.add(p.x + p.y * this.size);
				}
			}
		} else {
			for (final Point prevUp : prevUpStairs) {
				final Point p2 = new Point(prevUp.x, prevUp.y);
				this.downStairs.add(p2);
				this.downStairsStates.add(p2.x + p2.y * this.size);
			}
			int i = this.holeNum;
			while (i > 0) {
				final Point p = this.randomPoint();
				boolean ok = true;
				for (final Point hole : this.holes) {
					if (hole.distance(p) == 0.0) {
						ok = false;
						break;
					}
				}
				for (final Point down : this.downStairs) {
					if (down.distance(p) == 0.0) {
						ok = false;
						break;
					}
				}
				if (ok) {
					--i;
					this.holes.add(p);
					this.holeStates.add(p.x + p.y * this.size);
				}
			}
			i = 1;
			while (i > 0) {
				this.stop = this.randomPoint();
				boolean ok2 = true;
				for (final Point hole2 : this.holes) {
					if (hole2.distance(this.stop) == 0.0) {
						ok2 = false;
						break;
					}
				}
				for (final Point down2 : this.downStairs) {
					if (down2.distance(this.stop) == 0.0) {
						ok2 = false;
						break;
					}
				}
				if (ok2) {
					break;
				}
			}
		}
	}

	private Point randomPoint() {
		final int startX = (int) (Math.random() * this.size);
		final int startY = (int) (Math.random() * this.size);
		return new Point(startX, startY);
	}
}
