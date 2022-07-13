#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <cmath>

int SortedBag::hash_code(int k) {
    return abs(k);
}
// Theta(1)

int SortedBag::h1(TComp e) const {
    return hash_code(e) % this->capacity;
}
// Theta(1)

int SortedBag::h2(TComp e) const {
    return 1 + (hash_code(e)%(this->capacity - 1));
}
// Theta(1)

bool SortedBag::is_prime(int x) {
    if (x < 2 || (x > 2 && x % 2 == 0)) return false;
    for (int i = 3; i * i <= x; i = i + 2)
        if (x%i == 0) return false;
    return true;
}
// Theta(sqrt(x))

int SortedBag::first_prime_greater_than(int k) {
    k++;
    while (!is_prime(k))
        k++;
    return k;
}

int SortedBag::h(TComp e, int i) const {
    return (h1(e) + i*h2(e)) % this->capacity;
}
// Theta(1)

SortedBag::SortedBag(Relation r) {
	this->rel = r;
    this->capacity = max_capacity;
    this->length = 0;
    this->empty_elem = -1111;
    this->deleted_elem = -1112;
    this->hash_table = new TComp[this->capacity];
    for (int i = 0; i < max_capacity; i++)
        this->hash_table[i] = this->empty_elem;
}
// Theta(max_cap)

void SortedBag::add(TComp e) {
	if (this->length == this->capacity)
    {
        int old_capacity = this->capacity;
        int new_cap = first_prime_greater_than(this->capacity*2);
        this->capacity = new_cap;
        TComp* old_hash = this->hash_table;
        this->hash_table = new TComp[this->capacity];
        this->length = 0;
        for (int j = 0; j < this->capacity; j++)
            this->hash_table[j] = this->empty_elem;
        for (int j = 0; j < old_capacity; j++)
            add(old_hash[j]);
        delete[] old_hash;
    }

    int i = 0;
    int pos = h(e, i);
    while (i < this->capacity && this->hash_table[pos] != this->empty_elem && this->hash_table[pos] != this->deleted_elem)
    {
        i++;
        pos = h(e,i);
    }
    this->length++;
    this->hash_table[pos] = e;
}
// Best case Theta(1)
// Worst case: Theta(new_cap + old_cap)
// Total Complexity => O(new_cap + old_cap)


bool SortedBag::remove(TComp e) {
    int i = 0;
    int pos = h(e, i);
    while (i < this->capacity && this->hash_table[pos] != this->empty_elem && this->hash_table[pos] != e)
    {
        i++;
        pos = h(e, i);
    }
    if (this->capacity == i || this->hash_table[pos] == this->empty_elem) return false;

    this->length--;
    this->hash_table[pos] = deleted_elem;
	return true;
}
// Best case: Theta(1)
// Worst case: Theta(capacity)
// Total Complexity: O(capacity)


bool SortedBag::search(TComp elem) const {
	int i = 0;
    int pos = h(elem, i);
    while (i < this->capacity && this->hash_table[pos] != this->empty_elem && this->hash_table[pos] != elem)
    {
        i++;
        pos = h(elem, i);
    }
    if (this->capacity == i || this->hash_table[pos] == this->empty_elem) return false;
	return true;
}
// Best case: Theta(1)
// Worst case: Theta(capacity)
// Total Complexity => O(capacity)

int SortedBag::nrOccurrences(TComp elem) const {
	int occurences = 0;
    int i = 0;
    int pos = h(elem, i);
    while (i < this->capacity && this->hash_table[pos] != this->empty_elem)
    {
        if (this->hash_table[pos] == elem) occurences++;
        i++;
        pos = h(elem, i);
    }
	return occurences;
}
// Theta(capacity)

int SortedBag::size() const {
	return this->length;
}
// Theta(1)

bool SortedBag::isEmpty() const {
    if (this->length == 0) return true;
	return false;
}
// Theta(1)

SortedBagIterator SortedBag::iterator() const {
	return SortedBagIterator(*this);
}
// Theta(1)

SortedBag::~SortedBag() {
	delete[] this->hash_table;
}
// Theta(1)


