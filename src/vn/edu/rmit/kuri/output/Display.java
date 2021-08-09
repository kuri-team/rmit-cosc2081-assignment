package vn.edu.rmit.kuri.output;

import vn.edu.rmit.kuri.input.Metric;
import vn.edu.rmit.kuri.input.ResultType;
import vn.edu.rmit.kuri.processing.Summary;
import vn.edu.rmit.kuri.test.TestSummary;

public class Display {

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
    System.out.println("To be implemented");
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
