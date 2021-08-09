package vn.edu.rmit.kuri.processing;

import java.util.ArrayList;
import vn.edu.rmit.kuri.data.Data;
import vn.edu.rmit.kuri.data.DataArray;
import vn.edu.rmit.kuri.data.DataFilter;
import vn.edu.rmit.kuri.data.Database;
import vn.edu.rmit.kuri.input.DateRange;
import vn.edu.rmit.kuri.input.Grouping;

public class Summary implements DataArray<ArrayList<Data>> {

  private final DataFilter filteredData;
  private final ArrayList<ArrayList<Data>> processedData;

  public Summary(String geoArea, DateRange dateRange, Grouping grouping, Database database) {
    this.filteredData = new DataFilter(geoArea, dateRange.getStart(), dateRange.getEnd(), database);
    this.processedData = new ArrayList<>();

    // Implementation TODO: Turn filtered data into processed data using grouping and groupingNum from parameter
    switch (grouping.getType()) {
      case NONE -> {}  // do processing
      case N_GROUPS -> {}  // do processing
      case N_DAYS_PER_GROUP -> {}  // do processing
    }
  }

  public int size() {
    return this.processedData.size();
  }

  public ArrayList<Data> get(int index) {
    return this.processedData.get(index);
  }
}
