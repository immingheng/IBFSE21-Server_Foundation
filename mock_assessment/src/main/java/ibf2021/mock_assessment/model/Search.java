package ibf2021.mock_assessment.model;

import org.springframework.stereotype.Component;

@Component
public class Search {
    private String author;
    private String book;

    // Constructor
    public Search() {
    }

    // Getters and Setters
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook() {
        return this.book;
    }

    public void setBook(String book) {
        this.book = book;
    }

}
