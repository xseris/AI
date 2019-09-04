package holes3d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;

	static int floors = 10;
	static int floorSize = 10;
	static int holes = 50;
	static int stairs = 5;

	static Cube cube;
	static int cellSize;
	static boolean going = true;
	static int random = 0;
	static int speed = 500000000;
	static int win = 0;
	static int lose = 0;
	static Map<Integer, Integer> moves = new HashMap<>();
	static Map<Integer, Integer> status = new HashMap<>();
	static int epoch = 0;
	static int gWidth = 1800;
	static int gHeight = 1000;
	static int gridHeight = gHeight - 150;
	static int infoHeight = gHeight - 150;
	Dimension screenSize;
	JButton startStopButton;
	Image dbImage;
	Graphics dbGraphics;
	static Player ai;

	public Game() {
		this.screenSize = new Dimension(gWidth, gHeight);
		this.startStopButton = new JButton("Start/Stop");
		this.setTitle("Holes!");
		this.setSize(this.screenSize);
		this.setResizable(false);
		this.setVisible(true);
		this.setBackground(Color.DARK_GRAY);
		this.startStopButton.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(final KeyEvent e) {
			}

			@Override
			public void keyReleased(final KeyEvent e) {
			}

			@Override
			public void keyTyped(final KeyEvent e) {
				if (e.getKeyChar() == 'w') {
					speed /= 2;
				}
				if (e.getKeyChar() == 's') {
					speed *= 2;
				}
				if (e.getKeyChar() == 'r') {
					random = 1 - random;
				}
			}

		});
		this.startStopButton.setBounds(100, 100, 100, 100);
		this.add(this.startStopButton);
	}

	public static void main(final String[] args) {
		moves.put(0, 0);
		cube = new Cube(floors, floorSize, holes, stairs);
		cellSize = gridHeight / cube.grids.get(0).size;
		ai = new Player(cube.grids.get(0).start, cube.grids.get(0).size, cube.gridNum);
		new Game();

		while (true) {
			if (going) {
				play();
			}
		}
	}

	public static void play() {
		ai.move(cube, random);
		if (moves.containsKey(epoch)) {
			moves.put(epoch, moves.get(epoch) + 1);
		} else {
			moves.put(epoch, 1);
		}
		if (ai.z == cube.gridNum - 1 && ai.p.distance(cube.grids.get(cube.gridNum - 1).stop) == 0.0) {
			ai.reset(cube);
			++win;
			++epoch;
			status.put(epoch, 1);
		} else if (ai.z == 0) {
			for (final Point hole : cube.grids.get(0).holes) {
				if (ai.p.distance(hole) == 0.0) {
					ai.reset(cube);
					++lose;
					++epoch;
					status.put(epoch, 0);
					break;
				}
			}
		}
		final long start = System.nanoTime();
		long end = 0L;
		do {
			end = System.nanoTime();
		} while (start + speed >= end);
	}

	@Override
	public void paint(final Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		draw(dbGraphics = dbImage.getGraphics());
		g.drawImage(dbImage, 0, 0, this);
	}

	public void draw(final Graphics g) {
		drawSquare(g, cube.grids.get(ai.z).start, Color.GREEN);
		drawSquare(g, cube.grids.get(ai.z).stop, Color.RED);
		g.setColor(Color.WHITE);
		for (int i = 0; i < cube.grids.get(ai.z).size; ++i) {
			for (int j = 0; j < cube.grids.get(ai.z).size; ++j) {
				g.drawRect(i * cellSize + 10, j * cellSize + 50, cellSize, cellSize);
			}
		}
		for (final Point hole : cube.grids.get(ai.z).holes) {
			drawSquare(g, hole, Color.BLACK);
		}
		for (final Point upS : cube.grids.get(ai.z).upStairs) {
			drawSquare(g, upS, Color.CYAN);
		}
		for (final Point downS : cube.grids.get(ai.z).downStairs) {
			drawSquare(g, downS, Color.ORANGE);
		}
		drawSquare(g, ai.p, Color.WHITE);
		g.drawString("Game: " + epoch, 10, cube.grids.get(ai.z).size * cellSize + 100);
		g.drawString("Win: " + win, 10, cube.grids.get(ai.z).size * cellSize + 115);
		g.drawString("Lose: " + lose, 10, cube.grids.get(ai.z).size * cellSize + 130);
		g.drawString("Z: " + ai.z, 10, cube.grids.get(ai.z).size * cellSize + 145);
		g.drawString("Random: " + random, 100, cube.grids.get(ai.z).size * cellSize + 145);
		this.repaint();
		int k = 0;
		for (int l = 0; l < moves.size(); ++l) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(cube.grids.get(ai.z).size * cellSize + 100, l - infoHeight * k + 50, 2000, 1);
			g.setColor(Color.WHITE);
			if (status.containsKey(l)) {
				if (status.get(l) == 0) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.GREEN);
				}
			}
			g.drawRect(cube.grids.get(ai.z).size * cellSize + 100, l - infoHeight * k + 50, moves.get(l), 1);
			if (l != 0 && l % infoHeight == 0) {
				++k;
			}
		}
	}

	public void drawSquare(final Graphics g, final Point p, final Color color) {
		if (p != null) {
			g.setColor(color);
			g.fillRect(p.x * cellSize + 10, p.y * cellSize + 50, cellSize, cellSize);
		}
	}
}
