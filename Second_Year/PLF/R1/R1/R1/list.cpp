#include "list.h"
#include <iostream>

using namespace std;

Pnod creare_recursiva()
{
    TElem x;
    cout << "Next elem = ";
    cin >> x;
    if (x == 0) return NULL;
    else {
        Pnod p = new Nod();
        p->element = x;
        p->urm = creare_recursiva();
        return p;
    }
}

Lista Creare()
{
    Lista l;
    l.prim_nod = creare_recursiva();
    return l;
}

void sicrie_rec(Pnod p)
{
    if (p != NULL)
    {
        cout << p->element << " ";
        sicrie_rec(p->urm);
    }
}

void write_list(Lista l)
{
    sicrie_rec(l.prim_nod);
}

void destroy_rec(Pnod p) {
    if (p != NULL)
    {
        destroy_rec(p->urm);
        delete p;
    }
}

void destroy(Lista l)
{
    destroy_rec(l.prim_nod);
}

// =================================== STATEMENT A

TElem stat_a_rec(Pnod p, TElem s) {
    if (p == NULL) return s;  // daca e ultimul nod se opreste 
    else {
        if (p->element % 2 == 0) stat_a_rec(p->urm, s + p->element); // daca nr e par paseaza urmatorul nod (p->urm) si noua suma (s + p->elem)
        else stat_a_rec(p->urm, s - p->element); // daca nr e impar paseaza urmatorul nod (p->urm) si noua suma (s - p->elem)
    }
}

TElem Statement_A(Lista l)
{
    TElem rez = stat_a_rec(l.prim_nod, 0);
    return rez;
}

// =========================================== Statement B


bool cauta_recursiv_pb(Pnod pa, Pnod pb)
{
    if (pb == NULL) return true; // daca se ajunge la finalul listei inseamna ca valoare din pa nu se regaseste in a doua lista
    if (pa->element == pb->element) return false; // valoare din pa este in a doua lista => false
    else return cauta_recursiv_pb(pa, pb->urm); // altfel se trece la urmatorul nod din a doua lista
    
}

Pnod stat_rec_b(Pnod pa, Pnod pb)
{
    if (pa == NULL) return NULL; // daca prima lista e goala returneaza null
    if (pb == NULL) return pa; // daca a doua lista e goala returneaza prima lista
    else 
    {
        if (!cauta_recursiv_pb(pa, pb)) // daca valoare din nodul pa se gaseste in lista pb se trece la urmatorul
        {
            return stat_rec_b(pa->urm, pb);
        }
        else // daca nu se creeaza un nou nod pc si se trece recursiv la urmatorul nod din prima lista, dupa care se returneaza pc
        {
            Pnod pc = new Nod();    
            pc->element = pa->element;
            pc->urm = stat_rec_b(pa->urm, pb);
            return pc;
        }
    }
}


Lista Statement_B(Lista a, Lista b)
{
    Lista l;
    l.prim_nod = stat_rec_b(a.prim_nod, b.prim_nod);
    return l;
}

// =================== Liste fara citiri

Pnod creare_recursiva_fara_citire(TElem x)
{
    if (x == 10) return NULL;
    else {
        Pnod p = new Nod();
        p->element = x;
        p->urm = creare_recursiva_fara_citire(x+1);
        return p;
    }
}

Lista creare_lista_fara_citire(TElem x)
{
    Lista l;
    l.prim_nod = creare_recursiva_fara_citire(x);
    return l;
}

Pnod creare_recursiva_fara_citire1(TElem x)
{
    if (x >= 10) return NULL;
    else {
        Pnod p = new Nod();
        p->element = x;
        p->urm = creare_recursiva_fara_citire1(x + 2);
        return p;
    }
}
//2 4 6 8

Lista creare_lista_fara_citire1(TElem x)
{
    Lista l;
    l.prim_nod = creare_recursiva_fara_citire1(x);
    return l;
}

void adauga_inceput(Lista &l, TElem x)
{
    Pnod new_nod = new Nod();
    new_nod->element = x;
    new_nod->urm = l.prim_nod;
    l.prim_nod = new_nod;
}

Lista creare_lista_goala()
{
    Lista l;
    l.prim_nod = NULL;
    return l;
}



// a = 1 5 6 3 7 12 4
// b = 1 2 9 10 4

// a - b = 5 6 3 7 12