package vn.edu.rmit.kuri.input;

import java.util.Scanner;

public class Validation {

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
   * @param input <code>String</code>: user input
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
   * @param input <code>String</code>: user input
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

  public static void main(String[] args) {
  }
}
