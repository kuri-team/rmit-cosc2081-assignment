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

  public static void checkInput(String input, Scanner sc) {
    while (!isIntegerFormat(input)) {
      System.out.print("Your input is invalid. Please try again.\n>>>\s");
      input = sc.nextLine();
    }
  }
}
