#include <cassert>

#include "SortedIteratedList.h"
#include "ListIterator.h"

#include <exception>
using namespace std;

bool relation1(TComp e1, TComp e2) {
	if (e1 <= e2) {
		return true;
	}
	else {
		return false;
	}
}

void testAll(){
	SortedIteratedList list = SortedIteratedList(relation1);
	assert(list.size() == 0);
	assert(list.isEmpty());
    list.add(1);
    assert(list.size() == 1);
    assert(!list.isEmpty());
    ListIterator it = list.search(1);
    assert(it.valid());
    assert(it.getCurrent() == 1);
    it.next();
    assert(!it.valid());
    it.first();
    assert(it.valid());
    ListIterator itFirst = list.first();
    assert(itFirst.valid());
    assert(itFirst.getCurrent() == 1);
    assert(list.remove(it) == 1);
    assert(list.size() == 0);
    assert(list.isEmpty());

    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    ListIterator itFirst2 = list.first();

    assert(itFirst2.remove() == 1);
    assert(itFirst2.getCurrent() == 2);
    itFirst2.next();
    itFirst2.next();
    assert(itFirst2.remove() == 4);
    assert(!itFirst2.valid());
}

