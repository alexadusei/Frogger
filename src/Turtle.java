// Turtle Class Done By: Bon Castro

import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Turtle {
	
	//Declare field variables
	private int xPos, yPos, width, height;
	private ImageIcon imgTurtle;

	//Declare constructor
	public Turtle(ImageIcon imgTurtle, int xPos, int yPos) {
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.imgTurtle = imgTurtle;
		this.width = imgTurtle.getIconWidth();
		this.height = imgTurtle.getIconHeight();
	}
	
	//Declare accessor methods
	public int getX(){
		
		return xPos;
	}
	
	public int getY(){
		
		return yPos;
	}
	public int getWidth(){
		
		return width;
	}
	public int getHeight(){
		
		return height;
	}
	public ImageIcon getImage(){
		
		return imgTurtle;
	}
	//Declare method to move the x position of the log;
	public void move(int pixels, int FRAME_WIDTH){
		
		xPos -= pixels;
		
		if(xPos + width <= 0) // When turtle's x is less than 0, set the turtle's x to the right of the frame
		{
			xPos = FRAME_WIDTH;
		}
	}
	
	//Declare mutator methods
	public void setX(int x){
		
		xPos = x;
	}
	public void setY(int y){
		
		yPos = y;
	}
	public void setWidth(int width){
		
		this.width = width;
	}
	public void setHeight(int height){
		
		this.height = height;
	}
	public void setImage(ImageIcon imgTurtle){
		
		this.imgTurtle = imgTurtle;
	}
	//Draw turtle
	public void drawTurtle(Graphics2D g2){
		
		g2.drawImage(imgTurtle.getImage(), xPos, yPos, null);
	}

}
