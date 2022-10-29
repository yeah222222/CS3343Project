
public class GenerateMerchantId {
    private String prefix = "M";
    private int currentId = 0;


    protected GenerateMerchantId() {
 
    }
    private static final GenerateMerchantId instance = new GenerateMerchantId();
    
    public static GenerateMerchantId getInstance() {
        return instance;
    }
  

    public String getNextId() {
        this.currentId += 1;
        String temp = String.format("%04d", currentId);
        return (this.prefix + temp);
    }
}
