#pragma once
#include "SortedBag.h"

class SortedBag;

class SortedBagIterator
{
	friend class SortedBag;

private:
	const SortedBag& bag;
	explicit SortedBagIterator(const SortedBag& b);

	TComp* array;
    int length, index, key;

public:
	TComp getCurrent();
	bool valid() const;
	void next();
	void first();
    void previous();
};

