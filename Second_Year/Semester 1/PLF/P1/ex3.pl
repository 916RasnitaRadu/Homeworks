% a. Define a predicate to remove from a list all repetitive elements. Eg.: l=[1,2,1,4,1,3,4] => l=[2,3])
% b. Remove all occurrence ofa maximum value from a list on integer numbers.

% a) =======

% remove(E: number, L: list, R: list)
% flow model: (i,i,o) (i,i,i)

remove(_, [], []).
remove(E, [E], []):-!.
remove(E, [H], [H]):- \+ H =:= E.
remove(E, [H|T], R):-
    H =:= E,
    remove(E, T, R).
remove(E, [H|T], [H|R]):-
    \+ H =:= E,
    remove(E, T, R).

contains(E, [E]).
contains(E, [H|_]):-
    E =:= H,
    !.
contains(E, [H|T]):-
    \+ E =:= H,
    contains(E, T).

% removeRepetitive(L: list, R: list)
% flow model: (i,o) (i,i)

removeRepetitive([], []).
removeRepetitive([H|T], R):-
    contains(H, T),
    !,
    remove(H,[H|T],RO),
    removeRepetitive(RO, R).
removeRepetitive([H|T],[H|R]):-
    \+ contains(H,T),
    removeRepetitive(T,R).

% b) =========

% find_max(L: list, R: number)
% flow model(i,o) (i,i)

maxim_number(A,B,A):- A >= B.
maxim_number(A,B,B):- A < B.

find_max([H], H):-!.
find_max([H|T], R):-
    find_max(T, RM),
    maxim_number(H, RM, R).

remove_maxim(L, Rez):-
    find_max(L, R),
    remove(R, L, Rez).