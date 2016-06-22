import java.awt.Point;

import processing.core.PApplet;


public class Board {
	PApplet parent;
	Point upperLeft;
	Point upperRight;
	Point lowerLeft;
	Point lowerRight;
	int   size;
	Point upperhor1;
	Point upperhor2;
	Point lowerhor1;
	Point lowerhor2;
	Point leftver1;
	Point leftver2;
	Point rightver1;
	Point rightver2;
	Board(int x, int y, int d, PApplet p){
		parent = p;
		upperLeft = new Point(x,y);
		size = d;
		upperRight = new Point(x+d,y);
		lowerLeft = new Point(x,y+d);
		lowerRight = new Point(x+d,y+d);
		upperhor1 = new Point(x+size/3,y);
		upperhor2 = new Point(x+2*size/3,y);
		lowerhor1 = new Point(x+size/3,y+d);
		lowerhor2 = new Point(x+2*size/3,y+d);
		leftver1 = new Point(x,y+size/3);
		leftver2 = new Point(x,y+2*size/3);
		rightver1 = new Point(x+d,y+size/3);
		rightver2 = new Point(x+d,y+2*size/3);
	}
	public void draw() {
		//parent.fill(255,255,0);
		parent.line(upperhor1.x,upperhor1.y,lowerhor1.x,lowerhor1.y);
		//parent.stroke(126);
		parent.line(upperhor2.x,upperhor2.y,lowerhor2.x,lowerhor2.y);
		//parent.stroke(126);
		parent.line(leftver1.x,leftver1.y,rightver1.x,rightver1.y);
		//parent.stroke(126);
		parent.line(leftver2.x,leftver2.y,rightver2.x,rightver2.y);
		//parent.stroke(126);
	}
}