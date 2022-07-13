from math import inf


class PriorityQueue:
    def __init__(self):
        self._queue = []

    def __str__(self):
        return ' '.join([str(i) for i in self._queue])

    def is_empty(self):
        if len(self._queue) == 0:
            return True
        return False

    def enqueue(self, x):
        self._queue.append(x)

    def dequeue(self):
        try:
            min_val = 0
            for i in range(len(self._queue)):
                if self._queue[i][1] < self._queue[min_val][1]:
                    min_val = i
            item = self._queue[min_val]
            del self._queue[min_val]
            return item
        except IndexError:
            print()


def test_priority_queue():
    q = PriorityQueue()
    q.enqueue((2,0))
    q.enqueue((5, 1))
    q.enqueue((6, 4))
    q.enqueue((1, 3))
    q.enqueue((15, 1))
    it = q.dequeue()
    assert(it == (2, 0))
    it1 = q.dequeue()
    assert(it1 == (5, 1))
