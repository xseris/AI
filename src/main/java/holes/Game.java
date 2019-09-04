package holes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;

	static int floorSize = 70;
	static int holes = 1200;

	static Grid grid;
	static int cellSize;
	static int speed = 200000000;
	static int win = 0;
	static int lose = 0;
	static Map<Integer, Integer> moves = new HashMap<>();
	static Map<Integer, Integer> status = new HashMap<>();
	static int epoch = 0;
	static int gWidth = 1800;
	static int gHeight = 1000;
	static int gridHeight = gHeight - 150;
	static int infoHeight = gHeight - 100;
	Dimension screenSize = new Dimension(gWidth, gHeight);

	Image dbImage;
	Graphics dbGraphics;

	static Player ai;

	public Game() {
		this.setTitle("Holes!");
		this.setSize(screenSize);
		this.setResizable(false);
		this.setVisible(true);
		this.setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) throws InterruptedException {
		moves.put(0, 0);
		grid = new Grid(floorSize, holes);
		speed = speed / 10000;
		cellSize = gridHeight / grid.size;
		ai = new Player(grid.start, grid.size);

		new Game();

		while (true) {
			ai.move(grid);
			if (moves.containsKey(epoch)) {
				moves.put(epoch, moves.get(epoch) + 1);
			} else {
				moves.put(epoch, 1);
			}
			if (ai.p.distance(grid.stop) == 0) {
				ai.reset(grid);
				win++;
				epoch++;
				status.put(epoch, 1);
			} else {
				for (Point hole : grid.holes) {
					if (ai.p.distance(hole) == 0) {
						ai.reset(grid);
						lose++;
						epoch++;
						status.put(epoch, 0);
						break;
					}
				}
			}
			// Thread.sleep(1);
			long start = System.nanoTime();
			long end = 0;
			do {
				end = System.nanoTime();
			} while (start + speed >= end);
		}

	}

	@Override
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbGraphics = dbImage.getGraphics();
		draw(dbGraphics);
		g.drawImage(dbImage, 0, 0, this);
	}

	public void draw(Graphics g) {
		drawSquare(g, grid.start, Color.GREEN);
		drawSquare(g, grid.stop, Color.RED);
		g.setColor(Color.WHITE);
		for (int i = 0; i < grid.size; i++) {
			for (int j = 0; j < grid.size; j++) {
				g.drawRect(i * cellSize + 10, j * cellSize + 50, cellSize, cellSize);
			}
		}
		for (Point hole : grid.holes) {
			drawSquare(g, hole, Color.BLACK);
		}

		drawSquare(g, ai.p, Color.WHITE);
		g.drawString("Game: " + epoch, 10, grid.size * cellSize + 100);
		g.drawString("Win: " + win, 10, grid.size * cellSize + 115);
		g.drawString("Lose: " + lose, 10, grid.size * cellSize + 130);

		repaint();

		int k = 0;
		for (int i = 0; i < moves.size(); i++) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(grid.size * cellSize + 100, i - (infoHeight * k) + 50, 2000, 1);
			g.setColor(Color.WHITE);
			if (status.containsKey(i)) {
				if (status.get(i) == 0) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.GREEN);
				}
			}
			g.drawRect(grid.size * cellSize + 100, i - (infoHeight * k) + 50, moves.get(i), 1);
			if (i != 0 && i % infoHeight == 0) {
				k++;
			}
		}
	}

	public void drawSquare(Graphics g, Point p, Color color) {
		g.setColor(color);
		g.fillRect(p.x * cellSize + 10, p.y * cellSize + 50, cellSize, cellSize);
	}

	private static Point randomPoint() {
		int startX = (int) (Math.random() * grid.size);
		int startY = (int) (Math.random() * grid.size);
		return new Point(startX, startY);
	}

}
