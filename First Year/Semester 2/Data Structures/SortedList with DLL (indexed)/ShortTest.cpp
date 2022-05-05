#include <cassert>

#include "ListIterator.h"
#include "SortedIndexedList.h"

using namespace std;

bool relation1(TComp e1, TComp e2) {
	if (e1 <= e2) {
		return true;
	}
	else {
		return false;
	}
}

bool cond(TComp e)
{
    if (e >= 5) return true;
    return false;
}

void testAll(){
	SortedIndexedList list = SortedIndexedList(relation1);
	assert(list.size() == 0);
	assert(list.isEmpty());
    list.add(1);
    assert(list.size() == 1);
    assert(!list.isEmpty());
    ListIterator iterator = list.iterator();
    assert(iterator.valid());
    iterator.first();
    assert(iterator.getCurrent() == 1);
    iterator.next();
    assert(!iterator.valid());
    iterator.first();
    assert(iterator.valid());
    assert(list.search(1) == 0);
    assert(list.remove(0) == 1);
    assert(list.size() == 0);
    assert(list.isEmpty());

    list.add(1);
    list.add(2);
    list.add(3);
    list.add(3);
    list.add(5);
    list.add(4);
    list.add(16);

    assert(list.size() == 7);
    list.filter(cond);
    assert(list.size() == 2);
    assert(list.search(5) == 0);


}

