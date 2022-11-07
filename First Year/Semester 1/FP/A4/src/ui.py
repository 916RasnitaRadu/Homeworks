"""
  User interface module
"""

from functions import *
from copy import deepcopy

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
    if calculate_total_expenses_apartament(elements, apartament_nr) > sum:
      print(apartament_nr)
      p += 1
  if p == 0:
    print(f"There are no apartaments with the total expenses higher than {sum}.")


def display_expenses_lower_than(elements,sum):
  p = 0
  for i in range(len(elements["apartaments"])):
    apartament_nr = get_ap(elements, "apartaments", i)
    if calculate_total_expenses_apartament(elements, apartament_nr) < sum:
      print(apartament_nr)
      p += 1
  if p == 0:
    print(f"There are no apartaments with the total expenses lower than {sum}.")


def display_expenses_equal_to(elements, sum):
  p = 0
  for i in range(len(elements["apartaments"])):
    apartament_nr = get_ap(elements, "apartaments", i)
    if calculate_total_expenses_apartament(elements, apartament_nr) == sum:
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


def display_total_expenses_type(params_list, elements):
  # A function that displays the total amount of expenses for a certain type.
  amount = calculate_total_expenses_type(params_list, elements)
  print(f"The total amount of expenses for {params_list[0]} is: " + str(amount))
  print(" ")


def display_apartaments_sorted(elements):
  # A function that displays the apartaments in the building sorted ascending by total amount of expenses.
  apartaments_sorted = sort_apartament(elements)
  print("The list of apartments sorted ascending by total amount of expenses is:\n")
  for nr in apartaments_sorted:
    print(nr)


def display_types_sorted(elements):
  # A function that displays the types of expensive sorted ascending by amount of money.
  total_expenses = list_total_expenses_type(elements)
  total_expenses.sort()
  types_sorted = sort_type(elements)
  print("The list of the total amount of expenses for each type, sorted ascending by amount of money is:")
  for i in range(len(types_sorted)):
    print(types_sorted[i] + ": " + str(total_expenses[i]))


def display_max_expense_for_each_type(elements, history_list, params_list):
  max_expenses = max_expense_for_each_type(elements, history_list, params_list)
  print("The max expense for water is: " + str(max_expenses[0]))
  print("The max expense for heating is: " + str(max_expenses[1]))
  print("The max expense for gas is: " + str(max_expenses[2]))
  print("The max expense for electricity is: " + str(max_expenses[3]))
  print("The max expense for others is: " + str(max_expenses[4]))
  print(" ")

def wrong_undo():
  print("There aren't any actions to undo.")


def wrong_input():
  print("Sorry, you've entered an incorrect command. Please try again.\n")

def print_command_menu():
  print("\n\t1. Add transaction. (add)")
  print("\t2. Modify expenses. (remove/replace)")
  print("\t3. Display expenses. (list)")
  print("\t4. Display the total amount of expenses for a certain type. (sum)")
  print("\t5. Display the maximum amount per each expense type for an apartment. (max)")
  print("\t6. Display the list of apartments sorted ascending by total amount of expenses. (sort apartament)")
  print("\t7. Display the total amount of expenses for each type, sorted ascending by amount of money. (sort type)")
  print("\t8. Filter. (filter)")
  print("\t9. Exit the program. (exit)\n")

def run_menu_ui():
  elements = init_elements()
  history_list = []
  history_list.append(deepcopy(elements))

  while True:
    print_command_menu()
    cmd_line = input("Input the command and the arguments: ")
    cmd_word, cmd_params = split_command_user(cmd_line)
    params_list = params_split(cmd_params)
    try:
      if cmd_word == "add":
        if add_transaction(params_list,elements) is False:
          wrong_input()
        else:
          history_list.append(deepcopy(elements))
      elif cmd_word == "remove":
        if remove_expenses(params_list,elements) is False:
          wrong_input()
        else:
          history_list.append(deepcopy(elements))
      elif cmd_word == "replace":
          if replace_expenses(params_list, elements) is False:
            wrong_input()
          else:
            history_list.append(deepcopy(elements))
      elif cmd_word == "list":
          if display_expenses(params_list, elements) is False:
            wrong_input()
      elif cmd_word == "sum":
        if calculate_total_expenses_type(params_list, elements) is False:
          wrong_input()
        else:
          display_total_expenses_type(params_list, elements)
      elif cmd_word == "sort" and len(params_list) == 1 and params_list[0] == "type":
        display_types_sorted(elements)
      elif cmd_word == "sort" and len(params_list) == 1 and params_list[0] == "apartament":
        display_apartaments_sorted(elements)
      elif cmd_word == "filter" and len(params_list) == 1:
        if filter(params_list, elements) is False:
          wrong_input()
        else:
          history_list.append(deepcopy(elements))
      elif cmd_word == "max" and len(params_list) == 1:
        if max_expense_for_each_type(elements, history_list, params_list) is False:
          wrong_input()
        else:
          display_max_expense_for_each_type(elements,history_list,params_list)
      elif cmd_word == "undo" and params_list == []:
        if len(history_list) == 1:
          wrong_undo()
        else:
          history_list.pop()
          elements = deepcopy(history_list[-1])
      elif cmd_word == "exit":
        return
      else:
        wrong_input()
    except KeyError:
      wrong_input()
    except ValueError:
      wrong_input()

"""
add 2 water 20
add 2 other 50
replace 2 electricity with 10
"""