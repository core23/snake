package de.core23.snake.impl;

import java.awt.Color;

public interface Const {
	final int MAP_WIDTH = 40;
	final int MAP_HEIGHT = 20;
	final int MAP_BLOCK = 15;

	final int TYPE_NULL = 0;
	final int TYPE_WALL = 1;
	final int TYPE_SNAKE = 2;
	final int TYPE_APPLE = 3;
	
	final int SNAKE_STARTTAIL = 5;
	final int SNAKE_APLLETAIL = 3;
	
	final int SPEED_START = 200;
	final int SPEED_MIN = 20;
	final int SPEED_MAX = 1000;
	final int SPEED_STEP = 20;

	final Color COLOR_NULL = new Color(0xEEEEEE);
	final Color COLOR_APPLE = new Color(0xFF2222);
	final Color COLOR_WALL = new Color(0x333333);
	final Color COLOR_SNAKE = new Color(0x007700);
	final Color COLOR_TAIL = new Color(0x00AA00);
}
