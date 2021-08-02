package vn.edu.rmit.kuri.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Data {
  private final String geoArea;
  private final LocalDate date;
  private final int newCases;
  private final int newDeaths;
  private final int newVaccinations;

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
}