import java.util.ArrayList;


public class Graph {
	private int n ; // number of nodes
	private int m ; // number of arcs
	private ArrayList < Node > node ; // ArrayList *or* array of nodes
	private boolean [][] A ; // adjacency matrix
	private double [][] C ; // cost matrix
	
	// constructors
	public Graph () {
		this.node = new ArrayList<Node>();
	}
	public Graph ( int n ) {
		this.node = new ArrayList<Node>(n);
	}

	// setter
	public void setN ( int n ) {
		this.n = n;
	}
	public void setM ( int m ) {
		this.m = m;
	}
	public void setArc ( int i , int j , boolean b ) {
		A[i][j] = b;
		A[j][i] = b;
	}
	public void setCost ( int i , int j , double c ) {
		C[i][j] = c;
		C[j][i] = c;
	}
	
	// getters
	public int getN () {
		return this.n;
	}
	public int getM () {
		return this.m;
	}
	public boolean getArc ( int i , int j ) {
		return A[i][j];
		}
	
	public double getCost ( int i , int j ) {
		return C[i][j];
	}
	
	public Node getNode ( int i ) {
		return node.get(i);
	}
	
	//Initialize 
	public void init ( int n ) {
		this.n = n;
		this.m = 0;
		node =  new ArrayList<Node>(n);
		this.A = new boolean[n][n];
		this.C = new double[n][n];
		
		for (int i = 0; i <n; i++) {
			for (int j =0; j <n; j++) {
				A[i][j] = false;
				C[i][j] = 0;
			}
		}
	} 
	
	//clear everything
	public void clear(ArrayList<Graph> G) {
	    // Iterate over each graph and reset it
	    for (Graph graph : G) {
	        graph.reset();
	    }

	    // Now clear the ArrayList
	    G.clear();
	    System.out.println("\nAll graphs cleared.\n");
	}

	//reset nodes, arcs, A and C
	public void reset() {
	    this.n = 0;
	    this.m = 0;
	    this.node.clear();
	    this.A = new boolean[n][n]; // Resetting the adjacency matrix
	    this.C = new double[n][n];  // Resetting the cost matrix

	    // Optionally, you can iterate over the matrices to explicitly set values to false/0.0
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            A[i][j] = false;
	            C[i][j] = 0.0;
	        }
	    }
	}

	public boolean isValidNode(Node node) {
        // Check if node coordinates are within the valid range
        if (existsNode(node) || node.getLat() < -90 || node.getLat() > 90 || node.getLon() < -180 || node.getLon() > 180) {
            return false; // The node has invalid coordinates
        }

        return true; // The node is valid and unique
    }
	
	// check if arc exists
	public boolean existsArc ( int i , int j ) {
		if (A[i][j] || A[j][i]) {
			return true;
		}
		return false;
	} 
	
	// check if node exists
	public boolean existsNode (Node t) {
	    for (Node i : node) {
	        // Check if the names are the same
	        if (i.getName().equals(t.getName())) {
	            return true;
	        }
	        // Check if the coordinates are the same
	        if ((i.getLat() == t.getLat()) && (i.getLon() == t.getLon())) {
	            return true;
	        }
	    }
	    return false;
	}

	public boolean existsGraphs(ArrayList<Graph> G) {
	    for (Graph graph : G) {
	        for (Node node : graph.node) {
	            if (existsNode(node)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	//accounting for initial empty graph error
	public boolean existsGraph(ArrayList<Graph> G) {
		if (G.isEmpty()) {
			System.out.println("\nERROR: No graphs have been loaded!\n");
			return false;
			
		} else {
			return true;
		}
	}
	
	//adding arc
	public boolean addArc ( int i , int j ) {
		if (existsArc(i,j)) {
			return false;
			}
			setArc(i,j,true);
			double distance = Node.distance(getNode(i), getNode(j));
			setCost(i,j,distance);
			this.m++;
			
		
	return true;
	} 
	
	//removing arc
	public void removeArc(int k) {
	    int arcCount = 1;

	    for (int i = 0; i < n; i++) {
	        for (int j = i + 1; j < n; j++) {
	            if (A[i][j]) {
	                if (arcCount == k) {
	                    A[i][j] = false;
	                    A[j][i] = false;
	                    C[i][j] = 0.0;
	                    C[j][i] = 0.0;
	                    System.out.printf("\nArc %d removed!\n\n", k);
	                    m--;
	                }
	                arcCount++;
	            }
	        }
	    }
	}
	
	//adding nodes
	public boolean addNode ( Node t ) {
		this.node.add(t);
		return true;
	} 
	
	// print all graph info
	public void print (){
		System.out.printf("\nNumber of nodes: %d\n", getN());
		System.out.printf("Number of arcs: %d\n", getM());
		printNodes();
		printArcs();
	}
	
	//print nodes
	public void printNodes() {
	    System.out.println("\nNODE LIST");
	    System.out.println("No.               Name        Coordinates");
	    System.out.println("-----------------------------------------");

	    for (int i = 0; i < node.size(); i++) {
	        Node currentNode = getNode(i);
	        String formattedLat = formatCoordinate(currentNode.getLat());
	        String formattedLon = formatCoordinate(currentNode.getLon());
	        String coordinates = "(" + formattedLat + "," + formattedLon + ")";

	        System.out.printf("%3d%19s%19s\n", i + 1, currentNode.getName(), coordinates);
	    }
	    System.out.print("\n");
	}

	public static String formatCoordinate(double value) {
	    // Check if the value has a non-zero value in the second decimal place
	    if (Math.round(value * 100) / 100.0 != Math.round(value * 10) / 10.0) {
	        return String.format("%.2f", value); // Two decimal places
	    } 
	    // Else, return the value with one decimal place
	    else {
	        return String.format("%.1f", value); // One decimal place
	    }
	}

	//printing arcs
	public void printArcs() {
	    String print;
		System.out.println("ARC LIST");
	    System.out.println("No.    Cities       Distance");
	    System.out.println("----------------------------");

	    int arcCount = 1;
	    
	    for (int i = 0; i < n; i++) {
	        for (int j = i + 1; j < n; j++) {
	            if (A[i][j]) {
	                double distance = C[i][j];
	                int city1 = i+1;
	                int city2 = j+1;
	                if (j < i) {
	                	city1 = city2;
	                	city2 = city1;
	                }
	                print = String.format("%s-%s", city1, city2);
	                System.out.printf("%3d%10s%15.2f%n", arcCount, print, distance);
	                arcCount++;
	            }
	        }
	    }
	    System.out.print("\n");
	}
	
	public static void printGraph (ArrayList<Graph> G) {
		System.out.println("\nGRAPH SUMMARY");
	    System.out.println("No.    # nodes    # arcs");
	    System.out.println("------------------------");	
	    
	    for (int i = 0; i < G.size(); i++) {
	          System.out.printf("%3d%11d%10d", i + 1, G.get(i).getN(), G.get(i).getM());
	          System.out.print("\n");
	    }
	}
	
	//check for errors in path, que is to stop the third from printing
	public boolean checkPath(int[] P) {
	    boolean check = true;
	    boolean que = false;
	    
	    if (P[0] != P[this.n]) {
	        System.out.println("\nERROR: Start and end cities must be the same!\n");
	        return false;
	    }
	    
	    boolean[] visited = new boolean[this.n];
	    
	    for (int i = 0; i < this.n; i++) {
	        int currentCity = P[i] - 1; 
	        
	        if (visited[currentCity]) {
	            System.out.println("\nERROR: Cities cannot be visited more than once!");
	            check = false;
	            break;
	        } else {
	            visited[currentCity] = true;
	        }
	    }

	    for (int i = 0; i < this.n; i++) {
	        if (!visited[i]) {
	            System.out.println("ERROR: Not all cities are visited!\n");
	            que = true;
	            check = false;
	            break;
	        }
	    }
	    
	    for (int i = 0; i < this.n; i++) {
	    		if (A[P[i] - 1][P[i + 1] - 1] == false && que == false) {
	    			System.out.printf("\nERROR: Arc %d-%d does not exist!\n\n", P[ i + 1], P[i]);
	    			check = false;
	    			break;
	    	}
	    }
	    
	    return check;
	}
	
	//calculate distance in the path
	public double pathCost(int[] P) {
	    double totalCost = 0.0;

	    for (int i = 0; i < this.n; i++) {
	        int city1 = P[i];
	        int city2 = P[i + 1];
	        
	        double distance = getCost(city1 - 1, city2 - 1);
	        totalCost += distance;
	    }

	    return totalCost;
	}

}