package model;

public class Driver extends Thread {

	ImaginedNetwork imagined;
	RealNetwork world;
	
	public Driver (RealNetwork world, ImaginedNetwork imagined){
		this.imagined = imagined;
		this.world = world;
		
		start();
	}
	
	public void run(){
		for (int i=0; i<imagined.n; i++){
			for (int j=0; j<imagined.n; j++){
				imagined.edgeWeight[i][j]=imagined.edgeWeight[i][j]*world.trafficReport.theReport[i][j];
			}
		}
		
		imagined.calculateShortestPaths();
	}
}
