import Models.Auth;
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
//        try{
//            API api = new API();
//            api.createConnection(0);
//            System.out.println("success");
//        } catch (Exception e){
//            System.out.println(e);
//        }
//    Customer cust = new Customer(3, "Idan", "Cohen", 5327);
//    cust.deposit(173);
//    cust.deposit(1000);
//    Manager man = new Manager("John", "Cohen");
//        /*System.out.println(man.getCustomerById(0));*/
//        man.getCustomerByName("Idan");
        Auth auth = new Auth();
        try {
            auth.validatePW("HelloWorld", "00000");
        } catch (Exception e) {
            System.out.println("error");
            return;
        }
    }
}