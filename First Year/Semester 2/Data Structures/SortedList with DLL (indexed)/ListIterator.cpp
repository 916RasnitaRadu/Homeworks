#include "ListIterator.h"
#include "SortedIndexedList.h"
#include <iostream>

using namespace std;

ListIterator::ListIterator(const SortedIndexedList& list) : list(list) {
	this->current_node = this->list.head;
}
// Theta(1)

void ListIterator::first(){
    this->current_node = this->list.head;
}
// Theta(1)

void ListIterator::next(){
    if (this->current_node != nullptr)
	    this->current_node = this->current_node->next;
    else {throw exception();}
}
// Theta(1)

bool ListIterator::valid() const{
    if (this->current_node != nullptr) return true;
	return false;
}
// Theta(1)

TComp ListIterator::getCurrent() const{
    if (valid())
    {
        return this->current_node->info;
    }
	return NULL_TCOMP;
}
// Theta(1)

