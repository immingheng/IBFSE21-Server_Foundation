package ibf2021.mock_assessment.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ibf2021.mock_assessment.model.Book;

// Repo that extends CrudRepository based on type Object with String ID
@Repository
public interface RedisRepo extends CrudRepository<Object, String> {

    // Search by author & alphabetical
    List<Book> SearchByAuthor(String author);

    // Search by title & alphabetical
    List<Book> SearchByTitle(String title);

    // Sort by alphabetical
    List<Book> sortA2Z(List<Book> books);

    // Sort by reverse alphabetical
    List<Book> sortZ2A(List<Book> books);

}
