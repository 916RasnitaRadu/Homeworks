# ex 1 in the case of the normal law

# Student law

# a) P(X <= 0) (P1) and P(X >= 0) (P2)

N = input("N = ")

P1 = normcdf(0, N)

P2 = 1 - normcdf(0, N)

# b) P(-1 <= X <= 1) (P3) and P(X≤ −1 or X ≥ 1) (P4);

# P(-1 <= X <= 1) = P(X <= 1) - P(X <= -1) = tcdf(1, niu, sigma) - tcdf(-1, niu, sigma)

P3 = tcdf(1, N) - tcdf(-1, N)

# P(X≤ −1 or X ≥ 1) = 1 - P(-1 <= X <= 1)

P4 = 1 - P3

# c) the value xα such that P(X < xα) = α, for α ∈ (0,1) (xα is called the
# quantile of order α);

alpha = input("Alpha = ")
res = norminv(alpha, N)

# d) he value xβ such thatP(X > xβ) =β, for β ∈ (0,1) (xβ is the quantile
# of order 1−β)

beta = input("Beta = ")
res = tinv(1-beta, N)

