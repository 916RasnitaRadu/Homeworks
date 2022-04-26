import random
from copy import deepcopy
from PracticWork1.src.Exceptions.exception import *


class Graph:
    def __init__(self, vertices, edges):
        """
        verices - iterable of vertices [1,2,3,4,5,6]
        edges - iterable of tuples
        [(1,2, 5), (6,4, -3), (3,1, 18), (2,2, 6), (5,3,4)]
        """
        self._outbound = dict()  # a dictionary that stores the outbound neighbours for a vertex
        self._inbound = dict()  # a dictionary that stores the inbound neighbours for a vertex
        self._costs = dict()  # a dictionary that stores the costs for an edge

        for x in vertices:
            self._outbound[x] = []
            self._inbound[x] = []

        for x, y, z in edges:
            self._outbound[x].append(y)
            self._inbound[y].append(x)
            self._costs[(x, y)] = z

    def is_edge(self, x, y):
        # a function that verifies if there is an edge between x and y (x,y - vertices)
        if x in self._outbound:
            if y in self._outbound[x]:
                return True
        return False

    @property
    def outbound(self):
        return self._outbound

    @property
    def inbound(self):
        return self._inbound

    @property
    def number_vertices(self):
        # a function that returns the number of vertices
        return len(self._outbound)

    @property
    def number_of_edges(self):
        # a function that returns the number of edges
        return len(self._costs)

    @property
    def costs(self):
        # a function that returns the dictionary of costs
        return self._costs

    def parse_vertices(self):
        # a function that returns an iterable of the vertices
        if len(self._outbound) == 0:
            raise GraphException("There aren't any vertices.")
        return [x for x in self._outbound.keys()]

    def find_edge(self, x, y):
        # a function that verifies if there is an edge between vertices x and y and returns the cost of it if yes or raises an exception otherwise
        if self.is_edge(x, y) is False:
            raise GraphException("There is no edge between these vertices.")
        return self._costs[(x, y)]

    def parse_outbound(self, x):
        # a function that returns an iterable of the outbound neighbours of vertex x or None if x is not a vertex
        if x not in self.parse_vertices():
            raise GraphException("The given value is not a vertex.")
        return [y for y in self._outbound[x]]

    def parse_inbound(self, x):
        # a function that returns an iterable of the inbound neighbours of vertex x or None if x is not a vertex
        if x not in self.parse_vertices():
            raise GraphException("The given value is not a vertex.")
        return [y for y in self._inbound[x]]

    def parse_outbound_with_costs(self, x):
        # a function that returns an iterable of the edge costs of the outbound neighbours of vertex x or None if x is not a vertex
        if x not in self.parse_vertices():
            raise GraphException("The given value is not a vertex.")
        return [self._costs[(x, y)] for y in self._outbound[x]]

    def parse_inbound_with_costs(self, x):
        # a function that returns an iterable of the edge costs of the inbound neighbours of vertex x or None if x is not a vertex
        if x not in self.parse_vertices():
            raise GraphException("The given value is not a vertex.")
        return [self._costs[(y, x)] for y in self._inbound[x]]

    def get_degree_out(self, x):
        # a function that returns the outbound degree of the vertex x if this one exists
        if x not in self._outbound:
            raise GraphException("The given value is not a vertex.")
        return len(self._outbound[x])

    def get_degree_in(self, x):
        # a function that returns the inbound degree of the vertex x if this one exists
        if x not in self._inbound:
            raise GraphException("The given value is not a vertex.")
        return len(self._inbound[x])

    def add_vertex(self, x):
        # a function that adds a vertex to the graph
        if x in self.parse_vertices():
            raise GraphException("The given value is a vertex.")
        self._outbound[x] = []
        self._inbound[x] = []

    def remove_vertex(self, x):
        # a function that removes a vertex from the graph
        if x not in self.parse_vertices():
            raise GraphException("The given value is not a vertex.")

        for y in self.parse_vertices():
            if x in self.parse_outbound(y):
                del self._costs[(y, x)]
                self._outbound[y].remove(x)
            if x in self.parse_inbound(y):
                del self._costs[(x, y)]
                self._inbound[y].remove(x)
        self._inbound.pop(x)
        self._outbound.pop(x)

    def add_edge(self, x, y, cost):
        # a function that adds an edge to the graph where x and y are the vertices
        if self.is_edge(x, y):
            raise GraphException("There already is an edge between these vertices.")
        self._outbound[x].append(y)
        self._inbound[y].append(x)
        self._costs[(x, y)] = cost

    def remove_edge(self, x, y):
        # a function that removes an edge from the graph if it exists (where x and y are vertices)
        if self.is_edge(x, y) is False:
            raise GraphException("There is no edge between these vertices.")
        del self._costs[(x, y)]

        self._outbound[x].remove(y)
        self._inbound[y].remove(x)

    def make_copy(self):
        # a function that returns a copy of the graph
        return deepcopy(self)

    def modify_cost(self, x, y, cost):
        # a function that modifies the cost of an edge if it exists
        if self.is_edge(x, y) is False:
            raise GraphException("There is no edge between these vertices.")
        self._costs[(x, y)] = cost


def build_random_graph(number_of_vertices, number_of_edges):
    # A function that receives 2 integers (number_of_vertices and number_of_edges) and generates and returns a random graph.
    if number_of_edges > number_of_vertices * number_of_vertices:
        return False
    edge_set = set()
    while len(edge_set) < number_of_edges:
        x = random.randrange(number_of_vertices) - 1
        y = random.randrange(number_of_vertices) - 1
        z = random.randint(-20, 120)
        edge_set.add((x, y, z))

    generated_graph = Graph(range(number_of_vertices), edge_set)

    return generated_graph


def write_graph_to_file(graph, file_path):
    """
    A function that writes a given graph into a text file.
    :param graph: an object of class Graph
    :param file_path: a string representing the given file.
    """
    f = open(file_path, "wt")
    first_line = str(graph.number_vertices) + " " + str(graph.number_of_edges) + "\n"
    f.write(first_line)
    if len(graph.parse_vertices()) == 0:
        raise ValueError("Can't write anything.")
    for edge in graph.costs:
        line = str(edge[0]) + " " + str(edge[1]) + " " + str(graph.find_edge(edge[0], edge[1])) + "\n"
        f.write(line)
    for vertex in graph.parse_vertices():
        if len(graph.outbound[vertex]) == 0 and len(graph.inbound[vertex]) == 0:
            line = str(vertex) + "\n"
            f.write(line)
    f.close()


def read_graph(file_path):
    # a function that reads a graph from a given file path and returns an object of class Graph
    f = open(file_path, "rt")
    line = f.readline()
    if len(line) < 3:
        raise ValueError("Number of vertices/edges is missing.")
    edge_set = list()
    elements = line.split()
    vertices_nr = int(elements[0])
    edges_nr = int(elements[1])
    for line in f.readlines():
        if len(line) > 1:
            elements = line.split()
            out_vertex = int(elements[0])
            in_vertex = int(elements[1])
            cost = int(elements[2])
            edge_set.append((out_vertex, in_vertex, cost))

    new_graph = Graph(range(vertices_nr), edge_set)
    return new_graph
