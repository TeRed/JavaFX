package api;

import api.SortImages;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GUI {
    private static String choosertitle;
    private static String choosertitle2;

    public static void main (String[] args){
        JFrame frame = new JFrame("Test");
        frame.setVisible(true);
        frame.setSize(500,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton button = new JButton("From");
        panel.add(button);
        button.addActionListener (new Action1());

        JButton button2 = new JButton("To");
        panel.add(button2);
        button2.addActionListener (new Action2());

        JButton button3 = new JButton("Get");
        panel.add(button3);
        button3.addActionListener (new Action3());
    }
    static class Action1 implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("choosertitle");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
//                choosertitle = "./" + chooser.getSelectedFile().getName();
                choosertitle = chooser.getSelectedFile().getAbsolutePath();
                System.out.println(choosertitle);
            } else {
                System.out.println("No Selection ");
            }
        }
    }
    static class Action2 implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("choosertitle2");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
//                choosertitle2 = "./" + chooser.getSelectedFile().getName() + "/";
                choosertitle2 = chooser.getSelectedFile().getAbsolutePath();
                System.out.println("Heyyyy " + choosertitle2);
            } else {
                System.out.println("No Selection ");
            }
        }
    }

    static class Action3 implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            System.out.println(choosertitle);
            System.out.println(choosertitle2);
            /*try {
                SortImages.sort(choosertitle, choosertitle2 + "/");
            } catch(Exception exception) {
                exception.printStackTrace();
            }*/
        }
    }

}
