from PracticWork5.src.Graph.graph import *
from PracticWork5.src.Exceptions.exception import GraphException


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

    def ui_add_edge(self):
        x = int(input("First vertex = "))
        y = int(input("Second vertex = "))
        self._graph.add_edge(x, y)

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

    def list_bounds_of_vertex(self):
        x = int(input("Enter the vertex: "))
        bounds = self._graph.parse_bound(x)
        bounds_str = ""
        for vertex in bounds:
            bounds_str += str(vertex) + " "
        print(bounds_str)

    def ui_get_degree(self):
        x = int(input("Enter the vertex: "))
        print(self._graph.get_degree(x))

    def ui_get_connected_comps(self):
        connected_comp = self._graph.get_connected_components()

        for component in connected_comp:
            component_string = "New component: "
            for vertex in component:
                component_string += str(vertex) + " "
            print(component_string)

    def ui_get_min_vertex_cover(self):
        result = list(self._graph.get_vertex_cover())

        if len(result) == 0:
            print("There is no vertex cover.")
        else:
            str_res = "The vertex cover is: "

            for i in result:
                    str_res += str(i) + " "

            print(str_res)

    @staticmethod
    def print_menu():
        print("\n1. Get the number of vertices.")
        print("2. Get the number of edges.")
        print("3. Parse vertices.")
        print("4. Add edge.")
        print("5. Remove edge.")
        print("6. Add vertex.")
        print("7. Remove vertex")
        print("8. List the neigbours of a vertex.")
        print("9. Get the degree of a vertex.")
        print("10. Get connected componenets.")
        print("11. Get the minimum vertex cover.")
        print("12. Exit.\n")

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
                    self.ui_add_edge()
                elif n == 5:
                    self.ui_remove_edge()
                elif n == 6:
                    self.ui_add_vertex()
                elif n == 7:
                    self.ui_remove_vertex()
                elif n == 8:
                    self.list_bounds_of_vertex()
                elif n == 9:
                    self.ui_get_degree()
                elif n == 10:
                    self.ui_get_connected_comps()
                elif n == 11:
                    self.ui_get_min_vertex_cover()
                elif n == 12:
                    break
                else:
                    print("Invalid option.")
                write_graph_to_file(self._graph, "graph_modif.txt")
            except ValueError:
                print("Invalid input.")
            except GraphException as ge:
                print(ge.get_message())
