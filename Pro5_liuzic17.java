import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Pro5_liuzic17 {

    public static BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    static String CHOICE = "";

    public static  void main(String[] args) throws IOException {
        Graph graph = new Graph();
        Boolean que = true;
        ArrayList<Graph> G = new ArrayList<Graph>();
        NNSolver NNSolver = new NNSolver();
        NNFLSolver NNFLSolver = new NNFLSolver();
        NISolver NISolver = new NISolver();
        Boolean display = false;
       
        do {
            do {
                displayMenu();
                System.out.printf("Enter choice: ");
            } while (menu() == false);

            if (CHOICE.equalsIgnoreCase("L")) {
                que = loadFile(G);
                System.out.print("\n");
                display = que;

            } else if (CHOICE.equalsIgnoreCase("I") && graph.existsGraph(G)) {
                displayGraphs(G);

            } else if (CHOICE.equalsIgnoreCase("C") && graph.existsGraph(G)) {
                graph.clear(G);
                display = false;
            } else if (CHOICE.equalsIgnoreCase("R") && graph.existsGraph(G)) {
                runAll(G, NNSolver, NNFLSolver, NISolver);

            } else if (CHOICE.equalsIgnoreCase("D")) {
            	
            	if (G.size() > 0 && display) { 
            		printAll(NNSolver,NNFLSolver,NISolver);
            		
            	} else {
            		System.out.println("\nERROR: Results do not exist for all algorithms!\n");
            	}
                
            } else if (CHOICE.equalsIgnoreCase("X")) {
            	if (G.size() > 0 && display) { 
            		compare(NNSolver,NNFLSolver,NISolver);
            		
            	} else {
            		System.out.println("\nERROR: Results do not exist for all algorithms!\n");
            	}
            	
            } else if (CHOICE.equalsIgnoreCase("Q")) {
            	System.out.print("\nCiao!\n");
                return;
            }
        } while (que);
    }

    // Display the menu.
    public static void displayMenu() {
        System.out.println("   JAVA TRAVELING SALESMAN PROBLEM V3");
        System.out.println("L - Load graphs from file\nI - Display graph info\nC - Clear all graphs\nR - Run all algorithms\nD - Display algorithm performance\nX - Compare average algorithm performance\nQ - Quit\n");
    }
    
    // Check for proper input for menu selection
    public static Boolean menu() {
        try {
            String input = READER.readLine();
            if (input.equalsIgnoreCase("L")||input.equalsIgnoreCase("I") || input.equalsIgnoreCase("C")
                    || input.equalsIgnoreCase("R") || input.equalsIgnoreCase("D") || input.equalsIgnoreCase("X") || input.equalsIgnoreCase("Q")) {
                CHOICE = input;
                return true;
            } else {
                System.out.println("\nERROR: Invalid menu choice!\n");
                return false;
            }

        } catch (IOException e) {
            System.out.println("\nERROR: Invalid menu choice\n");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("\nERROR: Invalid menu choice\n");
            return false;
        }
    }
    
    public static boolean loadFile(ArrayList<Graph> G) throws IOException {
        String input;
        BufferedReader fin = null;
        int numGraphs = 0;
        String line;
        Graph currentGraph = new Graph();
        int numNode = 0;
        boolean graphLoad = true;
        int success = 0;
        
        try {
            System.out.print("\nEnter file name (0 to cancel): ");
            input = READER.readLine();

            if (input.equals("0")) {
                System.out.println("\nFile loading process canceled");
            	return true;
            }

            File file = new File(input);
            if (!file.exists()) {
                System.out.println("\nERROR: File not found!");
                return true;
            }

            fin = new BufferedReader(new FileReader(file));

            do {
            	line = fin.readLine () ;
            	
            	if ( line != null ) {
            		numNode = Integer.parseInt(line);
            		currentGraph.init(numNode);
            		numGraphs++;
            		
            	for (int i = 0; i < numNode; i++) {
            		line = fin.readLine () ;
            		String [] splitString = line . split (",") ;
            		String name = splitString[0];
                    double lat = Double.parseDouble(splitString[1]);
                    double lon = Double.parseDouble(splitString[2]);
                    Node currentNode = new Node(name, lat, lon);
                    
                    if (currentGraph.isValidNode(currentNode)) {
                        currentGraph.addNode(currentNode);
                    } else {
                        graphLoad = false;
                    }
            	}
         
            	for (int i = 0; i < numNode - 1; i++) {
            	    line = fin.readLine();
            	    String[] splitString = line.split(",");

            	    for (String neighborIndexStr : splitString) {
            	        int neighborNodeIndex = Integer.parseInt(neighborIndexStr.trim()) - 1;

            	        if (graphLoad) {
            	            currentGraph.addArc(i, neighborNodeIndex);
            	        }
            	    }
            	}

            	//space would be expected here
            		line = fin.readLine () ;
                    if (graphLoad) {
                        G.add(currentGraph);
                        success ++;
                    }
                    currentGraph = new Graph();
                    graphLoad = true;
            		}
            	
          } while(line != null);
            fin.close () ;
            
            } catch (IOException | NumberFormatException e) {
               System.out.println("An error occurred while reading the file: " + e.getMessage());
               return false;
           }
        System.out.printf("\n%d of %d graphs loaded!\n", success,numGraphs );
        return true;
        }
    
   public static void displayGraphs(ArrayList<Graph> G) {
	   	int choice;
	   Graph.printGraph(G);
	   
	    do {
		    choice = getInteger("\nEnter graph to see details (0 to quit): ", 0, G.size());
		    if (choice != 0) {
		    G.get(choice - 1).print();
		    Graph.printGraph(G);
		    } else {
		    	System.out.print("\n");
		    }
	    } while(choice != 0);
	    
   }
   
   public static void resetAll(NNSolver NN, NNFLSolver FL, NISolver NI) {
	   NN.reset();
	   FL.reset();
	   NI.reset();
   }
   
   //Running all Algorithms
   public static void runAll(ArrayList<Graph> G, NNSolver NN, NNFLSolver FL, NISolver NI) {
	   NN.init(G);
	   FL.init(G);
	   NI.init(G);
	  
	   boolean noOut = false;
	   System.out.print("\n");
	 
	   // Run NN algorithm on all graphs
	    for (int i = 0; i < G.size(); i++) {
	        NN.run(G, i, noOut );
	    }
	    System.out.println("Nearest neighbor algorithm done.\n");
	    
	    // Run NN-FL algorithm on all graphs
	    for (int i = 0; i < G.size(); i++) {
	        FL.run(G, i, noOut);
	    }
	    System.out.println("Nearest neighbor first-last algorithm done.\n");

	    // Run NI algorithm on all graphs
	    for (int i = 0; i < G.size(); i++) {
	        NI.run(G, i, noOut);
	    }
	    System.out.println("Node insertion algorithm done.\n");
   }
   
   // Print all the graphs
   public static void printAll(NNSolver NN, NNFLSolver FL, NISolver NI) {
	   System.out.print("\nDetailed results for nearest neighbor:");
		NN.printAll();
		System.out.println("\nStatistical summary for nearest neighbor:");
		NN.printStats();
		
		System.out.print("\nDetailed results for nearest neighbor first-last:");
		FL.printAll();
		System.out.println("\nStatistical summary for nearest neighbor first-last:");
		FL.printStats();
		
		System.out.print("\nDetailed results for node insertion:");
		NI.printAll();
		System.out.println("\nStatistical summary for node insertion:");
		NI.printStats();	
   }
   
   //compare the results and print the winner (only if all 3 win)
   public static void compare(NNSolver NN, NNFLSolver FL, NISolver NI) {
	    // Extracting data for NN
	    double nnCost = NN.avgCost();
	    double nnTime = NN.avgTime();
	    double nnSuccessRate = NN.successRate() * 100;

	    // Extracting data for NN-FL
	    double nnflCost = FL.avgCost();
	    double nnflTime = FL.avgTime();
	    double nnflSuccessRate = FL.successRate() * 100;

	    // Extracting data for NI
	    double niCost = NI.avgCost();
	    double niTime = NI.avgTime();
	    double niSuccessRate = NI.successRate() * 100;

	    // Determining the winners in each category with tie-breaking
	    String costWinner = determineWinner(nnCost, nnflCost, niCost, "NN", "NN-FL", "NI");
	    String timeWinner = determineWinner(nnTime, nnflTime, niTime, "NN", "NN-FL", "NI");
	    String successRateWinner = determineWinner(niSuccessRate, nnflSuccessRate, nnSuccessRate, "NI", "NN-FL", "NN"); // For success rate, higher is better

	    // Overall winner is the one who wins the most categories
	    String overallWinner = determineOverallWinner(costWinner, timeWinner, successRateWinner);

	    // Printing the comparison
	    System.out.println("\n------------------------------------------------------------");
	    System.out.println("           Cost (km)     Comp time (ms)     Success rate (%)");
	    System.out.println("------------------------------------------------------------");
	    System.out.printf("NN%18.2f%19.3f%21.1f\n", nnCost, nnTime, nnSuccessRate);
	    System.out.printf("NN-FL%15.2f%19.3f%21.1f\n", nnflCost, nnflTime, nnflSuccessRate);
	    System.out.printf("NI%18.2f%19.3f%21.1f\n", niCost, niTime, niSuccessRate);
	    System.out.println("------------------------------------------------------------");
	    System.out.printf("Winner%14s%19s%21s\n", costWinner, timeWinner, successRateWinner);
	    System.out.println("------------------------------------------------------------");
	    System.out.printf("Overall winner: %s\n\n", overallWinner);
	}
   
   //Determine who wins each category
	private static String determineWinner(double nnMetric, double nnflMetric, double niMetric, String nnName, String nnflName, String niName) {
	    if (nnMetric < nnflMetric && nnMetric < niMetric) {
	        return nnName;
	    } else if (nnflMetric < niMetric) {
	        return nnflName;
	    } else if (nnMetric == nnflMetric) {
	        return nnName; // Tie-breaker
	    } else {
	        return niName;
	    }
	}
	
	//Determine the final winner, if there is one
	private static String determineOverallWinner(String... winners) {
	    int nnWins = 0, nnflWins = 0, niWins = 0;
	    for (String winner : winners) {
	        if (winner.equals("NN")) {
	            nnWins++;
	        } else if (winner.equals("NN-FL")) {
	            nnflWins++;
	        } else if (winner.equals("NI")) {
	            niWins++;
	        }
	    }
	    if (nnWins == 3) {
	    	return "NN";
	    }
	    else if (nnflWins == 3) {
	    	return "NN-FL";
	    }
	    else if (niWins == 3) {
	    	return "NI";
	    } else {
	        return "Unclear";
	    } 
	}

   
    // Get an integer in the range [LB, UB] from the user. Prompt the user repeatedly until a valid value is entered.
    public static int getInteger(String prompt, int LB, int UB) {
        boolean que = false;
        int choice = 0;

        do {
            try {
                System.out.print(prompt);

                double input = Double.parseDouble(READER.readLine());

                if (input < LB || input > UB || input % 1 != 0) {
                    System.out.printf("\nERROR: Input must be an integer in [%d, %d]!\n", LB, UB);
                    que = true;
                } else {
                    choice = (int) input;
                    que = false;
                }
            } catch (IOException e) {
                System.out.println("ERROR: An input/output error occurred.");
            } catch (NumberFormatException e) {
                System.out.printf("\nERROR: Input must be an integer in [%d, %d]!\n", LB, UB);
                que = true;
            }
        } while (que == true);

        return choice;
    }
   
    // Get a real number in the range [LB, UB] from the user. Prompt the user repeatedly until a valid value is entered.
    public static double getDouble(String prompt, double LB, double UB) {
        boolean que = false;
        double choice = 0;

        do {
            try {
                System.out.format(prompt);
                Double input = Double.parseDouble(READER.readLine());

                if (input < LB || input > UB) {
                    System.out.printf("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
                    que = true;
                } else {
                    choice = input;
                    que = false;
                }
            } catch (IOException e) {
                System.out.println("ERROR: An input/output error occurred.\n\n");
            } catch (NumberFormatException e) {
                System.out.printf("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
                que = true;
            }
        } while (que == true);

        return choice;
    }
    
    public static String getString1(String Prompt) {
        String input = null;

        try {
            System.out.format(Prompt);
            input = READER.readLine();
        } catch (IOException e) {
            System.out.println("ERROR: Invalid menu choice!\n");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid menu choice!\n");
        }
        return input;
    }
    
}

                   
