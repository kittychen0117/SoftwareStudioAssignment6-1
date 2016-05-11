package main.java;

import java.util.ArrayList;
import java.util.HashMap;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	public float x, y, radius;
	public String name;
	public MainApplet parent;
	private boolean insideNetwork;
	private int color;
	private ArrayList<Character> targets;
	private HashMap<String, Integer> value;//line weight
	private float localX,localY;//original position

	public Character(MainApplet parent, String name, float x, float y, int color){//initial

		this.parent = parent;
		this.name = name;
		this.x = x;
		this.y = y;
		this.localX = x;
		this.localY = y;
		this.radius = 40;
		this.color = color;
		this.insideNetwork = false;
		this.targets = new ArrayList<Character>();
		this.value = new HashMap<String, Integer>();
	}

	public void display(){//draw a circle is filled
		this.parent.noStroke();
		this.parent.fill(color);
		this.parent.ellipse(this.x, this.y, this.radius, this.radius);
	}
	public void setPosition(float x,float y){//set the character's position
		this.x = x;
		this.y = y;
	}
	public void setlocal(){//to put original position
		this.x = this.localX;
		this.y = this.localY;
	}
	public void addTarget(Character target,int v){//add targets and the line weight
		this.targets.add(target);
		this.value.put(target.name, v);
	}
	
	public ArrayList<Character> getTargets(){//to get the target
		return this.targets;
	}
	public void setInsideNetwork(boolean s){//set whether is inside the network or not
		this.insideNetwork = s;
	}
	public boolean getInsideNetwork(){//get whether is inside the network or not
		return this.insideNetwork;
	}
	
	public int getvalue(String n){//get the line weight
		return value.get(n);
	}
}
