import unittest

from src.domain.client import Client
from src.domain.movies import Movie
from src.repository.repository import Repository
from src.services.handler import Handler
from src.services.reantalservice import RentalService
from src.services.serviceclients import ClientService
from src.services.servicemovies import MovieService
from src.services.undo import UndoService


class HandlerTest(unittest.TestCase):
    def setUp(self) -> None:
        self.__movie_repo = Repository()
        self.__client_repo = Repository()
        self.__rental_repo = Repository()

        self.__undo_service = UndoService()
        self.__movie_service = MovieService(self.__movie_repo)
        self.__client_service = ClientService(self.__client_repo)
        self.__rental_service = RentalService(self.__movie_repo, self.__rental_repo, self.__client_repo)
        self.__handler = Handler(self.__undo_service, self.__movie_service, self.__client_service, self.__rental_service)

    def tearDown(self) -> None:
        pass

    def test_add_movie(self):
        self.__handler.add_movie(22, "Title", "desc", "genre")

        self.assertEqual(self.__movie_service.service_get_movie(22).id, 22)
        self.assertEqual(self.__movie_service.service_get_movie(22).title, "Title")
        self.assertEqual(self.__movie_service.service_get_movie(22).description, "desc")
        self.assertEqual(self.__movie_service.service_get_movie(22).genre, "genre")

    def test_remove_movie(self):
        self.__handler.remove_movie(4)

        with self.assertRaises(KeyError):
            title = self.__movie_service.service_get_movie(4).title

    def test_update_movie(self):
        self.__handler.update_movie(Movie(2, "HP5", "Majie", "Adventure"))

        self.assertEqual(self.__movie_service.service_get_movie(2).title, "HP5")
        self.assertEqual(self.__movie_service.service_get_movie(2).description, "Majie")
        self.assertEqual(self.__movie_service.service_get_movie(2).genre, "Adventure")

    def test_search_movie_by_id_handler(self):
        self.assertEqual(self.__handler.search_movie_by_id(2), self.__movie_service.service_get_movie(2))

    def test_search_movie_by_title_handler(self):
        title = "Title3"
        movie = self.__movie_repo.__getitem__(3)
        searched_movies = self.__handler.search_movie_by_title(title)
        self.assertEqual(searched_movies, [movie])

    def test_search_movie_by_title_description(self):
        desc = "desc3"
        movie = self.__movie_repo.__getitem__(3)
        searched_movies = self.__handler.search_movie_by_description(desc)
        self.assertEqual(searched_movies, [movie])

    def test_search_movie_by_title_genre(self):
        genre = "Horror"
        movie3 = self.__movie_repo.__getitem__(3)
        movie14 = self.__movie_repo.__getitem__(14)
        movie18 = self.__movie_repo.__getitem__(18)
        movie21 = self.__movie_repo.__getitem__(21)
        searched_movies = self.__handler.search_movie_by_genre(genre)
        self.assertEqual(searched_movies, [movie3, movie14, movie18, movie21])


    def test_add_client(self):
        self.__handler.add_client(22, "Lorenzo")

        self.assertEqual(self.__client_service.service_get_client(22).id, 22)
        self.assertEqual(self.__client_service.service_get_client(22).name, "Lorenzo")

    def test_remove_client(self):
        self.__handler.remove_client(4)

        with self.assertRaises(KeyError):
            name = self.__client_service.service_get_client(4).name

    def test_update_client(self):
        self.__handler.update_client(Client(5, "Marinescu"))

        self.assertEqual(self.__client_service.service_get_client(5).name, "Marinescu")

    def test_search_client_by_id_handler(self):
        self.assertEqual(self.__handler.search_client_by_id(2), self.__client_service.service_get_client(2))

    def test_search_client_by_name_service_handler(self):
        name = "Radu"
        client = self.__client_repo.__getitem__(1)
        searched_clients = self.__handler.search_client_by_name(name)
        self.assertEqual(searched_clients, [client])

