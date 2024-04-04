# TSP Solver Project

The TSP Solver Project is an intricate implementation aimed at tackling the Traveling Salesman Problem (TSP), a renowned optimization challenge that seeks the most efficient route visiting a set of cities exactly once and returning to the origin. This project delves into three heuristic algorithms to find solutions to the TSP.

## Features

- **Graph-Based Representation**: Utilizes graph structures to depict cities as nodes and their connections as edges, employing the Haversine formula to compute the distances between cities.

- **Heuristic Algorithms**: Features three heuristic algorithm implementations for the TSP: Nearest Neighbor (NN), Nearest Neighbor First-Last (NN-FL), and Node Insertion (NI).

- **Algorithm Performance Comparison**: Provides an analytical comparison of the algorithms based on average solution cost, computational time, and the rate of successful solutions.

- **Interactive User Interface**: Offers a user-friendly, menu-driven interface for loading graph data, initiating algorithm runs, viewing specific outcomes, and comparing the performance across algorithms.

- **Comprehensive Error Handling**: Implements detailed error messages to guide users through file loading, data input, and the algorithm execution stages.

## Structure

- **Node and Graph Data Structures**: Defines classes to encapsulate city information and graph representations, including adjacency lists and cost matrices for efficient data management.

- **Algorithm Classes**: Introduces an abstract class `TSPSolver` and its specialized subclasses `NNSolver`, `NNFLSolver`, and `NISolver`, each dedicated to a particular heuristic TSP-solving approach.

- **Utility Functions**: Encompasses a variety of functions to facilitate user interaction, such as graph data loading, graph summaries presentation, algorithm execution, and performance analysis.

This project is a comprehensive exploration of heuristic methods in solving the TSP, designed to offer insights into algorithm efficiency and effectiveness in route optimization tasks.
