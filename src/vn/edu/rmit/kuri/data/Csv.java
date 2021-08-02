package vn.edu.rmit.kuri.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Csv {

  public static ArrayList<HashMap<String, String>> read(String path) throws FileNotFoundException {
    ArrayList<HashMap<String, String>> data = new ArrayList<>();

    Scanner sc = new Scanner(new File(path));
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
