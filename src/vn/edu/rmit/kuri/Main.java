package vn.edu.rmit.kuri;

import java.io.FileNotFoundException;
import vn.edu.rmit.kuri.data.Database;
import vn.edu.rmit.kuri.processing.Summary;

public class Main {

  public static void invalidSyntaxError() {
    System.out.println("Invalid number of arguments");
  }

  /**
   * Prints out the program's header. For decoration purpose.
   *
   * @param databasePath String: Path to the database to be displayed back to the user
   */
  public static void header(String databasePath) {
    System.out.println("╔═══════════════════════════════════════════╗");
    System.out.println("║               COVID TRACKER               ║");
    System.out.println("╚═══════════════════════════════════════════╝");
    System.out.printf("Database Path: %s\n", databasePath);
  }

  public static void main(String[] args) {
    String databasePath;

    // Parameter conditions
    switch (args.length) {
      case 0 -> databasePath = "data/covid-data.csv";   // Default database path when no argument is provided
      case 1 -> databasePath = args[0];                 // Database path provided via program argument
      default -> {                                      // Invalid syntax, exit immediately
        invalidSyntaxError();
        return;
      }
    }

    // Main program
    header(databasePath);
    try {
      // Load database into memory
      Database database = new Database(databasePath);

      // Prompt user for input

      // Data processing
      Summary summary = new Summary();

      // Display processed data

    } catch (FileNotFoundException e) {  // Invalid database file path, or file doesn't exist
      System.out.println(e.getMessage());
    }
  }
}
