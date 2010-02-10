package de.core23.snake;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTextField;
import de.core23.snake.component.JTextFieldFilter;


public class SettingsFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jButtonSave = null;
	private JLabel jLabelMapWidth = null;
	private JLabel jLabelMapHeight = null;
	private JTextField jTextMapWidth = null;
	private JTextField jTextMapHeight = null;
	private GameFrame snakeFrame = null;
	private int mapWidth = 10;
	private int mapHeight = 10;

	
	public SettingsFrame(GameFrame snakeFrame) {
		super();
		initialize();	
		this.snakeFrame = snakeFrame;

		setMapWidth(snakeFrame.getMapWidth());
		setMapHeight(snakeFrame.getMapHeight());
	}
	
	public void setMapWidth(int width) {
		if (width<10)
			this.mapWidth = 10;
		else if (width>50)
			this.mapWidth = 50;
		else
			this.mapWidth = width;

		getJTextMapWidth().setText(new Integer(this.mapWidth).toString());
	}
	
	public void setMapHeight(int height) {
		if (height<10)
			this.mapHeight = 10;
		else if (height>50)
			this.mapHeight = 50;
		else
			this.mapHeight = height;

		getJTextMapHeight().setText(new Integer(this.mapHeight).toString());
	}

	private void initialize() {
		this.setSize(190, 180);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("Neues Spiel");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				snakeFrame.setEnabled(true);
				snakeFrame.toFront();
			}
		});
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelMapHeight = new JLabel();
			jLabelMapHeight.setBounds(new Rectangle(20, 60, 50, 20));
			jLabelMapHeight.setText("H�he");
			jLabelMapWidth = new JLabel();
			jLabelMapWidth.setBounds(new Rectangle(20, 20, 50, 20));
			jLabelMapWidth.setText("Breite");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButtonSave(), null);
			jContentPane.add(jLabelMapWidth, null);
			jContentPane.add(jLabelMapHeight, null);
			jContentPane.add(getJTextMapWidth(), null);
			jContentPane.add(getJTextMapHeight(), null);
		}
		return jContentPane;
	}

	private JButton getJButtonSave() {
		if (jButtonSave == null) {
			jButtonSave = new JButton();
			jButtonSave.setBounds(new Rectangle(20, 100, 140, 20));
			jButtonSave.setText("Starten");
			jButtonSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {		
						setMapWidth(Integer.parseInt(jTextMapWidth.getText()));
						setMapHeight(Integer.parseInt(jTextMapHeight.getText()));
						
						snakeFrame.setEnabled(true);	
						snakeFrame.toFront();
						snakeFrame.newGame(mapWidth, mapHeight);
						
						setVisible(false);
						
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(), "Snake", JOptionPane.INFORMATION_MESSAGE);
					}									
				}
			});			
		}
		return jButtonSave;
	}


	private JTextField getJTextMapWidth() {
		if (jTextMapWidth == null) {
			jTextMapWidth = new JTextField();
			jTextMapWidth.setBounds(new Rectangle(100, 20, 60, 20));
			jTextMapWidth.setText("10");
			jTextMapWidth.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
			jTextMapWidth.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					try {					
						setMapWidth(Integer.parseInt(jTextMapWidth.getText()));
					} catch (NumberFormatException ex) {
						jTextMapWidth.setText(mapWidth+"");
					}
				}
			});
		}
		return jTextMapWidth;
	}

	private JTextField getJTextMapHeight() {
		if (jTextMapHeight == null) {
			jTextMapHeight = new JTextField();
			jTextMapHeight.setBounds(new Rectangle(100, 60, 60, 20));
			jTextMapHeight.setText("10");
			jTextMapHeight.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
			jTextMapHeight.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					try {					
						setMapHeight(Integer.parseInt(jTextMapHeight.getText()));
					} catch (NumberFormatException ex) {
						jTextMapHeight.setText(mapHeight+"");
					}					
				}
			});
		}
		return jTextMapHeight;
	}
}
