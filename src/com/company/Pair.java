package com.company;

public  class Pair<T> {
  T key;
  T val;

  public Pair(T key, T val) {
    this.key = key;
    this.val = val;
  }

  public T getKey() {
    return key;
  }

  public void setKey(T key) {
    this.key = key;
  }

  public T getVal() {
    return val;
  }

  public void setVal(T val) {
    this.val = val;
  }
}