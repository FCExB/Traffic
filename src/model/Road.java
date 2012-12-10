package model;

public class Road {
	
	int length;
	int[] traffic;
	
	public Road(int length){
		this.length = length;
		
		traffic = new int[length];
		for (int i=0; i<length; i++){
			traffic[i] = 0;

		}
	}
	
	public int moveCar (int location,int size, int type){
		if(location + size == length){
			return 0;
		}
		else if(location + size +1 == length ){
			if(traffic[location+size]>0){
				 return 2;
			 }
			 else {
				 traffic[location] = 0;
				 traffic[location +size] = type;
				 return 1;
			 }
		}
		else if(location + size +2 == length ){
			if(traffic[location+size]>0||traffic[location+size+1]>0){
				 return 2;
			 }
			 else {
				 traffic[location] = 0;
				 traffic[location +size] = type;
				 return 1;
			 }
		}
		else if(location + size +3 == length ){
			if(traffic[location+size]>0||traffic[location+size+1]>0||traffic[location+size+2]>0){
				 return 2;
			 }
			 else {
				 traffic[location] = 0;
				 traffic[location +size] = type;
				 return 1;
			 }
		}
		 
		
		
		 if(traffic[location+size+3]>0){
			 return 2;
		 }
		 else {
			 traffic[location] = 0;
			 traffic[location +size] = type;
			 return 1;
		 }
	}

	public int addCar(int location, int type){
		
		for (int i =location; i<location+3; i++){
			if(traffic[i]>0){
				return 0;
			}
		}
		traffic[location]= type;
		return 1;
	}
	
	public int removeCar(int location){
		traffic[location] = 0;
		return 1;
	}
	
	public int removeCarFromEnd(int size, int roadJoiner){
		traffic[length-(size-roadJoiner)] = 0;
		return 1;
	}
}
