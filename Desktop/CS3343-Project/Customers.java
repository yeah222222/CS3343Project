import java.util.ArrayList;

public class Customers implements UserType {

    private String username;
    protected String password;
    private String CId;
    private String billno;

    private double billAmount;
    // private Tables reservedtable;

    protected CustomerState customerState;
    private CustomerMembership membership; // State

    private Restaurants restaurantChosed;
    private ArrayList<Dish> orders = new ArrayList<>();

    // constructor
    public Customers(String username, String userid) {
        this.username = username;
        this.CId = userid;
        this.membership = new CustomerMembership(this);
        setBillNo();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getUserId() {
        return CId;
    }

    public String getID() {
        return this.CId;
    }

    public CustomerMembership getMembership() {
        return membership;
    }

    public void setBillNo() {
        GenerateCustomerBillNo genBillNo = GenerateCustomerBillNo.getInstance();
        this.billno = genBillNo.getNextBillNo();
    }

    public String printBillNo() {
        return this.billno;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    // for Payment module
    public double getBillAmount() {
        return billAmount;
    }

    // customer choose restaurant to order
    public void chooseRestaurant(Restaurants restaurants) {
        this.restaurantChosed = restaurants;
    }

    // get chosed restaurants
    public Restaurants getRestaurantChosed() {
        return this.restaurantChosed;
    }

    // customer order dish
    public ArrayList<Dish> orderdish(Dish dish) {
        this.orders.add(dish);
        return this.orders;
    }

    // customer delete dish
    public ArrayList<Dish> deletedish(Dish dish) {
        this.orders.remove(dish);
        return this.orders;
    }

    // get ArrayList of customer's official-confirmed orders
    public ArrayList<Dish> customerOrders() {
        return this.orders;
    }

    // print official customer's orders
    public void printOrders() {
        System.out.println("Official Orders: ");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + orders.get(i).toString());
        }
    }

    // About table reservation
    /*
     * public void reserveTable() {
     * this.reservedtable.setReserved();
     * }
     * 
     * public void deleteReserveTable() {
     * this.reservedtable.cancelReserved();
     * }
     */
}
