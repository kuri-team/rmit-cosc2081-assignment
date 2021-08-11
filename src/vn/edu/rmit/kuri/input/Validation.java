package vn.edu.rmit.kuri.input;

import java.util.Scanner;

public class Validation {

  private static final int[] threeOptions = {1, 2, 3};
  private static final int[] twoOptions = {1, 2};

  private static int[] returnOptionsArray(int numOptions) {
    int[] options = new int[numOptions];

    for (int i = 0; i < numOptions; i++) {
      options[i] = i + 1;
    }
    return options;
  }

  public static int returnValidOption(String input, int[] options) {
    for (int option : options) {
      if (input.equals(Integer.toString(option))) {
        return option;
      }
    }
    return -1;
  }

  public static int checkInput(String input, Scanner sc, int[] options) {
    input = input.trim();

    while (returnValidOption(input, options) == -1) {
      System.out.print("Your input is invalid. Please enter one of the options "
          + "specified above.\n>>>\s");
      input = sc.nextLine().trim();
    }
    return Integer.parseInt(input);
  }

  public static int[] getThreeOptions() {
    return threeOptions;
  }

  public static int[] getTwoOptions() {
    return twoOptions;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int[] common = {1, 2, 3};
    int option = checkInput("abc", sc, common);
    System.out.println(option);
  }
}
