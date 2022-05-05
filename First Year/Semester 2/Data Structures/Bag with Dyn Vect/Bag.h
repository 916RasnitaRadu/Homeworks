#pragma once
//DO NOT INCLUDE BAGITERATOR

#include <utility>


//DO NOT CHANGE THIS PART
#define NULL_TELEM -111111;
typedef int TElem;

class BagIterator;

typedef int Frequency;
typedef std::pair<TElem, Frequency> Element;

class Bag {

private:
    int size_vector = 0, capacity = 100;
    Element *elements = new Element[capacity];

    void size_grow();
    void add_elem(Element element);
    void delete_element(int pos);
    int get_size() const;
    Element* get_elements() const;

    //DO NOT CHANGE THIS PART
    friend class BagIterator;

public:
    //constructor
    Bag();

    //adds an element to the bag
    void add(TElem e);

    //removes one occurence of an element from a bag
    //returns true if an element was removed, false otherwise (if e was not part of the bag)
    bool remove(TElem e);

    //checks if an element appearch is the bag
    bool search(TElem e) const;

    //returns the number of occurrences for an element in the bag
    int nrOccurrences(TElem e) const;

    // adds nro ccurrences of elem in the Bag(if elem was not in the bag, it will still be added).
    // throws an exception if nr is negative
    void addOccurrences(int nr,TElem elem);

    //returns the number of elements from the bag
    int size() const;

    //returns an iterator for this bag
    BagIterator iterator() const;

    //checks if the bag is empty
    bool isEmpty() const;

    //destructor
    ~Bag();
};