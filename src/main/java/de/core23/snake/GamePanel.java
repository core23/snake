package de.core23.snake;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import de.core23.snake.impl.Const;
import de.core23.snake.component.Snake;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements Const {
	private static final long serialVersionUID = 1L;
	private Random rnd = new Random();
	private boolean start = false;

	private int[][] map = new int[MAP_WIDTH][MAP_HEIGHT];
	private LinkedList<Snake> snake = new LinkedList<Snake>();
	private int keyPressed = 0;
	private int moveX = 0;
	private int moveY = 0;
	private int score = 0;
	private int speed = SPEED_START;
	private int mapWidth = MAP_WIDTH;
	private int mapHeight = MAP_HEIGHT;
	private boolean pause = false;

	public GamePanel() {
		newGame(MAP_WIDTH, MAP_HEIGHT);

		// Key Actions
		addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == 48 || key.getKeyCode() == 96) {
					speed = SPEED_START;
					actionTimer.setDelay(speed);
				} else if (key.getKeyCode() == 107) {
					speed = Math.max(SPEED_MIN, speed - SPEED_STEP);
					actionTimer.setDelay(speed);

				} else if (key.getKeyCode() == 109) {
					speed = Math.min(SPEED_MAX, speed + SPEED_STEP);
					actionTimer.setDelay(speed);

				} else {
					keyPressed = key.getKeyCode();
				}

			}
		});
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void newGame(int width, int height) {		
		start = false;
		pause = false;
		score = 0;
		keyPressed = 0;
		moveX = 0;
		moveY = 0;
		this.mapWidth = width;
		this.mapHeight = height;

		this.setSize(mapWidth * MAP_BLOCK, mapHeight * MAP_BLOCK);

		actionTimer.setDelay(speed);
		actionTimer.start();
		
		map = new int[mapWidth][mapHeight];

		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				if (x == 0 || x == mapWidth - 1 || y == 0 || y == mapHeight - 1)
					map[x][y] = TYPE_WALL;
				else
					map[x][y] = TYPE_NULL;
			}
		}

		spawnSnake();
		spawnApple();

		start = true;
	}

	private Timer actionTimer = new Timer(speed, new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			// 37 = Left
			if (keyPressed == 37 && moveX != 1) {
				moveX = -1;
				moveY = 0;
			}

			// 39 = Right
			if (keyPressed == 39 && moveX != -1) {
				moveX = 1;
				moveY = 0;
			}

			// 38 = Top
			if (keyPressed == 38 && moveY != 1) {
				moveX = 0;
				moveY = -1;
			}
			// 40 = Down
			if (keyPressed == 40 && moveY != -1) {
				moveX = 0;
				moveY = 1;
			}
			
			// Pause
			if (keyPressed==19) {
				pause = !pause;
			}
			
			keyPressed = 0;
			
			if (!pause)
				moveSnake();
		}
	});

	private void spawnSnake() {
		int x = 0;
		int y = 0;
		do {
			x = rnd.nextInt(mapWidth);
			y = rnd.nextInt(mapHeight);
		} while (map[x][y] != TYPE_NULL);

		snake.clear();

		for (int i = 0; i < 1 + SNAKE_STARTTAIL; i++)
			snake.add(new Snake(x, y));
		map[x][y] = TYPE_SNAKE;

		moveX = 0;
		moveY = 0;

		repaint();
	}

	private void eatApple(int x, int y) {
		for (int i = 0; i < SNAKE_APLLETAIL; i++)
			snake.addFirst(new Snake(snake.getFirst().getX(), snake.getFirst().getY()));

		map[x][y] = TYPE_NULL;

		score++;
		spawnApple();
	}

	private void moveSnake() {
		if (!start)
			return;
		if (moveX == 0 && moveY == 0)
			return;

		// Head
		int x = snake.getLast().getX() + moveX;
		int y = snake.getLast().getY() + moveY;

		// Out Of Map
		if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
			setGameOver();
			return;
		}

		// Collision
		switch (map[x][y]) {
		case TYPE_WALL:
		case TYPE_SNAKE:
			setGameOver();
			return;

		case TYPE_APPLE:
			eatApple(x, y);
			break;
		}

		// Remove Last Tail
		map[snake.getFirst().getX()][snake.getFirst().getY()] = TYPE_NULL;
		snake.removeFirst();

		// Add New Tail
		snake.addLast(new Snake(x, y));

		// Place Snake
		for (Snake piece : snake)
			map[piece.getX()][piece.getY()] = TYPE_SNAKE;

		repaint();
	}

	private void setGameOver() {
		start = false;
		actionTimer.stop();

		JOptionPane.showMessageDialog(null, "Game Over!\r\nSie haben " + score + " �pfel gefressen.", "Snake", JOptionPane.ERROR_MESSAGE);

		repaint();
	}

	private void spawnApple() {
		Vector<Point> points = new Vector<Point>();

		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				if (map[x][y] == TYPE_NULL)
					points.add(new Point(x, y));
			}
		}
		
		if (points.size()==0) {
			start = false;
			actionTimer.stop();
			JOptionPane.showMessageDialog(null, "Gewonnen!\r\nSie haben " + score + " �pfel gefressen.", "Snake", JOptionPane.INFORMATION_MESSAGE);
			repaint();
			return;
		}
		
		Collections.shuffle(points);
		
		Point p = points.firstElement();		
		map[p.x][p.y] = TYPE_APPLE;
	}

	public void paint(Graphics g) {
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				if (map[x][y] == TYPE_NULL || map[x][y] == TYPE_APPLE)
					g.setColor(COLOR_NULL);
				else if (map[x][y] == TYPE_WALL)
					g.setColor(COLOR_WALL);
				else if (map[x][y] == TYPE_SNAKE) {
					if (snake.getLast().getX() == x && snake.getLast().getY() == y)
						g.setColor(COLOR_SNAKE);
					else
						g.setColor(COLOR_TAIL);
				}
				g.fillRect(x * MAP_BLOCK, y * MAP_BLOCK, MAP_BLOCK, MAP_BLOCK);

				if (map[x][y] == TYPE_APPLE) {
					g.setColor(COLOR_APPLE);
					g.fillOval(x * MAP_BLOCK, y * MAP_BLOCK, MAP_BLOCK, MAP_BLOCK);
				}
			}
		}

		g.setColor(COLOR_APPLE);
		String text = score + " ";
		Rectangle2D rectFont = g.getFont().getStringBounds(text, g.getFontMetrics().getFontRenderContext());
		g.drawString(text, MAP_BLOCK * mapWidth - (int) (rectFont.getWidth()), (int) (rectFont.getHeight()));
	}
}
