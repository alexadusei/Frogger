import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Start extends JPanel implements KeyListener {

	private ImageIcon imgBackground;
	private Graphics2D g2;
	private int frameWidth, frameHeight;
	private JFrame frame;
	
	private Frogger game;

	public static void main(String[] args) {

		new Start();
	}

	public Start() {

		game = new Frogger();
		
		imgBackground = new ImageIcon("images\\welcome_screen.png");
		frameWidth = imgBackground.getIconWidth();
		frameHeight = imgBackground.getIconHeight();

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(frameWidth, 
				frameHeight));
		addKeyListener(this);
		setFocusable(true);
		requestFocus();

		frame = new JFrame();
		frame.add(this);
		frame.setSize(frameWidth, frameHeight + 10);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(false);
		frame.setTitle("Frogger");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void startGame() {
		
		game.setVisible(true);
		
		imgBackground = new ImageIcon("images\\gameBoard.png");
		frameWidth = imgBackground.getIconWidth();
		frameHeight = imgBackground.getIconHeight();
		
		setPreferredSize(new Dimension(frameWidth, 
				frameHeight));
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.validate();
	}
	

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g2 = (Graphics2D) g;
		g2.drawImage(imgBackground.getImage(), 0, 0, null);
	}

	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			startGame();
		}
	}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}
}
