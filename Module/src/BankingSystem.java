import Models.Customer;
import Models.Manager;
import Models.Users;

public class BankingSystem{
    public static void main(String[] args) {
//        System.out.println("Hello World");
//        Customer cust1 = new Customer("Idan", "Cohen", 5327);
//        Customer cust2 = new Customer("Itay", "Grinberg", 7312);
//        Customer cust3 = new Customer("Amit", "Inbar", 42127);
//        Manager manager = new Manager("Ido", "Gever");
//        Users polymorphism = new Manager("Poly", "Morphism");
//        //cust.withdraw(6000);
//        System.out.println(cust1);
//        System.out.println(cust2);
//        System.out.println(cust3);
//        System.out.println(manager);
//        System.out.println(polymorphism);
        try{
            System.out.println(API.getCustomer("1"));
        } catch (Exception e){
            System.out.println(e);
        }


    }
}