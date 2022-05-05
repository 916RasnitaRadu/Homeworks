#pragma once


typedef int TElem;


template<typename TElem>
class DynamicVector {
private:
    int size, capacity;
    TElem *elements;

    void size_grow();

public:
    DynamicVector(int cap);

    DynamicVector(const DynamicVector<TElem> &other);

    ~DynamicVector();

    DynamicVector<TElem> &operator=(const DynamicVector other_vect);

    void add_elem(TElem element);

    void delete_element(int pos);

    void update_element(TElem element, int pos);

    int get_size() const;

    TElem get_element(int pos) const;

    TElem * get_elements() const;

    DynamicVector operator+(const TElem v);
};


template<typename T>
DynamicVector<T>::DynamicVector(int cap) {
    if (cap <= 0) throw ("Capacity must be a positive non-zero integer.");

    this->capacity = cap;
    this->size = 0;
    this->elements = new T[capacity];
}

template<typename T>
DynamicVector<T>::DynamicVector(const DynamicVector<T> &other) {
    this->size = other.size;
    this->capacity = other.capacity;
    this->elements = new T[capacity];

    for (int i = 0; i < other.size; i++)
        this->elements[i] = other.elements[i];
}

template<typename T>
DynamicVector<T>::~DynamicVector() {
    delete[] this->elements;
}

template<typename T>
void DynamicVector<T>::size_grow() {
    this->capacity *= 2;

    T *tempArr = new T[capacity];

    for (int i = 0; i < this->size; i++)
        tempArr[i] = this->elements[i];

    delete[] this->elements;
    this->elements = tempArr;
}


template<typename T>
void DynamicVector<T>::add_elem(T element) {
    if (size >= capacity) size_grow();

    elements[size++] = element;
}

template<typename T>
void DynamicVector<T>::delete_element(int pos) {
    if (pos >= this->size || pos < 0) throw ("Index out of range!");

    for (int i = pos; i < this->size - 1; i++)
        this->elements[i] = this->elements[i + 1];

    this->size--;
}

template<typename T>
void DynamicVector<T>::update_element(T element, int pos) {
    if (pos >= this->size) throw ("Index out of range!");

    this->elements[pos] = element;
}

template<typename T>
int DynamicVector<T>::get_size() const {
    return this->size;
}

template<typename T>
T DynamicVector<T>::get_element(int pos) const {
    if (pos >= this->size || pos < 0) throw ("Index out of range.");

    return this->elements[pos];
}

template<typename T>
T * DynamicVector<T>::get_elements() const {
    return this->elements;
}


template<typename T>
DynamicVector<T> &DynamicVector<T>::operator=(const DynamicVector other_vect) {
    this->size = other_vect.size;
    this->capacity = other_vect.capacity;

    delete[] this->elements;
    this->elements = new T[other_vect.capacity];
    for (int i = 0; i < other_vect.size; i++)
        this->elements[i] = other_vect.elements[i];

    return *this;
}

template<typename T>
DynamicVector<T> DynamicVector<T>::operator+(const T v) {
    DynamicVector<T> new_vect{capacity};


    new_vect.size = this->size;

    for (int i = 0; i < this->size; i++)
        new_vect.elements[i] = this->elements[i];
    new_vect.add_elem(v);
    return new_vect;
}
