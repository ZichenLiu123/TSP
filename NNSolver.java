import java.util.ArrayList;

public class NNSolver extends TSPSolver{
	public NNSolver() {
		super();
	}
	
	@Override
	public int[] nextNode(Graph G, ArrayList<Integer> visited) {
	    int[] result = new int[2];
	    int closestCity = -1;

	    closestCity = nearestNeighbor(G,visited,visited.size() - 1);
	    
	    result[0] = closestCity;
	    int insertionPosition = visited.size(); // Position where closestCity will be inserted
	    result[1] = insertionPosition;
	    return result;
	}
	
	@Override
	//When no routes are found
	public void printingError(int i) {
		System.out.printf("ERROR: NN did not find a TSP route for Graph %d!\n", i);
	}
}
