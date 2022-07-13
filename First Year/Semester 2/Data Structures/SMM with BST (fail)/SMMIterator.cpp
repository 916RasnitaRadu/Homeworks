#include "SMMIterator.h"
#include "SortedMultiMap.h"

SMMIterator::SMMIterator(const SortedMultiMap& d) : map(d){
	this->current_position = this->map.root;
    if (this->map.size() == 0) {
        this->current_position = -1;
        return;
    }
    while (current_position != -1) {
        order_stack.push(current_position);
        current_position = this->map.left[current_position];
    }
    if (!order_stack.empty())
    {
        current_position = this->order_stack.top();
    }
}
// Best case: Theta(1)
// Worst case: Theta(height)
// => Total Complexity: O(height)

void SMMIterator::first(){
	if (map.size() == 0) {
        this->current_position = -1;
    }
    else {
        stack<int> empty_stack;
        this->order_stack = empty_stack; // clear the stack

        current_position = this->map.root;

        while (current_position != -1) //while current positiob is valid
            {
                order_stack.push(current_position);
                current_position = map.left[current_position];
            } // current position is the left child until no more lefts
        if (!order_stack.empty())
        {
            current_position = order_stack.top();
        }
        // now current pos is the position of the leftmost element
        //if stack is empty then current pos is -1;
    }
}
// Best case: Theta(1)
// Worst case: Theta(height)
// => Total Complexity: O(height)

void SMMIterator::next(){
	if (!valid()) throw exception();

    this->order_stack.pop();
    if (this->map.right[current_position] != -1)
    {
        current_position = this->map.right[current_position];
        while (current_position != -1)
        {
            this->order_stack.push(current_position);
            current_position = this->map.left[current_position];
        }
    }
    if (!order_stack.empty()) current_position = this->order_stack.top();
    else this->current_position = -1;
}
// Best case: Theta(1)
// Worst case: Theta(height)
// => Total Complexity: O(height)

bool SMMIterator::valid() const{
    if (this->current_position != -1) return true;
	return false;
}
// Theta(1)

TElem SMMIterator::getCurrent() const{
    if (!valid()) throw exception();

    TElem current_elem;
    current_elem = this->map.elements[current_position];

	return current_elem;
}
// Theta(1)

