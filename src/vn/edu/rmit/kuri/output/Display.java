package vn.edu.rmit.kuri.output;

import java.util.ArrayList;
import vn.edu.rmit.kuri.input.Metric;
import vn.edu.rmit.kuri.input.ResultType;
import vn.edu.rmit.kuri.processing.Summary;
import vn.edu.rmit.kuri.test.TestSummary;

public class Display {

  /**
   * Create a table from the summary data and user's selections
   * @param summary Data that already has grouping
   * @param metric  Metric chosen by the user
   * @param resultType  Result type chosen by the user
   */
  public static void tabular(TestSummary summary, Metric metric, ResultType resultType) {
    // Dummy content TODO: Implement this
    // Create a table
    // Data from summary will be queried to return 2 arrays to add data to the table
    // The 1st array stores the Range and the 2nd stores the Value data
    // Elements with the same index from the 2 arrays matches each other
    // The headers of the table are Range and Value
    // metric and resultType will determine the data of the arrays
    // Table demo (the width can be changed):
    //+---------------------+---------------+
    //|         Range       |     Value     |
    //+---------------------+---------------+
    //|         .....       |     .....     |
    //|         .....       |     .....     |
    //|         .....       |     .....     |
    //+---------------------+---------------+


    // Extract needed values based on the metric
    // Stored data in a 2D ArrayList
    // Data from the same group will be in the same ArrayList
    ArrayList<ArrayList<Integer>> value = new ArrayList<>();
    for (int i = 0; i < summary.size(); i++) {
      ArrayList<Integer> valueGroup = new ArrayList<>();
      for (int j = 0; j <summary.get(i).size(); j++)
      {
        switch (metric) {
          case CASES -> {
            int k = summary.get(i).get(j).getNewCases();
            valueGroup.add(k);
          }
          case DEATHS -> {
            int k = summary.get(i).get(j).getNewDeaths();
            valueGroup.add(k);
          }
          case VACCINATIONS -> {
            int k = summary.get(i).get(j).getNewVaccinations();
            valueGroup.add(k);
          }
        }
      }
      value.add(valueGroup);
    }

    // Calculate the value to display based on the resultType
    // Each group's value will be stored as an integer in an ArrayList
    ArrayList<Integer> valueForDisplay = new ArrayList<>();
    int valueOfEachGroup = 0;
    for (ArrayList<Integer> integers : value) {
      for (int j = 0; j < integers.size(); j++) {
        valueOfEachGroup += integers.get(j);
        switch (resultType) {
          case CUMULATIVE -> {
            if (j == integers.size() - 1) {
              // do not reset value for cumulative results
              valueForDisplay.add(valueOfEachGroup);
            }
          }
          case NEW_PER_PERIOD -> {
            if (j == integers.size() - 1) {
              valueForDisplay.add(valueOfEachGroup);
              // reset value for the next group when reach the last value
              valueOfEachGroup = 0;
            }
          }
        }
      }
    }

    // Store each group's range in an ArrayList
    ArrayList<String> range = new ArrayList<>();
    for (int i = 0; i < summary.size(); i++) {
      int groupSize = summary.get(i).size();
      String startDate = summary.get(i).get(0).getDate().toString();
      String endDate = summary.get(i).get(groupSize - 1).getDate().toString();
      if (groupSize > 1) {
        range.add(startDate + " - " + endDate);
      } else {
        range.add(startDate);
      }
    }

    // Nested interface containing methods for creating a terminal-based table
    // Cells have their own vertical borderlines, horizontal borderlines methods must be used before or after the cell methods
    interface tableInterface {

      /**
       * Methods for creating first-column cells
       * @param width Width of the cell
       * @param value Value inside the cell
       * @return Return the padding, value, and 2 vertical borderlines for column cells
       */
      String cellWidthFirstCol(int width, String value);

      /**
       * Methods for creating non-first-column cells
       * @param width Width of the cell
       * @param value Value inside the cell
       * @return Return the padding, value, and 1 vertical borderline for non-first-column cells
       */
      String cellWidth(int width, String value);


      /**
       * Methods for creating a non-first-column horizontal borderline
       * @param width Width of the cell
       * @return <p>Return the conjunction of borderlines using "+", a vertical borderline
       * for non-first-column cells using "-"</p>
       */
      String horizontalBorder(int width);

      /**
       * Methods for creating a first-column horizontal borderline
       * @param width Width of the cell
       * @return <p>Return the conjunctions of borderlines using "+", a vertical borderline
       * for non-first-column cells using "-"</p>
       */
      String horizontalBorderFirstCol(int width);
    }

    // Anonymous class is used to provide methods that help improve expandability in case there are more columns needed
    tableInterface table = new tableInterface() {
      public String cellWidth(int width, String value) {
        String cell = " ";
        int padding = Math.round(((float) width - value.length()) / 2);
        cell = cell.repeat(padding);
        cell += value;
        if ((width - value.length()) % 2 == 1) {
          padding += 1;
        }
        cell += " ".repeat(padding) + "|";
        return cell;
      }

      public String cellWidthFirstCol(int width, String value) {
        String cell = cellWidth(width, value);
        cell = "|" + cell;
        return cell;
      }

      public String horizontalBorder(int width) {
        String border = "-";
        border = border.repeat(width);
        border += "+";
        return border;
      }

      public String horizontalBorderFirstCol(int width) {
        String border = horizontalBorder(width);
        border = "+" + border;
        return border;
      }
    };

    int rangeColWidth = 35;
    int valueColWidth = 10;

    System.out.println(table.horizontalBorderFirstCol(rangeColWidth) + table.horizontalBorder(valueColWidth));
    System.out.println(table.cellWidthFirstCol(rangeColWidth, "Range") + table.cellWidth(valueColWidth, "Value"));
    System.out.println(table.horizontalBorderFirstCol(rangeColWidth) + table.horizontalBorder(valueColWidth));

    for (int i = 0; i < range.size(); i++) {
      System.out.println(table.cellWidthFirstCol(rangeColWidth, range.get(i)) + table.cellWidth(valueColWidth, valueForDisplay.get(i).toString()));
    }
    System.out.println(table.horizontalBorderFirstCol(rangeColWidth) + table.horizontalBorder(valueColWidth));
  }

  public static void chart(Summary summary, Metric metric, ResultType resultType) {
    // Dummy content TODO: Implement this
    // Draw a textual chart
    // Create a 2D array to represent 24 rows x 80 cols
    // The 1st col and the last row will be left for groups and results
    // Data from summary will be queried to return a 2D array to add data to the chart
    // metric and resultType will determine the data of the 2D array
    System.out.println("To be implemented");
  }
}
