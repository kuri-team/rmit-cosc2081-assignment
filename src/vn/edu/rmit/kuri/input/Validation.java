package vn.edu.rmit.kuri.input;

import java.util.Scanner;

public class Validation {

  public static int returnValidOption(String input, int[] options) {
    for (int option : options) {
      if (input.equals(Integer.toString(option))) {
        return option;
      }
    }
    return -1;
  }

  public static int checkInput(String input, Scanner sc, int[] options) {
    while (returnValidOption(input, options) == -1) {
      System.out.print("Your input is invalid. Please enter one of the options "
          + "specified above.\n>>>\s");
      input = sc.nextLine();
    }
    return Integer.parseInt(input);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int[] common = {1, 2, 3};
    int option = checkInput("abc", sc, common);
    System.out.println(option);
  }
}
