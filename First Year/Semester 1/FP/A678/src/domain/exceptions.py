class RepositoryException(Exception):
    def __init__(self, message):
        self.__message = message

    def get_message(self):
        return self.__message


class IdException(RepositoryException):
    pass


class MovieIdException(RepositoryException):
    pass


class DateException(RepositoryException):
    pass


class RentException(RepositoryException):
    pass


class NameException(RepositoryException):
    pass


class UndoException(RepositoryException):
    pass


class IterationException(StopIteration):
    pass
