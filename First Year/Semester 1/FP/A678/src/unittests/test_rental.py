import unittest
from datetime import datetime
from src.domain.rental import Rental

class RentalTest(unittest.TestCase):
    def setUp(self) -> None:
        unittest.TestCase.setUp(self)

    def tearDown(self) -> None:
        unittest.TestCase.tearDown(self)

    def test_rental(self):
        rnt1 = Rental(1, 2, 6, "01.05.2019", "1.05.2020", "06.04.2019")

        self.assertEqual(rnt1.id, 1)
        self.assertEqual(rnt1.movie_id, 2)
        self.assertEqual(rnt1.client_id, 6)
        self.assertEqual(rnt1.rented_date, "01.05.2019")
        self.assertEqual(rnt1.due_date, "1.05.2020")
        self.assertEqual(rnt1.returned_date, "06.04.2019")

        rent_date = datetime.strptime('16/03/2019', '%d/%m/%Y')
        due_date = datetime.strptime('17/05/2019', '%d/%m/%Y')
        returned_date = datetime.strptime('12/02/2019', '%d/%m/%Y')
        rnt2 = Rental(2, 7, 10, rent_date, due_date, returned_date)

        self.assertEqual(rnt2.id, 2)
        self.assertEqual(rnt2.movie_id, 7)
        self.assertEqual(rnt2.client_id, 10)
        self.assertEqual(rnt2.rented_date, rent_date)
        self.assertEqual(rnt2.due_date, due_date)
        self.assertEqual(rnt2.returned_date, returned_date)