public class GenerateCustomerBillNo {
    private String prefix = "B";
    private int currentId = 0;


    protected GenerateCustomerBillNo() {
 
    }
    private static final GenerateCustomerBillNo instance = new GenerateCustomerBillNo();
    
    public static GenerateCustomerBillNo getInstance() {
        return instance;
    }

    public String getNextBillNo() {
        this.currentId += 1;
        String temp = String.format("%04d", currentId);
        return (this.prefix + temp);
    }
}
