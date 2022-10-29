public class Admin implements UserType {

    private String adminId = "A0001";

    private String adminUsername = "Admin";
    protected String adminPassword = "admin1234";
    private static Admin instance = new Admin();

    public Admin() {
    }

    public static Admin getInstance() {
        return instance;
    }

    @Override
    public String getUsername() {
        return adminUsername;
    }

    @Override
    public String getUserId() {
        return adminId;
    }

    // Admin force-add Restaurants into Main.listOfRestaurants
    public static void addRestaurant(Restaurants res) {
        Main.listOfRestaurants.add(res);
    }

    // Admin force-delete Restaurants into Main.listOfRestaurants
    public static void deleteRestaurant(Restaurants res) {
        Main.listOfRestaurants.remove(res);
    }

    /*
     * For Customer Force Edit
     */

    // TODO: set the discount of the different kinds of customers
    public void setstateanddiscount(CustomerState state, double discount) {
        state.setdiscount(discount);
    }

    // TODO: force set customer state
    public void setCustomerState() {

    }

    // TODO: Force set customerState discount
    public void setCustomerStateDiscount() {

    }

    // TODO: Output the order only
    public void checkCustomerOrder() {

    }

    /*
     * For Payment - Force Refund
     */

    // Customer apply refund -> Force Refund
    public void refund() {

    }

    /*
     * For Table and Reservation Force Edit
     */

    // TODO: Force set status of the table
    public void setTableStatus() {

    }
}
