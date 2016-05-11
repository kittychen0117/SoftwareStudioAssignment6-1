package main.java;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

/**
* This class is used for the visualization of the network.
* Depending on your implementation, you might not need to use this class or create a class on your own.
* I used the class to draw the circle and re-arrange nodes and links.
* You will need to declare other variables.
*/
public class Network {
	
	private PApplet parent;
	public float x,y,radius;
	private ArrayList<Character> ch_innet;

	public Network(PApplet parent){

		this.parent = parent;
		this.x = 600;
		this.y = 350;
		this.radius = 500;
		this.ch_innet = new ArrayList<Character>();
	}

	public void display(){
		this.parent.fill(256,256,256);
		this.parent.stroke(204, 255, 102);
		this.parent.strokeWeight(5);
		this.parent.ellipse(x, y, radius, radius);
		for (int i=0;i<ch_innet.size();i++){
			for (int j=0;j<ch_innet.get(i).getTargets().size();j++){
				if (ch_innet.get(i).getTargets().get(j).getInsideNetwork()){
					this.parent.noFill();
					this.parent.stroke(0, 0, 0);
					this.parent.strokeWeight(1);
					this.parent.bezier(ch_innet.get(i).x, ch_innet.get(i).y, x, y, x, y,
					ch_innet.get(i).getTargets().get(j).x, ch_innet.get(i).getTargets().get(j).y);
				}
			}
		}
	}
	
	public void arrangePosition(){
		for (int i=0;i<this.ch_innet.size();i++){
			double lenth = (double)this.ch_innet.size();
			double part = i/lenth;
			double r_x = this.radius/2*Math.sin(part*2*Math.PI);
			double r_y = this.radius/2*Math.cos(part*2*Math.PI);
			/*System.out.println(part);
			System.out.println(r_x);
			System.out.println(r_y);*/
			float rx_fl = (float)r_x;
			float ry_fl = (float)r_y;
			/*System.out.println(rx_fl);
			System.out.println(ry_fl);*/
			this.ch_innet.get(i).setPosition(this.x+rx_fl,this.y+ry_fl);
		}
	}
	public void addnode(Character ch){
		this.ch_innet.add(ch);
	}
	public void removenode(Character ch){
		this.ch_innet.remove(ch);
	}
}
