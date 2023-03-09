%a. Transform a list in a set, in the order of the last occurrences of elements. Eg.: [1,2,3,1,2] is transformed in [3,1,2].

% Function to count the number of occurences of an element.
% count_occ(L - list, E - element, OCC - occurences)
% flow model: (i,i,o)

count_occ([],_,0).
count_occ([H|T], E, OCC):-
    H=:=E,
    !,
    count_occ(T, E, OCC1),
    OCC is OCC1 + 1.
count_occ([_|T], E, OCC):-
    count_occ(T, E, OCC).

% checks if appears multiple times
% multiple_times(L - list, E - integer,OK - integer)

appears_in_list([],_).
appears_in_list([H|T],E):-
    H=:=E.
appears_in_list([H|T],E):-
    appears_in_list(T, E).

multiple_times([], _):-
multiple_times([H|T], E):-
    H =:= E,
    appears_in_list(T,H).
multiple_times([H|T],E):-
    H =/= E,
    multiple_times(T,E).

% add an element to the end of the list
% addE(L -initial list, E - Element to be added, R -resulted list)
% flow model: (i,i,o)

addE([], E, [E]).
addE([H|T],E,[H|R]):-
    addE(T,E,R).

% Function to transform the list in a set.
% list_to_set(L - inital list, S - final set)
% flow model: (i, o)

list_to_set([], []). % if the list is empty there will be an empty set
list_to_set([H|T],[H|R]):- 
    count_occ(T,H,CNT), % we count the occurences of the first element in the rest of the list, if it's lower than 1, we add it to the set
    CNT < 1,
    !,
    list_to_set(T,R).
list_to_set([_|T],R):- % otherwise, we move to the next element.
    list_to_set(T,R).
 
% list_to_set with multiple_times()

list_to_set1([], []). % if the list is empty there will be an empty set
list_to_set1([H|T],[H|R]):- 
    multiple_times(T,H), % we count the occurences of the first element in the rest of the list, if it's lower than 1, we add it to the set
    !,
    addE(R,H,A),
    list_to_set1(T,A). 
list_to_set1([_|T],R):- % otherwise, we move to the next element.S
    list_to_set1(T,R).

test_list_to_set:-
    list_to_set([], []),
    list_to_set([1,2,3,2,1],[3,2,1]),
    list_to_set([1,2,3,4,2,2,3,1,1,1,2],[4,3,1,2]).
    list_to_set1([], []),
    list_to_set1([1,2,3,2,1],[3,2,1]),
    list_to_set1([1,2,3,4,2,2,3,1,1,1,2],[4,3,1,2]).