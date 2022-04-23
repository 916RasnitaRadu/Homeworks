from datetime import *
from operator import itemgetter
from random import choice, randint
from src.domain.client import Client
from src.domain.exceptions import *

from src.domain.rental import Rental


class RentalService:
    def __init__(self, movie_repo, rental_repo, client_repo):
        self.__movie_repo = movie_repo
        self.__rental_repo = rental_repo
        self.__client_repo = client_repo
        self.temporary_repo = {}
        self.__rental_repo._data.clear()
        self.populate()

    def add_rental_to_temp_repo(self, rental_id, movie_id, client_id, rented_date, due_date, returned_date=None):
        can_rent = self.__client_repo.__getitem__(client_id).can_rent

        if self.__movie_repo.find_by_id(movie_id) is None:
            raise MovieIdException("You can not rent a movie that is not in the list.")
        if not can_rent:
            raise RentException("This client can not rent movies anymore.")
        if self.__rental_repo.find_by_id(rental_id) is not None or rental_id in self.temporary_repo:
            raise RepositoryException("There is already a rental with the same id in the list.")

        temporary_rental = [rental_id, movie_id, client_id, rented_date, due_date, returned_date, can_rent]
        self.temporary_repo[rental_id] = temporary_rental

    def delete_from_temporary_repo(self, rental_id):
        del self.temporary_repo[rental_id]

    def change_client_permisions(self, client_id):
        client_name = self.__client_repo.__getitem__(client_id).name
        client_changed = Client(client_id, client_name, False)
        self.__client_repo.__setitem__(client_id, client_changed)

    def add_rental_to_repo_if_returned(self, rental_id, returned_date):

        if rental_id not in self.temporary_repo:
            raise IdException("There is not any rental with this id.")

        movie_id = self.temporary_repo[rental_id][1]
        client_id = self.temporary_repo[rental_id][2]
        rented_date = self.temporary_repo[rental_id][3]
        due_date = self.temporary_repo[rental_id][4]
        can_rent = self.temporary_repo[rental_id][6]

        if not rented_date <= returned_date:
            raise DateException("You've entered a wrong date.")

        if due_date <= returned_date:
            self.change_client_permisions(client_id)

        self.delete_from_temporary_repo(rental_id)

        rental = Rental(rental_id, movie_id, client_id, rented_date, due_date, returned_date)
        self.__rental_repo.save(rental)

    def delete_rental_from_repo(self, rental_id):
        if self.__rental_repo.find_by_id(rental_id) is None:
            raise RepositoryException("There isn't any rental with this id in the list.")
        self.__rental_repo.remove(rental_id)


    def calculate_total_days_rented_for_movie(self, movie_id):
        rentals = self.__rental_repo.get_all()
        total_days_rented = 0

        for rental in rentals:
            if rental.movie_id == movie_id:
                total_days_rented += (rental.due_date - rental.rented_date).days
        return total_days_rented

    def calculate_total_days_rented_for_clients(self, client_id):
        rentals = self.__rental_repo.get_all()
        total_days_rented = 0

        for rental in rentals:
            if rental.client_id == client_id:
                total_days_rented += (rental.due_date - rental.rented_date).days
        return total_days_rented

    @staticmethod
    def calculate_days_delay(due_date):
        tday = datetime.today()
        days_delay = (tday - due_date).days
        return days_delay

    """
                - S T A T I S T I C S -
    For both of this 2 functions, we search in the rental repo for the movies or clients, we calculate the total number
    of rented days and we put them in a list of tuples [(<object>,  total_days_rented)] that is gonna be sorted.
    """

    def statistics_most_rented_movies(self):
        most_rented_movies = []
        movies = self.__movie_repo.get_all()

        for movie in movies:
            total_days_rented = self.calculate_total_days_rented_for_movie(movie.id)
            most_rented_movies.append((movie, total_days_rented))

        most_rented_movies = sorted(most_rented_movies, key=itemgetter(1), reverse=True)
        most_rented_movies = list(filter(lambda x: x[1] > 0, most_rented_movies))
        return most_rented_movies

    def statistics_most_active_clients(self):
        most_active_clients = []
        clients = self.__client_repo.get_all()

        for client in clients:
            if any([client.id == rental.client_id for rental in self.__rental_repo.get_all()]):
                total_days_rented = self.calculate_total_days_rented_for_clients(client.id)
                most_active_clients.append((client, total_days_rented))

        most_active_clients = sorted(most_active_clients, key=itemgetter(1), reverse=True)
        most_active_clients = list(filter(lambda x: x[1] > 0, most_active_clients))
        return most_active_clients

    def statistics_late_rentals(self):
        late_rentals = []
        for rental_id in self.temporary_repo:
            due_date = self.temporary_repo[rental_id][4]
            days_delay = self.calculate_days_delay(due_date)
            if days_delay >= 0:
                movie = self.__movie_repo.__getitem__(self.temporary_repo[rental_id][1]).title
                late_rentals.append((movie, days_delay))

        late_rentals = sorted(late_rentals, key=itemgetter(1), reverse=True)
        return late_rentals

    def populate(self):
        return_dates = [datetime.strptime('01/03/2021', '%d/%m/%Y'),
                        datetime.strptime('04/05/2021', '%d/%m/%Y'),
                        datetime.strptime('05/07/2021', '%d/%m/%Y'),
                        datetime.strptime('13/06/2020', '%d/%m/%Y'),
                        datetime.strptime('21/02/2021', '%d/%m/%Y'),
                        datetime.strptime('29/10/2020', '%d/%m/%Y'),
                        datetime.strptime('03/12/2020', '%d/%m/%Y'),
                        datetime.strptime('07/01/2021', '%d/%m/%Y')]
        due_dates = [datetime.strptime('01/03/2021', '%d/%m/%Y'),
                     datetime.strptime('14/09/2021', '%d/%m/%Y'),
                     datetime.strptime('15/05/2021', '%d/%m/%Y'),
                     datetime.strptime('13/06/2020', '%d/%m/%Y'),
                     datetime.strptime('11/08/2021', '%d/%m/%Y'),
                     datetime.strptime('11/11/2021', '%d/%m/%Y'),
                     datetime.strptime('12/02/2020', '%d/%m/%Y'),
                     datetime.strptime('17/04/2020', '%d/%m/%Y')]
        rent_dates = [datetime.strptime('01/03/2020', '%d/%m/%Y'),
                      datetime.strptime('14/10/2019', '%d/%m/%Y'),
                      datetime.strptime('10/03/2019', '%d/%m/%Y'),
                      datetime.strptime('06/11/2019', '%d/%m/%Y'),
                      datetime.strptime('16/03/2019', '%d/%m/%Y'),
                      datetime.strptime('04/01/2019', '%d/%m/%Y'),
                      datetime.strptime('12/02/2019', '%d/%m/%Y'),
                      datetime.strptime('17/02/2019', '%d/%m/%Y')]
        for i in range(1, 21):
            movie = randint(1, 21)
            client = randint(1, 21)
            rental = Rental(i, movie, client, choice(rent_dates), choice(due_dates), choice(return_dates))
            self.__rental_repo.save(rental)



