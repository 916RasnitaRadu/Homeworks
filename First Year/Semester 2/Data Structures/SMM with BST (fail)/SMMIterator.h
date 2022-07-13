#pragma once

#include "SortedMultiMap.h"
#include <stack>

using std::stack;

class SMMIterator{
	friend class SortedMultiMap;
private:
	//DO NOT CHANGE THIS PART
	const SortedMultiMap& map;
	SMMIterator(const SortedMultiMap& map);

    int current_position;
    stack<int> order_stack;

public:
	void first();
	void next();
	bool valid() const;
   	TElem getCurrent() const;
};













