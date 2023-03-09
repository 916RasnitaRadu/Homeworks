% 2.a. Write a predicate to remove all occurrences of a certain atom from a list.
% b.Define a predicate to produce a list of pairs (atom n) from an initial list of atoms. In this initial list atom has n occurrences.
% Eg.:numberatom([1, 2, 1, 2, 1, 3, 1], X) => X =[[1, 4], [2, 2], [3, 1]].

% a) ==========

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

% b) =============

% makePair(A: number, B: number, L: list)
% flow model (i,i, o) (i,i,i)

makePair(_,_,[]).
makePair(A,B,[A,B]).

% count_occurences(L:list, E:number, R:number)
% count_occurences(i, i, o)

count_occurences([], _, 0):-!.
count_occurences([H|T], E, R) :- H =:= E,
    !,
    count_occurences(T, E, R1),
    R is R1 + 1.
count_occurences([H|T], E, R) :- H =\= E,!,
    count_occurences(T, E, R).

% solution(L: list, R: list)

solution([],[]):-!.
solution([H|T], [P|R]):-
    count_occurences([H|T], H, RC),
    RC =:= 1,
    !,
    makePair(H,1,P),
    solution(T,R).
solution([H|T], [P|R]):-
    count_occurences([H|T], H, RC),
    RC > 1,
    !,
    makePair(H,RC,P),
    remove(H,T,RO),
    solution(RO,R).
