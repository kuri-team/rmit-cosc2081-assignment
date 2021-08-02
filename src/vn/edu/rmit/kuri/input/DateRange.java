package vn.edu.rmit.kuri.input;

import java.time.LocalDate;

public class DateRange {

  private LocalDate start;
  private LocalDate end;

  /**
   * @param range <code>String</code>. Possible format:<br />
   *              <ul>
   *                <li><code>yyyy-MM-dd yyyy-MM-dd</code> (start date -> end date, inclusive). Example: <code>2020-01-02 2021-03-04</code></li>
   *                <li>Number of days before end date, inclusive. Example: <code>12d 2021-03-04</code></li>
   *                <li>Number of weeks before end date, inclusive. Example: <code>3w 2021-03-04</code></li>
   *                <li>Number of days after start date, inclusive. Example: <code>2020-01-02 12d</code></li>
   *                <li>Number of weeks after start date, inclusive. Example: <code>2020-01-02 3w</code></li>
   *              </ul>
   */
  public DateRange(String range) {
    // Implementation TODO: Process the String range into start date and end date
    // Possible format:
    //  	yyyy-MM-dd yyyy-MM-dd (start date -> end date, inclusive). Example: 2020-01-02 2021-03-04
    //    Number of days before end date, inclusive. Example: 12d 2021-03-04
    //    Number of weeks before end date, inclusive. Example: 3w 2021-03-04
    //    Number of days after start date, inclusive. Example: 2020-01-02 12d
    //    Number of weeks after start date, inclusive. Example: 2020-01-02 3w
  }

  public LocalDate getStart() {
    return this.start;
  }

  public LocalDate getEnd() {
    return this.end;
  }
}
