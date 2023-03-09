#include <iostream>
#include <assert.h>
#include "list.h"

using namespace std;

void test_statement_a()
{
	Lista l1 = creare_lista_fara_citire(4); 
	Lista l2 = creare_lista_fara_citire1(2);
	Lista l3 = creare_lista_fara_citire1(1);
	Lista l4 = creare_lista_goala();

	assert(Statement_A(l1) == -3);
	assert(Statement_A(l2) == 20);
	assert(Statement_A(l3) == -25);
	assert(Statement_A(l4) == 0);

	cout << "Merge BOSS!\n";
}

void test_statement_b()
{
	Lista l1 = creare_lista_goala();
	Lista l2 = creare_lista_goala();

	Lista a = Statement_B(l1, l2);

	assert(a.prim_nod == NULL);

	adauga_inceput(l1, 1);
	adauga_inceput(l1, 3);
	adauga_inceput(l1, 5);
	adauga_inceput(l1, 6);
	adauga_inceput(l1, 7);
	adauga_inceput(l1, 12);// 12 7 6 5 3 1

	adauga_inceput(l2, 1);
	adauga_inceput(l2, 3);
	adauga_inceput(l2, 2);
	adauga_inceput(l2, 6);
	adauga_inceput(l2, 12);


	Lista l3 = creare_lista_goala();

	adauga_inceput(l3, 5);
	adauga_inceput(l3, 7);
	
	Lista l4 = Statement_B(l1, l2);

	cout << l4.prim_nod->element << "\n";

	Pnod p = l3.prim_nod;
	Pnod q = l4.prim_nod;

	while (p != NULL)
	{
		assert(p->element == q->element);
		p = p->urm;
		q = q->urm;
	}

	cout << "Merge si asta!!\n";
}

int main()
{
	test_statement_a();
	test_statement_b();
	return 0;
}