#include <iostream>
#include <thread>
#include <fstream>
#include <vector>
#include <string>
#include <shared_mutex>
#include "Inventory.h"
#include "Sale.h"
#include "InventoryChecker.h"
#include <windows.h>

using namespace std::chrono_literals;
int count = 0;
const int THREAD_COUNT = 10000;
long double money = 0;
int nrBills;
std::mutex mutex;


Inventory* loadInventory() {
    auto* inventory = new Inventory();

    int currentPrice, currentQuantity;
    std::string currentName;

    std::ifstream read("products.txt");

    while (!read.eof()) {
        read >> currentName >> currentQuantity >> currentPrice;
        inventory->addProduct(new Product(currentName, currentPrice), currentQuantity);
    }

    return inventory;
}

Inventory* generateInventorySubsets(Inventory* inventory) {
    int productCount = (rand() % 1000) + 1;

    std::vector<Product*> products = inventory->getProducts();

    auto* inventorySubset = new Inventory();

    for (int i = 0; i < productCount; i++) {
        bool foundNewProduct = false;

        do {
            int productIndex = (rand() % 3000) + 1;
            Product* selectedProduct = products[productIndex];

            if (!inventorySubset->containsProduct(selectedProduct)) {
                foundNewProduct = true;
                int quantity = (rand() % 40) + 1;
                inventorySubset->addProduct(selectedProduct, quantity);
            }

        } while (!foundNewProduct);
    }

    return inventorySubset;
}

void run(Sale* sale) {
    sale->run();
    mutex.lock();
    money += sale->getProfit();
    nrBills++;
    mutex.unlock();
}

void doInventoryCheck(InventoryChecker* inventoryCheck) {
    while (true)
    {
        if (nrBills % 100 == 0) {
            std::cout << nrBills << "\n";
            Sleep(1);

            inventoryCheck->inventoryCheck(money);
        }
        if (nrBills == THREAD_COUNT) {
            break;
        }
    }
    //mutex.unlock();
}

int main()
{
    Inventory* inventory = loadInventory();

    long double totalInitialValue = inventory->computeValue();

    std::vector<Sale*> sales;
    std::thread threads[THREAD_COUNT];

    for (int i = 0; i < THREAD_COUNT; i++) {
        sales.push_back(new Sale(inventory, generateInventorySubsets(inventory)));
    }

    auto* inventoryChecker = new InventoryChecker(totalInitialValue, inventory, sales);

    std::thread timerThread(doInventoryCheck, inventoryChecker);

    for (int i = 0; i < THREAD_COUNT; i++)
    {
        threads[i] = std::thread(run, sales[i]);
    }

    for (int i = 0; i < THREAD_COUNT; i++) {
        threads[i].join();
        count++;
    }
    timerThread.join();

    Sleep(4000);

    std::cout << "All sales have been finished. Result of the final check: ";
    inventoryChecker->inventoryCheck(money);
}

