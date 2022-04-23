# Problem 3: For a given natural number n find the minimal natural number m formed with the same digits.

def read_number():
    # Function that reads the given number n
    n = int(input("Please enter the number (positive integer): "))
    return n

def create_list(n):
    # Function that converts a number into list of digits.
    # Input - a natural number n
    # Output - a list of digits 'digits'
    digits = []
    while n > 0:
        c = n % 10
        n = int(n / 10)
        digits.append(c)
    return digits

def calculate_number(digits):
    # Function that converts a list of digits into a natural number m.
    # Input - a list of digits digits
    # Output - the obtained number m
    m = 0
    for digit in digits:
        m = m * 10 + digit
    return m

def sort_list(digits):
    # Function that sorts a list of digits in such way that there will be no zeros at the beginning of the list.
    # Input - an unsorted list of digits digits
    # Output - the same list, digits, sorted
    digits.sort()
    if digits[0] == 0:
        i = 1
        while i < len(digits) and digits[i] == 0:
            i += 1
        if i < n: digits[0], digits[i] = digits[i], digits[0]
    return digits

def find_minimal_natural_number(n):
    # Function that find the minimal natural number formed with the same digits as the read number.
    # Input - the given natural number n
    # Output - the minimal natural number formed with the same digits m
    digits = create_list(n)
    digits = sort_list(digits)
    m = calculate_number(digits)
    return m

def start():
    try:
        n = read_number()
        if n < 0: raise ValueError
        m = find_minimal_natural_number(n)
        print("The minimal natural number formed with the same digits is: " + str(m))
    except ValueError:
        print("Sorry, the input must be a valid number.(positive integer)")

start()
