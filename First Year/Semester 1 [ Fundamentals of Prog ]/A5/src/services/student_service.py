from src.domain.student import Student
from src.services.serviceexception import IdException, GroupException, UndoException
from copy import deepcopy

class StudentService:
    def __init__(self):
        self.__students = {
            1: Student(1,"Radu", 916),
            2: Student(2,"Cristi", 212),
            3: Student(3,"Marian", 913),
            4: Student(4,"Paul", 916),
            5: Student(5,"Maria", 212),
            6: Student(6,"Nelutu", 917),
            7: Student(7,"Aryana", 215),
            8: Student(8,"John", 217),
            9: Student(9,"Moreno", 212),
            10: Student(10,"Laurentiu", 916)
        }
        self.__stack = []


    def add_student(self, id, name, group):
        """
        A function that creates an object of class Student and adds it to the list of students.
        :param id: the unique id of the stud, integer
        :param name: the name of the stud, string
        :param group: the group of the stud, positive integer
        """
        student = Student(id, name, group)
        if student.id in self.__students:
            raise IdException("The already exists a student with the same id.")
        self.__stack.append(self.__students.copy())
        self.__students[student.id] = student


    def __getitem__(self, id):
        if id in self.__students:
            return self.__students[id]
        else:
            raise IdException("ID not found.")

    def filter(self, grp):
        """
        A function that creates a new list of students so that the students from a given group are no longer in the
        list.
        :param grp: an integer, the given group.
        :return:
        """
        if not any([student.group == grp for id, student in self.__students.items()]):
            raise GroupException(f"There are no students in group {grp}.")
        self.__stack.append(self.__students.copy())
        new_students = {}
      # new_students = dict(filter(lambda x: x == grp, self.__students))
        for id, student in self.__students.items():
            if student.group != grp:
                new_students[student.id] = student
        self.__students = new_students



    def get_all(self):
        return list(self.__students.values())

    def undo(self):
        """
        A function that resets the last operation.
        """
        if len(self.__stack) == 0:
            raise UndoException("There aren't any operations to undo.")
        self.__students = self.__stack[-1]
        self.__stack.pop()




def test_add_student():
    stdsv = StudentService()
    stdsv.add_student(11,"Mauricio",915)
    stdsv.add_student(12,"Lorena", 213)
    assert stdsv[12].name == "Lorena"
    assert stdsv[11].group == 915
    stdsv.add_student(13,"Andreea", 911)
    assert stdsv[13].name == "Andreea"
    assert stdsv[12].id == 12
    assert stdsv[13].group == 911


def test_flt():
    stdsv = StudentService()
    stdsv.add_student(11, "Mauricio", 916)
    stdsv.add_student(12, "Lorena", 213)
    stdsv.add_student(13, "Andreea", 911)
    stdsv.filter(916)
    studs = stdsv.get_all()
    for stud in studs:
        assert stud.group != 916



test_add_student()
test_flt()



