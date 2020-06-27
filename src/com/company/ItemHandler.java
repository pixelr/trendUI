package com.company;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Date;
import java.util.Map;
import java.util.StringJoiner;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

class ItemHandler {
  private final Window window;
  private final JFrame frame;
  private final JList<String> jList;
  private final Map<String, int[][]> finalObserverHintDetailsMap;
  private final String time = "_time";
  ;
  private final String maxTimeInDB = "maxTimeInDB";

  public ItemHandler(Window window, JFrame jFrame, JList<String> jList,
      Map<String, int[][]> finalObserverHintDetailsMap) {
    this.window = window;
    this.frame = jFrame;
    this.jList = jList;
    this.finalObserverHintDetailsMap = finalObserverHintDetailsMap;
  }

  public ListSelectionListener invoke() {
    return (it) -> {
      if (window.globChart[0] != null) {
        String id = jList.getSelectedValue();

        final XYChart xyChart =
            new XYChartBuilder().width(600).height(400).title("Trend Chart")
                .xAxisTitle(time)
                .yAxisTitle(maxTimeInDB).build();

        // Customize Chart
        xyChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        xyChart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        frame.remove((JPanel) window.globChart[0]);
        window.globChart[0] = new XChartPanel<>(xyChart);
        var pair = finalObserverHintDetailsMap.get(id);
        xyChart.addSeries(id, pair[0], pair[1]);
        frame.add((JPanel) window.globChart[0], BorderLayout.CENTER);


        String[][] pairData = new String[pair[0].length][pair.length + 1];
        for (int i = 0; i < pair[0].length; i++) {
          Date pickupTime = new Date((long) pair[0][i] * 1000);
          pairData[i][0] = String.valueOf(pickupTime.toString());
          pairData[i][1] = String.valueOf(pair[1][i]);
          pairData[i][2] = getDelay(new Date((long) pair[1][i] * 1000), pickupTime);
        }
        JTable jTable = new JTable(pairData, new String[] {time, maxTimeInDB, "LocalTimeMaxTimeInDb"});
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(0).setWidth(300);
        jTable.setPreferredSize(new Dimension(450, 0));

        if (window.globChart[1] != null) {
          frame.remove((JTable) window.globChart[1]);
        }
        window.globChart[1] = jTable;
        frame.add((JTable) window.globChart[1], BorderLayout.EAST);
        frame.invalidate();
        frame.validate();

      }
    };
  }

  private String getDelay(Date oldDate, Date newDate) {
    long diff = newDate.getTime() - oldDate.getTime();
    long diffSeconds = diff / 1000 % 60;
    long diffMinutes = diff / (60 * 1000) % 60;
    long diffHours = diff / (60 * 60 * 1000) % 24;
    long diffDays = diff / (24 * 60 * 60 * 1000);
    StringJoiner stringJoiner = new StringJoiner(":");
    stringJoiner.add(diffDays+"d");
    stringJoiner.add(diffHours+"");
    stringJoiner.add(diffMinutes+"");
    stringJoiner.add(diffSeconds+"");
    return stringJoiner.toString();
  }
}
