package model;

import java.util.Random;

public class RealNetwork {
	
	int n,numWide,numHigh;
	int[][] edgeWeight;
	int[][] laneNumber;
	int[]start,finish;
	
	boolean generatedNetwork;
	
	static final int INF = Integer.MAX_VALUE;
	static final int NULL = Integer.MAX_VALUE-1;
	
	Road[][][] roads;
	boolean[][][] allLights;
	TrafficLight[] lights;
	TrafficReport trafficReport;
	
	public RealNetwork(){
		
		
		createNetwork(5,5,100);
		
		/*
		edgeWeight[44][55] = 50;
		edgeWeight[55][44] = 50;
		
		laneNumber[44][55] = 1;
		laneNumber[55][44] = 1;
		*/		
		
		/*edgeWeight[15][10] = 140;
		edgeWeight[10][15] = 140;
		edgeWeight[20][15] = 140;
		edgeWeight[15][20] = 140;
		edgeWeight[20][25] = 140;
		edgeWeight[25][20] = 140;
		edgeWeight[30][29] = 620;
		edgeWeight[29][30] = 620;*/
		
		//edgeWeight[44][35] = 160;
		//edgeWeight[35][44] = 160;
		
		//edgeWeight[9][n-9] = 1100;
		//edgeWeight[n-9][9] = 1100;
		
		/*laneNumber[10][11] =3;
		laneNumber[11][12] =3;
		laneNumber[12][13] =3;
		laneNumber[13][14] =3;
		
		laneNumber[11][10] =3;
		laneNumber[12][11] =3;
		laneNumber[13][12] =3;
		laneNumber[14][13] =3;*/
		
		Random rand = new Random();
		
		lights = new TrafficLight[n];
		allLights = new boolean[n][n][n];
		roads = new Road[n][n][];
		
		
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				roads[i][j] = new Road[laneNumber[i][j]];
			}
		}
		
		
		for (int i=0; i<n; i++){
			lights[i]= new TrafficLight(rand.nextInt(1500)+800,this,i);
			for (int j=0; j<n; j++){
				if (edgeWeight[i][j]!=INF && edgeWeight[i][j]!=0){ 
					roads[i][j] = new Road[laneNumber[i][j]];
					for (int lane=0;lane<roads[i][j].length;lane++){
						roads[i][j][lane] = new Road(edgeWeight[i][j]);
					}
					lights[i].addConnectedNode(j);
				}
			}
		}
		
		trafficReport = new TrafficReport(500,edgeWeight,roads, n);
		addCars();
	}

	public void createNetwork(int width,int height, int weight){
		
		this.numWide = width;
		this.numHigh = height;
		
		n=width*height;
		
		edgeWeight = new int[n][n];
		laneNumber = new int[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				edgeWeight[i][j]=INF;
				laneNumber[i][j]=5;
			}
		}
		
		int[][] network = new int[width][height];	
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				network[x][y]= x+(y*width);
			}
		}
		
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				
				try{
					edgeWeight[network[x][y]][network[x-1][y]] = weight;
					edgeWeight[network[x-1][y]][network[x][y]] = weight;
				} catch(Exception ex){}
				
				try{
					edgeWeight[network[x][y]][network[x+1][y]] = weight;
					edgeWeight[network[x+1][y]][network[x][y]] = weight;
				} catch(Exception ex){}
				
				try{
					edgeWeight[network[x][y]][network[x][y-1]] = weight;
					edgeWeight[network[x][y-1]][network[x][y]] = weight;
				} catch(Exception ex){}
		
				try{
					edgeWeight[network[x][y]][network[x][y+1]] = weight;
					edgeWeight[network[x][y+1]][network[x][y]] = weight;
				} catch(Exception ex){}
				
				
			}
		}
		generatedNetwork = true;
		//start = new int[]{network[0][numHigh-1],network[0][numHigh-2],network[0][numHigh-3],network[0][numHigh-1],network[1][numHigh-1],network[2][numHigh-1],network[3][numHigh-1]};
		//finish = new int[]{network[numWide-1][0],network[numWide-1][1],network[numWide-1][2],network[numWide-1][3],network[numWide-2][0],network[numWide-3][0],network[numWide-4][0]};
	
		start = new int[]{network[0][0]};
		finish = new int[]{network[numWide-1][numHigh-1]};
		
	}
	
	public void addCars(){
		
		int redNum = 50;
		int blueNum = 50;
		Random rand = new Random();

		Car[] red = new Car[redNum];
		for (int i=0;i<redNum;i++){
			red[i] = new Car(this,rand.nextInt(100)+10,rand.nextInt(15)+9,start[rand.nextInt(start.length)],finish[rand.nextInt(finish.length)],1);
		}
		
		Car[] green = new Car[blueNum];
		for (int i=0;i<blueNum;i++){
			green[i] = new Car(this,rand.nextInt(80)+10,rand.nextInt(15)+9,finish[rand.nextInt(finish.length)],start[rand.nextInt(start.length)],2);
		}
		
	}
}
