import networkx as nx
import random

# Create a directed graph with 1000 nodes
G = nx.gnp_random_graph(1000, 0.01, directed=True)

# Add capacities to the edges
for u, v in G.edges():
    G[u][v]['capacity'] = random.randint(1, 100)

# Export the graph to use in other programs
nx.write_edgelist(G, "large_graph.edgelist", data=["capacity"])

