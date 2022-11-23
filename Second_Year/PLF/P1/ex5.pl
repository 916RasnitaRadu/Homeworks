%  5.a. Write a predicate to compute the union of two sets.
%  b. Write a predicate to determine the set of all the pairs of elements in a list. Eg.: L = [a b c d] =>[[a b] [a c] [a d] [b c] [b d] [c d]].


% a) =================


% union(L1: list, L2: list)

% union(l, a1, a2, ...) = {
%     l, a vida
%     a1 + l, if not contains(l, a1)
%     union(l, a2, ....), otherwise
% }

contains(E, [E]).
contains(E, [H|_]):-
    E =:= H,
    !.
contains(E, [H|T]):-
    \+ E =:= H,
    contains(E, T).

union(L, [], L).
union(L, [H|T], [H|R]):-
    \+ contains(H, L),
    !,
    union(L, T, R).
union(L, [_|T], R):-
    union(L,T,R).


% b) =================

% Model matematic:
% sets(l1...ln, k) =
% 	[], k = 0
% 	l1 + sets(l2...ln, k - 1), k > 0
% 	sets(l2...ln, k), k > 0

% sets(L: list, K: number, R: list)
% flow model: (i,i,o)  (i,i,i)

sets(_, 0, []):- !.
sets([H|T], K, [H|R]):-
    K1 is K -1,
    sets(T,K1,R).
sets([_|T], K, R):-
    sets(T,K,R).

% gen_sets(L: list, R: list)
% flow model: (i,i) (i,o)

gen_sets([],[]).
gen_sets(L,R):-
    findall(RPart, sets(L,2,RPart), R).
