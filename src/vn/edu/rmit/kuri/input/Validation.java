package vn.edu.rmit.kuri.input;

import java.util.Scanner;

public class Validation {

  public static boolean isIntegerFormat(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException exception) {
      return false;
    }
  }

  public static int returnValidOption(String input, int[] options) {
    for (int option : options) {
      if (input.equals(Integer.toString(option))) {
        return option;
      }
    }
    return -1;
  }

  public static int checkInput(String input, Scanner sc) {
    while (!isIntegerFormat(input)) {
      System.out.print("Your input is invalid. Please try again.\n>>>\s");
      input = sc.nextLine();
    }
    return Integer.parseInt(input);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int option = checkInput("abc", sc);
    System.out.println(option);
  }
}
