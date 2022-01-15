package ibf2021.mock_assessment.model;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class Book {
    private String author;
    private String title;
    private String BookId;

    // Constructors
    public Book() {
        this.BookId = hexBookId();
    }

    public Book(String author, String title) {
        this.author = author;
        this.title = title;
        this.BookId = hexBookId();
    }

    // Method to create hex strings for bookId
    private synchronized String hexBookId() {
        SecureRandom sr = new SecureRandom();
        long num = sr.nextLong(1 * 100000000L);
        String formattedId = String.format("%09x", num);
        return formattedId;
    }

    // Getters and Setters
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookId() {
        return this.BookId;
    }

    public void setBookId(String BookId) {
        this.BookId = BookId;
    }

}
