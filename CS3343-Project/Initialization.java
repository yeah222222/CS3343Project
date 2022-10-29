public class Initialization {

    private static Visitor visitor = Visitor.getInstance();
    // 测试模拟代码
    static Restaurants PepperLunch = new Restaurants("Pepper-Lunch");
    static Restaurants TamJai = new Restaurants("Tam-Jai-Mi-Xian");
    static Restaurants McDonalds = new Restaurants("McDonald's");
    static Restaurants KFC = new Restaurants("KFC");

    static Dish hotChoco = new Dish("Hot-Chocolate", 16.0);
    static Dish latte = new Dish("Latte", 18.0);
    static Dish Americano = new Dish("Americano", 18.0);
    static Dish CrunchyOvaltine = new Dish("Crunchy-Ovaltine", 33.0);
    static Dish FiletOFish = new Dish("Filet-O-Fish", 12.0);
    static Dish BeefnEggBurger = new Dish("Beef-Egg-Burger", 13.0);
    static Dish BeefFries = new Dish("Beef", 59.9);
    static Dish PorkFries = new Dish("Pork", 54.9);
    static Dish TurkeyFries = new Dish("Turkey", 52.9);
    static Dish PorkMixian = new Dish("Pork-Mixian", 34.9);
    static Dish LettuceMixian = new Dish("Lettuce-Mixian", 29.9);

    public static void initiateRestaurants() {
        Main.addTolistOfRestaurants(PepperLunch);
        Main.addTolistOfRestaurants(TamJai);
        Main.addTolistOfRestaurants(McDonalds);
        Main.addTolistOfRestaurants(KFC);
    }

    public static void initiateDish() {
        McDonalds.adddishtoMenu(hotChoco);
        McDonalds.adddishtoMenu(latte);
        McDonalds.adddishtoMenu(Americano);
        McDonalds.adddishtoMenu(CrunchyOvaltine);

        KFC.adddishtoMenu(FiletOFish);
        KFC.adddishtoMenu(BeefnEggBurger);

        PepperLunch.adddishtoMenu(BeefFries);
        PepperLunch.adddishtoMenu(PorkFries);
        PepperLunch.adddishtoMenu(TurkeyFries);

        TamJai.adddishtoMenu(LettuceMixian);
        TamJai.adddishtoMenu(PorkMixian);
    }

    public static void initiateMerchants() {
        visitor.registerMerchant("KFCWorker", "t123", KFC);
        visitor.registerMerchant("McDonaldWorker", "t123", McDonalds);
        visitor.registerMerchant("TamJaiWorker", "t123", TamJai);
        visitor.registerMerchant("PepperLunchWorker", "t123", PepperLunch);
    }

    public static void initiateCustomers() {
        visitor.registerCustomer("yinch33", "t123");
        visitor.registerCustomer("ta123", "t123");
        visitor.registerCustomer("wedu2", "t123");
    }
}
