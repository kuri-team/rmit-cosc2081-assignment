package vn.edu.rmit.kuri.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataFilter implements DataArray<Data> {

  private final Data[] data;

  public DataFilter(String geoArea, LocalDate startDate, LocalDate endDate, Database database) {
    // Populate this.data with blank entries
    this.data = new Data[endDate.compareTo(startDate) + 1];
    for (int i = 0; i < endDate.compareTo(startDate) + 1; i++) {
      this.data[i] = new Data(
              geoArea,
              startDate.plusDays(i).format(DateTimeFormatter.ofPattern("M/d/yyyy")),
              "0",
              "0",
              "0"
      );
    }

    // Populate this.data with actual data, while skipping the missing dates
    for (int i = 0; i < database.size(); i++) {
      if (
          database.get(i).getGeoArea().equals(geoArea) &&
              database.get(i).getDate().compareTo(startDate) >= 0 &&
              database.get(i).getDate().compareTo(endDate) <= 0
      ) {
        for (Data entry : this.data) {
          if (database.get(i).getDate().compareTo(entry.getDate()) == 0) {
            entry.setNewCases(database.get(i).getNewCases());
            entry.setNewDeaths(database.get(i).getNewDeaths());
            entry.setNewVaccinations(database.get(i).getNewVaccinations());
            break;
          }
        }
      }
    }
  }

  public int size() {
    return this.data.length;
  }

  public Data get(int index) {
    return this.data[index];
  }
}
