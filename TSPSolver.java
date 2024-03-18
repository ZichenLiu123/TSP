import java.util.ArrayList;

public class TSPSolver {
    private ArrayList<int[]> solnPath; // ArrayList of solution paths
    private double[] solnCost; // Array of solution costs
    private double[] compTime; // Array of computation times
    private boolean[] solnFound; // Array of T/F solutions found
    private boolean resultsExist; // whether or not results exist

    // Constructors
    public TSPSolver() {
        this.solnPath = new ArrayList<int[]>();
        this.solnCost = new double[0];
        this.compTime = new double[0];
        this.solnFound = new boolean[0];
        this.resultsExist = false;
    }

    public TSPSolver(ArrayList<Graph> G) {
        int size = G.size();
        this.solnPath = new ArrayList<int[]>(size);
        this.solnCost = new double[size];
        this.compTime = new double[size];
        this.solnFound = new boolean[size];
        this.resultsExist = false;
        for (int i = 0; i < size; i++) {
            this.solnPath.add(null);
        }
    }

    // Getters
    public int[] getSolnPath(int i) {
        return this.solnPath.get(i);
    }

    public double getSolnCost(int i) {
        return this.solnCost[i];
    }

    public double getCompTime(int i) {
        return this.compTime[i];
    }

    public boolean getSolnFound(int i) {
        return this.solnFound[i];
    }

    public boolean hasResults() {
        return this.resultsExist;
    }

    // Setters
    public void setSolnPath(int i, int[] path) {
        this.solnPath.set(i, path);
    }

    public void setSolnCost(int i, double cost) {
        this.solnCost[i] = cost;
    }

    public void setCompTime(int i, double time) {
        this.compTime[i] = time;
    }

    public void setSolnFound(int i, boolean found) {
        this.solnFound[i] = found;
    }

    public void setHasResults(boolean hasResults) {
        this.resultsExist = hasResults;
    }

    // Initialize variables and arrays based on provided graphs
    public void init(ArrayList<Graph> G) {
        int size = G.size();
        this.solnPath = new ArrayList<int[]>(size);
        this.solnCost = new double[size];
        this.compTime = new double[size];
        this.solnFound = new boolean[size];
        this.resultsExist = false;
        for (int i = 0; i < size; i++) {
            this.solnPath.add(null);
        }
    }

    // Reset variables and arrays
    public void reset() {
        this.solnPath.clear();
        this.solnCost = new double[0];
        this.compTime = new double[0];
        this.solnFound = new boolean[0];
        this.resultsExist = false;
    }
    
    //Running the algorithms
    public void run(ArrayList<Graph> G, int i, boolean suppressOutput) {
        Graph graph = G.get(i);
        int numCities = graph.getN();
        ArrayList<Integer> visited = new ArrayList<>();
        visited.add(0); // Start from the first city (assumed to be index 0)

        long startTime = System.currentTimeMillis();

        while (visited.size() < numCities) {
            int[] nextCityInfo = nextNode(graph, visited);
            if (nextCityInfo == null || nextCityInfo[0] == -1) {
                this.solnFound[i] = false;
                if (!suppressOutput) {
                    printingError(i+1);
                }
                return;
            }

            // Insert the next city at the specified position
            visited.add(nextCityInfo[1], nextCityInfo[0]);
        }

        // Calculate the total cost of the path
        double totalCost = calculateTotalPathCost(graph, visited);

        // Complete the cycle by adding the cost to return to the starting city
        if (graph.getArc(visited.get(visited.size() - 1), visited.get(0))) {
            totalCost += graph.getCost(visited.get(visited.size() - 1), visited.get(0));
            visited.add(visited.get(0)); // Add starting city to the end to complete the cycle
        } else {
            this.solnFound[i] = false;
            if (!suppressOutput) {
                printingError(i+1);
            }
            return;
        }

        long endTime = System.currentTimeMillis();
        
        // Store the solution
        this.solnPath.set(i, visited.stream().mapToInt(Integer::intValue).toArray());
        this.solnCost[i] = totalCost;       
        this.compTime[i] = (endTime - startTime);
        this.solnFound[i] = true;
        this.resultsExist = true;
    }
    
    //calculating the cost of the path after the path has been set
    public double calculateTotalPathCost(Graph G, ArrayList<Integer> path) {
        double totalCost = 0.0;
        for (int j = 0; j < path.size() - 1; j++) {
            totalCost += G.getCost(path.get(j), path.get(j + 1));
        }
        return totalCost;
    }
    
    //this one never prints because its overridden
    public void printingError(int graphNumber) {
        System.out.printf("ERROR: No TSP route found for Graph %d!\n", graphNumber);
    }
    
    //never runs because it gets overriden
    public int[] nextNode(Graph G, ArrayList <Integer> visited) {
    	return null;
    }
    
    //Find the closest neighbor city
    public int nearestNeighbor(Graph G, ArrayList<Integer> visited, int k) {
        int closestCity = -1;
        double minDistance = Double.MAX_VALUE;
        int currentCity = visited.get(k);
        
        for (int j = 0; j < G.getN(); j++) {
            if (!visited.contains(j) && G.getArc(currentCity, j)) {
                double distance = G.getCost(currentCity, j);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCity = j;
                }
            }
        }
        return closestCity;
    }
    
    //calculate the success rate
    public double successRate() {
        int successfulSolutions = 0;
        for (boolean found : this.solnFound) {
            if (found) successfulSolutions++;
        }
        return (double) successfulSolutions / this.solnPath.size();
    }
    
    //calculate average cost of successful routes
    public double avgCost (){
    	int successfulSolutions = 0;
    	double totalCost = 0.0;
		
    	for (int i = 0; i < this.solnPath.size(); i++) {
             if (this.solnFound[i]) {
                 successfulSolutions++;
                 double cost = this.solnCost[i];
                 totalCost += cost;
             }
         }

         double averageCost = successfulSolutions > 0 ? totalCost / successfulSolutions : 0;
         return averageCost;
    }
    
    // calculate average comp time of successful routes
    public double avgTime () {
    	int successfulSolutions = 0;
    	double totalTime = 0.0;
		
    	for (int i = 0; i < this.solnPath.size(); i++) {
             if (this.solnFound[i]) {
                 successfulSolutions++;
                 double time = this.compTime[i];
                 totalTime += time;
             }
         }
         double averageTime = successfulSolutions > 0 ? totalTime / successfulSolutions : 0;
         return averageTime;
    }
    
    //Printing each row of the result
    public void printSingleResult(int i, boolean rowOnly) {
        if (!rowOnly) {
            // Building the path string
            StringBuilder pathStr = new StringBuilder();
            int[] path = this.solnPath.get(i);
            for (int j = 0; j < path.length; j++) {
                pathStr.append(path[j] + 1); 
                if (j < path.length - 1) {
                    pathStr.append("-");
                }
            }

            System.out.printf("%3d%17.2f%19.3f   %s", i + 1, this.solnCost[i], this.compTime[i], pathStr.toString());
        } else {
            System.out.printf("%3d                -                  -   -", i + 1);
        }
    }
    
    //print all the results
    public void printAll() {
    	System.out.println("\n-----------------------------------------------");
    	System.out.println("No.        Cost (km)     Comp time (ms)   Route   ");
    	System.out.println("-----------------------------------------------");
        
    	for (int i = 0; i < this.solnPath.size(); i++) {
        	if (getSolnFound(i)) {
        		this.printSingleResult(i, false);
        	} else {
            this.printSingleResult(i, true);
        	}
            System.out.println();
        }
    }
    
    //print the stats
    public void printStats() {
        int successfulSolutions = 0;
        double minCost = Double.MAX_VALUE;
        double maxCost = Double.MIN_VALUE;
        double minTime = Double.MAX_VALUE;
        double maxTime = Double.MIN_VALUE;

        for (int i = 0; i < this.solnPath.size(); i++) {
            if (this.solnFound[i]) {
                successfulSolutions++;
                double cost = this.solnCost[i];
                double time = this.compTime[i];
                if (cost < minCost) minCost = cost;
                if (cost > maxCost) maxCost = cost;
                if (time < minTime) minTime = time;
                if (time > maxTime) maxTime = time;
            }
        }

        double averageCost = avgCost();
        double averageTime = avgTime();
        double successRate = (double) successfulSolutions / this.solnPath.size();

        double stDevCost = calculateStandardDeviation(this.solnCost, averageCost, successfulSolutions);
        double stDevTime = calculateStandardDeviation(this.compTime, averageTime, successfulSolutions);

        System.out.println("---------------------------------------");
        System.out.printf("           Cost (km)     Comp time (ms)%n");
        System.out.println("---------------------------------------");
        System.out.printf("Average%13.2f%19.3f\n", averageCost, averageTime);
        System.out.printf("St Dev%14s%19s\n", (successfulSolutions > 1 ? String.format("%.2f", stDevCost) : "NaN"),(successfulSolutions > 1 ? String.format("%.3f", stDevTime) : "NaN"));
        System.out.printf("Min%17.2f%19.3f\n", (successfulSolutions > 0 ? minCost : 0), (successfulSolutions > 0 ? minTime : 0));
        System.out.printf("Max%17.2f%19.3f\n", (successfulSolutions > 0 ? maxCost : 0), (successfulSolutions > 0 ? maxTime : 0));
        System.out.printf("%nSuccess rate: %.1f%%%n\n", successRate * 100);
    }

    //calculating the standard deviation
    private double calculateStandardDeviation(double[] values, double average, int count) {
        if (count <= 1) {
            return Double.NaN;
        }
        double sumSquareDiffs = 0.0;
        int validValuesCount = 0;
        for (int i = 0; i < values.length; i++) {
            if (this.solnFound[i]) {
                sumSquareDiffs += Math.pow(values[i] - average, 2);
                validValuesCount++;
            }
        }
        return Math.sqrt(sumSquareDiffs / (validValuesCount - 1));
    }

}
