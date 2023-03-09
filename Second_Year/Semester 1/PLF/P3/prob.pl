% Write a predicate to compute the union of 2 lists without keeping duplicates

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