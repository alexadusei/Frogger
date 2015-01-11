// Log Class Done By: Bon Castro

import java.awt.*;
import javax.swing.*;

public class Log
{

	//Declare field variables
	private int xPos, yPos, width, height;
	private ImageIcon imgLog;
	private boolean lvl2;

	//Declare constructor
	public Log(int xPos, int yPos)
	{
		this.imgLog = new ImageIcon("images//log0.png");
		this.width = imgLog.getIconWidth();
		this.height = imgLog.getIconHeight();
		this.xPos = xPos;
		this.yPos = yPos;
		this.lvl2 = false;
	}

	//Declare mutator methods
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
		this.imgLog = img;
	}

	public void setLevel2 (boolean n)
	{
		this.lvl2 = n;
	}

	//Declare accessor methods
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
		return this.imgLog;
	}

	public boolean getLevel2()
	{
		return this.lvl2;
	}

	//Declare move method for the log
	public void move(int pixels, int FRAME_WIDTH)
	{
		this.xPos += pixels; // increase log's x

		//When log's x is greater than the frame, set log's x to the left of frame
		if (xPos >= FRAME_WIDTH)
		{
			xPos = 0 - width;

			// if game is at level two, switch log's image to crocodile only after it passes the frame's right side
			if (lvl2 && xPos <= 0) 
			{
				this.imgLog = new ImageIcon("images\\croc0.png");
			}
		}

		//When game is being reset and it is not level two, set image back to log, regardless of side
		if (!lvl2)
		{
			this.imgLog = new ImageIcon("images//log0.png");
		}

	}

	//Draw log
	public void drawLog (Graphics2D g2)
	{
		g2.drawImage(imgLog.getImage(), xPos, yPos, width, height, null);
	}
}