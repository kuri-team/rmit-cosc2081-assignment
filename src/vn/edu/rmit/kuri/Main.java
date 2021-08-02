package vn.edu.rmit.kuri;

import java.io.FileNotFoundException;
import vn.edu.rmit.kuri.data.Database;
import vn.edu.rmit.kuri.processing.Summary;

public class Main {

  public static void argError() {
    System.out.println("Invalid number of arguments");
  }

  /**
   * Prints out the program's header. For decoration purpose.
   * @param databasePath String: Path to the database to be displayed back to the user
   */
  public static void header(String databasePath) {
    System.out.println("╔═══════════════════════════════════════════╗");
    System.out.println("║               COVID TRACKER               ║");
    System.out.println("╚═══════════════════════════════════════════╝");
    System.out.printf("Database Path: %s\n", databasePath);
  }

  public static void main(String[] args) {
    if (args.length != 1) {  // Program accepts only one argument: path to the covid database
      argError();
    } else {
      try {
        String databasePath = args[0];
        header(databasePath);

        // Load database into memory
        Database database = new Database(databasePath);

        // Prompt user for input

        // Data processing
        Summary summary = new Summary();

        // Display processed data

      } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
