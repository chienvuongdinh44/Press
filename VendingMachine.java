import java.util.List;
import java.util.ArrayList;

/**
 * Represents a vending machine that stores and sells books.
 */
public class VendingMachine {
    private List<Book> shelf;
    private double locationFactor;
    private int cassette;
    private int safe;
    private String password;

    /**
     * Constructs a new VendingMachine with the given location factor and password.
     * @param lf the location factor used to calculate book prices
     * @param pw the password required for restocking and emptying the safe
     */
    public VendingMachine(double lf, String pw) {
        locationFactor = lf;
        password = pw;
        cassette = 0;
        safe = 0;
        shelf = new ArrayList<Book>();
    }

    /**
     * Returns the current value of the cassette.
     * @return the cassette value
     */
    public int getCassette() {
        return cassette;
    }

    /**
     * Inserts a coin of the given denomination into the cassette.
     * @param coin the coin value to insert (must be 1, 2, 5, 10, 20, 50, 100, or 200)
     * @throws IllegalArgumentException if the coin is not a valid denomination
     */
    public void insertCoin(int coin) {
        if (coin == 1 || coin == 2 || coin == 5 || coin == 10 || coin == 20 || coin == 50 || coin == 100 || coin == 200) {
            cassette += coin;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Cancels the current sale and returns the value of the cassette.
     * @return the value of the cassette before resetting
     */
    public int cancel() {
        int sale = cassette;
        cassette = 0;
        return sale;
    }

    /**
     * Restocks the shelf with the given list of books if the password matches.
     * @param books the list of books to add to the shelf
     * @param pw the password to authenticate the restock
     * @throws InvalidPasswordException if the password does not match
     */
    public void restock(List<Book> books, String pw){
        if(pw.equals(password)) {
            shelf.addAll(books);
        }
        else {
            throw new InvalidPasswordException();
        }
    }

    /**
     * Empties the safe and returns its value if the password matches.
     * @param pw the password to authenticate the operation
     * @return the value of the safe before resetting
     * @throws InvalidPasswordException if the password does not match
     */
    public int emptySafe(String pw) {
        if(pw.equals(password)) {
            int revenue = safe;
            safe = 0;
            return revenue;
        }
        else {
            throw new InvalidPasswordException();
        }
    }

    /**
     * Returns a list of strings describing the books currently on the shelf.
     * @return list of book descriptions
     */
    public List<String> getCatalogue() {
        List<String> catalogue = new ArrayList<>();
        for (int i = 0; i < shelf.size(); i++) {
            catalogue.add(shelf.get(i).toString());
        }
        return catalogue;
    }

    /**
     * Returns the price of the book at the given shelf index.
     * @param index the index of the book on the shelf
     * @return the price of the book in pence
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public int getPrice(int index) {
        if(index >= 0 && index < shelf.size()) {
            Book book = shelf.get(index);
            int pages = book.getPages();
            int price = (int) Math.ceil(pages*locationFactor);
            return price;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Sells the book at the given shelf index if the cassette contains enough money.
     * @param index the index of the book on the shelf
     * @return the book that was purchased
     * @throws IndexOutOfBoundsException if the index is invalid
     * @throws CassetteException if the cassette does not contain enough money
     */
    public Book buyBook(int index) {
        if(index < 0 || index >= shelf.size()) {
            throw new IndexOutOfBoundsException();
        }
        else if(getPrice(index) > cassette) {
            throw new CassetteException();
        }
        else {
            Book book = shelf.get(index);
            shelf.remove(index);
            cassette = cassette - getPrice(index);
            safe += getPrice(index);
            return book;
        }
    }
}