from src.domain.exceptions import RepositoryException
from src.domain.movies import Movie
from src.domain.client import Client
from datetime import datetime, timedelta

class UI:
    def __init__(self, handler):
        self.__handler = handler

    # ========================= StaticMethods ===================================

    @staticmethod
    def ui_print_first_menu():
        print("\n1. Movies")
        print("2. Clients")
        print("3. Rentals")
        print("4. See statistics")
        print("5. Undo the last operation")
        print("6. Redo the last operation")
        print("7. Exit the program\n")

    @staticmethod
    def ui_print_movie_menu():
        print("\n1. Add a movie")
        print("2. Remove a movie")
        print("3. Update the specifications for a movie")
        print("4. Show the list of movies")
        print("5. Search for movies")
        print("6. Exit the program\n")

    @staticmethod
    def ui_print_client_menu():
        print("\n1. Add a client")
        print("2. Remove a client")
        print("3. Update the specifications for a client")
        print("4. Show the list of clients")
        print("5. Search for clients")
        print("6. Exit the program\n")

    @staticmethod
    def ui_print_rental_menu():
        print("\n1. Rent a movie.")
        print("2. Return a movie.")
        print("3. Exit the program.\n")

    @staticmethod
    def ui_print_search_client_menu():
        print("\nYou want to search a client by: ")
        print("1. ID")
        print("2. Name\n")

    @staticmethod
    def ui_print_search_movie_menu():
        print("\nYou want to search a movie by: ")
        print("1. ID")
        print("2. Title")
        print("3. Description")
        print("4. Genre\n")

    @staticmethod
    def ui_print_statistics_menu():
        print("\n1. Most rented movies.")
        print("2. Most active clients.")
        print("3. Late rentals.")
        print("4. Exit the program.\n")

    # ============================== Movie Functionalities ==========================

    def ui_add_movie(self):
        try:
            id = int(input("Please enter the id of the film: "))
            if id < 0:
                raise ValueError
            title = input("Please enter the title of the film: ")
            description = input("Please enter a description for the film: ")
            genre = input("Please enter the genre of the film: ")

            self.__handler.add_movie(id, title,description,genre)
        except ValueError:
            print("The id must be a positive integer.")

    def ui_remove_movie(self):
        try:
            id = int(input("Enter the id of the movie you want to remove: "))
            if id < 0:
                raise ValueError
            self.__handler.remove_movie(id)
        except ValueError:
            print("The id must be a positive integer.")

    def ui_update_movie(self):
        try:
            id = int(input("Enter the id of the movie you want to update: "))
            if id < 0:
                raise ValueError
            new_title = input("Enter the new title: ")
            new_description = input("Enter the new description: ")
            new_genre = input("Enter the new genre: ")
            updated_movie = Movie(id, new_title, new_description, new_genre)
            self.__handler.update_movie(updated_movie)
        except ValueError:
            print("The id must be a positive integer.")

    def ui_list_movies(self):
        movie_list = self.__handler.get_movies()
        print("The list of movies is: ")
        for movie in movie_list:
            print(str(movie.id) + " " + movie.title + "," + " " + movie.description + "," + " " + movie.genre)

    def ui_search_movie_id(self):
        try:
            id = int(input("Enter the id of the movie you want to find: "))
            if id < 0:
                raise ValueError
            movie_found = self.__handler.search_movie_by_id(id)
            print("The next movie was found: " + str(id) + ", " + movie_found.title + ", " + movie_found.description +
                  ", " + movie_found.genre)
        except ValueError:
            print("The id must be a positive integer.")

    def ui_search_movie_title(self):
        title = input("Enter the title of the movie you want to find: ")
        movies_found = self.__handler.search_movie_by_title(title)

        print("The following movies having a matching title were found: ")
        for movie in movies_found:
            print(str(movie.id) + ", " + movie.title + ", " + movie.description +  ", " + movie.genre)

    def ui_search_movie_description(self):
        description = input("Enter the description of the movie you want to find: ")
        movies_found = self.__handler.search_movie_by_description(description)

        print("The following movies having a matching description were found: ")
        for movie in movies_found:
            print(str(movie.id) + ", " + movie.title + ", " + movie.description +  ", " + movie.genre)

    def ui_search_movie_genre(self):
        genre = input("Enter the genre of the movie you want to find: ")
        movies_found = self.__handler.search_movie_by_genre(genre)

        print("The following movies having a matching genre were found: ")
        for movie in movies_found:
            print(str(movie.id) + ", " + movie.title + ", " + movie.description + ", " + movie.genre)

    def ui_search_movie(self):
        self.ui_print_search_movie_menu()
        try:
            n = int(input("Select an option: "))
            if n < 0 or n > 4:
                raise ValueError
            if n == 1:
                self.ui_search_movie_id()
            if n == 2:
                self.ui_search_movie_title()
            if n == 3:
                self.ui_search_movie_description()
            if n == 4:
                self.ui_search_movie_genre()
        except ValueError:
            print("Sorry! Wrong input!")


    # ================== Client Functionalities ===========================

    def ui_add_client(self):
        try:
            id = int(input("Please enter the id of the client: "))
            if id < 0:
                raise ValueError
            name = input("Please enter the name of the client: ")

            self.__handler.add_client(id, name)
        except ValueError:
            print("The id must be a positive integer.")

    def ui_remove_client(self):
        try:
            id = int(input("Enter the id of the client you want to remove: "))
            if id < 0:
                raise ValueError
            self.__handler.remove_client(id)
        except ValueError:
            print("The id must be a positive integer.")

    def ui_update_client(self):
        try:
            id = int(input("Enter the id of the client you want to update: "))
            if id < 0:
                raise ValueError
            new_name = input("Enter the name of the new client: ")
            updated_client = Client(id, new_name)
            self.__handler.update_client(updated_client)
        except ValueError:
            print("The id must be a positive integer.")

    def ui_list_clients(self):
        client_list = self.__handler.get_clients()
        print("The list of movies is: ")
        for client in client_list:
            print(str(client.id) + " " + client.name)

    def ui_search_client_id(self):
        try:
            id = int(input("Enter the id of the client you want to find: "))
            if id < 0:
                raise ValueError
            client_found = self.__handler.search_client_by_id(id)
            print("The next client was found: " + str(id) + ", " + client_found.name)
        except ValueError:
            print("The id must be a positive integer.")

    def ui_search_client_name(self):
        name = input("Enter the name of the client you want to find: ")
        clients_found = self.__handler.search_client_by_name(name)

        print(f"The following clients having the name {name} were found: ")
        for client in clients_found:
            print(str(client.id) + ", " + client.name)

    def ui_search_client(self):
        self.ui_print_search_client_menu()
        try:
            n = int(input("Select an option: "))
            if n < 0 or n > 2:
                raise ValueError
            if n == 1:
               self.ui_search_client_id()
            if n == 2:
                self.ui_search_client_name()
        except ValueError:
            print("Sorry! Wrong input.")

    # ================================ Rental Functionalities===========================

    def calculate_due_date(self, rented_date):
        tdelta = timedelta(days=31)
        due_date = rented_date + tdelta
        return due_date


    def ui_rent_a_movie(self):
        try:
            rental_id = int(input("Please enter the id of the rental: "))
            movie_id = int(input("Please enter the id of the movie: "))
            client_id = int(input("Please enter the id of the client: "))
            if rental_id < 0 or movie_id < 0 or client_id < 0:
                raise ValueError
            rented_date_str = input("Enter the rented date (day month year): ")
            rented_date = datetime.strptime(rented_date_str, '%d/%m/%Y')
        #   due_date_str = input("Enter the due date (day month year): ")
        #   due_date = datetime.strptime(due_date_str, '%d/%m/%Y')
            due_date = self.calculate_due_date(rented_date)
            returned_date = None
            self.__handler.add_rental_to_temp_repo_handler(rental_id, movie_id,client_id,rented_date, due_date, returned_date)
        except ValueError:
            print("The ids must be positive integers.")

    def ui_return_a_movie(self):
        try:
            rental_id = int(input("Enter the id of the rental: "))
            if rental_id < 0:
                raise ValueError
            returned_date_str = input("Enter the date of return: ")
            returned_date = datetime.strptime(returned_date_str, '%d/%m/%Y')
            self.__handler.add_rental_to_repo_if_returned_handler(rental_id, returned_date)
        except ValueError:
            print("The id must be a positive integer.")

    def ui_most_rented_movies(self):
        most_rented_movies = self.__handler.statistics_most_rented_movies_handler()
        for item in most_rented_movies:
            print(item[0].title + " with " + str(item[1]) + " days")

    def ui_most_active_clients(self):
        most_active_clients = self.__handler.statistics_most_active_clients_handler()
        for item in most_active_clients:
            print(item[0].name + " with " + str(item[1]) + " days")

    def ui_late_rentals(self):
        late_rentals = self.__handler.statistics_late_rentals_handler()
        for item in late_rentals:
            print(item[0] + " with: " + str(item[1]) + " days delay")

    def ui_start(self):

        while True:
            self.ui_print_first_menu()
            try:
                m = int(input("Select with what do you want to work next: "))
                if m <= 0 or m > 7:
                    raise ValueError
                if m == 1:
                    self.ui_print_movie_menu()
                    x = int(input("Select what you want to do next: "))
                    if x <= 0 or x > 6:
                        raise ValueError
                    if x == 1:
                        self.ui_add_movie()
                    elif x == 2:
                        self.ui_remove_movie()
                    elif x == 3:
                        self.ui_update_movie()
                    elif x == 4:
                        self.ui_list_movies()
                    elif x == 5:
                        self.ui_search_movie()
                    elif x == 6:
                        return
                elif m == 2:
                    self.ui_print_client_menu()
                    y = int(input("Select what you want to do next: "))
                    if y <= 0 or y > 6:
                        raise ValueError
                    if y == 1:
                        self.ui_add_client()
                    elif y == 2:
                        self.ui_remove_client()
                    elif y == 3:
                        self.ui_update_client()
                    elif y == 4:
                        self.ui_list_clients()
                    elif y == 5:
                        self.ui_search_client()
                    elif y == 6:
                        return
                elif m == 3:
                    self.ui_print_rental_menu()
                    z = int(input("Select what you want to do next: "))
                    if z <= 0 or z > 3:
                        raise ValueError
                    if z == 1:
                        self.ui_rent_a_movie()
                    elif z == 2:
                        self.ui_return_a_movie()
                    elif z == 3:
                        return
                elif m == 4:
                    self.ui_print_statistics_menu()
                    a = int(input("Select what you want to do next: "))
                    if a <= 0 or a > 4:
                        raise ValueError
                    if a == 1:
                        self.ui_most_rented_movies()
                    elif a == 2:
                        self.ui_most_active_clients()
                    elif a == 3:
                        self.ui_late_rentals()
                    elif a == 4:
                        return
                elif m == 5:
                    self.__handler.undo()
                elif m == 6:
                    self.__handler.redo()
                elif m == 7:
                    return
            except ValueError as ve:
                print("The option you entered is not available.")
            except RepositoryException as re:
                print(re.get_message())
