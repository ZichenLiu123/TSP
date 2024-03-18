import java.util.ArrayList;

public class NISolver extends TSPSolver {

    public NISolver() {
        super();
    }

    @Override
    public int[] nextNode(Graph G, ArrayList<Integer> visited) {

        double minCost = Double.MAX_VALUE;
        int[] nextNodeInfo = new int[2]; // [0] for next node, [1] for insertion position

        // Check nearest neighbor for the first node and insert at the beginning if it's the best option
        int nearestToStart = nearestNeighbor(G, visited, 0);
        if (nearestToStart != -1) {
            double costToStart = G.getCost(visited.get(0), nearestToStart);
            if (costToStart < minCost) {
                minCost = costToStart;
                nextNodeInfo[0] = nearestToStart;
                nextNodeInfo[1] = 0; // Insert at the start
            }
        }

        // Iterate through the rest of the path to find the best insertion point
        for (int i = 1; i < visited.size(); i++) {
            int currentCity = visited.get(i);
            int nearestNeighbor = nearestValidNeighbor(G, visited, currentCity, i+1);

            if (nearestNeighbor != -1) {
                double cost = G.getCost(currentCity, nearestNeighbor);
                if (cost < minCost) {
                    minCost = cost;
                    nextNodeInfo[0] = nearestNeighbor;
                    nextNodeInfo[1] = i + 1; // Insert after the current city
                }
            } 
        }
   
        return (minCost == Double.MAX_VALUE) ? null : nextNodeInfo;
    }

    private int nearestValidNeighbor(Graph G, ArrayList<Integer> visited, int currentCity, int position) {
        int nearestNeighbor = -1;
        double minDistance = Double.MAX_VALUE;

        for (int j = 0; j < G.getN(); j++) {
            if (!visited.contains(j) && canBeInserted(G, visited, position, j)) {
                double distance = G.getCost(currentCity, j);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestNeighbor = j;
                }
            }
        }

        return nearestNeighbor;
    }
    
    private boolean canBeInserted(Graph G, ArrayList<Integer> visited, int position, int newNode) {
        if (position == visited.size()) {
            return G.getArc(visited.get(visited.size() - 1), newNode); // Inserting at the end
        } else {
            return G.getArc(visited.get(position - 1), newNode) && G.getArc(newNode, visited.get(position)); // Inserting in between
        }
    }
    
    @Override
	//When no routes are found
    public void printingError(int i) {
        System.out.printf("ERROR: NI did not find a TSP route for Graph %d!\n", i);
    }
}
