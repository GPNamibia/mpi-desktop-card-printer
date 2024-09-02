package com.mpi.main;

import com.evolis.sdk.*;
import com.evolis.sdk.examples.PrinterName;
import com.mpi.component.PanelCover;
import com.mpi.component.PanelCard;
import com.mpi.component.PanelLoading;
import com.mpi.component.PanelLogin;
import com.mpi.component.PanelSearch;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Main extends javax.swing.JFrame {

    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
    private MigLayout layout;
    private PanelCover cover;
    public static PanelCard panelCard;
    public static PanelSearch panelSearch;
    public static PanelLogin login;
    public static PanelLoading panelLoading;
    private boolean isLogin = true;
    private final double addSize = 30;
    private final double coverSize = 100;
    private final double loginSize = 50;
    private static final int CARD_WIDTH = 800;
    private static final int CARD_HEIGHT = 600;
    private String savedImagePath;
    public static Preferences preferences; 
    
    

    public Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(false);

        // Set the default window size
        setSize(1200, 700); // Adjust the size as needed
        // Prevent window resizing
        setResizable(true);
        // Assuming you want to lock the size to 900x800 pixels
        Dimension lockedSize = new Dimension(1200, 700);

        bg = new JLayeredPane();
        bg.setBackground(new Color(204, 204, 204));
        bg.setOpaque(true);
        GroupLayout bgLayout = new GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 933, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 537, Short.MAX_VALUE)
        );
        bg.setMinimumSize(lockedSize);
        bg.setMaximumSize(lockedSize);
        bg.setPreferredSize(lockedSize);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(bg, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(bg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);

        init();
    }
    
    public void init() {
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover(this);
        panelCard = new PanelCard(this);
        panelSearch = new PanelSearch(this);
        login = new PanelLogin(this);
        panelLoading=new PanelLoading();
        bg.setLayout(new MigLayout("fill, insets 0, flowy"));

        // Add PanelCard and PanelSearch to the bg panel
        bg.setLayer(panelLoading, JLayeredPane.POPUP_LAYER);
        bg.add(panelLoading, "pos 0 0 100% 100%");
        bg.add(panelCard, "width " + 78 + "%, gapleft push, gapright 0, align right");
        bg.add(panelSearch, "width " + 23 + "%, pos " + (isLogin ? "0al" : "1al") + " 0 n 100%");
        bg.add(login, "width " + coverSize + "%, pos " + (isLogin ? "0al" : "1al") + " 0 n 100%");

        // Hide the other panels initially
        panelCard.setVisible(false);
        panelSearch.setVisible(false);
        panelLoading.setVisible(false);
    }



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(204, 204, 204));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 933, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

 
public void savePanelAsImage() {
    SwingUtilities.invokeLater(() -> {
        // Define the resolution (dots per inch) for printing
        int resolution = 1200; // 300 DPI is a common print resolution
        int imageWidth = (int) (panelCard.getWidth());
        int imageHeight = (int) (panelCard.getHeight());

        // Create a BufferedImage with the desired resolution
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        // Create a Graphics2D object from the BufferedImage
        Graphics2D g2d = image.createGraphics();
        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Scale the graphics context to match the higher resolution
        //g2d.scale(resolution, resolution);

        // Render the panel onto the BufferedImage
        panelCard.paint(g2d);

        // Dispose of the Graphics object
        g2d.dispose();

        // Save the BufferedImage to a BMP file
        try {
            File output = new File("panel_image.bmp"); // Save as BMP format
            ImageIO.write(image, "bmp", output);
            preferences = Preferences.userNodeForPackage(getClass());
            savedImagePath = output.getAbsolutePath();
            
            preferences.put("savedImagePath", savedImagePath);
            System.out.println("Image path saved: " + savedImagePath);
            
            // Update the UI after saving the image
            panelCard.repaint(); // Repaint the panel
            panelCard.revalidate(); // Revalidate the panel
            
            // Display a message or perform any other necessary actions
            System.out.println("Panel image saved and UI updated.");
            
        } catch (IOException ex) {
            System.err.println("Error saving image: " + ex.getMessage());
        }
    });
}


    public static void showLoggedInPanels(boolean isLoggedIn) {
        if (isLoggedIn) {
            // Hide the login panel
            login.setVisible(false);
            

            // Show the other panels
            panelCard.setVisible(true);
            panelSearch.setVisible(true);
        } else  {
            // Show the login panel
            login.setVisible(true);

            // Hide the other panels
            panelCard.setVisible(false);
            panelSearch.setVisible(false);
            System.err.println("Logged checked: ");
        }
    }
    
        public static void showLoading(boolean isLoggedIn) {
        if (isLoggedIn) {
            // Hide the loading panel
             panelLoading.setVisible(true);
        } else  {
            // Show the loading panel
             panelLoading.setVisible(false);

        }
    }
    
    public void checkIfTokenExist() {
        
        if (preferences == null) {
           
            JOptionPane.showMessageDialog(this, "Session Expired", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
