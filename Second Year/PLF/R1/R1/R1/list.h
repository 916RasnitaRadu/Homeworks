#pragma once

typedef int TElem;  // generic data type

struct Nod; //  node structure

typedef Nod* Pnod; // adress of a node

typedef struct Nod {
	TElem element;
	Pnod urm;
}; // structure of a node

typedef struct {
	Pnod prim_nod;
}Lista; // structure of a SLL

// list operations

Lista Creare();

void write_list(Lista l);

void destroy(Lista l);

TElem Statement_A(Lista l); // a.Determine the number formed by adding all even elements and subtracting all odd numbers of the list.

Lista Statement_B(Lista a, Lista b); // b. Determine difference of two sets represented as lists.

Lista creare_lista_fara_citire(TElem x);

Lista creare_lista_fara_citire1(TElem x);

void adauga_inceput(Lista& l, TElem x);

Lista creare_lista_goala();