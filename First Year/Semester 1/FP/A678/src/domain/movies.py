class Movie:
    def __init__(self, _movie_id, title, description, genre):
        """
        Creates a new movie
        :param _movie_id: the unique id of the movie, integer
        :param title: the title of the movie, string
        :param description: a short description of the movie, string
        :param genre: the genre of the movie, string
        """
        self.__movie_id = _movie_id
        self.__title = title
        self.__description = description
        self.__genre = genre

    @property
    def id(self):
        return self.__movie_id

    @property
    def title(self):
        return self.__title

    @property
    def description(self):
        return self.__description

    @property
    def genre(self):
        return self.__genre

    @staticmethod
    def line_to_entity(line):
        words = line.split(',')
        return Movie(int(words[0]), words[1], words[2], words[3])

    @staticmethod
    def entity_to_line(movie):
        return f"{movie.id}, {movie.title}, {movie.description}, {movie.genre}"






























