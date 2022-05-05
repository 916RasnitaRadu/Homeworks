#include "ListIterator.h"
#include "SortedIndexedList.h"
#include <iostream>
using namespace std;
#include <exception>

SortedIndexedList::SortedIndexedList(Relation r) {
	this->length = 0;
    this->rel = r;
    this->head = nullptr;
    this->tail = this->head;
}
// Theta(1)

int SortedIndexedList::size() const {
	return this->length;
}
// Theta(1)

bool SortedIndexedList::isEmpty() const {
	if (this->length == 0) return true;
	return false;
}
// Theta(1)

TComp SortedIndexedList::getElement(int i) const{
	int pos = 0;
    Node* search_node = this->head;
    while (search_node != nullptr)
    {
        if (pos == i) return search_node->info;
        else {
            search_node = search_node->next;
            pos++;
        }
    }
	throw exception();
}
// Best case: Theta(1)
// Worst case: Theta(number of nodes)
// => Total complexity: O(number of nodes)


TComp SortedIndexedList::remove(int i) {
	if (i >= this->length || i < 0 || this->length == 0)
        throw exception();
    else
    {
        Node *current_node = this->head;
        for (int pos = 0; pos < i; pos++)
            current_node = current_node->next;

        TComp result = current_node->info;
        this->length--;

        if (current_node == this->head) this->head = current_node->next;
        if (current_node->next != nullptr) current_node->next->prev = current_node->prev;
        if (current_node->prev != nullptr) current_node->prev->next = current_node->next;

        delete current_node;

        return result;
    }
}
// Best case: Theta(1)
// Worst case: Theta(number of nodes)
// => Total complexity: O(number of nodes)



int SortedIndexedList::search(TComp e) const {
	Node* search_node = this->head;
    int pos = 0;
    while (search_node != nullptr)
    {
        if (search_node->info == e) return pos;
        else{
            pos++;
            search_node = search_node->next;
        }
    }
	return -1;
}
// Best case: Theta(1)
// Worst case: Theta(number of nodes)
// => Total complexity: O(number of nodes)

void SortedIndexedList::add(TComp e) {
	if (this->length == 0)
    {
        Node* new_node = new Node();
        new_node->info = e;
        new_node->next = nullptr;
        new_node->prev = nullptr;
        this->head = new_node;
        this->tail = new_node;
        this->length++;
    }
    else
    {
        Node* current_node = this->head;
        while (current_node != nullptr && this->rel(current_node->info, e))
        {
            current_node = current_node->next;
        }
        if (current_node == this->head && current_node != nullptr)
        { // adding it to the beginning
            Node* new_node = new Node();
            new_node->info = e;
            new_node->prev = nullptr;
            new_node->next = this->head;
            this->head->prev = new_node;
            this->head = new_node;
            this->length++;
        }
        else if (current_node == nullptr)
        { // adding it to the end
            Node* new_node = new Node();
            new_node->info = e;
            new_node->next = nullptr;
            new_node->prev = this->tail;
            this->tail->next = new_node;
            this->tail = new_node;
            this->length++;
        }
        else { // adding it inside
            Node* help_node = current_node->prev;
            Node* new_node = new Node();
            new_node->info = e;
            new_node->next = current_node;
            current_node->prev = new_node;
            new_node->prev = help_node;
            help_node->next = new_node;
            this->length++;
        }
    }

}
// Best case: Theta(1)
// Worst case: Theta(number of nodes)
// => Total complexity: O(number of nodes)

void SortedIndexedList::filter(Condition condition) {
    Node* current_node = this->head;
    int pos;
    while (current_node != nullptr)
    {
        if (!condition(current_node->info))
        {
            pos = this->search(current_node->info);
            current_node = current_node->next;
            this->remove(pos);

        }
        else current_node = current_node->next;
    }
}
// Theta(n)


ListIterator SortedIndexedList::iterator(){
	return ListIterator(*this);
}
// Theta(1)

//destructor
SortedIndexedList::~SortedIndexedList() {
	Node* prev_node = nullptr;
    Node* current_node = this->head;
    while (current_node != nullptr)
    {
        prev_node = current_node;
        current_node = current_node->next;
        delete prev_node;
    }
}
// Theta(1)