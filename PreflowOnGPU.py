import networkx as nx
import numpy as np
from numba import cuda, int32
#Same as Solver.py to load the graph
# Load the graph from the edgelist file
G = nx.read_edgelist(
    "large_graph.edgelist", 
    create_using=nx.DiGraph, 
    data=[("capacity", int)]
)

#Source kommer vara med lägsta ID i large_graph_edgelist och sink med högst (0 till 999)
source = min(G.nodes, key=int)
sink = max(G.nodes, key=int)

