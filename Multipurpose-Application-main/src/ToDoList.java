/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ToDoList extends javax.swing.JFrame {

//GLOBAL VARIABLE FOR "counting clicks" & TO CAPTURE "sr_no" OF A NOTE WHICH THE USER CLICKS
private static String title;
int clickcount = 0;
int sr_no;

public static void settitle(String Title) {
    title = Title;
}

public static String gettitle() {
    return title;
}

/**
 * Creates new form Calculator
 */
public ToDoList() {
    initComponents();
    clearTable();
    setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons/icon_256x256.jpg"))); //FOR ICON 
}
Icon icon = new javax.swing.ImageIcon(getClass().getResource("/icons/tick.png"));

//TO SET NOTES INTO THE TABLE
public void setNotesDetails() {
    try {
        Connection con = DbConn.getConnection();
        PreparedStatement pst = con.prepareStatement("Select * from mpa.todolist");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            sr_no = rs.getInt("srno");
            Timestamp dateTime = rs.getTimestamp("datetime");
             title = rs.getString("title");
            String Description = rs.getString("description");

//            ToDoList.settitle(tit);
            Object[] obj = {sr_no, dateTime, title, Description};
            DefaultTableModel model = (DefaultTableModel) table_note.getModel();
            model.addRow(obj);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

//TO ADD NEW NOTE
public boolean addNoteDetails() {
    boolean isAdd = false;
    String title = txt_note_title.getText();
    String descp = txtarea_note_desc.getText();
    // Capture current date and time
    Timestamp time = new Timestamp(System.currentTimeMillis());

    try {
        Connection con = DbConn.getConnection();
        PreparedStatement pst = con.prepareStatement("insert into mpa.todolist(datetime,title,description) value(?,?,?)");
        pst.setTimestamp(1, time);
        pst.setString(2, title);
        pst.setString(3, descp);

        int rs = pst.executeUpdate();
        if (rs > 0) {
            isAdd = true;
        } else {
            isAdd = false;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return isAdd;
}

//TO UPDATE NOTE 
public boolean updateNoteDetails() {
    boolean isupdate = false;
    String title = txt_note_title.getText();
    String desc = txtarea_note_desc.getText();

    try {
        Connection con = DbConn.getConnection();
        PreparedStatement ps = con.prepareStatement("update mpa.todolist set title=?,description=? where srno=?");
        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setInt(3, sr_no);
        int rowcount = ps.executeUpdate();
        if (rowcount > 0) {
            isupdate = true;
        } else {
            JOptionPane.showMessageDialog(this, "Cannot Update the task please try again!!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
            isupdate = false;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return isupdate;
}

//TO DELETE NOTE
public boolean deleteNotesDetails() {
    boolean isdeleted = false;
    String title = txt_note_title.getText();
    String desc = txtarea_note_desc.getText();

    try {
        Connection con = DbConn.getConnection();
        PreparedStatement pst = con.prepareStatement("delete from mpa.todolist where srno=?");
        pst.setInt(1, sr_no);
        int rowcount = pst.executeUpdate();
        if (rowcount > 0) {
            isdeleted = true;
        } else {
            isdeleted = false;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return isdeleted;
}

//TO CLEAR FIELDS 
public void clearField() {
    txt_note_title.setText(null);
    txtarea_note_desc.setText(null);
}

//TO STOP THE REPEATED PRINTING OF DATA INTO THE TABLE
public void clearTable() {
    DefaultTableModel model = (DefaultTableModel) table_note.getModel();
    model.setRowCount(0);
}

//TO CHECK IF FIELDS ARE EMPTY ARE NOT
public boolean emptyField() {
    boolean isEmpty = false;
   String title = txt_note_title.getText();
    String desc = txtarea_note_desc.getText();
    if (title.isEmpty() || desc.isEmpty()) {
        isEmpty = true;
        JOptionPane.showMessageDialog(this, "Please fill both the fields!!!!!", "WARNING", JOptionPane.WARNING_MESSAGE);
    } else {
        isEmpty = false;
    }
    return isEmpty;
}

/**
 * This method is called from within the constructor to initialize the form.
 * WARNING: Do NOT modify this code. The content of this method is always
 * regenerated by the Form Editor.
 */
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btn_back = new javax.swing.JLabel();
        txt_note_title = new app.bolivia.swing.JCTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtarea_note_desc = new javax.swing.JTextArea();
        btn_add = new rojerusan.RSMaterialButtonCircle();
        btn_edit = new rojerusan.RSMaterialButtonCircle();
        btn_del = new rojerusan.RSMaterialButtonCircle();
        btn_view = new rojerusan.RSMaterialButtonCircle();
        radio_addremind = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        btn_close = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        scrollPaneWin111 = new ScrollPaneWin11();
        table_note = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(51, 51, 255));

        btn_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/back button.png"))); // NOI18N
        btn_back.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_backMouseClicked(evt);
            }
        });

        txt_note_title.setBackground(new java.awt.Color(51, 51, 255));
        txt_note_title.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_note_title.setForeground(new java.awt.Color(255, 255, 255));
        txt_note_title.setPhColor(new java.awt.Color(255, 255, 255));
        txt_note_title.setPlaceholder("Enter Your Note Title");
        txt_note_title.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_note_titleActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Note Title");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Note Description");

        txtarea_note_desc.setBackground(new java.awt.Color(51, 51, 255));
        txtarea_note_desc.setColumns(20);
        txtarea_note_desc.setForeground(new java.awt.Color(255, 255, 255));
        txtarea_note_desc.setLineWrap(true);
        txtarea_note_desc.setRows(5);
        txtarea_note_desc.setWrapStyleWord(true);
        txtarea_note_desc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane1.setViewportView(txtarea_note_desc);

        btn_add.setBackground(new java.awt.Color(255, 51, 51));
        btn_add.setText("ADD NOTE");
        btn_add.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        btn_add.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_edit.setBackground(new java.awt.Color(255, 51, 51));
        btn_edit.setText("EDIT NOTE");
        btn_edit.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        btn_edit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_del.setBackground(new java.awt.Color(255, 51, 51));
        btn_del.setText("DELETE NOTE");
        btn_del.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        btn_del.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delActionPerformed(evt);
            }
        });

        btn_view.setBackground(new java.awt.Color(255, 51, 51));
        btn_view.setText("ViEW NOTE");
        btn_view.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        btn_view.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viewActionPerformed(evt);
            }
        });

        radio_addremind.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        radio_addremind.setForeground(new java.awt.Color(255, 255, 255));
        radio_addremind.setText("Add Reminder");
        radio_addremind.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_addremind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_addremindActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btn_back)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(btn_view, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(radio_addremind)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_note_title, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btn_back)
                .addGap(121, 121, 121)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_note_title, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radio_addremind)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_view, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(588, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btn_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close_log.png"))); // NOI18N
        btn_close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_closeMouseClicked(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/to-do-list 50x50.png"))); // NOI18N
        jLabel1.setText("To-Do-List");

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(220, 3));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        table_note.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Sr.No", "Date & Time", "Note Title", "Note Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_note.setShowGrid(true);
        table_note.setSurrendersFocusOnKeystroke(true);
        table_note.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_noteMouseClicked(evt);
            }
        });
        scrollPaneWin111.setViewportView(table_note);
        if (table_note.getColumnModel().getColumnCount() > 0) {
            table_note.getColumnModel().getColumn(0).setMaxWidth(45);
            table_note.getColumnModel().getColumn(1).setPreferredWidth(145);
            table_note.getColumnModel().getColumn(1).setMaxWidth(2000);
            table_note.getColumnModel().getColumn(2).setPreferredWidth(150);
            table_note.getColumnModel().getColumn(2).setMaxWidth(200);
            table_note.getColumnModel().getColumn(3).setPreferredWidth(30);
        }

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/45x45 audio.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(155, 155, 155)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_close, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPaneWin111, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel1))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(btn_close, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(scrollPaneWin111, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1411, 803));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

//BACK BUTTON ACTION
    private void btn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseClicked
        // TODO add your handling code here:
        int id=Login.getUser_id();
        Dashboard dash = new Dashboard();
        dash.getDetails(id);
        dash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_backMouseClicked

//ADD BUTTON ACTION
    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
        if (emptyField() == false) {
            if (addNoteDetails() == true) {
                JOptionPane.showMessageDialog(this, "Note Added Successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE, icon);
                clearTable();
                setNotesDetails();
                clearField();
            } else {
                JOptionPane.showMessageDialog(this, "Note not added!!!! Please try again", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_btn_addActionPerformed

//VIEW BUTTON ACTION
    private void btn_viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewActionPerformed
// THIS IS A MOUSE CLICK  COUNTER WHICH IS USED TO INCREMENT THE NO OF CLICKS. IT IS DONE SO THAT USER CAN VIEW HIS NOTES OR TASK WITH A SINGLE CLICK RATHER THAN DOUBLE CLICK. IF MORE THAN ONE CLICK IS PRESSED THEN THE WARNING MESSAGE IS SHOWN
        clickcount++;
        if (clickcount == 1) {
            setNotesDetails();
        } else if (clickcount == 2) {
            JOptionPane.showMessageDialog(this, "Notes are already displayed!!!!!!", "WARNING", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Notes are already displayed!!!!!!", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_viewActionPerformed

//UPDATE BUTTON ACTION 
    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        if (updateNoteDetails() == true) {
            JOptionPane.showMessageDialog(this, "Note Updated Successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE, icon);
            clearTable();
            setNotesDetails();
            clearField();
        } else {
            JOptionPane.showMessageDialog(this, "Note cannot be Updated. Please try again!!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_editActionPerformed

//TO DISPLAY TABLE DATA INTO THE FIELDS
    private void table_noteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_noteMouseClicked
        int rowno = table_note.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table_note.getModel();

        sr_no = Integer.parseInt(model.getValueAt(rowno, 0).toString());
        txt_note_title.setText(model.getValueAt(rowno, 2).toString());
        txtarea_note_desc.setText(model.getValueAt(rowno, 3).toString());
        System.out.println(sr_no);
    }//GEN-LAST:event_table_noteMouseClicked

//CLOSE BUTTON ACTION
    private void btn_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_closeMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btn_closeMouseClicked

//DELETE BUTTON ACTION
    private void btn_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delActionPerformed
        // TODO add your handling code here:
        if (deleteNotesDetails() == true) {
            JOptionPane.showMessageDialog(this, "Note Successfully Deleted", "SUCCESS", JOptionPane.INFORMATION_MESSAGE, icon);
            clearTable();
            setNotesDetails();
            clearField();
        } else {
            JOptionPane.showMessageDialog(this, "Note Cannot be Deleted Please try Again!!!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_delActionPerformed

//RADIO BUTTON ACTION   
    private void radio_addremindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_addremindActionPerformed
        // TODO add your handling code here:
        if (radio_addremind.isSelected()) {
            
            date_time_reminder remind = new date_time_reminder(title);
            
            remind.setVisible(true);
        }

    }//GEN-LAST:event_radio_addremindActionPerformed

    private void txt_note_titleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_note_titleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_note_titleActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        Music audio=new Music();
        audio.setVisible(true);
    }//GEN-LAST:event_jLabel2MouseClicked

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
        java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
        new ToDoList().setVisible(true);
    }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonCircle btn_add;
    private javax.swing.JLabel btn_back;
    private javax.swing.JLabel btn_close;
    private rojerusan.RSMaterialButtonCircle btn_del;
    private rojerusan.RSMaterialButtonCircle btn_edit;
    private rojerusan.RSMaterialButtonCircle btn_view;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton radio_addremind;
    private ScrollPaneWin11 scrollPaneWin111;
    private javax.swing.JTable table_note;
    public app.bolivia.swing.JCTextField txt_note_title;
    private javax.swing.JTextArea txtarea_note_desc;
    // End of variables declaration//GEN-END:variables

}
