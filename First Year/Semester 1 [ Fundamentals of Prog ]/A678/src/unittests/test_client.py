import unittest

from src.domain.client import Client

class ClientTest(unittest.TestCase):
    def setUp(self) -> None:
        unittest.TestCase.setUp(self)

    def tearDown(self) -> None:
        unittest.TestCase.tearDown(self)

    def test_can_client_rent(self):
        client1 = Client(1, "Joe")
        client2 = Client(2, "Mary", False)

        self.assertTrue(client1.can_rent)
        self.assertFalse(client2.can_rent)

    def test_client(self):
        client1 = Client(1, "Joe")
        client2 = Client(2, "Mary")

        self.assertEqual(client1.id, 1)
        self.assertEqual(client1.name, "Joe")
        self.assertEqual(client2.id, 2)
        self.assertEqual(client2.name, "Mary")
        self.assertTrue(client2.can_rent, True)
        self.assertTrue(client1.can_rent, True)