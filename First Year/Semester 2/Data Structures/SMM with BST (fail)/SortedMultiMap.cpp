#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>
#include <vector>
#include <exception>
using namespace std;

SortedMultiMap::SortedMultiMap(Relation r) {
    this->length = 0;
    this->rel = r;
    this->capacity = 1;
    this->first_empty = 0;
    this->elements = new TElem[capacity];
    this->left = new int[capacity];
    this->right = new int[capacity];
    this->elements[0] = NULL_TELEM;
    this->left[0] = 1;
    this->right[0] = -1;
    this->root = -1;
}
// Theta(1)

void SortedMultiMap::resize() {
    int* new_left = new int[capacity * 2];
    int* new_right = new int[capacity * 2];
    auto* new_elements = new TElem[capacity* 2];
    for (int i = 0; i < capacity; i++)
    {
        new_left[i] = this->left[i];
        new_right[i] = this->right[i];
        new_elements[i] = this->elements[i];
    }
    for (int i = capacity; i <= 2 * capacity - 2; i++)
    {
        new_left[i] = i + 1;
        new_right[i] = -1;
    }
    new_left[2*capacity - 1] = -1;
    new_right[2*capacity - 1] = -1;

    delete[] elements;
    delete[] left;
    delete[] right;

    this->elements = new_elements;
    this->left = new_left;
    this->right = new_right;

    this->first_empty = this->capacity;
    this->capacity = this->capacity * 2;
}
// Theta(capacity)

int SortedMultiMap::allocate() {
    if (this->first_empty == -1) resize();
    this->length++;
    int res = first_empty;
    first_empty = this->left[first_empty];
    this->left[res] = -1;
    this->right[res] = -1;
    return res;
}

void SortedMultiMap::add(TKey c, TValue v) {
	if (this->length == 0)
    {   // adding first element
        int new_node = this->allocate();
        this->elements[new_node].first = c;
        this->elements[new_node].second = v;

        this->root = new_node;

        this->left[root] = -1;
        this->right[root] = -1;
        return;
    }
    int current_pos = this->root;
    int prev_pos = -1;
    while (current_pos != -1)
    {
        if (this->elements[current_pos].first == c) break;
        else if (this->rel(c, this->elements[current_pos].first)) {
            prev_pos = current_pos;
            current_pos = left[current_pos];
        }
        else {
            prev_pos = current_pos;
            current_pos = right[current_pos];
        }
    }

    if (current_pos != -1) { // if the key already exists
        int new_node = this->allocate();
        this->elements[new_node].first = c;
        this->elements[new_node].second = v;
        this->left[current_pos] = new_node;

        this->right[new_node] = -1;
        this->left[new_node] = -1;

    }
    else {
        // if the key doesn't exist
        int new_node = this->allocate();
        this->elements[new_node].first = c;
        this->elements[new_node].second = v;

        if (this->rel(c, elements[prev_pos].first)) left[prev_pos] = new_node;
        else right[prev_pos] = new_node;

        this->first_empty = this->left[new_node];
        left[new_node] = -1;
        right[new_node] = -1;
    }
}
// Best case: Theta(1)
// Worst case: Theta(height)
// => Total Complexity: O(height)

vector<TValue> SortedMultiMap::search(TKey c) const {
	vector<TValue> result;
    result.clear();
    if (this->length == 0)
        return result;

    int current_pos = this->root;
    while (current_pos != -1)
    {
        if (this->elements[current_pos].first == c)
            result.push_back(this->elements[current_pos].second);
        if (this->rel(c,this->elements[current_pos].first)) current_pos = left[current_pos];
        else current_pos = right[current_pos];
    }
	return result;
}
// Best case: Theta(1)
// Worst case: Theta(length) => Total complexity: O(length)


void SortedMultiMap::deallocate(int node) {
    this->left[node] = this->first_empty;
    this->elements[node] = NULL_TELEM;
    this->first_empty = node;
    this->length--;
}
// Theta(1)

bool SortedMultiMap::remove(TKey c, TValue v) {
	if (length == 0) return false;

    if (length == 1 && elements[root].second == v && elements[root].first == c)
    {
        deallocate(root);
        root = -1;
        return true;
    }
    else if (length == 1 && (this->elements[root].second != v || this->elements[root].first != c)) return false;

    int current_pos = this->root;
    int prev_pos = -1;
    while (current_pos != -1)
    {
        if (this->elements[current_pos].first == c && this->elements[current_pos].second == v)
        {
            break;
        }
        else if (this->rel(c, this->elements[current_pos].first))
        {
            prev_pos = current_pos;
            current_pos = left[current_pos];
        }
        else {
            prev_pos = current_pos;
            current_pos = right[current_pos];
        }
    }
    if (current_pos == -1) return false; // the key doesn't exist
    else { // the key exists
        if (this->left[current_pos] == -1 && this->right[current_pos] == -1)
        { // leaf
            this->deallocate(current_pos);
            if (left[prev_pos] == current_pos) left[prev_pos] = -1;
            else if (right[prev_pos == current_pos]) right[prev_pos] = -1;
            return true;
        }
        else if (left[current_pos] == -1 || right[current_pos] == -1) { // only 1 side descendant
            this->deallocate(current_pos);
            remove_node_with_one_desc(current_pos, prev_pos);
            this->right[current_pos] = -1;
            return true;
        }
        else if (left[current_pos] != -1 && right[current_pos] != -1) {
            int node_to_repl = current_pos;
            while (left[current_pos] != -1) {
                prev_pos = current_pos;
                current_pos = left[current_pos];
            }
            elements[node_to_repl].first = elements[current_pos].first;
            elements[node_to_repl].second = elements[current_pos].second;

            if (left[current_pos] == -1 && right[current_pos] == -1) {
                this->deallocate(current_pos);
                if (left[prev_pos] == current_pos) left[prev_pos] = -1;
                else if (right[prev_pos == current_pos]) right[prev_pos] = -1;
                return true;
            }
            else {
                remove_node_with_one_desc(current_pos, prev_pos);
                right[current_pos] = -1;
                this->deallocate(current_pos);
                return true;
            }
        }
    }
    return false;
}
// Best case: Theta(1)
// Worst case: Theta(height)
// => Total Complexity: O(height)

int SortedMultiMap::size() const {
	return this->length;
}
// Theta(1)

bool SortedMultiMap::isEmpty() const {
    if (this->length == 0) return true;
	return false;
}
// Theta(1)

SMMIterator SortedMultiMap::iterator() const {
	return SMMIterator(*this);
}
// Theta(1)

SortedMultiMap::~SortedMultiMap() {
//    delete[] this->elements;
//    delete[] this->left;
   // delete[] this->right;
}
// Theta(1)

void SortedMultiMap::remove_node_with_one_desc(int nodePos, int parentPos) {
    if (parentPos != -1)
    {
        if (left[parentPos] == nodePos) //node is left child
        {
            if (right[nodePos] == -1) //node has only left descendants
            {
                left[parentPos] = left[nodePos];
            }
            else if (left[nodePos] == -1) //node has only right descendants
            {
                left[parentPos] = right[nodePos];
            }
        }
        else  //node is right child
        {
            if (right[nodePos] == -1) //node has only left descendants
            {

                right[parentPos] = left[nodePos];
            }
            else if (left[nodePos] == -1) //node has only right descendants
            {
                right[parentPos] = right[nodePos];
            }
        }
    }
    else //remove root
    {
        if (left[nodePos] == -1) //root has only right descendants
        {
            root = right[nodePos];
        }
        else if (right[nodePos] == -1) //root has only right descendants
        {
            root = left[nodePos];
        }
    }
}
// Theta(1)

void SortedMultiMap::replace(TKey k, TValue old_value, TValue new_value) {
    int current_pos = root;
    while (current_pos != -1)
    {
        if (this->elements[current_pos].first == k && this->elements[current_pos].second == old_value)
        {
            this->elements[current_pos].second = new_value;
        }
        else if (this->rel(k, this->elements[current_pos].first))
        {
            current_pos = left[current_pos];
        }
        else {
            current_pos = right[current_pos];
        }
    }
}
// Best case: Theta(1)
// Worst case: Theta(height)
// => Total Complexity: O(height)