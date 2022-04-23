import unittest

from src.domain.movies import Movie
from src.repository.repository import Repository
from src.services.servicemovies import MovieService
from src.domain.exceptions import *


class MovieServiceTest(unittest.TestCase):
    def setUp(self) -> None:
        self.__movie_repo = Repository()
        self.__movie_sv = MovieService(self.__movie_repo)

    def tearDown(self) -> None:
        pass

    def test_add_movie_service(self):
        self.__movie_sv.add_movie(22, "Soul", "nebunie", "Cartoon")
        self.assertEqual(len(self.__movie_repo), 22)
        self.assertEqual(self.__movie_repo.__getitem__(22).title, "Soul")
        self.assertEqual(self.__movie_repo.__getitem__(22).id, 22)
        self.assertEqual(self.__movie_repo.__getitem__(22).description, "nebunie")

        with self.assertRaises(RepositoryException):
            self.__movie_sv.add_movie(10, "Title", "desc", "gen")

    def test_update_movie_service(self):
        self.__movie_sv.update(Movie(2, "HP5", "Majie", "Adventure"))
        self.assertEqual(self.__movie_repo.__getitem__(2).title, "HP5")
        self.assertEqual(self.__movie_repo.__getitem__(2).description,"Majie")
        self.assertEqual(self.__movie_repo.__getitem__(2).genre, "Adventure")

        with self.assertRaises(RepositoryException):
            self.__movie_sv.update(Movie(50, "titlu", "descr", "gen"))

    def test_remove_movie_service(self):
        self.__movie_sv.service_remove(3)
        with self.assertRaises(KeyError):
            title = self.__movie_repo.__getitem__(3).title

    def test_search_movie_by_title_service(self):
        title = "Title3"
        movie = self.__movie_repo.__getitem__(3)
        searched_movies = self.__movie_sv.search_movie_by_title_service(title)
        self.assertEqual(searched_movies, [movie])

        title = "Mam_$aturat_D3_T3$t3"

        with self.assertRaises(NameException):
            searched_movies = self.__movie_sv.search_movie_by_title_service(title)

    def test_search_movie_by_title_description(self):
        desc = "desc3"
        movie = self.__movie_repo.__getitem__(3)
        searched_movies = self.__movie_sv.search_movie_by_description_service(desc)
        self.assertEqual(searched_movies, [movie])

        desc = "Mam_$aturat_D3_T3$t3"

        with self.assertRaises(NameException):
            searched_movies = self.__movie_sv.search_movie_by_description_service(desc)

    def test_search_movie_by_title_genre(self):
        genre = "Horror"
        movie3 = self.__movie_repo.__getitem__(3)
        movie14 = self.__movie_repo.__getitem__(14)
        movie18 = self.__movie_repo.__getitem__(18)
        movie21 = self.__movie_repo.__getitem__(21)
        searched_movies = self.__movie_sv.search_movie_by_genre_service(genre)
        self.assertEqual(searched_movies, [movie3, movie14, movie18, movie21])

        genre = "Mam_$aturat_D3_T3$t3_pt_Ca_sunt_horror"

        with self.assertRaises(NameException):
            searched_movies = self.__movie_sv.search_movie_by_genre_service(genre)
