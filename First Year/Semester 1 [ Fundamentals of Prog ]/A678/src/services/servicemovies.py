from src.domain.exceptions import *
from src.domain.movies import Movie


class MovieService:
    """
    A class that manages the list of movies. You can either add, remove, update a movie or get the list of all movies.
    """
    def __init__(self, movie_repo):
        self.__movie_repo = movie_repo
        self.__movie_repo._data.clear()
        self.add_movie(1, "Title1", "desc1", "Action")
        self.add_movie(2, "Title2", "desc2", "Adventure")
        self.add_movie(3, "Title3", "desc3", "Horror")
        self.add_movie(4, "Title4", "desc4", "Romance")
        self.add_movie(5, "Title5", "desc5", "Adventure")
        self.add_movie(6, "Title6", "desc6", "Action")
        self.add_movie(7, "Title7", "desc7", "Adventure")
        self.add_movie(8, "Title8", "desc8", "Action")
        self.add_movie(9, "Title9", "desc9", "SF")
        self.add_movie(10, "Title10", "desc10", "Adventure")
        self.add_movie(11, "Title11", "desc11", "Action")
        self.add_movie(12, "Title12", "desc12", "SF")
        self.add_movie(13, "Title13", "desc13", "Romance")
        self.add_movie(14, "Title14", "desc14", "Horror")
        self.add_movie(15, "Title15", "desc15", "Romance")
        self.add_movie(16, "Title16", "desc16", "SF")
        self.add_movie(17, "Title17", "desc17", "Adventure")
        self.add_movie(18, "Title18", "desc18", "Horror")
        self.add_movie(19, "Title19", "desc19", "Adventure")
        self.add_movie(20, "Title20", "desc20", "Action")
        self.add_movie(21, "Title21", "desc21", "Horror")

    def add_movie(self, movie_id, title, description, genre):
        """
        Adds a new movie to the movie repository
        :param movie_id: the unique id of the movie, integer
        :param title: the title of the movie, string
        :param description: a short description of the movie, string
        :param genre: the genre of the movie, string
        """
        new_movie = Movie(movie_id, title, description, genre)
        if self.__movie_repo.find_by_id(movie_id) is not None:
            raise RepositoryException("There is already a movie with the same id in the list.")
        self.__movie_repo.save(new_movie)

    def update(self, updated):
        """
        Updates a new movie from the repository after a given id.
        :param updated: an object of class movie that is going to be updated in the list
        """
        if self.__movie_repo.find_by_id(updated.id) is None:
            raise RepositoryException("There isn't any movie with this id in the list.")
        self.__movie_repo[updated.id] = updated

    def service_remove(self, key):
        """
        Remove a movie of a given id from the list.
        :param key: the id of the movie you want to remove
        """
        if self.__movie_repo.find_by_id(key) is None:
            raise RepositoryException("There isn't any movie with this id in the list.")
        self.__movie_repo.remove(key)

    def service_get_movie(self, key):
        return self.__movie_repo.__getitem__(key)

    def service_get_all(self):
        """
        A function that creates a list of all the movies from repository.
        :return: movie_list, the list of all movies coverage run -m unittest discover
        """
        movie_list = self.__movie_repo.get_all()
        return movie_list

    def search_movie_by_id_service(self, id):
        if self.__movie_repo.find_by_id(id) is None:
            raise IdException("There isn't any movie with this id in the list.")
        movie = self.__movie_repo.__getitem__(id)
        return movie

    def search_movie_by_title_service(self, title):
        if not any([title.lower() in movie.title.lower() for movie in self.__movie_repo.get_all()]):
            raise NameException("There aren't any movies with this title in the list.")
        searched_movies = list(filter(lambda x: title.lower() in x.title.lower(), self.__movie_repo.get_all()))
        return searched_movies

    def search_movie_by_description_service(self, desc):
        if not any([desc.lower() in movie.description.lower() for movie in self.__movie_repo.get_all()]):
            raise NameException("There aren't any movies with a matching description in the list.")
        searched_movies = list(filter(lambda x: desc.lower() in x.description.lower(), self.__movie_repo.get_all()))
        return searched_movies

    def search_movie_by_genre_service(self, genre):
        if not any([genre.lower() in movie.genre.lower() for movie in self.__movie_repo.get_all()]):
            raise NameException("There aren't any movies with a matching genre in the list.")
        searched_movies = list(filter(lambda x: genre.lower() in x.genre.lower(), self.__movie_repo.get_all()))
        return searched_movies

