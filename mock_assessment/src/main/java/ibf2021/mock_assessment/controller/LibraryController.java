package ibf2021.mock_assessment.controller;

// SLF4J Logging tool
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Spring packages
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import ibf2021.mock_assessment.model.Author;
import ibf2021.mock_assessment.model.Book;
import ibf2021.mock_assessment.model.Search;
import ibf2021.mock_assessment.service.RedisService;

@Controller
public class LibraryController {

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    Book book;

    @Autowired
    Author author;

    @Autowired
    Search search;

    @Autowired
    RedisService service;

    @GetMapping("/")
    public String searchForm(Model model) {
        model.addAttribute("Search", new Search());
        return "form";
    }

    @PostMapping("/Search")
    public String searchSubmit(@ModelAttribute Search search, Model model) {
        logger.info("Queried author -->" + search.getAuthor());
        logger.info("Queried title -->" + search.getBook());
        Book persistToRedis = new Book(search.getAuthor(), search.getBook());
        model.addAttribute("SubmittedSearch", persistToRedis);
        return "showResults";
    }

}
