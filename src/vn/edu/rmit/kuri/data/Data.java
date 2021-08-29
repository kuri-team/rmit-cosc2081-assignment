package vn.edu.rmit.kuri.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Data {
  private final String geoArea;
  private final LocalDate date;
  private int cases;
  private int deaths;
  private int vaccinations;

  public Data(String geoArea, String date, String cases, String deaths, String vaccinations) {
    this.geoArea = geoArea;
    this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("M/d/yyyy"));
    this.cases = cases == null || cases.equals("") ? 0 : Integer.parseInt(cases);
    this.deaths = deaths == null || deaths.equals("") ? 0 : Integer.parseInt(deaths);
    this.vaccinations = vaccinations == null || vaccinations.equals("") ? 0 : Integer.parseInt(vaccinations);

    if (this.cases < 0 || this.deaths < 0 || this.vaccinations < 0) {
      this.cases = this.deaths = this.vaccinations = 0;
    }
  }

  @Override
  public String toString() {
    return this.getClass() + "\n{"
        + "\n\tgeoArea: " + this.geoArea
        + "\n\tdate: " + this.date.toString()
        + "\n\tcases: " + this.cases
        + "\n\tdeaths: " + this.deaths
        + "\n\tvaccinations: " + this.vaccinations
        + "\n}\n";
  }

  public String getGeoArea() {
    return geoArea;
  }

  public LocalDate getDate() {
    return date;
  }

  public int getCases() {
    return cases;
  }

  public int getDeaths() {
    return deaths;
  }

  public int getVaccinations() {
    return vaccinations;
  }

  public void setCases(int cases) {
    this.cases = cases;
  }

  public void setDeaths(int deaths) {
    this.deaths = deaths;
  }

  public void setVaccinations(int vaccinations) {
    this.vaccinations = vaccinations;
  }

  public void setNewVaccinationsPerDay(int i, Database database) {
    int dtbCurrentValue = database.get(i).getVaccinations();

    if (dtbCurrentValue == 0 || (i > 0 && dtbCurrentValue < database.get(i - 1).getVaccinations())) {
      // if current accumulative value is 0, null, or less than previous value,
      // set the new vaccination as 0
      this.setVaccinations(0);

    } else {
      int j = 1;
      // make sure that index doesn't get out of bounds and two data are in the same geoArea
      while (j <= i && database.get(i - j).getGeoArea().equals(database.get(i).getGeoArea())) {
        if (database.get(i - j).getVaccinations() != 0) {
          this.setVaccinations(dtbCurrentValue - database.get(i - j).getVaccinations());
          return;
        }
        j++;
      }
      // set new vaccination as current accumulative value if all data above it
      // are 0 or null
      this.setVaccinations(dtbCurrentValue);
    }
  }

  public void setCumulativeVaccinations(int i, Database database) {
    int dtbCurrentValue = database.get(i).getVaccinations();
    if (dtbCurrentValue == 0 || (i > 0 && dtbCurrentValue < database.get(i - 1).getVaccinations())) {
      // if current accumulative value is 0, null, or less than previous day's value,
      // set the new vaccination as the previous day's value
      this.setVaccinations(database.get(i - 1).getVaccinations());
    } else {
      this.setVaccinations(database.get(i).getVaccinations());
    }
  }
}
