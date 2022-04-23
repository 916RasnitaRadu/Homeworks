#
# Write the implementation for A2 in this file
#

from math import sqrt

# Function section
# (write all non-UI functions in this section)
# There should be no print or input statements below this comment
# Each function should do one thing only
# Functions communicate using input parameters and their return values

def create_complex(real=0, imag=0):
    return [real, imag]

def init_list():
    return [create_complex(1,2),
            create_complex(3,4),
            create_complex(12,-3),
            create_complex(64),
            create_complex(5,-13),
            create_complex(4,10),
            create_complex(11),
            create_complex(32,-4),
            create_complex(7,-2),
            create_complex(50,3)]

def clear_list():
    return []

def get_real(n):
    return n[0]

def get_imag(n):
    return n[1]

def to_str(n):
    if get_imag(n) > 0:
        return str(get_real(n)) + "+" + str(get_imag(n)) + "i"
    elif get_imag(n) == 0:
        return str(get_real(n))
    elif get_imag(n) < 0:
        return str(get_real(n)) + str(get_imag(n)) + "i"


def test_to_str():
    assert to_str(create_complex(2,3)) == '2+3i'
    assert to_str(create_complex(4,-4)) == '4-4i'
    assert to_str(create_complex(10)) == '10'
    assert to_str(create_complex(5,10)) == '5+10i'
    assert to_str(create_complex(6,-14)) == '6-14i'

def is_real(n):
    """
    A function that verifies if a complex number is real. (the imaginary part is equal to 0)
    :param n: A list of two values representing the complex number. First value is the real part and the second is the
    imaginary part.
    :return: A boolean value: True if the number is real and False if it isn't.
    """
    if get_imag(n) == 0: return True
    else: return False

def test_is_real():
    assert is_real(create_complex()) == True
    assert is_real(create_complex(9,4)) == False
    assert is_real(create_complex(8,-1)) == False
    assert is_real(create_complex(8,0)) == True
    assert is_real(create_complex(4)) == True
    assert is_real(create_complex(2, 4312)) == False
    assert is_real(create_complex(5,-2312)) == False

def real_numbers_sequence(list_compl):
    """
    A function that finds the longest sequence of real numbers from the input list, list_compl.
    :param list_compl: A list of complex numbers
    :return: A list of arrays that represents the longest sequence of real numbers from the input list, list_compl.
    """
    real_numbers = [] # The list that will have the searched sequence.
    max_l = 0 # The length of the longest sequence.
    l = 0 # The length of the current sequence.
    for n in list_compl:
        if is_real(n):
            l += 1
            if l > max_l:
                max_l = l
                j = list_compl.index(n)
        else: l = 0
    if max_l == 0: return []
    else:
        real_numbers = list_compl[j-max_l+1:j+1].copy()
        return real_numbers

def test_real_numbers_sequence():
    lis = [create_complex(3, 4),create_complex(3, 0),create_complex(4, 0),create_complex(6,0),create_complex(9, 5)]
    assert real_numbers_sequence(lis) == [create_complex(3, 0),create_complex(4, 0),create_complex(6, 0)]
    lis = [create_complex(3, 4),create_complex(3, 0),create_complex(4, 0),create_complex(6, 0),create_complex(9, 0),
           create_complex(2, -4), create_complex(4,0), create_complex(123, 0)]
    assert real_numbers_sequence(lis) == [create_complex(3, 0),create_complex(4, 0),create_complex(6, 0),create_complex(9, 0)]
    lis = [create_complex(3, 0),create_complex(4, 0),create_complex(6, 0),create_complex(9, 5), create_complex(4, 3),
           create_complex(0, 0), create_complex(1, 0), create_complex(4, 0), create_complex(1234, 0) ]
    assert real_numbers_sequence(lis) == [create_complex(0, 0), create_complex(1, 0), create_complex(4, 0),
                                          create_complex(1234, 0)]
    lis = [create_complex(4, 3), create_complex(3, -2), create_complex(34, 1)]
    assert real_numbers_sequence(lis) == []

def calc_modulus(n):
    """
    A function that calculates the modulus of a complex number.
    :param n:  A list of two values representing the complex number. First value is the real part and the second is the
    imaginary part.
    :return: the modulus of the number n
    """
    a = get_real(n)
    b = get_imag(n)
    c = sqrt(a*a + b*b)
    return c


def test_calc_modulus():
    assert calc_modulus(create_complex(2)) == 2
    assert calc_modulus(create_complex(1,1)) == sqrt(2)
    assert calc_modulus(create_complex(3,4)) == 5
    assert calc_modulus(create_complex(3,-4)) == 5
    assert calc_modulus(create_complex(1,-1)) == sqrt(2)

def modulus_numbers_sequence(list_compl):
    """
     A function that finds the longest sequence of complex numbers .
    :param list_compl: A list of complex numbers that have the modulus of all elements in the [0, 10] range.
    :return: A list of arrays that represents the longest sequence of complex numbers from the input list, list_compl.
    """
    numbers = []  # The list that will have the searched sequence.
    max_l = 0  # The length of the longest sequence.
    l = 0  # The length of the current sequence.
    for n in list_compl:
        c = calc_modulus(n)
        if  c >= 0 and c <= 10:
            l += 1
            if l > max_l:
                max_l = l
                j = list_compl.index(n)
        else:
            l = 0
    if max_l == 0:
        return []
    else:
        numbers = list_compl[j - max_l + 1:j + 1].copy()
        return numbers

def test_modulus_numbers_sequence():
    lis = [create_complex(10, 0), create_complex(10, 13), create_complex(1, 1), create_complex(2, -2),
           create_complex(3, 4), create_complex(-3, 4), create_complex(53, 42)]
    assert modulus_numbers_sequence(lis) == [create_complex(1, 1), create_complex(2, -2), create_complex(3, 4),
                                             create_complex(-3, 4)]
    lis = [create_complex(13, 23), create_complex(10, 5)]
    assert modulus_numbers_sequence(lis) == []
    lis = [create_complex(1, 3), create_complex(1, -4), create_complex(-1, -1), create_complex(2, 0),
           create_complex(-2, 1), create_complex(-3, 0), create_complex(4, 10), create_complex(-10, 3),
           create_complex(2, 0), create_complex(1, -1)]
    assert modulus_numbers_sequence(lis) == [create_complex(1, 3), create_complex(1, -4), create_complex(-1, -1),
        create_complex(2, 0), create_complex(-2, 1), create_complex(-3, 0)]

#def add_complex():



# print('Hello A2'!) -> prints aren't allowed here!

test_to_str()
test_is_real()
test_real_numbers_sequence()
test_calc_modulus()
test_modulus_numbers_sequence()

# UI section
# (write all functions that have input or print statements here).
# Ideally, this section should not contain any calculations relevant to program functionalities


def read_complex():
    real = float(input("Please enter the real part: "))
    imag = float(input("Please enter the imaginary part: "))

    return create_complex(real,imag)


def print_menu():
    print("1. Read a complex number and add to the list.")
    print("2. Display the entire list of numbers.")
    print("3. Clear list.")
    print("4. Display the longest sequence of real numbers.")
    print("5. Display the longest sequence of numbers that have the modulus of all elements in the [0, 10] range.")
    print("6. Exit application.")


def print_numbers(list_compl):
    for number in list_compl:
        print(to_str(number))


def start_menu_ui():
    list_compl = init_list()

    while True:
        print_menu()
        try:
            op = int(input("Please what do you want to do next: "))
            if op < 0 or op > 6:
                raise ValueError
            elif op == 1:
                n = read_complex()
                list_compl.append(n)
            elif op == 2:
                if len(list_compl) == 0:
                    print("The list is empty.\n")
                else:
                    print_numbers(list_compl)
                    print(" ")
            elif op == 3:
                list_compl = clear_list()
                print("The list has been cleared.\n")
            elif op == 4:
                real_numbers = real_numbers_sequence(list_compl)
                if len(real_numbers) == 0: print("There doesn't exists any real numbers in the list.")
                else:
                    print("The longest sequence of real numbers from the current list is: ")
                    print_numbers(real_numbers)
                    print(" ")
            elif op == 5:
                modulus_numbers = modulus_numbers_sequence(list_compl)
                if  len(modulus_numbers) == 0: print("There doesn't exists any numbers with the modulus in range [0,10].")
                else:
                    print("The longest sequence of numbers that have the modulus of all elements in the [0, 10] range is:")
                    print_numbers(modulus_numbers)
                    print(" ")
            elif op == 6: return
        except ValueError:
            print("Sorry, the input must be a valid number.\n")

start_menu_ui()
