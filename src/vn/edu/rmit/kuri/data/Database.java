package vn.edu.rmit.kuri.data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Database implements DataArray<Data> {

  private static final HashSet<String> geoAreas = new HashSet<>();
  private final Data[] data;
  private final boolean cumulative;

  public Database(String path, boolean cumulative) throws FileNotFoundException {
    ArrayList<HashMap<String, String>> data = Csv.read(path);
    this.data = new Data[data.size()];
    this.cumulative = cumulative;

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

    if (this.cumulative) {
      String currentGeoArea = this.data[0].getGeoArea();
      int cumulativeCases = this.data[0].getCases();
      int cumulativeDeaths = this.data[0].getDeaths();
      for (int i = 1; i < this.data.length; i++) {
        if (this.data[i].getGeoArea().equals(currentGeoArea)) {
          cumulativeCases += this.data[i].getCases();
          this.data[i].setCases(cumulativeCases);
          cumulativeDeaths += this.data[i].getDeaths();
          this.data[i].setDeaths(cumulativeDeaths);
        } else {
          currentGeoArea = this.data[i].getGeoArea();
          cumulativeCases = this.data[i].getCases();
          cumulativeDeaths = this.data[i].getDeaths();
        }
      }
    }
  }

  public static String[] allGeoAreas() {
    String[] result = geoAreas.toArray(new String[0]);
    Arrays.sort(result);
    return result;
  }

  public int size() {
    return this.data.length;
  }

  public Data get(int index) {
    return this.data[index];
  }

  public boolean isCumulative() {
    return this.cumulative;
  }
}
