% Problem 12
% a. Define a predicate to add after every element from a list, the divisors of that number.
% b. For a heterogeneous list, formed from integer numbers and list of numbers, 
% define a predicate to add in every sublist the divisors of every element.
% Eg.: [1, [2, 5, 7], 4, 5, [1, 4], 3, 2, [6, 2, 1], 4, [7, 2, 8, 1], 2] =>
% [1, [2, 5, 7], 4, 5, [1, 4, 2], 3, 2, [6, 2, 3, 2, 1], 4, [7, 2, 8, 2, 4, 1], 2]

% Point a)

% insert_divisors(N: Number, D: number, L: list, R: list) - is returning a list
% flow model: (i,i,o)


get_div(N,N,L,L):-!.
get_div(N,_,L,L):- N =< 2. 
get_div(N,D,L,[D|R]):-
    N mod D =:= 0,
    !,
    D1 is D + 1,
    get_div(N,D1,L,R).
get_div(N,D,L,R):- 
    D1 is D+1,
    get_div(N,D1,L,R).

% % add_div(L - list, R - list).
% % flow model: (i, o)

add_div([],[]).
add_div([H|T], [H|R]):-
    add_div(T, RD),
    get_div(H,2,RD,R).

% hetero_list(L - list, R - list)
% flow model: (i, o)

hetero_list([], []).
hetero_list([H|T], [RD|R]):-
    is_list(H),
    !,
    add_div(H, RD),
    hetero_list(T,R).
hetero_list([H|T], [H|R]):-
    hetero_list(T,R).

% TESTS

test_add_div:-
    add_div([1,2,4,6,7], [1,2,4,2,6,2,3,7]),
    add_div([], []),
    add_div([1,2,3], [1,2,3]),
    add_div([6,2,4], [6,2,3,2,4,2]).

test_hetero_list:-
    hetero_list([],[]),
    hetero_list([1,2,5,6,7,8], [1,2,5,6,7,8]),
    hetero_list([1,2,[1,2,4], 56, 7], [1,2, [1,2,4,2], 56, 7]),
    hetero_list([3, 4, [3, 5, 7], 2, 24], [3, 4, [3, 5, 7], 2, 24]),
    hetero_list([1, [2, 5, 7], 4, 5, [1, 4], 3, 2, [6, 2, 1], 4, [7, 2, 8, 1], 2], [1, [2, 5, 7], 4, 5, [1, 4, 2], 3, 2, [6, 2, 3, 2, 1], 4, [7, 2, 8, 2, 4, 1], 2]).