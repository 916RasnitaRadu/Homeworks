import sys


class recursion_depth:
    def __init__(self, limit):
        self.limit = limit
        self.default_limit = sys.getrecursionlimit()

    def __enter__(self):
        sys.setrecursionlimit(self.limit)

    def __exit__(self, exc_type, exc_val, exc_tb):
        sys.setrecursionlimit(self.default_limit)
