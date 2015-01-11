// Vehicle Class Done By: Stephen Dienaar

import javax.swing.*;
import java.awt.*;


public class Vehicle{
	//Declare field variables
	private int xPos, yPos, width, height;
	private ImageIcon imgVehicle;

	//Declare constructor with flexible parameters and default settings
	public Vehicle(ImageIcon imgVehicle, int xPos, int yPos){
		this.imgVehicle = imgVehicle;
		this.xPos = xPos;
		this.yPos = yPos;
		width = imgVehicle.getIconWidth();
		height = imgVehicle.getIconHeight();
	}
	//Declare mutator methods
	public void setX (int x){
		this.xPos = x;
	}
	public void setY (int y){
		this.yPos = y;
	}

	public void setLocation(int x, int y){
		this.xPos = x;
		this.yPos = y;
	}

	public void setWidth (int w){
		this.width = w;
	}

	public void setHeight (int h){
		this.height = h;
	}

	public void setSize (int w, int h){
		this.width = w;
		this.height = h;
	}

	public void setImage(ImageIcon img){
		this.imgVehicle = img;
	}

	//Declare accessor methods
	public int getX(){
		return this.xPos;
	}

	public int getY(){
		return this.yPos;
	}

	public int getWidth(){
		return this.width;
	}

	public int getHeight(){
		return this.height;
	}

	public ImageIcon getImage(){
		return this.imgVehicle;
	}

	//Declare methods of movements (left and right)
	public void moveRight(int pixels, int FRAME_WIDTH){
		this.xPos += pixels; // increase xPos by users desired pixel amount
		//If vehicle's xPosition is greater than the frame, set xPosition to the left
		if (xPos >= FRAME_WIDTH + width)
		{
			xPos = 0 - width;
		}
	}

	public void moveLeft(int pixels, int FRAME_WIDTH){
		this.xPos -= pixels; // decrease xPos by users desired pixel amount

		//If vehicle's xPosition is less than the frame, set the xPosition to the write
		if (xPos <= 0 - width){
			xPos = FRAME_WIDTH;
		}
	}

	//Draw the vehicle
	public void drawVehicle (Graphics2D g2){
		g2.drawImage(imgVehicle.getImage(), xPos, yPos, width, height, null);
	}
}