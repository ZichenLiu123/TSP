import java.util.ArrayList;

public class NNFLSolver extends TSPSolver {
    public NNFLSolver() {
        super();
    }

    @Override
    public int[] nextNode(Graph G, ArrayList<Integer> visited) {
        int startNode = visited.get(0);
        int endNode = visited.get(visited.size() - 1);
        int nearestNodeToStart = -1;
        int nearestNodeToEnd = -1;
        double minDistanceToStart = Double.MAX_VALUE;
        double minDistanceToEnd = Double.MAX_VALUE;

        for (int i = 0; i < G.getN(); i++) {
            if (!visited.contains(i)) {
                if (G.getArc(startNode, i)) {
                    double distanceToStart = G.getCost(startNode, i);
                    if (distanceToStart < minDistanceToStart) {
                        minDistanceToStart = distanceToStart;
                        nearestNodeToStart = i;
                    }
                }
                if (G.getArc(endNode, i)) {
                    double distanceToEnd = G.getCost(endNode, i);
                    if (distanceToEnd < minDistanceToEnd) {
                        minDistanceToEnd = distanceToEnd;
                        nearestNodeToEnd = i;
                    }
                }
            }
        }

        // Determine whether to insert at the start or the end based on the shorter distance
        if (minDistanceToStart < minDistanceToEnd) {
            return new int[] {nearestNodeToStart, 0}; // Insert at start
        } else if (minDistanceToEnd < Double.MAX_VALUE) {
            return new int[] {nearestNodeToEnd, visited.size()}; // Insert at end
        }

        return null; // No viable node found
    }
    
    @Override
	//When no routes are found
    public void printingError(int i) {
		System.out.printf("ERROR: NN-FL did not find a TSP route for Graph %d!\n", i);
	}
}
