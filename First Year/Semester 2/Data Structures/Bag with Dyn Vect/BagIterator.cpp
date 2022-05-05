#include <exception>
#include "BagIterator.h"
#include "Bag.h"

using namespace std;


BagIterator::BagIterator(const Bag& c): bag(c)
{
	this->index = 0;
    this->index_vector = 0;
    this->index_frequency = 1;
}
// Theta(1)

void BagIterator::first() {
    this->index = 0;
    this->index_vector = 0;
    this->index_frequency = 1;
}
// Theta(1)

void BagIterator::next() {
    if (this->index >= this->bag.size()) {
        throw exception();
    }
    this->index_frequency++;
    if (this->index_frequency > this->bag.get_elements()[this->index_vector].second)
    {
        this->index++;
        this->index_vector++;
        this->index_frequency = 1;
    }
    else this->index++;
}// Theta(1)


bool BagIterator::valid() const {
	if (this->index < this->bag.size()) return true;
	return false;
}
// Theta(1)


TElem BagIterator::getCurrent() const
{
    if (!valid()) { throw exception(); }
	return this->bag.get_elements()[this->index_vector].first;
}
// Theta(1)
