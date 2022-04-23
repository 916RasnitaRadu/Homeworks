import unittest

from structure.structure import *


class TestStruct(unittest.TestCase):
    def setUp(self) -> None:
        test = {
            "USA": 328.2,
            "France": 67,
            "Japan": 125.8,
            "China": 1393,
            "Peru": 32.97,
            "Romania": 19.27
        }
        start, end = 0, 0
        self._structure_test = Struct(start, end, test)

    def tearDown(self) -> None:
        pass

    def test_get_item(self):
        self.assertEqual(self._structure_test.__getitem__("USA"), 328.2)
        self.assertEqual(self._structure_test.__getitem__("France"), 67)
        self.assertEqual(self._structure_test.__getitem__("Japan"), 125.8)
        self.assertEqual(self._structure_test.__getitem__("China"), 1393)
        self.assertEqual(self._structure_test.__getitem__("Peru"), 32.97)
        self.assertEqual(self._structure_test.__getitem__("Romania"), 19.27)

    def test_set_item(self):
        self._structure_test.__setitem__("France", 68)

        self.assertEqual(self._structure_test.__getitem__("France"), 68)

    def test_del_item(self):
        self._structure_test.__delitem__("USA")

        with self.assertRaises(KeyError):
            country = self._structure_test.__getitem__("USA")

    def test_len(self):
        self.assertEqual(self._structure_test.__len__(), 6)
        self._structure_test.__delitem__("USA")
        self.assertEqual(self._structure_test.__len__(), 5)

    def test_iter(self):
        strct = self._structure_test.__iter__()
        for i in strct:
            pass

    def test_next(self):
        self.assertEqual(self._structure_test.__next__(), 67)
        self.assertEqual(self._structure_test.__next__(), 125.8)
        self.assertEqual(self._structure_test.__next__(), 1393)
        self.assertEqual(self._structure_test.__next__(), 32.97)
        self.assertEqual(self._structure_test.__next__(), 19.27)
        with self.assertRaises(IndexError):
            nr = self._structure_test.__next__()

    def test_get_all(self):
        lista_aia_blana = self._structure_test.get_all()
        self.assertEqual(lista_aia_blana, [328.2, 67, 125.8, 1393, 32.97, 19.27])

    def test_filter_list(self):
        lista_struct = self._structure_test.get_all()
        lista_struct = filter_list(lista_struct, lambda x: x < 300)
        self.assertEqual(lista_struct, [67, 125.8, 32.97, 19.27])

    def test_filter_dict(self):
        new_dict = filter_dict(self._structure_test, lambda x: x >= 70)
        self.assertEqual(new_dict, {"USA": 328.2, "Japan": 125.8, "China": 1393})

    def test_sort_comb(self):
        lista_struct = self._structure_test.get_all()
        lista_struct = comb_sort(lista_struct, lambda x, y: x >= y)
        self.assertEqual(lista_struct, [19.27, 32.97, 67, 125.8, 328.2, 1393])

    def test_get_next_gap(self):
        gap = 10
        gap = get_next_gap(gap)
        self.assertEqual(gap, 7)

    def test_clear(self):
        self._structure_test.clear()
        with self.assertRaises(KeyError):
            elem = self._structure_test.__getitem__("USA")

