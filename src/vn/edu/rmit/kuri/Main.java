package vn.edu.rmit.kuri;

import java.io.FileNotFoundException;
import java.util.Scanner;
import vn.edu.rmit.kuri.data.Database;
import vn.edu.rmit.kuri.input.DateRange;
import vn.edu.rmit.kuri.input.Grouping;
import vn.edu.rmit.kuri.input.GroupingType;
import vn.edu.rmit.kuri.input.Metric;
import vn.edu.rmit.kuri.input.ResultType;
import vn.edu.rmit.kuri.output.Display;
import vn.edu.rmit.kuri.output.DisplayFormat;
import vn.edu.rmit.kuri.processing.Summary;
import vn.edu.rmit.kuri.test.Test;

public class Main {

  public static void printInvalidSyntaxError() {
    System.out.println("Invalid number of arguments");
  }

  public static void printEmptyDatabaseMessage() {
    System.out.println("Database file contains no data");
  }

  /**
   * Prints out the program's header. Essentially an eye-candy except serving to provide the user
   * feedback on which file/where the database is loaded from.
   *
   * @param databasePath <code>String</code>: Path to the database to be displayed back to the user
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
        printInvalidSyntaxError();
        return;
      }
    }

    // Main program
    header(databasePath);
    try {
      // Load database into memory
      System.out.print("Loading database...");
      Database database = new Database(databasePath);
      System.out.println("[ LOADED ]\n\n");

      if (database.size() == 0) {
        printEmptyDatabaseMessage();
      } else {
        // TODO: Optimize 1 through 6 (input) and check for bad inputs from user. I.e. when the program wants an integer but the user enters a string, when the user press enters too many times, etc.
        Scanner sc = new Scanner(System.in);

        // 1 - User chooses a country/region
        System.out.println("Choose a country/region. Available choices:");
        String[] allGeoAreas = database.allGeoAreas();
        for (int i = 0; i < allGeoAreas.length; i++) {
          System.out.printf("\t%d - %s\t", i + 1, allGeoAreas[i]);
        }
        System.out.print("\n>>> ");
        String geoArea = allGeoAreas[Integer.parseInt(sc.nextLine()) - 1];

        // 2 - User chooses a date range
        System.out.print("""
            Choose a format for date range:
            \t1 - yyyy-MM-dd yyyy-MM-dd (start date and end date, inclusive and in any order)
            \t2 - Number of days or weeks before end date, inclusive
            \t3 - Number of days or weeks after start date, inclusive
            >>>\s"""
        );
        int option = Integer.parseInt(sc.nextLine());

        while (option != 1 && option != 2 && option != 3) {
          System.out.print("Please enter a valid option.\n>>>\s");
          option = Integer.parseInt(sc.nextLine());
        }

        switch (option) {
          case 1 -> System.out.print("""
              \tEnter a pair of dates. Valid format example:
              \t\t2020-01-02 2021-03-04
              >>>\s"""
          );
          case 2 -> System.out.print("""
              \tEnter number of days/ weeks and end date (yyyy-MM-dd). Valid format example:
              \t\t12d 2021-03-04 or 3w 2021-03-04
              >>>\s"""
          );
          case 3 -> System.out.print("""
              \tEnter start date (yyyy-MM-dd) and number of days/ weeks. Valid format example:
              \t\t2020-01-02 12d or 2020-01-02 3w
              >>>\s"""
          );
        }
        DateRange dateRange = new DateRange(option, sc.nextLine());

        // 3 - User chooses a metric
        Metric metric = null;
        System.out.print("""
            Choose a metric to track:
            \t1 - Cases
            \t2 - Deaths
            \t3 - Vaccinations
            >>>\s"""
        );
        switch (Integer.parseInt(sc.nextLine())) {
          case 1 -> metric = Metric.CASES;
          case 2 -> metric = Metric.DEATHS;
          case 3 -> metric = Metric.VACCINATIONS;
        }

        // 4 - User chooses a calculation method
        ResultType resultType = null;
        System.out.printf("""
                Choose a metric to track:
                \t1 - New %s per day
                \t2 - Cumulative %s
                >>>\s""",
            metric.toString().toLowerCase(),
            metric.toString().toLowerCase()
        );
        switch (Integer.parseInt(sc.nextLine())) {
          case 1 -> resultType = ResultType.NEW_PER_PERIOD;
          case 2 -> resultType = ResultType.CUMULATIVE;
        }

        // 5 - User chooses a grouping method
        Grouping grouping = null;
        System.out.print("""
            Choose a metric to track:
            \t1 - No grouping
            \t2 - n group(s)
            \t3 - n day(s) per group
            >>>\s"""
        );
        switch (Integer.parseInt(sc.nextLine())) {
          case 1 -> grouping = new Grouping();
          case 2 -> {
            System.out.print("Enter number of group(s): ");
            grouping = new Grouping(Integer.parseInt(sc.nextLine()), GroupingType.N_GROUPS);
          }
          case 3 -> {
            System.out.print("Enter number of day(s) per group: ");
            grouping = new Grouping(Integer.parseInt(sc.nextLine()), GroupingType.N_DAYS_PER_GROUP);
          }
        }

        // 6 - User chooses a display format
        DisplayFormat displayFormat = null;
        System.out.print("""
            Choose a format to display the processed data:
            \t1 - Tabular
            \t2 - Chart
            >>>\s"""
        );
        switch (Integer.parseInt(sc.nextLine())) {
          case 1 -> displayFormat = DisplayFormat.TABULAR;
          case 2 -> displayFormat = DisplayFormat.CHART;
        }

        // 7 - Data processing
        System.out.print("\n\nProcessing data...");
        Summary summary = new Summary(geoArea, dateRange, grouping, database);
        System.out.println("[ DONE ]\n");

        // 8 - Display processed data
        Test.main(database); // TODO: Remove this line after summary has been implemented
        System.out.println("\n\n─────────────────[ RESULTS ]─────────────────");
//        switch (displayFormat) {
//          case TABULAR -> Display.tabular(summary, metric, resultType);
//          case CHART -> Display.chart(summary, metric, resultType);
//        }
      }

    } catch (FileNotFoundException e) {  // Invalid database file path, or file doesn't exist
      System.out.println(e.getMessage());
    }
  }
}
