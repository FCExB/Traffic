package model;

import java.util.Random;


public class TrafficLight extends Agent {
	
		RealNetwork world;
		int location, counter;
		int[] connectedNodes, waitingNodes;
		
	TrafficLight(int delay, RealNetwork world, int location){
		super(delay);
		this.world = world;
		this.location = location;
		counter = 0;
		connectedNodes = new int[0];
		
		start();
	}
	
	public void addConnectedNode(int add){
		int[] temp = new int[connectedNodes.length+1];
		for (int i=0; i<connectedNodes.length; i++){
			temp[i]=connectedNodes[i];
		}
		temp[connectedNodes.length] = add;
		connectedNodes = temp;
	}

	
	void act(){
		
		if(connectedNodes.length==2){
			world.allLights[connectedNodes[0]][location][connectedNodes[1]] = true;
			world.allLights[connectedNodes[1]][location][connectedNodes[0]] = true;
			running = false;
			return;
		} 
		
		for (int i=0; i<connectedNodes.length; i++)
		{
			for (int j=0; j<connectedNodes.length; j++)
			{
				if (i!=j){
					world.allLights[connectedNodes[i]][location][connectedNodes[j]] = false;
				}
				
			}
		}
		
		try {
			Thread.sleep(delay/4);
		} catch (InterruptedException e) {
		}
			
		waitingNodes = new int[0];
		for (int i=0; i<connectedNodes.length; i++)
		{
			for(int lane =0; lane<world.roads[connectedNodes[i]][location].length;lane++){
				if(world.roads[connectedNodes[i]][location][lane].traffic [world.roads[connectedNodes[i]][location][lane].traffic.length-1]>0){	
					addWaitingNode(i);
				}
			}
		}
		if(waitingNodes.length!=0){
			Random rand = new Random();
			counter = waitingNodes[rand.nextInt(waitingNodes.length)];
		}
		
		for (int i=0; i<connectedNodes.length; i++)
		{
			if(i!=counter){
				world.allLights[connectedNodes[counter]][location][connectedNodes[i]] = true;
			}
		}
		counter++;
		
		if(counter==connectedNodes.length){
			counter=0;
		}
	}

	void addWaitingNode(int add){
		int[] temp = new int[waitingNodes.length+1];
		for (int i=0; i<waitingNodes.length; i++){
			temp[i]=waitingNodes[i];
		}
		temp[waitingNodes.length] = add;
		waitingNodes = temp;
	}
}
