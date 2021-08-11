package vn.edu.rmit.kuri.input;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateRange {

  private LocalDate start;
  private LocalDate end;

  /**
   * @param option <code>int</code>. Format type.
   * @param dateRange <code>String</code> in these possible formats:<br />
   *              <ul>
   *                <li>Type 1: <code>yyyy-MM-dd yyyy-MM-dd</code> (start date -> end date, inclusive). Example: <code>2020-01-02 2021-03-04</code></li>
   *                <li>Type 2: Number of days or weeks before end date, inclusive. Example: <code>12d 2021-03-04</code></li>
   *                <li>Type 3: Number of days or weeks after start date, inclusive. Example: <code>2020-01-02 12d</code></li>
   *              </ul>
   */
  public DateRange(int option, String dateRange) {
    Scanner sc = new Scanner(System.in);
    String[] rangeTokens;
    String range, timePeriod;
    int numRange;

    /* WHEN USER CHOOSES PAIR DATES */
    if (option == 1) {
      // regex for start and end dates format
      String datesRegex = "^\\d{4}-\\d{2}-\\d{2}\\s+\\d{4}-\\d{2}-\\d{2}$";
      // message to print when input entered has invalid format
      String datesValidFormat = "Correct format example: 2020-01-02 2021-03-04";

      dateRange = checkValidFormat(dateRange, datesRegex, datesValidFormat, sc);
      // only split user input when input has valid format. if input has invalid
      // format => splitting may lead to error or incorrect data processing afterwards
      rangeTokens = dateRange.split("\s+");

      // check if either date is correct or if the 2 dates are the same
      while (!isValidDate(rangeTokens[0]) || !isValidDate(rangeTokens[1])
          || rangeTokens[0].equals(rangeTokens[1])) {
        System.out.print("The date you have entered does not exist, or the 2 dates entered "
            + "are identical. Please try again.\n>>>\s");
        dateRange = sc.nextLine();

        // need to check the format of user input again before it can be split
        dateRange = checkValidFormat(dateRange, datesRegex, datesValidFormat, sc);
        rangeTokens = dateRange.split("\s+");
      }

      // parse to LocalDate only when all conditions are met
      LocalDate firstDate = LocalDate.parse(rangeTokens[0]);
      LocalDate secondDate = LocalDate.parse(rangeTokens[1]);

      // check which date is before to set it as start date
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

      // only need to check one date input
      while (!isValidDate(rangeTokens[1])) {
        System.out.print("The date you have entered does not exist. Please try again.\n>>>\s");
        dateRange = sc.nextLine();
        dateRange = checkValidFormat(dateRange, durationEndDateRegex, durationEndValidFormat, sc);
        rangeTokens = dateRange.split("\s+");
      }

      end = LocalDate.parse(rangeTokens[1]);

      // replace letters with empty string to only get the number part of the string
      range = rangeTokens[0].replaceFirst("[wd]", "");
      numRange = Integer.parseInt(range);

      timePeriod = rangeTokens[0].replaceFirst("[0-9]+", "");

      if (timePeriod.matches("d")) {
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
      range = rangeTokens[1].replaceFirst("[wd]", "");
      numRange = Integer.parseInt(range);
      timePeriod = rangeTokens[1].replaceFirst("[0-9]+", "");

      if (timePeriod.matches("d")) {
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
   * @return user input string only when the format is valid
   */
  private static String checkValidFormat(String range, String pattern, String msg, Scanner sc) {
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
  private static boolean isValidDate(String date) {
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

  /**
   * @return the number of days between the start and end date
   */
  public long getNumDays() {
    // add 1 because the original method misses 1 day
    return (DAYS.between(start, end) + 1);
  }

}
