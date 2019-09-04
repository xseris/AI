package holes;

import java.awt.Rectangle;

public class Ball {

	Rectangle rect = new Rectangle();

	int xDir = 1;
	int yDir = 1;
	int speed = 1;

	public Ball(int x, int y, int speed) {
		rect.x = x;
		rect.y = y;
		rect.height = 5;
		rect.width = 5;
		this.speed = speed;
	}

	public void move() {
		rect.x += xDir;
		rect.y += yDir;
		// bounce the ball when it hits the edge of the screen
		if (rect.x <= 5) {
			xDir = 1;
		}
		if (rect.x >= 495) {
			xDir = -1;
		}
		if (rect.y <= 25) {
			yDir = 1;
		}
		if (rect.y >= 395) {
			yDir = -1;
		}
	}

	public void reset() {
		rect.x = 250;
		rect.y = (int) (Math.random() * 400);
		int dir = (int) (Math.random() * 4);
		if (dir == 0) {
			xDir = -1;
			yDir = -1;
		} else if (dir == 1) {
			xDir = 1;
			yDir = -1;
		} else if (dir == 2) {
			xDir = -1;
			yDir = 1;
		} else {
			xDir = 1;
			yDir = 1;
		}
	}

}
