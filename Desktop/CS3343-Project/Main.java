import java.util.ArrayList;
import java.util.HashMap;

class Main {

    public static final InputScanner in = InputScanner.getInstance();

    // Temporary Database
    protected static ArrayList<Restaurants> listOfRestaurants = new ArrayList<>();

    // CustomerId, Customer instance
    protected static HashMap<String, Customers> listofCustomers = new HashMap<>();

    // MerchantId, Merchant instance
    protected static HashMap<String, Merchants> listofMerchants = new HashMap<>();

    // MerchantId, Restaurant
    protected static HashMap<String, Restaurants> listofMerchantsnRestaurant = new HashMap<>();

    private static Visitor visitor = Visitor.getInstance();

    /*
     * Main Function
     */

    public static void loginNRegisterNDelete() {
        int select = 0, success = -1;
        String returnValue = null, ID = null;

        String input = "";

        do {
            try {
                System.out.print("\nPlease choose your operation [1 Login | 2 Register | 3 Delete Account] : ");
                input = Main.in.next("Input: ");
                select = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                e.getStackTrace();
            }

            if (select == 1) {
                // Login
                returnValue = login();
                if (!returnValue.equals(null)) {
                    ID = returnValue; // UserId
                    success = 1;
                    break;
                }

                // 註冊完進入module run()
                if (success == 1) {
                    if(visitor.distinguishMerchantandCustomer(ID).equals("C")){
                        CustomerModule.run(listofCustomers.get(ID));
                    }
                    else if(visitor.distinguishMerchantandCustomer(ID).equals("M")){
                        MerchantModule.run(listofMerchants.get(ID));
                    }
                    else if(visitor.distinguishMerchantandCustomer(ID).equals("A")){
                        AdminModule.run();
                    }
                    else {
                        System.out.println("There is some error in running corresponding module.");
                    }
                    // TODO: 分辨UserId是Customer/merchant/admin
                  
            } else if (select == 2) {
                // register -> 完成後返回到這裡選擇login
                success = register();
            } else if (select == 3) {
                // delete account
                success = deleteAcc();
            }
            // testing
            System.out.println("success: " + success);
        }
        } while (select != 1 && select != 3 || success == -1);

    }

    public static void main(String[] args) {

        Initialization.initiateRestaurants();
        Initialization.initiateDish();
        Initialization.initiateMerchants();
        Initialization.initiateCustomers();

        visitor.printAllActiveAccounts();

        while (true) {
            loginNRegisterNDelete();
        }

    }

    public static String login() {

        String input = "", username = "", password = "";

        System.out.print("\nPlease input the username: ");
        input = Main.in.next("Input: ");
        username = input;

        System.out.print("\nPlease input the password: ");
        input = Main.in.next("Input: ");
        password = input;

        // 返回ID，需判断是Customer/Merchant/Admin
        String temp = visitor.login(username, password);

        // Testing

        System.out.println("customerId: " + temp);

        if (temp.equals("")) {
            return null;
        } else {
            return temp;
        }
    }

    public static int register() {
        String input = "", username = "", password = "";
        int select = 0;
        boolean registerFinish = false;

        // 1: 註冊Customer帳戶
        // 2: 註冊商戶帳戶（一併建立餐廳）
        do {
            try {
                System.out.print(
                        "\nPlease choose the type of account to register [1 Customer | 2 Merchant | 3 Cancel]: ");
                input = Main.in.next("Input: ");
                select = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if (select == 1 || select == 2) {

                System.out.print("\nPlease input the username: ");
                input = Main.in.next("Input: ");
                username = input;

                System.out.print("\nPlease input the password: ");
                input = Main.in.next("Input: ");
                password = input;

                if (select == 1) {
                    registerFinish = visitor.registerCustomer(username, password);

                } else if (select == 2) {
                    String rName;
                    System.out.println("\nPlease input the name of the Restaurant: ");
                    input = Main.in.next("Input: ");
                    rName = input;

                    registerRestaurant(rName);
                    registerFinish = visitor.registerMerchant(username, password, matchRestaurant(rName));
                }
            } else if (select == 3) {
                break;
            } else {
                // TODO： 重新输入
            }
        } while (select != 1 && select != 2 || registerFinish == false);

        if (registerFinish == true) {
            return 1;
        } else {
            return -1;
        }
    }

    public static int deleteAcc() {
        String username = "", input = "";

        System.out.print("\nPlease input the username to delete account: ");
        input = Main.in.next("Input: ");
        username = input;

        boolean success = visitor.deleteaccountinUserNameAndAccount(username);
        if (success == true) {
            return 1;
        } else {
            return -1;
        }
    }

    public static void registerRestaurant(String rName) {
        listOfRestaurants.add(new Restaurants(rName));
    }

    public static void addTolistOfRestaurants(Restaurants restaurant) {
        listOfRestaurants.add(restaurant);
    }

    // in MerchantModule.run()
    public static Customers matchUserName(String username) {
        return listofCustomers.get(username);
    }

    // in Payment paythebill()
    public static Merchants matchStaffUserName(String username) {
        return listofMerchants.get(username);
    }

    // in AdminModule.removeRestaurant()
    public static Restaurants matchRestaurant(String rName) {
        for (int i = 0; i < listOfRestaurants.size(); i++) {
            if (listOfRestaurants.get(i).toString().equals(rName)) {
                return listOfRestaurants.get(i);
            }
        }
        return null;
    }
}
