/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.awt.Toolkit;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.Random;

/**
 *
 * @author TEST
 */
public class SignUp extends javax.swing.JFrame {

private void clearField() {
    txt_username.setText(null);
    txt_contact_no.setText(null);
    txt_password.setText(null);
    txt_conf_password.setText(null);
}

/**
 * Creates new form SignUp
 */
public SignUp() {
    initComponents();
    setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons/icon_256x256.jpg"))); //FOR ICON 
}

//GLOBAL VARIABLE TO SET THE SUCCESSFUL TICK ICON ON JOPTIONPANE MESSAGE
Icon icon = new javax.swing.ImageIcon(getClass().getResource("/icons/tick.png"));

//RANDOM NUMBER VARIABLES "key" AND "digit"
private StringBuilder key = new StringBuilder();
int digit;

//GLOBAL VARIABLES AND GETTER , SETTER METHOD TO STORE USER NAME AND CONTACT NO FOR FURTHER OPERATIONS IF REQUIRED
private static String user_name;
private static int user_contact;

public static void setUser_Name(String name) {
    user_name = name;
}

public static void setUser_Contact(int phone_no) {
    user_contact = phone_no;
}

public static String getUser_Name() {
    return user_name;
}

public static int getUser_Contact() {
    return user_contact;
}

//TO GET USER DATA FOR SIGNUP
public void SignUp(String username, String pwd, String logkey) {
    Random rand = new Random();
    for (int i = 0; i <= 3; i++) {
        digit = rand.nextInt(10);
        key.append(digit);
    }
    String contact = txt_contact_no.getText();
    String conf_pwd = txt_conf_password.getText();
    String passwd = txt_password.getText();
    String passkey = keyHash(key.toString());

    try {
        Connection con = DbConn.getConnection();
        PreparedStatement pst = con.prepareStatement("insert into mpa.users(name,contact,password,loginkey) values(?,?,?,?)");
        pst.setString(1, username);
        pst.setString(2, contact);
        pst.setString(3, pwd);
        pst.setString(4, passkey);

        int rowcount = pst.executeUpdate();
        if (rowcount > 0) {
            if (conf_pwd.equals(passwd)) {
                JOptionPane.showMessageDialog(this, "SignUp Successful..." + username + "\nYour login key is:-  " + key, "SUCCESS", JOptionPane.INFORMATION_MESSAGE, icon);
                Login login = new Login();
                login.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Enter Same Password!!!!!!!", "WARNING", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "SignUp unsuccessful...Try again later", "WARNING", JOptionPane.WARNING_MESSAGE);
            clearField();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

//FOR HASING USER PASSWORD
public static String passwordHash(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA");   //HERE WE HAVE USED SHA-1 ALGORITHM WHICH CONTAINS 40 HEXADECIMAL CHARACTERS 
        md.update(password.getBytes());
        byte[] rbt = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : rbt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

//FOR HASHING LOGIN KEY
public static String keyHash(String loginkey) {
    try {
        MessageDigest mds = MessageDigest.getInstance("SHA");    //HERE WE HAVE USED SHA-1 ALGORITHM WHICH CONTAINS 40 HEXADECIMAL CHARACTERS 
        mds.update(loginkey.getBytes());
        byte[] rbts = mds.digest();
        StringBuilder sbc = new StringBuilder();

        for (byte bc : rbts) {
            sbc.append(String.format("%02x", bc));
        }
        return sbc.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

//TO CHECK IF FIELDS ARE NOT EMPTY
public boolean emptyFields() {
    String username = txt_username.getText();
    String pwd = txt_password.getText();
    String contact = txt_contact_no.getText();
    String conf_pwd = txt_conf_password.getText();

    if (username.equals("")) {      //THIS  IF  BLOCK  IS  TO  CHECK  IF  USERNAME  FIELD  IS  EMPTY
        JOptionPane.showMessageDialog(null, "Please Enter Your Username !!", "WARNING", JOptionPane.WARNING_MESSAGE);    //THIS IS HOW WE GIVE WARNING MSG
        return false;
    } else if (!username.matches("^(.+)@(\\S+)$")) {       //THIS    else if   BLOCK IS TO CHECK  THAT  EMAIL IS  GIVEN  PROPERLY  IN  ITS  STD  FORMAT
        JOptionPane.showMessageDialog(null, "Please Enter Your Username Properly!!", "WARNING", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    if (contact.equals("")) {
        JOptionPane.showMessageDialog(null, "Please Enter Your Contact", "WARNING", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    if (pwd.equals("")) {
        JOptionPane.showMessageDialog(null, "Please Enter Your Password", "WARNING", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    if (conf_pwd.equals("")) {
        JOptionPane.showMessageDialog(null, "Please Enter Your Confirm Password", "WARNING", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    return true;
}

//TO CHECK IF THE CONTACT NUMBER IS OF TEN DIGITS
public boolean digitCheck() {
    boolean isSame = false;
    String regex = "\\d{10}";
    String phone = txt_contact_no.getText();
    if (phone.matches(regex)) {
        isSame = true;
    } else {
        isSame = false;
    }
    return isSame;
}

//TO CHECK IF THE USER ENTERS DIFFERENT USERNAME
public boolean duplicateUsername() {
    boolean isName = false;
    String name = txt_username.getText();
    try {
        Connection con = DbConn.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM mpa.users where name=? ");
        pst.setString(1, name);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            isName = true;
        } else {
            isName = false;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return isName;
}

//TO CHECK IF THE USER ENTERS DIFFERENT CONTACT
public boolean duplicateContact() {
    boolean isSame = false;
    String phone = txt_contact_no.getText();
    try {
        Connection con = DbConn.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM mpa.users where contact=? ");
        pst.setString(1, phone);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            isSame = true;
        } else {
            isSame = false;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return isSame;
}

/**
 * This method is called from within the constructor to initialize the form.
 * WARNING: Do NOT modify this code. The content of this method is always
 * regenerated by the Form Editor.
 */
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main_panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_register = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_username = new app.bolivia.swing.JCTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_contact_no = new app.bolivia.swing.JCTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btn_signup = new rojerusan.RSMaterialButtonCircle();
        jLabel10 = new javax.swing.JLabel();
        btn_login = new javax.swing.JButton();
        txt_password = new rojerusan.RSPasswordTextPlaceHolder();
        txt_conf_password = new rojerusan.RSPasswordTextPlaceHolder();
        checkbox_viewpwd = new javax.swing.JCheckBox();
        checkbox_confpwd = new javax.swing.JCheckBox();
        lbl_close = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        main_panel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/signup page image.jpg"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));

        lbl_register.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_register.setForeground(new java.awt.Color(255, 255, 255));
        lbl_register.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_register.setText("SIGNUP");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Username");

        txt_username.setBackground(new java.awt.Color(51, 51, 255));
        txt_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_username.setForeground(new java.awt.Color(255, 255, 255));
        txt_username.setPhColor(new java.awt.Color(255, 255, 255));
        txt_username.setPlaceholder("Enter a Unique Username (Example: xyz@123)");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Account_50px.png"))); // NOI18N

        txt_contact_no.setBackground(new java.awt.Color(51, 51, 255));
        txt_contact_no.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_contact_no.setForeground(new java.awt.Color(255, 255, 255));
        txt_contact_no.setPhColor(new java.awt.Color(255, 255, 255));
        txt_contact_no.setPlaceholder("Enter Your Contact No");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Contact No");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Google_Mobile_50px.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Password");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Secure_50px.png"))); // NOI18N

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Forgot_Password_50px_4.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Confirm Password");

        btn_signup.setBackground(new java.awt.Color(255, 51, 51));
        btn_signup.setText("SIGNUP");
        btn_signup.setFont(new java.awt.Font("Roboto Medium", 1, 15)); // NOI18N
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Already have account? Click on ");

        btn_login.setText("Login");
        btn_login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        txt_password.setBackground(new java.awt.Color(51, 51, 255));
        txt_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_password.setForeground(new java.awt.Color(255, 255, 255));
        txt_password.setPhColor(new java.awt.Color(255, 255, 255));
        txt_password.setPlaceholder("Enter Your Password");

        txt_conf_password.setBackground(new java.awt.Color(51, 51, 255));
        txt_conf_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_conf_password.setForeground(new java.awt.Color(255, 255, 255));
        txt_conf_password.setPhColor(new java.awt.Color(255, 255, 255));
        txt_conf_password.setPlaceholder("Confirm Your Password");
        txt_conf_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_conf_passwordActionPerformed(evt);
            }
        });

        checkbox_viewpwd.setForeground(new java.awt.Color(255, 255, 255));
        checkbox_viewpwd.setText("View Password");
        checkbox_viewpwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbox_viewpwdActionPerformed(evt);
            }
        });

        checkbox_confpwd.setForeground(new java.awt.Color(255, 255, 255));
        checkbox_confpwd.setText("View Password");
        checkbox_confpwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbox_confpwdActionPerformed(evt);
            }
        });

        lbl_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close_log.png"))); // NOI18N
        lbl_close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_closeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(checkbox_viewpwd)
                    .addComponent(checkbox_confpwd)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(txt_username, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(txt_contact_no, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(txt_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_conf_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_login)
                        .addGap(29, 29, 29)))
                .addGap(0, 144, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_close, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_register, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(216, 216, 216))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_signup, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(228, 228, 228))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lbl_close, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(lbl_register, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_contact_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkbox_viewpwd)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_conf_password, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkbox_confpwd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_signup, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_login))
                .addGap(0, 171, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout main_panelLayout = new javax.swing.GroupLayout(main_panel);
        main_panel.setLayout(main_panelLayout);
        main_panelLayout.setHorizontalGroup(
            main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_panelLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        main_panelLayout.setVerticalGroup(
            main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(main_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(main_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1411, 803));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed
        String name = txt_username.getText();
        String password = passwordHash(txt_password.getText());
        String passkey = keyHash(key.toString());

// TODO add your handling code here:
        if (emptyFields() == true) {
            if (duplicateUsername() == false) {
                if (duplicateContact() == false) {
                    if (digitCheck() == true) {
                        SignUp(name, password, passkey);
                    } else {
                        JOptionPane.showMessageDialog(this, "Enter 10 digit contact no!!!!", "WARNING", JOptionPane.WARNING_MESSAGE);
                        txt_contact_no.setText(null);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Contact Already exists!!!!", "WARNING", JOptionPane.WARNING_MESSAGE);
                    txt_contact_no.setText(null);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Username Already exists!!!!", "WARNING", JOptionPane.WARNING_MESSAGE);
                txt_username.setText(null);
            }
        }

    }//GEN-LAST:event_btn_signupActionPerformed

    private void checkbox_viewpwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbox_viewpwdActionPerformed
        // TODO add your handling code here:
        if (checkbox_viewpwd.isSelected()) {
            txt_password.setEchoChar((char) 0); //TO VIEW PASSWORD
        } else {
            txt_password.setEchoChar('*');
        }
    }//GEN-LAST:event_checkbox_viewpwdActionPerformed

    private void txt_conf_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_conf_passwordActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_conf_passwordActionPerformed

    private void checkbox_confpwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbox_confpwdActionPerformed
        // TODO add your handling code here:
        if (checkbox_confpwd.isSelected()) {
            txt_conf_password.setEchoChar((char) 0);
        } else {
            txt_conf_password.setEchoChar('*');
        }
    }//GEN-LAST:event_checkbox_confpwdActionPerformed

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        // TODO add your handling code here:
        Login login = new Login();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_loginActionPerformed

    private void lbl_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_closeMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_lbl_closeMouseClicked

/**
 * @param args the command line arguments
 */
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
        java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
        new SignUp().setVisible(true);
    }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private rojerusan.RSMaterialButtonCircle btn_signup;
    private javax.swing.JCheckBox checkbox_confpwd;
    private javax.swing.JCheckBox checkbox_viewpwd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_close;
    private javax.swing.JLabel lbl_register;
    private javax.swing.JPanel main_panel;
    private rojerusan.RSPasswordTextPlaceHolder txt_conf_password;
    private app.bolivia.swing.JCTextField txt_contact_no;
    private rojerusan.RSPasswordTextPlaceHolder txt_password;
    private app.bolivia.swing.JCTextField txt_username;
    // End of variables declaration//GEN-END:variables

}
