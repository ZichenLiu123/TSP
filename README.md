# TSP Solver Project
The TSP Solver is an advanced implementation that explores three heuristic algorithms to solve the TSP, a classic optimization problem that seeks the shortest possible route that visits a set of cities exactly once and returns to the original city.

**Features**
Graph-Based Representation: The project uses graph structures to represent cities (nodes) and connections (edges), with travel costs between cities calculated using the Haversine formula.

Heuristic Algorithms: Implements three variations of the heuristic approach to solve the TSP: Nearest Neighbor (NN), Nearest Neighbor First-Last (NN-FL), and Node Insertion (NI).

Algorithm Performance Comparison: Evaluates and compares the algorithms based on three metrics: average solution cost, computation time, and success rate.

Interactive User Interface: Features a menu-driven interface that allows users to load graph data, execute algorithms, view individual results, and compare algorithm performances.

Comprehensive Error Handling: Incorporates detailed error messaging for user guidance during file loading, data input, and algorithm execution processes.

**Structure**
Node and Graph Data Structures: Classes designed to store city information and graph representations, including adjacency and cost matrices.

Algorithm Classes: The TSPSolver abstract class and its subclasses (NNSolver, NNFLSolver, NISolver), each implementing a specific heuristic approach to solving the TSP.

Utility Functions: Includes functions for displaying menus, loading graphs from files, displaying graph summaries and details, running algorithms, and analyzing algorithm performance.
