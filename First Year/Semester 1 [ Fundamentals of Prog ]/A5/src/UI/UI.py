from src.services.serviceexception import IdException, ServiceException
from src.services.studentService import StudentService

class UI:
    def __init__(self, studentService):
        self.__stud_service = studentService

    def ui_add_student(self):
        """
        A function that gets the id, the name and the group of a student from the user.
        """
        try:
            id = int(input("Enter the id: "))
            name = input("Enter the name of the student: ")
            group = int(input("Enter the group: "))
            if group < 0 or id < 0:
                raise ValueError
            self.__stud_service.add_student(id, name, group)
        except ValueError:
            print("The group and the id must be a positive integer.")

    def ui_filter(self):
        try:
            gr = int(input("Enter the group that you want to be filtered: "))
            if gr < 0:
                raise ValueError
            self.__stud_service.filter(gr)
        except ValueError:
            print("The group must be a positive integer.")

    @staticmethod
    def ui_print_menu():
        print("\n1. Add a student")
        print("2. Display the list of students.")
        print("3. Filter the list of the students by group.")
        print("4. Undo the last operation.")
        print("5. Exit the program.\n")

    def ui_display_students(self):
        students = self.__stud_service.get_all()
        print("The list of students is: ")
        for stud in students:
            print(str(stud.id) + " " + stud.name + ", group: " + str(stud.group))


    def ui_start(self):

        while True:
            self.ui_print_menu()
            try:
                n = int(input("Select what you want to do next: "))
                if n <= 0 or n > 5:
                    raise ValueError
                if n == 1:
                    self.ui_add_student()
                elif n == 2:
                    self.ui_display_students()
                elif n == 3:
                    self.ui_filter()
                elif n == 4:
                    self.__stud_service.undo()
                elif n == 5:
                    return
            except ValueError as ve:
                print("Sorry, the input must be an integer between 1 and 5.")
            except ServiceException as se:
                print(se.get_message())
