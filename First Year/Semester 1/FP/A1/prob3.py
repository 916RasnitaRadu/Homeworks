# Problem 15: Generate the largest perfect number smaller than a given natural number n. If such a number does not exist,
# a message should be displayed. A number is perfect if it is equal to the sum of its divisors, except itself.
# (e.g. 6 is a perfect number, as 6=1+2+3).

def read_number():
    # Function that reads the given number n
    n = int(input("Please enter the number (positive integer): "))
    return n

def sum_of_divisors(n):
    # Function that calculates the sum of the divisors of a given natural number.
    # Input - a natural number n
    # Output - a natural number s representing the sum of the divisors of n
    s = 0
    for div in range(1,n):
        if n % div == 0: s += div
    return s

def is_perfect(n):
    # Function that verifies if a natural number is perfect or not.
    # Input - a natural number n
    # Output - a boolean value, either True or False, depending if the given number is perfect or not.
    if sum_of_divisors(n) == n: return True
    else: return False

def find_largest_perfect_number(n):
    # A function that finds the largest perfect number smaller than a given number
    # Input - a natural number n
    # Output - a natural number num representing the largest perfect number smaller than the given number n
    for num in range(n - 1, 0, -1):
        if is_perfect(num): return num

def start():
    try:
        n = read_number()
        if n < 0:
            raise ValueError
        if n <= 6:
            print("Sorry, but there doesn't exist a perfect number smaller than the given number.")
        else:
            m = find_largest_perfect_number(n)
            print("The largest perfect number smaller than the given number is: " + str(m))
    except ValueError:
        print("Sorry, the input must be a valid number.(positive integer)")

# start()

dsad = [1,2,3,4,4,5,5,34,6,6,2]

print(dsad[1:3])
