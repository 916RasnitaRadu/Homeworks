% b. Define a predicate to determine the greatest common divisor of all numbers in a list.

% The function to find the greatest common divisor of 2 numbers.
% gcd(A - integer, B - integer, X - gcd)

% flow model: (i,i,o)

gcd(X, 0, X):-!. % if an operand is 0 the function returns the non zero one
gcd(0, X, X):-!.
gcd(X, X, X):-!. % if the operands are the same it returns it
gcd(X, Y, Z):-
    X > Y,
    M is X-Y,
    gcd(M, Y, Z).
gcd(X, Y, Z):-
    X < Y,
    M is Y-X,
    gcd(X, M, Z).

% The function to find the greatest common divisor of the numbers of a list.
% gcdL(L - list, Z - greatest common div)

% flow model: (i,o), (i,i)

gcdL([] , Z).
gcdL([H1,H2|T], Z):-
    gcd(H1, H2, X),
    gcdL([X|T], Z).
gcdL([H1,H2],Z):-
    gcd(H1,H2,Z).

test_gcdL:-
    gcd(4,0,4),
    gcd(0,4,4),
    gcd(5,5,5),
    gcd(12,6,6),
    gcdL([3,1,4],1),
    gcdL([12,6,4],2),
    gcdL([2,3,5,6], 1).