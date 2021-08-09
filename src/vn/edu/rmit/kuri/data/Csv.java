package vn.edu.rmit.kuri.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Csv {

  /**
   * Reads a .csv file and return it as an <code>ArrayList</code> containing each data row as a
   * <code>HashMap</code>. <br /><br /><strong>Note:</strong> The 1st row will be considered the
   * header row and the <code>HashMap</code> keys for subsequent data rows are generated using
   * values in this row.
   *
   * @param path Path to the .csv file to be read
   * @return <code>ArrayList&lt;HashMap&lt;String, String&gt;&gt;</code>
   * @throws FileNotFoundException If the path is invalid
   */
  public static ArrayList<HashMap<String, String>> read(String path) throws FileNotFoundException {
    ArrayList<HashMap<String, String>> data = new ArrayList<>();
    Scanner sc = new Scanner(new File(path));

    if (!sc.hasNextLine()) {  // Return an empty ArrayList if the file is empty
      return data;
    }

    String[] header = sc.nextLine().split(",");
    while (sc.hasNextLine()) {
      HashMap<String, String> row = new HashMap<>();

      String[] line = sc.nextLine().split(",");
      for (int i = 0; i < line.length; i++) {
        row.put(header[i], line[i]);
      }
      data.add(row);
    }

    return data;
  }
}
