import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        setTitle("Library Dashboard");
        setSize(400,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(220, 240, 255)); // light blue
        setLayout(null);

        JLabel lblTitle = new JLabel("Library Dashboard", JLabel.CENTER);
        lblTitle.setBounds(50, 10, 300, 40);
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 22));
        lblTitle.setForeground(new Color(50, 50, 50));
        add(lblTitle);

        JButton addBook = new JButton("Add Book");
        addBook.setBounds(100, 60, 200, 40);
        addBook.setBackground(new Color(0, 120, 215));
        addBook.setForeground(Color.WHITE);
        addBook.setFont(new Font("Arial", Font.BOLD, 16));
        add(addBook);

        JButton viewBook = new JButton("View Books");
        viewBook.setBounds(100, 110, 200, 40);
        viewBook.setBackground(new Color(0, 120, 215));
        viewBook.setForeground(Color.WHITE);
        viewBook.setFont(new Font("Arial", Font.BOLD, 16));
        add(viewBook);

        JButton issueBook = new JButton("Issue Book");
        issueBook.setBounds(100, 160, 200, 40);
        issueBook.setBackground(new Color(0, 120, 215));
        issueBook.setForeground(Color.WHITE);
        issueBook.setFont(new Font("Arial", Font.BOLD, 16));
        add(issueBook);

        JButton returnBook = new JButton("Return Book");
        returnBook.setBounds(100, 210, 200, 40);
        returnBook.setBackground(new Color(0, 120, 215));
        returnBook.setForeground(Color.WHITE);
        returnBook.setFont(new Font("Arial", Font.BOLD, 16));
        add(returnBook);

        JButton logout = new JButton("Logout");
        logout.setBounds(100, 260, 200, 40);
        logout.setBackground(Color.RED);
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Arial", Font.BOLD, 16));
        add(logout);

        addBook.addActionListener(e -> new AddBook());
        viewBook.addActionListener(e -> new ViewBooks());
        issueBook.addActionListener(e -> new IssueBook());
        returnBook.addActionListener(e -> new ReturnBook());
        logout.addActionListener(e -> {
            dispose(); // Dashboard close பண்ணும்
            new LoginForm().setVisible(true); // LoginForm open பண்ணும்
        });


        setVisible(true);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
