package model;

public class TrafficReport extends Agent {
	
	static final int INF = Integer.MAX_VALUE;
	
	int[][] trueEdgeWeights;
	int[][] theReport;
	Road[][][] roads;
	int n;
	
	
	public TrafficReport(int delay, int[][] edgeWeights, Road[][][] roads, int n){
		super(delay);
		this.trueEdgeWeights = edgeWeights;
		this.roads = roads;
		this.n = n;
		theReport = new int[n][n];
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				theReport[i][j]=1;
			}
		}
		
		start();
	}
	
	public void act(){
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				if(trueEdgeWeights[i][j]!=INF){
					int counter =0 ;
					for (int k=0; k<roads[i][j][0].length; k++){
						if(roads[i][j][0].traffic[k]>0){
							counter++;
						}
						//s
					}
					
					float index = (float)counter/(float)trueEdgeWeights[i][j];
					
					if(index>0.85){
						theReport[i][j]=10;
					} else if (index > 0.7){
						theReport[i][j]=7;
					} else if (index > 0.5){
						theReport[i][j]=4;
					} else if (index > 0.4){
						theReport[i][j]=3;
					} else if (index > 0.2){
						theReport[i][j]=2;
					}  else {
						theReport[i][j]=1;
					}
				}
			}
		}
		return;
	}
}
