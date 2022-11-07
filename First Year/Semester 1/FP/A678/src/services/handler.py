
from src.services.undo import Call, Operation
# coverage run --source=./ -m unittest discover -s ./src/unittests/

class Handler:
    def __init__(self, undo_service, movie_service, client_service, rental_service):
        self.__undo_service = undo_service
        self.__movie_service = movie_service
        self.__client_service = client_service
        self.__rental_service = rental_service

    # =========================================== Movies ========

    def add_movie(self, id, title, description, genre):
        self.__movie_service.add_movie(id, title,description,genre)

        undo_call = Call(self.__movie_service.service_remove, id)
        redo_call = Call(self.__movie_service.add_movie, id, title, description, genre)
        operation = Operation(undo_call, redo_call)
        self.__undo_service.record(operation)

    def remove_movie(self, id):
        movie = self.__movie_service.service_get_movie(id)
        self.__movie_service.service_remove(id)

        undo_call = Call(self.__movie_service.add_movie, id, movie.title, movie.description, movie.genre)
        redo_call = Call(self.__movie_service.service_remove, id)
        operation = Operation(undo_call, redo_call)
        self.__undo_service.record(operation)

    def update_movie(self, new_movie):
        old_movie = self.__movie_service.service_get_movie(new_movie.id)
        self.__movie_service.update(new_movie)

        undo_call = Call(self.__movie_service.update, old_movie)
        redo_call = Call(self.__movie_service.update, new_movie)
        operation = Operation(undo_call, redo_call)
        self.__undo_service.record(operation)

    def get_movies(self):
        return self.__movie_service.service_get_all()

    def search_movie_by_id(self, id):
        return self.__movie_service.search_movie_by_id_service(id)

    def search_movie_by_title(self, title):
        return self.__movie_service.search_movie_by_title_service(title)

    def search_movie_by_description(self, description):
        return self.__movie_service.search_movie_by_description_service(description)

    def search_movie_by_genre(self, genre):
        return self.__movie_service.search_movie_by_genre_service(genre)

    # =========================================== Clients ========
    def add_client(self, id, name):
        self.__client_service.add_client(id, name)

        undo_call = Call(self.__client_service.service_remove, id)
        redo_call = Call(self.__client_service.add_client, id, name)
        operation = Operation(undo_call, redo_call)
        self.__undo_service.record(operation)

    def remove_client(self, id):
        client = self.__client_service.service_get_client(id)
        self.__client_service.service_remove(id)

        undo_call = Call(self.__client_service.add_client, id, client.name)
        redo_call = Call(self.__client_service.service_remove, id)
        operation = Operation(undo_call, redo_call)
        self.__undo_service.record(operation)

    def update_client(self, newClient):
        oldClient = self.__client_service.service_get_client(newClient.id)
        self.__client_service.update(newClient)

        undo_call = Call(self.__client_service.update, oldClient)
        redo_call = Call(self.__client_service.update, newClient)
        operation = Operation(undo_call, redo_call)
        self.__undo_service.record(operation)

    def get_clients(self):
        return self.__client_service.service_get_all()

    def search_client_by_id(self, id):
        return self.__client_service.search_client_by_id_service(id)

    def search_client_by_name(self, name):
        return self.__client_service.search_client_by_name_service(name)

    # =========================================== Rentals ========

    def add_rental_to_temp_repo_handler(self, rental_id, movie_id, client_id, rented_date, due_date, returned_date):
        self.__rental_service.add_rental_to_temp_repo(rental_id, movie_id, client_id, rented_date, due_date, None)

        undo_call = Call(self.__rental_service.delete_from_temporary_repo, rental_id)
        redo_call = Call(self.__rental_service.add_rental_to_temp_repo, rental_id, movie_id, client_id, rented_date, due_date, None)
        operation = Operation(undo_call, redo_call)
        self.__undo_service.record(operation)

    def add_rental_to_repo_if_returned_handler(self, rental_id, returned_date):
        self.__rental_service.add_rental_to_repo_if_returned(rental_id, returned_date)

        undo_call = Call(self.__rental_service.delete_rental_from_repo, rental_id)
        redo_call = Call(self.__rental_service.add_rental_to_repo_if_returned, rental_id, returned_date)
        operation = Operation(undo_call, redo_call)
        self.__undo_service.record(operation)

    # =========================================== Stats ========

    def statistics_most_rented_movies_handler(self):
        return self.__rental_service.statistics_most_rented_movies()

    def statistics_most_active_clients_handler(self):
        return self.__rental_service.statistics_most_active_clients()

    def statistics_late_rentals_handler(self):
        return self.__rental_service.statistics_late_rentals()

    def undo(self):
        self.__undo_service.undo()

    def redo(self):
        self.__undo_service.redo()
