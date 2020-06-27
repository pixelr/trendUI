package com.company;

import static com.company.FileUtil.getObserverHintDetailsMapFromCSV;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

public class Window implements Runnable {
  public final Object[] globChart;
  private final String fileName;

  public Window(Object[] globChart, String fileName) {
    this.globChart = globChart;
    this.fileName = fileName;
  }

  @Override
  public void run() {

    // Create and set up the window.
    JFrame frame = new JFrame("ObserverId Trends : " + fileName);
    frame.setLayout(new BorderLayout());

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JButton button = new JButton("Hide/UnHide Panels");
    frame.add(button, BorderLayout.NORTH);


    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    final XYChart chart =
        new XYChartBuilder()
            .width((int) (screenSize.getWidth()))
            .height((int) screenSize.getHeight())
            .title("No Char").xAxisTitle("Untitled")
            .yAxisTitle("Untitled").build();
    var observerHintDetailsMap = getObserverHintDetailsMapFromCSV(fileName);

    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
    chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
    JPanel chartPanel = new XChartPanel<XYChart>(chart);
    globChart[0] = chartPanel;
    globChart[1] = null;
    globChart[2] = Boolean.FALSE;
    frame.add(chartPanel, BorderLayout.CENTER);

    // label
    JLabel label = new JLabel("ObserverHintTrend", SwingConstants.CENTER);
    frame.add(label, BorderLayout.SOUTH);


    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBorder(new EmptyBorder(0, 30, 0, 10));
    JList<String> jList = new JList<>(observerHintDetailsMap.keySet().toArray(new String[0]));
    scrollPane.setViewportView(jList);
    scrollPane.setPreferredSize(new Dimension(200, 0));
    frame.add(scrollPane, BorderLayout.WEST);
    button.addActionListener(an -> {
      if(globChart[2] != null && globChart[1] !=null){
        ((JTable)globChart[1]).setVisible((Boolean)globChart[2]);
        scrollPane.setVisible((Boolean)globChart[2]);
        globChart[2] = !((Boolean)globChart[2]);
      }
    });
    jList.addListSelectionListener(new ItemHandler(this, frame, jList, observerHintDetailsMap).invoke());

    // Display the window.
    frame.pack();
    frame.setVisible(true);

  }

}
