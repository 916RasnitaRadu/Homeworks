# ex 1 in the case of the normal law

# Normal law

# a) P(X <= 0) (P1) and P(X >= 0) (P2)

niu = input("Niu = ")
sigma = input("Sigma = ")

P1 = normcdf(0, niu, sigma)

P2 = 1 - normcdf(0, niu, sigma)

# b) P(-1 <= X <= 1) (P3) and P(X≤ −1 or X ≥ 1) (P4);

# P(-1 <= X <= 1) = P(X <= 1) - P(X <= -1) = normcdf(1, niu, sigma) - normcdf(-1, niu, sigma)

P3 = normcdf(1, niu, sigma) - normcdf(-1, niu, sigma)

# P(X≤ −1 or X ≥ 1) = 1 - P(-1 <= X <= 1)

P4 = 1 - P3

# c) the value xα such that P(X < xα) = α, for α ∈ (0,1) (xα is called the
# quantile of order α);

alpha = input("Alpha = ")
res = norminv(alpha, niu, sigma)

# d) he value xβ such thatP(X > xβ) =β, for β ∈ (0,1) (xβ is the quantile
# of order 1−β)

beta = input("Beta = ")
res = norminv(1-beta, niu, sigma)

