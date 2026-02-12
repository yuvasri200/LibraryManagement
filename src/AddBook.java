import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddBook extends JFrame {
    JTextField txtTitle, txtAuthor, txtPublisher, txtQuantity;
    JButton btnAdd;

    public AddBook() {
        setTitle("Add New Book");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 245, 230));
        setLayout(new GridLayout(5,2,10,10));

        JLabel lblTitle = new JLabel("Title:"); add(lblTitle);
        txtTitle = new JTextField(); add(txtTitle);

        JLabel lblAuthor = new JLabel("Author:"); add(lblAuthor);
        txtAuthor = new JTextField(); add(txtAuthor);

        JLabel lblPublisher = new JLabel("Publisher:"); add(lblPublisher);
        txtPublisher = new JTextField(); add(txtPublisher);

        JLabel lblQuantity = new JLabel("Quantity:"); add(lblQuantity);
        txtQuantity = new JTextField(); add(txtQuantity);

        btnAdd = new JButton("Add Book");
        btnAdd.setBackground(new Color(0,150,0));
        btnAdd.setForeground(Color.WHITE);
        add(new JLabel(""));
        add(btnAdd);

        btnAdd.addActionListener(e -> addBookToDB());

        setVisible(true);
    }

    private void addBookToDB() {
        String title = txtTitle.getText().trim();
        String author = txtAuthor.getText().trim();
        String publisher = txtPublisher.getText().trim();
        String qtyText = txtQuantity.getText().trim();

        // ✅ Validation
        if(title.isEmpty() || author.isEmpty() || publisher.isEmpty() || qtyText.isEmpty()) {
            JOptionPane.showMessageDialog(this,"All fields required ❌");
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(qtyText);
            if(qty <= 0) throw new NumberFormatException();
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Quantity must be a positive number ❌");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO books(title,author,publisher,quantity) VALUES(?,?,?,?)"
            );
            pst.setString(1,title);
            pst.setString(2,author);
            pst.setString(3,publisher);
            pst.setInt(4,qty);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this,"Book Added ✅");
            this.dispose();
        } catch(Exception ex){ ex.printStackTrace(); }
    }
}
