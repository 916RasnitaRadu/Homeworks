#pragma once

#include "Inventory.h"
#include <mutex>

class Sale {
private:
	Inventory* inventory;
	Inventory* inventorySubset;
	long double profit;
	std::mutex lock;

	void sellProduct(Product* product);

public:
	Sale(Inventory* inv, Inventory* invSubset);

	long double getProfit() const;

	void run();
};