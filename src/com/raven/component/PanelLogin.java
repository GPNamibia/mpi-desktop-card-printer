package com.raven.component;

import static com.raven.component.PanelCard.parseAndPopulateLabels;
import com.raven.main.Main;
import static com.raven.main.Main.panelLoading;
import static com.raven.main.Main.preferences;
import com.raven.swing.Button;
import com.raven.swing.MyPasswordField;
import com.raven.swing.MyTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.prefs.Preferences;
import javax.naming.Context;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import org.json.JSONException;
import org.json.JSONObject;

public class PanelLogin extends javax.swing.JLayeredPane {
    
     public static Preferences preference; 
     private PanelCard panelCard;
     private PanelSearch panelSearch;
     private static Main mainFrame;

    public PanelLogin(Main mainFrame) {
        this.mainFrame = mainFrame; 
        initComponents();
//        initRegister();
        initLogin();
//        loginButton.setVisible(true);
        register.setVisible(false);
    }

    private void initRegister() {
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("MPI Login");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(0,74,151,255));
        register.add(label);
        MyTextField txtUser = new MyTextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/user.png")));
        txtUser.setHint("Enter Username");
        register.add(txtUser, "w 60%");
        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/pass.png")));
        txtPass.setHint("Enter PasswordPassword");
        register.add(txtPass, "w 60%");
        Button cmd = new Button();
        cmd.setBackground(new Color(0,74,151,255));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.setText("SIGN UP");
        register.add(cmd, "w 40%, h 40");
    }

    private void initLogin() {
        loginButton.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("MPI Login");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(0,74,151,255));
        loginButton.add(label);
        MyTextField txtUser = new MyTextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/user.png")));
        txtUser.setHint("Enter Username");
        loginButton.add(txtUser, "w 60%");
        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/pass.png")));
        txtPass.setHint("Enter Password");
        loginButton.add(txtPass, "w 60%");
        Button cmd = new Button();
        cmd.setBackground(new Color(0,74,151,255));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.setText("Login");
        
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                mainFrame.showLoading(true);
                String username = txtUser.getText();
                String password = new String(txtPass.getPassword());
                login(username, password);
            }
        });
        loginButton.add(cmd, "w 40%, h 40");
    }

    public void showRegister(boolean show) {
        if (show) {
            register.setVisible(true);
            loginButton.setVisible(false);
        } else {
            register.setVisible(false);
            loginButton.setVisible(true);
        }
    }
    
    private void login(String username, String password) {
        try {

            String urlString = "https://namibia-mpi.globalhealthapp.net/auth";

            // Create a URL object
            URL url = new URL(urlString);

            // Open a connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set request properties
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/fhir+json");
            conn.setRequestProperty("Accept", "application/fhir+json");
            conn.setRequestProperty("username", username);
            conn.setRequestProperty("password", password);
            conn.setRequestProperty("client_id", "fiddler");
            conn.setRequestProperty("client_secret", "fiddler");
            conn.setRequestProperty("grant_type", "password");

            // Check response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Handle successful response
                successResponseSuccessful(response.toString());
                System.out.println(" Response : " + response.toString());

            } else {
                // Handle unsuccessful response
                successResponseUnsuccessful();
//                System.out.println("Error retrieving data. Response code: " + responseCode);
            }

            // Close connection
            conn.disconnect();
        } catch (IOException e) {
             successResponseUnsuccessfulConnnection();
            e.printStackTrace();
            // Handle exception
        }
    }
    
    public void successResponseSuccessful(String body) {
    // Process the response body on the Event Dispatch Thread (EDT)
    SwingUtilities.invokeLater(() -> {
        try {
            JSONObject jsonObject = new JSONObject(body);
            String access_token = jsonObject.getString("access_token");

            // Save the tokens in Preferences
            preference = Preferences.userNodeForPackage(getClass());
            preference.put("access_token", access_token); 
            
             mainFrame.showLoading(false);
            JOptionPane.showMessageDialog(null, "Successfully logged in", "Success", JOptionPane.INFORMATION_MESSAGE);

            ((Main) SwingUtilities.getWindowAncestor(this)).showLoggedInPanels(true);

        } catch (JSONException e) {
            // Handle JSON parsing error
            successResponseUnsuccessful();
        }
    });
}
    
    private static void successResponseUnsuccessful() {
         mainFrame.showLoading(false);
        JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Failure", JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    private static void successResponseUnsuccessfulConnnection() {
         mainFrame.showLoading(false);
        JOptionPane.showMessageDialog(null, "We're having a problem connecting to santeMPI. Please try an alternative network connection.", "Failure", JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginButton = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        loginButton.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginButtonLayout = new javax.swing.GroupLayout(loginButton);
        loginButton.setLayout(loginButtonLayout);
        loginButtonLayout.setHorizontalGroup(
            loginButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        loginButtonLayout.setVerticalGroup(
            loginButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(loginButton, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel loginButton;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
