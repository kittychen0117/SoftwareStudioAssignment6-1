package main.java;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PShape;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	public float x, y, radius;
	public String name;
	public MainApplet parent;
	private int episode;
	private int color;
	private ArrayList<Character> targets;

	public Character(MainApplet parent, String name, float x, float y, int color){

		this.parent = parent;
		this.name = name;
		this.x = x;
		this.y = y;
		this.radius = 30;
		this.color = color;
		targets = new ArrayList<Character>();
	}

	public void display(){
		this.parent.stroke(color);
		this.parent.fill(color);
		this.parent.ellipse(this.x, this.y, this.radius, this.radius);
		this.parent.fill(0,0,0);
	}
	
}
