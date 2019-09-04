package holes3d;

import java.awt.Point;

public class Player {

	Point p = new Point(0, 0);
	int z = 0;
	QLearnsAI nn;

	public Player(Point start, int gridSize, int cubeSize) {
		p = new Point(start.x, start.y);
		nn = new QLearnsAI(cubeSize, gridSize, start.x, start.y);
	}

	public void move(Cube cube, int random) {
		nn.getAction(random);
		if (nn.chosenAction == 0 && p.y != 0) {
			p.y--;
		} else if (nn.chosenAction == 2 && p.y != cube.grids.get(0).size - 1) {
			p.y++;
		} else if (nn.chosenAction == 3 && p.x != 0) {
			p.x--;
		} else if (nn.chosenAction == 1 && p.x != cube.grids.get(0).size - 1) {
			p.x++;
		}
		boolean down = false;
		boolean up = false;

		for (Point upS : cube.grids.get(z).upStairs) {
			if (upS.distance(p) == 0) {
				up = true;
			}
		}
		if (up) {
			z++;
		} else {
			for (Point downS : cube.grids.get(z).downStairs) {
				if (downS.distance(p) == 0) {
					down = true;
				}
			}
			if (down && z > 0) {
				z--;
			} else if (z > 0) {
				while (true) {
					boolean changed = false;
					for (Point hole : cube.grids.get(z).holes) {
						if (hole.distance(p) == 0) {
							z--;
							changed = true;
						}
					}
					if (z == 0 || !changed) {
						break;
					}
				}
			}
		}

		if (cube.grids.get(z).stop == null) {
			nn.rewardAndUpdate(p.x + cube.grids.get(z).size * p.y, -1, cube.grids.get(z), z);
		} else {
			nn.rewardAndUpdate(p.x + cube.grids.get(z).size * p.y,
					cube.grids.get(z).stop.x + (cube.grids.get(z).size * (cube.grids.get(z).stop.y)), cube.grids.get(z),
					z);
		}
	}

	public void reset(Cube cube) {
		p.x = cube.grids.get(0).start.x;
		p.y = cube.grids.get(0).start.y;
		z = 0;
		nn.currentState = p.x + cube.grids.get(0).size * p.y;
		nn.currentZ = 0;
		nn.timesZ = 1;
	}

}
