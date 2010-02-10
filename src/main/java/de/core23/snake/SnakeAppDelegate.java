package de.core23.snake;

import javax.swing.SwingUtilities;

public class SnakeAppDelegate {
	public static void main (String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameFrame();				
			}
		});
	}
}
