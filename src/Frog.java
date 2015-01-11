// Frog Class Done By: Alex Adusei

import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Frog
{

	//Declare the field variables of the class
	private int xPos, yPos, width, height;
	private ImageIcon imgFrog;
	private boolean dead;

	/*Declare the constructor of the class, which only gives the user access to one parameter (frame's width)
	 * along with default variables
	 */
	public Frog(int FRAME_WIDTH)
	{
		imgFrog = new ImageIcon("images\\frogUp.png");
		width = imgFrog.getIconWidth();
		height = imgFrog.getIconHeight();
		xPos = FRAME_WIDTH/2 - width/2;
		yPos = 527;
		dead = false;
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
		this.imgFrog = img;
	}

	public void setDeath (boolean death)
	{
		this.dead = death;
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
		return this.imgFrog;
	}

	public boolean getDeath()
	{
		return this.dead;
	}

	/*Declare methods of the frog's movement. Only move the frog when the 'dead' boolean is not true.
	 * Increase/decrease the frog's x and y according to which method was called, along with changing the frog's
	 * picture for its position
	 */
	public void moveRight(int FRAME_WIDTH)
	{
		if (!this.dead)
		{
			this.xPos += 12;
			this.imgFrog = new ImageIcon("images\\frogRight.png");
		}
	}

	public void moveLeft(int FRAME_WIDTH)
	{
		if (!this.dead)
		{
			this.xPos -= 12;
			this.imgFrog = new ImageIcon("images\\frogLeft.png");
		}
	}

	public void moveUp(int FRAME_WIDTH)
	{
		if (!this.dead)
		{
			this.yPos -= 10;
			this.imgFrog = new ImageIcon("images\\frogUp.png");
		}
	}

	public void moveDown(int FRAME_WIDTH)
	{
		if (!this.dead)
		{
			this.yPos += 10;
			this.imgFrog = new ImageIcon("images\\frogDown.png");
		}
	}

	//Method for frog's death, setting it to death picture and changing dead boolean to true.
	public void die()
	{
		this.imgFrog = new ImageIcon("images\\frogDead.png");
		this.dead = true;
	}
	
	/* Respawning method, setting its position back to default, along with its default image and setting dead
	 * boolean back to false
	 */
	public void respawn(int FRAME_WIDTH)
	{
		this.dead = false;
		this.xPos = FRAME_WIDTH/2 - width/2;
		this.yPos = 527;
		this.imgFrog = new ImageIcon("images\\frogUp.png");
	}

	//Draws the frog image
	public void drawFrog (Graphics2D g2)
	{
		g2.drawImage(imgFrog.getImage(), xPos, yPos, width, height, null);
	}
}