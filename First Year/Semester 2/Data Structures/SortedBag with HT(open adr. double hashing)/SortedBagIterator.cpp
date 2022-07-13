#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b) {
	this->index = 0;
    this->length = this->bag.size();
    this->key = 0;
    this->array = new TComp[this->length];

    for (int i = 0; i < this->bag.capacity; i++)
        if (this->bag.hash_table[i] != this->bag.empty_elem && this->bag.hash_table[i] != this->bag.deleted_elem)
            this->array[key++] = this->bag.hash_table[i];

    TComp aux;
    for (int i = 0; i < this->key - 1; i++)
        for (int j = i + 1; j < this->key; j++)
            if (!b.rel(this->array[i], this->array[j]))
            {
                aux = this->array[i];
                this->array[i] = this->array[j];
                this->array[j] = aux;
            }
}
// Theta(key^2)

TComp SortedBagIterator::getCurrent() {
    if (!valid()) throw exception();
	return this->array[this->index];
}
// Theta(1)

bool SortedBagIterator::valid() const {
    if (this->index == -1) return false;
    if (this->index < this->length) return true;

	return false;
}
// Theta(1)

void SortedBagIterator::next() {
    if (!valid()) throw exception();
    this->index++;
}
// Theta(1)

void SortedBagIterator::first() {
	this->index = 0;
}
//Theta(1)

void SortedBagIterator::previous() {
    if (!valid())
        throw exception();
    if (this->index == 0) this->index = -1;
    else this->index--;
}
// Theta(1)

