# Solve the problem from the second set here
# Problem 10: The palindrome of a number is the number obtained by reversing the order of its digits (e.g. the palindrome
# of 237 is 732). For a given natural number n, determine its palindrome.

def read_number():
    # Function that reads the given number n
    n = int(input("Please enter the number (positive integer): "))
    return n

def find_palindrome(n):
    # Function that calculates the palindrome of a given natural number n
    # Input - a natural number n
    # Output - a natural number m, the palindrome of the given number n
    m = 0
    while n > 0:
        c = n % 10
        n = int(n / 10)
        m = m * 10 + c
    return m

def start():
    try:
        n = read_number()
        if n < 0:
            raise ValueError
        m = find_palindrome(n)
        print("The palindrome of the given number is: " + str(m))
    except ValueError:
        print("Sorry, the input must be a valid number.(positive integer)")

start()
