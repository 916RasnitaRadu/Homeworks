
class Rental:
    def __init__(self, _rental_id, movie_id, client_id, rented_date, due_date, returned_date):
        """
        Creates a new rental
        :param _rental_id: the id of the rental, integer
        :param movie_id: the id of the rented movie, integer
        :param client_id: the id of the client, integer
        :param rented_date: the date the movie is rented
        :param due_date: the date until the movie can be returned
        :param returned_date: the date the movie is returned
        """
        self.__rental_id = _rental_id
        self.__movie_id = movie_id
        self.__client_id = client_id
        self.__rented_date = rented_date
        self.__due_date = due_date
        self.__returned_date = returned_date

    @property
    def id(self):
        return self.__rental_id

    @property
    def movie_id(self):
        return self.__movie_id

    @property
    def client_id(self):
        return self.__client_id

    @property
    def rented_date(self):
        return self.__rented_date

    @property
    def due_date(self):
        return self.__due_date

    @property
    def returned_date(self):
        return self.__returned_date

    @staticmethod
    def line_to_entity(line):
        words = line.split(',')
        return Rental(int(words[0]), int(words[1]), int(words[2]), words[3], words[4], words[5])

    @staticmethod
    def entity_to_line(rental):
        return f"{rental.id}, {rental.movie_id}, {rental.client_id}, {rental.rented_date}, {rental.due_date}, {rental.returned_date}"





























