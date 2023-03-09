% 1.
% a. Sort a list with removing the double values. E.g.: [4 2 6 2 3 4] --> [2 3 4 6]
% b. For a heterogeneous list, formed from integer numbers and list of numbers,
% write a predicate to sort every sublist with removing the doubles.
% Eg.: [1, 2, [4, 1, 4], 3, 6, [7, 10, 1, 3, 9], 5, [1, 1, 1], 7] =>
% [1, 2, [1, 4], 3, 6, [1, 3, 7, 9, 10], 5, [1], 7].

% my_length(L:list, C:number, R:number)
% flow model: my_length(i, i, o)

my_length([],C,C):-!.
my_length([_ | T], C, R):-
    NC is C + 1,
    my_length(T, NC, R).

% Model matematic:
% merge_sort(l1...ln) =
% 	[], n = o
% 	[l1], n = 1
% 	merge( merge_sort(l1....l(n/2)), merge_sort(l(n/2+1)...ln)), otherwise

% merge_sort(L:list, R:list)
% flow model: merge_sort(i, o)