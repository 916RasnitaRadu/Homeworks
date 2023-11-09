def gcd_extended(a, b):
    """
    :param a: first number
    :param b: second number
    :return: gcd and to numbers x, y such that ax + by = GCD(a, b)
    """
    if a == 0:
        return b, 0, 1

    gcd, x1, y1 = gcd_extended(b % a, a)

    x = y1 - (b // a) * x1
    y = x1

    return gcd, x, y


def crt_algo(congruences):
    M = 1
    x = 0

    # Compute M
    for a, m in congruences:
        M *= m

    for a, m in congruences:
        # Calculate the M partial products
        Mi = M // m

        # Calculate the modular multiplicative inverses of Mi
        _, t, _ = gcd_extended(Mi, m)
        # t = 1/Mi % m

        # Add their product to x
        x += a * Mi * t

    # Solution
    return x % M


congruences = [(2, 3), (3, 4), (2, 5), (5, 7)]
x = crt_algo(congruences)
print("Solution is: ", x)
