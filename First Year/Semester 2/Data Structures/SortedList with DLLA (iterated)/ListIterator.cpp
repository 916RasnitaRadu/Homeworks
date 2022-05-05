#include "ListIterator.h"
#include "SortedIteratedList.h"
#include <exception>

using namespace std;

ListIterator::ListIterator(SortedIteratedList& list) : list(list){
	this->current = this->list.head;
}
// Theta(1)

void ListIterator::first(){
	this->current = this->list.head;
}
// Theta(1)

bool ListIterator::valid() const{
    if (this->current != -1) return true;
    return false;
}
// Theta(1)

void ListIterator::next(){
	if (!valid())
        throw exception();
    this->current = this->list.next[this->current];
}
// Theta(1)

TComp ListIterator::getCurrent() const{
    if (!valid()) throw exception();
    return this->list.elems[this->current];
}
// Theta(1)

TComp ListIterator::remove() {
    if (!valid()) throw exception();

    TComp result = NULL_TCOMP;

    result = this->list.remove(*this);

    return result;
}
// Theta(1)

