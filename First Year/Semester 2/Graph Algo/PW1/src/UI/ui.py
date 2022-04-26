from PracticWork1.src.Graph.graph import *
from PracticWork1.src.Exceptions.exception import GraphException


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

    def ui_get_connected_comps(self):
        connected_comp = self._graph.get_connected_components()
        for component in connected_comp:
            component_string = "New component: "
            for vertex in component:
                component_string += str(vertex) + " "
            print(component_string)

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
        print("16. Exit.\n")

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
                    break
                else:
                    print("Invalid option.")
                write_graph_to_file(self._graph, "graph_modif.txt")
            except ValueError:
                print("Invalid input.")
            except GraphException as ge:
                print(ge.get_message())
