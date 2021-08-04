package vn.edu.rmit.kuri.data;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataFilter implements DataArray<Data> {

  private final Data[] data;

  public DataFilter(String geoArea, LocalDate startDate, LocalDate endDate, Database database) {
    ArrayList<Data> filteredData = new ArrayList<>();
    for (int i = 0; i < database.size(); i++) {
      if (
          database.get(i).getGeoArea().equals(geoArea) &&
              database.get(i).getDate().compareTo(startDate) >= 0 &&
              database.get(i).getDate().compareTo(endDate) <= 0
      ) {
        filteredData.add(database.get(i));
      }
    }

    this.data = filteredData.toArray(new Data[0]);
  }

  public int size() {
    return this.data.length;
  }

  public Data get(int index) {
    return this.data[index];
  }
}
