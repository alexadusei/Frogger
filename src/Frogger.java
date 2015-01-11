/**GROUP PARTNERS: Alex Adusei, Bon Castro, Fred Donkor, Stephen Dienaar
 * TEACHER: Mr. Bulhao
 * COURSE CODE: ICS4U1
 */

// Main Class done by: Alex & Fred

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Frogger extends JPanel  implements ActionListener
{

	//Declare global variables
	public final int FRAME_WIDTH;
	public final int FRAME_HEIGHT;
	public static int level;

	private ImageIcon imgBackground, imgLives;
	private ImageIcon[] imgFrogs;
	private Graphics2D g2;
	private int score, v1xPos, v2xPos, v3xPos, v4xPos, v5xPos, logxPos, logyPos, turtlexPos, turtleyPos, lives, frogs, timerCounter, seconds,
	strWidth, strChoiceWidth, stringY, stringChoiceY, deathCounter, diveValue, num, snakeAmount, lvl2Pixels, momentCounter, head;
	private int[] xFrog;
	private Font f, f1;
	private Timer tmrOverall, tmrTurtleDive;
	private double location, width, stepCounter;
	private Color c, c1;
	private boolean left, right, up, down, gameOver, turtleDown, successfulJump;
	private boolean[] frogOccupied;
	private String message, choice;
	private FontMetrics fm, fm1;

	//Declare global objects of the classes
	private Vehicle[] v1, v2, v3, v4, v5;
	private Turtle[] turtle;
	private Log[] log;
	private Snake snake;
	private Frog frog;

	public static void main(String[] args) 
	{
		new Frogger();
	}

	public Frogger() 
	{

		//Set all default numerical values
		level = 1;
		snakeAmount = 10;
		lvl2Pixels = 0;
		diveValue = 1;
		num = 1;
		stringY = -10;
		stringChoiceY = -10;

		lives = 3;
		location = 370;
		width = 200;
		seconds = 47;

		//Set message colour
		c1 = Color.RED; 
		c = Color.GREEN;

		//Set timer intervals;
		tmrOverall = new Timer(50, this);
		tmrTurtleDive = new Timer(175, this);

		//Set string messages and font/fontMetrics types along with string widths
		message = "GAMEOVER";
		choice = "Play Again = 1                  Quit = 0";

		f = new Font("Berlin Sans FB Demi", Font.BOLD, 37);
		f1 = new Font("Berlin Sans FB Demi", Font.BOLD, 120);

		fm = getFontMetrics(f1);
		fm1 = getFontMetrics(f);

		strWidth = fm.stringWidth(message);
		strChoiceWidth = fm1.stringWidth(choice);

		//Set images
		imgLives = new ImageIcon("images\\lives3.png");
		imgBackground = new ImageIcon("images\\gameBoard.png");
		
		//Set background width and height
		FRAME_WIDTH = imgBackground.getIconWidth();
		FRAME_HEIGHT = imgBackground.getIconHeight();

		//Further initialize all objects, with their given positions and parameter details.
		frog = new Frog(FRAME_WIDTH);
		snake = new Snake();

		log = new Log[6];
		turtle = new Turtle[6];

		v1 = new Vehicle[3];
		v2 = new Vehicle[3];
		v3 = new Vehicle[3];
		v4 = new Vehicle[3];
		v5 = new Vehicle[2];

		imgFrogs = new ImageIcon[5];
		frogOccupied = new boolean[5];
		xFrog = new int[]{40, 182, 325, 466, 608};

		for (int i = 0; i < imgFrogs.length; i++)
		{
			imgFrogs[i] = new ImageIcon("");
		}

		int value = 2;

		for (int i = 0; i < turtle.length; i++)
		{
			if (i == 3)
			{
				turtleyPos += 120;
				turtlexPos = 100;
				value = 3;
			}

			turtle[i] = new Turtle(new ImageIcon("images\\" + value + "turtle0.png"), 50 + turtlexPos, 125 + turtleyPos);
			turtlexPos += 250;
		}

		for (int i = 0; i < log.length; i++)
		{
			if (i == 2)
			{
				logxPos = 200;
				logyPos += 80;
			}
			else if (i == 4)
			{
				logxPos = 80;
				logyPos += 40;
			}	

			log[i] = new Log(50 + logxPos, 85 + logyPos);
			logxPos += 350;
		}

		for (int i = 0; i < v1.length; i++)
		{
			v1[i] = new Vehicle(new ImageIcon("images\\car0.png"), 150 + v1xPos, 485);
			v1xPos += 200;

			v2[i] = new Vehicle(new ImageIcon("images\\car1.png"), 100 + v2xPos, 445);
			v2xPos += 200;

			v3[i] = new Vehicle(new ImageIcon("images\\car2.png"), 230 + v3xPos, 405);
			v3xPos += 175;

			v4[i] = new Vehicle(new ImageIcon("images\\car3.png"), -250 + v4xPos, 365);
			v4xPos += 200;
		}

		for (int i = 0; i < v5.length; i++)
		{
			v5[i] = new Vehicle(new ImageIcon("images\\car4.png"), 150 + v5xPos, 325);
			v5xPos += 300;
		}

		//After all these values have been set, start the timer
		tmrOverall.start();
		tmrTurtleDive.start();

		//Add keyListeners to JPanel, set preferred size, etc
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		addKeyListener(new frogListener());
		setFocusable(true);
		requestFocus();

		//JFrame details
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Frogger");
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setFocusable(false);
		frame.setVisible(true);
	}

	//Make inner class for the KeyListener to avoid unused methods
	class frogListener extends KeyAdapter
	{
		public void keyPressed (KeyEvent e)
		{
			//Set actions to when the user hits up/down/left/right (Move frog in that direction).
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if (!left && !up && !down)
				{
					if (frog.getX() >= 613 && frog.getY() >= 287)
					{
						frog.setX(613);
					}
					else
					{
						right = true;
					}
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if (!right && !up && !down)
				{
					if (frog.getX() <= 37 && frog.getY() >= 287)
					{
						frog.setX(37);
					}
					else
					{
						left = true;
					}
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				if (!right && !left && !down)
				{
					up = true;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				if (!right && !left && !up)
				{
					if (frog.getY() >= 527)
					{
						frog.setY(527);
					}
					else
					{
						down = true;
					}
				}
			}
			
			//Allow user to press 1 to play again or press 0 to quit when game is over or they win
			if (level >= 3 || lives <= 0)
			{
				if (e.getKeyCode() == KeyEvent.VK_0)
				{
					System.exit(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_1)
				{
					restart(); // Call restart method and reset all default values/levels
				}
			}

			
		}
	}

	public void actionPerformed(ActionEvent e)
	{

		if (e.getSource() == tmrTurtleDive)
		{
			//Animate turtle diving into water
			turtle[0].setImage(new ImageIcon("images\\2turtle" + diveValue + ".png"));
			turtle[4].setImage(new ImageIcon("images\\3turtle" + diveValue + ".png"));


			if (diveValue >= 5 || diveValue <= 0)
			{
				num = -num;
			}

			if (diveValue >= 4)
			{
				turtleDown = true;
			}
			else
			{
				turtleDown = false;;
			}

			diveValue += num;
		}

		else if (e.getSource() == tmrOverall)
		{
			//Timer for frog moving left/right/up/down gradually, but swiftly for 'hopping' effect of an actual frog
			if (right)
			{
				frog.moveRight(FRAME_WIDTH);
				stepCounter++;
			}
			else if (left)
			{
				frog.moveLeft(FRAME_WIDTH);
				stepCounter++;
			}
			else if (up)
			{
				frog.moveUp(FRAME_WIDTH);
				stepCounter++;
			}
			else if (down)
			{
				frog.moveDown(FRAME_WIDTH);
				stepCounter++;
			}

			
			if (stepCounter >= 4)//After the frog's jump program will...
			{
				//Increase score by 10 when jumped forward
				if (up && !frog.getDeath() && lives > 0 && level < 3)
				{
					score+= 10;
				}

				//Reset all direection booleans to stop movement after jump
				right = false;
				left = false;
				up = false;
				down = false;

				if (frog.getY() <= 47)//When frog's y position is very close to the lilipads..
				{
					for (int i = 0; i < xFrog.length; i++)
					{
						/* Check if frog landed in lilypad, if it did, play respective sound effects, add points
						 * accord to time remaining, respawn frog with default, y, increase value of frogs in lilypads,
						 * change the empty lilypad to image of frog in it along with a boolean to tell if a frog is occupying
						 * said lilypad. If frog misses a lilypad, kill frog.
						 */
						if (frog.getX() >= xFrog[i] - 17 && frog.getX() <= xFrog[i] + 20 && !frogOccupied[i])
						{
							imgFrogs[i] = new ImageIcon("images\\frogDown.png");
							frogOccupied[i] = true;
							frog.respawn(FRAME_WIDTH);
							frogs++;

							if (frogs > 0)
							{
								score += 1000;
							}

							score += 50 + (10 * seconds);

							//Reset timer along with its colour
							location = 370;
							width = 200;
							c = Color.GREEN;
							seconds = 47;
							timerCounter = 0;

							break;
						}
						else
						{
							death();
						}
					}
				}

				//After frog's jump, set this boolean to true
				successfulJump = true;

				/* When frog's jump is done, check if frog's y position is getting near the river. If it is within
				 * the river, check if it's on any of the logs or turtles. Since the frog moves gradually to get a
				 * hopping effect and not instantly, the frog covers every x and y while jumping. Using an array also
				 * checks for all logs/turtle, so if a frog is one log #1, program will still check for log #2,
				 * kill frog, even if its one either on of those logs/turtles. This being so, using
				 * an array of checking if the frog is within the boundaries of a log/turtle would cause problems while
				 * the frog is jumping from one turtle/log to another (and ultimately kill it). Due to shortness of 
				 * time, program checks each and every log/turtle individually instead of in an array.
				 */
				if (successfulJump)
				{
					if (frog.getY() <= 247)
					{

						if ((frog.getY() + frog.getHeight() <= log[0].getY() + log[0].getHeight() + 5 &&
								frog.getY() >= log[0].getY() &&
								frog.getX() + (frog.getWidth()/2) >= log[0].getX() &&
								frog.getX() + (frog.getWidth()/2) <= log[0].getX() + log[0].getWidth() - head)

								||

								(frog.getY() + frog.getHeight() <= log[1].getY() + log[1].getHeight() + 5 &&
								frog.getY() >= log[1].getY() &&
								frog.getX() + (frog.getWidth()/2) >= log[1].getX() &&
								frog.getX() + (frog.getWidth()/2) <= log[1].getX() + log[1].getWidth() - head)

								||

								(frog.getY() + frog.getHeight() <= log[2].getY() + log[2].getHeight() + 5 &&
								frog.getY() >= log[2].getY() &&
								frog.getX() + (frog.getWidth()/2) >= log[2].getX() &&
								frog.getX() + (frog.getWidth()/2) <= log[2].getX() + log[2].getWidth() - head)

								||
								(frog.getY() + frog.getHeight() <= log[3].getY() + log[3].getHeight() + 5 &&
								frog.getY() >= log[3].getY() &&
								frog.getX() + (frog.getWidth()/2) >= log[3].getX() &&
								frog.getX() + (frog.getWidth()/2) <= log[3].getX() + log[3].getWidth() - head)

								||

								(frog.getY() + frog.getHeight() <= log[4].getY() + log[4].getHeight() + 5 &&
								frog.getY() >= log[4].getY() &&
								frog.getX() + (frog.getWidth()/2) >= log[4].getX() &&
								frog.getX() + (frog.getWidth()/2) <= log[4].getX() + log[4].getWidth() - head)

								||

								(frog.getY() + frog.getHeight() <= log[5].getY() + log[5].getHeight() + 5 &&
								frog.getY() >= log[5].getY() &&
								frog.getX() + (frog.getWidth()/2) >= log[5].getX() &&
								frog.getX() + (frog.getWidth()/2) <= log[5].getX() + log[5].getWidth() - head)

								||

								//TURTLES

								(frog.getY() + frog.getHeight() <= turtle[0].getY() + turtle[0].getHeight() + 5 &&
								frog.getY() >= turtle[0].getY() &&
								frog.getX() + (frog.getWidth()/4) >= turtle[0].getX() &&
								frog.getX() + (frog.getWidth()/2)<= turtle[0].getX() + turtle[0].getWidth())

								||

								(frog.getY() + frog.getHeight() <= turtle[1].getY() + turtle[1].getHeight() + 5 &&
								frog.getY() >= turtle[1].getY() &&
								frog.getX() + (frog.getWidth()/4) >= turtle[1].getX() &&
								frog.getX() + (frog.getWidth()/2)<= turtle[1].getX() + turtle[1].getWidth())

								||

								(frog.getY() + frog.getHeight() <= turtle[2].getY() + turtle[2].getHeight() + 5 &&
								frog.getY() >= turtle[2].getY() &&
								frog.getX() + (frog.getWidth()/4) >= turtle[2].getX() &&
								frog.getX() + (frog.getWidth()/2)<= turtle[2].getX() + turtle[2].getWidth())
								||

								(frog.getY() + frog.getHeight() <= turtle[3].getY() + turtle[3].getHeight() + 5 &&
								frog.getY() >= turtle[3].getY() &&
								frog.getX() + (frog.getWidth()/4) >= turtle[3].getX() &&
								frog.getX() + (frog.getWidth()/2)<= turtle[3].getX() + turtle[3].getWidth())
								||

								(frog.getY() + frog.getHeight() <= turtle[4].getY() + turtle[4].getHeight() + 5 &&
								frog.getY() >= turtle[4].getY() &&
								frog.getX() + (frog.getWidth()/4) >= turtle[4].getX() &&
								frog.getX() + (frog.getWidth()/2)<= turtle[4].getX() + turtle[4].getWidth())
								||

								(frog.getY() + frog.getHeight() <= turtle[5].getY() + turtle[5].getHeight() + 5 &&
								frog.getY() >= turtle[5].getY() &&
								frog.getX() + (frog.getWidth()/4) >= turtle[5].getX() &&
								frog.getX() + (frog.getWidth()/2)<= turtle[5].getX() + turtle[5].getWidth()))


						{
							successfulJump = false;
						}
						else
						{
							death();
							successfulJump = false;
						}

					}
				}

				stepCounter = 0; // Set stepCounter back to 0 so program can measure frog's 'steps' again for the next
				                 // jump.
			}

			/* Movement of all obstacles in the game take place. If frog is within boundaries of a vehicle object, 
			 * frog will die. If frog is in-between the boundaries of a snake object, frog will also die. If frog is
			 * in-between boundaries of a turtle or log object, frog will stay alive (as seen above), and frog's x -
			 * position will follow that of the log or turtle, unless it hits the boundaries of the frame, which will
			 * ultimately kill the frog.
			 */
			for (int i = 0; i < 3; i++)
			{
				v1[i].moveLeft(4 + lvl2Pixels, FRAME_WIDTH);
				v2[i].moveRight(4 + lvl2Pixels, FRAME_WIDTH);
				v3[i].moveLeft(7 + lvl2Pixels, FRAME_WIDTH);
				v4[i].moveRight(5 + lvl2Pixels, FRAME_WIDTH);

				if (frog.getY() <= v1[i].getY() + v1[i].getHeight() &&
						frog.getY() + frog.getHeight() >= v1[i].getY() + 5 &&
						frog.getX() + frog.getWidth() >= v1[i].getX() &&
						frog.getX() <= v1[i].getX() + v1[i].getWidth())
				{
					death();
				}
				else if(frog.getY() <= v2[i].getY() + v2[i].getHeight() &&
						frog.getY() + frog.getHeight() >= v2[i].getY() + 5 &&
						frog.getX() + frog.getWidth() >= v2[i].getX() &&
						frog.getX() <= v2[i].getX() + v2[i].getWidth())
				{
					death();
				}
				else if(frog.getY() <= v3[i].getY() + v3[i].getHeight() &&
						frog.getY() + frog.getHeight() >= v3[i].getY() + 5 &&
						frog.getX() + frog.getWidth() >= v3[i].getX() &&
						frog.getX() <= v3[i].getX() + v3[i].getWidth())
				{
					death();
				}
				else if(frog.getY() <= v4[i].getY() + v4[i].getHeight() &&
						frog.getY() + frog.getHeight() >= v4[i].getY() + 5 &&
						frog.getX() + frog.getWidth() >= v4[i].getX() &&
						frog.getX() <= v4[i].getX() + v4[i].getWidth())
				{
					death();
				}

			}

			for (int i = 0; i < v5.length; i++)
			{
				v5[i].moveLeft(7 + lvl2Pixels, FRAME_WIDTH);

				if(frog.getY() <= v5[i].getY() + v5[i].getHeight() &&
						frog.getY() + frog.getHeight() >= v5[i].getY() + 5 &&
						frog.getX() + frog.getWidth() >= v5[i].getX() &&
						frog.getX() <= v5[i].getX() + v5[i].getWidth())
				{
					death();
				}
			}

			for (int i = 0; i < log.length; i++)
			{
				if (i < 2)
				{
					log[i].move(4 + lvl2Pixels, FRAME_WIDTH);

					if (frog.getY() + frog.getHeight() <= log[i].getY() + log[i].getHeight() + 5 &&
							frog.getY() >= log[i].getY() &&
							frog.getX() + (frog.getWidth()/2) >= log[i].getX() &&
							frog.getX() + (frog.getWidth()/2) <= log[i].getX() + log[i].getWidth())
					{
						if (!frog.getDeath())
						{
							frog.setX(frog.getX() + 4 + lvl2Pixels);
						}
					}
				}
				else if (i >= 2 && i < 4)
				{
					log[i].move(5 + lvl2Pixels, FRAME_WIDTH);

					if (frog.getY() + frog.getHeight() <= log[i].getY() + log[i].getHeight() + 5 &&
							frog.getY() >= log[i].getY() &&
							frog.getX() + (frog.getWidth()/2) >= log[i].getX() &&
							frog.getX() + (frog.getWidth()/2) <= log[i].getX() + log[i].getWidth())
					{
						if (!frog.getDeath())
						{
							frog.setX(frog.getX() + 5 + lvl2Pixels);
						}
					}
				}
				else if (i >= 4)
				{
					log[i].move(3 + lvl2Pixels, FRAME_WIDTH);

					if (frog.getY() + frog.getHeight() <= log[i].getY() + log[i].getHeight() + 5 &&
							frog.getY() >= log[i].getY() &&
							frog.getX() + (frog.getWidth()/2) >= log[i].getX() &&
							frog.getX() + (frog.getWidth()/2) <= log[i].getX() + log[i].getWidth())
					{
						if (!frog.getDeath())
						{
							frog.setX(frog.getX() + 3 + lvl2Pixels);
						}
					}
				}

				if (frog.getY() + frog.getHeight() <= turtle[0].getY() + turtle[0].getHeight() + 5 &&
						frog.getY() >= turtle[0].getY() &&
						frog.getX() + (frog.getWidth()/4) >= turtle[0].getX() &&
						frog.getX() + (frog.getWidth()/2) <= turtle[0].getX() + turtle[0].getWidth()

						||

						(frog.getY() + frog.getHeight() <= turtle[4].getY() + turtle[4].getHeight() + 5 &&
						frog.getY() >= turtle[4].getY() &&
						frog.getX() + (frog.getWidth()/4) >= turtle[4].getX() &&
						frog.getX() + (frog.getWidth()/2) <= turtle[4].getX() + turtle[4].getWidth()))
				{
					if (turtleDown)
					{
						death();
					}
				}

				if (i < 3)
				{
					turtle[i].move(4 + lvl2Pixels, FRAME_WIDTH);

					if (frog.getY() + frog.getHeight() <= turtle[i].getY() + turtle[i].getHeight() + 5 &&
							frog.getY() >= turtle[i].getY() &&
							frog.getX() + (frog.getWidth()/4) >= turtle[i].getX() &&
							frog.getX() + (frog.getWidth()/2) <= turtle[i].getX() + turtle[i].getWidth())
					{
						if (!frog.getDeath())
						{
							frog.setX(frog.getX() - 4 - lvl2Pixels);
						}
					}
				}
				else
				{
					turtle[i].move(3 + lvl2Pixels, FRAME_WIDTH);

					if (frog.getY() + frog.getHeight() <= turtle[i].getY() + turtle[i].getHeight() + 5 &&
							frog.getY() >= turtle[i].getY() &&
							frog.getX() + (frog.getWidth()/4) >= turtle[i].getX() &&
							frog.getX() + (frog.getWidth()/2) <= turtle[i].getX() + turtle[i].getWidth())
					{
						if (!frog.getDeath())
						{
							frog.setX(frog.getX() - 3 - lvl2Pixels);
						}
					}
				}


			}

			if ((frog.getY() <= 247) && (frog.getX() + frog.getWidth() >= FRAME_WIDTH || frog.getX() <= 0))
			{
				death();
			}

			snake.move(snakeAmount + lvl2Pixels, FRAME_WIDTH);

			if (frog.getY() <= snake.getY() + snake.getHeight() &&
					frog.getY() + frog.getHeight() >= snake.getY() + 5 &&
					frog.getX() + frog.getWidth() >= snake.getX() &&
					frog.getX() <= snake.getX() + snake.getWidth())
			{
				death();
			}

			/* Time bar for the alloted time. If the game is still going (game not over and game not beaten), the width
			 * of the rectangle made to show the time bar will decrease the same amount as the location of the 
			 * rectangle will increase. This will make it seem as if the time-bar is not moving at all, but 
			 * continuously getting smaller.
			 */
			if (lives > 0 && level < 3 )
			{
				location += .2;
				width -= .2;
			}

			/* The program's timer interval is every 50 milliseconds. Therefore, this timeCounter has been programmed 
			 * to go off every second, and reduce the variable 'seconds' by one. (Seconds starts at approximately 48).
			 * The variable 'seconds' is used to calculate the frog's points of alloted time only.
			 */
			timerCounter += 50;

			if (timerCounter >= 1000)
			{
				seconds--;
				timerCounter = 0;
			}

			if (seconds <= 0)
			{
				seconds = 0;
				timerCounter = 0;
			}

			// When the width of the timer bar passes the middle, it will turn yellow, when it gets lower, it will turn
			// red and an 'alerting' sound effect will play.
			if (width <= 125 && width > 50)
			{
				c = Color.YELLOW;
			}
			else if (width <= 50)
			{
				c = Color.RED;
			}

			//When the timer reaches 'zero', the frog will die, and the width and x position of the rectangle will 
			// stay the same until reset.
			if (width <= 10)
			{
				width = 10;
				location = 560;
				
				death();
			}

			//When the frog dies, the frog's image will change to a skull and cross-bones for a short amount of time.
			if (frog.getDeath() && lives > 0 && level < 3)
			{
				deathCounter++;

				/* After the death timer, frog will respawn, with reset x and y positions, seconds will be reset, 
				 * Timer bar will be reset along with a reset colour. When it is gameover, timer bar will not reset
				 */
				if (deathCounter >= 25)
				{
					frog.respawn(FRAME_WIDTH);
					deathCounter = 0;
					seconds = 47;
					timerCounter = 0;
					lives--;
					c = Color.GREEN;

					if (lives > 0)
					{
						location = 370;
						width = 200;
					}

					//Lives at the bottom will be refreshed to the remaining lives left.
					imgLives = new ImageIcon("images\\lives" + lives + ".png");
				}
			}

			if (lives <= 0)
			{
				gameOver = true;
				frog.setImage(new ImageIcon(""));
			}

			/* When the game is over, a message will fall from the top of the frame, either saying 'GAMEOVER' or
			 * 'YOU WIN!', depending on the circumstances.
			 */
			
			if(gameOver)
			{
				strWidth = fm.stringWidth(message);
				stringY += 10;

				/* When  the y of the string reaches a certain point, it will stop, and the y position of the second
				 * message will appear (play again and quit)
				 */
				if (stringY >= 330)
				{
					stringY = 330;
					stringChoiceY = 360;
				}

			}

			/* When 5 frogs have reached their lilypads, the frog variable will be set to zero, the images of the frogs
			 * on the lilypads will be removed, and their booleans reset to zero. The level will increase and the 
			 * obstacle's x-position will be increased to move faster. All logs will be switched to crocodiles after
			 * they pass the screen. The variable 'head' is the amount of pixels that will be removed from the crocs'
			 * width when the frog will check the boundaries of the crocodile, meaning, the frog can only move of
			 * the crocodile's back. If it moves towards the head (the removed x-position boundary), the frog will
			 * die.
			 */
			if (frogs >= 5)
			{
				for (int i = 0; i < imgFrogs.length; i++)
				{
					imgFrogs[i] = new ImageIcon("");
					frogOccupied[i] = false;
				}

				momentCounter++;
				{
					if (momentCounter >= 5)
					{
						lvl2Pixels = 3;
						frogs = 0;
						head = 80;
						level++;
						momentCounter = 0;

						for (int i = 0; i < log.length; i++)
						{
							log[i].setLevel2(true);
						}

					}
				}
			}

			/* When all 5 frogs have reached their bases for a second time, the message colour will be changed to green
			 * and say 'YOU WIN!'. The second message of play again and quit will appear. Frog will also disappear.
			 */
			if (level >= 3)
			{
				c1 = Color.GREEN;
				message = "YOU WIN!";
				strWidth = fm.stringWidth(message);
				gameOver = true;
				frog.setDeath(true);
				frog.setImage(new ImageIcon(""));
			}

			repaint();
		}
	}

	public void paintComponent(Graphics g)
	{
		
		//Paint and display all images and rectangles on JPanel.
		super.paintComponent(g);
		g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.drawImage(imgBackground.getImage(), 0, 0, this);
		g2.drawImage(imgLives.getImage(), 255, 587, this);

		g2.setFont(f);
		g2.setColor(Color.GREEN);
		g2.drawString(Integer.toString(score), 155, 609);

		g2.setColor(c);
		g2.fill(new RoundRectangle2D.Double(location, 585, width, 25, 10, 10));

		for (int i = 0; i < imgFrogs.length; i++)
		{
			g2.drawImage(imgFrogs[i].getImage(), xFrog[i], 43, this);
		}

		for (int i = 0; i < turtle.length; i++)
		{
			turtle[i].drawTurtle(g2);
		}

		for (int i = 0; i < log.length; i++)
		{
			log[i].drawLog(g2);
		}

		for (int i = 0; i < v1.length; i++)
		{
			v1[i].drawVehicle(g2);
			v2[i].drawVehicle(g2);
			v3[i].drawVehicle(g2);
			v4[i].drawVehicle(g2);
		}

		for (int i = 0; i < v5.length; i++)
		{
			v5[i].drawVehicle(g2);
		}

		snake.drawSnake(g2);
		frog.drawFrog(g2);

		g2.setFont(f1);
		g2.setColor(c1);
		g2.drawString(message,  getWidth() / 2 - strWidth / 2, stringY); //330

		g2.setFont(f);
		g2.drawString(choice, getWidth() / 2 - strChoiceWidth / 2, stringChoiceY);

	}

	//Death method. Kills frog along with death sound effect.
	public void death()
	{
		frog.die();
	}

	/* Restart methods. Restarts all variables to their default settings at the beginning of the game. Resets all
	 * sound effects along with stopping them.
	 */
	public void restart()
	{
		frog.setImage(new ImageIcon("images\\frogUp.png"));
		frog.setDeath(false);
		frog.setY(527);
		frog.setX(FRAME_WIDTH/2 - frog.getWidth()/2);

		for (int i = 0; i < log.length; i++)
		{
			log[i].setLevel2(false);
		}

		for (int i = 0; i < imgFrogs.length; i++)
		{
			imgFrogs[i] = new ImageIcon("");
			frogOccupied[i] = false;
		}

		gameOver = false;

		message = "GAMEOVER";
		c1 = Color.RED;
		c = Color.GREEN;

		lvl2Pixels = 0;
		level = 1;
		stringY = -10;
		stringChoiceY = -10;
		head = 0;
		score = 0;
		frogs = 0;

		lives = 3;
		location = 370;
		width = 200;
		seconds = 47;

		imgLives = new ImageIcon("images\\lives" + lives + ".png");
	}
}