package model;

public class Car extends Agent {

	RealNetwork world;
	
	ImaginedNetwork imagined;
	int initialStart,finalDestination;
	int to, from;
	int location, size, lane, newLane;
	int counter, roadJoiner, laneChanger, upOrDown;
	int stoppedInPlaceCounter;
	

	boolean onRoads,changingRoad,changingLane;
	
	Driver driver;
	int type;
	
	public Car (RealNetwork world, int delay, int size, int from, int to, int type){
		super(delay);
		imagined = new ImaginedNetwork(world);
		
		this.world = world;
		this.finalDestination = to;
		this.from = this.initialStart = from;
		this.to = imagined.getNext(from,to);
		this.size = size;
		onRoads = changingRoad = changingLane= false;
		counter =0;
		
		lane = upOrDown = newLane =0;
		
		this.type = type;
		
		start();
	}
	
	public Car (RealNetwork world, ImaginedNetwork imagined){
		super(1000);
		this.world = world;
		this.imagined = imagined;
	}

	void act() {
		
		if(stoppedInPlaceCounter==8){
			driver = new Driver(world,imagined);
		}
		
		
		if(!onRoads){
			joinNetwork();
			return;
		}
		
		if(changingRoad){
			changeRoad();
			return;
		}
		
		if(changingLane){
			changeLane();
			return;
		}
		
		int output = world.roads[from][to][lane].moveCar(location,size,type);
		
		if(output==0){
			roadJoiner= 0;
			changeRoad();
			stoppedInPlaceCounter=0;
			
		} else if(output == 2){
			upOrDown =1;
			if(safeToChangeLane()){
				changeLane();
			}
			else{
				counter++;
				stoppedInPlaceCounter++;
				return;
			}
			
		} else{
			upOrDown = -1;
			if(safeToChangeLane()){
				changingLane=true;
				laneChanger =0;
			}
			counter++;
			location++;
			stoppedInPlaceCounter=0;
		}

	}

	void joinNetwork(){
		if(!changingRoad){
			location = roadJoiner =0;
			
			if(lane>=world.roads[from][to].length){
				lane= world.roads[from][to].length-1;
			}
			
			if(world.roads[from][to][lane].addCar(0,type)==1){
				changingRoad=true;
				counter++;
				roadJoiner++;
			} else {	
				counter++;
			}
		} else if(roadJoiner<=size){
			if(world.roads[from][to][lane].addCar(roadJoiner,type)==1){
				roadJoiner++;
				return;
			}
			counter++;
		} else {
			changingRoad = false;
			onRoads = true;
			act();
		}
	}

	boolean safeToChangeLane(){
		
		if(lane+upOrDown<0){
			return false;
		}
		
		if(world.roads[from][to].length==lane+upOrDown){
			return false;
		}
		
		if(location+(size*2)+5>world.roads[from][to][lane+upOrDown].length){
			return false;
		}
		
		for (int i=0; i<(size*1.5)+6;i++){
			if (world.roads[from][to][lane+upOrDown].traffic[location+i]>0){
				return false;
			}
		}
		
		return true;
	}
	
	void changeLane(){
		if(!changingLane){
			changingLane = true;
			laneChanger =0;
		}
		
		if(laneChanger<size){
			if(world.roads[from][to][lane+upOrDown].addCar(location+size,type)==1){
				world.roads[from][to][lane].removeCar(location);
				world.roads[from][to][lane].removeCar(location+1);
				location++;
				laneChanger++;
			} else
			counter++;
			
		} else {
			lane = lane+upOrDown;
			changingLane=false;
			act();
		}
	}
	void changeRoad(){		
		if(to == finalDestination){
			reset();
			return;
		}
		
		int next = imagined.getNext(to,finalDestination);
		
		if(!changingRoad){
			if(world.allLights[from][to][next]){
				
				changingRoad = true;
				roadJoiner=0;
				
				if(world.roads[to][next].length>lane){
					newLane = lane;
				}
				else newLane= world.roads[to][next].length-1;
			}
			else{
				counter++;
				return;
			}
		}
		
		if(roadJoiner<size){
			
			if(world.roads[to][next][newLane].addCar(roadJoiner,type)==1){
				world.roads[from][to][lane].removeCarFromEnd(size,roadJoiner);
				roadJoiner++;
			} else
			counter++;
			
		} else {
			
			imagined.edgeWeight[from][to]=counter;
			
			from = to;
			to = imagined.getNext(from, finalDestination);
			
			
			location= counter = 0;
			changingRoad = false;
			lane=newLane;
	
			act();
		}
	}

	void reset(){
		changingRoad = true;
		
		if(roadJoiner<size){
			world.roads[from][to][lane].removeCarFromEnd(size,roadJoiner);
			roadJoiner++;
			counter++;	
		} else{
			onRoads=changingRoad=false;
			
			imagined.edgeWeight[from][to]=counter;
			counter =0;
		    
			for (int i=0; i<imagined.n; i++){
				for (int j=0; j<imagined.n; j++){
					imagined.edgeWeight[i][j]=imagined.edgeWeight[i][j]*world.trafficReport.theReport[i][j];
				}
			}
			
			imagined.calculateShortestPaths();
			
			from = initialStart;
			to = imagined.getNext(from,finalDestination);
		}
	}
}
