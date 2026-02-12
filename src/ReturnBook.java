import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ReturnBook extends JFrame {
    JTable table;
    DefaultTableModel model;
    JTextField txtBookId, txtUserId;
    JButton btnReturn, btnRefresh;

    public ReturnBook() {
        setTitle("Return Book");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 245, 230));
        setLayout(null);

        JLabel lblTitle = new JLabel("Return Book", JLabel.CENTER);
        lblTitle.setBounds(200,10,200,30);
        lblTitle.setFont(new Font("Verdana",Font.BOLD,20));
        lblTitle.setForeground(new Color(50,50,150));
        add(lblTitle);

        JLabel lblBookId = new JLabel("Book ID:");
        lblBookId.setBounds(50,50,100,25);
        add(lblBookId);
        txtBookId = new JTextField();
        txtBookId.setBounds(150,50,150,25);
        add(txtBookId);

        JLabel lblUserId = new JLabel("User ID:");
        lblUserId.setBounds(320,50,100,25);
        add(lblUserId);
        txtUserId = new JTextField();
        txtUserId.setBounds(400,50,150,25);
        add(txtUserId);

        btnReturn = new JButton("Return");
        btnReturn.setBounds(200,90,150,30);
        btnReturn.setBackground(new Color(0,120,215));
        btnReturn.setForeground(Color.WHITE);
        add(btnReturn);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(360,90,150,30);
        btnRefresh.setBackground(new Color(0,150,0));
        btnRefresh.setForeground(Color.WHITE);
        add(btnRefresh);

        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Book ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Publisher");
        model.addColumn("Quantity");

        table.getTableHeader().setBackground(new Color(0,120,215));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,14));
        table.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50,140,500,200);
        add(scroll);

        loadData();

        btnReturn.addActionListener(e -> returnBook());
        btnRefresh.addActionListener(e -> loadData());

        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0); // clear table
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");
            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("quantity")
                });
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void returnBook() {
        String bookIdText = txtBookId.getText().trim();
        String userIdText = txtUserId.getText().trim();

        if(bookIdText.isEmpty() || userIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both Book ID and User ID ❌");
            return;
        }

        int bookId, userId;
        try {
            bookId = Integer.parseInt(bookIdText);
            userId = Integer.parseInt(userIdText);
            if(bookId <= 0 || userId <= 0){
                JOptionPane.showMessageDialog(this,"IDs must be positive numbers ❌");
                return;
            }
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"IDs must be valid numbers ❌");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pstCheck = con.prepareStatement("SELECT * FROM books WHERE id=?");
            pstCheck.setInt(1, bookId);
            ResultSet rs = pstCheck.executeQuery();

            if(rs.next()) {
                // Update quantity
                PreparedStatement pstUpdate = con.prepareStatement(
                        "UPDATE books SET quantity = quantity + 1 WHERE id=?"
                );
                pstUpdate.setInt(1, bookId);
                pstUpdate.executeUpdate();
                JOptionPane.showMessageDialog(this,"Book Returned ✅");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,"Book ID not found ❌");
            }

        } catch(Exception e){ e.printStackTrace(); }
    }

    public static void main(String[] args){
        new ReturnBook();
    }
}

