import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Visitor {
    /*
     * 先加载txt文件到hashmap里面
     */

    protected static HashMap<String, String> allaccounts = new HashMap<>();// username-> userId
    protected static HashMap<String, String> usernamesandPasswords = new HashMap<>();// username->password

    protected Visitor() {
        initializationALLACCOUNTStxt();
        initializationUsernameandPasswordtxt();
    }

    private static final Visitor instance = new Visitor();

    public static Visitor getInstance() {
        return instance;
    }

    public int getRandomID() {
        Random r = new Random();

        // generate from range(2, 1000)
        return r.nextInt(1000 - 2) + 2;
    }

    // initialize into the hashmap
    public void initializationUsernameandPasswordtxt() {
        FileInputStream freader;
        try {
            freader = new FileInputStream("UsernameAndPassword.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);

            List<HashMap<String, String>> list = (List<HashMap<String, String>>) objectInputStream.readObject();
            for (HashMap<String, String> map : list) {
                Visitor.usernamesandPasswords = map;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initializationALLACCOUNTStxt() {
        FileInputStream freader;
        try {
            freader = new FileInputStream("ALLACCOUNTS.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);

            List<HashMap<String, String>> list = (List<HashMap<String, String>>) objectInputStream.readObject();
            for (HashMap<String, String> map : list) {
                Visitor.allaccounts = map;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 注册account并且生成每个顾客独一无二的customerid
    public void customerIDPutHashMap(String username, String Id) {

        Visitor.allaccounts.put(username, Id);
    }

    // login
    public String login(String username, String password) {

        String userId = "";

        if (Visitor.usernamesandPasswords.containsKey(username)
                && Visitor.usernamesandPasswords.get(username).equals(password)) {

            System.out.println("\nYou have succeeded in logging in the system.");

            userId = Visitor.allaccounts.get(username);
            System.out.println("\nUserId: " + userId);
            return userId;

        } else if (Visitor.usernamesandPasswords.containsKey(username)
                && Visitor.usernamesandPasswords.get(username).equals(password)) {

            System.out.println("\nYour password is wrong. Please try again.");

        } else if (!Visitor.usernamesandPasswords.containsKey(username)) {

            System.out.println("\nWe cannot find the username. Please register first.");
        }
        return "";
    }

    // register Customer Account
    public boolean registerCustomer(String username, String password) {
        if (Visitor.usernamesandPasswords.containsKey(username)) {
            System.out.println("\nThis username has been registered. Please choose another username.");

            return false;
        } else {
            GenerateCustomerId genCId = GenerateCustomerId.getInstance();
            String customerId = genCId.getNextId();

            // 隨機生成UserId並放到HashMap
            customerIDPutHashMap(username, customerId);

            Visitor.usernamesandPasswords.put(username, password);

            // 加到Main的customer hashmap中
            Main.listofCustomers.put(allaccounts.get(username), 
            new Customers(username, allaccounts.get(username)));

            return true;
        }
    }

    // Register Merchant account
    public boolean registerMerchant(String username, String password, Restaurants restaurant) {
        if (Visitor.usernamesandPasswords.containsKey(username)) {
            System.out.println("\nThis username has been registered. Please choose another username.");
            return false;
        } else {
            GenerateMerchantId genMId = GenerateMerchantId.getInstance();
            String merchantID = genMId.getNextId();
            // 隨機生成UserId並放到HashMap
            customerIDPutHashMap(username, merchantID);

            Visitor.usernamesandPasswords.put(username, password);

            // 加到Main的customer hashmap中
            Main.listofMerchants.put(allaccounts.get(username),
                    new Merchants(username, allaccounts.get(username), restaurant));
            Main.listofMerchantsnRestaurant.put(allaccounts.get(username), restaurant);

            return true;
        }
    }

    // 退出时将hashmap中的数据存入对应的txt文件中
    public void exit1andStoreAllaccounts() throws Exception {
        try {
            File file = new File("ALLACCOUNTS.txt");
            file.delete();
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            list.add(Visitor.allaccounts);
            FileOutputStream fileOutputStream = new FileOutputStream("ALLACCOUNTS.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            fileOutputStream.close();
            System.out.println("\nYou have succeeded in input the data to the ALLACCOUNTS.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit2andStoreUsernameAndPassword() throws Exception {
        try {
            File file = new File("UsernameAndPassword.txt");
            file.delete();
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            list.add(Visitor.usernamesandPasswords);
            FileOutputStream fileOutputStream = new FileOutputStream("UsernameAndPassword.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            fileOutputStream.close();
            System.out.println("\nYou have succeeded in input the data to the UsernameAndPassword.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 注销账户，把二进制文件中对应的删掉
    public boolean deleteaccountinUserNameAndAccount(String username) {
        if (Visitor.usernamesandPasswords.containsKey(username)) {
            Visitor.usernamesandPasswords.remove(username);

            // 順便把另一個txt也清理掉
            boolean finished = deleteaccountinUserNameAndCustomerid(username);
            if (finished == true) {
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("\nPlease enter the correct username.");
            return false;
        }
    }

    public boolean deleteaccountinUserNameAndCustomerid(String username) {
        if (Visitor.allaccounts.containsKey(username)) {
            Visitor.usernamesandPasswords.remove(username);
            return true;
        }
        return false;
    }

    public void printAllActiveAccounts() {
        System.out.println("\nList of Active Accounts: ");

        allaccounts.entrySet().forEach(entry -> {
            System.out.println(entry.getValue());

        });
    }

    public String distinguishMerchantandCustomer(String userid){
        return userid.substring(0,1);
    }

}

// //login
// public void login(String username,String password){
// FileInputStream freader;
// try {
// freader = new FileInputStream("UsernameAndPassword.txt");
// ObjectInputStream objectInputStream = new ObjectInputStream(freader);

// List<HashMap<String, String>> list=(List<HashMap<String,
// String>>)objectInputStream.readObject();
// for (HashMap<String, String> map : list) {
// if(map.containsKey(username)&&map.get(username)==password){
// this.islogin=true;
// System.out.println("You have succeeded in logging in the system.");
// }
// else if(map.containsKey(username)&&map.get(username)!=password){
// System.out.println("Your password is wrong. Please input the password
// again.");
// }
// else if(!map.containsKey(username)){
// System.out.println("We can not find the username. Please register first.");
// }
// }

// } catch (FileNotFoundException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// } catch (ClassNotFoundException e) {
// e.printStackTrace();
// }
// }
// register
// public void register(String username,String password)throws
// FileNotFoundException,IOException,ClassCastException{
// FileInputStream freader;
// try {
// freader = new FileInputStream("UsernameAndPassword.txt");
// ObjectInputStream objectInputStream = new ObjectInputStream(freader);

// List<HashMap<String, String>> list=(List<HashMap<String,
// String>>)objectInputStream.readObject();
// for (HashMap<String, String> map : list) {
// if(map.containsKey(username)){
// System.out.println("This username has been registered. Please choose another
// username.");
// }
// else {
// this.usernamesandPasswords.put(username, password);
// }
// }

// } catch (FileNotFoundException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// } catch (ClassNotFoundException e) {
// e.printStackTrace();
// }

// }

// //注销账户，把二进制文件中对应的删掉
// public void deleteaccountinUserNameAndAccount(String username,int
// customerid)throws
// Exception,FileNotFoundException,IOException,ClassNotFoundException{
// //把原来文件里的东西拿出来，并且删除某一行
// FileInputStream freader;
// try {
// freader = new FileInputStream("UsernameAndPassword.txt");
// ObjectInputStream objectInputStream = new ObjectInputStream(freader);
// HashMap<String,String> temp=new HashMap<>();
// List<HashMap<String, String>> list=(List<HashMap<String,
// String>>)objectInputStream.readObject();
// //System.out.println(list);
// for (HashMap<String, String> map : list) {
// if(map.containsKey(username)){
// map.remove(username);
// //删除原来的文件
// File file=new File("UsernameAndPassword.txt");
// file.delete();
// //放入新的文件中，
// try{
// List<HashMap<String,String>> newlist=new ArrayList<HashMap<String,String>>();
// newlist.add(map);
// FileOutputStream fileOutputStream=new
// FileOutputStream("UsernameAndPassword.txt");
// ObjectOutputStream objectOutputStream=new
// ObjectOutputStream(fileOutputStream);
// objectOutputStream.writeObject(list);
// fileOutputStream.close();
// System.out.println("You have succeeded in input the data to the
// UsernameAndPassword.txt");
// }
// catch(Exception e){
// e.printStackTrace();
// }
// }
// else{
// System.out.println("Please input the right username. This username is not
// existed");
// }

// }

// } catch (FileNotFoundException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// } catch (ClassNotFoundException e) {
// e.printStackTrace();
// }
// }

// public void deleteaccountinUserNameAndCustomerid(String username)throws
// Exception,FileNotFoundException,IOException,ClassNotFoundException{
// //把原来文件里的东西拿出来，并且删除某一行
// FileInputStream freader;
// try {
// freader = new FileInputStream("ALLACCOUNTS.txt");
// ObjectInputStream objectInputStream = new ObjectInputStream(freader);
// HashMap<String,String> temp=new HashMap<>();
// List<HashMap<String, String>> list=(List<HashMap<String,
// String>>)objectInputStream.readObject();
// System.out.println(list);
// for (HashMap<String, String> map : list) {
// if(map.containsKey(username)){
// map.remove(username);
// //删除原来的文件
// File file=new File("ALLACCOUNTS.txt");
// file.delete();
// //放入新的文件中，
// try{
// List<HashMap<String,String>> newlist=new ArrayList<HashMap<String,String>>();
// newlist.add(map);
// FileOutputStream fileOutputStream=new FileOutputStream("ALLACCOUNTS.txt");
// ObjectOutputStream objectOutputStream=new
// ObjectOutputStream(fileOutputStream);
// objectOutputStream.writeObject(list);
// fileOutputStream.close();
// System.out.println("You have succeeded in deleting the data from the
// ALLACCOUNTS.txt");
// }
// catch(Exception e){
// e.printStackTrace();
// }
// }
// else{
// System.out.println("Please input the right username. This username is not
// existed");
// }

// }

// } catch (FileNotFoundException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// } catch (ClassNotFoundException e) {
// e.printStackTrace();
// }
// }

// public void deleteaccountinUserNameAndCustomerid(String username)throws
// Exception,FileNotFoundException,IOException,ClassNotFoundException{
// //把原来文件里的东西拿出来，并且删除某一行
// FileInputStream freader;
// try {
// freader = new FileInputStream("ALLACCOUNTS.txt");
// ObjectInputStream objectInputStream = new ObjectInputStream(freader);
// HashMap<String,String> temp=new HashMap<>();
// List<HashMap<String, String>> list=(List<HashMap<String,
// String>>)objectInputStream.readObject();
// System.out.println(list);
// for (HashMap<String, String> map : list) {
// if(map.containsKey(username)){
// map.remove(username);
// //删除原来的文件
// File file=new File("ALLACCOUNTS.txt");
// file.delete();
// //放入新的文件中，
// try{
// List<HashMap<String,String>> newlist=new ArrayList<HashMap<String,String>>();
// newlist.add(map);
// FileOutputStream fileOutputStream=new FileOutputStream("ALLACCOUNTS.txt");
// ObjectOutputStream objectOutputStream=new
// ObjectOutputStream(fileOutputStream);
// objectOutputStream.writeObject(list);
// fileOutputStream.close();
// System.out.println("You have succeeded in deleting the data from the
// ALLACCOUNTS.txt");
// }
// catch(Exception e){
// e.printStackTrace();
// }
// }
// else{
// System.out.println("Please input the right username. This username is not
// existed");
// }

// }

// } catch (FileNotFoundException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// } catch (ClassNotFoundException e) {
// e.printStackTrace();
// }
// }
