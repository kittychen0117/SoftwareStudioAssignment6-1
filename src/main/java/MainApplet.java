package main.java;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import controlP5.*;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	private String file[] = {"starwars-episode-1-interactions.json",
							"starwars-episode-2-interactions.json",
							"starwars-episode-3-interactions.json",
							"starwars-episode-4-interactions.json",
							"starwars-episode-5-interactions.json",
							"starwars-episode-6-interactions.json",
							"starwars-episode-7-interactions.json"};//filename
	
	JSONObject data;
	JSONArray nodes, links;
	
	private ControlP5 cp5;
	private int episode = 0;
	/*music*/
	Minim minim;
	AudioPlayer song;
	
	private ArrayList<Character> characters;
	private Character ch_now;//the node is mousedragged 
	private Network network;

	private final static int width = 1200, height = 650;
	
	public void setup() {

		size(width, height);
		background(255, 255, 255);
		
		characters = new ArrayList<Character>();
		
		cp5 = new ControlP5(this);//add two buttons
		cp5.addButton("buttonA")
		.setLabel("ADD ALL")
		.setPosition(width-250, 100)
		.setSize(200, 50);
		cp5.addButton("buttonB")
		.setLabel("CLEAR")
		.setPosition(width-250, 300)
		.setSize(200, 50);
		
		minim = new Minim(this);//add music
		song = minim.loadFile("main/resources/song.mp3");
		song.play();
		
		network = new Network(this);
		
		loadData();
		smooth();
	}

	public void draw() {
		background(255,255,255);
		
		network.display();//draw a big circle and the strokes
		
		this.fill(0, 0, 0);//draw the title of episode
		textSize(32);
		this.text("Star Wars"+(this.episode+1), 500, 50);
		
		for (int i=0;i<characters.size();i++){//draw the characters
			characters.get(i).display();
		}
		
		for (int i=0;i<characters.size();i++){//draw the character's name when mouse move to it
			if ((characters.get(i).x-mouseX)*(characters.get(i).x-mouseX)+
				(characters.get(i).y-mouseY)*(characters.get(i).y-mouseY)<300){
				this.fill(153, 204, 255);
				this.noStroke();//the name's background
				this.rect(characters.get(i).x-20,characters.get(i).y-40,characters.get(i).name.length()*12,30,3);
				this.fill(0, 0, 0);
				textSize(15);
				this.text(characters.get(i).name, characters.get(i).x-20, characters.get(i).y-20);//show the name
			}
		}
	}

	private void loadData(){
		data = loadJSONObject(path+file[this.episode]);
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");
		for (int i=0;i<nodes.size();i++){//load nodes into the characters
			JSONObject temp = nodes.getJSONObject(i);
			String name = temp.getString("name");
			String tmp = temp.getString("colour");
			int color = Long.decode(tmp).intValue();
			characters.add(new Character(this,name,(float)30+(i%4)*50,(float)40+(i/4)*50,color));//add a character
		}
		for (int i=0;i<links.size();i++){//load links into the character
			JSONObject temp = links.getJSONObject(i);
			int source = temp.getInt("source");
			int target = temp.getInt("target");
			int value = temp.getInt("value");
			characters.get(source).addTarget(characters.get(target),value);//add targets and line weight
		}
	}
	public void mouseDragged(){//when mouse dragged the nodes is moved as mouse moved
		if (ch_now!=null){
			ch_now.setPosition(mouseX, mouseY);
		}
	}
	public void mousePressed(){//if in the region of the node ,ch_now = the node
		for (int i=0;i<characters.size();i++){
			if ((characters.get(i).x-mouseX)*(characters.get(i).x-mouseX)+
				(characters.get(i).y-mouseY)*(characters.get(i).y-mouseY)<300){
				ch_now = characters.get(i);
			}
		}
	}
	public void mouseReleased(){
		if (ch_now!=null){
			//if is not in the network and the node is dragged into the circle
			if ((ch_now.x-network.x)*(ch_now.x-network.x)+(ch_now.y-network.y)*(ch_now.y-network.y)<70000&&ch_now.getInsideNetwork()==false){
				this.network.addnode(ch_now);
				this.network.arrangePosition();//set position
				ch_now.setInsideNetwork(true);
			}
			//if is in the network and the node is dragged out of the circle
			else if (ch_now.getInsideNetwork()&&(ch_now.x-network.x)*(ch_now.x-network.x)+(ch_now.y-network.y)*(ch_now.y-network.y)>=70000){
				ch_now.setlocal();//node return its original position
				ch_now.setInsideNetwork(false);
				this.network.removenode(ch_now);
			}
			else {
				ch_now.setlocal();
				ch_now.setInsideNetwork(false);
			}
			ch_now = null;
		}
	}
	public void buttonA(){
		for (int i=0;i<characters.size();i++){//first remove all in the network
			this.network.removenode(characters.get(i));
		}
		for (int i=0;i<characters.size();i++){//add all in the network
			this.network.addnode(characters.get(i));
			this.network.arrangePosition();//set position
			characters.get(i).setInsideNetwork(true);
		}

	}
	public void buttonB(){
		for (int i=0;i<characters.size();i++){//remove all in the network
			this.network.removenode(characters.get(i));
			this.characters.get(i).setInsideNetwork(false);
			this.characters.get(i).setlocal();//set to the original position
		}
		
	}
	public void keyPressed() {//when key pressed, the episode++,change the episode
		if (this.episode==6){
			this.episode=0;
		}
		else {
			this.episode++;
		}
		setup();
	}
}
