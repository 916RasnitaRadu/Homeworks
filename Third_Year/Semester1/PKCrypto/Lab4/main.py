import random


def gcd(a, b):
    """
    Extended Euclidean Algorithm
    Calculates coeffs s and t such that a * s + b * t = gcd(a, b)
    :param a: integer
    :param b: integer
    :return: gcd, s, t
    """
    s, old_s = 0, 1
    t, old_t = 1, 0
    r, old_r = b, a

    while r != 0:
        q = old_r // r
        tr = r
        r = old_r - q * r
        old_r = tr

        ts = s
        s = old_s - q * s
        old_s = ts

        tt = t
        t = old_t - q * t
        old_t = tt

    # old_s and old_t are the coeffs such that a * old_s + b * old_t = old_r
    return old_r, old_s, old_t


def is_probably_prime(n, k=5):
    """
    Checks if the number is probably using Miller-Rabin Test
    :param n: integer to be tested
    :param k: number of iterations(trials)
    :return: True or False
    """
    if n == 2 or n == 3:
        return True
    if n % 2 == 0:
        return False

    # Write n = (2^r) * s + 1
    r, s = 0, n - 1
    while s % 2 == 0:
        r += 1
        s //= 2

    for _ in range(k):
        # Choose a random integer a in the range (2, n-1)
        a = random.randint(2, n - 1)

        # compute x = a^s mod n
        x = pow(a, s, n)

        # Check if x is congruent to 1 or n-1
        if x == 1 or x == n - 1:
            continue

        # Repeat r-1 times: x = x^2 mod n
        for _ in range(r - 1):
            x = pow(x, 2, n)

            # If x is congruent to n-1, break the loop
            if x == n - 1:
                break
        else:
            return False

    # if no composite witness was found in k iterations, then n is probably prime
    return True


def generate_prime(bit_length=512):
    """
    Generates a (blum) prime number which satisfies the cond p % 4 = 3
    :param bit_length:
    :return: p -> blum prime number
    """
    p = random.getrandbits(bit_length)  # generates a random number with bit_length bits
    while p % 4 != 3 or not is_probably_prime(p):
        p = random.getrandbits(bit_length)
    return p


def generate_keys(bit_length):
    """
    Function that generates the public and private keys
    :param bit_length:
    :return: (n -> public key, p -> private key, q -> private key)
    """
    p = generate_prime(bit_length // 2)
    q = generate_prime(bit_length // 2)
    n = p * q
    return n, p, q


def validate_plaintext(plaintext, alphabet):
    """
    Function to validate plain text
    Checks if each character in the plain text is present in the specified alphabet
    :param plaintext:
    :param alphabet:
    :return: True or False
    """
    return all(char in alphabet for char in plaintext)


def encrypt(message, n):
    """
    Encrypt the plaintext using the Rabin encryption algorithm.
    Converts each character in the plain text to its corresponding index in the alphabet and the encrypts each index using Rabin encryption algorithm
    :param message: integer representing the plaintext message
    :param n: the public key
    :return: integer representing the cypher text (message ^ 2 % n)
    """
    return pow(message, 2, n)


def decrypt(ciphertext, p, q):
    """
    Decrypt the ciphertext
    Specifically, each element in the ciphertext, which represents the result of squaring a character modulo p * q,
    is decrypted by finding the square root modulo p. So we basically reverse the encrypt operation
    :param ciphertext:
    :param p: private key
    :param q: private key
    :return: list of integers representing the (possible) decrypted message calculated with Chinese Remainder Theorem
    """
    n = p * q  # computes the public key

    # Calculate square roots modulo p and q
    m1_p = pow(ciphertext, (p + 1) // 4, p)
    m2_p = p - m1_p
    m1_q = pow(ciphertext, (q + 1) // 4, q)
    m2_q = q - m1_q

    # Calculate extended Euclidean algorithm to find inverses
    ext = gcd(p, q)

    y_p, y_q = ext[1], ext[2]  # we take old_s and old_t

    # Chinese Remainder Theorem to calculate four possible decryption results
    d1 = (y_p * p * m1_q + y_q * q * m1_p) % n
    d2 = (y_p * p * m2_q + y_q * q * m1_p) % n
    d3 = (y_p * p * m1_q + y_q * q * m2_p) % n
    d4 = (y_p * p * m2_q + y_q * q * m2_p) % n

    return d1, d2, d3, d4


def validate_ciphertext(ciphertext, n):
    """
    Verifies if each element is within the range [0, alphabet_size)
    :param ciphertext: list of integers
    :param n: integer
    :return: True or False
    """
    return all(pow(char, 2, n) == char % n for char in ciphertext)
    # return all(0 <= char < alphabet_size for char in ciphertext)


def rabin_main():
    """
    Main function
    :return: None
    """
    alphabet = [' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']

    n, p, q = generate_keys(512)

    print("Public Key (n):", n)
    print("Private Keys (p, q):", p, q)

    while True:

        plaintext = input("Enter the message (plaintext): ")

        if plaintext == "afara":
            break

        if validate_plaintext(plaintext, alphabet):
            message = int.from_bytes(plaintext.encode('ascii'), byteorder='big')  # integer representation for plaintext message
            ciphertext = encrypt(message, n)

            print("The encrypted message is: ", ciphertext)
            print("Now let's decrypt the message...")

            decrypted_message = decrypt(ciphertext, p, q)

            # we attempt to convert each decrypted message to see if it is the right one
            for i, decrypt_try in enumerate(decrypted_message, 1):
                try:
                    # we attempt to convert each decrypted integer to bytes and decode it to obtain the original message
                    decrypt_try_str = decrypt_try.to_bytes((decrypt_try.bit_length() + 7) // 8, byteorder='big').decode('ascii')
                    print(f"Decryption attempt {i}: {decrypt_try_str}")

                    if decrypt_try == message:
                        print("Decryption successful. Message obtained.")
                        break
                except UnicodeDecodeError:
                    print(f"Decryption attempt {i}: {decrypt_try.to_bytes((decrypt_try.bit_length() + 7) // 8, byteorder='big').hex()}")
            else:
                print("Decryption unsuccessful. No valid message obtained.")

            # if validate_ciphertext(decrypted_message, n):
            #     decrypted_text = ''.join(alphabet[char] if char != 0 else ' ' for char in decrypted_message)
            #     print("Decrypted message: ", decrypted_text)
            # else:
            #     print("Something went wrong.. Invalid ciphertext...")

        else:
            print("Invalid plaintext")


if __name__ == '__main__':
    rabin_main()
