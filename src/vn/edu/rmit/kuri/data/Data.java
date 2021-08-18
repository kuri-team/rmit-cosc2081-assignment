package vn.edu.rmit.kuri.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Data {
  private final String geoArea;
  private final LocalDate date;
  private int newCases;
  private int newDeaths;
  private int newVaccinations;

  public Data(String geoArea, String date, String newCases, String newDeaths, String newVaccinations) {
    this.geoArea = geoArea;
    this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("M/d/yyyy"));
    this.newCases = newCases == null || newCases.equals("") ? 0 : Integer.parseInt(newCases);
    this.newDeaths = newDeaths == null || newDeaths.equals("") ? 0 : Integer.parseInt(newDeaths);
    this.newVaccinations = newVaccinations == null || newVaccinations.equals("") ? 0 : Integer.parseInt(newVaccinations);
  }

  @Override
  public String toString() {
    return this.getClass() + "\n{"
        + "\n\tgeoArea: " + this.geoArea
        + "\n\tdate: " + this.date.toString()
        + "\n\tnewCase: " + this.newCases
        + "\n\tnewDeaths: " + this.newDeaths
        + "\n\tnewVaccinations: " + this.newVaccinations
        + "\n}\n";
  }

  public String getGeoArea() {
    return geoArea;
  }

  public LocalDate getDate() {
    return date;
  }

  public int getNewCases() {
    return newCases;
  }

  public int getNewDeaths() {
    return newDeaths;
  }

  public int getNewVaccinations() {
    return newVaccinations;
  }

  public void setNewCases(int newCases) {
    this.newCases = newCases;
  }

  public void setNewDeaths(int newDeaths) {
    this.newDeaths = newDeaths;
  }

  public void setNewVaccinations(int newVaccinations) {
    this.newVaccinations = newVaccinations;
  }

  public void setNewVaccinationsPerDay(int i, Database database) {
    int dtbCurrentValue = database.get(i).getNewVaccinations();

    if (dtbCurrentValue == 0 || (i > 0 && dtbCurrentValue < database.get(i - 1).getNewVaccinations())) {
      // if current accumulative value is 0, null, or less than previous value,
      // set the new vaccination as 0
      this.setNewVaccinations(0);

    } else {
      int j = 1;
      // make sure that index doesn't get out of bounds
      // and two data are in the same geoArea
      while (j <= i && database.get(i - j).getGeoArea().equals(database.get(i).getGeoArea())) {
        if (database.get(i - j).getNewVaccinations() != 0) {
          this.setNewVaccinations(dtbCurrentValue - database.get(i - j).getNewVaccinations());
          return;
        }
        j++;
      }
      // set new vaccination as current accumulative value if all data above it
      // are 0 or null
      this.setNewVaccinations(dtbCurrentValue);
    }
  }
}
