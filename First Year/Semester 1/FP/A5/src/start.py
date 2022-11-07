from src.services.studentService import StudentService
from src.ui.UI import UI


stud_service = StudentService()
ui = UI(stud_service)
ui.ui_start()
