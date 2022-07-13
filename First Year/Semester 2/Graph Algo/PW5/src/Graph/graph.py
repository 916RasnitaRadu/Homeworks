from copy import deepcopy
from PracticWork5.src.Exceptions.exception import *


class UndirectedGraph:
    def __init__(self, number_vertices, number_edges):
        """
        verices - iterable of vertices [1,2,3,4,5,6]
        edges - iterable of tuples
        [(1,2, 5), (6,4, -3), (3,1, 18), (2,2, 6), (5,3)]
        """
        self._number_of_edges = number_edges
        self._bounds = dict()  # a dictionary that stores all the neighbours for a vertex
        self._vertices = []

        for x in range(number_vertices):
            self._bounds[x] = []
            self._vertices.append(x)

        self._edge_list = []

    def is_edge(self, x, y):
        # a function that verifies if there is an edge between x and y (x,y - vertices)
        if x == y:
            return False
        if x in self._bounds:
            if y in self._bounds[x]:
                return True
        return False

    @property
    def bounds(self):
        return self._bounds

    @property
    def number_vertices(self):
        # a function that returns the number of vertices
        return len(self._bounds)

    @property
    def number_of_edges(self):
        # a function that returns the number of edges
        return len(self._edge_list)

    def parse_vertices(self):
        # a function that returns an iterable of the vertices
        if len(self._bounds) == 0:
            raise GraphException("There aren't any vertices.")
        return [x for x in self._bounds.keys()]

    def parse_bound(self, x):
        # a function that returns an iterable of the neighbours of vertex x or an exception if x is not a vertex
        if x not in self.parse_vertices():
            raise GraphException("The given value is not a vertex.")
        return [y for y in self._bounds[x]]

    def get_degree(self, x):
        # a function that returns the degree of the vertex x if this one exists
        if x not in self._bounds:
            raise GraphException("The given value is not a vertex.")
        return len(self._bounds[x])

    def add_vertex(self, x):
        # a function that adds a vertex to the graph
        if x in self.parse_vertices():
            raise GraphException("The given value is a vertex.")
        self._bounds[x] = []
        self._vertices.append(x)

    def remove_vertex(self, x):
        # a function that removes a vertex from the graph
        if x not in self.parse_vertices():
            raise GraphException("The given value is not a vertex.")
        for y in self.parse_vertices():
            if x in self._bounds[y]:
                self._bounds[y].remove(x)
                for edge in self._edge_list:
                    if edge == (x, y):
                        self._edge_list.remove((x, y))
                    elif edge == (y, x):
                        self._edge_list.remove((y, x))
        self._bounds.pop(x)

    def add_edge(self, x, y):
        # a function that adds an edge to the graph where x and y are the vertices
        if self.is_edge(x, y):
            raise GraphException("There already is an edge between these vertices.")
        self._bounds[x].append(y)
        self._bounds[y].append(x)
        self._number_of_edges += 1
        self._edge_list.append((x, y))

    def remove_edge(self, x, y):
        # a function that removes an edge from the graph if it exists (where x and y are vertices)
        if self.is_edge(x, y) is False:
            raise GraphException("There is no edge between these vertices.")
        self._bounds[x].remove(y)
        self._bounds[y].remove(x)
        self._number_of_edges -= 1
        self._edge_list.remove((x, y))

    def make_copy(self):
        # a function that returns a copy of the graph
        return deepcopy(self)

    def DFS(self, temp, x, visited):
        visited.add(x)
        temp.append(x)

        for vertex in self._bounds[x]:
            if vertex not in visited:
                temp = self.DFS(temp, vertex, visited)
        return temp

    def get_connected_components(self):
        visited = set()
        connected_comp = []

        for i in self.parse_vertices():
            if i not in visited:
                temporary_list = []
                connected_comp.append(self.DFS(temporary_list, i, visited))

        return connected_comp

    def get_vertex_cover(self):
        """
        A function that finds the minimum vertex cover of a graph.
        :return: result - a set of the vertices that cover all the edges
        """
        degrees = [(0, 0)] * self.number_vertices  # a list of tuples where the elements are (node_deg, node)
        visited = dict()  # a dictionary where the keys are the edges and the values is either True or False, depending if the edge was visited or not
        result = set()
        if len(self._edge_list) == 0:  # if there are no edges in the graph, the function will return an empty list
            return []
        for edge in self._edge_list:  # initializing the dictionary of edges
            visited[edge] = False

        for i in range(self.number_vertices):   # initializing the list of degrees
            degrees[i] = (self.get_degree(i), i)

        ok = 0  # ok counts how many edges were visited
        while len(degrees) != 0 and ok != self.number_of_edges:  # the loop stops if the degrees list is empty or we've visited all the edges
            deg = max(degrees)  # we take the vertex of max degree from the list
            node1 = deg[1]  # we take the vertex from the tuple
            degrees.remove(deg)  # remove the tuple from the list
            result.add(node1)  # add the vertex to the result
            for node2 in self._bounds[node1]:  # we take each neighbour of the current vertex
                if (node1, node2) in visited and visited[(node1, node2)] is False:  # we check if the edge was visited and if it wasn't
                    visited[(node1, node2)] = True  # we mark it as True and
                    ok += 1  # count it in the ok variable
                elif (node2, node1) in visited and visited[(node2, node1)] is False:
                    visited[(node2, node1)] = True
                    ok += 1

        return result


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
    edges = set()
    for first_vertex in graph.parse_vertices():
        if len(graph.bounds[first_vertex]) != 0:
            for second_vertex in graph.bounds[first_vertex]:
                if (second_vertex, first_vertex) not in edges:
                    edges.add((first_vertex, second_vertex))
        else:
            line = str(first_vertex) + "\n"
            f.write(line)
    for edge in edges:
        line = str(edge[0]) + " " + str(edge[1]) + "\n"
        f.write(line)
    f.close()


def read_graph(file_path):
    # a function that reads a graph from a given file path and returns an object of class Graph
    f = open(file_path, "rt")
    line = f.readline()
    if len(line) < 3:
        raise ValueError("Number of vertices/edges is missing.")
    elements = line.split()
    vertices_nr = int(elements[0])
    edges_nr = int(elements[1])
    new_graph = UndirectedGraph(vertices_nr, edges_nr)
    for line in f.readlines():
        if len(line) > 1:
            elements = line.split()
            first_vertex = int(elements[0])
            second_vertex = int(elements[1])
            if first_vertex != second_vertex and new_graph.is_edge(first_vertex, second_vertex) is False:
                new_graph.add_edge(first_vertex, second_vertex)
    f.close()
    return new_graph
