"""
Jane is the administrator of an apartment building and she wants to manage the monthly expenses for each apartment.
Each expense is stored using the following elements: apartment (number of apartment,
positive integer), amount (positive integer), type (from one of the predefined categories water, heating, electricity,
gas and other). Write a program that implements the functionalities exemplified
below:
(A) Add new transaction 
(B) Modify expenses
(C) Display expenses having different properties
"""

"""
  Write non-UI functions below
"""

# ==================== GETTER FUNCTIONS =================
def get_apartament_index(elements,apartament_number):
  """
  A function that gets the index of an apartament in the list of apartaments from the dictionary elements.
  Input - a dictionary elements, a positive integer apartament_number
  Output - a >= 0 integer i representing the index of the given apartament
  """
  i = elements["apartaments"].index(apartament_number)
  return i

def get_ap(elements,type,i):
  """
  elements - dictionary
  type - string in "water" / "apartaments" /..
  i - integer - index
  """
  return elements[type][i]

# ==================== SETTER FUCNTIONS ================
def set_ap_expenses(elements, type, i, expense):
  """
  elements - dictionary
  type - string in "water" / "apartaments" /..
  i - integer - index
  expense - integer
  """
  elements[type][i] = expense

# =======================================================
def init_elements():
  """
  A function that initialize the lists of elements(water, gas, heating etc.) with 10 items.
  Return - a dictionary with 10 items for each element. The items of the list from "apartaments" stands for the number
  of the apartament. The items for the rest of the lists stands for the amount of expenses.
  """
  return {
    "apartaments": [23, 43, 12, 11, 10, 32, 22, 3, 4, 17],
    "water": [40, 53, 12, 28, 32, 74, 21, 98, 32, 100],
    "heating": [50, 64, 34 ,21 ,44 ,21 ,40 ,23 ,42 ,64],
    "gas": [48,21,98,52,65,32,47,87,41,63],
    "electricity": [41,23,89,65,41,23,55,19,41,54],
    "other": [74,12,33,25,65,41,23,79,54,15]
  }

def split_command_user(cmd_line):
  """
  A function that splits the command line into the key-word ('add', 'remove', 'replace' etc.) and its parameters.
  Input - a string that represents the input command
  Output - A tuple of (<command word>, <command params>) in lowercase
  """
  cmd_line = cmd_line.strip()
  tokens = cmd_line.split(maxsplit=1)
  cmd_word = tokens[0].lower() if len(tokens) > 0 else None
  cmd_params = tokens[1].lower() if len(tokens) ==2 else None

  return cmd_word, cmd_params

def params_split(cmd_params):
  """
  A function that splits the command parameters in one-word strings.
  Input: a string containing the parameters of the command: cmd_params
  Output: a list of strings(words) params_list
  """
  if cmd_params == None: return []
  else:
    x = cmd_params.split()
    params_list = []
    for item in x:
      params_list.append(item)
    return params_list

def add_new_expenses(expense,amount):
  """
  A function that updates the amount of expenses.
  """
  expense += amount
  return expense

def add_new_apartment(elements,apartament_number):
  """
  A function that adds a new apartament to the dictionary elements a new with all the initial expenses to 0.
  Input - the dictionary of elements and an integer: apartament_number
  """
  elements["apartaments"].append(apartament_number)
  elements["water"].append(0)
  elements["heating"].append(0)
  elements["gas"].append(0)
  elements["electricity"].append(0)
  elements["other"].append(0)


def add_transaction(params_list, elements):
  """
  A function that adds a new transaction to a specific apartament. If the apartament doesn't exist, it's created with
  all the initial expenses to 0.
  add <apartment> <type> <amount>
  Input - a dictionary elements and a string containing the parameters of the command: cmd_params
  """
  if len(params_list) == 3:
    apartament_nr = int(params_list[0])
    amount = int(params_list[2])
    if apartament_nr not in elements["apartaments"]:
      add_new_apartment(elements, apartament_nr)
    i = get_apartament_index(elements, apartament_nr)
    expense = get_ap(elements, params_list[1],i)
    expense = add_new_expenses(expense,amount)
    set_ap_expenses(elements, params_list[1], i, expense)
  else:
    return False

def calculate_total_expenses(elements,apartament_nr):
  """
  A function that calculates the total expenses of an apartament.
  Input - a dictionary elements, an integer apartament_nr
  Output - an integer s, representing the sum of all the expenses
  """
  i = get_apartament_index(elements,apartament_nr)
  s = 0
  s = get_ap(elements,"water",i) + get_ap(elements,"gas",i) + get_ap(elements,"electricity",i) + get_ap(elements,"heating",i) + get_ap(elements,"other",i)
  return s

def remove_expenses_type(elements,type):
  """
  A function that removes the expenses of type type for every apartament in the list.
  Input - a dictionary elements and one string type, representing the type of utilities from which the expenses will be
  removed.
  Output - If the input is correct the function will execute the commands.
  """
  for i in range(len(elements["apartaments"])):
    set_ap_expenses(elements, type, i, 0)

def remove_expenses_apartament(elements, apartament_nr):
  """
  A function that removes all the expenses for one specific apartament in the list.
  Input - a dictionary elements and one integer, representing the number of the apartament.
  Output - If the input is correct the function will execute the commands.
  """
  apartament_index = get_apartament_index(elements, apartament_nr)
  for type in elements:
    if type != "apartaments":
      set_ap_expenses(elements, type, apartament_index, 0)


def remove_expenses_apartament_to_apartament(elements, apartament_nr_start, apartament_nr_stop):
  """
  A function that removes the expenses from the starting apartament to the finish apartament inputed.
  Input - a dictionary elements and 2 integers, representing the starting and the finishing apartament number.
  Output - If the input is correct the function will execute the commands
  """
  apartament_nr_start_index = get_apartament_index(elements, apartament_nr_start)
  apartament_nr_stop_index = get_apartament_index(elements, apartament_nr_stop)
  for i in range(apartament_nr_start, apartament_nr_stop + 1):
    if i in elements["apartaments"]:
      j = get_apartament_index(elements, i)
      for type in elements:
        if type != "apartaments":
          set_ap_expenses(elements, type, j, 0)


def remove_expenses(params_list, elements):
  """
  A function that removes the either the all the expenses for an apartament, the expenses for apartaments in a given
  range, or the expenses of a type for every apartament.
  remove <apartment> / remove <start apartment> to <end apartment> / remove <type>
  Input - a dictionary elements, and a list of parameters, strings, params_list
  Output - a boolean False value if the input is not correct
  If the input is correct the function will execute the commands
  """
  if len(params_list) == 1 or len(params_list) == 3:
    if len(params_list) == 1:
      if params_list[0] in elements:
        remove_expenses_type(elements,params_list[0])
      else:
        apartament_nr = int(params_list[0])
        remove_expenses_apartament(elements,apartament_nr)
    elif params_list[1] == "to":
      apartament_nr_start = int(params_list[0])
      apartament_nr_stop = int(params_list[2])
      if apartament_nr_start >= apartament_nr_stop: return False
      else:
        remove_expenses_apartament_to_apartament(elements,apartament_nr_start,apartament_nr_stop)
    else: return False
  else: return False

def replace_expenses(params_list, elements):
  """
  replace <apartment> <type> with <amount>
  A function that replaces the expenses of a specific type from an apartament with an specific amount.
  Input - a dictionary elements, and a list of parameters, strings, params_list
  Output - a boolean False value if the input is not correct
  If the input is correct the function will execute the commands
  """
  if len(params_list) == 4:
    if params_list[2] == "with":
      if params_list[1] in elements:
        apartament_nr = int(params_list[0])
        apartament_nr_index = get_apartament_index(elements, apartament_nr)
        amount = int(params_list[3])
        set_ap_expenses(elements, params_list[1], apartament_nr_index, amount)
      else: return False
    else: return False
  else: return False

# ======================== TESTS FUNCTIONS ================

def test_add_transaction():
  elem = init_elements()
  add_transaction(["12", "gas", "50"],elem)
  assert elem == {
    "apartaments": [23, 43, 12, 11, 10, 32, 22, 3, 4, 17],
    "water": [40, 53, 12, 28, 32, 74, 21, 98, 32, 100],
    "heating": [50, 64, 34 ,21 ,44 ,21 ,40 ,23 ,42 ,64],
    "gas": [48,21,148,52,65,32,47,87,41,63],
    "electricity": [41,23,89,65,41,23,55,19,41,54],
    "other": [74,12,33,25,65,41,23,79,54,15]
  }
  add_transaction(["23", "water", "20"],elem)
  assert elem == {
    "apartaments": [23, 43, 12, 11, 10, 32, 22, 3, 4, 17],
    "water": [60, 53, 12, 28, 32, 74, 21, 98, 32, 100],
    "heating": [50, 64, 34 ,21 ,44 ,21 ,40 ,23 ,42 ,64],
    "gas": [48,21,148,52,65,32,47,87,41,63],
    "electricity": [41,23,89,65,41,23,55,19,41,54],
    "other": [74,12,33,25,65,41,23,79,54,15]
  }
  add_transaction(["4", "electricity", "40"], elem)
  assert elem == {
    "apartaments": [23, 43, 12, 11, 10, 32, 22, 3, 4, 17],
    "water": [60, 53, 12, 28, 32, 74, 21, 98, 32, 100],
    "heating": [50, 64, 34, 21, 44, 21, 40, 23, 42, 64],
    "gas": [48, 21, 148, 52, 65, 32, 47, 87, 41, 63],
    "electricity": [41, 23, 89, 65, 41, 23, 55, 19, 81, 54],
    "other": [74, 12, 33, 25, 65, 41, 23, 79, 54, 15]
  }

def test_remove_expenses():
  elem ={"apartaments": [23, 43, 12,10],
    "water": [40, 53, 12,5],
    "heating": [50, 64, 34,6],
    "gas": [48,21,98,100],
    "electricity": [41,23,89,23],
    "other": [74,12,33,90] }

  remove_expenses(["12"],elem)

  assert elem ==  {"apartaments": [23, 43, 12,10],
    "water": [40, 53, 0,5],
    "heating": [50, 64, 0,6],
    "gas": [48,21, 0,100],
    "electricity": [41,23, 0,23],
    "other": [74,12, 0,90] }

  remove_expenses(["electricity"],elem)
  assert elem == {"apartaments": [23, 43, 12,10],
    "water": [40, 53, 0,5],
    "heating": [50, 64, 0,6],
    "gas": [48,21, 0,100],
    "electricity": [0,0, 0,0],
    "other": [74,12, 0,90] }

def test_calculate_total_expenses():
  elem = {"apartaments": [23, 43, 12,10],
    "water": [40, 53, 12,5],
    "heating": [50, 64, 34,6],
    "gas": [48,21,98,100],
    "electricity": [41,23,89,23],
    "other": [74,12,33,90] }

  assert calculate_total_expenses(elem,23) == 253
  assert calculate_total_expenses(elem,43) == 173
  assert calculate_total_expenses(elem,12) == 266
  assert calculate_total_expenses(elem,10) == 224


def test_split_command_user():
  assert split_command_user('exit') == ('exit', None)
  assert split_command_user('eXiT') == ('exit', None)
  assert split_command_user('add 23 water 34') == ('add', '23 water 34')
  assert split_command_user('  REMOve 5 to   10') == ('remove', '5 to   10')
  assert split_command_user('   ADD    45 Heating 100   ') == ('add', '45 heating 100')
  assert split_command_user('list 14') == ('list', '14')


def test_params_split():
  assert params_split('23 water 3') == ['23', 'water', '3']
  assert params_split('5 to 10') == ['5', 'to', '10']
  assert params_split('45 heating 100') == ['45', 'heating', '100']
  assert params_split('45') == ['45']
  assert params_split('23 to 87') == ['23', 'to', '87']
  assert params_split('> 23') == ['>', '23']


def test_get_apartament_index():
  elements = init_elements()
  assert get_apartament_index(elements, 12) == 2
  assert get_apartament_index(elements, 11) == 3
  assert get_apartament_index(elements, 10) == 4
  assert get_apartament_index(elements, 32) == 5
  assert get_apartament_index(elements, 3) == 7
  assert get_apartament_index(elements, 4) == 8
  assert get_apartament_index(elements, 17) == 9


def test_all():
  test_get_apartament_index()
  test_split_command_user()
  test_params_split()
  test_calculate_total_expenses()
  test_add_transaction()
  test_remove_expenses()


"""
  Write the command-driven UI below
"""


def display_all(elements):
  for i in range(len(elements["apartaments"])):
    print("Apartament number: " + str(get_ap(elements, "apartaments", i)))
    print("Water expenses: " + str(get_ap(elements, "water", i)) + " RON")
    print("Heating expenses: " + str(get_ap(elements, "heating", i)) + " RON")
    print("Gas expenses: " + str(get_ap(elements, "gas", i)) + " RON")
    print("Electricity expenses: " + str(get_ap(elements, "electricity", i)) + " RON")
    print("Other expenses: " + str(get_ap(elements, "other", i)) + " RON")
    print(" ")


def display_apartament(elements,apartament_number):
  i = get_apartament_index(elements,apartament_number)
  print("Apartament number: " + str(get_ap(elements, "apartaments", i)))
  print("Water expenses: " + str(get_ap(elements, "water", i)) + " RON")
  print("Heating expenses: " + str(get_ap(elements, "heating", i)) + " RON")
  print("Gas expenses: " + str(get_ap(elements, "gas", i)) + " RON")
  print("Electricity expenses: " + str(get_ap(elements, "electricity", i)) + " RON")
  print("Other expenses: " + str(get_ap(elements, "other", i)) + " RON")
  print(" ")


def display_expenses_higher_than(elements, sum):
  p = 0
  for i in range(len(elements["apartaments"])):
    apartament_nr = get_ap(elements,"apartaments",i)
    if calculate_total_expenses(elements,apartament_nr) > sum:
      print(apartament_nr)
      p += 1
  if p == 0:
    print(f"There are no apartaments with the total expenses higher than {sum}.")


def display_expenses_lower_than(elements,sum):
  p = 0
  for i in range(len(elements["apartaments"])):
    apartament_nr = get_ap(elements, "apartaments", i)
    if calculate_total_expenses(elements, apartament_nr) < sum:
      print(apartament_nr)
      p += 1
  if p == 0:
    print(f"There are no apartaments with the total expenses lower than {sum}.")


def display_expenses_equal_to(elements, sum):
  p = 0
  for i in range(len(elements["apartaments"])):
    apartament_nr = get_ap(elements, "apartaments", i)
    if calculate_total_expenses(elements, apartament_nr) == sum:
      print(apartament_nr)
      p += 1
  if p == 0:
    print(f"There are no apartaments with the total expenses equal to {sum}.")


def display_expenses(params_list, elements):
  if len(params_list) == 0:
    display_all(elements)
  elif len(params_list) == 1:
    apartament_number = int(params_list[0])
    if apartament_number not in elements["apartaments"]:
      print(f"Sorry, but the apartament {params_list[0]} is not registered.")
    else:
      display_apartament(elements,apartament_number)
  elif len(params_list) == 2:
    if params_list[0] == '>':
      print(f"The apartaments with the total expenses higher than {params_list[1]} are: ")
      display_expenses_higher_than(elements,int(params_list[1]))
      print(" ")
    elif params_list[0] == '<':
      print(f"The apartaments with the total expenses lower than {params_list[1]} are: ")
      display_expenses_lower_than(elements, int(params_list[1]))
      print(" ")
    elif params_list[0] == '=':
      print(f"The apartaments with the total expenses equal to {params_list[1]} are: ")
      display_expenses_equal_to(elements, int(params_list[1]))
      print(" ")
    else: return False
  else: return False



def wrong_input():
  print("Sorry, you've entered an incorrect command. Please try again.\n")

def print_command_menu():
  print("\t1. Add transaction. (add)")
  print("\t2. Modify expenses. (remove/replace)")
  print("\t3. Display expenses. (list)")
  print("\t4. Exit the program. (exit)")

def run_menu_ui():
  elements = init_elements()

  while True:
    print_command_menu()
    cmd_line = input("Input the command and the arguments: ")
    cmd_word, cmd_params = split_command_user(cmd_line)
    params_list = params_split(cmd_params)
    try:
      if cmd_word == "add":
        if add_transaction(params_list,elements) == False:
          wrong_input()
      elif cmd_word == "remove":
        if remove_expenses(params_list,elements) == False:
          wrong_input()
      elif cmd_word == "replace":
          if replace_expenses(params_list, elements) == False:
            wrong_input()
      elif cmd_word == "list":
          if display_expenses(params_list, elements) == False:
            wrong_input()
      elif cmd_word == "exit": return
      else:
        wrong_input()
    except KeyError:
      wrong_input()
    except ValueError:
      wrong_input()


test_all()
run_menu_ui()
