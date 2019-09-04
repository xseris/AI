package holes;

import java.awt.Point;

public class Player {

	Point p = new Point(0, 0);
	QLearnsHoles nn;

	public Player(Point start, int gridSize) {
		p = new Point(start.x, start.y);
		nn = new QLearnsHoles(gridSize, start.x, start.y);
	}

	public void move(Grid grid) {
		nn.getAction();
		if (nn.chosenAction == 0 && p.y != 0) {
			p.y--;
		} else if (nn.chosenAction == 2 && p.y != grid.size - 1) {
			p.y++;
		} else if (nn.chosenAction == 3 && p.x != 0) {
			p.x--;
		} else if (nn.chosenAction == 1 && p.x != grid.size - 1) {
			p.x++;
		}
		nn.rewardAndUpdate(p.x + grid.size * p.y, grid.stop.x + (grid.size * (grid.stop.y)), grid.holeStates);
	}

	public void reset(Grid grid) {
		this.p.x = grid.start.x;
		this.p.y = grid.start.y;
		nn.currentState = grid.start.x + grid.size * grid.start.y;
	}

}
