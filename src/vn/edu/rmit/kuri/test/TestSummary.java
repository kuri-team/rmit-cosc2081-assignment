package vn.edu.rmit.kuri.test;

import java.time.LocalDate;
import java.util.ArrayList;
import vn.edu.rmit.kuri.data.Data;
import vn.edu.rmit.kuri.data.DataFilter;
import vn.edu.rmit.kuri.data.Database;

public class TestSummary {

  private final ArrayList<ArrayList<Data>> processedData = new ArrayList<>();

  TestSummary(Database database) {
    DataFilter data = new DataFilter("Vietnam", LocalDate.parse("2020-04-01"), LocalDate.parse("2020-04-01").plusDays(158), database);
    ArrayList<Data> group = new ArrayList<>();
    for (int i = 0; i < data.size(); i++) {
      if ((i + 1) % 2 != 0) {
        group.add(data.get(i));
      } else {
        group.add(data.get(i));
        this.processedData.add(group);
        group = new ArrayList<>();
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
