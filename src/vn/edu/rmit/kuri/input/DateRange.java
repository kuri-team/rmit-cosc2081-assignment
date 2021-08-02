package vn.edu.rmit.kuri.input;

import java.time.LocalDate;

public class DateRange {

  private LocalDate start;
  private LocalDate end;

  public DateRange(String range) {
    // process the String range into start date and end date
  }

  public LocalDate getStart() {
    return this.start;
  }

  public LocalDate getEnd() {
    return this.end;
  }
}
