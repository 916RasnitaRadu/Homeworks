import random
import datetime
import timeit


def gcd_euclidean(a, b):
    """
     Compute the GCD of 2 numbers using the Euclidean algorithm
     works with arbitrary size as well
    """
    if a == 0 or a == b:
        return a
    elif b == 0:
        return b
    while b != 0:
        a, b = b, a % b
    return a


def gcd_subtraction(a, b):
    while (a != b):
        if a > b:
            a -= b
        else :
            b -= a
    return a




def gcd_brute_force(x, y):
    """
    Consider the GCD as being the smallest number between the given two and subtract from this number until it divides
    both numbers
    """
    if x == 0 or x == y:
        return y
    elif y == 0:
        return x
    greatest_common_divisor = min(x, y)
    while x % greatest_common_divisor != 0 or y % greatest_common_divisor != 0:
        greatest_common_divisor -= 1
    return greatest_common_divisor


if __name__ == '__main__':
    for i in range(10):
        a = random.randint(0, 100) * 10000 + 1
        b = random.randint(0, 100) * 10000 + 1

        print("============> Number {}) a={},b={}".format(i+1, a, b))

        print("Start Euclidean GCD")
        euclidean = timeit.timeit(lambda: gcd_euclidean(a, b), number=50)
        print("\tTime elapsed: {:.40f}".format(euclidean))
        print("\tGCD is: ", gcd_euclidean(a, b), "\n")

        print("Start Prime Subtraction GCD")
        fact_time = timeit.timeit(lambda: gcd_subtraction(a, b), number=50)
        print("\tTime elapsed: {}".format(fact_time))
        print("\tGCD is: ", gcd_subtraction(a, b), "\n")

        print("Start Brute Force GCD")
        brute_time = timeit.timeit(lambda: gcd_brute_force(a, b), number=50)
        print("\tTime elapsed: {}".format(brute_time))
        print("\tGCD is: ", gcd_brute_force(a, b), "\n")


"""
Lab 2: problem 4
get a nr n;
search if is prime
S0: n-1 = 2^S * t
t in binary
S1: 
Choose a primary base
compute: b^, b^2t, ...., b^(2^S)*t
"""