package com.company;

public class TimeMaxTimeInDBPair {
  private int time;
  private int maxtimeInDB;

  public TimeMaxTimeInDBPair(int time, int maxtimeInDB) {
    this.time = time;
    this.maxtimeInDB = maxtimeInDB;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public int getMaxtimeInDB() {
    return maxtimeInDB;
  }

  public void setMaxtimeInDB(int maxtimeInDB) {
    this.maxtimeInDB = maxtimeInDB;
  }
}

