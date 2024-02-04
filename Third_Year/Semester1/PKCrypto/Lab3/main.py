# Generalized Fermat's algorithm
# It first considers k = 1
# If not successful then it will consider k = 2, 3, ...

import math


def gcd(a, b):
    # Computes the greatest common divisor of 2 numbers
    while b:
        a, b = b, a % b
    return a


def fermat_factorization(n, B=1000):
    """
    Generalized Fermat's factorization algorithm
    :param n: number to be factorized
    :param B: arbitrary bound B (how far we go with trials of k and t)
    :return:
    """
    for k in range(1, B + 1):
        # first we start from k = 1, if we don't find a result we continue with k = 2, 3, ..., B
        t0 = int(math.sqrt(k * n))

        for t in range(t0 + 1, t0 + B):
            b_squared = t**2 - k * n
            b = int(math.sqrt(b_squared))

            if b**2 == b_squared:  # if b is a perfect square
                s = t - b  # s and t + b are potential factors of n
                p = gcd(n, s)  # so we use gcd to verify if they are non-trivial ( != 1 and != n)
                q = gcd(n, t + b)

                if 1 < p < n and 1 < q < n:  # if the gcd are indeed non-trivial factors then n is not prime and the result is returned
                    return p, q

    return None


if __name__ == '__main__':
    while True:

        number = int(input("Enter the number: "))

        if number == 0:
            break

        result = fermat_factorization(number, 1000)

        if result is None:
            print("The number is prime (maybe)")
        else:
            print(f"{number} = {result[0]} * {result[1]}")
