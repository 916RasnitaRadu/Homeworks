class Client:
    def __init__(self, _client_id, name, can_rent = None):
        """
        Creates a new client
        :param client_id: the id of the client, integer
        :param name: the name of the client, string
        """
        self.__client_id = _client_id
        self.__name = name
        if can_rent is None:
            self.__can_rent = True
        else:
            self.__can_rent = can_rent


    @property
    def id(self):
        return self.__client_id

    @property
    def name(self):
        return self.__name

    @property
    def can_rent(self):
        return self.__can_rent

    @staticmethod
    def line_to_entity(line):
        words = line.split(',')
        return Client(int(words[0]), words[1])

    @staticmethod
    def entity_to_line(client):
        return f"{client.id}, {client.name}"


