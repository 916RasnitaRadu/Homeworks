% Sort a list eliminating duplicates.
% [4 2 6 2 3 4] -> [2 3 4 6]
% [5 4 3 2 1] -> [1 2 3 4 5]
% [1 2 4 2 1] -> [1 2 4]
% [6 1 1 2 2 3 3 4 5 5 3 1] ->[1 2 3 4 5 6]

% diffAbs(A: number, B: number, R: number)
% flow model: (i, i, o) (i,i,i)

diffAbs(A, B, R):-
    A < B,
    R is B-A.
diffAbs(A, B, R):-
    A >= B,
    R is A-B.

% length(L: list, C: number, R: number)
% flow model: (i,i,i) (i,i,o)

length([],C,C).
length([_|T], C, R):-
    C1 is C + 1,
    length(T, C1, R).

% merge_sort(L: list, R: list)
% flow model: (i,i) (i,o)

merge_sort([], []).
merge_sort([E], [E]).
merge_sort(L, R):-
    split(L, Left, Right),
    merge_sort(Left, RL),
    merge_sort(Right, RR),
    mergeAux(RL, RR, R).

% mergeAux(L:list, R:list, R:list)
% flow model: (i, i, o) (i,i,i)

mergeAux(L, [], L).
mergeAux([], R, R).
mergeAux([HL|TL], [HR|TR], [HL|R]) :- HL =< HR,
    mergeAux(TL, [HR|TR], R).
mergeAux([HL|TL], [HR|TR], [HR|R]) :- HL > HR,
    mergeAux([HL|TL], TR, R).

% split(L: List, Left: list, Right: list)
% flow model: (i, i, i) (i, o, o)

split(L, Left, Right):-
    splitAux(L, [], Left, Right).

% splitAux(L: list, LC: list, Left: List, Right: List)

splitAux(L, LC, LC, L):-
    length(L, 0, RL),
    length(LC, 0, RLC),
    diffAbs(RL, RLC, AUX),
    AUX =< 1.
splitAux([H|T], LC, Left, Right):-
    my_append(LC, H, RA),
    splitAux(T, RA, Left, Right).

% my_append(L:list, E:number, R:list)
% flow model: (i, i, i) (i, i, o)

my_append([], E, [E]).
my_append([H|T], E, [H|R]):-
    my_append(T, E, R).


% remove_duplicates(L:list, R:list)
% flow model: (i,i) (i, o)

remove_duplicates([], []).
remove_duplicates([E], [E]).
remove_duplicates([H1, H2|T], [H1|R]) :- H1 =\= H2, !,
    remove_duplicates([H2|T], R).
remove_duplicates([H1, H2|T], R) :- H1 =:= H2, !,
    remove_duplicates([H2|T], R).

% sort_list(L: list, R: list)
% flow model: (i,i) (i, o)

sort_list(L, R):-
    merge_sort(L, RS),
    remove_duplicates(RS, R),
    !.

test_sort:-
    sort_list([4, 2, 6, 2, 3, 4],[2, 3, 4, 6]),
    sort_list([5, 4, 3, 2, 1], [1, 2, 3, 4, 5]),
    sort_list([1, 2, 4, 2, 1], [1, 2, 4]),
    sort_list([6, 1, 1, 2, 2, 3, 3, 4, 5, 5, 3, 1], [1, 2, 3, 4, 5, 6]).