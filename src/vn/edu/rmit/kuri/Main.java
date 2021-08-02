package vn.edu.rmit.kuri;

import java.io.FileNotFoundException;
import java.util.Objects;
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
      System.out.print("Data base loading... ");
      Database database = new Database(databasePath);
      System.out.println("[ LOADED ]\n\n");

      if (database.size() == 0) {
        printEmptyDatabaseMessage();
      } else {
        // TODO: Optimize 1 through 5 (input) and check for bad inputs from user. I.e. when the program wants an integer but the user enters a string, when the user press enters too many times, etc.
        Scanner sc = new Scanner(System.in);

        // 1 - User chooses a country/region
        System.out.println("Choose a country/region. Available choices:");
        String[] allGeoAreas = database.allGeoAreas();
        for (int i = 0; i < allGeoAreas.length; i++) {
          System.out.printf("\t%d - %s\t", i + 1, allGeoAreas[i]);
        }
        System.out.print("\n>>> ");
        String geoArea = allGeoAreas[Integer.parseInt(sc.nextLine()) - 1];

        System.out.print("""
            Enter a date range. Valid format:
                    
            \tyyyy-MM-dd yyyy-MM-dd (start date -> end date, inclusive)
            \t\tExample: 2020-01-02 2021-03-04
                    
            \t Number of days before end date, inclusive
            \t\tExample: 12d 2021-03-04
                    
            \t Number of weeks before end date, inclusive
            \t\tExample: 3w 2021-03-04
                    
            \t Number of days after start date, inclusive
            \t\tExample: 2020-01-02 12d
                    
            \t Number of weeks after start date, inclusive
            \t\tExample: 2020-01-02 3w
                    
            >>>\s"""
        );
        DateRange dateRange = new DateRange(sc.nextLine());

        // 2 - User chooses a metric
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

        // 3 - User chooses a calculation method
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

        // 4 - User chooses a grouping method
        Grouping grouping = null;
        System.out.print("""
            Choose a metric to track:
            \t1 - No groupingType
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

        // 5 - User chooses a display format
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

        // 6 - Data processing
        System.out.print("\n\nProcessing data... ");
        Summary summary = new Summary(geoArea, dateRange, grouping, database);
        System.out.println("[ DONE ]\n");

        // 7 - Display processed data
        System.out.println("\n\n─────────────────[ RESULTS ]─────────────────");
        switch (Objects.requireNonNull(displayFormat)) {
          case TABULAR -> Display.tabular(summary, metric, resultType);
          case CHART -> Display.chart(summary, metric, resultType);
        }
      }

    } catch (FileNotFoundException e) {  // Invalid database file path, or file doesn't exist
      System.out.println(e.getMessage());
    }
  }
}
