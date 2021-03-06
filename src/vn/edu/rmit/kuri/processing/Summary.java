package vn.edu.rmit.kuri.processing;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
        int temp = daysPerGroup;
        int groupWithMoreDays = filteredData.size() - daysPerGroup * grouping.getGroupingNum();

        ArrayList<Data> group = new ArrayList<>();
        for (int i = 0; i < filteredData.size(); i++) {
          group.add(filteredData.get(i));
          // When the number of days is not divisible by the number of the groups,
          // there must be groups with Math.round(numOfDays/numOfGroups) days and
          // groups with Math.floor(numOfDays/numOfGroups) days.
          // Groups with Math.round(numOfDays/numOfGroups) will be likely to appear earlier
          // in the result.
          if (groupWithMoreDays > 0) {
            daysPerGroup = temp + 1;
          } else {
            daysPerGroup = temp;
          }
          if (group.size() == daysPerGroup) {
            if (database.isCumulative()) {
              this.processedData.add(
                  new ArrayList<>(
                      Arrays.asList(
                          new Data(geoArea, group.get(0).getDate()
                              .format(DateTimeFormatter.ofPattern("M/d/yyyy")), "0", "0", "0"),
                          // Dummy group start data for Display to access the group's start date
                          group.get(group.size() - 1)) // Actual cumulative data
                  )
              );
            } else {
              this.processedData.add(group);
            }
            group = new ArrayList<>();
            groupWithMoreDays -= 1;
          }
        }
      }
      case N_DAYS_PER_GROUP -> {
        ArrayList<Data> group = new ArrayList<>();
        for (int i = 0; i < filteredData.size(); i++) {
          group.add(filteredData.get(i));
          if ((i + 1) % grouping.getGroupingNum() == 0) {
            if (database.isCumulative()) {
              this.processedData.add(
                  new ArrayList<>(
                      Arrays.asList(
                          new Data(geoArea, group.get(0).getDate()
                              .format(DateTimeFormatter.ofPattern("M/d/yyyy")), "0", "0", "0"),
                          // Dummy group start data for Display to access the group's start date
                          group.get(group.size() - 1)) // Actual cumulative data
                  )
              );
            } else {
              this.processedData.add(group);
            }
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
