#pragma once

#include "Inventory.h"
#include "Sale.h"

class InventoryChecker {
public:
	InventoryChecker(long double initVal, Inventory* inv, std::vector<Sale*> saless);

	long double initialValue;

	Inventory* inventory;

	std::vector<Sale*> sales;

	std::mutex lock;

	void inventoryCheck(long double money);
	
};