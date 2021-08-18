package vn.edu.rmit.kuri.input;

import java.util.Scanner;
import vn.edu.rmit.kuri.processing.Summary;

public class Validation {

  /**
   * Check if user input can be parsed to an integer
   * @param input <code>String</code>
   * @return true if input is in integer format, false otherwise
   */
  private static boolean isIntegerFormat(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException exception) {
      return false;
    }
  }

  /**
   * Create an array with all the options in the menu from the number of options
   * @param numOptions <code>int</code>: the number of options in the menu
   * @return <code>int[]</code>: an array containing all of the available options
   */
  private static int[] returnOptionsArray(int numOptions) {
    int[] options = new int[numOptions];

    for (int i = 0; i < numOptions; i++) {
      options[i] = i + 1;
    }
    return options;
  }

  /**
   * Check if user input matches one of the options in the menu
   * @param input <code>String</code>
   * @param numOptions <code>int</code>: the number of options in the menu
   * @return <code>int</code>: the option that matches with the user input, or -1 if
   * there is no match
   */
  private static int returnValidOption(String input, int numOptions) {
    int[] options = returnOptionsArray(numOptions);
    for (int option : options) {
      if (input.equals(Integer.toString(option))) {
        return option;
      }
    }
    return -1;
  }

  /**
   * Prompt user to enter their input again if their input is not one of the available choices
   * in the menu
   * @param input <code>String</code>
   * @param sc <code>Scanner</code>: to scan user input again
   * @param numOptions <code>int</code>: the number of options in the menu
   * @return <code>int</code>: user input in integer format only when user input is valid
   */
  public static int checkInput(String input, Scanner sc, int numOptions) {
    input = input.trim();

    // asks user to enter their choice again until their input is one of the options in menu
    while (returnValidOption(input, numOptions) == -1) {
      System.out.print("Your input is invalid. Please enter one of the options "
          + "specified above.\n>>>\s");
      input = sc.nextLine().trim();
    }
    return Integer.parseInt(input);
  }

  /**
   * Prompt user to enter their input again if their input is not an integer, or if it
   * is not in the range 0 < input <= numDays
   * @param input <code>String</code>
   * @param numDays <code>int</code>: number of days between start and end date, inclusive
   * @param sc <code>Scanner</code>: to scan user input again
   * @return <code>int</code>: user input in integer format only when user input is valid
   */
  public static int checkGroupingInput(String input, long numDays, Scanner sc) {
    input = input.trim();

    // cannot divide the groups if the number of groups is not positive
    // or if the number of group exceeds the total number of days
    while (!isIntegerFormat(input) || Integer.parseInt(input) <= 0 || Integer.parseInt(input) > numDays) {
      System.out.print("Your input is invalid, or the number you entered exceeds the number\n");
      System.out.print("of days between the start and end date. Please try again.\n>>>\s");
      input = sc.nextLine().trim();
    }
    return Integer.parseInt(input);
  }

  /**
   * Prompt user to enter their input again if the groups cannot be divided equally
   * @param input <code>String</code>
   * @param numDays <code>int</code>: number of days between start and end date, inclusive
   * @param sc <code>Scanner</code>: to scan user input again
   * @return <code>int</code>: user input in integer format only when it is possible to divide groups equally
   */
  public static int canDivideGroupsEqually(String input, long numDays, Scanner sc) {
    int numDaysPerGroup = checkGroupingInput(input, numDays, sc);

    while (numDays % numDaysPerGroup != 0) {
      System.out.println("With the number of days per group provided, we cannot divide the groups equally.");
      System.out.print("Please enter another value: ");
      numDaysPerGroup = checkGroupingInput(sc.nextLine(), numDays, sc);
    }
    return numDaysPerGroup;
  }

  /**
   * If user inputs a number of group that is larger than 79, display a message error
   */
  public void chartDisplayLimit(String input, long numDays, Scanner sc) {
    int duration = checkGroupingInput(input, numDays, sc);
    int groups = new Summary.processedData.size();
    switch (Grouping.getGroupingNum()) { // resolve later
      case 2 -> {
        // if user choose the data in chart form, display message error
      }
      case 3 -> {
        // if the number of groups calculated from num of days and numOfDaysPerGroup is greater
        // than 79 and user choose to display data in chart form, display error.
      }
    }
  }
}
