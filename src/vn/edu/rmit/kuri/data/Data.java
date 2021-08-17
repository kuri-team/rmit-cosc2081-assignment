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

    if (dtbCurrentValue == 0) {
      this.setNewVaccinations(0);

    } else {
      int j = 1;
      while (j <= i) {
        if (database.get(i - j).getNewVaccinations() != 0) {
          this.setNewVaccinations(dtbCurrentValue - database.get(i - j).getNewVaccinations());
          return;
        }
        j++;
      }
      this.setNewVaccinations(dtbCurrentValue);
    }
  }
}
