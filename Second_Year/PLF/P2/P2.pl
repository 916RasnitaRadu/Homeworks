% Problem 12
% a. Define a predicate to add after every element from a list, the divisors of that number.
% b. For a heterogeneous list, formed from integer numbers and list of numbers, 
% define a predicate to add in every sublist the divisors of every element.
% Eg.: [1, [2, 5, 7], 4, 5, [1, 4], 3, 2, [6, 2, 1], 4, [7, 2, 8, 1], 2] =>
% [1, [2, 5, 7], 4, 5, [1, 4, 2], 3, 2, [6, 2, 3, 2, 1], 4, [7, 2, 8, 2, 4, 1], 2]

% ====================================================

% Point a)

% get_div(N: Number, D: number, L: list, R: list) - is returning a list
% flow model: (i,i,i,o), (i,i,i,i)

% Mathematical model:
%     get_div(n, div, list) = {
%         list, n <= 2 or n = div
%         get_div(n, div + 1, list  + div), n mod div == 0
%         get_div(n, div + 1, list), otherwise
%     }

get_div(N,N,L,L):-!.
get_div(N,_,L,L):- N =< 2, !. 
get_div(N,D,L,[D|R]):-
    N mod D =:= 0,
    !,
    D1 is D + 1,
    get_div(N,D1,L,R).
get_div(N,D,L,R):- 
    D1 is D+1,
    get_div(N,D1,L,R).

% ====================================================

% getDiv0(N: number, D: number, L: list, R: list)
% getDiv(N: number, D: number, L: list, R: list)
% divisors(X: number, R: list)

getDiv0(N,D,R,R):-
    \+ N mod D =:= 0.
getDiv0(N,D,R,[D|R]):-
    N mod D =:= 0.
    
getDiv(_, 0, R, R):-!.
getDiv(N, D, L, R):-
    getDiv0(N, D, L, R1),
    D1 is D - 1,
    getDiv(N, D1, R1, R).

% flow model: (i,i), (i,o)

divisors(X, R):-
    X > 1,
    getDiv(X, X, [], R).
divisors(1, [1]).

% ====================================================

% add_div(L - list, R - list).
% flow model: (i, o), (i,i)

% Mathematical model:
    % add_div(l1,...,ln) = {
    %     [], l vida
    %     get_div(l1, 2) + add_div(l2, ..., ln), otherwise
    % }

add_div([],[]).
add_div([H|T], [H|R]):-
    add_div(T, RD),
    get_div(H,2,RD,R).

% hetero_list(L - list, R - list)
% flow model: (i, o), (i,i)

% Mathematical model:
    % hetero_list(l1, ..., ln) = {
    %     [], l vida
    %     add_div(l1) + hetero_list(l2, ..., ln), is_list(l1) = True
    %     hetero_list(l2, ..., ln), otherwise
    % }

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

test_all_divisors:-
    divisors(1,[1]),
    divisors(5, [1, 5]),
    divisors(4, [1,2,4]),
    divisors(16, [1,2,4,8,16]),
    divisors(17, [1,17]),
    divisors(14, [1,2,7,14]).