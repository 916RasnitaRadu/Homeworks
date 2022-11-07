import unittest
from datetime import datetime

from src.domain.client import Client
from src.domain.movies import Movie
from src.repository.repository import Repository
from src.domain.exceptions import *
from src.services.reantalservice import RentalService


class RentalServiceTest(unittest.TestCase):
    def setUp(self) -> None:
        self.__movie_repo = Repository()
        self.__client_repo = Repository()
        self.__rental_repo = Repository()
        self.__movie_repo.save(Movie(2, "1917", "WW1", "Action/History"))
        self.__client_repo.save(Client(3, "Cozmin", True))
        self.__rental_sv = RentalService(self.__movie_repo, self.__rental_repo, self.__client_repo)

    def tearDown(self) -> None:
        pass

    def test_add_to_temporary_repo_and_to_repo_if_returned(self):

        rented_date = datetime.strptime("12/03/2021", '%d/%m/%Y')
        due_date = datetime.strptime("12/04/2021", '%d/%m/%Y')
        returned_date = datetime.strptime("21/03/2021", '%d/%m/%Y')
        # coverage run --source=./ -m unittest discover -s ./src/unittests/
        self.__rental_sv.add_rental_to_temp_repo(21, 2, 3, rented_date, due_date)
        self.assertEqual(self.__rental_sv.temporary_repo[21][0], 21)
        self.assertEqual(self.__rental_sv.temporary_repo[21][1], 2)
        self.assertEqual(self.__rental_sv.temporary_repo[21][2], 3)
        self.assertEqual(self.__rental_sv.temporary_repo[21][3], rented_date)
        self.assertEqual(self.__rental_sv.temporary_repo[21][4], due_date)

        self.__rental_sv.add_rental_to_repo_if_returned(21, returned_date)

        self.assertEqual(self.__rental_repo.__getitem__(21).id, 21)
        self.assertEqual(self.__rental_repo.__getitem__(21).movie_id, 2)
        self.assertEqual(self.__rental_repo.__getitem__(21).client_id, 3)
        self.assertEqual(self.__rental_repo.__getitem__(21).rented_date, rented_date)
        self.assertEqual(self.__rental_repo.__getitem__(21).due_date, due_date)
        self.assertEqual(self.__rental_repo.__getitem__(21).returned_date, returned_date)

    def test_delete_from_temporary_repo(self):
        rented_date = datetime.strptime("12/03/2021", '%d/%m/%Y')
        due_date = datetime.strptime("12/04/2021", '%d/%m/%Y')
        self.__rental_sv.add_rental_to_temp_repo(21, 2, 3, rented_date, due_date)

        self.__rental_sv.delete_from_temporary_repo(21)

        with self.assertRaises(KeyError):
            rental = self.__rental_sv.temporary_repo[21]
            