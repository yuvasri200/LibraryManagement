import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class IssueBook extends JFrame {
    JTable table;
    DefaultTableModel model;
    JTextField txtBookId, txtUserId;
    JButton btnIssue, btnRefresh;

    public IssueBook() {
        setTitle("Issue Book");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245,245,255));
        setLayout(null);

        JLabel lblTitle = new JLabel("Issue Book", JLabel.CENTER);
        lblTitle.setBounds(200,10,200,30);
        lblTitle.setFont(new Font("Verdana",Font.BOLD,20));
        lblTitle.setForeground(new Color(0,50,150));
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

        btnIssue = new JButton("Issue");
        btnIssue.setBounds(200,90,150,30);
        btnIssue.setBackground(new Color(0,120,215));
        btnIssue.setForeground(Color.WHITE);
        add(btnIssue);

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
        btnIssue.addActionListener(e -> issueBook());
        btnRefresh.addActionListener(e -> loadData());

        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
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

    private void issueBook() {
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
            if(bookId <= 0 || userId <= 0) {
                JOptionPane.showMessageDialog(this, "IDs must be positive numbers ❌");
                return;
            }
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "IDs must be valid numbers ❌");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT quantity FROM books WHERE id=?");
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                int qty = rs.getInt("quantity");
                if(qty > 0){
                    PreparedStatement pst2 = con.prepareStatement(
                            "UPDATE books SET quantity=quantity-1 WHERE id=?"
                    );
                    pst2.setInt(1, bookId);
                    pst2.executeUpdate();
                    JOptionPane.showMessageDialog(this,"Book Issued ✅");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this,"Book Out of Stock ❌");
                }
            } else {
                JOptionPane.showMessageDialog(this,"Book ID not found ❌");
            }
        } catch(Exception e){ e.printStackTrace(); }
    }
}


