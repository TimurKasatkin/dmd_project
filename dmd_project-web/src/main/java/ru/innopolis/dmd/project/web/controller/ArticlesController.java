package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.dmd.project.service.ArticleService;

import static ru.innopolis.dmd.project.web.util.Constants.ARTICLES_PAGE_LIMIT;

/**
 * @author Timur Kasatkin
 * @date 23.10.15.
 * @email aronwest001@gmail.com
 */
@Controller
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    private ArticleService articleService;


    @RequestMapping(method = RequestMethod.GET)
    public String articles(@RequestParam(required = false, defaultValue = "title") String sortBy,
                           @RequestParam(required = false, defaultValue = "0") int offset,
                           @RequestParam(required = false, defaultValue = "true") boolean isAsc,
                           ModelMap map) {
        map.addAttribute("articles", articleService
                .findAllSortedBy(sortBy, isAsc, offset, ARTICLES_PAGE_LIMIT));
        map.addAttribute("sortBy", sortBy);
        map.addAttribute("offset", offset);
        map.addAttribute("isAsc", isAsc);
//        if (offset == 0)
        return "articles/articles";
//        return "articles/articlesInclude";
    }

//    @RequestMapping(value = "/articles/{id:[1-9]+\\d*}", method = RequestMethod.GET)
//    public String articleById(@PathVariable Long id, ModelMap map) {
//        map.addAttribute("articles", articleService.findBy("id", id));
//        return "articlesInclude";
//    }

}
