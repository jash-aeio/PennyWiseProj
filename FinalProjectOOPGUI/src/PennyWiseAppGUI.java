import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PennyWiseAppGUI {
    private UserAuth userAuth;
    private PennyWise pennyWise;
    private PennyWish pennyWish;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private PennyWiseManager pennyWiseManager;

    public PennyWiseAppGUI() {
        userAuth = new UserAuth();
        pennyWiseManager = new PennyWiseManager(); // Initialize PennyWiseManager
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Penny Wise App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(240, 240, 240)); // Light gray background

        // Create panels for different screens
        mainPanel.add(createWelcomePanel(), "Welcome");
        mainPanel.add(createMainMenuPanel(), "Main Menu");
        mainPanel.add(createPennyWisePanel(), "PennyWise Menu");
        mainPanel.add(createPennyWishPanel(), "PennyWish Menu");

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        JLabel label = new JLabel("Welcome to Penny Wise App!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color

        JButton signUpButton = createStyledButton("Sign Up");
        JButton loginButton = createStyledButton("Log In");
        JButton exitButton = createStyledButton("Exit");

        signUpButton.addActionListener(e -> userAuth.signUp());
        loginButton.addActionListener(e -> {
            if (userAuth.login()) {
                String username = userAuth.getCurrentUsername(); // Get the current username
                pennyWise = new PennyWise(pennyWiseManager, username); // Initialize PennyWise
                pennyWish = new PennyWish(pennyWiseManager, username); // Initialize PennyWish
                pennyWise.loadUserData(); // Load user data
                pennyWish.loadUserData(); // Load user data
                cardLayout.show(mainPanel, "Main Menu");
            }
        });
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(label);
        panel.add(signUpButton);
        panel.add(loginButton);
        panel.add(exitButton);

        return panel;
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Main Menu", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(0, 102, 204));

        JButton pennyWiseButton = createStyledButton("PennyWise (Expense Tracker)", "icons/pennywise.png");
        JButton pennyWishButton = createStyledButton("PennyWish (Special Menu)", "icons/pennywish.png");
        JButton signOutButton = createStyledButton("Sign Out", "icons/signout.png");
        JButton exitButton = createStyledButton("Exit", "icons/exit.png");

        pennyWiseButton.addActionListener(e -> {
            pennyWise.reset(); // Reset PennyWise before showing
            cardLayout.show(mainPanel, "PennyWise Menu");
        });
        pennyWishButton.addActionListener(e -> {
            pennyWish.reset(); // Reset PennyWish before showing
            cardLayout.show(mainPanel, "PennyWish Menu");
        });
        signOutButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
        exitButton.addActionListener(e -> {
            pennyWise.saveUserData(); // Save data on exit
            System.exit(0);
 });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make buttons fill the width
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(label, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.weightx = 1.0; // Allow buttons to expand
        panel.add(pennyWiseButton, gbc);

        gbc.gridy++;
        panel.add(pennyWishButton, gbc);

        gbc.gridy++;
        panel.add(signOutButton, gbc);

        gbc.gridy++;
        panel.add(exitButton, gbc);

        return panel;
    }

    private JPanel createPennyWisePanel() {
        JPanel panel = new JPanel(new GridLayout(8, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("PennyWise Menu", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(0, 102, 204));

        JButton setBudgetButton = createStyledButton("Set Budget");
        JButton addExpenseButton = createStyledButton("Add Expense");
        JButton viewExpensesButton = createStyledButton("View Expenses");
        JButton generateSummaryButton = createStyledButton("Generate Summary");
        JButton clearAllExpensesButton = createStyledButton("Clear All Expenses");
        JButton removeSpecificExpenseButton = createStyledButton("Remove Specific Expense");
        JButton backButton = createStyledButton("Back to Main Menu");

        clearAllExpensesButton.addActionListener(e -> {
            pennyWise.clearAllExpenses();
            JOptionPane.showMessageDialog(panel, "All expenses cleared!");
            pennyWise.saveUserData();
        });
        removeSpecificExpenseButton.addActionListener(e -> {
            String category = JOptionPane.showInputDialog("Enter the category of the expense to remove:");
            pennyWise.removeSpecificExpense(category);
            JOptionPane.showMessageDialog(panel, "Expense removed if it existed.");
            pennyWise.saveUserData();
        });

        setBudgetButton.addActionListener(e -> {
            pennyWise.setBudget();
            JOptionPane.showMessageDialog(panel, "Budget Set!");
            pennyWise.saveUserData();
        });
        addExpenseButton.addActionListener(e -> {
            pennyWise.addExpense();
            JOptionPane.showMessageDialog(panel, "Expense Added!");
            pennyWise.saveUserData();
        });
        viewExpensesButton.addActionListener(e -> {
            String expenses = pennyWise.viewExpenses();
            JOptionPane.showMessageDialog(panel, expenses);
        });
        generateSummaryButton.addActionListener(e -> {
            String summary = pennyWise.generateSummary();
            JOptionPane.showMessageDialog(panel, summary);
        });
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        panel.add(label);
        panel.add(setBudgetButton);
        panel.add(addExpenseButton);
        panel.add(viewExpensesButton);
        panel.add(generateSummaryButton);
        panel.add(clearAllExpensesButton);
        panel.add(removeSpecificExpenseButton);
        panel.add(backButton);

        return panel;
    }

    private JPanel createPennyWishPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("PennyWish Menu", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(0, 102, 204));

        JButton viewWishesButton = createStyledButton("View Wishes");
        JButton addWishButton = createStyledButton("Add Wish");
        JButton clearWishlistButton = createStyledButton("Clear Wishlist");
        JButton removeSpecificWishButton = createStyledButton("Remove Specific Wish");
        JButton generateSummaryButton = createStyledButton("Generate Penny Wish Summary");
        JButton backButton = createStyledButton("Back to Main Menu");

        viewWishesButton.addActionListener(e -> {
            String wishes = pennyWish.viewWishes();
            JOptionPane.showMessageDialog(panel, wishes);
        });

        addWishButton.addActionListener(e -> {
            pennyWish.addWish();
            JOptionPane.showMessageDialog(panel, "Wish Added!");
            pennyWish.saveUserData();
        });

        clearWishlistButton.addActionListener(e -> {
            pennyWish.clearWishlist();
            JOptionPane.showMessageDialog(panel, "Wishlist cleared!");
            pennyWish.saveUserData();
        });

        removeSpecificWishButton.addActionListener(e -> {
            String itemName = JOptionPane.showInputDialog("Enter the name of the wish to remove:");
            pennyWish.removeSpecificWish (itemName);
            JOptionPane.showMessageDialog(panel, "Wish removed if it existed.");
            pennyWish.saveUserData();
        });

        generateSummaryButton.addActionListener(e -> {
            String summary = pennyWish.generateSummary();
            JOptionPane.showMessageDialog(panel, summary);
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        panel.add(label);
        panel.add(viewWishesButton);
        panel.add(addWishButton);
        panel.add(clearWishlistButton);
        panel.add(removeSpecificWishButton);
        panel.add(generateSummaryButton);
        panel.add(backButton);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(0, 153, 255)); // Light blue background
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215)); // Darker blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 153, 255)); // Original color
            }
        });
        return button;
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = createStyledButton(text);
        ImageIcon icon = new ImageIcon(iconPath);
        button.setIcon(icon);
        button.setHorizontalTextPosition(SwingConstants.RIGHT); // Text on the right of the icon
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PennyWiseAppGUI::new);
    }
}