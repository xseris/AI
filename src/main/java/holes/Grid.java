package holes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Grid {

	int size;
	Point start;
	Point stop;

	int holeNum;

	List<Point> holes = new ArrayList<>();
	List<Integer> holeStates = new ArrayList<>();

	public Grid(int size, int holeNum) {
		this.size = size;
		this.holeNum = holeNum;
		initialize();
	}

	private void initialize() {
		start = randomPoint();

		while (true) {
			this.stop = randomPoint();
			if (this.stop.distance(this.start) != 0) {
				break;
			}
		}
		int i = holeNum;
		while (i > 0) {
			Point p = randomPoint();
			boolean ok = true;
			for (Point hole : holes) {
				if (hole.distance(p) == 0) {
					ok = false;
					break;
				}
			}
			if (ok && p.distance(start) != 0 && p.distance(stop) != 0) {
				i--;
				holes.add(p);
				holeStates.add(p.x + p.y * size);
			}
		}
	}

	private Point randomPoint() {
		int startX = (int) (Math.random() * size);
		int startY = (int) (Math.random() * size);
		return new Point(startX, startY);
	}

}
