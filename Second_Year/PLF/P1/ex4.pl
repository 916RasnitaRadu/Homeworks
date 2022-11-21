% 4.a. Write a predicate to determine the difference of two sets.
% b. Write a predicate to add value 1 after every even element from a list.

% a) ==================================

% contains(E: number, L: list)
% flow model (i,i)

contains(E, [H|_]):-
    E =:= H,
    !.
contains(E, [_|T]):-
    contains(E, T).

% difference(A: list, B: list, R: list)
% flow model: (i,i,i) (i,i,o)

difference(A, [], A):-!.
difference([],_,[]).
difference([H|T], B, R):-
    contains(H, B),
    difference(T,B,R),
    !.
difference([H|T], B, [H|R]):-
    difference(T,B,R).

% b) ==================================

% insert(L: list, V: number,R: list)
% flow model: (i,i), (i,o)

insert([],[]):-!.
insert([H|T],[H|R]):-
    H mod 2 =\= 0,
    !,
    insert(T, R).
insert([H|T], [H,1|R]):-
    H mod 2 =:= 0,
    insert(T,R).
