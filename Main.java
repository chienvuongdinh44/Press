public class Main {
    public static void main(String[] args) {
        Book book = new Book("Shadow Slave", "G3", "asdasdasd",2022);
        VendingMachine vd = new VendingMachine(1.2, "password");
        System.out.println(vd.getPrice(0));
    }
}
