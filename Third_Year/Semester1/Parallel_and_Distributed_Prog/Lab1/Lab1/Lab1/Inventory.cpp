#include "Inventory.h"

Inventory::Inventory()
{
}

std::vector<Product*> Inventory::getProducts()
{
	Inventory::lock.lock();

	std::vector<Product*> keys;
	for (auto& pair : products) {
		keys.push_back(pair.first);
	}

	Inventory::lock.unlock();

	return keys;
}

bool Inventory::containsProduct(Product* product)
{
	Inventory::lock.lock();
	bool result = false;

	if (products.count(product) == 1) result = true;

	Inventory::lock.unlock();
	return result;
}

int Inventory::getQuantityOfProduct(Product* product)
{
	mutexes[product]->lock();
	int quantity = 0;

	if (products.count(product) == 1) quantity = products[product];

	mutexes[product]->unlock();
	return quantity;
}

// computes the total price of the products
long double Inventory::computeValue()
{
	Inventory::lock.lock();

	long double totalPrice = 0;
	for (auto& prod : products) {
		// first is the product and second is the quantity
		totalPrice += prod.first->getPrice() * prod.second;
	}

	Inventory::lock.unlock();
	return 0;
}

void Inventory::addProduct(Product* product, int quantity)
{
	Inventory::lock.lock();

	if (products.count(product) == 0) {
		products[product] = quantity;
		mutexes[product] = new std::mutex();
	}

	Inventory::lock.unlock();
}

void Inventory::removeProduct(Product* product, int quantity)
{
	mutexes[product]->lock();

	int prevQuantity = products[product];

	products[product] = prevQuantity - quantity;

	mutexes[product]->unlock();
}
