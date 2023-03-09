% 5. Two integers, n and m are given.
% Write a predicate to determine all possible sequences of numbers from 1 to n,
% such that between any two numbers from consecutive positions,
% the absolute difference to be >= m.

% diffAbs(a ,b) = {
%     b - a , a < b
%     a - b , otherwise
% }

% diffAbs(A: number, B: number, R: number)
% flow model: (i, i, o) (i,i,i)

diffAbs(A, B, R):-
    A < B,
    R is B-A.
diffAbs(A, B, R):-
    A >= B,
    R is A-B.

% sequence(n, i) = {
%     [] , if i > n
%     i + sequence(n, i + 1), i <= n
%     sequence(n, i + 1), i <= n
% }

% sequence(N: number, I: number, R: list)
% flow model: (i,i,o) (i,i,i)

sequence(N, I, []):- I >= N, !.
sequence(N, I, [I|R]):-
    I =< N,
    I1 is I + 1,
    sequence(N,I1,R).
sequence(N, I, R):-
    I =< N,
    I1 is I + 1,
    sequence(N,I1,R).

% checkCond(l1, l2, ..., ln, m) = {
%     true, if diffAbs(l1, l2) >= m and n = 2
%     checkCond(l2, ...,ln), if diffAbs(l1, l2) >= m and n > 2
%     false, otherwise
% }

% checkCond(L: list, M: number)
% flow model: (i, i)

checkCond([H1, H2], M):-
    diffAbs(H1, H2, R),
    R >= M,
    !.
checkCond([H1, H2|T], M):-
    diffAbs(H1, H2, R),
    R >= M,
    checkCond([H2 | T], M).

% solution(N: number, M: number, R: list)
% flow model: (i,i,o) (i,i,i)

solution(N, M, R):-
    sequence(N, 1, R),
    checkCond(R, M).

% allsolutions(N: number,  M: number, R: list)
% flow model: (i,i,o) (i,i,i)

allsolutions(N, M, R):-
    findall(RPart, solution(N, M, RPart), R).

% findall( <rezultatu de la fct>, <functia + parametrii>, lista de liste finala)

test_solution:-
    diffAbs(0,0,0),
    diffAbs(5,7,2),
    diffAbs(65,32,33),
    allsolutions(5,2,[[1,3],[1,4],[2,4]]),
    allsolutions(0,0,[]),
    allsolutions(6,3,[[1,4],[1,5],[2,5]]),
    allsolutions(7,3,[[1, 4], [1, 5], [1, 6], [2, 5], [2, 6], [3, 6]]),
    allsolutions(7,4,[[1, 5], [1, 6], [2, 6]]),!.
