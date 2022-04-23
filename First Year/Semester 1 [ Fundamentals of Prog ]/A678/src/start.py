from src.repository.repository import Repository, TextFileRepository, BinFileRepository
from src.services.handler import Handler
from src.services.reantalservice import RentalService
from src.services.serviceclients import ClientService
from src.services.servicemovies import MovieService
from src.services.undo import UndoService
from src.ui.UI import UI
from src.domain.movies import Movie
from src.domain.client import Client
from src.domain.rental import Rental

if __name__ == '__main__':

    settings_props = dict()
    with open("setting.properties.txt", 'r') as f:
        lines = f.readlines()
        for line in lines:
            line = line.strip()
            key, value = line.split("=")
            key = key.strip()
            value = value.strip()
            if value[0] == "\"" and value[-1] == "\"":
                value = value[1:-1]
            settings_props[key] = value

    if settings_props["repository"] == "inmemory":
        movie_repo = Repository()
        client_repo = Repository()
        rental_repo = Repository()

        movie_service = MovieService(movie_repo)
        client_service = ClientService(client_repo)
        rental_service = RentalService(movie_repo, rental_repo, client_repo)

    elif settings_props["repository"] == "textfiles":
        movies = settings_props["movies"]
        clients = settings_props["clients"]
        rentals = settings_props["rentals"]

        movie_file_repo = TextFileRepository(movies, Movie)
        client_file_repo = TextFileRepository(clients, Client)
        rental_file_repo = TextFileRepository(rentals, Rental)

        movie_service = MovieService(movie_file_repo)
        client_service = ClientService(client_file_repo)
        rental_service = RentalService(movie_file_repo, rental_file_repo, client_file_repo)

    elif settings_props["repository"] == "binaryfiles":
        movies = settings_props["movies"]
        clients = settings_props["clients"]
        rentals = settings_props["rentals"]

        movie_bin_repo = BinFileRepository(movies)
        client_bin_repo = BinFileRepository(clients)
        rental_bin_repo = BinFileRepository(rentals)

        movie_service = MovieService(movie_bin_repo)
        client_service = ClientService(client_bin_repo)
        rental_service = RentalService(movie_bin_repo, rental_bin_repo, client_bin_repo)

    undo_service = UndoService()
    handler = Handler(undo_service, movie_service, client_service, rental_service)

    ui = UI(handler)

    ui.ui_start()


