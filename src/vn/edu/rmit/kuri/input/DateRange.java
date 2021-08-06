package vn.edu.rmit.kuri.input;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class DateRange {

  private LocalDate start;
  private LocalDate end;

  /**
   * @param dateRange <code>String</code>. Possible format:<br />
   *              <ul>
   *                <li><code>yyyy-MM-dd yyyy-MM-dd</code> (start date -> end date, inclusive). Example: <code>2020-01-02 2021-03-04</code></li>
   *                <li>Number of days before end date, inclusive. Example: <code>12d 2021-03-04</code></li>
   *                <li>Number of weeks before end date, inclusive. Example: <code>3w 2021-03-04</code></li>
   *                <li>Number of days after start date, inclusive. Example: <code>2020-01-02 12d</code></li>
   *                <li>Number of weeks after start date, inclusive. Example: <code>2020-01-02 3w</code></li>
   *              </ul>
   */
  public DateRange(String dateRange) {
    // Implementation TODO: Process the String range into start date and end date
    // Possible format:
    //  	yyyy-MM-dd yyyy-MM-dd (start date -> end date, inclusive). Example: 2020-01-02 2021-03-04
    //    Number of days before end date, inclusive. Example: 12d 2021-03-04
    //    Number of weeks before end date, inclusive. Example: 3w 2021-03-04
    //    Number of days after start date, inclusive. Example: 2020-01-02 12d
    //    Number of weeks after start date, inclusive. Example: 2020-01-02 3w

    Scanner sc = new Scanner(System.in);

    // regex for start and end dates format
    String datesRegex = "^\\d{4}-\\d{2}-\\d{2}\\s+\\d{4}-\\d{2}-\\d{2}$";
    String datesValidFormat = "Correct format example: 2020-01-02 2021-03-04";

    // regex for number of days/ week before end date
    String durationEndDateRegex = "^\\d+[wd]\\s+\\d{4}-\\d{2}-\\d{2}$";

    // regex for number of days/ week after start date
    String startDateDurationRegex = "^\\d{4}-\\d{2}-\\d{2}\\s+\\d+[wd]$";

    dateRange = checkValidFormat(dateRange, datesRegex, datesValidFormat, sc);
    String[] rangeTokens = dateRange.split("\s+");

    while (!isValidDate(rangeTokens[0]) || !isValidDate(rangeTokens[1])) {
      System.out.print("The date you have entered does not exist. Please try again.\n>>>\s");
      dateRange = sc.nextLine();
      dateRange = checkValidFormat(dateRange, datesRegex, datesValidFormat, sc);
      rangeTokens = dateRange.split("\s+");
    }

    start = LocalDate.parse(rangeTokens[0]);
    end = LocalDate.parse(rangeTokens[1]);

//    String[] rangeInput = dateRange.split(" ");
//    String range = "";
//    String[] numRange, timePeriod; //regex for duration: \d+(w|d)
//
//    while (!(isValidDate(rangeInput[0]) && isValidDate(rangeInput[1]))) {
//      System.out.println("invalid. Enter again: ");
//      dateRange = sc.nextLine();
//      rangeInput = dateRange.split(" ");
//    }
//
//    System.out.println(Arrays.toString(rangeInput));
//
//    if (isValidDate(rangeInput[0])) {
//      start = LocalDate.parse(rangeInput[0]);
//      // can do an else if here: if invalid date (e.g: 30/2) but correct date format
//    } else {
//      range = rangeInput[0];
//    }
//
//    if (isValidDate(rangeInput[1])) {
//      end = LocalDate.parse(rangeInput[1]);
//    } else {
//      range = rangeInput[1];
//    }
//
//    numRange = range.split("[a-z]");
//    timePeriod = range.replaceFirst("[0-9]+", "").split("[0-9]+");

  }

  /**
   * Prompt user to enter their input again if input has invalid format.
   * @param range <code>String</code>: user input
   * @param pattern <code>String</code>: regex pattern to check against
   * @param msg <code>String</code>: message to print correct format example if user input
   *                has invalid format
   * @param sc <code>Scanner</code>: scan user input again
   */
  static String checkValidFormat(String range, String pattern, String msg, Scanner sc) {
    while (!range.matches(pattern)) {
      System.out.println("The date range entered has invalid format. Please try again.");
      System.out.print(msg + "\n>>>\s");
      range = sc.nextLine();
    }
    return range;
  }

  /**
   * @param date <code>String</code>
   * @return true if date provided is valid, false otherwise.
   * E.g: if user enters 2021-01-33 => return false since that date doesn't exist
   */
  static boolean isValidDate(String date) {
    try {
      LocalDate.parse(date);
      return true;
    } catch (DateTimeParseException exception) {
      return false;
    }
  }

  public LocalDate getStart() {
    return this.start;
  }

  public LocalDate getEnd() {
    return this.end;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
//    System.out.println("Enter date range: ");
//    String range = sc.nextLine();
//
//    while (!isValidDate(range)) {
//      System.out.print("Invalid date range. Please enter the date range again in valid format: ");
//      range = sc.nextLine();
//    }

    DateRange date = new DateRange("2020-3-1    2020-02-28");
    System.out.println(date.getStart());
    System.out.println(date.getEnd());

//    checkValidFormat("2021-01-33", "^\\d+[wd]\\s+\\d{4}-\\d{2}-\\d{2}$",
//        "Correct format example: 12w 2021-02-03", sc);
  }
}
