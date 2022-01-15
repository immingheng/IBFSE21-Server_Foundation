package ibf2021.mock_assessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ibf2021.mock_assessment.model.Author;
import ibf2021.mock_assessment.model.Book;

@Service
public class RedisService {

    @Autowired
    RedisRepo redisRepo;

    @Autowired
    Book book;

    @Autowired
    Author author;

    @Autowired
    RedisTemplate<String, Object> template;
    // Business logics:
    // 1. Pagination -> Display a list of items in a page
    // 2. Search library using queries with title and author
    // 3. add existing databases (The 4 books)
    // 4. Sort by title's alphabetical order
    // 5. Sort by author's name in alphabetical order

    public List<Book> search() {
        return author.getBooks();
    }
}
