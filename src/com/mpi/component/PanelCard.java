package com.mpi.component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import static com.mpi.component.PanelLogin.preference;
import com.mpi.main.Main;
import com.mpi.main.ConfigReader;
import com.mpi.swing.Button;
import com.mpi.swing.MyPasswordField;
import com.mpi.swing.MyTextField;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PanelCard extends javax.swing.JLayeredPane {

    private static JLabel nameValueLabel;
    private static JLabel sexValueLabel;
    private static JLabel dobValueLabel;
    private static JLabel healthID;
    private static ImageIcon healthIDImageIcon;
    private static ImageIcon patientImageIcon;
    private static JLabel photoLabel;
    private static JLabel healthIDLabel;
    private static Main mainFrame;
    public static PanelLoading panelLoading;
    private static ConfigReader configReader = new ConfigReader();

    public PanelCard(Main mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        initLogin();
        login.setVisible(true);
    }


    private void initLogin() {
        panelLoading=new PanelLoading();
        //    login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        login.setPreferredSize(new Dimension(800, 600));
        login.setLayout(null);

        // Coat of Arms image
        ImageIcon coatOfArmsIcon = new ImageIcon(getClass().getResource("/com/raven/icon/coam.jpg"));
        Image coatOfArmsImage = coatOfArmsIcon.getImage();
        int desiredWidth = 144; // Set your desired width
        int desiredHeight = 144; // Set your desired height
        Image scaledCoatOfArmsImage = coatOfArmsImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledCoatOfArmsIcon = new ImageIcon(scaledCoatOfArmsImage);
        JLabel coatOfArmsLabel = new JLabel(scaledCoatOfArmsIcon);
        coatOfArmsLabel.setBounds(40, 20, desiredWidth, desiredHeight);
        login.add(coatOfArmsLabel);

        // Republic label
        JLabel republicLabel = new JLabel("REPUBLIC OF NAMIBIA");
        republicLabel.setFont(new Font("Arial", Font.BOLD, 32));
        republicLabel.setForeground(Color.BLACK);
        republicLabel.setBounds(280, 40, 400, 40);
        login.add(republicLabel);

        // Ministry label
        JLabel ministryLabel = new JLabel("MINISTRY OF HEALTH & SOCIAL SERVICES");
        ministryLabel.setFont(new Font("Arial", Font.BOLD, 28));
        ministryLabel.setForeground(Color.BLACK);
        ministryLabel.setBounds(200, 80, 600, 40);
        login.add(ministryLabel);

        // Patient card label
        JLabel patientCardLabel = new JLabel("PATIENT HEALTH IDENTITY CARD");
        patientCardLabel.setFont(new Font("Arial", Font.BOLD, 36));
        //patientCardLabel.setForeground(new Color(7, 33, 82));
        patientCardLabel.setForeground(Color.BLUE);
        patientCardLabel.setBounds(200, 120, 600, 40);
        login.add(patientCardLabel);

        // Photo placeholder
        patientImageIcon = new ImageIcon(getClass().getResource(""));
        Image scaledPatientImage = patientImageIcon.getImage();
        ImageIcon scaledPatientImageIcon = new ImageIcon(scaledPatientImage);
        photoLabel = new JLabel(scaledPatientImageIcon);
        photoLabel.setBounds(24, 185, 330, 290);
        login.add(photoLabel);

        // Name label
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 25));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setBounds(380, 180, 160, 40);
        login.add(nameLabel);

        // Name value
        nameValueLabel = new JLabel("");
        nameValueLabel.setFont(new Font("Arial", Font.BOLD, 33));
        nameValueLabel.setForeground(Color.BLACK);
        nameValueLabel.setBounds(380, 206, 400, 40);
        login.add(nameValueLabel);

        // Sex label
        JLabel sexLabel = new JLabel("Sex:");
        sexLabel.setFont(new Font("Arial", Font.BOLD, 25));
        sexLabel.setForeground(Color.BLACK);
        sexLabel.setBounds(380, 242, 160, 40);
        login.add(sexLabel);

        // Sex value
        sexValueLabel = new JLabel("");
        sexValueLabel.setFont(new Font("Arial", Font.BOLD, 30));
        sexValueLabel.setForeground(Color.BLACK);
        sexValueLabel.setBounds(380, 273, 160, 40);
        login.add(sexValueLabel);

        // DOB label
        JLabel dobLabel = new JLabel("DOB:");
        dobLabel.setFont(new Font("Arial", Font.BOLD, 25));
        dobLabel.setForeground(Color.BLACK);
        dobLabel.setBounds(380, 310, 160, 40);
        login.add(dobLabel);

        // DOB value
        dobValueLabel = new JLabel("");
        dobValueLabel.setFont(new Font("Arial", Font.BOLD, 30));
        dobValueLabel.setForeground(Color.BLACK);
        dobValueLabel.setBounds(380, 336, 400, 40);
        login.add(dobValueLabel);

        // Health ID label
        healthIDImageIcon = new ImageIcon(getClass().getResource(""));
        Image scaledHealthImage = healthIDImageIcon.getImage();
        ImageIcon scaledHealthImageIcon = new ImageIcon(scaledHealthImage);
        healthIDLabel = new JLabel(scaledHealthImageIcon);
        healthIDLabel.setBounds(450, 330, 320, 274);
        login.add(healthIDLabel);

        // Health ID value
        healthID = new JLabel("");
        healthID.setFont(new Font("Arial", Font.BOLD, 34));
        healthID.setForeground(Color.BLACK);
        healthID.setBounds(550, 330, 320, 430);
        login.add(healthID);

    }

    public void showRegister(boolean show) {
        if (show) {
            register.setVisible(true);
            login.setVisible(false);
        } else {
            register.setVisible(false);
            login.setVisible(true);
        }
    }

    public static void getPatientData(String query) throws WriterException {
        try {

            String access_token = preference.get("access_token", null);

            // Set your authorization token
            String auth =  "Bearer "+ access_token;
            System.out.println("col " + auth);

            String urlString = configReader.getUrl() +"/patient/"+ query;

            // Create a URL object
            URL url = new URL(urlString);

            // Open a connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set request properties
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/fhir+json");
            conn.setRequestProperty("Accept", "application/fhir+json");
            conn.setRequestProperty("Authorization", auth);

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
                parseAndPopulateLabels(response.toString());
                System.out.println(" Response : " + response.toString());

            } else {
                // Handle unsuccessful response
                mainFrame.showLoading(false);
                JOptionPane.showMessageDialog(null, "> Error retrieving data, make sure you are connected to the internet !!");
                mainFrame.showLoggedInPanels(false);
            }

            // Close connection
            conn.disconnect();
        } catch (IOException e) {
            mainFrame.showLoading(false);
            JOptionPane.showMessageDialog(null, "> Error retrieving data, make sure you are connected to the internet !!");
            mainFrame.showLoggedInPanels(false);
            e.printStackTrace();
            // Handle exception
        }
    }

    public static void parseAndPopulateLabels(String jsonResponse) throws WriterException {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray entryArray = jsonObject.getJSONArray("entry");
            JSONObject patientObject = entryArray.getJSONObject(0).getJSONObject("resource");

            // Extract patient name
            String givenName = patientObject.getJSONArray("name").getJSONObject(0).getJSONArray("given").getString(0);
            String familyName = patientObject.getJSONArray("name").getJSONObject(0).getString("family");
            String middleName = "";
            if (patientObject.getJSONArray("name").getJSONObject(0).has("given")) {
                JSONArray givenArray = patientObject.getJSONArray("name").getJSONObject(0).getJSONArray("given");
                if (givenArray.length() > 1) {
                    middleName = givenArray.getString(1);
                }
            }
            String patientFullName;
            if (middleName != null && !middleName.isEmpty()) {
                String middleInitial = middleName.substring(0, 1) + ".";
                patientFullName = givenName + " " + middleInitial + " " + familyName;
            } else {
                patientFullName = givenName + " " + familyName;
            }

            // Extract sex and date of birth
            String sex = patientObject.getString("gender");
            sex = sex.substring(0, 1).toUpperCase() + sex.substring(1);

            String dob = patientObject.getString("birthDate");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
            Date date = inputFormat.parse(dob);
            dob = outputFormat.format(date);

            // Extract health identifier
            String healthIdentifier = "";
            JSONArray identifierArray = patientObject.getJSONArray("identifier");
            for (int i = 0; i < identifierArray.length(); i++) {
                JSONObject identifierObj = identifierArray.getJSONObject(i);
                if (identifierObj.has("system") && identifierObj.getString("system").equals("http://ohie.org/Health_ID")) {
                    healthIdentifier = identifierObj.getString("value");
                    break;
                }
            }

            // Create barcode image from health ID
            Code128Writer writer = new Code128Writer();
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(healthIdentifier, BarcodeFormat.CODE_128, 442, 100);
            System.out.println("JEHOO"+healthIdentifier);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage barcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    barcodeImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            ImageIcon barcodeIcon = new ImageIcon(barcodeImage);
            healthIDLabel.setIcon(barcodeIcon);

            final String finalPatientFullName = patientFullName;
            final String finalSex = sex;
            final String finalDob = dob;
            final String finalHealthIdentifier = healthIdentifier;

            // Extract and set photo
            String imageData = patientObject.getJSONArray("photo").getJSONObject(0).getString("data");
            byte[] decodedBytes = Base64.getDecoder().decode(imageData);
            ImageIcon imageIcon = new ImageIcon(decodedBytes);
            photoLabel.setIcon(imageIcon);

            // Convert ImageIcon to BufferedImage
            BufferedImage sourceImage = new BufferedImage(
                    imageIcon.getIconWidth(),
                    imageIcon.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics g = sourceImage.createGraphics();
            imageIcon.paintIcon(null, g, 0, 0);
            g.dispose();

            // Create a new BufferedImage for the scaled image
            BufferedImage scaledImage = new BufferedImage(
                    photoLabel.getWidth(),
                    photoLabel.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = scaledImage.createGraphics();

            // Apply rendering hints for smoother, clearer quality
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

            // Draw the original image scaled
            g2d.drawImage(sourceImage, 0, 0, photoLabel.getWidth(), photoLabel.getHeight(), null);
            g2d.dispose();

            // Convert back to ImageIcon for use with JLabel
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            photoLabel.setIcon(scaledImageIcon);

            // Populate JLabels
            SwingUtilities.invokeLater(() -> {
                nameValueLabel.setText(finalPatientFullName);
                sexValueLabel.setText(finalSex);
                dobValueLabel.setText(finalDob);
                healthID.setText(finalHealthIdentifier);
                photoLabel.setIcon(scaledImageIcon);
                healthIDLabel.setIcon(barcodeIcon);
                System.out.println(finalHealthIdentifier);

                mainFrame.showLoading(false);
            });
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            mainFrame.showLoading(false);
            JOptionPane.showMessageDialog(null, "> Error retrieving data, patient not found !!");
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
                loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 505, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
                loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 272, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
                registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 505, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
                registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 272, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
