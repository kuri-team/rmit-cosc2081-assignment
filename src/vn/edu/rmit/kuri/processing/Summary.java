package vn.edu.rmit.kuri.processing;

import java.util.ArrayList;
import vn.edu.rmit.kuri.data.Data;
import vn.edu.rmit.kuri.data.DataArray;
import vn.edu.rmit.kuri.data.DataFilter;
import vn.edu.rmit.kuri.data.Database;
import vn.edu.rmit.kuri.input.DateRange;
import vn.edu.rmit.kuri.input.Grouping;

public class Summary implements DataArray<ArrayList<Data>> {

  private final ArrayList<ArrayList<Data>> processedData;

  public Summary(String geoArea, DateRange dateRange, Grouping grouping, Database database) {
    DataFilter filteredData = new DataFilter(
        geoArea,
        dateRange,
        database
    );
    this.processedData = new ArrayList<>();

    switch (grouping.getType()) {
      case NONE -> {
        for (int i = 0; i < filteredData.size(); i++) {
          ArrayList<Data> group = new ArrayList<>();
          group.add(filteredData.get(i));
          this.processedData.add(group);
        }
      }
      case N_GROUPS -> {
        int daysPerGroup = filteredData.size() / grouping.getGroupingNum();
        int groupCount = 0;
        ArrayList<Data> group = new ArrayList<>();
        for (int i = 0; i < filteredData.size(); i++) {
          group.add(filteredData.get(i));
          if ((i + 1) % daysPerGroup == 0 && groupCount < grouping.getGroupingNum() - 1) {
            this.processedData.add(group);
            groupCount++;
            group = new ArrayList<>();
          } else if (i == filteredData.size() - 1) {
            this.processedData.add(group);
          }
        }
      }
      case N_DAYS_PER_GROUP -> {
        ArrayList<Data> group = new ArrayList<>();
        for (int i = 0; i < filteredData.size(); i++) {
          group.add(filteredData.get(i));
          if ((i + 1) % grouping.getGroupingNum() == 0) {
            this.processedData.add(group);
            group = new ArrayList<>();
          }
        }
      }
    }
  }

  public int size() {
    return this.processedData.size();
  }

  public ArrayList<Data> get(int index) {
    return this.processedData.get(index);
  }
}
