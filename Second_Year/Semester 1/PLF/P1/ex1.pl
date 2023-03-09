% 1.a. Write a predicate to determine the lowest common multiple of a list formed from integer numbers.
% b. Write a predicate to add a value v after 1-st, 2-nd, 4-th, 8-th, ... element in a list.2.

% a) ==========================
% gcd(A - integer, B - integer, X - gcd)

% flow model: (i,i,o)

gcd(X, 0, X):-!. % if an operand is 0 the function returns the non zero one
gcd(0, X, X):-!.
gcd(X, X, X):-!. % if the operands are the same it returns it
gcd(X, Y, Z):-
    X > Y,
    M is X-Y,
    gcd(M, Y, Z).
gcd(X, Y, Z):-
    X < Y,
    M is Y-X,
    gcd(X, M, Z).

% lcm(A: integer, B: integer, X - lcm)

lcm(A, B, R):-
    gcd(A,B,X),
    R is A * B / X.

% b) ===========================

% Model matematic:
% insert_pow(l1...ln, v, pos, index) =
%	[], n = 0
%	l1 + v + insert_pow(l2...ln, v, pos * 2, index + 1), index = pos
%	l1 + insert_pow(l2...ln, v, pos, index + 1), pos != index

% insert_pow(L:list, V:number, POS:number, INDEX:number, R:list)
% insert_pow(i, i, i, i, o)

insert_pow([], _, _, _, []).
insert_pow([H|T], V, POS, INDEX, [H, V | R]):-
    INDEX =:= POS,
    !,
    POS1 is POS * 2,
    INDEX1 is INDEX + 1,
    insert_pow(T,V,POS1,INDEX1,R).
insert_pow([H|T],V,POS,INDEX, [H|R]):-
    POS =\= INDEX,
    INDEX1 is INDEX + 1,
    insert_pow(T, V, POS, INDEX1, R).

insert(L,V,R):-
    insert_pow(L,V,1,1,R).