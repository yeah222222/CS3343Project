
public class GenerateCustomerId {
    private String prefix = "C";
    private int currentId = 0;


    protected GenerateCustomerId() {
 
    }
    private static final GenerateCustomerId instance = new GenerateCustomerId();
    
    public static GenerateCustomerId getInstance() {
        return instance;
    }

    public String getNextId() {
        this.currentId += 1;
        String temp = String.format("%04d", currentId);
        return (this.prefix + temp);
    }
}
