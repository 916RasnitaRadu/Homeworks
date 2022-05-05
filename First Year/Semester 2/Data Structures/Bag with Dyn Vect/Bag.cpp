#include "Bag.h"
#include "BagIterator.h"
#include <exception>
#include <iostream>
using namespace std;




Bag::Bag() = default;
//Theta(1)

void Bag::size_grow() {
    this->capacity *= 2;

    Element *tempArr = new Element[capacity];

    for (int i = 0; i < this->size_vector; i++)
        tempArr[i] = this->elements[i];

    delete[] this->elements;
    this->elements = tempArr;
}



void Bag::add(TElem elem) {
	int index = 0;
	bool found = false;

	while (index < get_size() && !found) {
		if (this->elements[index].first == elem) {
			this->elements[index].second = this->elements[index].second + 1;
			found = true;
		}
		index++;
	}
	if (!found)
	{
		Element new_elem;
		new_elem.first = elem;
		new_elem.second = 1;
		add_elem(new_elem);
	}
}
// Worst case: Theta(this->data.get_size())
// Best case: Theta(1)
// Total complexity => O(this->data.get_size())

bool Bag::remove(TElem elem) {
	int index = 0;
	while (index < get_size())
	{
		if (this->elements[index].first == elem && this->elements[index].second > 1)
		{
			this->elements[index].second--;
			return true;
		}
		else if (this->elements[index].first == elem && this->elements[index].second == 1)
		{
			delete_element(index);
			return true;
		}
		index++;
	}
	return false; 
}
// Worst case: Theta(this->data.get_size())
// Best case: Theta(1)
// Total complexity => O(this->data.get_size())


bool Bag::search(TElem elem) const {
	int index = 0;
	while (index < get_size())
	{
		if (this->elements[index].first == elem) return true;
		index++;
	}
	return false; 
}
// Worst case: Theta(this->data.get_size())
// Best case: Theta(1)
// Total complexity => O(this->data.get_size())

int Bag::nrOccurrences(TElem elem) const {
	int index = 0;
	while (index < get_size())
	{
		if (this->elements[index].first == elem) return this->elements[index].second;
		index++;
	}
	return 0; 
}
// Worst case: Theta(this->data.get_size())
// Best case: Theta(1)
// Total complexity => O(this->data.get_size())


int Bag::size() const {
	int sum = 0;
	for (int i = 0; i < get_size(); i++)
		sum += this->elements[i].second;
	return sum;
}
// Theta(this->data->get_size())

bool Bag::isEmpty() const {
	if (get_size() == 0) return true;
	return false;
}
//Theta(1)

BagIterator Bag::iterator() const {
	return BagIterator(*this);
}
//Theta(1)

void Bag::addOccurrences(int nr, TElem elem) {
    if (nr <= 0) throw exception();

    if (!search(elem))
    {
        Element new_elem;
        new_elem.first = elem;
        new_elem.second = 1;
        this->add_elem(new_elem);
        int index = this->get_size() - 1;
        this->elements[index].second += nr - 1;
    }
    else
    {
        int index = 0, found = 0;
        while (index < this->get_size() && found == 0)
        {
            if (this->elements[index].first == elem)
            {
                this->elements[index].second += nr;
                found++;
            }
            index++;
        }
    }
}
// Best case: Theta(1)
// Worst case: Theta(this->data.get_size())
// Total complexity => O(this->data.get_size())

void Bag::add_elem(Element element) {
    if (size_vector >= capacity) size_grow();

    elements[size_vector++] = element;
}
// Theta(1)

int Bag::get_size() const {
    return this->size_vector;
}
// Theta(1)

void Bag::delete_element(int pos) {
    if (pos >= this->size_vector || pos < 0) throw ("Index out of range!");

    for (int i = pos; i < this->size_vector - 1; i++)
        this->elements[i] = this->elements[i + 1];

    this->size_vector--;
}
// Theta(size_vector -  pos + 1)

Element *Bag::get_elements() const {
    return this->elements;
}
// Theta(1)

Bag::~Bag() {
    delete[] this->elements;
}
// Theta(1)
