/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpi.component;

import com.evolis.sdk.CardFace;
import com.evolis.sdk.Connection;
import com.evolis.sdk.PrintSession;
import com.evolis.sdk.PrinterInfo;
import com.evolis.sdk.ReturnCode;
import com.evolis.sdk.RwCardType;
import com.evolis.sdk.*;
import com.evolis.sdk.examples.PrinterName;
import com.google.zxing.WriterException;
import static com.mpi.component.PanelCard.getPatientData;
import com.mpi.main.Main;
import static com.mpi.main.Main.preferences;
import com.mpi.swing.Button;
import com.mpi.swing.ButtonOutLine;
import com.mpi.swing.MyTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author collinnehemia
 */
public class PanelSearch extends javax.swing.JPanel {
    
      private Button button,buttonPrint,buttonLogout;
      private MigLayout layout;
      private MyTextField searchField;
      private JLabel title;
      private Main mainFrame;
      private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));

    /**
     * Creates new form PanelSearch
     */
    public PanelSearch(Main mainFrame) {
        this.mainFrame = mainFrame; 
        initComponents();
        layout = new MigLayout("wrap, fill", "[center]", "push[]20[]20[]20[]push");
        setLayout(layout);
        setOpaque(false);
        init();
    }
    
    private void init() {
        // Title
        title = new JLabel("Search & Print Health Card");
        title.setFont(new Font("sansserif", Font.BOLD, 26));
        title.setForeground(new Color(0,74,151,255));
        add(title);

        // Search field
        searchField = new MyTextField();
        searchField.setFont(new Font("Arial", Font.BOLD, 14));
        searchField.setForeground(Color.BLACK);
        searchField.setHint("Enter Health ID");
        add(searchField, "w 60%, h 40");

        // Search Button
        button = new Button();
        button.setBackground(new Color(0,74,151,255));
        button.setForeground(new Color(245, 245, 245));
        button.setText("Search");
        add(button, "w 60%, h 40");

        // Add action listener to the search button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // Retrieve the text from the search field
                mainFrame.showLoading(true);
                String searchText = searchField.getText();
                try {
                    getPatientData(searchText);
                } catch (WriterException ex) {
                    Logger.getLogger(PanelSearch.class.getName()).log(Level.SEVERE, null, ex);
                }

                SwingUtilities.invokeLater(() -> {
                    // Perform actions after the search is done

                    // Make the print button visible
                    buttonPrint.setVisible(true);

                    // Save panel as image
                    mainFrame.savePanelAsImage();
                });

                try {
                    // Perform search action
                    getPatientData(searchText);
                } catch (WriterException ex) {
                    Logger.getLogger(PanelSearch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Print Button
        buttonPrint = new Button();
        buttonPrint.setBackground(new Color(0,74,151,255));
        buttonPrint.setForeground(new Color(245, 245, 245));
        buttonPrint.setText("Print");
        buttonPrint.setVisible(preferences != null);
        add(buttonPrint, "w 60%, h 40");

        // Add action listener to the print button
        buttonPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Printing...");
                // Perform printing action
                displayPrinterInfo();
            }
        });

        // Logout Button
        buttonLogout = new Button();
        buttonLogout.setBackground(Color.red);
        buttonLogout.setForeground(new Color(245, 245, 245));
        buttonLogout.setText("Logout");
        add(buttonLogout, "w 40%, h 20");

        // Add action listener to the logout button
        buttonLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //JOptionPane.showMessageDialog(mainFrame, "Successfully Logged out", "Success", JOptionPane.INFORMATION_MESSAGE);
                mainFrame.showLoggedInPanels(false);
            }
        });
    }


 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 161, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

        @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        GradientPaint gra = new GradientPaint(0, 0, new Color(245, 245, 245), 0, getHeight(), new Color(245, 245, 245));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }
        
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
   
    public void displayPrinterInfo() {
        
        String printerName = PrinterName.get(new String[]{});
        Connection connection = new Connection(printerName);
        buttonPrint.setEnabled(false);
        buttonPrint.setBackground(Color.GRAY);

        if (preferences == null) {
            JOptionPane.showMessageDialog(this, "Error: Image file path is null", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String imagePath = preferences.get("savedImagePath", null);
        System.out.println("imagePathDis " + imagePath);

        if (connection.isOpen()) {
            PrinterInfo printerInfo = connection.getInfo();
            State state = connection.getState();

            if (printerInfo != null) {
                printerName = printerInfo.getName();
            }

            if (printerName != null) {
                //JOptionPane.showMessageDialog(this, "Successfully connected to " + printerName,"Printer Info", JOptionPane.INFORMATION_MESSAGE);
                // Display printer state to user if the printer is not ready
                if (state != null) {
                    if (state.getMajorState() == State.MajorState.READY) {
                        System.out.println("Printer is READY.");
                    } else {
                        System.out.println("Printer state is " + state.getMajorState() + ":" + state.getMinorState());
                        JOptionPane.showMessageDialog(this,  state.getMajorState() + ":" + state.getMinorState(),
                                "Printer Status", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } else {
                JOptionPane.showMessageDialog(this, "Failed to retrieve printer information.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Wait until the image is saved
            SwingUtilities.invokeLater(() -> {
                // Convert the selected image to a BMP file
                // System.out.println("> Path.."+savedImagePath);
                File bmpFile = new File(imagePath);
                if (!bmpFile.exists()) {
                    JOptionPane.showMessageDialog(this, "Error: Image file not found", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PrintSession ps = new PrintSession(connection, RwCardType.MBLACK);

                // do printing
                if (!ps.setImage(CardFace.FRONT, imagePath)) {
                    JOptionPane.showMessageDialog(this, "Error: Can't load the image file", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("> Print result: " + bmpFile);
                    return;
                }

                System.out.println("> Start printing...");
                // print result output
                ReturnCode r = ps.print();
                System.out.println("> Print result: " + r);
                JOptionPane.showMessageDialog(this, ""+r, "Print Result", JOptionPane.INFORMATION_MESSAGE);

                connection.close();
            });
        } else {
            JOptionPane.showMessageDialog(this, "Printer connection failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
        buttonPrint.setEnabled(true);
        buttonPrint.setBackground(new Color(0,74,151,255));
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}