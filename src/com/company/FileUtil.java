package com.company;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class FileUtil {
  public static Map<String, int[][]> getObserverHintDetailsMapFromCSV(String fileName) {
    try {
      Reader in = new FileReader(fileName);
      String[] HEADERS = {"rawObserverId", "maxTimeInDb", "_time"};
      Iterable<CSVRecord> records = CSVFormat.DEFAULT
          .withHeader(HEADERS)
          .withFirstRecordAsHeader()
          .parse(in);
      Map<String, int[][]> observerHintDetailsMap = new HashMap<>();
      for (CSVRecord record : records) {
        String observerHint = record.get(HEADERS[0]);
        try {
          String maxTimeDbs = record.get(HEADERS[1]);
          String times = record.get(HEADERS[2]);
          var values = new int[][] {getSplitDoubles(times), getSplitDoubles(maxTimeDbs)};
          List<TimeMaxTimeInDBPair> list = new ArrayList<>(values.length);
          for (int i = 0; i < values[0].length; i++) {
            list.add(new TimeMaxTimeInDBPair(values[0][i], values[1][i]));
          }
          list.sort(Comparator.comparingInt(TimeMaxTimeInDBPair::getTime));
          var newValue = new int[2][values[0].length];
          for (int i = 0; i < values[0].length; i++) {
            newValue[0][i] = list.get(i).getTime();
            newValue[1][i] = list.get(i).getMaxtimeInDB();
          }
          observerHintDetailsMap.put(observerHint, newValue);
        } catch (Exception e) {
          System.out.println("Exception during processing : " + observerHint + e);
        }
      }
      return observerHintDetailsMap;
    } catch (IOException e) {
      System.out.println("Error Reading files");
      return Map.of();
    }
  }


  public static int[] getSplitDoubles(String val) {
    String[] splits = val.split(" ");
    int[] result = new int[splits.length];
    int i = 0;
    for (String split : splits) {
      if (split.contains(".")) {
        result[i++] = Integer.parseInt(split.substring(0, split.indexOf(".")));
      } else {
        result[i++] = (int) (Long.parseLong(split) / 1000);
      }
    }
    return result;
  }
}
