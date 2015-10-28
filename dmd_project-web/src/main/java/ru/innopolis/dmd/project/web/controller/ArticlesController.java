package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.innopolis.dmd.project.service.ArticleService;

import static ru.innopolis.dmd.project.web.util.Constants.ARTICLE_PAGE_LIMIT;

/**
 * @author Timur Kasatkin
 * @date 23.10.15.
 * @email aronwest001@gmail.com
 */
@Controller
public class ArticlesController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private FreeMarkerViewResolver viewResolver;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map) {
        map.addAttribute("articles", articleService
                .findAllSortedBy("title", true, 0, ARTICLE_PAGE_LIMIT));
        return "index";
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public String articles(@RequestParam(value = "offset", required = false) Integer offset, ModelMap map) {
        map.addAttribute("articles", articleService
                .findAllSortedBy("title", true, offset, ARTICLE_PAGE_LIMIT));
        return "articlesInclude";
    }

//    @RequestMapping(value = "/articles/{id:[1-9]+\\d*}", method = RequestMethod.GET)
//    public String articleById(@PathVariable Long id, ModelMap map) {
//        map.addAttribute("articles", articleService.findBy("id", id));
//        return "articlesInclude";
//    }

}
