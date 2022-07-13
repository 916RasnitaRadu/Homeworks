import math
from math import inf
from copy import deepcopy
from PracticWork4.src.Exceptions.exception import *
from PracticWork4.src.PriorityQueue.priority_queue import PriorityQueue


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

    def get_cost(self, x, y):
        if self.is_edge(x, y) is False:
            raise GraphException("There is no edge between vertices " + str(x) + " and " + str(y))
        return self._costs[(x, y)]

    def minimum_cost_path(self, start_vertex, end_vertex):
        """
        A function that computes the minimum cost walk and the reversed path from a vertex to another using Dijkstra's algorithm
        :param start_vertex: a vertex (integer) that represents the start point
        :param end_vertex: a vertex (integer) that represents the end point
        :return: dist[end_vertex] - integer which represents the lowest cost from the start_vertex to the end_vertex
        reverse_path - a list which represents the path in reverse order, from the end_vertex to the start_vertex
        """

        if start_vertex not in self.parse_vertices() or end_vertex not in self.parse_vertices():
            raise GraphException("One of the given value is not a vertice. Or maybe both.")

        if self.get_degree_out(start_vertex) == 0:
            raise GraphException("There is no path between the vertices.")

        if self.get_degree_in(end_vertex) == 0:
            raise GraphException("There is no path between the vertices.")

        priority_queue = PriorityQueue()
        dist = dict()  # a dictionary that has as the keys the vertices of the graph and as values the lowest cost walks from the start vertex to the key
        prev = dict()  # a dictionary that has as the keys the vertices of the graph and as values the predecessor of the key in the lowest cost walks from
        # the start vertex to the key
        priority_queue.enqueue((end_vertex, 0))  # we put the starting in the queue with priority 0
        # we initialize for each vertex the distance as infinity and the predecessor as -1
        for vertex in self.parse_vertices():
            dist[vertex] = inf
            prev[vertex] = -1

        dist[end_vertex] = 0
        found = False
        # we start iterating
        while not priority_queue.is_empty() and not found:
            x = priority_queue.dequeue()[0]  # we take the vertex with the highest priority from the queue(the one with the lowest distance)
            for y in self.parse_inbound(x):  # we iterate through his outbound neighbours
                if dist[x] + self.get_cost(y, x) < dist[y]:  # we check if the vertex y was not visited or if the distance from it
                    # to the start vertex is minimum
                    dist[y] = dist[x] + self.get_cost(y, x)  # if one condition is true we update his value in the distances dictionary
                    priority_queue.enqueue((y, dist[y]))  # and we put in the queue
                    prev[y] = x
            if x == start_vertex:  # if we have found the end vertex we stop
                found = True

        if prev[start_vertex] == -1:  # next we check if there is a walk between the start vertex and the end vertex, if not, we raise an exception
            raise GraphException("There is no walk between the 2 vertices.")
        # we will continue by computing the path in reverse order
        reverse_path = []
        current_vertex = start_vertex
        reverse_path.append(start_vertex)
        while prev[current_vertex] != -1:
            reverse_path.append(prev[current_vertex])
            current_vertex = prev[current_vertex]
        # we return the minimum cost and the path reversed
        return dist[start_vertex], reverse_path

    def topsort_dfs(self, node, sorted_list, visited, visiting):
        """
        The DFS function for making the topological sorting.
        :param node: a vertex, int
        :param sorted_list: a list of vertices
        :param visited: a set of the visited vertices
        :param visiting: a set of the vertices that are visited in the curent proccess
        :return: True or False, based on the fact that we find a cycle in the graph or not
        """
        visiting.add(node)  # we mark the node as visited

        for other_node in self._inbound[node]:  # we check the inbound neighbours of the given vertex
            if other_node in visiting:   # if we find a node that was visited in the current process, it means that we have a cycle in the graph
                return False  # therefore, the graph is not DAG and we return False
            elif other_node not in visited:
                result = self.topsort_dfs(other_node, sorted_list, visited, visiting)
                if result is False:
                    return False
        visiting.remove(node)
        visited.add(node)
        sorted_list.append(node)
        return True

    def topsort(self):
        """
        The topsort function that uses DFS.
        :return: sorted_list - a list of vertices, one of the possibles topological sortings
        """
        sorted_list = []
        visited = set()
        visiting = set()
        for node in self.parse_vertices():  # we parse all the vertices
            if node not in visited:  # if the vertex is not visited we apply DFS
                result = self.topsort_dfs(node, sorted_list, visited, visiting)
                if result is False:  # if the output of the DFS function is False it means that the algorithm found a cycle in the graph the cointans the
                    # respective node
                    return []  # therefore we will return an empty list
        return sorted_list

    def find_highest_cost_path(self, start_vertex, end_vertex):
        """
        A function that computes the highest cost path in a DAG from a vertex to another, using topological sort.
        :param start_vertex: starting vertex, int
        :param end_vertex: destination vertex, int
        :return: distance - int, representing the total cost of the path
        prev - a dictionary, in which each key has as a value the previous node in the path
        """
        if start_vertex not in self._outbound or end_vertex not in self._outbound:
            raise GraphException("One of the given values is not a vertex. Or maybe both.")

        topsort_list = self.topsort()  # we calculate the topological sorted list
        dist = dict()  # a dict in which every key has as value the total cost until the respective node in the path
        prev = dict()

        for vertex in topsort_list:  # we initialise the dictionaries
            dist[vertex] = -math.inf
            prev[vertex] = -1
        dist[start_vertex] = 0

        for vertex in topsort_list:  # then we take each node in the sorted list
            if vertex == end_vertex:  # if it's the destination vertex, we stop
                break
            for other_vertex in self._outbound[vertex]:  # next we take each outbound neighbour of the current vertex and we check if the distance is maximum
                if dist[other_vertex] < dist[vertex] + self.get_cost(vertex, other_vertex):  # if it's not, we update it
                    dist[other_vertex] = dist[vertex] + self.get_cost(vertex, other_vertex)
                    prev[other_vertex] = vertex

        return dist[end_vertex], prev


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

# for (x, y) in self._costs.keys():
#     if dist[y] > dist[x] + self.get_cost(x, y) and dist[x] != inf:
#         raise GraphException("Graph contains a negative weight cycle.")
