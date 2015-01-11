// Snake Class Done by: Alex Adusei

import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Snake
{
	//Declare the field variables of the class
	private int xPos, yPos, width, height, direction;
	private int[] yPotential;
	private ImageIcon imgSnake;
	private Random rnd;

	/*Declare the constructor for the Snake class. It being a no-arguments constructor, it needs no parameters, because its
	 * best the user has no control over what the snake does for this program. All variables have one default in this 
	 * constructor
	 */
	public Snake()
	{
		rnd = new Random();
		yPotential = new int[] {85, 125, 165, 205, 245, 280}; // potential y-positions the snake can take
		
		direction = 1; // direction of snake (0 = west, 1 = east)
		
		imgSnake = new ImageIcon("images\\snake" + direction + ".png");
		width = imgSnake.getIconWidth();
		height = imgSnake.getIconHeight();
		xPos = -width;
		yPos = 280;
	}

	//Declare all the mutator methods with changeable data
	public void setX (int x)
	{
		this.xPos = x;
	}

	public void setY (int y)
	{
		this.yPos = y;
	}

	public void setLocation(int x, int y)
	{
		this.xPos = x;
		this.yPos = y;
	}

	public void setWidth (int w)
	{
		this.width = w;
	}

	public void setHeight (int h)
	{
		this.height = h;
	}

	public void setSize (int w, int h)
	{
		this.width = w;
		this.height = h;
	}

	public void setImage(ImageIcon img)
	{
		this.imgSnake = img;
	}

	//Declare all the accessor methods which return data
	public int getX()
	{
		return this.xPos;
	}

	public int getY()
	{
		return this.yPos;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public ImageIcon getImage()
	{
		return this.imgSnake;
	}

	/* Method to move snake on the x position. If direction is 0, the snake will move left and be facing left-wards.
	 * If the direction is 1, or anything but 0, it will face and move in the opposite direction. When the snake goes past
	 * any sides of the frame (left or right sides), the snake's direction will be randomized along with its y-position.
	 */
	public void move(int pixels, int FRAME_WIDTH)
	{
		if (direction == 0)
		{
			this.xPos -= pixels;
		}
		else
		{
			this.xPos += pixels;
		}

		if (xPos <= 0 - width || xPos >= FRAME_WIDTH + width)
		{
			direction = rnd.nextInt(2);
			yPos = yPotential[rnd.nextInt(1) + 4];
			
			if (direction == 0)
			{
				xPos = FRAME_WIDTH;
			}
			else
			{
				xPos = 0 - width;
			}
			
			setImage(new ImageIcon("images\\snake" + direction + ".png"));
		}
	}

	//Draws the snake
	public void drawSnake (Graphics2D g2)
	{
		g2.drawImage(imgSnake.getImage(), xPos, yPos, width, height, null);
	}
}