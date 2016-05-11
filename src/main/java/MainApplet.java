package main.java;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PShape;
import processing.data.JSONArray;
import processing.data.JSONObject;

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
							"starwars-episode-7-interactions.json"};
	
	JSONObject data;
	JSONArray nodes, links;
	
	private ArrayList<Character> characters;
	private Character ch_now;
	private Network network;

	private final static int width = 1200, height = 650;
	
	public void setup() {

		size(1200, 650);
		background(255, 255, 255);
		
		characters = new ArrayList<Character>();
		
		network = new Network(this);
		
		loadData();
		smooth();
	}

	public void draw() {
		background(255,255,255);
		network.display();
		for (int i=0;i<characters.size();i++){
			characters.get(i).display();
		}
		for (int i=0;i<characters.size();i++){
			if ((characters.get(i).x-mouseX)*(characters.get(i).x-mouseX)+
				(characters.get(i).y-mouseY)*(characters.get(i).y-mouseY)<300){
				this.fill(153, 204, 255);
				this.noStroke();
				this.rect(characters.get(i).x-20,characters.get(i).y-40,characters.get(i).name.length()*10,30,3);
				this.fill(0, 0, 0);
				this.text(characters.get(i).name, characters.get(i).x-15, characters.get(i).y-20);
			}
		}
	}

	private void loadData(){
		data = loadJSONObject(path+file[0]);
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");
		for (int i=0;i<nodes.size();i++){
			JSONObject temp = nodes.getJSONObject(i);
			String name = temp.getString("name");
			String tmp = temp.getString("colour");
			int color = Long.decode(tmp).intValue();
			characters.add(new Character(this,name,(float)30+(i%4)*50,(float)40+(i/4)*50,color));
		}
		for (int i=0;i<links.size();i++){
			JSONObject temp = links.getJSONObject(i);
			int source = temp.getInt("source");
			int target = temp.getInt("target");
			int value = temp.getInt("value");
			characters.get(source).addTarget(characters.get(target));
		}
	}
	public void mouseDragged(){
		if (ch_now!=null){
			ch_now.setPosition(mouseX, mouseY);
		}
	}
	public void mousePressed(){
		for (int i=0;i<characters.size();i++){
			if ((characters.get(i).x-mouseX)*(characters.get(i).x-mouseX)+
				(characters.get(i).y-mouseY)*(characters.get(i).y-mouseY)<300){
				ch_now = characters.get(i);
			}
		}
	}
	public void mouseReleased(){
		if (ch_now!=null){
			
			if ((ch_now.x-network.x)*(ch_now.x-network.x)+(ch_now.y-network.y)*(ch_now.y-network.y)<70000&&ch_now.getInsideNetwork()==false){
				this.network.addnode(ch_now);
				this.network.arrangePosition();
				ch_now.setInsideNetwork(true);
			}
			else if (ch_now.getInsideNetwork()&&(ch_now.x-network.x)*(ch_now.x-network.x)+(ch_now.y-network.y)*(ch_now.y-network.y)>=70000){
				ch_now.setlocal();
				ch_now.setInsideNetwork(false);
				this.network.removenode(ch_now);
			}
			else {
				ch_now.setlocal();
				ch_now.setInsideNetwork(false);
				ch_now = null;
			}
		}
	}
}
