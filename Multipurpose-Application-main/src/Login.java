
import java.awt.Toolkit;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

public class Login extends javax.swing.JFrame {

private void clearField() {
    txt_username.setText(null);

    txt_password.setText(null);
    txt_login_key.setText(null);
}

/**
 * Creates new form SignUp
 */
public Login() {
    initComponents();
    setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons/icon_256x256.jpg"))); //FOR ICON 
}

//GLOBAL VARIABLE TO SET THE SUCCESSFUL TICK ICON ON JOPTIONPANE MESSAGE
Icon icon = new javax.swing.ImageIcon(getClass().getResource("/icons/tick.png"));

//GLOBAL VARIABLES AND GETTER , SETTER METHOD TO STORE USER NAME AND CONTACT NO FOR FURTHER OPERATIONS IF REQUIRED
private static String user_name;
private static int user_contact;
private static int user_id;

public static void setUser_Name(String name) {
    user_name = name;
}

public static void setUser_id(int id) {
    user_id = id;
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

public static int getUser_id() {
    return user_id;
}

//TO LOGIN THE USER
public void login() {
    String username = txt_username.getText();

    try {
        Connection con = DbConn.getConnection();
        PreparedStatement pst = con.prepareStatement("select * from mpa.users where name=?");
        pst.setString(1, username);

        ResultSet rst = pst.executeQuery();
        if (rst.next()) {
            String uname = rst.getString("name");
            int uid = rst.getInt("id");
            String pwd = rst.getString("password");
            String log_key = rst.getString("loginkey");
            String pass = passwordHash(txt_password.getText());
            String LogKey = keyHash(txt_login_key.getText());

            if ((pwd.equals(pass)) && (log_key.equals(LogKey))) {
                JOptionPane.showMessageDialog(this, "Login Successful...." + username, "SUCCESS", JOptionPane.INFORMATION_MESSAGE, icon);

                Login.setUser_Name(uname);

                Login.setUser_id(uid);
                Dashboard dash = new Dashboard();
                dash.getDetails(uid);
                dash.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login Unsuccessful....", "ERROR", JOptionPane.ERROR_MESSAGE);
                clearField();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Login was Unsuccessful....", "ERROR", JOptionPane.ERROR_MESSAGE);
            clearField();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

//FOR HASHING LOGIN KEY
public static String keyHash(String loginkey) {
    try {
        MessageDigest mds = MessageDigest.getInstance("SHA");
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
    String key = txt_login_key.getText();

    if (username.equals("")) {      //THIS  IF  BLOCK  IS  TO  CHECK  IF  USERNAME  FIELD  IS  EMPTY
        JOptionPane.showMessageDialog(null, "Please Enter Your Username !!", "WARNING", JOptionPane.WARNING_MESSAGE);    //THIS IS HOW WE GIVE WARNING MSG
        return false;
    } else if (!username.matches("^(.+)@(\\S+)$")) {       //THIS    else if   BLOCK IS TO CHECK  THAT  EMAIL IS  GIVEN  PROPERLY  IN  ITS  STD  FORMAT
        JOptionPane.showMessageDialog(null, "Please Enter Your Username Properly!!", "WARNING", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    if (pwd.equals("")) {
        JOptionPane.showMessageDialog(null, "Please Enter Your Password", "WARNING", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    if (key.equals("")) {
        JOptionPane.showMessageDialog(null, "Please Enter Your Login Key", "WARNING", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    return true;
}

//FOR HASING USER PASSWORD
public static String passwordHash(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA");
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
        jLabel2 = new javax.swing.JLabel();
        txt_username = new app.bolivia.swing.JCTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btn_login = new rojerusan.RSMaterialButtonCircle();
        jLabel10 = new javax.swing.JLabel();
        btn_signup = new javax.swing.JButton();
        txt_password = new rojerusan.RSPasswordTextPlaceHolder();
        txt_login_key = new rojerusan.RSPasswordTextPlaceHolder();
        checkbox_viewpwd = new javax.swing.JCheckBox();
        checkbox_confpwd = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        lbl_close = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        main_panel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/signup page image.jpg"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Username");

        txt_username.setBackground(new java.awt.Color(51, 51, 255));
        txt_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_username.setForeground(new java.awt.Color(255, 255, 255));
        txt_username.setPhColor(new java.awt.Color(255, 255, 255));
        txt_username.setPlaceholder("Enter a Unique Username");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Account_50px.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Password");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Secure_50px.png"))); // NOI18N

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Forgot_Password_50px_4.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Login Key");

        btn_login.setBackground(new java.awt.Color(255, 51, 51));
        btn_login.setText("LOGIN");
        btn_login.setFont(new java.awt.Font("Roboto Medium", 1, 15)); // NOI18N
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Don't have account? Click on ");

        btn_signup.setText("SignUp");
        btn_signup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });

        txt_password.setBackground(new java.awt.Color(51, 51, 255));
        txt_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_password.setForeground(new java.awt.Color(255, 255, 255));
        txt_password.setPhColor(new java.awt.Color(255, 255, 255));
        txt_password.setPlaceholder("Enter Your Password");

        txt_login_key.setBackground(new java.awt.Color(51, 51, 255));
        txt_login_key.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_login_key.setForeground(new java.awt.Color(255, 255, 255));
        txt_login_key.setPhColor(new java.awt.Color(255, 255, 255));
        txt_login_key.setPlaceholder("Enter Your Login Key");
        txt_login_key.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_login_keyActionPerformed(evt);
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

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("LOGIN");

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(checkbox_viewpwd)
                            .addComponent(checkbox_confpwd)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2)
                                    .addComponent(txt_username, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel9)
                                    .addComponent(txt_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_login_key, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_signup)))
                .addGap(0, 138, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(240, 240, 240))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(lbl_close, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_close, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3))
                        .addGap(43, 43, 43)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(checkbox_viewpwd)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txt_login_key, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addComponent(checkbox_confpwd)
                .addGap(18, 18, 18)
                .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_signup))
                .addGap(0, 230, Short.MAX_VALUE))
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
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_panelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        // TODO add your handling code here:
        if (emptyFields() == true) {
            login();
        }
    }//GEN-LAST:event_btn_loginActionPerformed

    private void checkbox_viewpwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbox_viewpwdActionPerformed
        // TODO add your handling code here:
        if (checkbox_viewpwd.isSelected()) {
            txt_password.setEchoChar((char) 0); //TO VIEW PASSWORD
        } else {
            txt_password.setEchoChar('*');
        }
    }//GEN-LAST:event_checkbox_viewpwdActionPerformed

    private void txt_login_keyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_login_keyActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_login_keyActionPerformed

    private void checkbox_confpwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbox_confpwdActionPerformed
        // TODO add your handling code here:
        if (checkbox_confpwd.isSelected()) {
            txt_login_key.setEchoChar((char) 0);
        } else {
            txt_login_key.setEchoChar('*');
        }
    }//GEN-LAST:event_checkbox_confpwdActionPerformed


    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed
        // TODO add your handling code here:
        SignUp signup = new SignUp();
        signup.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_signupActionPerformed


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
        new Login().setVisible(true);
    }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonCircle btn_login;
    private javax.swing.JButton btn_signup;
    private javax.swing.JCheckBox checkbox_confpwd;
    private javax.swing.JCheckBox checkbox_viewpwd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_close;
    private javax.swing.JPanel main_panel;
    private rojerusan.RSPasswordTextPlaceHolder txt_login_key;
    private rojerusan.RSPasswordTextPlaceHolder txt_password;
    private app.bolivia.swing.JCTextField txt_username;
    // End of variables declaration//GEN-END:variables

}
