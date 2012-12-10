package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class DisplayMap {
	
	static final int INF = Integer.MAX_VALUE;
	static final int NULL = Integer.MAX_VALUE-1;
	
	RealNetwork world;
	Point[] nodes;
	int width,height;
	
	public DisplayMap(int width, int height, RealNetwork world){

		this.world = world;
		this.width = width;
		this.height = height;
		if(world.generatedNetwork){
			generateNodes();
			return;
		}
		nodes = new Point[]{new Point(20,20),new Point(170,20),new Point(320,20),new Point(470,20),
							new Point(20,170),new Point(170,170),new Point(320,170),new Point(470,170),
							new Point(20,320),new Point(170,320),new Point(320,320),new Point(470,320),
							new Point(20,470),new Point(170,470),new Point(320,470),new Point(470,470),new Point(245,245)};
		
		//nodes = new Point[]{new Point (150,50), new Point(50,150)};
	}
	
	public void generateNodes(){
		float xUnit = width/(world.numWide-1);
		float yUnit = height/(world.numHigh-1);
		
		nodes = new Point[world.n];
		
		for(int y=0; y<world.numHigh; y++){
			for(int x=0; x<world.numWide; x++){
				nodes[x+(y*world.numWide)]= new Point((int)(20+(xUnit*x)),(int)(20+(yUnit*y)));
			}
		}
	}
	
	public void drawNetwork(Graphics g){
		for (int i=0; i<nodes.length; i++){
			for (int j=0; j<nodes.length; j++){
				if(world.edgeWeight[i][j]!=INF){
					
					int xUnit = betterCopySign(6,nodes[j].x -nodes[i].x);
					int yUnit = betterCopySign(6,nodes[j].y -nodes[i].y);
					
					g.drawLine(nodes[i].x+xUnit,nodes[i].y+yUnit,nodes[j].x-xUnit,nodes[j].y-yUnit);
				}
			}
		}
	}

	public void drawCarsOnRoad (Graphics g, int from, int to, int lane, Road road){
		
		float xUnit = (float)(nodes[to].x -nodes[from].x)/(road.length+20);
		float yUnit = (float)(nodes[to].y -nodes[from].y)/(road.length+20);
		
		int  xNudge=0;
		int yNudge=0;
		
		
		if(xUnit<0 || yUnit>0){
			if(yUnit ==0){
				yNudge = 2*(lane+1)+lane;
			} else{
				xNudge = 2*(lane+1)+lane;
			}
		} else{
			if(yUnit ==0){
				yNudge = -(3*(lane+1)+lane);
			} else{
				xNudge = -(3*(lane+1)+lane);
			}
		}
		
		for (int i =0; i<road.length; i++){
			if (road.traffic[i]>0){
				g.setColor(new Color(0,0,0));
				if (road.traffic[i]==1){
					g.setColor(new Color(255,0,0));
				}
				else if (road.traffic[i]==2){
					g.setColor(new Color(0,0,255));
				}
				g.drawRect((int)(nodes[from].x+(xUnit*(i+10))+xNudge), (int)(nodes[from].y+(yUnit*(i+10))+yNudge), 1, 1);
			}
		}
		return;
	}
	
	public void drawTrafficLights (Graphics g){
		
		g.setColor(new Color(0,0,0));
		
		for (int i=0; i<world.n; i++){
			for (int j=0; j<world.lights[i].connectedNodes.length; j++){
				for (int k=0; k<world.lights[i].connectedNodes.length; k++){
					
					int nodeJ = world.lights[i].connectedNodes[j];
					int nodeK = world.lights[i].connectedNodes[k];
					
					if (j!=k && world.allLights[nodeJ][i][nodeK]){
												 
					    int jxChange = betterCopySign(5,nodes[nodeJ].x -nodes[i].x);
						int jyChange = betterCopySign(5,nodes[nodeJ].y -nodes[i].y);	
						
						int kxChange = betterCopySign(5,nodes[nodeK].x -nodes[i].x);
						int kyChange = betterCopySign(5,nodes[nodeK].y -nodes[i].y);
						
						g.drawLine(nodes[i].x + jxChange, nodes[i].y + jyChange, nodes[i].x + kxChange,nodes[i].y + kyChange);						
						
					}
				}
			}
		}
	}
	
	public void drawAllRoads (Graphics g){
		for (int i=0; i<world.n; i++){
			for (int j=0; j<world.n; j++){
				for (int lane=0; lane<world.roads[i][j].length; lane++){
					if(world.roads[i][j][lane]!=null){
						drawCarsOnRoad(g,i,j,lane,world.roads[i][j][lane]);
					}
				}
			}
		}
	}

	public int betterCopySign(int magnitude, int sign){
		if(sign == 0){
			return 0;
		}
		else return (int)Math.copySign(magnitude, sign);
	}
}
