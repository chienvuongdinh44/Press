/**
 * Represents a book with a title, author, content, and edition number.
 */
public class Book {
    private String title;
    private String author;
    private String content;
    private int edition;

    /**
     * Returns the title of the book.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the book.
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the content of the book.
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the edition number of the book.
     * @return the edition
     */
    public int getEdition() {
        return edition;
    }

    /**
     * Constructs a new Book with the given title, author, content, and edition.
     * @param t the title
     * @param a the author
     * @param c the content
     * @param e the edition number
     */
    public Book(String t, String a, String c, int e) {
        title = t;
        author = a;
        content = c;
        edition = e;
    }

    /**
     * Returns the number of pages in the book, where each page holds 666 characters.
     * @return the number of pages rounded up
     */
    public int getPages() {
        int totalPages = (int) Math.ceil(content.length()/666.0);
        return totalPages;
    }

    /**
     * Returns a string representation of the book showing title, author, and edition.
     * @return formatted string with title, author, and edition
     */
    public String toString() {
        String toString = "Title: " + title + "\n" + "Author: " + author + "\n" + "Edition: " + edition + "\n";
        return toString;
    }
}