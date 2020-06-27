package com.company;

import javax.swing.JPanel;

public class Main {


  public static void main(String[] args) {
    if(args.length ==0){
      System.out.println("FilePath is required. Pass absolute path.");
      System.exit(-1);
    }
    final Object[] globChart = new Object[3];
    Window runnable = new Window(globChart, args[0]);
    javax.swing.SwingUtilities.invokeLater(runnable);

  }



}
