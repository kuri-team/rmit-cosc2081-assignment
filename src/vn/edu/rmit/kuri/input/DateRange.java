package vn.edu.rmit.kuri.input;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
  public DateRange(int option, String dateRange) {
    Scanner sc = new Scanner(System.in);
    String[] rangeTokens, range, timePeriod;
    int numRange;

    /* WHEN USER CHOOSES PAIR DATES */
    if (option == 1) {
      // regex for start and end dates format
      String datesRegex = "^\\d{4}-\\d{2}-\\d{2}\\s+\\d{4}-\\d{2}-\\d{2}$";
      String datesValidFormat = "Correct format example: 2020-01-02 2021-03-04";

      dateRange = checkValidFormat(dateRange, datesRegex, datesValidFormat, sc);
      rangeTokens = dateRange.split("\s+");

      while (!isValidDate(rangeTokens[0]) || !isValidDate(rangeTokens[1])
          || rangeTokens[0].equals(rangeTokens[1])) {
        System.out.print("The date you have entered does not exist, or the 2 dates entered "
            + "are identical. Please try again.\n>>>\s");
        dateRange = sc.nextLine();
        dateRange = checkValidFormat(dateRange, datesRegex, datesValidFormat, sc);
        rangeTokens = dateRange.split("\s+");
      }

      LocalDate firstDate = LocalDate.parse(rangeTokens[0]);
      LocalDate secondDate = LocalDate.parse(rangeTokens[1]);

      if (firstDate.isBefore(secondDate)) {
        start = firstDate;
        end = secondDate;
      } else {
        start = secondDate;
        end = firstDate;
      }
    }

    /* WHEN USER CHOOSES DURATION AND END DATE */
    if (option == 2) {
      // regex for number of days/ week before end date
      String durationEndDateRegex = "^\\d+[wd]\\s+\\d{4}-\\d{2}-\\d{2}$";
      String durationEndValidFormat = "Correct format example: 12d 2021-03-04 or 3w 2021-03-04";

      dateRange = checkValidFormat(dateRange, durationEndDateRegex, durationEndValidFormat, sc);
      rangeTokens = dateRange.split("\s+");

      while (!isValidDate(rangeTokens[1])) {
        System.out.print("The date you have entered does not exist. Please try again.\n>>>\s");
        dateRange = sc.nextLine();
        dateRange = checkValidFormat(dateRange, durationEndDateRegex, durationEndValidFormat, sc);
        rangeTokens = dateRange.split("\s+");
      }

      end = LocalDate.parse(rangeTokens[1]);
      range = rangeTokens[0].split("[a-z]");
      numRange = Integer.parseInt(range[0]);
      timePeriod = rangeTokens[0].replaceFirst("[0-9]+", "").split("[0-9]+");

      if (timePeriod[0].matches("d")) {
        start = end.minusDays(numRange);
      } else {
        start = end.minusWeeks(numRange);
      }
    }

    /* WHEN USER CHOOSES START DATE AND DURATION */
    if (option == 3) {
      // regex for number of days/ week after start date
      String startDateDurationRegex = "^\\d{4}-\\d{2}-\\d{2}\\s+\\d+[wd]$";
      String startDurationValidFormat = "Correct format example: 2020-01-02 12d or 2020-01-02 3w";

      dateRange = checkValidFormat(dateRange, startDateDurationRegex, startDurationValidFormat, sc);
      rangeTokens = dateRange.split("\s+");

      while (!isValidDate(rangeTokens[0])) {
        System.out.print("The date you have entered does not exist. Please try again.\n>>>\s");
        dateRange = sc.nextLine();
        dateRange = checkValidFormat(dateRange, startDateDurationRegex, startDurationValidFormat, sc);
        rangeTokens = dateRange.split("\s+");
      }

      start = LocalDate.parse(rangeTokens[0]);
      range = rangeTokens[1].split("[a-z]");
      numRange = Integer.parseInt(range[0]);
      timePeriod = rangeTokens[1].replaceFirst("[0-9]+", "").split("[0-9]+");

      if (timePeriod[0].matches("d")) {
        end = start.plusDays(numRange);
      } else {
        end = start.plusWeeks(numRange);
      }
    }
  }

  /**
   * Prompt user to enter their input again if input has invalid format.
   * @param range <code>String</code>: user input
   * @param pattern <code>String</code>: regex pattern to check against
   * @param msg <code>String</code>: message to print correct format example if user input
   *                has invalid format
   * @param sc <code>Scanner</code>: scan user input again
   * @return user input string when the format is valid
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
}
