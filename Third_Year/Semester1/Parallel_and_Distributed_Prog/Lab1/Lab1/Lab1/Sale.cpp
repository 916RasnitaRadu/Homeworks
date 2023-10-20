#include "Sale.h"

void Sale::sellProduct(Product* product)
{
	// the products are sold and the earnings are added to the profit
	int quantity = this->inventory->getQuantityOfProduct(product);
	this->inventory->removeProduct(product, quantity);
	this->profit += product->getPrice() * quantity;
}

Sale::Sale(Inventory* inv, Inventory* invSubset)
{
	this->inventory = inv;
	this->inventorySubset = invSubset;
	this->profit = 0;
}

long double Sale::getProfit() const
{
	return this->profit;
}

// each sale decreases the amounts of available products in a subset of the invetory
void Sale::run()
{
	for (const auto& item : this->inventorySubset->getProducts()) {
		sellProduct(item);
	}
}
