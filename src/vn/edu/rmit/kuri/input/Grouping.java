package vn.edu.rmit.kuri.input;

public class Grouping {

  private final GroupingType type;
  private int groupingNum = 0;

  public Grouping() {
    this.type = GroupingType.NONE;
  }

  public Grouping(int groupingNum, GroupingType type) {
    this.type = type;
    this.groupingNum = groupingNum;
  }

  public GroupingType getType() {
    return this.type;
  }

  public int getGroupingNum() {
    return this.groupingNum;
  }
}
