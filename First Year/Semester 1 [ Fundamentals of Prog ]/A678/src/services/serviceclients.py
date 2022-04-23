from src.domain.client import Client
from src.domain.exceptions import *
from src.repository.repository import Repository


class ClientService:
    """
    A class that manages the list of clients. You can either add, remove, update a movie or get the list of all
    clients.
    """
    def __init__(self, client_repo):
        self.__client_repo = client_repo
        self.__client_repo._data.clear()
        self.add_client(1, "Radu")
        self.add_client(2, "Gion")
        self.add_client(3, "Lupu")
        self.add_client(4, "Dorin")
        self.add_client(5, "Maria")
        self.add_client(6, "Denisa")
        self.add_client(7, "Larisa")
        self.add_client(8, "Biciclentiu")
        self.add_client(9, "Moreno")
        self.add_client(10, "Laurentiu")
        self.add_client(11, "Ana")
        self.add_client(12, "Gion Bulbasaur")
        self.add_client(13, "Robert")
        self.add_client(14, "Basescu")
        self.add_client(15, "Ponta")
        self.add_client(16, "Dragnea")
        self.add_client(17, "Gion Nedelcu")
        self.add_client(18, "Iohannis")
        self.add_client(19, "Gabriela")
        self.add_client(20, "Ghita")
        self.add_client(21, "Alexandra")

    def update(self, updated):
        """
        Updates a new client from the repository after a given id.
        :param updated:
        """
        if self.__client_repo.find_by_id(updated.id) is None:
            raise RepositoryException("There isn't any client with this id in the list.")
        self.__client_repo[updated.id] = updated

    def service_remove(self, key):
        """
        Remove a client of a given id from the list.
        :param key: the id of the client you want to remove
        """
        if self.__client_repo.find_by_id(key) is None:
            raise RepositoryException("There isn't any client with this id in the list.")
        self.__client_repo.remove(key)

    def add_client(self, client_id, name):
        """
        Adds a new client to the clients repository.
        :param client_id: the id of the client, integer
        :param name: the name of the client, string
        """
        new_client = Client(client_id, name)
        if self.__client_repo.find_by_id(client_id) is not None:
            raise RepositoryException("There is already a client with the same id in the list.")
        self.__client_repo.save(new_client)

    def service_get_all(self):
        """
         A function that creates a list of all the clients from repository.
        :return: client_list, the list of all movies
        """
        client_list = self.__client_repo.get_all()
        return client_list

    def service_get_client(self, key):
        return self.__client_repo.__getitem__(key)

    def search_client_by_id_service(self, id):
        if self.__client_repo.find_by_id(id) is None:
            raise IdException("There isn't any client with this id in the list.")
        client = self.__client_repo.__getitem__(id)
        return client

    def search_client_by_name_service(self, name):
        if not any([name.lower() in client.name.lower() for client in self.__client_repo.get_all()]):
            raise NameException("There aren't any clients with this name in the list.")
        searched_clients = list(filter(lambda x: name.lower() in x.name.lower(), self.__client_repo.get_all()))
        return searched_clients



