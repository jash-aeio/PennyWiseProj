import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PennyWish {
    private List<WishItem> wishList = new ArrayList<>();
    private PennyWiseManager pennyWiseManager;
    private String username;

    public PennyWish(PennyWiseManager pennyWiseManager, String username) {
        this.pennyWiseManager = pennyWiseManager;
        this.username = username;
        loadUserData(); // Load user data on initialization
    }

    public void loadUserData() {
        UserData userData = pennyWiseManager.getUserData(username);
        if (userData != null) {
            this.wishList = userData.getWishList();
        } else {
            this.wishList = new ArrayList<>();
        }
    }

    public void reset() {
        wishList.clear();
    }

    public void addWish() {
        String item = JOptionPane.showInputDialog("Enter wishlist item:");
        String priceStr = JOptionPane.showInputDialog("Enter price: PHP");
        try {
            double price = Double.parseDouble(priceStr);
            wishList.add(new WishItem(item, price));
            JOptionPane.showMessageDialog(null, "Item added to wishlist.");
            saveUserData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price. Please enter a valid number.");
        }
    }

    public String viewWishes() {
        if (wishList.isEmpty()) {
            return "Your wishlist is empty.";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("+-----------------------------+\n");
            sb.append("|       Item       |   Price   |\n");
            sb.append("+-----------------------------+\n");
            for (WishItem item : wishList) {
                sb.append(String.format("| %-15s | %9.2f |\n", item.getName(), item.getPrice()));
            }
            sb.append("+-----------------------------+\n");
            return sb.toString();
        }
    }

    public void clearWishlist() {
        wishList.clear();
        JOptionPane.showMessageDialog(null, "Wishlist cleared!");
        saveUserData();
    }

    public void removeSpecificWish(String itemName) {
        boolean removed = wishList.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        if (removed) {
            JOptionPane.showMessageDialog(null, "Wish removed: " + itemName);
        } else {
            JOptionPane.showMessageDialog(null, "Wish not found: " + itemName);
        }
        saveUserData();
    }

    public String generateSummary() {
        if (wishList.isEmpty()) {
            return "Your wishlist is empty.";
        }

        double totalCost = wishList.stream().mapToDouble(WishItem::getPrice).sum();
        double savingsPerPeriod = Double.parseDouble(JOptionPane.showInputDialog("Enter your preferred savings amount: PHP"));

        String[] options = {"Daily", "Weekly", "Monthly"};
        String period = (String) JOptionPane.showInputDialog(null, " Select a saving period:", "Savings Period",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (period == null) {
            return "No period selected.";
        }

        int periodsNeeded = (int) Math.ceil(totalCost / savingsPerPeriod);
        StringBuilder summary = new StringBuilder();
        summary.append("Total Wishlist Cost: PHP ").append(totalCost).append("\n");
        summary.append("You need to save PHP ").append(savingsPerPeriod).append(" ").append(period).append(" for ").append(periodsNeeded).append(" periods.\n");

        // Calculate the dates for savings
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        summary.append("Savings Dates:\n");
        for (int i = 1; i <= periodsNeeded; i++) {
            LocalDate savingDate;
            switch (period.toLowerCase()) {
                case "daily":
                    savingDate = today.plusDays(i);
                    break;
                case "weekly":
                    savingDate = today.plusWeeks(i);
                    break;
                case "monthly":
                    savingDate = today.plusMonths(i);
                    break;
                default:
                    return "Invalid period. Please select Daily, Weekly, or Monthly.";
            }
            summary.append(" - ").append(savingDate.format(formatter)).append("\n");
        }

        return summary.toString();
    }

    void saveUserData() {
        UserData userData = new UserData();
        userData.setUsername(username);
        userData.setWishList(wishList);
        pennyWiseManager.saveUserData(username, userData);
    }
}