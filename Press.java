import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.io.BufferedReader;

/**
 * Represents a printing press that prints and stores books for distribution.
 */
public class Press {
    private Map<String, List<Book>> shelf;
    private int shelfSize;
    private Map<String, Integer> edition;
    private String pathToBookDir;

    /**
     * Constructs a new Press with the given directory path and shelf size.
     * @param pathToBookDir the path to the directory containing book text files
     * @param shelfSize the maximum number of copies to store per book
     */
    public Press(String pathToBookDir, int shelfSize) {
        shelf = new HashMap<>();
        edition = new HashMap<>();
        this.shelfSize = shelfSize;
        this.pathToBookDir = pathToBookDir;

        try {
            File directoryPath = new File(pathToBookDir); // Create a File object represent the directory at the given path
            File[] filesList = directoryPath.listFiles(); // Look into the array and return a list of files
            for(File file : filesList) {
            shelf.put(file.getName(), new ArrayList<>()); // Put the name of the file as key
            edition.put(file.getName(), 0);
            }
        }catch(Exception e) {
        } 
    }

    /**
     * Prints a single copy of the book identified by the given book ID and edition.
     * @param bookID the filename identifying the book to print
     * @param edition the edition number to assign to the printed book
     * @return the printed Book object
     * @throws IllegalArgumentException if the bookID is not in the catalogue
     * @throws IOException if the file content is not in the expected format
     */
    protected Book print(String bookID, int edition) throws IOException {
        if(shelf.containsKey(bookID)) {
            String title = "";
            String author = "";
            String content = "";
            boolean contentStarted = false;
            
            File book = new File(pathToBookDir, bookID); // File of the book from the directoryPath folder
            BufferedReader reader = new BufferedReader(new FileReader(book)); 
            String line = reader.readLine(); // Read each line from the file
            while (line != null) {
                if(contentStarted) {
                    content += line +"\n";
                }
                else if(line.startsWith("Title:")) {
                    int index = line.indexOf(':');
                    title = line.substring(index + 2);
                }
                else if(line.startsWith("Author:")) {
                    int index = line.indexOf(':');
                    author = line.substring(index + 2);
                }
                else if(line.startsWith("*** START OF")) {
                    contentStarted = true;
                }

                line = reader.readLine();
            }
            reader.close(); // Need to close the reader
            
            if(title.isEmpty() || author.isEmpty() || content.isEmpty()) {
                throw new IOException();
            }
            Book printBook = new Book(title, author, content, edition);
            return printBook;         
            }
        else {
        throw new IllegalArgumentException();
        }
    }

    /**
     * Returns a list of book IDs corresponding to the books this press can produce.
     * @return list of valid book ID strings
     */
    public List<String> getCatalogue() {
        List<String> catalogue = new ArrayList<>(shelf.keySet());
        return catalogue;
    }

    /**
     * Returns the requested number of copies of the given book.
     * Takes books from the shelf if available, otherwise prints new ones and restocks the shelf.
     * @param bookID the filename identifying the book to request
     * @param amount the number of copies requested
     * @return a list of Book objects of the requested size
     * @throws IllegalArgumentException if the bookID is not in the catalogue
     */
    public List<Book> request(String bookID, int amount) {
        if(shelf.containsKey(bookID)) {
            List<Book> result = new ArrayList<>(amount);
            for (int i = 0; i < amount; i++) {
                if(shelf.get(bookID).isEmpty()) {
                    try{
                        for(int j = 0; j < shelfSize + (amount - result.size()); j++) {
                            shelf.get(bookID).add(print(bookID,edition.get(bookID) + 1));
                        }
                        edition.put(bookID, edition.get(bookID) + 1); // Update edition
                    
                        for (int z = 0; z < amount - result.size(); z++) {
                            Book book = shelf.get(bookID).remove(0);
                            result.add(book);
                        }
                    }catch(IOException ioe) {
                        return new ArrayList<>();
                    }
                    break;
                }
                else{
                    Book book = shelf.get(bookID).remove(0);
                    result.add(book);
                }

            }
            return result;
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}