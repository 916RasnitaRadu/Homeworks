class Student():
    """
    - id (integer, unique), a name (string) and a group (positive integer)
    """
    def __init__(self, _id, name, group):
        """
        Creates a new student
        :param id: the unique id of the stud, integer
        :param name: the name of the stud, string
        :param group: the group of the stud, positive integer
        """
        self.__id = _id
        self.__name = name
        self.__group = group

    @property
    def id(self):
        return self.__id

    @property
    def name(self):
        return self.__name

    @property
    def group(self):
        return self.__group



def test_student():
    stud1 = Student(1,"Jim",912)
    stud2 = Student(2,"Biciclentiu", 215)
    assert stud1.id == 1
    assert stud1.name == "Jim"
    assert stud1.group == 912
    assert stud2.id == 2
    assert stud2.name == "Biciclentiu"
    assert stud2.group == 215

test_student()


