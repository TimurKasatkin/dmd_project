package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.service.AuthorService;

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

    @RequestMapping("/{id:[1-9]+[0-9]*}")
    public String author(@PathVariable Long id, ModelMap map) {
        Author author = authorService.findById(id);
        map.addAttribute("author", author);
        return null;
    }

}
