import unittest

from src.domain.client import Client
from src.domain.exceptions import *
from src.repository.repository import Repository
from src.services.serviceclients import ClientService


class ClientServiceTest(unittest.TestCase):
    def setUp(self) -> None:
        self.__client_repo = Repository()
        self.__client_sv = ClientService(self.__client_repo)

    def tearDown(self) -> None:
        pass

    def test_add_client_service(self):
        self.__client_sv.add_client(22, "Haurentiu")
        self.__client_sv.add_client(23, "Miguel")
        self.assertEqual(len(self.__client_repo), 23)
        self.assertEqual(self.__client_repo.__getitem__(22).id, 22)
        self.assertEqual(self.__client_repo.__getitem__(22).name, "Haurentiu")

        with self.assertRaises(RepositoryException):
            self.__client_sv.add_client(10, "Title")

    def test_update_client_service(self):
        self.__client_sv.update(Client(12, "Bulbasar"))
        self.assertEqual(self.__client_repo.__getitem__(12).name, "Bulbasar")

        with self.assertRaises(RepositoryException):
            self.__client_sv.update(Client(60, "Jon"))

    def test_remove_client_service(self):
        self.__client_sv.service_remove(3)
        with self.assertRaises(KeyError):
            name = self.__client_repo.__getitem__(3).name

    def test_search_client_by_id_service(self):
        client = self.__client_repo.__getitem__(1)
        searched_client = self.__client_sv.search_client_by_id_service(1)
        self.assertEqual(client, searched_client)

        with self.assertRaises(IdException):
            searched_client = self.__client_sv.search_client_by_id_service(69)

    def test_search_client_by_name_service(self):
        name = "Radu"
        client = self.__client_repo.__getitem__(1)
        searched_clients = self.__client_sv.search_client_by_name_service(name)
        self.assertEqual(searched_clients, [client])

        with self.assertRaises(NameException):
            searched_clients = self.__client_sv.search_client_by_name_service("Matteo")