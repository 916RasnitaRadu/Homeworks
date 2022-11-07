# EX 2

# Poisson approximation of the binomial distribution

n = input("n = ") # n >= 30
p = input("p = ") # p <= 0.05

lambda = n*p
x = [0:n]

plot(x, binopdf(x, n, p), 'g')
hold on;

plot(x, poisspdf(x, lambda), 'r')


