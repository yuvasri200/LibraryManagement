import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewBooks extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewBooks() {
        setTitle("View Books");
        setSize(550,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 245, 245));
        model = new DefaultTableModel();
        table = new JTable(model);

        // Columns
        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Publisher");
        model.addColumn("Quantity");

        // Header styling
        table.getTableHeader().setBackground(new Color(0, 120, 215));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll);

        // Load data from DB
        loadBooks();

        setVisible(true);
    }

    // Load books from database
    private void loadBooks() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");

            model.setRowCount(0); // Clear previous rows

            while(rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title") != null ? rs.getString("title") : "N/A",
                        rs.getString("author") != null ? rs.getString("author") : "N/A",
                        rs.getString("publisher") != null ? rs.getString("publisher") : "N/A",
                        rs.getInt("quantity")
                });
            }
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading books: " + e.getMessage());
        }
    }

    // For testing
    public static void main(String[] args) {
        new ViewBooks();
    }
}
