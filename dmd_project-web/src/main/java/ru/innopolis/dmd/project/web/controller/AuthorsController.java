package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.service.AuthorService;

import static ru.innopolis.dmd.project.web.util.Constants.AUTHORS_PAGE_LIMIT;

/**
 * @author Timur Kasatkin
 * @date 31.10.15.
 * @email aronwest001@gmail.com
 */
@Controller
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(method = RequestMethod.GET)
    public String authors(@RequestParam(required = false, defaultValue = "first_name") String sortBy,
                          @RequestParam(required = false, defaultValue = "0") int offset,
                          @RequestParam(required = false, defaultValue = "true") boolean isAsc,
                          ModelMap map) {
        map.addAttribute("authors", authorService.findAllSortedBy("first_name", isAsc, offset, AUTHORS_PAGE_LIMIT));
        return "authors/authors";
    }

    @RequestMapping(value = "/{id:[1-9]+[0-9]*}", method = RequestMethod.GET)
    public String author(@PathVariable Long id, ModelMap map) {
        Author author = authorService.findById(id);
        map.addAttribute("author", author);
        return "authors/author";
    }

}
