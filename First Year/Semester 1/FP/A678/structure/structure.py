from src.domain.exceptions import IterationException


class Struct:
    def __init__(self, start=0, end=0, test=None):
        # test is just a dictionary given in the case when the module is tested
        self._start = start
        if test is None:
            self._data = dict()
            self._keys = list(self._data.keys())
            self._end = end
        else:
            self._data = test
            self._keys = list(self._data.keys())
            self._end = len(self._keys) - 1

    def __getitem__(self, item):
        return self._data[item]

    def __setitem__(self, key, value):
        self._data[key] = value

    def __delitem__(self, key):
        del self._data[key]

    def __iter__(self):
        return iter(self._data)

    def __next__(self):
        if self._end == 0:
            return 0
        if self._start <= self._end:
            self._start += 1
        else:
            raise IterationException("No more values to proceed.")
        return self._data[self._keys[self._start]]

    def __len__(self):
        return len(self._data)

    def get_all(self):
        return list(self._data.values())

    def values(self):
        return self._data.values()

    def clear(self):
        self._data.clear()


def filter_dict(dict_s, func) -> dict:
    result = dict()
    for key in dict_s:
        if func(dict_s[key]):
            result[key] = dict_s[key]
    return result


def filter_list(arr, func) -> list:
    result = list()
    for elem in arr:
        if func(elem):
            result.append(elem)
    return result


def get_next_gap(gap):
    gap = (gap * 10) // 13
    if gap < 1:
        return 1
    return gap


def comb_sort(list, func):
    """
    an improvement over Bubble Sort
    Comb Sort uses a gap of size larger than one.  The gap starts with a large value and shrinks by a factor of 1.3 in
    every iteration until it reaches the value 1.
    :param list: the array that has to be sorted
    :return:
    """
    n = len(list)
    gap = n
    swapped = True

    while gap != 1 or swapped is True:
        gap = get_next_gap(gap)
        swapped = False

        for i in range(0, n - gap):
            if func(list[i], list[i + gap]):
                list[i], list[i + gap] = list[i + gap], list[i]
                swapped = True
    return list

