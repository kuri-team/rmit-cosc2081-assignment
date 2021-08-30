package vn.edu.rmit.kuri.output;

import java.util.ArrayList;
import vn.edu.rmit.kuri.input.Metric;
import vn.edu.rmit.kuri.processing.Summary;

public class Display {

  /**
   * Create a table from the summary data and user's selections
   *
   * @param summary    Data that already has grouping
   * @param metric     Metric chosen by the user
   */
  public static void tabular(Summary summary, Metric metric) {
    // Create a table
    // Data from summary will be queried to return 2 arrays to add data to the table
    // The 1st array stores the Range and the 2nd stores the Value data
    // Elements with the same index from the 2 arrays matches each other
    // The headers of the table are Range and Value
    // metric will determine the data of the arrays
    // Table demo (the width can be changed):
    //+---------------------+---------------+
    //|         Range       |     Value     |
    //+---------------------+---------------+
    //|         .....       |     .....     |
    //|         .....       |     .....     |
    //|         .....       |     .....     |
    //+---------------------+---------------+

    ArrayList<Integer> valueForDisplay = query(summary, metric);

    // Store each group's range in an ArrayList
    ArrayList<String> range = groupRange(summary);

    // Nested interface containing methods for creating a terminal-based table
    // Cells have their own vertical borderlines, horizontal borderlines methods must be used before or after the cell methods
    interface tableInterface {

      /**
       * Methods for creating first-column cells
       * @param width Width of the cell
       * @param value Value inside the cell
       * @return Return the padding, value, and 2 vertical borderlines for first-column cells
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
       * for first-column cells using "-"</p>
       */
      String horizontalBorderFirstCol(int width);
    }

    // Anonymous class is used to provide methods that help improve expandability in case there are more columns needed
    tableInterface table = new tableInterface() {
      public String cellWidth(int width, String value) {
        String cell = " ";
        int padding = (width - value.length());
        cell = " " + value + cell.repeat(padding - 1) + "|";
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
    int valueColWidth = 15;

    System.out.println(
        table.horizontalBorderFirstCol(rangeColWidth) + table.horizontalBorder(valueColWidth));
    System.out.println(
        table.cellWidthFirstCol(rangeColWidth, "Range") + table.cellWidth(valueColWidth, "Value"));
    System.out.println(
        table.horizontalBorderFirstCol(rangeColWidth) + table.horizontalBorder(valueColWidth));

    for (int i = 0; i < range.size(); i++) {
      System.out.println(
          table.cellWidthFirstCol(rangeColWidth, range.get(i)) + table.cellWidth(valueColWidth,
              valueForDisplay.get(i).toString()));
    }
    System.out.println(
        table.horizontalBorderFirstCol(rangeColWidth) + table.horizontalBorder(valueColWidth));
  }

  /**
   * Create a chart from the summary data and user's selections
   *
   * @param summary    Data that already has grouping
   * @param metric     Metric chosen by the user
   */
  public static void chart(Summary summary, Metric metric) {
    // Draw a textual chart
    // Create a 2D array to represent 24 rows x 80 cols
    // The 1st col and the last row will be left for groups and results
    // Data from summary will be queried to return a 2D array to add data to the chart
    // metric will determine the data of the 2D array

    // row is also chart's vertical length
    // col is also represent chart's horizontal length
    int vertical = 24;
    int horizontal = 80;
    int spacing;
    Character[][] displayChart = new Character[vertical][horizontal];
    ArrayList<Integer> valueForDisplay = query(summary, metric);
    ArrayList<Integer> valuePositionOnChart = new ArrayList<>();

    // check if the size of summary processed data is more than 79 groups.
    if (summary.size() > 79) {
      System.out.println("Cannot draw chart with more than 79 groups.");
      return;
    }

    // find the max value to calculate the position of the * based on it
    // each * present a value from a group
    int max = 0;
    int zeroCount = 0;

    for (Integer i : valueForDisplay) {
      if (max < i) {
        max = i;
      }
      if (i == 0) {
      zeroCount += 1;
      }
    }

    // store position values in an ArrayList
    for (Integer value : valueForDisplay) {
      // the position of value is usually from the x axis which means from the bottom
      // the program prints from the top, the distance from the top to the * must be calculated
      // the distance must be rounded due to the limit of the program (only represent * with integer position)
      // the reason for -2: -1 for the double array start from 0 rather than 1, -1 for the 1st col and the last row is used for axes
      int position = vertical - 2;  // last row in the display area
      if (zeroCount != valueForDisplay.size()) {
      position = Math.round((vertical - 2) - (((float) value / max) * (vertical - 2)));
      }
      valuePositionOnChart.add(position);
    }

    spacing = (int) Math.floor((float) (horizontal - 1) / valueForDisplay.size());  // TODO: This is not accurate. Fix it.

    // Replace null elements with ' '
    // Elements with other values will be modified later
    for (int i = 0; i < vertical; i++) {
      for (int j = 0; j < horizontal; j++) {
        displayChart[i][j] = ' ';
      }
    }

    int index = 0;
    // Elements represent the value is replaced with '*'
    for (int i = 1; i <= valuePositionOnChart.size() * spacing; i += spacing) {
      displayChart[valuePositionOnChart.get(index)][i] = '*';
      index += 1;
    }

    // Elements for drawing axes will be replaced with '|' or '_'
    for (int i = 0; i < vertical; i++) {
      for (int j = 0; j < horizontal; j++) {
        if (i == vertical - 1) {
          displayChart[i][j] = '_';
        }
        if (j == 0) {
          displayChart[i][j] = '|';
        }
      }
    }

    // Print the chart
    for (int i = 0; i < vertical; i++) {
      for (int j = 0; j < horizontal; j++) {
        if (j != horizontal - 1) {
          System.out.print(displayChart[i][j]);
        } else {
          System.out.println(displayChart[i][j]);
        }
      }
    }
  }

  /**
   * Query from already grouped data that fits the metric
   *
   * @param summary    Data that already has grouping
   * @param metric     Metric chosen by the user
   * @return an arraylist that contains value for display
   */
  private static ArrayList<Integer> query(Summary summary, Metric metric) {
    // Extract needed values based on the metric
    // Stored data in a 2D ArrayList
    // Data from the same group will be in the same ArrayList
    ArrayList<ArrayList<Integer>> value = new ArrayList<>();
    for (int i = 0; i < summary.size(); i++) {
      ArrayList<Integer> valueGroup = new ArrayList<>();
      for (int j = 0; j < summary.get(i).size(); j++) {
        switch (metric) {
          case CASES -> {
            int k = summary.get(i).get(j).getCases();
            valueGroup.add(k);
          }
          case DEATHS -> {
            int k = summary.get(i).get(j).getDeaths();
            valueGroup.add(k);
          }
          case VACCINATIONS -> {
            int k = summary.get(i).get(j).getVaccinations();
            valueGroup.add(k);
          }
        }
      }
      value.add(valueGroup);
    }

    // Calculate the value to display
    // Each group's value will be stored as an integer in an ArrayList
    ArrayList<Integer> valueForDisplay = new ArrayList<>();
    int valueOfEachGroup = 0;
    for (ArrayList<Integer> integers : value) {
      for (int j = 0; j < integers.size(); j++) {
        valueOfEachGroup += integers.get(j);
        if (j == integers.size() - 1) {
          valueForDisplay.add(valueOfEachGroup);
          // reset value for the next group when reach the last value
          valueOfEachGroup = 0;
        }
      }
    }

    return valueForDisplay;
  }

  /**
   * Store each group's range in an ArrayList
   *
   * @param summary Data that already has grouping
   * @return an arraylist of strings that represent group's range for display
   */
  private static ArrayList<String> groupRange(Summary summary) {
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

    return range;
  }
}
