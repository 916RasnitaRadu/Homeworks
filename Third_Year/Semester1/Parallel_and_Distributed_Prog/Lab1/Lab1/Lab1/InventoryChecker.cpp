#include "InventoryChecker.h"
#include <iostream>

InventoryChecker::InventoryChecker(long double initVal, Inventory* inv, std::vector<Sale*> saless)
{
	this->initialValue = initVal;
	this->inventory = inv;
	this->sales = saless;
}

void InventoryChecker::inventoryCheck(long double money)
{
	InventoryChecker::lock.lock();

	long double totalSumSale = 0;
	for (auto& sale : sales) {
		totalSumSale += sale->getProfit();
	}

	if (money >= totalSumSale) {
		std::cout << "Inventory is ok!\n";
	}
	else {
		std::cout << "Inventory is not ok! :( \n";
	}

	InventoryChecker::lock.unlock();
}
