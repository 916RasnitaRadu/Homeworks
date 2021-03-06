

class ServiceException(Exception):
    def __init__(self, message):
        self.__message = message

    def get_message(self):
        return self.__message


class IdException(ServiceException):
    pass


class GroupException(ServiceException):
    pass


class UndoException(ServiceException):
    pass