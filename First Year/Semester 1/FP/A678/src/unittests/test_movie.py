import unittest

from src.domain.movies import Movie

class MovieTest(unittest.TestCase):
    def setUp(self) -> None:
        unittest.TestCase.setUp(self)

    def tearDown(self) -> None:
        unittest.TestCase.tearDown(self)
        
    def test_movie(self):
        mov1 = Movie(1, "Inception", "dreams and stuff", "Action/SF")
        mov2 = Movie(2, "1917", "WW1", "Action/History")

        self.assertEqual(mov1.id, 1)
        self.assertEqual(mov1.title, "Inception")
        self.assertEqual(mov1.description, "dreams and stuff")
        self.assertEqual(mov1.genre, "Action/SF")
        self.assertEqual(mov2.id, 2)
        self.assertEqual(mov2.title, "1917")
        self.assertEqual(mov2.description, "WW1")
        self.assertEqual(mov2.genre, "Action/History")