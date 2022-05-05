#include "ListIterator.h"
#include "SortedIteratedList.h"
#include <iostream>
using namespace std;
#include <exception>

SortedIteratedList::SortedIteratedList(Relation r) {
	this->rel = r;
    this->capacity = 1;
    this->length = 0;
    this->first_empty = 0;
    this->head = -1;
    this->tail = -1;
    this->elems = new TComp[capacity];
    this->next = new int[1];
    this->prev = new int[1];
    this->elems[0] = NULL_TCOMP;
    this->next[0] = -1;
    this->prev[0] = -1;
}
// Theta(1)

void SortedIteratedList::resize() {
    auto* new_elems = new TComp[capacity*2];
    int* new_next = new int[capacity*2];
    int* new_prev = new int[capacity*2];
    for (int i = 0; i < this->capacity; i++)
    {
        new_elems[i] = this->elems[i];
        new_next[i] = this->next[i];
        new_prev[i] = this->prev[i];
    }

    for (int i = this->capacity; i < this->capacity*2; i++)
    {
        new_next[i] = first_empty;
        if (first_empty != -1)
        {
            new_prev[first_empty] = i;
        }
        new_prev[i] = -1;
        new_elems[i] = NULL_TCOMP;
        this->first_empty = i;
    }

    delete[] this->elems;
    delete[] this->next;
    delete[] this->prev;
    this->capacity = this->capacity*2;
    this->elems = new_elems;
    this->next = new_next;
    this->prev = new_prev;
}
// Theta(capacity)

int SortedIteratedList::allocate() {
    if (this->first_empty == -1) resize();
    this->length++;
    int res = this->first_empty;
    first_empty = next[first_empty];
    this->prev[res] = -1;
    this->next[res] = -1;
    return res;
}
// Theta(1)

void SortedIteratedList::deallocate(int node) {
    this->next[node] = this->first_empty;
    this->elems[node] = NULL_TCOMP;
    this->first_empty = node;
    this->length--;
}
// Theta(1)

int SortedIteratedList::size() const {
	return this->length;
}
// Theta(1)

bool SortedIteratedList::isEmpty() const {
    if (this->length == 0) return true;
	return false;
}
// Theta(1)

ListIterator SortedIteratedList::first()
{
    return ListIterator(*this);
}
// Theta(1)

TComp SortedIteratedList::getElement(ListIterator poz) const {
    if (!poz.valid()) throw exception();
    return poz.getCurrent();
}
// Theta(1)

TComp SortedIteratedList::remove(ListIterator& poz) {
	if (!poz.valid()) throw exception();
    TComp result;

    if (this->head == this->tail)
    {
        result = this->elems[this->head];
        this->deallocate(this->head);
        this->head = this->tail = -1;
        return result;
    }
    if (poz.current == this->head)
    {
        result = this->elems[this->head];
        int node = this->head;
        this->head = next[this->head];
        poz.current = this->head;
        this->deallocate(node);
        return result;
    }
    else if (poz.current == this->tail)
    {
        result = this->elems[this->tail];
        int node = this->tail;
        this->tail = prev[this->tail];
        poz.current = -1;
        this->deallocate(node);
        return result;
    }
    for (int node = this->head; node != -1; node = next[node])
    {
        if (next[node] == poz.current)
        {
            result = this->elems[next[node]];
            next[node] = next[next[node]];
            prev[next[node]] = node;
            this->deallocate(poz.current);
            poz.current = next[node];
            return result;
        }
    }
}
// Best case: Theta(1)
// Worst case: Theta(length)
// Total Complexity => O(length)

ListIterator SortedIteratedList::search(TComp e){
	ListIterator current(*this);
    while (current.valid())
    {
        if (current.getCurrent() == e) return current;
        current.next();
    }
    return current;
}
// Best case: Theta(1)
// Worst case: Theta(length)
// => Total Complexity: O(length)

void SortedIteratedList::add(TComp e) {
    if (this->length == 0)
    {
        int new_node = this->allocate();
        this->head = new_node;
        this->tail = new_node;
        this->elems[new_node] = e;
    }
    else
    {
        ListIterator current(*this);
        bool ok = true;
        while (current.valid() && ok)
        {
            if (!rel(current.getCurrent(), e)) ok = false;
            else current.next();
        }

        if (current.current == this->head)
        {
            int new_node = this->allocate();
            next[new_node] = this->head;
            prev[new_node] = -1;
            prev[this->head] = new_node;
            this->head = new_node;
            this->elems[new_node] = e;
        }
        else if (current.current == -1)
        {
            int new_node = this->allocate();
            this->elems[new_node] = e;
            prev[new_node] = this->tail;
            next[new_node] = -1;
            next[this->tail] = new_node;
            this->tail = new_node;
        }
        else {
            int new_node = this->allocate();
            this->elems[new_node] = e;
            next[new_node] = current.current;
            prev[new_node] = prev[current.current];
            prev[current.current] = new_node;
            next[prev[new_node]] = new_node;
        }
    }
}
// Best case: Theta(1)
// Worst case: Theta(length)
// Total Complexity => O(length)

SortedIteratedList::~SortedIteratedList() {
	delete[] this->elems;
    delete[] this->next;
    delete[] this->prev;
}
// Theta(1)

