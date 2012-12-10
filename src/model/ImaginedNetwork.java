package model;

public class ImaginedNetwork {
	
	int n;
	int[][] edgeWeight,sPath,next;
	
	static final int INF = Integer.MAX_VALUE;
	static final int NULL = Integer.MAX_VALUE-1;
	
	public ImaginedNetwork(RealNetwork real){
		
		this.n = real.n;
		
		edgeWeight = new int[n][n];
		sPath = new int[n][n];
		next = new int[n][n];
		
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				if(real.edgeWeight[i][j]!=INF){
					edgeWeight[i][j] = (int)(real.edgeWeight[i][j]*1.2);
				} else{
					edgeWeight[i][j] = real.edgeWeight[i][j];
				}
			}
		}
		
		calculateShortestPaths();
	}
	
	public void calculateShortestPaths(){
			
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				sPath[i][j] = edgeWeight[i][j];
				next[i][j] = NULL;
			}
		}
		
		for (int k=0;k<n;k++){
			for (int i=0;i<n;i++){
				for (int j=0;j<n;j++){
					if(sPath[i][k] != INF 
							&& sPath[k][j] != INF 
							&& sPath[i][k] + sPath[k][j] < sPath[i][j]){
						sPath[i][j] = sPath[i][k] + sPath[k][j];
						next[i][j] = k;
					}
				}
			}
		}
		
	}

	public int getNext(int i, int j){
		int intermediate = next[i][j];
		if (intermediate == NULL){
			return j;
		}
		else if(edgeWeight[i][intermediate] == INF){
			return getNext(i,intermediate);
		}
		else return intermediate;
	}
}
