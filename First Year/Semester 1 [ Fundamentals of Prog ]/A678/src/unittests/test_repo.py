import unittest

from src.domain.movies import Movie
from src.repository.repository import Repository


class RepositoryTest(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = Repository()

    def tearDown(self) -> None:
        pass

    def test_empty_Repo(self):
        self.assertEqual(len(self.__repo), 0)

    def test_repo_add_one(self):
        movie = Movie(1, "Inception", "dreams and stuff", "Action/SF")
        self.__repo.save(movie)
        self.assertEqual(len(self.__repo), 1)

    def test_repo_find_by_id(self):
        movie = Movie(1, "Inception", "dreams and stuff", "Action/SF")
        self.__repo.save(movie)

        self.assertEqual(self.__repo.find_by_id(1), movie)

    def test_repo_get_all(self):
        movie1 = Movie(1, "Inception", "dreams and stuff", "Action/SF")
        movie2 = Movie(2, "Movie2", "desc", "SF")
        self.__repo.save(movie1)
        self.__repo.save(movie2)
        self.assertEqual(len(self.__repo), 2)
        movies = self.__repo.get_all()
        self.assertEqual(movies, [movie1, movie2])

    def test_repo_remove(self):
        movie1 = Movie(1, "Inception", "dreams and stuff", "Action/SF")
        movie2 = Movie(2, "Movie2", "desc", "SF")
        self.__repo.save(movie1)
        self.__repo.save(movie2)
        self.assertEqual(len(self.__repo), 2)
        self.__repo.remove(2)
        self.assertEqual(len(self.__repo), 1)
