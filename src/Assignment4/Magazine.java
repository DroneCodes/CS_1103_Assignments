package Assignment4;

public class Magazine {
    private String issueDate;
    private String publisher;
    private String category;

    public Magazine(String issueDate, String publisher, String category) {
        this.issueDate = issueDate;
        this.publisher = publisher;
        this.category = category;
    }

    // Getters and setters
    public String getIssueDate() {
        return issueDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Magazine [Issue Date: " + issueDate + ", Publisher: " + publisher + ", Category: " + category + "]";
    }
}