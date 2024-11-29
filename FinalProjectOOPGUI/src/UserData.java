import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private double totalBudget;
    private List<BudgetItem> budgetItems;
    private List<WishItem> wishList;

    public UserData() {
        budgetItems = new ArrayList<>();
        wishList = new ArrayList<>();
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public List<BudgetItem> getBudgetItems() {
        return budgetItems;
    }

    public void setBudgetItems(List<BudgetItem> budgetItems) {
        this.budgetItems = budgetItems;
    }

    public List<WishItem> getWishList() {
        return wishList;
    }

    public void setWishList(List<WishItem> wishList)
    {
        this.wishList = wishList;
    }
}