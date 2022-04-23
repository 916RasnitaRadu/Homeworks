import unittest

from src.domain.client import Client

from src.repository.repository import TextFileRepository


class TextFileRepoTest(unittest.TestCase):
    def setUp(self) -> None:
        file_path = "clients_test.txt"

        self.__repo = TextFileRepository(file_path, Client)
        self.__repo._data.clear()

    def tearDown(self) -> None:
        pass

    def test_file_repo_add_one(self):
        client = Client(1, "Radu")
        self.__repo.save(client)

        self.assertEqual(len(self.__repo), 1)

    def test_file_repo_find_by_id(self):
        client = Client(1, "Radu")
        self.__repo.save(client)

        self.assertEqual(self.__repo.find_by_id(1), client)

    def test_repo_get_all(self):
        client1 = Client(1, "Radu")
        client2 = Client(2, "Ilie")
        client3 = Client(3, "Bubu")

        self.__repo.save(client1)
        self.__repo.save(client2)
        self.__repo.save(client3)

        self.assertEqual(len(self.__repo), 3)

        clients = self.__repo.get_all()
        self.assertEqual(clients, [client1, client2, client3])

    def test_repo_remove(self):
        client1 = Client(1, "Radu")
        client2 = Client(2, "Ilie")
        client3 = Client(3, "Bubu")

        self.__repo.save(client1)
        self.__repo.save(client2)
        self.__repo.save(client3)
        self.assertEqual(len(self.__repo), 3)
        self.__repo.remove(2)
        self.assertEqual(len(self.__repo), 2)
