package Assignment4;

public class LibraryItem<T> {
    private String title;
    private String author;
    private String itemID;
    private T content; // Generic content type

    /**
     * Constructor for creating a library item
     * @param title Title of the item
     * @param author Author/creator of the item
     * @param itemID Unique identifier for the item
     * @param content Content specific to the item type
     */
    public LibraryItem(String title, String author, String itemID, T content) {
        this.title = title;
        this.author = author;
        this.itemID = itemID;
        this.content = content;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ID: " + itemID + " | Title: " + title + " | Author: " + author +
                " | Content Type: " + (content != null ? content.getClass().getSimpleName() : "None");
    }
}
