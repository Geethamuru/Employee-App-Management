/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package design;

    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import javax.swing.table.DefaultTableModel;
//    import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.table.DefaultTableModel;
/**
 *
 * @author geeth
 */
public class Employee extends JFrame {
    
Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    JTextField tfName = new JTextField(15);
    JTextField tfEmail = new JTextField(15);
    JTextField tfSalary = new JTextField(10);
    JTable table = new JTable();
    DefaultTableModel model;
    /**
     * Creates new form Employee
     */
    public Employee() {
       //
        setTitle("Employee Management App");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Employee Info"));
        panel.add(new JLabel("Name:"));
        panel.add(tfName);
        panel.add(new JLabel("Email:"));
        panel.add(tfEmail);
        panel.add(new JLabel("Salary:"));
        panel.add(tfSalary);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");

        panel.add(btnAdd);
        panel.add(btnUpdate);
        add(panel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottom = new JPanel();
        JButton btnDelete = new JButton("Delete");
        bottom.add(btnDelete);
        add(bottom, BorderLayout.SOUTH);

        // Events
        btnAdd.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                tfName.setText(model.getValueAt(row, 1).toString());
                tfEmail.setText(model.getValueAt(row, 2).toString());
                tfSalary.setText(model.getValueAt(row, 3).toString());
            }
        });

        connect();
        loadTable();
    }

    void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/EmployeeDB", "root", "root"
            );
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database Connection Failed");
        }
    }

    void loadTable() {
        try {
            pst = conn.prepareStatement("SELECT * FROM employees");
            rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();

            // Setup columns
            model.setRowCount(0);
            model.setColumnCount(0);
            for (int i = 1; i <= cols; i++) {
                model.addColumn(rsmd.getColumnName(i));
            }

            // Load data
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= cols; i++) {
                    row.add(rs.getString(i));
                }
                model.addRow(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addEmployee() {
        try {
            pst = conn.prepareStatement("INSERT INTO employees (name, email, salary) VALUES (?, ?, ?)");
            pst.setString(1, tfName.getText());
            pst.setString(2, tfEmail.getText());
            pst.setDouble(3, Double.parseDouble(tfSalary.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee Added");
            clearFields();
            loadTable();
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void updateEmployee() {
        try {
            int row = table.getSelectedRow();
            String id = model.getValueAt(row, 0).toString();
            pst = conn.prepareStatement("UPDATE employees SET name=?, email=?, salary=? WHERE id=?");
            pst.setString(1, tfName.getText());
            pst.setString(2, tfEmail.getText());
            pst.setDouble(3, Double.parseDouble(tfSalary.getText()));
            pst.setString(4, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee Updated");
            clearFields();
            loadTable();
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void deleteEmployee() {
        try {
            int row = table.getSelectedRow();
            String id = model.getValueAt(row, 0).toString();
            pst = conn.prepareStatement("DELETE FROM employees WHERE id=?");
            pst.setString(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee Deleted");
            clearFields();
            loadTable();
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void clearFields() {
        tfName.setText("");
        tfEmail.setText("");
        tfSalary.setText("");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Employee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
