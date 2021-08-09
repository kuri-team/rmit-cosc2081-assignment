package vn.edu.rmit.kuri.test;

import vn.edu.rmit.kuri.data.Database;
import vn.edu.rmit.kuri.input.Metric;
import vn.edu.rmit.kuri.input.ResultType;
import vn.edu.rmit.kuri.output.Display;

public class Test {

  public static void main(Database database) {
    TestSummary summary = new TestSummary(database);
    Display.tabular(summary, Metric.CASES, ResultType.NEW_PER_PERIOD);
  }
}
