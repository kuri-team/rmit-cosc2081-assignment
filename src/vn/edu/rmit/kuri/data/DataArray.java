package vn.edu.rmit.kuri.data;

public interface DataArray<T> {
  int size();
  T get(int index);
}
