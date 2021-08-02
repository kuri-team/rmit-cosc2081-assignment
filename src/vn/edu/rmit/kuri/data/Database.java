package vn.edu.rmit.kuri.data;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Database implements DataArray {

  private final Data[] data;
  private final HashSet<String> geoAreas;

  public Database(String path) throws FileNotFoundException {
    ArrayList<HashMap<String, String>> data = Csv.read(path);
    this.data = new Data[data.size()];
    this.geoAreas = new HashSet<>();

    for (int i = 0; i < this.data.length; i++) {
      HashMap<String, String> row = data.get(i);
      this.data[i] = new Data(
          row.get("location"),
          row.get("date"),
          row.get("new_cases"),
          row.get("new_deaths"),
          row.get("people_vaccinated")
      );
      geoAreas.add(row.get("location"));
    }
  }

  public int size() {
    return this.data.length;
  }

  public Data get(int index) {
    return this.data[index];
  }

  public LocalDate getEarliestDate() {
    LocalDate now = LocalDate.now();
    LocalDate earliestDate = now;

    for (Data row : this.data) {
      if (
          Duration.between(row.getDate().atStartOfDay(), now.atStartOfDay()).toDays() >
              Duration.between(earliestDate.atStartOfDay(), now.atStartOfDay()).toDays()
      ) {
        earliestDate = row.getDate();
      }
    }

    return earliestDate;
  }

  public String[] allGeoAreas() {
    String[] result = this.geoAreas.toArray(new String[0]);
    Arrays.sort(result);
    return result;
  }

  public LocalDate getLatestDate() {
    LocalDate now = LocalDate.now();
    LocalDate latestDate = this.getEarliestDate();

    for (Data row : this.data) {
      if (
          Duration.between(row.getDate().atStartOfDay(), now.atStartOfDay()).toDays() <
              Duration.between(latestDate.atStartOfDay(), now.atStartOfDay()).toDays()
      ) {
        latestDate = row.getDate();
      }
    }

    return latestDate;
  }
}
