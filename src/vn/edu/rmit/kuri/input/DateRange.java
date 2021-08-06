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

    // for start and end dates format
    String datesRegex = "^\\d{4}-\\d{2}-\\d{2}\\s+\\d{4}-\\d{2}-\\d{2}$";

    // for number of days/ week before end date
    String durationEndDateRegex = "^\\d+[wd]\\s+\\d{4}-\\d{2}-\\d{2}$";

    // for  number of days/ week after start date
    String startDateDurationRegex = "^\\d{4}-\\d{2}-\\d{2}\\s+\\d+[wd]$";

    while (!dateRange.matches(datesRegex) && !dateRange.matches(durationEndDateRegex) &&
          !dateRange.matches(startDateDurationRegex)) {
      System.out.print("Your date range format is invalid. Please try again: ");
      dateRange = sc.nextLine();
    }

    String[] rangeInput = dateRange.split(" ");
    String range = "";
    String[] numRange, timePeriod; //regex for duration: \d+(w|d)

    while (!(isValidDate(rangeInput[0]) && isValidDate(rangeInput[1]))) {
      System.out.println("invalid. Enter again: ");
      dateRange = sc.nextLine();
      rangeInput = dateRange.split(" ");
    }

    System.out.println(Arrays.toString(rangeInput));

    if (isValidDate(rangeInput[0])) {
      start = LocalDate.parse(rangeInput[0]);
      // can do an else if here: if invalid date (e.g: 30/2) but correct date format
    } else {
      range = rangeInput[0];
    }

    if (isValidDate(rangeInput[1])) {
      end = LocalDate.parse(rangeInput[1]);
    } else {
      range = rangeInput[1];
    }

    numRange = range.split("[a-z]");
    timePeriod = range.replaceFirst("[0-9]+", "").split("[0-9]+");

  }

  static boolean isValidDate(String range) {
    // check validity of date entered according to specified format
    try {
      LocalDate.parse(range);
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
//    Scanner sc = new Scanner(System.in);
//    System.out.println("Enter date range: ");
//    String range = sc.nextLine();
//
//    while (!isValidDate(range)) {
//      System.out.print("Invalid date range. Please enter the date range again in valid format: ");
//      range = sc.nextLine();
//    }

//    DateRange date = new DateRange("2020-03-01   2020-02-28");

  }
}
