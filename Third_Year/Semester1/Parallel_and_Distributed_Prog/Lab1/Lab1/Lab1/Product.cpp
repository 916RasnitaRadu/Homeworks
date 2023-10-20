#include "Product.h"

Product::Product(const std::string& name, int price)
{
	this->name = name;
	this->price = price;
}

void Product::setPrice(int price)
{
	this->price = price;
}

void Product::setName(std::string name)
{
	this->name = name;
}

const int& Product::getPrice()
{
	return this->price;
}

const std::string& Product::getName()
{
	return this->name;
}
