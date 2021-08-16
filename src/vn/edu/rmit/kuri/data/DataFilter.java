package vn.edu.rmit.kuri.data;

import java.time.format.DateTimeFormatter;
import vn.edu.rmit.kuri.input.DateRange;

public class DataFilter implements DataArray<Data> {

  private final Data[] data;

  public DataFilter(String geoArea, DateRange dateRange, Database database) {
    // Populate this.data with blank entries
    this.data = new Data[(int) dateRange.getDurationInDays()];
    for (int i = 0; i < this.data.length; i++) {
      this.data[i] = new Data(
              geoArea,
              dateRange.getStart().plusDays(i).format(DateTimeFormatter.ofPattern("M/d/yyyy")),
              "0",
              "0",
              "0"
      );
    }

    // Populate this.data with actual data, while skipping the missing dates
    for (int i = 0; i < database.size(); i++) {
      if (
          database.get(i).getGeoArea().equals(geoArea) &&
              database.get(i).getDate().compareTo(dateRange.getStart()) >= 0 &&
              database.get(i).getDate().compareTo(dateRange.getEnd()) <= 0
      ) {
        for (Data entry : this.data) {
          if (database.get(i).getDate().compareTo(entry.getDate()) == 0) {
            entry.setNewCases(database.get(i).getNewCases());
            entry.setNewDeaths(database.get(i).getNewDeaths());
            entry.setNewVaccinations(database.get(i).getNewVaccinations() - database.get(i - 1).getNewVaccinations());
            break;
          }
        }
      }
    }
  }

  public void setNewVaccinationsPerDay(int newVaccinations) {

  }

  public int size() {
    return this.data.length;
  }

  public Data get(int index) {
    return this.data[index];
  }
}
