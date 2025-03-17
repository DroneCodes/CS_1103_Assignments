package Assignment4;

public class Book {
    private int pageCount;
    private String genre;
    private String isbn;

    public Book(int pageCount, String genre, String isbn) {
        this.pageCount = pageCount;
        this.genre = genre;
        this.isbn = isbn;
    }

    // Getters and setters
    public int getPageCount() {
        return pageCount;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "Book [Pages: " + pageCount + ", Genre: " + genre + ", ISBN: " + isbn + "]";
    }
}