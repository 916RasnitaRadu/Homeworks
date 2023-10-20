import random
import datetime


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
        temp = b
        b = a % b
        a = temp
    return a


def gcd_prime_factors(a, b):
    """
    Compute gcd of 2 nr by decomposing the numbers into products of prime factors.
    The GCD is the product of the common factors, taken at the lowest power.
    """
    if a == 0 or a == b:
        return a
    elif b == 0:
        return b
    i = 2
    greatest_com_div = 1
    while a > i or b > i:
        while a % i == 0 and b % i == 0:
            greatest_com_div *= i
            a //= i
            b //= i
        i += 1

    return greatest_com_div


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

        print("\na={},b={}".format(a, b))

        print("Start Euclidean GCD")
        start = datetime.datetime.now()
        gcd = gcd_euclidean(a, b)
        end = datetime.datetime.now()
        print("Time elapsed: {:.12f}".format((end-start).total_seconds()))
        print("Gcd is {}\n".format(gcd))

        print("Start Prime Factorization GCD")
        start = datetime.datetime.now()
        gcd = gcd_prime_factors(a, b)
        end = datetime.datetime.now()
        print("Time elapsed: {}".format(end - start))
        print("Gcd is {}\n".format(gcd))

        print("Start Brute Force GCD")
        start = datetime.datetime.now()
        gcd = gcd_brute_force(a, b)
        end = datetime.datetime.now()
        print("Time elapsed: {}".format(end - start))
        print("Gcd is {}\n".format(gcd))

