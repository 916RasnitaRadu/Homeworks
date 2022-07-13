from PracticWork4.src.Graph.graph import *
from PracticWork4.src.Exceptions.exception import GraphException
from math import inf

class UI:
    def __init__(self, file_path):
        try:
            self._file_path = file_path
            self._graph = read_graph(file_path)
        except FileNotFoundError:
            print("There is no such file.")

    def print_number_of_vertices(self):
        print(self._graph.number_vertices)

    def print_number_of_edges(self):
        print(self._graph.number_of_edges)

    def print_parse_vertices(self):
        vertices = ""
        for vertex in self._graph.parse_vertices():
            vertices += str(vertex) + " "
        print(vertices)

    def ui_find_edge(self):
        x = int(input("First vertex = "))
        y = int(input("Second vertex = "))
        print(self._graph.find_edge(x, y))

    def ui_add_edge(self):
        x = int(input("First vertex = "))
        y = int(input("Second vertex = "))
        cost = int(input("Cost of the edge = "))
        self._graph.add_edge(x, y, cost)

    def ui_remove_edge(self):
        x = int(input("First vertex = "))
        y = int(input("Second vertex = "))
        self._graph.remove_edge(x, y)

    def ui_add_vertex(self):
        x = int(input("Enter the new vertex: "))
        self._graph.add_vertex(x)

    def ui_remove_vertex(self):
        x = int(input("Enter the vertex you want to remove: "))
        self._graph.remove_vertex(x)

    def list_outbound_of_vertex(self):
        x = int(input("Enter the vertex: "))
        outbound = self._graph.parse_outbound(x)
        outbound_str = ""
        for vertex in outbound:
            outbound_str += str(vertex) + " "
        print(outbound_str)

    def list_inbound_of_vertex(self):
        x = int(input("Enter the vertex: "))
        inbound = self._graph.parse_inbound(x)
        inbound_str = ""
        for vertex in inbound:
            inbound_str += str(vertex) + " "
        print(inbound_str)

    def ui_get_degree_in(self):
        x = int(input("Enter the vertex: "))
        print("The in degree of vertex " + str(x) + " is: " + str(self._graph.get_degree_in(x)))

    def ui_get_degree_out(self):
        x = int(input("Enter the vertex: "))
        print("The out degree of vertex " + str(x) + " is: " + str(self._graph.get_degree_in(x)))

    def ui_modify_cost(self):
        x = int(input("First vertex = "))
        y = int(input("Second vertex = "))
        cost = int(input("Input cost: "))
        self._graph.modify_cost(x, y, cost)

    def list_outbound_of_vertex_with_costs(self):
        x = int(input("Enter the vertex: "))
        outbound = self._graph.parse_outbound_with_costs(x)
        outbound_str = ""
        for vertex in outbound:
            outbound_str += str(vertex) + " "
        print(outbound_str)

    def list_inbound_of_vertex_with_costs(self):
        x = int(input("Enter the vertex: "))
        inbound = self._graph.parse_inbound_with_costs(x)
        inbound_str = ""
        for vertex in inbound:
            inbound_str += str(vertex) + " "
        print(inbound_str)

    def ui_find_minimum_cost_path(self):
        start_vertex = int(input("Enter the starting vertex: "))
        end_vertex = int(input("Enter the end vertex: "))
        cost, path = self._graph.minimum_cost_path(start_vertex, end_vertex)

        print("The minimum cost path between " + str(start_vertex) + " and " + str(end_vertex) + " is: " + str(cost))
        str_res = "The path from the end vertex to the start vertex is: "
        for vertex in path:
            str_res += str(vertex) + " "
        print(str_res)

    def ui_check_DAG(self):
        topsort_list = self._graph.topsort()
        if len(topsort_list) == 0:
            print("The Graph is not a DAG.\n")
        else:
            print("The graph is a DAG.\n")

    def ui_find_the_longest_path(self):
        topsort_list = self._graph.topsort()
        if len(topsort_list) == 0:
            raise GraphException("The graph is not a DAG.")

        print("For this graph we have the following topological sorting: ")
        top_str = ""
        for vertex in topsort_list:
            top_str += str(vertex) + ' '
        print(top_str)
        start_vertex = int(input("Enter the start vertex: "))
        end_vertex = int(input("Enter the end vertex: "))
        distance, prev = self._graph.find_highest_cost_path(start_vertex, end_vertex)
        if distance == -inf:
            raise GraphException("There is no walk between " + str(start_vertex) + " and " + str(end_vertex))
        path = []
        current_vertex = end_vertex
        path.append(end_vertex)
        while prev[current_vertex] != -1:
            current_vertex = prev[current_vertex]
            path.append(current_vertex)

        print("The cost of highest cost path is " + str(distance) + " and the path is: ")
        path.reverse()
        str_res = ""
        for vertex in path:
            str_res += str(vertex) + " "
        print(str_res)

    @staticmethod
    def print_menu():
        print("\n1. Get the number of vertices.")
        print("2. Get the number of edges.")
        print("3. Parse vertices.")
        print("4. Find edge.")
        print("5. Add edge.")
        print("6. Remove edge.")
        print("7. Add vertex.")
        print("8. Remove vertex")
        print("9. List the outbound of a vertex.")
        print("10. List the inbound of a vertex.")
        print("11. List the outbound costs of a vertex.")
        print("12. List the inbound costs of a vertex.")
        print("13. Get degree in.")
        print("14. Get degree out.")
        print("15. Modify cost")
        print("16. Find the minimum cost path between two vertices.")
        print("17. Check if the graph is a DAG.")
        print("18. Find the longest path between 2 vertices.")
        print("19. Exit.\n")

    def run_console(self):
        while True:
            try:
                self.print_menu()
                n = int(input("Please select what you want to do next: "))

                if n == 1:
                    self.print_number_of_vertices()
                elif n == 2:
                    self.print_number_of_edges()
                elif n == 3:
                    self.print_parse_vertices()
                elif n == 4:
                    self.ui_find_edge()
                elif n == 5:
                    self.ui_add_edge()
                elif n == 6:
                    self.ui_remove_edge()
                elif n == 7:
                    self.ui_add_vertex()
                elif n == 8:
                    self.ui_remove_vertex()
                elif n == 9:
                    self.list_outbound_of_vertex()
                elif n == 10:
                    self.list_inbound_of_vertex()
                elif n == 11:
                    self.list_outbound_of_vertex_with_costs()
                elif n == 12:
                    self.list_inbound_of_vertex_with_costs()
                elif n == 13:
                    self.ui_get_degree_in()
                elif n == 14:
                    self.ui_get_degree_out()
                elif n == 15:
                    self.ui_modify_cost()
                elif n == 16:
                    self.ui_find_minimum_cost_path()
                elif n == 17:
                    self.ui_check_DAG()
                elif n == 18:
                    self.ui_find_the_longest_path()
                elif n == 19:
                    break
                else:
                    print("Invalid option.")
                write_graph_to_file(self._graph, "graph_modif.txt")
            except ValueError:
                print("Invalid input.")
            except GraphException as ge:
                print(ge.get_message())




