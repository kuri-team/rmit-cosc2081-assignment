package vn.edu.rmit.kuri.data;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataFilter implements DataArray<Data> {

  private final Data[] data;

  public DataFilter(String geoArea, LocalDate startDate, LocalDate endDate, Database database) {
    LocalDate prevDate = startDate.minusDays(1);
    ArrayList<Data> filteredData = new ArrayList<>();
    for (int i = 0; i < database.size(); i++) {
      if (
          database.get(i).getGeoArea().equals(geoArea) &&
              database.get(i).getDate().compareTo(startDate) >= 0 &&
              database.get(i).getDate().compareTo(endDate) <= 0
      ) {
        while (database.get(i).getDate().compareTo(prevDate) - 1 > 0) {
          filteredData.add(new Data(geoArea, prevDate.toString(), "0", "0", "0"));
          prevDate = prevDate.plusDays(1);
          System.out.println("fill!");
        }
        filteredData.add(database.get(i));
        prevDate = database.get(i).getDate();
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
