package vn.edu.rmit.kuri.output;

import vn.edu.rmit.kuri.input.Metric;
import vn.edu.rmit.kuri.input.ResultType;
import vn.edu.rmit.kuri.processing.Summary;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Display {

  public static void tabular(Summary summary, Metric metric, ResultType resultType) {
    // Dummy content TODO: Implement this
    // Imported classes for creating table: Jtable, Jframe, JScrollPane
    // Data from summary will be queried to return a 2D array to add data to the table
    // metric and resultType will determine the data of the 2D array
    // The table will 2 have 2 columns: Range and Value
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
