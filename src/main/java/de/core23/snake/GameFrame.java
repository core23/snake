package de.core23.snake;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenuFile = null;
	private JMenuItem jMenuItemExit = null;
	private JMenuItem jMenuItemNewGame = null;
	private JMenuItem jMenuItemAbout = null;
	private JMenu jMenuHelp = null;
	private GamePanel snakePanel = null;

	public GameFrame() {
		super();
		initialize();
	}

	public int getMapWidth() {
		return getSnakePanel().getMapWidth();
	}

	public int getMapHeight() {
		return getSnakePanel().getMapHeight();
	}
	
	public void newGame(int width, int height) {
		getSnakePanel().newGame(width, height);
		resizeScreen();
	}
	
	private void resizeScreen() {
		getJContentPane().setPreferredSize(new Dimension(getSnakePanel().getWidth(), getSnakePanel().getHeight()));
		this.pack(); 
		this.setLocationRelativeTo(null);
		this.setVisible(true);		
	}
	
	private void initialize() {
		this.setJMenuBar(getJJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		getJContentPane().setPreferredSize(new Dimension(getSnakePanel().getWidth(), getSnakePanel().getHeight()));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		resizeScreen();

		addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent key) {
				getSnakePanel().dispatchEvent(key);
			}			
		});
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getSnakePanel(), null);
		}
		return jContentPane;
	}

	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenuFile());
			jJMenuBar.add(getJMenuHelp());
		}
		return jJMenuBar;
	}


	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setText("Datei");
			jMenuFile.add(getJMenuItemNewGame());
			jMenuFile.add(getJMenuItemExit());
		}
		return jMenuFile;
	}

	private JMenuItem getJMenuItemNewGame() {
		if (jMenuItemNewGame == null) {
			jMenuItemNewGame = new JMenuItem();
			jMenuItemNewGame.setText("Neues Spiel");
			jMenuItemNewGame.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GameFrame.this.setEnabled(false);

					final SettingsFrame set = new SettingsFrame(GameFrame.this);
					set.setVisible(true);
					set.toFront();
				}
			});
		}
		return jMenuItemNewGame;
	}

	private JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setText("Beenden");
			jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return jMenuItemExit;
	}

	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText("?");
			jMenuHelp.add(getJMenuItemAbout());
		}
		return jMenuHelp;
	}

	private JMenuItem getJMenuItemAbout() {
		if (jMenuItemAbout == null) {
			jMenuItemAbout = new JMenuItem();
			jMenuItemAbout.setText("Info");
			jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Version 1.01\r\n\r\nCopyright 2010 Christian Gripp\r\n\r\nhttp://gripp.name", "Snake",
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return jMenuItemAbout;
	}

	private GamePanel getSnakePanel() {
		if (snakePanel == null) {
			snakePanel = new GamePanel();
			snakePanel.setLocation(0, 0);			
		}
		return snakePanel;
	}

}
