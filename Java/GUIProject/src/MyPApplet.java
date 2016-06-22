import java.awt.Point;

import processing.core.*;


public class MyPApplet extends PApplet{
	
	private String url;
	private PImage backgroundImg;
	Board board;
	public void setup(){
		size(400,400);
		backgroundImg = loadImage("D:\\Java\\GUIProject\\bin\\test.jpg","jpg");
		backgroundImg.resize(400, 400);
		background(160,160,160);
		board  = new Board(10,10,300,this);
	}
	public void draw(){
		//ellipse(10,10,10,10);
		//line(10,10,100,100);
		board.draw();
	}



}


